package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.event.DocumentEvent;

import Model.Data;
import Model.InventoryData;
import View.InventoryPanel;
import View.ViewPanel;

public class InventoryHandler extends Handler{
    private InventoryData inventoryData;
    private InventoryPanel inventoryPanel;
    private Timer debounceTimer = null;
    private static final int DEBOUNCE_TIME = 300; // milliseconds

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
        if (this.debounceTimer != null) {
            this.debounceTimer.cancel();
        }
        this.debounceTimer = new Timer();
        this.debounceTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (e.getDocument() == inventoryPanel.getInventorySearchInput().getDocument()) {
                    String searchText = inventoryPanel.getInventorySearchInput().getText();
                    inventoryData.queryData(searchText);
                    inventoryPanel.updateUI();
                }
            }
        }, DEBOUNCE_TIME);
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
