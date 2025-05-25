package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;

import Model.Data;
import Model.InventoryData;
import Model.ShipmentData;
import View.ShipmentPanel;
import View.ViewPanel;

public class ShipmentHandler extends Handler {

    private ShipmentPanel shipmentPanel = null;
    private ShipmentData shipmentData = null;
    private SaleHistoryHandler saleHistoryHandler = null;
    private final boolean BARCODE_MODEL = false;
    private final boolean PAY_MODEL = true;
    private boolean modelState = BARCODE_MODEL;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (this.shipmentData != null && this.shipmentPanel != null) {
            if (!isDigitOrEnter(e) && e.getKeyCode() != KeyEvent.VK_DECIMAL) {
                JOptionPane.showMessageDialog(shipmentPanel, "請使用數字", "警告", JOptionPane.ERROR_MESSAGE);
                this.shipmentPanel.getShipmentInput().setText("");
                return;
            }
            barcodeAction(e);
            changeModelState(e);
            this.shipmentPanel.updateUI();
        }
    }

    private void barcodeAction(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && this.modelState == this.BARCODE_MODEL) {
            String barcodeString = shipmentPanel.getShipmentInput().getText();
            boolean isSuccess = this.shipmentData.insertData(barcodeString);
            this.shipmentPanel.shipmentInputClear();

            int inventoryCount = 0;
            int nowBarcodeProductCount = 0;

            // 將特定商品的現有庫存轉成Int
            if (isSuccess)
                inventoryCount = Integer.parseInt(InventoryData.getInstance().queryOnceData(barcodeString).get(3));

            // 將現在所輸入之商品的數量取回
            Vector<String> row = null;
            if (isSuccess)
                row = this.shipmentData.getOnceData(barcodeString);

            if (row != null && !row.isEmpty())
                nowBarcodeProductCount = Integer.parseInt(row.get(3));

            boolean isNotNull = isSuccess && nowBarcodeProductCount <= inventoryCount;

            if (isNotNull) {
                shipmentPanel.setShipmentLabelCountingSum(shipmentData.getTotalPrice());
            } else {
                // 修改barcode對應的價格欄位
                if (isSuccess)
                    this.shipmentData.modifyOnecData(String.valueOf(inventoryCount), barcodeString, 3);
                JOptionPane.showMessageDialog(shipmentPanel, "此物品不存在或商品不足", "警告", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void changeModelState(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DECIMAL || e.getKeyCode() == KeyEvent.VK_PERIOD) {
            this.modelState = this.PAY_MODEL;
            this.shipmentPanel.getShipmentButton().setEnabled(this.PAY_MODEL);
            this.shipmentPanel.shipmentInputClear();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    // 當售出按鈕被觸發時，將會進行資料更新
    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.shipmentData != null && this.shipmentPanel != null
                && this.shipmentPanel.getShipmentButton() == e.getSource()) {

            if (this.shipmentPanel.getShipmentInput().getText().equals("")) {
                JOptionPane.showMessageDialog(shipmentPanel, "請輸入收取金額", "警告", JOptionPane.ERROR_MESSAGE);
            } else {
                countingCash();

                DefaultTableModel dfm = shipmentData.getData();
                for (int i = 0; i < dfm.getRowCount(); i++) {
                    String id = (String) dfm.getValueAt(i, 0);
                    String num = (String) dfm.getValueAt(i, 3);
                    // triggerEvent
                    InventoryData.getInstance().updateNewData(id, num);
                    this.saleHistoryHandler.insertData(id, num);
                }

                shipmentData.clearData();
                this.modelState = this.BARCODE_MODEL;
                // shipment button is disable in barcode model
                this.shipmentPanel.getShipmentButton().setEnabled(this.modelState);
                this.shipmentPanel.shipmentInputClear();
                this.saleHistoryHandler.notification();
            }

        }
        removeTableRows(e);
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

    public void bindingToSaleHistory(SaleHistoryHandler saleHistoryHandler) {
        this.saleHistoryHandler = saleHistoryHandler;
    }

    private void removeTableRows(ActionEvent e) {
        if (this.shipmentData != null && this.shipmentPanel != null
                && this.shipmentPanel.getDeleteRowButton() == e.getSource()) {
            DefaultTableModel d = this.shipmentData.getData();
            int size = d.getRowCount();
            for (int i = size - 1; i >= 0; i--) {
                Boolean isSelected = (Boolean) d.getValueAt(i, 4);
                if (isSelected != null && isSelected == true) {
                    d.removeRow(i);
                }
            }
            shipmentPanel.setShipmentLabelCountingSum(shipmentData.getTotalPrice());
        }
    }

    private void countingCash() {
        // 計算找零
        int sum = Integer.valueOf(this.shipmentData.getTotalPrice());
        int chargeMoney = Integer.valueOf(this.shipmentPanel.getShipmentInput().getText());
        int changeMoney = chargeMoney - sum;
        this.shipmentPanel.setChargeMoneyLabelText(String.valueOf(chargeMoney));
        this.shipmentPanel.setChangeMoneyLabelText(String.valueOf(changeMoney));
        this.shipmentPanel.updateUI();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertUpdate'");
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeUpdate'");
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changedUpdate'");
    }

}
