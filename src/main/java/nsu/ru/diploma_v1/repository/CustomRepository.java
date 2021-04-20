package nsu.ru.diploma_v1.repository;

import lombok.AllArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.model.enums.database_types.SysTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class CustomRepository{

    //private final EntityManager entityManager;
    //entityManager.createNativeQuery(sql.toString()).executeUpdate();

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Создать таблицу
     *
     * @param tableName название новой таблицы
     * @param attributeList её атрибуты
     */
    @Transactional
    public void createTable(String tableName, List<SysAttribute> attributeList){
        StringBuilder columns = new StringBuilder();

        for (SysAttribute attribute : attributeList) {
            columns.append(attributeToString(attribute));
        }
        columns.deleteCharAt(columns.length()-1);

        String sql = String.format("CREATE TABLE %s ( ID SERIAL NOT NULL PRIMARY KEY, %s );",tableName,columns);
        try{
            jdbcTemplate.execute(sql);
        }
        catch (Exception e){
            //TODO : log + send error
        }
    }

    /**
     * Получаем колонки класса где атрибуты совпадают с указанными значениями
     * @param tableName имя таблицы
     * @return колонки
     */
    public List<java.util.Map<String, Object>> selectFromTable(
            @NonNull String tableName,
            @Nullable List<String> col,
            @Nullable Map<String,Object> param){

        StringBuilder columns = new StringBuilder();

        //COLUMNS
        if(col == null){
            columns.append("*");
        }else{
            for (String column : col) {
                columns.append(column);
                columns.append(",");
            }
            columns.deleteCharAt(columns.length()-1);
        }

        //WHERE
        StringBuilder where = new StringBuilder();
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValues(param);
        if(param != null){
            where.append("WHERE ");
            for (Map.Entry<String, Object> entry : param.entrySet()) {
                where.append(String.format("%s = :%s and ",entry.getKey(),entry.getKey()));
            }
            where.delete(where.length()-5,where.length()-1);
        }

        String sql = String.format("SELECT %s FROM %s %s",columns.toString(),tableName,where.toString());
         return namedParameterJdbcTemplate.queryForList(sql,namedParameters);
    }

    /**
     * Получаем строку для создания таблицы
     * @param attribute атрибут
     * @return строка
     */
    String attributeToString(SysAttribute attribute){

        StringBuilder str = new StringBuilder();

        //имя и тип атрибута
        str.append(String.format(" %s %s",
                        attribute.getName(),
                        SysTypes.getDbType(attribute.getAttributeType()))
        );

        //размер атрибута (если есть)
        if(attribute.getAttributeSize()!=null){
            str.append(String.format("(%s)",
                            attribute.getAttributeSize())
            );
        }

        //ненулевой ?
        if(!attribute.isCanBeNull()){
            str.append(" NOT NULL");
        }

        str.append(",");
        return str.toString();
    }

    /**
     * Получить объект
     * @param tableName имя таблицы
     * @param id идентификатор объекта
     * @return карта колонка - значение \ null
     */
    public Map<String, Object> getObject(@NonNull String tableName, @NonNull int id){
        Map<String,Object> param = new HashMap<>();
        param.put("ID",id);
        List<java.util.Map<String, Object>> list = selectFromTable(tableName,null,param);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Transactional
    public void insertInTable(Map<String,Object> list, String tableName){
        StringBuilder columns = new StringBuilder();
        StringBuilder vars = new StringBuilder();

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValues(list);

        for (Map.Entry<String, Object> objectAttribute : list.entrySet()) {
            columns.append(objectAttribute.getKey()).append(",");
            vars.append(":").append(objectAttribute.getKey()).append(",");
        }
        vars.deleteCharAt(vars.length()-1);
        columns.deleteCharAt(columns.length()-1);

        String sql = String.format("INSERT INTO %s ( %s ) VALUES ( %s )",tableName,columns,vars);
        namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    @Transactional
    public void updateRowInTable(Map<String,Object> list, String tableName){
        StringBuilder columns = new StringBuilder();
        String vars = "id = :id";

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValues(list);

        for (Map.Entry<String, Object> objectAttribute : list.entrySet()) {
            if(objectAttribute.getKey().equals("id")){continue;}
            columns.append(String.format("%s = :%s ",objectAttribute.getKey(),objectAttribute.getKey()));
        }

        String sql = String.format("UPDATE %s SET %s WHERE %s ",tableName,columns,vars);
        namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    @Transactional
    public int deleteRowInTable(String tableName, int id){
        Map<String,Object> list = new HashMap<>();
        list.put("id",id);
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValues(list);
        String sql = String.format("DELETE FROM %s WHERE id = :id",tableName);
        return namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    public void deleteTable(String tableName){
        try{
            jdbcTemplate.execute(String.format("DROP TABLE %s",tableName));
        }
        catch (Exception e){
            //TODO : log + send error
        }
    }

}
