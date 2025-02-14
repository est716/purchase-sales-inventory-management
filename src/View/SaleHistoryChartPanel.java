package View;

import java.awt.BorderLayout;
import java.awt.Dimension;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class SaleHistoryChartPanel extends ViewPanel {

    public SaleHistoryChartPanel() {
        // initialize layout
        this.setLayout(new BorderLayout());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(150, "Sales", "Product A");
        dataset.addValue(200, "Sales", "Product B");
        dataset.addValue(50, "Sales", "Product C");
        dataset.addValue(120, "Sales", "Product D");

        JFreeChart barChart = ChartFactory.createBarChart(
                "商品銷售數量長條圖",
                "商品",
                "數量",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        barChart.getTitle().setFont(font);
        barChart.getCategoryPlot().getDomainAxis().setLabelFont(font);
        barChart.getCategoryPlot().getRangeAxis().setLabelFont(font);
        barChart.getCategoryPlot().getRangeAxis().setLabelAngle(Math.PI / 2);

        ChartPanel chartPanel = new ChartPanel(barChart);
        this.add(chartPanel, BorderLayout.CENTER);
    }
}
