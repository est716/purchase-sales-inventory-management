package Model;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class RestorkData extends Data {
    private DefaultTableModel data;

    public RestorkData() {
        this.data = new DefaultTableModel();
        this.data.setColumnIdentifiers(this.columnName);
    }

    public DefaultTableModel getData() {
        return data;
    }

    public void insertData(String barcodeString) {
        if (!isExistBarcodeNum(barcodeString)) {
            Vector<String> vector = new Vector<String>(4);
            vector.add(barcodeString);
            vector.add("");
            vector.add("");
            vector.add("1");
            this.data.addRow(vector);
        }
    }

    public void clearData() {
        for (int i = this.data.getRowCount() - 1; i > -1; i--) {
            this.data.removeRow(i);
        }
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
