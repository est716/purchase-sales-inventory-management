package Model;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class RestorkData extends Data {
    private DefaultTableModel data;

    public RestorkData() {
        this.data = constructTableModel();
        this.columnName.add("刪除");
        this.data.setColumnIdentifiers(this.columnName);
    }

    private DefaultTableModel constructTableModel(){
        return new DefaultTableModel(){

            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
            
        };
    }

    public DefaultTableModel getData() {
        return data;
    }

    public void insertData(String barcodeString) {
        Vector<String> result = InventoryData.getInstance().queryOnceData(barcodeString);
        Vector<String> vector = new Vector<String>(4);
        vector.setSize(4);
        if (!result.isEmpty()) {
            vector.set(1, result.get(1));
            vector.set(2, result.get(2));
        }

        if (!isExistBarcodeNum(barcodeString)) {
            vector.set(0, barcodeString);
            vector.set(3, "1");
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
