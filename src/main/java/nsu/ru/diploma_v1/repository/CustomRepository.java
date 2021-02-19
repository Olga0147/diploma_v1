package nsu.ru.diploma_v1.repository;

import lombok.AllArgsConstructor;
import nsu.ru.diploma_v1.model.entity.SysAttribute;
import nsu.ru.diploma_v1.utils.SysTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class CustomRepository{

    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SysTypes sysTypes;

    /**
     * Создать таблицу
     *
     * SysAttribute sysAttribute = new SysAttribute(1,"n",1,1,0,false);
     * customRepository.createTable("testt",List.of(sysAttribute));
     *
     *
     * @param tableName название новой таблицы
     * @param attributeList её атрибуты
     * @return что-то
     */
    @Transactional
    public void createTable(String tableName, List<SysAttribute> attributeList){
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("CREATE TABLE %s ( ID SERIAL NOT NULL PRIMARY KEY, ",tableName));

        for (SysAttribute attribute : attributeList) {
            sql.append(attributeToString(attribute));
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(");");

        System.out.println(sql.toString());

        try{
        //v1
        jdbcTemplate.execute(sql.toString());
        //v2
        //entityManager.createNativeQuery(sql.toString()).executeUpdate();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            //TODO
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
        System.out.println(sql);
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
        str.append(
                String.format(
                        " %s %s",
                        attribute.getName(),
                        sysTypes.getType(attribute.getAttributeType())
                )
        );
        //размер атрибута (если есть)
        if(attribute.getAttributeSize()!=null){
            str.append(
                    String.format(
                            "(%s)",
                            attribute.getAttributeSize()
                    )
            );
        }
        //ненулевой?
        if(!attribute.isCanBeNull()){
            str.append(" NOT NULL");
        }
        str.append(",");
        return str.toString();
    }

    @Transactional
    public void insertInTable(Map<String,Object> list, String tableName){
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("INSERT INTO %s ( ",tableName));

        StringBuilder vars = new StringBuilder();
        vars.append("VALUES ( ");

        SqlParameterSource namedParameters = new MapSqlParameterSource().addValues(list);

        for (Map.Entry<String, Object> objectAttribute : list.entrySet()) {
            sql.append(objectAttribute.getKey()).append(",");
            vars.append(":").append(objectAttribute.getKey()).append(",");
        }
        sql.deleteCharAt(sql.length()-1);
        vars.deleteCharAt(vars.length()-1);
        sql.append(")");
        vars.append(");");

        sql.append(vars);
        System.out.println(sql.toString());

        namedParameterJdbcTemplate.update(sql.toString(), namedParameters);
    }

}
