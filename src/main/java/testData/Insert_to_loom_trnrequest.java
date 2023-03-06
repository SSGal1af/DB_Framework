package testData;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import connections.DBconnection;
import utils.Table_Mapping;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert_to_loom_trnrequest {

    public void importCSV() throws SQLException, FileNotFoundException {

        String query = "INSERT INTO LOOM.newtable (id, termid, reqtype, reqflag, reqprocessing, batch, " +
                "stan, amount, currency, pan, cardseq, exp, entrymode, emvdata, applicationid, pankey, " +
                "posdata, cardissuer,  configversion, cvm, bintable_lineid, transactionguid, time_zone," +
                " amounttip, cardholderlanguagecode, receiptnumber, terminalserialnumber) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";

        DBconnection db_connection = new DBconnection("postgres");
        Connection conn = db_connection.activeConnection("postgres");

        ColumnPositionMappingStrategy<Table_Mapping> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Table_Mapping.class);
        String[] columns = new String[]{"id", "termid", "reqtype", "reqflag", "reqprocessing", "batch", "stan", "amount", "currency", "pan", "cardseq", "exp"
                , "entrymode", "emvdata", "applicationid", "pankey", "posdata", "cardissuer",  "configversion", "cvm", "bintable_lineid", "transactionguid", "time_zone", "amounttip"
        , "cardholderlanguagecode", "receiptnumber", "terminalserialnumber"};
        strategy.setColumnMapping(columns);
        String csvFilename = "TestData.csv";

        CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
        CsvToBean<Table_Mapping> csv = new CsvToBeanBuilder<Table_Mapping>(csvReader).withMappingStrategy(strategy).build();

        PreparedStatement prepStatement = conn.prepareStatement(query);
        final int batchSize = 10000;
        int count = 5000;

        for (Table_Mapping newtable : csv) {
            prepStatement.setInt(1, newtable.getID());
            prepStatement.setString(2, newtable.getTermid());
            prepStatement.setString(3, newtable.getReqtype());
            prepStatement.setString(4, newtable.getReqflag());
            prepStatement.setString(5, newtable.getReqprocessing());
            prepStatement.setInt(6, newtable.getBatch());
            prepStatement.setInt(7, newtable.getStan());
            prepStatement.setInt(8, newtable.getAmount());
            prepStatement.setString(9, newtable.getCurrency());
            prepStatement.setString(10, newtable.getPan());
            prepStatement.setInt(11, newtable.getCardseq());
            prepStatement.setString(12, newtable.getExp());
            prepStatement.setString(13, newtable.getEntrymode());
            prepStatement.setString(14, newtable.getEmvdata());
            prepStatement.setString(15, newtable.getApplicationid());
            prepStatement.setString(16, newtable.getPankey());
            prepStatement.setString(17, newtable.getPosdata());
            prepStatement.setString(18, newtable.getCardissuer());
            prepStatement.setString(19, newtable.getConfigversion());
            prepStatement.setString(20, newtable.getCvm());
            prepStatement.setString(21, newtable.getBintable_lineid());
            prepStatement.setString(22, newtable.getTransactionguid());
            prepStatement.setString(23, newtable.getTime_zone());
            prepStatement.setInt(24, newtable.getAmounttip());
            prepStatement.setString(25, newtable.getCardholderlanguagecode());
            prepStatement.setString(26, newtable.getReceiptnumber());
            prepStatement.setString(27, newtable.getTerminalserialnumber());
            prepStatement.addBatch();

            if (++count % batchSize == 0) {
                prepStatement.executeBatch();
            }
        }
        prepStatement.executeBatch();
        System.out.println("Test data imported to newtable table ...");
        prepStatement.close();

        db_connection.dbClose();

    }
}
