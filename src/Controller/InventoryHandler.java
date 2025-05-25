package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.print.Doc;
import javax.swing.event.DocumentEvent;

import Model.Data;
import Model.InventoryData;
import View.InventoryPanel;
import View.ViewPanel;

public class InventoryHandler extends Handler{
    private InventoryData inventoryData;
    private InventoryPanel inventoryPanel;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.inventoryPanel.getDeleteButton()) {
            inventoryData.deleteTable();
            inventoryPanel.updateUI();
        }
    }

    @Override
    public void bindingToModel(Data dataModel) {
        this.inventoryData = (InventoryData) dataModel;
    }

    @Override
    public void bindingToView(ViewPanel viewPanel) {
        this.inventoryPanel = (InventoryPanel) viewPanel;
    }

    public void triggerQueryDataAndUpdateUI(DocumentEvent e) {
        if (e.getDocument() == this.inventoryPanel.getInventorySearchInput().getDocument()) {
            String searchText = this.inventoryPanel.getInventorySearchInput().getText();
            this.inventoryData.queryData(searchText);
            this.inventoryPanel.updateUI();
        }
    } 

    @Override
    public void insertUpdate(DocumentEvent e) {
        triggerQueryDataAndUpdateUI(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        triggerQueryDataAndUpdateUI(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}
}
