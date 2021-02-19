package nsu.ru.diploma_v1.model.const_data;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class SystemTypes {

    private static final Map<String,InnerSysTypes> types;
    private static final Map<Integer,InnerSysTypes> innerTypes;

    static  {

        InnerSysTypes t1 = new InnerSysTypes(1, "FLOAT", "FLOAT");
        InnerSysTypes t2 = new InnerSysTypes(2, "INTEGER", "INTEGER");
        InnerSysTypes t3 = new InnerSysTypes(3, "bytea", "MEDIA");
        InnerSysTypes t4 = new InnerSysTypes(4, "TEXT", "TEXT");
        InnerSysTypes t5 = new InnerSysTypes(5, "VARCHAR", "STRING");
        InnerSysTypes t6 = new InnerSysTypes(6, "SMALLINT", "SMALLINT");
        InnerSysTypes t7 = new InnerSysTypes(7, "DATE", "TIME");

        innerTypes = new HashMap<>();
        innerTypes.put(t1.id, t1);
        innerTypes.put(t2.id, t2);
        innerTypes.put(t3.id,t3);
        innerTypes.put(t4.id,t4);
        innerTypes.put(t5.id, t5);//need size!
        innerTypes.put(t6.id, t6);
        innerTypes.put(t7.id, t7);

        types = new HashMap<>();
        types.put(t1.nameForUser, t1);
        types.put(t2.nameForUser, t2);
        types.put(t3.nameForUser, t3);
        types.put(t4.nameForUser, t4);
        types.put(t5.nameForUser, t5);//need size!
        types.put(t6.nameForUser, t6);
        types.put(t7.nameForUser, t7);
    }

    /**
     * Получить имя типа для базы данных
     * @param key ключ
     * @return имя типа для бд
     */
    public static String getType(Integer key){
        return innerTypes.get(key).nameForDB;
    }

    /**
     * По пользьзовательскому типу получить номер
     * @param type пользовательский тип
     * @return номер
     */
    public static Integer getIdByTypeForUser(String type){ return types.get(type).id;}

    /**
     * Нужно ли указывать размер?
     * @param type пользовательское имя типа
     * @return да-нет
     */
    public static Boolean checkNeedSize(String type){
        return type.equals("STRING");
    }

    public static Set<String> getTypes(){
        return types.keySet();
    }

    @AllArgsConstructor
    private static class InnerSysTypes{
        private final int id;
        private final String nameForDB;
        private final String nameForUser;
    }
}
