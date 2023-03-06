package testData;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class TestDataGenerator {
    public static String generateGUID() {
        return UUID.randomUUID().toString();
    }

    public static List<String[]> generateTestData(int numRows, int numofFiles) throws IOException, SQLException {

        List<String[]> data = new ArrayList<>();
        Random random = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Get_TestData_from_DB tesdata = new Get_TestData_from_DB();
        int topid = (int) tesdata.latest_top5_trn();
        for (int t = 0; t < numofFiles; t++) {
            data.clear();
            if (t > 0) {
                data.clear();
                topid = topid + numRows;
                System.out.println("were added" + " " + numRows + " " + "to ID" + " " + "Run" + t);
            }
            // Use a new list to accumulate the rows for each iteration
            List<String[]> rows = new ArrayList<>();
            for (int i = 0; i < numRows; i++) {
                int id = topid + i;
//                String termid = String.valueOf(topid);
                String reqtype = "Purchase";
                String reqflag = "Normal";
                String reqprocessing = "Online";
                int batch = random.nextInt(999999);
                int amount = random.nextInt(99999);
                String currency = String.valueOf(826);
                String pan = generateRandomString("0123456789", random, 16);
                int cardseq = random.nextInt(999);
                String exp = String.valueOf(0000);
                String entrymode = String.valueOf(021);
                String emvdata = generateRandomString(alphabet + "0123456789", random, 20);
                String applicationid = generateRandomString(alphabet + "0123456789", random, 14);
                String pankey = String.valueOf(99999);
                String cardissuer = "VISA EMV";
                String configversion = generateRandomString("0123456789", random, 13);
                String cvm = String.valueOf(1);
                String bintable_lineid = String.valueOf(99999);
                String transactionguid = String.valueOf(generateGUID());
                String time_zone = "UTC";
                int amounttip = 0;
                String cardholderlanguagecode = "en";
                String receiptnumber = String.valueOf(37);
                String terminalserialnumber = String.valueOf(99999);

                rows.add(new String[]{String.valueOf(id), String.valueOf(id), reqtype, reqflag, reqprocessing,
                        String.valueOf(batch), String.valueOf(batch), String.valueOf(amount), currency, pan,
                        String.valueOf(cardseq),exp, entrymode, emvdata, applicationid,
                        pankey, applicationid, cardissuer,
                        configversion, cvm, bintable_lineid, transactionguid, time_zone,
                        String.valueOf(amounttip), cardholderlanguagecode, receiptnumber, terminalserialnumber});

            }
            data.addAll(rows);
            saveToCSV(data);
            // import to the table that we need
            Insert_to_loom_trnrequest newcsv = new Insert_to_loom_trnrequest();
            newcsv.importCSV();
            System.out.println("Insert were performed");

        }
        return data;
    }

    private static String generateRandomString(String alphabet, Random random, int length) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < length; j++) {
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return sb.toString();
    }

    private static void saveToCSV(List<String[]> data) {
        try (FileWriter writer = new FileWriter("TestData.csv")) {
            for (String[] rowData : data) {
                for (int i = 0; i < rowData.length; i++) {
                    writer.append(rowData[i]);
                    if (i < rowData.length - 1) {
                        writer.append(',');
                    }
                }
                writer.append('\n');
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the test data to CSV file: " + e.getMessage());
        }
    }
}