package jobfinder.analysis;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
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

public class CompanyGradeChart extends ApplicationFrame {

    private static final long serialVersionUID = 4546147160777122216L;

    Map<String, Float[]> jobData;

    Map<Integer, String> typemap;

    public CompanyGradeChart(String s, Map<String, Float[]> jobData) {
        super(s);
        this.jobData = jobData;
        setContentPane(createDemoLine());

        typemap = new HashMap<Integer, String>();
        typemap.put(1, "IT行业");
        typemap.put(2, "金融行业");
        typemap.put(3, "专业服务");
        typemap.put(4, "教育培训行业");
        typemap.put(5, "消费品行业");
        typemap.put(6, "文化传媒行业");
        typemap.put(7, "建筑/房地产行业");
        typemap.put(8, "贸易物流行业");
        typemap.put(9, "制造工业");
        typemap.put(10, "医疗/卫生");
        typemap.put(11, "服务业");
        typemap.put(12, "其他");
    }

    private JPanel createDemoLine() {
        JFreeChart jfreechart = createChart(createDataSet());
        return new ChartPanel(jfreechart);
    }

    private JFreeChart createChart(DefaultCategoryDataset linedataset) {

        JFreeChart chart = ChartFactory.createBarChart("行业评分情况", "", "评分", linedataset, PlotOrientation.VERTICAL, true, true,
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
        String series = "行业评分情况 ";
        Float[] typeData;
        for (Entry<String, Float[]> data : jobData.entrySet()) {
            typeData = data.getValue();
            // if (typeData[3] > 1000 && typeData[3] < 10000) {
            // continue;
            // }
            String type = "";
            if (data.getKey().equals("1")) {
                type = "IT行业";
            } else if (data.getKey().equals("2")) {
                type = "金融行业";
            } else if (data.getKey().equals("3")) {
                type = "专业服务";
            } else if (data.getKey().equals("4")) {
                type = "教育培训行业";
            } else if (data.getKey().equals("5")) {
                type = "消费品行业";
            } else if (data.getKey().equals("6")) {
                type = "文化传媒行业";
            } else if (data.getKey().equals("7")) {
                type = "建筑/房地产行业";
            } else if (data.getKey().equals("8")) {
                type = "贸易物流行业";
            } else if (data.getKey().equals("9")) {
                type = "制造行业";
            } else if (data.getKey().equals("10")) {
                type = "医疗/卫生";
            } else if (data.getKey().equals("11")) {
                type = "服务业";
            } else if (data.getKey().equals("12")) {
                type = "其他";
            }
            linedataset.addValue(typeData[1], series, type);
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
