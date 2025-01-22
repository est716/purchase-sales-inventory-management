package Model;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class ShipmentData extends Data {
    private DefaultTableModel data;

    public ShipmentData() {
        this.data = new DefaultTableModel();
        this.data.setColumnIdentifiers(this.columnName);
    }

    public DefaultTableModel getData() {
        return data;
    }

    /**
     * The function is insert data to the DefaultTableModel
     * 
     * @param barcode is String
     */
    public boolean insertData(String barcode) {
        if (!isExistBarcodeNum(barcode)) {
            Vector<String> row = InventoryData.getInstance().queryOnceData(barcode);
            if (!row.isEmpty()) {
                row.setElementAt("1", 3);
                this.data.addRow(row);
            }else{
                return false;
            }
        }
        return true;
    }

    public String getTotalPrice(){
        int total = 0;
        for (int i = 0; i < this.data.getRowCount(); i++) {
            int price = Integer.parseInt(this.data.getValueAt(i, 2).toString()) ;
            int num = Integer.parseInt(this.data.getValueAt(i, 3).toString());
            total += price * num;
        }
        return String.valueOf(total);
    }

    public void clearData() {
        for (int i = this.data.getRowCount() - 1; i > -1; i--) {
            this.data.removeRow(i);
        }
    }

    public Vector<String> getOnceData(String barcodeString){
        int i = 0;
        Vector<String> row = new Vector<String>();
        while (this.data.getRowCount() != 0 && i < this.data.getRowCount()) {
            if (this.data.getValueAt(i, 0).toString().equals(barcodeString)) {
                row.add(this.data.getValueAt(i, 0).toString());
                row.add(this.data.getValueAt(i, 1).toString());
                row.add(this.data.getValueAt(i, 2).toString());
                row.add(this.data.getValueAt(i, 3).toString());
                break;
            }
            ++i;
        }
        return row;
    }

    private boolean isExistBarcodeNum(String barcode) {
        for (int i = 0; i < this.data.getRowCount(); i++) {
            if (this.data.getValueAt(i, 0).equals(barcode)) {
                String numStr = this.data.getValueAt(i, 3).toString();
                numStr = String.valueOf(Integer.parseInt(numStr) + 1);
                this.data.setValueAt(numStr, i, 3);
                return true;
            }
        }
        return false;
    }
}
