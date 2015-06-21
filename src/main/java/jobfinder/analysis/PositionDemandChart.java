package jobfinder.analysis;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class PositionDemandChart extends ApplicationFrame {

    private static final long serialVersionUID = 4546147160777122216L;

    Map<String, Long[]> jobData;

    public PositionDemandChart(String s, Map<String, Long[]> jobData) {
        super(s);
        this.jobData = jobData;
        setContentPane(createDemoLine());
    }

    private JPanel createDemoLine() {
        JFreeChart jfreechart = createChart(createDataSet());
        return new ChartPanel(jfreechart);
    }

    private JFreeChart createChart(DefaultCategoryDataset linedataset) {

        JFreeChart chart = ChartFactory.createBarChart("职位需求分析图", "", "人数", linedataset, PlotOrientation.VERTICAL, true, true,
                false);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinesVisible(true);
        plot.setBackgroundAlpha(0.9f);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setUpperMargin(0.30);
        rangeAxis.setLabelAngle(Math.PI / 2.0);
        configFont(chart);
        return chart;
    }

    private DefaultCategoryDataset createDataSet() {

        DefaultCategoryDataset linedataset = new DefaultCategoryDataset();

        // 各曲线名称
        String series = "职位需求分析图 ";
        Long[] typeData;
        //
        for (Entry<String, Long[]> data : jobData.entrySet()) {
            typeData = data.getValue();
            if (typeData[3] > 1000 && typeData[3] < 10000) {
                continue;
            }
            linedataset.addValue(typeData[3], series, data.getKey());
        }

        return linedataset;

    }

    private void configFont(JFreeChart chart) {
        // 配置字体
        Font xfont = new Font("宋体", Font.PLAIN, 16);// X轴
        Font yfont = new Font("宋体", Font.PLAIN, 16);// Y轴
        Font kfont = new Font("宋体", Font.PLAIN, 16);// 底部
        Font titleFont = new Font("隶书", Font.BOLD, 25); // 图片标题
        CategoryPlot plot = chart.getCategoryPlot(); // 图形的绘制结构对象

        // 图片标题
        chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));

        // 底部
        chart.getLegend().setItemFont(kfont);

        // X 轴
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(xfont);// 轴标题
        domainAxis.setTickLabelFont(xfont);// 轴数值
        domainAxis.setTickLabelPaint(Color.BLACK); // 字体颜色
        // domainAxis.setTickLabelPaint(Color.getColor("", 180));
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        // // 横轴上的label斜显示

        // Y 轴
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelFont(yfont);
        rangeAxis.setLabelPaint(Color.black); // 字体颜色
        rangeAxis.setTickLabelFont(yfont);
        rangeAxis.setLabelAngle(45);

    }

}
