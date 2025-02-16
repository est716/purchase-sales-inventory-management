package View;

import java.awt.BorderLayout;
import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class SaleHistoryChartPanel extends ViewPanel {

    private ChartPanel chartPanel;
    private JFreeChart barChart;
    private DefaultCategoryDataset dataset;
    public SaleHistoryChartPanel() {
        // initialize layout
        this.setLayout(new BorderLayout());

        // dataset need get data from SaleHistoryData

        this.barChart = ChartFactory.createBarChart(
                "商品銷售數量長條圖",
                "商品",
                "數量",
                this.dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        setBarChartStyle();

        this.chartPanel = new ChartPanel(this.barChart);
        this.add(this.chartPanel, BorderLayout.CENTER);
    }

    private void setBarChartStyle(){
        this.barChart.getTitle().setFont(font); // 設定標題大小
        this.barChart.getLegend().setItemFont(font.deriveFont(20f)); // 設定Item大小
        this.barChart.getCategoryPlot().getDomainAxis().setLabelFont(font); // 設定X軸名稱大小
        this.barChart.getCategoryPlot().getDomainAxis().setTickLabelFont(font); // 設定X軸刻度大小
        this.barChart.getCategoryPlot().getRangeAxis().setLabelFont(font); // 設定Y軸名稱大小
        this.barChart.getCategoryPlot().getRangeAxis().setTickLabelFont(font); // 設定Y軸刻度大小
        this.barChart.getCategoryPlot().getRangeAxis().setLabelAngle(Math.PI / 2); // y axis label rotate 90 degree
        this.barChart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.decode("#64b5f6")); // set barChart Color
        this.barChart.setBackgroundPaint(Color.WHITE); // set chart background
        this.barChart.getCategoryPlot().setBackgroundPaint(Color.WHITE); // set chart background

    }

    public void setDataSet(DefaultCategoryDataset dataset){
        this.dataset = dataset;
        this.barChart.getCategoryPlot().setDataset(this.dataset);
    }

    public void updateUI(){
        revalidate();
        repaint();
    }

    public JFreeChart getbarChart(){
        return this.barChart;
    }
}
