package Model;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class SaleHistoryData {
    private static SaleHistoryData instance;
    private DefaultTableModel data;
    private SaleHistoryDB saleHistoryDB;
    
    private SaleHistoryData(){
        saleHistoryDB = SaleHistoryDB.getInstance();
        System.out.println("create SaleHistory table");
    }

    public SaleHistoryData getInstance(){
        if (instance == null) {
            instance = new SaleHistoryData();
        }
        return instance;
    }

    public void insertData(Vector<Vector<String>> list){
        for (Vector<String> row : list) {
            saleHistoryDB.insertData(row);   
        }
    }

}
