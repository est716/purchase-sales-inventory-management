package Model;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class ShipmentData extends Data {
    private DefaultTableModel data;
    private InventoryData inventoryData = InventoryData.getInstance();

    public ShipmentData() {
        this.data = constructTableModel();
        this.columnName.add("刪除");
        this.data.setColumnIdentifiers(this.columnName);
    }

    public DefaultTableModel getData() {
        return data;
    }

    private DefaultTableModel constructTableModel(){
        return new DefaultTableModel(){

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
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

    /**
     * The function is insert data to the DefaultTableModel
     * 
     * @param barcode is String
     */
    public boolean insertData(String barcode) {
        int index = getIndexOfBarcodeNumInTable(barcode);
        if (index != -1){
            changeValueOfNumLabelOfRowData(index);
            return true;
        }
        return addDataFromInventoryToShipmentTable(barcode);
    }

    private boolean addDataFromInventoryToShipmentTable(String barcode) {
        Vector<String> row = this.inventoryData.queryOnceData(barcode);
        if (row.isEmpty())
            return false;
        row.setElementAt("1", 3);
        this.data.addRow(row);
        return true;
    }

    public String getTotalPrice() {
        int total = 0;
        for (int i = 0; i < this.data.getRowCount(); i++) {
            int price = Integer.parseInt(this.data.getValueAt(i, 2).toString());
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

    public Vector<String> getOnceData(String barcodeString) {
        Vector<String> row = new Vector<String>();
        int i = findRow(barcodeString);
        row.add(this.data.getValueAt(i, 0).toString());
        row.add(this.data.getValueAt(i, 1).toString());
        row.add(this.data.getValueAt(i, 2).toString());
        row.add(this.data.getValueAt(i, 3).toString());
        return row;
    }

    public void modifyOnecData(String content, String barcodeString, int column) {
        int row = findRow(barcodeString);
        this.data.setValueAt(content, row, column);
        if (this.data.getValueAt(row, column).toString().equals("0")) {
            this.data.removeRow(row);
        }
    }

    private int findRow(String barcodeString) {
        int i = 0;
        while (this.data.getRowCount() != 0 && i < this.data.getRowCount()) {
            if (this.data.getValueAt(i, 0).toString().equals(barcodeString)) {
                break;
            }
            ++i;
        }
        return i;
    }

    private int getIndexOfBarcodeNumInTable(String barcode) {
        for (int i = 0; i < this.data.getRowCount(); i++) {
            if (this.data.getValueAt(i, 0).equals(barcode)) {
                return i;
            }
        }
        return -1;
    }

    private void changeValueOfNumLabelOfRowData(int index) {
        String numStr = this.data.getValueAt(index, 3).toString();
        numStr = String.valueOf(Integer.parseInt(numStr) + 1);
        this.data.setValueAt(numStr, index, 3);
    }
}
