package Model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;
public class SaleHistoryDB {
    private final String createSaleTable 
        = "Create Table SALEHISTORY (" +
            "id VARCHAR(40) NOT NULL, " +
            "num VARCHAR(40), " +
            "date_sale DATE NOT NULL, " +
            "PRIMARY KEY (id, date_sale))";
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
            ResultSet rs = dbm.getTables(null, null, "SALEHISTORY", null);
            if (!rs.next()) {
                statement.executeUpdate();
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
        LocalDate date = LocalDate.now();
        Vector<String> q = queryOnceData(row.get(0), date);
        if (q.isEmpty()) {
            String insertSyn = "INSERT INTO SALEHISTORY (id, num, date_sale) VALUES (?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertSyn)) {
                insertStatement.setString(1, row.get(0));
                insertStatement.setString(2, row.get(1));
                insertStatement.setDate(3, Date.valueOf(date));
                System.out.println(row.toString());
                insertStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            int newNum = Integer.valueOf(row.get(1)) + Integer.valueOf(q.get(2));
            row.set(1, String.valueOf(newNum));
            updateData(row);
        }
    }

    public void updateData(Vector<String> row){
        String updateSyn = "UPDATE SALEHISTORY SET num = ? WHERE id = ? AND date_sale = ?";
        try (PreparedStatement updateStament = connection.prepareStatement(updateSyn)) {
            updateStament.setString(1, row.get(1)); // num
            updateStament.setString(2, row.get(0)); // id
            updateStament.setDate(3, Date.valueOf(LocalDate.now())); // date_sale
            updateStament.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Vector<Vector<String>> queryData(){
        return queryData("5");
    }

    public Vector<Vector<String>> queryData(String topNum){
        String querySyn
            = "Select s.id, i.name, s.num, s.date_sale FROM"+
            " SALEHISTORY s INNER JOIN INVENTORY i ON s.id = i.id" + 
            " ORDER BY s.num DESC FETCH FIRST ? ROWS ONLY";
        Vector<Vector<String>> results = new Vector<Vector<String>>();
        // try-with-resource
        try(PreparedStatement queryStatement = connection.prepareStatement(querySyn);) {
            queryStatement.setString(1, topNum);
            ResultSet rs = queryStatement.executeQuery();
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(rs.getString("id"));
                row.add(rs.getString("name"));
                row.add(rs.getString("num"));
                row.add(rs.getString("date_sale"));
                results.add(row);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public Vector<String> queryOnceData(String id, LocalDate date){
        String querySyn 
            = "Select s.id, i.name, s.num, s.date_sale FROM SALEHISTORY s INNER JOIN INVENTORY i ON s.id = i.id WHERE s.id = ? AND s.date_sale = ?";
        Vector<String> result = new Vector<String>();

        // 嘗試使用 try-with-resource，觀察是否會因此造成連線中斷
        // try-with-resource 在()中，有實作Autocloseable的物件在離開try catch區塊將會自動關閉
        try(PreparedStatement queryStatement = connection.prepareStatement(querySyn);){  
            queryStatement.setString(1, id);
            queryStatement.setDate(2, Date.valueOf(date));
            ResultSet rs = queryStatement.executeQuery();
            if (!rs.next()) 
                return result;
            result.add(rs.getString("id"));
            result.add(rs.getString("name"));
            result.add(rs.getString("num"));
            result.add(rs.getString("date_sale"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    public void dropTable(){
        String deleteSyn = "Drop Table SALEHISTORY";
        try (PreparedStatement dropStatement = connection.prepareStatement(deleteSyn)) {
            dropStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("saleHistory is drop.");
    }

}
