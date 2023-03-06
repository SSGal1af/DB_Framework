package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    public static String dbUserName;
    public static String dbPassword;
    public static String dbURL;
    public static String dbName;

    private static PropertiesReader propertiesReader;

    private PropertiesReader() {
        initDataBasePropValues();
    }
//+ Schema.getSchema()
    private void initDataBasePropValues() {
        InputStream inputStream = null;
        Properties prop = new Properties();
//TO DO IAS (component) Schema NFT , DB postgres
        String propFileName = "prepare_api_testdata_populate_db//src//main//resources//Config//" + SystemComponent.getComponent() + "_nft_" + DBInstance.getDBInstance() + "_db.properties";
        try {
            inputStream = new FileInputStream(propFileName);
           // inputStream = new ByteArrayInputStream(propFileName.getBytes());
            prop.load(inputStream);

            dbUserName = prop.getProperty("DB_USERNAME");
            dbPassword = prop.getProperty("DB_PASSWORD");
            dbURL = prop.getProperty("DB_URL");
            dbName = prop.getProperty("DB_NAME");

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                assert inputStream != null;
                inputStream.close();
            } catch (IOException e) {
                System.out.println("Exception: " + e);
                e.printStackTrace();
            }
        }
    }

    public static PropertiesReader getInstance() {
        if (propertiesReader == null) {
            propertiesReader = new PropertiesReader();
        }
        return propertiesReader;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbUrl() {
        return dbURL;
    }

}
