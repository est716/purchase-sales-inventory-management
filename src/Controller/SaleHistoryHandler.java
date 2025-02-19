package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.SaleHistoryData;
import View.SaleHistoryChartPanel;

public class SaleHistoryHandler implements ActionListener{
    private SaleHistoryData saleHistoryData;
    private SaleHistoryChartPanel saleHistoryChartPanel;


    public void insertData(String id, String num){
        this.saleHistoryData.insertData(id, num);
    }

    public void notification(){
        this.saleHistoryChartPanel.updateUI();
    }

    public void bindingToModel(SaleHistoryData saleHistoryData){
        this.saleHistoryData = saleHistoryData;
    }

    public void bindingToView(SaleHistoryChartPanel saleHistoryChartPanel){
        this.saleHistoryChartPanel = saleHistoryChartPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.saleHistoryChartPanel.getDropButton()) {
            this.saleHistoryData.dropTable();
        }    
    }

}
