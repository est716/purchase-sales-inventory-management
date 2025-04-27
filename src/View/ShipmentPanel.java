package View;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import Controller.Handler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.MessageFormat;

public class ShipmentPanel extends ViewPanel {
    private JTable shipmentTable;

    private JScrollPane shipmentJSP;

    private JTextField shipmentInput;

    private JButton shipmentButton;
    private JButton deleteRowButton;

    private JLabel shipmentLabelCountingSum;
    private JLabel chargeMoneyLabel;
    private JLabel changeMoneyLabel;

    private JPanel shipmentNorthPanel;
    private JPanel shipmentSouthPanel;
    private JPanel labelPanel;

    private final String TOTAL_MONEY = "總金額：";
    private final String CHARGE_MONEY = "收 {0} 元";
    private final String CHANGE_MONEY = "找 {0} 元";

    public ShipmentPanel() {
        // initialize layout
        this.setLayout(new BorderLayout());

        // initialize componet of panel
        this.shipmentTable = new JTable();
        this.shipmentTable.setFont(font);
        this.shipmentTable.getTableHeader().setFont(font);
        this.shipmentTable.setRowHeight(font.getSize()+5);
        this.shipmentTable.getTableHeader().setReorderingAllowed(false);

        this.shipmentJSP = new JScrollPane(this.shipmentTable);

        this.shipmentSouthPanel = new JPanel();

        this.shipmentInput = new JTextField();
        this.shipmentInput.setFont(font);

        this.shipmentLabelCountingSum = new JLabel(TOTAL_MONEY, JLabel.LEFT);
        this.shipmentLabelCountingSum.setFont(font);

        this.chargeMoneyLabel = new JLabel(MessageFormat.format(CHARGE_MONEY, "0"));
        this.chargeMoneyLabel.setFont(font);

        this.changeMoneyLabel = new JLabel(MessageFormat.format(CHANGE_MONEY, "0"));
        this.changeMoneyLabel.setFont(font);

        this.labelPanel = new JPanel();
        this.labelPanel.setLayout(new BoxLayout(this.labelPanel, BoxLayout.Y_AXIS));
        this.labelPanel.add(this.shipmentLabelCountingSum);
        this.labelPanel.add(this.chargeMoneyLabel);
        this.labelPanel.add(this.changeMoneyLabel);

        this.shipmentButton = new JButton("售出");// shipment
        this.shipmentButton.setFont(font);
        this.shipmentButton.setEnabled(false);

        createNorthPanel();

        // set shipment South Panel layout and its componet position
        this.shipmentSouthPanel.setLayout(new GridBagLayout());
        GridBagConstraints shipmentInputConfig = new GridBagConstraints(6, 0, 5, 1, 10, 1,
                GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
        GridBagConstraints shipmentSumConfig = new GridBagConstraints(0, 0, 1, 1, 5, 1, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);
        GridBagConstraints shipmentButtonConfig = new GridBagConstraints(12, 0, 2, 1, 10, 1,
                GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0);

        // add componet to the South panel
        this.shipmentSouthPanel.add(this.shipmentInput, shipmentInputConfig);
        this.shipmentSouthPanel.add(this.labelPanel, shipmentSumConfig);
        this.shipmentSouthPanel.add(this.shipmentButton, shipmentButtonConfig);

        this.add(this.shipmentJSP, BorderLayout.CENTER);
        this.add(this.shipmentSouthPanel, BorderLayout.SOUTH);
        this.add(this.shipmentNorthPanel, BorderLayout.NORTH);

    }

    public JTextField getShipmentInput() {
        return shipmentInput;
    }

    public JButton getShipmentButton() {
        return shipmentButton;
    }

    public JButton getDeleteRowButton() {
        return deleteRowButton;
    }

    public JTable getShipmentTable() {
        return shipmentTable;
    }

    public void setTableModel(DefaultTableModel defaultTableModel) {
        this.shipmentTable.setModel(defaultTableModel);
        this.shipmentTable.updateUI();
    }

    public void setShipmentLabelCountingSum(String str) {
        this.shipmentLabelCountingSum.setText(TOTAL_MONEY + str);
    }

    public void addListener(Handler handler) {
        this.shipmentInput.addKeyListener(handler);
        this.shipmentButton.addActionListener(handler);
        this.shipmentTable.addMouseListener(handler);
        this.deleteRowButton.addActionListener(handler);
    }

    public void setChargeMoneyLabelText(String text) {
        this.chargeMoneyLabel.setText(MessageFormat.format(this.CHARGE_MONEY, text));
    }

    public void setChangeMoneyLabelText(String text) {
        this.changeMoneyLabel.setText(MessageFormat.format(this.CHANGE_MONEY, text));
    }

    public void shipmentInputClear(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                shipmentInput.setText("");
            }
        });   
    }

    public void updateUI(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                revalidate();
                repaint();
            }
        });
    }

    private void createNorthPanel(){
        this.shipmentNorthPanel = new JPanel();
        this.shipmentNorthPanel.setLayout(new BoxLayout(this.shipmentNorthPanel, BoxLayout.X_AXIS));
        
        JPanel paddingPanel = new JPanel();
        paddingPanel.setOpaque(false);
        paddingPanel.setPreferredSize(new Dimension((int)(1300 * 0.2), 50));
        
        this.deleteRowButton = new JButton("確認刪除");
        this.deleteRowButton.setFont(font);
        
        this.shipmentNorthPanel.add(paddingPanel);
        this.shipmentNorthPanel.add(this.deleteRowButton);
    
    }

}
