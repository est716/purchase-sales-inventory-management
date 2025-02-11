package Model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SaleHistoryDB {
    private final String createSaleTable = "Create Table SALEHISTORY (id VARCHAR(40) not null, num VARCHAR(40), date_sale DATE NOT NULL, PRIMARY KEY (id, date_sale));";
    private static Connection connection;
    private static Statement statement;

    private SaleHistoryDB(){
        connection = DatabaseManager.getInstance().getConnection();
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            statement = connection.createStatement();
            ResultSet rs = dbm.getTables(null, null, "INVENTORY", null);
            if (!rs.next()) {
                statement.executeUpdate(this.createSaleTable);
                System.out.println("create saleHistory Table");
            } else {
                System.out.println("exist saleHistory Table");
            }
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    } 
}
