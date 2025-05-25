package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;

import Model.Data;
import Model.InventoryData;
import Model.RestorkData;
import View.RestorkPanel;
import View.ViewPanel;

public class RestorkHandler extends Handler {
    private RestorkPanel restorkPanel = null;
    private RestorkData restorkData = null;

    public RestorkHandler() {

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // determine key is enter, and panel and data is not null
        if (!isDigitOrEnter(e)) {
            JOptionPane.showMessageDialog(restorkPanel, "請使用數字", "警告", JOptionPane.ERROR_MESSAGE);
            this.restorkPanel.getRestorkInput().setText("");
            return;
        }
        if (isEnterAndNotNull(e)) {
            String barcode = this.restorkPanel.getRestorkInputText();
            if (!barcode.equals("")) {
                this.restorkData.insertData(barcode);
                this.restorkPanel.updateUI();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        triggerIntoButton(e);
        triggerDeleteRowButton(e);
    }

    private void triggerDeleteRowButton(ActionEvent e) {
        if (isNotNull() && e.getSource() == this.restorkPanel.getDeleteRowButton()) {
            DefaultTableModel d = this.restorkData.getData();
            int size = d.getRowCount();
            for (int i = size - 1; i >= 0; i--) {
                Boolean isSelected = (Boolean) d.getValueAt(i, 4);
                if (isSelected != null && isSelected == true) {
                    d.removeRow(i);
                }
            }
        }
    }

    private void triggerIntoButton(ActionEvent e) {
        // isNotNull funtion that determine variable of panel and data is not null
        if (isNotNull() && e.getSource() == this.restorkPanel.getIntoButton()) {
            Vector<Vector<String>> list = this.restorkPanel.getRestorkList();
            if (isCellEmpty(list)) {
                InventoryData.getInstance().insertData(list);
                this.restorkData.clearData();
                this.restorkPanel.updateUI();
                System.out.println("test1");
            } else {
                JOptionPane.showMessageDialog(this.restorkPanel, "名稱或價格為空", "警告", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void bindingToModel(Data dataModel) {
        this.restorkData = (RestorkData) dataModel;
    }

    @Override
    public void bindingToView(ViewPanel viewPanel) {
        this.restorkPanel = (RestorkPanel) viewPanel;
    }

    private boolean isNotNull() {
        return this.restorkPanel != null && this.restorkData != null;
    }

    private boolean isEnterAndNotNull(KeyEvent e) {
        return isNotNull() && e.getKeyCode() == KeyEvent.VK_ENTER;
    }

    private boolean isCellEmpty(Vector<Vector<String>> list) { // determind cell of index 2 and 3 is empty
        for (Vector<String> vector : list) {
            if (vector.get(1) == null || vector.get(2) == null) {
                return false;
            }
        }
        return true;
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
