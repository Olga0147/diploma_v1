package nsu.ru.diploma_v1.model.enums;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Типы системы
 */
public enum SysTypes {
    FLOAT(1,"FLOAT"),
    INTEGER(2, "INTEGER"),
    SMALLINT(3, "SMALLINT"),
    MMEDIA(4,"TEXT"),
    TEXT(5,"TEXT"),
    STRING(6,"VARCHAR"),
    XMEMO(7,"TEXT"),
    TIME(8,"DATE");

    private int id;
    private String dbType;
    private static final Map map = new HashMap<>();
    private static final List<String> sysTypes = new LinkedList<>();

    static {
        for (SysTypes type : SysTypes.values()) {
            map.put(type.id, type);
        }
        for (SysTypes type : SysTypes.values()) {
            sysTypes.add(type.name());
        }
    }

    SysTypes(int id, String dbType){
        this.id = id;
        this.dbType = dbType;
    }

    public int getId() {
        return id;
    }
    public String getDbType(){ return dbType;}
    public String getUserType(){ return this.name();}
    public static List<String> getSysTypes(){ return sysTypes; }

    public static int getIdByUserType(String userType) throws IllegalArgumentException{
        SysTypes sysTypes = SysTypes.valueOf(userType);
        return sysTypes.id;
    }

    public static String getDbType(int id){
        SysTypes type = (SysTypes)map.get(id);
        return type.dbType;
    }

    public static boolean checkNeedSize(String type){
        return type.equals("VARCHAR");
    }


}
