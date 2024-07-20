package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

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
        if (this.restorkPanel != null && this.restorkData != null) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                String barcode = this.restorkPanel.getRestorkInputText();
                if (!barcode.equals("")) {
                    this.restorkData.insertData(barcode);
                    this.restorkPanel.updateUI();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.restorkPanel != null && this.restorkData != null) {
            if (e.getSource() == this.restorkPanel.getIntoButton()) {
                InventoryData.getInstance().insertData(this.restorkPanel.getRestorkList());
                this.restorkData.clearData();
                this.restorkPanel.updateUI();
                System.out.println("test1");
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
    public Vector<Vector<String>> getData() {
        return this.restorkPanel.getRestorkList();
    }

    @Override
    public Vector<String> getColumnName() {
        return null;
    }

    @Override
    protected void isNonNumberAndClearTextView(char c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isNonNumberAndClearTextView'");
    }

    @Override
    public void bindingToModel(Data dataModel) {
        this.restorkData = (RestorkData) dataModel;
    }

    @Override
    public void bindingToView(ViewPanel viewPanel) {
        this.restorkPanel = (RestorkPanel) viewPanel;
    }

}
