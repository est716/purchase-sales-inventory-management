

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import Controller.InventoryHandler;
import Controller.RestorkHandler;
import Controller.SaleHistoryHandler;
import Controller.ShipmentHandler;
import Model.InventoryData;
import Model.RestorkData;
import Model.SaleHistoryData;
import Model.ShipmentData;
import View.HomePage;

public class POS {
    public static void main(String[] args) throws Exception {

        InventoryData inventoryData = InventoryData.getInstance();
        RestorkData restorkData = new RestorkData();
        ShipmentData shipmentData = new ShipmentData();
        SaleHistoryData saleHistoryData = SaleHistoryData.getInstance();

        RestorkHandler restorkHandler = new RestorkHandler();
        InventoryHandler inventoryHandler = new InventoryHandler();
        ShipmentHandler shipmentHandler = new ShipmentHandler();
        SaleHistoryHandler saleHistoryHandler = new SaleHistoryHandler();

        HomePage homePage = new HomePage();
        homePage.setSize(1300, 800);
        homePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homePage.setLocationRelativeTo(null);
        homePage.setVisible(true);

        homePage.getInventoryPanel().setTableModel(inventoryData.getData());
        homePage.getRestorkPanel().setTableModel(restorkData.getData());
        homePage.getShipmentPanel().setTableModel(shipmentData.getData());
        homePage.getSaleHistoryChartPanel().setDataSet(saleHistoryData.getDataSet());

        restorkHandler.bindingToView(homePage.getRestorkPanel());
        restorkHandler.bindingToModel(restorkData);
        inventoryHandler.bindingToView(homePage.getInventoryPanel());
        inventoryHandler.bindingToModel(inventoryData);
        shipmentHandler.bindingToView(homePage.getShipmentPanel());
        shipmentHandler.bindingToModel(shipmentData);
        saleHistoryHandler.bindingToView(homePage.getSaleHistoryChartPanel());
        saleHistoryHandler.bindingToModel(saleHistoryData);
        // add notification
        shipmentHandler.bindingToSaleHistory(saleHistoryHandler);

        // add object of event listener
        homePage.getRestorkPanel().addListener(restorkHandler);
        homePage.getInventoryPanel().addListener(inventoryHandler);
        homePage.getShipmentPanel().addListener(shipmentHandler);
        homePage.getSaleHistoryChartPanel().addListener(saleHistoryHandler);
    }
}
