package Model;

import java.util.Vector;

import org.jfree.data.category.DefaultCategoryDataset;

public class SaleHistoryData {
    private static SaleHistoryData instance;
    DefaultCategoryDataset dataset;
    private SaleHistoryDB saleHistoryDB;

    private SaleHistoryData() {
        saleHistoryDB = SaleHistoryDB.getInstance();
        this.dataset = new DefaultCategoryDataset();
        queryData();
    }

    public static SaleHistoryData getInstance() {
        if (instance == null) {
            instance = new SaleHistoryData();
        }
        return instance;
    }

    public void insertData(String id, String num) {
        Vector<String> row = new Vector<>();
        row.add(id);
        row.add(num);
        saleHistoryDB.insertData(row);
        queryData();// update UI data
    }

    public void queryData() {
        Vector<Vector<String>> list = this.saleHistoryDB.queryData();
        this.dataset.clear();
        for (Vector<String> row : list) {
            this.dataset.addValue(Integer.valueOf(row.get(2)), "商品數量", row.get(1));
        }
    }

    public void dropTable(){
        this.saleHistoryDB.dropTable();
    }

    public DefaultCategoryDataset getDataSet() {
        return this.dataset;
    }

}
