package Model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class InventoryDB {
    private final String createInventoryTableSyn = "Create Table INVENTORY (id VARCHAR(40) not null,"
            + "name VARCHAR(128), price VARCHAR(40), num VARCHAR(40))";
    private Connection connection;
    private Statement statement;

    public InventoryDB() {
        this.connection = DatabaseManager.getInstance().getConnection();
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        try {
            DatabaseMetaData dbm = this.connection.getMetaData();
            this.statement = this.connection.createStatement();
            ResultSet rs = dbm.getTables(null, null, "INVENTORY", null);
            if (!rs.next()) {
                this.statement.executeUpdate(this.createInventoryTableSyn);
                System.out.println("create Table");
            } else {
                System.out.println("exist Table");
            }
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    // This method can only update the quantity of the product
    public void updateDataNum(String id, String num) {
        Vector<String> oldData = queryOnceData(id);
        String oldNumString = oldData.get(3);
        int oldNum = Integer.valueOf(oldNumString);
        int newNum = Integer.valueOf(num);
        String updateSyn = "UPDATE INVENTORY SET num = '" + String.valueOf(oldNum - newNum) + "' WHERE id = '" + id
                + "'";
        try {
            this.statement.executeUpdate(updateSyn);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void insertData(Vector<String> row) {
        String id = row.elementAt(0);
        String name = row.elementAt(1);
        String price = row.elementAt(2);
        String num = row.elementAt(3);
        String querySyn = "SELECT * FROM INVENTORY WHERE id = '" + id + "'";

        try {
            ResultSet rs = this.statement.executeQuery(querySyn);
            if (rs.next()) {
                if (id.equals(rs.getString("id")))
                    System.out.println(rs);
                int dbNum = Integer.valueOf(rs.getString("num"));
                int numI = Integer.valueOf(num);
                String updateSyn;
                updateSyn = "UPDATE INVENTORY SET num = '" + String.valueOf(dbNum + numI) + "', "
                        + "name = '" + (name.equals("") ? rs.getString("name") : name) + "', "
                        + "price = '" + (price.equals("") ? rs.getString("price") : price) + "' "
                        + " WHERE id = '" + id + "'";
                this.statement.executeUpdate(updateSyn);
            } else {
                String insertSyn = "INSERT INTO INVENTORY " +
                        "VALUES ('" + id + "', '" + name + "', '" + price + "', '" + num + "')";
                this.statement.executeUpdate(insertSyn);
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public Vector<String> queryOnceData(String barcodeNum) {
        String querySyn = "SELECT id, name, price, num FROM INVENTORY WHERE id LIKE '" + barcodeNum + "'";
        Vector<String> queryData = new Vector<String>();
        try {
            ResultSet rs = this.statement.executeQuery(querySyn);
            if (!rs.next())
                return queryData;
            queryData.add(rs.getString("id"));
            queryData.add(rs.getString("name"));
            queryData.add(rs.getString("price"));
            queryData.add(rs.getString("num"));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return queryData;
    }

    public Vector<Vector<String>> queryAllData() {
        // The following program uses SQL syntax to query the table named inventory
        String querySyn = "SELECT id, name, price, num FROM INVENTORY";

        Vector<Vector<String>> queryDataList = new Vector<Vector<String>>();

        try {
            ResultSet rs = this.statement.executeQuery(querySyn);
            while (rs.next()) {

                // Retrieve by column name
                String id = rs.getString("id");
                String name = rs.getString("name");
                String price = rs.getString("price");
                String num = rs.getString("num");

                Vector<String> data = new Vector<String>();
                data.add(id);
                data.add(name);
                data.add(price);
                data.add(num);
                queryDataList.add(data);
                // Display values
                System.out.println(rs);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return queryDataList;
    }

    public void deleteTable() {
        String deleteStatement = "DELETE FROM INVENTORY";
        try {
            this.statement.execute(deleteStatement);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void disconnected() {
        if (this.statement != null || this.connection != null) {
            this.statement = null;
            this.connection = null;
            DatabaseManager.getInstance().closeConnection();
        }
    }
}
