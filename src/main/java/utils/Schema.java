package utils;

public class Schema {
    private static String schema;
    private static String instance;

    public static String getSchema() {
        return schema;
    }

    public static void setSchema(String schemas) {
        Schema.schema = schemas;
    }

    public static String getInstance() {
        return instance;
    }

    public void setInstance(String instances) {
        instance = instances;
    }
}

