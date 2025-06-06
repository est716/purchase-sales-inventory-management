package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import Controller.SaleHistoryHandler;
import Model.SaleHistoryCategoryDataset;

public class SaleHistoryChartPanel extends JPanel {
    private final Font font = new Font("標楷體", Font.PLAIN, 35);
    private ChartPanel chartPanel;
    private JFreeChart barChart;
    private JButton dropButton;
    private SaleHistoryCategoryDataset dataset;

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
                false);

        setBarChartStyle();

        this.chartPanel = new ChartPanel(this.barChart);

        this.dropButton = new JButton("Drop Table");

        this.add(this.chartPanel, BorderLayout.CENTER);
        // this.add(this.dropButton, BorderLayout.SOUTH);
    }

    private void setBarChartStyle() {
        this.barChart.getTitle().setFont(font); // 設定標題大小
        this.barChart.getLegend().setItemFont(font.deriveFont(20f)); // 設定Item大小
        this.barChart.getCategoryPlot().getDomainAxis().setLabelFont(font); // 設定X軸名稱大小
        this.barChart.getCategoryPlot().getDomainAxis().setTickLabelFont(font); // 設定X軸刻度大小
        this.barChart.getCategoryPlot().getRangeAxis().setLabelFont(font); // 設定Y軸名稱大小
        this.barChart.getCategoryPlot().getRangeAxis().setTickLabelFont(font); // 設定Y軸刻度大小
        this.barChart.getCategoryPlot().getRangeAxis().setLabelAngle(Math.PI / 2); // y axis label rotate 90 degree
        this.barChart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.decode("#64b5f6")); // set barChart Color
        this.barChart.getCategoryPlot().getRenderer().setBaseItemLabelsVisible(true); // 設定長條圖數字顯示
        this.barChart.getCategoryPlot().getRenderer()
                .setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator()); // 設定長條圖數字顯示樣式
        this.barChart.setBackgroundPaint(Color.WHITE); // set chart background
        this.barChart.getCategoryPlot().setBackgroundPaint(Color.WHITE); // set chart background
    }

    public void setDataSet(SaleHistoryCategoryDataset dataset) {
        this.dataset = dataset;
        this.barChart.getCategoryPlot().setDataset(this.dataset);
    }

    public void updateUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (dataset != null) {
                    dataset.refreshDataSet(); // 更新資料集
                }
                revalidate();
                repaint();
            }
        });
    }

    public JFreeChart getbarChart() {
        return this.barChart;
    }

    public JButton getDropButton() {
        return dropButton;
    }

    public void addListener(SaleHistoryHandler saleHistoryHandler) {
        this.dropButton.addActionListener(saleHistoryHandler);
    }

}
