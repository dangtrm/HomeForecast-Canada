package visualizations;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Shape;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import dataLoading.*;
import utilities.*;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
public class BarChart extends Visualization{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CategoryPlot plot;
	JFreeChart chart;
	DefaultCategoryDataset dataset;
	int firstNum = 0;
	int secondNum = 0;
	public JPanel createNewChart(ArrayList<DataForRegion> dataRegionList) {
		dataset = new DefaultCategoryDataset();
		DataForRegion data = dataRegionList.get(dataRegionList.size() - 1);
        int year = 0;
        int nowYear = 0;
        for(int i = 0; i < data.values.size(); i++) {
        	year = Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i)));
        	if(year != nowYear) {
        		nowYear = year;
        		double avg = this.getAverageForYear(data,Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i))));
        		dataset.setValue(avg, "Values for " + data.region, ConvertStringFacade.convert("yearly", data.dates.get(i)));
        	}
        }
		plot = new CategoryPlot();
		BarRenderer barrenderer1 = new BarRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, barrenderer1);
		CategoryAxis domainAxis = new CategoryAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis("Values"));

		plot.mapDatasetToRangeAxis(firstNum, secondNum);// 1st dataset to 1st y-axis
		firstNum++;
		secondNum++;

		chart = new JFreeChart("Values for region "+ data.region,
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);


		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		return chartPanel;
       	
	}
	
	public JPanel CreateAddData(ArrayList<DataForRegion> dataRegionList) {
        DataForRegion data = dataRegionList.get(dataRegionList.size() - 1);
        int year = 0;
        int nowYear = 0;
        for(int i = 0; i < data.values.size(); i++) {
        	year = Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i)));
        	if(year != nowYear) {
        		nowYear = year;
        		double avg = this.getAverageForYear(data,Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i))));
        		dataset.setValue(avg, "Values for " + data.region, ConvertStringFacade.convert("yearly", data.dates.get(i)));
        	}
        }
        
		plot.setDataset(1, dataset);

		JFreeChart chart = new JFreeChart("Values for region "+ data.region,
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		return chartPanel;

	}
	
	public JPanel CreateConfiguredChart(Color color, Shape shape, int width, int length,  ArrayList<DataForRegion> dataRegionList) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int year = 0;
        int nowYear = 0;
        for(int a = 0; a < dataRegionList.size(); a++) {
    		DataForRegion data = dataRegionList.get(a);
        	for(int i = 0; i < data.values.size(); i++) {
            	year = Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i)));
            	if(year != nowYear) {
            		nowYear = year;
            		double avg = this.getAverageForYear(data,Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i))));
            		dataset.setValue(avg, "Values for " + data.region, ConvertStringFacade.convert("yearly", data.dates.get(i)));
            	}
            }
        	BarRenderer barrenderer1 = new BarRenderer();
        	barrenderer1.setSeriesPaint(0, color);
//    		plot.setRenderer(1, barrenderer1);
//        	barrenderer1.setSeriesShape(0,  shape);
    		plot.setDataset(1, dataset);
        }
        
		JFreeChart chart = new JFreeChart("Values for regions",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(width, length));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		return chartPanel;
	}
	public JPanel createMonthly(Color color, Shape shape, int width, int length, ArrayList<DataForRegion> dataRegionList) {
		int month = 0;
		int nowMonth = 0;
		int year = 0;
		int nowYear = 0;
		CategoryPlot plot = new CategoryPlot();
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for(int a = 0; a < dataRegionList.size(); a++) {
			DataForRegion data = dataRegionList.get(a);
			for(int i = 0; i < data.values.size(); i++) {
				month = Integer.parseInt(ConvertStringFacade.convert("monthly", data.dates.get(i)));
				year = Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i)));
	        	if(month != nowMonth || year != nowYear) {
	        		nowMonth = month;
					nowYear = year;
	        		double avg = this.getAverageForMonth(data,Integer.parseInt(ConvertStringFacade.convert("monthly", data.dates.get(i))), year);
	        		dataset.setValue(avg, "Values for " + data.region, ConvertStringFacade.convert("monthly", data.dates.get(i)) + ConvertStringFacade.convert("yearly", data.dates.get(i)));
	        	}
	        }
			BarRenderer barrenderer1 = new BarRenderer();
        	barrenderer1.setSeriesPaint(0, color);
//			splinerenderer2.setSeriesShape(0,  shape);
//
			plot.setDataset(0, dataset);
			plot.setRenderer(0, barrenderer1);
			CategoryAxis domainAxis = new CategoryAxis("Year");
			plot.setDomainAxis(domainAxis);
			plot.setRangeAxis(new NumberAxis("Values"));
		}
		JFreeChart chart = new JFreeChart("Values for regions",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(width, length));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		return chartPanel;
	}

}
