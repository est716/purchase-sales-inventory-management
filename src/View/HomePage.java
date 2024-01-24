package View;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HomePage extends JFrame {
    // mainly tab
    private JTabbedPane mainTab;
    private RestorkPanel restorkPanel;
    private ShipmentPanel shipmentPanel;
    private InventoryPanel inventoryPanel;

    public HomePage() {
        // set BorderLayout to HomePage Frame
        this.setLayout(new BorderLayout());

        // initialize mainTab
        this.mainTab = new JTabbedPane();
        this.restorkPanel = new RestorkPanel();
        this.shipmentPanel = new ShipmentPanel();
        this.inventoryPanel = new InventoryPanel();

        // Panel add to mainTab
        this.mainTab.add("出貨", this.shipmentPanel); // index 0
        this.mainTab.add("入貨", this.restorkPanel); // index 1
        this.mainTab.add("庫存", this.inventoryPanel); // index 2

        this.mainTab.setFont(new Font("標楷體", Font.PLAIN, 25));

        // add mainTab to JFrame
        this.add(this.mainTab, BorderLayout.CENTER);

        this.mainTab.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() == mainTab) {
                    System.out.println(mainTab.getSelectedIndex());
                    switch (mainTab.getSelectedIndex()) {
                        case 0:
                            shipmentPanel.getShipmentInput().requestFocusInWindow();
                            break;

                        case 1:
                            restorkPanel.getRestorkInput().requestFocusInWindow();
                            break;

                        case 2:
                            inventoryPanel.updateUI();
                            inventoryPanel.getInventorySearchInput().requestFocusInWindow();
                            break;
                    }
                }
            }

        });

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {
                shipmentPanel.getShipmentInput().requestFocusInWindow();
            }
            
        });

    }

    public RestorkPanel getRestorkPanel() {
        return restorkPanel;
    }

    public ShipmentPanel getShipmentPanel() {
        return shipmentPanel;
    }

    public InventoryPanel getInventoryPanel() {
        return inventoryPanel;
    }

}
