package Model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SaleHistoryDB {
    private final String createSaleTable 
        = "Create Table SALEHISTORY (" +
            "id VARCHAR(40) NOT NULL, " +
            "num VARCHAR(40), " +
            "date_sale DATE NOT NULL, " +
            "PRIMARY KEY (id, date_sale));";
    private static Connection connection;
    private static SaleHistoryDB instance;

    private SaleHistoryDB() {
        connection = DatabaseManager.getInstance().getConnection();
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            PreparedStatement statement = connection.prepareStatement(createSaleTable);
            ResultSet rs = dbm.getTables(null, null, "INVENTORY", null);
            if (!rs.next()) {
                statement.executeUpdate(this.createSaleTable);
                System.out.println("create saleHistory Table");
            } else {
                System.out.println("exist saleHistory Table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SaleHistoryDB getInstance() {
        if (instance == null) {
            instance = new SaleHistoryDB();
        }
        return instance;
    }

    public void insertData(Vector<String> row){
        Vector<String> q = queryOnceData(row.get(0), row.get(2));
        if (q.isEmpty()) {
            String insertSyn = "INSERT INTO SALEHISTORY (id, num, date) VALUES (?, ?, ?);";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertSyn)) {
                insertStatement.setString(1, row.get(0));
                insertStatement.setString(2, row.get(1));
                insertStatement.setString(3, row.get(2));
                insertStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            int newNum = Integer.valueOf(row.get(1)) + Integer.valueOf(q.get(1));
            row.set(1, String.valueOf(newNum));
            updateData(row);
        }
    }

    public void updateData(Vector<String> row){
        String updateSyn = "UPDATE SALEHISTORY SET num = ? WHERE id = ? AND sale_date = ?;";
        try (PreparedStatement updateStament = connection.prepareStatement(updateSyn)) {
            updateStament.setString(1, row.get(1)); // num
            updateStament.setString(2, row.get(0)); // id
            updateStament.setString(3, row.get(2)); // sale_date
            updateStament.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Vector<Vector<String>> queryData(){
        String querySyn = "Select * FROM SALEHISTORY;";
        Vector<Vector<String>> results = new Vector<Vector<String>>();

        // try-with-resource
        try(PreparedStatement queryStatement = connection.prepareStatement(querySyn);) {
            ResultSet rs = queryStatement.executeQuery();
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(rs.getString("id"));
                row.add(rs.getString("num"));
                row.add(rs.getString("sale_date"));
                results.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public Vector<String> queryOnceData(String id, String date){
        String querySyn = "Select * FROM SALEHISTORY WHERE id = ? AND sale_date = ?;";
        Vector<String> result = new Vector<String>();

        // 嘗試使用 try-with-resource，觀察是否會因此造成連線中斷
        // try-with-resource 在()中，有實作Autocloseable的物件在離開try catch區塊將會自動關閉
        try(PreparedStatement queryStatement = connection.prepareStatement(querySyn);){  
            queryStatement.setString(1, id);
            queryStatement.setString(2, date);
            ResultSet rs = queryStatement.executeQuery();
            if (!rs.next()) 
                return result;
            result.add(rs.getString("id"));
            result.add(rs.getString("num"));
            result.add(rs.getString("sale_date"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }

}
