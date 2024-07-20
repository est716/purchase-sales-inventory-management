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
    private InventoryDB inventoryDB;

    private InventoryData() {
        System.out.println("created DB");
        inventoryDB = new InventoryDB();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                inventoryDB.disconnected();
            }
        });

        this.data = new DefaultTableModel(this.inventoryDB.queryAllData(), this.columnName);
    }

    public DefaultTableModel getData() {
        return instance.data;
    }

    // I used setDataVector update data table model
    private void updateDataTableModel() {
        instance.data.setDataVector(inventoryDB.queryAllData(), instance.columnName);
    }
    /**
     * it provide function for you can insert list to DB
     * @param list is a Vector<Vector<String>> type
     */
    public void insertData(Vector<Vector<String>> list) {
        for (int i = 0; i < list.size(); i++) {
            instance.inventoryDB.insertData(list.elementAt(i));
        }
        updateDataTableModel();
    }

    public void updateNewData(String id, String num){
        instance.inventoryDB.updateDataNum(id, num);
        updateDataTableModel();
    }

    public Vector<String> queryOnceData(String barcode){
        return instance.inventoryDB.queryOnceData(barcode);
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
        }
        return instance;
    }

    public void deleteTable(){
        inventoryDB.deleteTable();
        updateDataTableModel();
    }
}
