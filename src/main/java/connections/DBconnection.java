package connections;

import org.apache.log4j.Logger;
import utils.PropertiesReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private PropertiesReader propertiesReader = PropertiesReader.getInstance();
    private Connection conn;
    private static final Logger logger = Logger.getLogger(DBconnection.class);

    public DBconnection(String dbInstance) {
        driverInitializing(dbInstance);
    }

    private void driverInitializing(String dbInstance) {
        if (dbInstance.equals("postgres")) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (Exception e) {
                System.out.println("Can't init SQLServerDriver driver.");
                logger.error("Exception on SQLServerDriver initialization.", e);
            }
        } else {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (Exception e) {
                System.out.println("Can't init SQLServerDriver driver.");
                logger.error("Exception on SQLServerDriver initialization.", e);
            }
        }
    }

    public Connection activeConnection(String dbInstance) {
        if (dbInstance.equals("postgres")) {
            try {
                System.out.println("Connecting to postgres db");
                conn = DriverManager.getConnection(propertiesReader.getDbUrl(), propertiesReader.getDbUserName(), propertiesReader.getDbPassword());
                System.out.println("Postgres DB connection enabled");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                System.out.println("Connecting to oracle db");
                conn = DriverManager.getConnection(propertiesReader.getDbUrl(), propertiesReader.getDbUserName(), propertiesReader.getDbPassword());
                System.out.println("oracle DB connection enabled");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return conn;
    }
    public void dbClose() {
        try {
            conn.close();
            System.out.println("DB Connection Closed ... \n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
