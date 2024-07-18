package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import Model.Data;
import Model.InventoryData;
import View.InventoryPanel;
import View.ViewPanel;

public class InventoryHandler extends Handler {
    private InventoryData inventoryData;
    private InventoryPanel inventoryPanel;

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.inventoryPanel.getDeleteButton()) {
            inventoryData.deleteTable();
            inventoryPanel.updateUI();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

    @Override
    public void bindingToModel(Data dataModel) {
        this.inventoryData = (InventoryData) dataModel;
    }

    @Override
    public void bindingToView(ViewPanel viewPanel) {
        this.inventoryPanel = (InventoryPanel) viewPanel;
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isNonNumberAndClearTextView'");
    }

}
