package Model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class CommodityInformationDBInterface {
    private final String createTableSyn = "Create Table COMMODITYS (id VARCHAR(40) not null,"
            + "name VARCHAR(128), price VARCHAR(40), num VARCHAR(40))";
    // private final String deleteTable = "Drop Table commoditys";
    private Connection connection;
    private Statement statement;

    public CommodityInformationDBInterface() {
        String jdbcURL = "jdbc:derby:./CommodityInformation;create=true";

        try {
            this.connection = DriverManager.getConnection(jdbcURL);
            DatabaseMetaData dbm = connection.getMetaData();
            this.statement = connection.createStatement();
            ResultSet rs = dbm.getTables(null, null, "COMMODITYS", null);
            if (!rs.next()) {
                this.statement.executeUpdate(this.createTableSyn);
                System.out.println("create Table");
            } else {
                // this.statement.executeUpdate("Drop Table commoditys");
                System.out.println("exist Table");
            }
            // System.out.println("created");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    // insert data to DB, format is id, name, price, num
    public void insertData(String id, String name, String price, String num) {
    
        String querySyn = "SELECT id, num FROM COMMODITYS WHERE id = '" + id + "'";
        
        try {
            ResultSet rs = this.statement.executeQuery(querySyn);
            if (rs.next()) {
                if (id == rs.getString("id"))
                    System.out.println(rs);
                int dbNum = Integer.valueOf(rs.getString("num"));
                int numI = Integer.valueOf(num);
                String updateSyn;
                if (name.equals("")) {
                    updateSyn = "UPDATE COMMODITYS SET num = '" + String.valueOf(dbNum + numI) + "', "
                            + "price = '" + price + "' "
                            + " WHERE id = '" + id + "'";
                } else {
                    updateSyn = "UPDATE COMMODITYS SET num = '" + String.valueOf(dbNum + numI) + "', "
                            + "name = '" + name + "', "
                            + "price = '" + price + "' "
                            + " WHERE id = '" + id + "'";
                }
                this.statement.executeUpdate(updateSyn);
            } else {
                String insertSyn = "INSERT INTO COMMODITYS " +
                        "VALUES ('" + id + "', '" + name + "', '" + price + "', '" + num + "')";
                this.statement.executeUpdate(insertSyn);
            }

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void deleteData() {
        String deleteSyn = "DELETE FROM COMMODITYS";
        try {
            this.statement.executeUpdate(deleteSyn);
            System.out.println("delete all data");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Vector<Vector<String>> queryAllData() {
        String querySyn = "SELECT id, name, price, num FROM COMMODITYS";

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

    public Vector<String> queryOnceData(String barcodeNum) {
        String querySyn = "SELECT id, name, price, num FROM COMMODITYS WHERE id LIKE '" + barcodeNum + "'";
        Vector<String> queryData = new Vector<String>();
        try {
            ResultSet rs = this.statement.executeQuery(querySyn);
            if (!rs.next())
                return null;
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

    public void updateData(String barcodeNum, int shipmentCount) {
        Vector<String> data = queryOnceData(barcodeNum);
        int num = Integer.valueOf(data.get(3)) - shipmentCount;
        String updateSyn = "UPDATE COMMODITYS SET num='" + num + "' WHERE id='" + barcodeNum + "'";
        try {
            this.statement.executeUpdate(updateSyn);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void disconnected() {
        if (this.statement != null || this.connection != null) {
            try {
                this.connection.close();
                System.out.println("database is closed");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
