package testData;

import connections.DBconnection;
import connections.QueryFactory;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class Get_TestData_from_DB {

    DBconnection db_connection = new DBconnection("postgres");
    Connection conn = db_connection.activeConnection("postgres");
    QueryFactory queryFactory = new QueryFactory();

    public long latest_top5_trn(){
        List<Map<String, String>> queryResults;
        String query = "select id FROM LOOM.newtable order by id desc limit 1;";

        queryResults = queryFactory.queryExecutor(conn, query, "id");

        return Long.parseLong(queryResults.get(0).get("id"))+1;
    }
}
