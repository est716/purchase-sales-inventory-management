package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Model.Data;
import Model.InventoryData;
import Model.ShipmentData;
import View.ShipmentPanel;
import View.ViewPanel;

public class ShipmentHandler extends Handler {

    private ShipmentPanel shipmentPanel = null;
    private ShipmentData shipmentData = null;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (this.shipmentData != null && this.shipmentPanel != null) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                String barcodeString = shipmentPanel.getShipmentInput().getText();
                boolean isExist = this.shipmentData.insertData(barcodeString);
                shipmentPanel.getShipmentInput().setText("");
                
                int inventoryCount = 0;
                int nowBarcodeProductCount = 0;

                // 將特定商品的現有庫存轉成Int
                if (isExist)
                    inventoryCount = Integer.parseInt(InventoryData.getInstance().queryOnceData(barcodeString).get(3));
                // 將現在所輸入之商品的數量取回
                Vector<String> row = this.shipmentData.getOnceData(barcodeString);
                if (!row.isEmpty())
                    nowBarcodeProductCount = Integer.parseInt(row.get(3));
                
                boolean isNotNull = nowBarcodeProductCount <= inventoryCount;

                if (isExist && isNotNull) {
                    shipmentPanel.setShipmentLabelCountingSum(shipmentData.getTotalPrice());
                }else{
                    // 修改barcode對應的價格欄位
                    this.shipmentData.modifyOnecData(String.valueOf(inventoryCount), barcodeString, 3);
                    JOptionPane.showMessageDialog(shipmentPanel, "此物品不存在或商品不足", "警告", JOptionPane.ERROR_MESSAGE);
                }
                shipmentPanel.updateUI();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    //當售出按鈕被觸發時，將會進行資料更新
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.shipmentData != null && this.shipmentPanel != null
                && this.shipmentPanel.getShipmentButton() == e.getSource()) {
                DefaultTableModel dfm = shipmentData.getData();
                for(int i = 0; i < dfm.getRowCount(); i++){
                    String id = (String) dfm.getValueAt(i, 0);
                    String num = (String) dfm.getValueAt(i, 3);
                    //triggerEvent
                    InventoryData.getInstance().updateNewData(id, num);
                }
                shipmentData.clearData();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void bindingToModel(Data dataModel) {
        if (dataModel instanceof ShipmentData) {
            this.shipmentData = (ShipmentData) dataModel;
        }
    }

    @Override
    public void bindingToView(ViewPanel viewPanel) {
        if (viewPanel instanceof ShipmentPanel) {
            this.shipmentPanel = (ShipmentPanel) viewPanel;
        }

    }

    @Override
    public Vector<Vector<String>> getData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getData'");
    }

    @Override
    public Vector<String> getColumnName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getColumnName'");
    }

    @Override
    protected void isNonNumberAndClearTextView(char c) {
    }
}
