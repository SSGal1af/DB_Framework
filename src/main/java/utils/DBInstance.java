package utils;

public class DBInstance {
    private static String dbInstance;


    public static void setDBInstance(String DBInstances) {
        DBInstance.dbInstance = DBInstances;
    }

    public static String getDBInstance() {
        return dbInstance;
    }
}
