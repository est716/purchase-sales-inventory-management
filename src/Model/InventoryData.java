/**
 * 這個類別是一個single instance class
 * 
 * 用於讀取DataBase內容
 * 
 */
package Model;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;


public class InventoryData extends Data {
    private static InventoryData instance;
    private DefaultTableModel data;
    private static InventoryDB inventoryDB;
    static {
        // System.out.println("cidb is create");
        inventoryDB = new InventoryDB();
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                inventoryDB.disconnected();
            }

        });
    }

    private InventoryData() {

    }

    public DefaultTableModel getData() {
        return instance.data;
    }

    // I used setDataVector update data table model
    private static void updateDataTableModel() {
        instance.data.setDataVector(inventoryDB.queryAllData(), instance.columnName);
    }
    /**
     * it provide function for you can insert list to DB
     * @param list is a Vector<Vector<String>> type
     */
    public static void insertData(Vector<Vector<String>> list) {
        for (int i = 0; i < list.size(); i++) {
            InventoryData.inventoryDB.insertData(list.elementAt(i));
        }
        updateDataTableModel();
    }

    public static void updateNewData(String id, String num){
        InventoryData.inventoryDB.updateDataNum(id, num);
        updateDataTableModel();
    }

    public static Vector<String> queryOnceData(String barcode){
        return InventoryData.inventoryDB.queryOnceData(barcode);
    }

    /**
     * check this class whether have constructed, 
     * if have constructed then return origin instance
     * else then construct a instance and return it
     * @return return an instance, its unique
     */
    public static InventoryData getInstance() {
        if (instance == null) {
            instance = new InventoryData();
            instance.data = new DefaultTableModel(inventoryDB.queryAllData(), instance.columnName);
        }
        return instance;
    }

    public void deleteTable(){
        inventoryDB.deleteTable();
        updateDataTableModel();
    }
}
