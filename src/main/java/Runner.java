import testData.TestDataGenerator;
import utils.DBInstance;
import utils.Schema;
import utils.SystemComponent;

import java.io.IOException;
import java.sql.SQLException;

public class Runner {

    public static void main(String[] args) throws SQLException, IOException {


        int numofFiles = Integer.parseInt(args[0]);//1
        int numRows = Integer.parseInt(args[1]);//50000
        String component = "LOOM";//LOOM
        String dBInstance = "postgres";//postgres

        SystemComponent.setComponent(component);
        DBInstance.setDBInstance(dBInstance);

        TestDataGenerator testDataGenerator = new TestDataGenerator();
        testDataGenerator.generateTestData(numRows,numofFiles);
        System.out.println("TestData was successfully generated");
    }
}
