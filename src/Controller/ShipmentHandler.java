package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Model.Data;
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
                boolean isExist = shipmentData.insertData(barcodeString);
                shipmentPanel.getShipmentInput().setText("");
                if (isExist) {
                    shipmentPanel.setShipmentLabelCountingSum(
                    shipmentData.getTotalPrice()
                    );
                    shipmentPanel.updateUI();
                }else{
                    JOptionPane.showMessageDialog(shipmentPanel, "此物品不存在", "警告", JOptionPane.ERROR_MESSAGE);
                }
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
                    dfm.getValueAt(i, 0);
                    dfm.getValueAt(i, 3);
                }
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
