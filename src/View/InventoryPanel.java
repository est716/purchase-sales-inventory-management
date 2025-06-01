package View;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Controller.Handler;

import java.awt.BorderLayout;

public class InventoryPanel extends ViewPanel {

    private JScrollPane inventoryJSP;
    private JTable inventoryTable;
    private JTextField inventorySearchInput;
    private JButton deleteButton;
    private TableRowSorter<DefaultTableModel> sorter;

    public InventoryPanel() {

        // set this panel layout
        this.setLayout(new BorderLayout());

        this.inventorySearchInput = new JTextField();
        this.inventorySearchInput.setFont(font);
        this.inventoryTable = new JTable();
        this.inventoryTable.setFont(font);
        this.inventoryTable.getTableHeader().setFont(font);
        this.inventoryTable.getTableHeader().setReorderingAllowed(false);
        this.inventoryTable.setRowHeight(font.getSize() + 5);
        this.sorter = new TableRowSorter<>();
        this.inventoryTable.setRowSorter(this.sorter);
        this.inventoryJSP = new JScrollPane(this.inventoryTable);
        this.deleteButton = new JButton("delete table");
        this.add(this.inventoryJSP, BorderLayout.CENTER);
        this.add(this.inventorySearchInput, BorderLayout.SOUTH);
        // this.add(this.deleteButton, BorderLayout.EAST);
    }

    public void setTableModel(DefaultTableModel defaultTableModel) {
        this.inventoryTable.setModel(defaultTableModel);
        this.sorter.setModel(defaultTableModel);
        this.inventoryTable.updateUI();
    }

    public JTable getInventoryTable() {
        return inventoryTable;
    }

    public JTextField getInventorySearchInput() {
        return inventorySearchInput;
    }

    public void addListener(Handler handler) {
        this.inventorySearchInput.addKeyListener(handler);
        this.deleteButton.addActionListener(handler);
        this.inventorySearchInput.getDocument().addDocumentListener(handler);
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void updateUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                revalidate();
                repaint();
            }
        });
    }
}
