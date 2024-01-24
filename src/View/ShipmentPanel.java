package View;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Controller.Handler;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class ShipmentPanel extends ViewPanel {
	private JTable shipmentTable;
	private JScrollPane shipmentJSP;
	private JTextField shipmentInput;
	private JButton shipmentButton;
	private JLabel shipmentLabelCountingSum;
	private JPanel shipmentSouthPanel;
	private final String TOTAL_MONEY = "總金額：";

	public ShipmentPanel() {
		// initialize layout
		this.setLayout(new BorderLayout());

		// initialize componet of panel

		this.shipmentTable = new JTable();
		this.shipmentTable.setFont(font);
        this.shipmentTable.getTableHeader().setFont(font);
        this.shipmentTable.setRowHeight(font.getSize()+5);
		this.shipmentJSP = new JScrollPane(this.shipmentTable);
		this.shipmentSouthPanel = new JPanel();
		this.shipmentInput = new JTextField();
		this.shipmentInput.setFont(new Font("標楷體", Font.PLAIN, 25));
		this.shipmentLabelCountingSum = new JLabel(TOTAL_MONEY, JLabel.LEFT);
		this.shipmentLabelCountingSum.setFont(new Font("標楷體", Font.PLAIN, 25));
		this.shipmentButton = new JButton("售出");// shipment
		this.shipmentButton.setFont(new Font("標楷體", Font.PLAIN, 25));
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
		this.shipmentSouthPanel.add(this.shipmentLabelCountingSum, shipmentSumConfig);
		this.shipmentSouthPanel.add(this.shipmentButton, shipmentButtonConfig);

		this.add(this.shipmentJSP, BorderLayout.CENTER);
		this.add(this.shipmentSouthPanel, BorderLayout.SOUTH);

	}

	public JTextField getShipmentInput() {
		return shipmentInput;
	}

	public JButton getShipmentButton() {
		return shipmentButton;
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
	}

	public void updateUI(){
        revalidate();
        repaint();
    }
}
