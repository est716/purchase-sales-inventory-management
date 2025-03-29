package View;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Controller.Handler;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

public class RestorkPanel extends ViewPanel {
    private JPanel restorkSouthPanel;
    private JButton into;
    private JTextField restorkInput;
    private JTable restorkTable;
    private JScrollPane restorkJSP;

    public RestorkPanel() {
        // initialize layout
        this.setLayout(new BorderLayout());

        // initialize componet of panel
        this.restorkSouthPanel = new JPanel();
        this.into = new JButton("確認輸入");
        this.restorkInput = new JTextField();
        this.restorkInput.setFont(font);
        this.into.setFont(font);
        this.restorkTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        

        this.restorkTable.setFont(font);
        this.restorkTable.getTableHeader().setFont(font);
        this.restorkTable.setRowHeight(font.getSize() + 5);
        this.restorkJSP = new JScrollPane(this.restorkTable);

        // component configure
        this.restorkSouthPanel.setLayout(new GridBagLayout());
        GridBagConstraints restorkInputConfig = new GridBagConstraints(0, 0, 6, 1, 10, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
        GridBagConstraints restorkIntoConfig = new GridBagConstraints(0, 1, 6, 1, 10, 1, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);

        this.restorkSouthPanel.add(this.restorkInput, restorkInputConfig);
        this.restorkSouthPanel.add(this.into, restorkIntoConfig);
        this.add(this.restorkJSP, BorderLayout.CENTER);
        this.add(this.restorkSouthPanel, BorderLayout.SOUTH);

    }

    public void setTableModel(DefaultTableModel defaultTableModel) {
        this.restorkTable.setModel(defaultTableModel);
        this.restorkTable.updateUI();
    }

    public Vector<Vector<String>> getRestorkList() {
        Vector<Vector<String>> list = new Vector<Vector<String>>();
        for (int i = 0; i < this.restorkTable.getRowCount(); i++) {
            Vector<String> row = new Vector<String>();
            row.add((String) this.restorkTable.getValueAt(i, 0));
            row.add((String) this.restorkTable.getValueAt(i, 1));
            row.add((String) this.restorkTable.getValueAt(i, 2));
            row.add((String) this.restorkTable.getValueAt(i, 3));
            list.add(row);
        }
        return list;
    }

    public JButton getIntoButton() {
        return into;
    }

    public String getRestorkInputText() {
        String textString = this.restorkInput.getText();
        this.restorkInput.setText("");
        return textString;
    }

    public void addListener(Handler handler) {
        this.restorkInput.addKeyListener(handler);
        this.into.addActionListener(handler);
    }

    public JTextField getRestorkInput() {
        return restorkInput;
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
