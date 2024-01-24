package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

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
                shipmentData.insertData(barcodeString);
                shipmentPanel.getShipmentInput().setText("");
                shipmentPanel.setShipmentLabelCountingSum(
                    shipmentData.getTotalPrice()
                );
                shipmentPanel.updateUI();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.shipmentData != null && this.shipmentPanel != null
                && this.shipmentPanel.getShipmentButton() == e.getSource()) {
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
