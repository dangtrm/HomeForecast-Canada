package visualizations;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Shape;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYShapeRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import dataLoading.*;
import utilities.*;

public class ScatterChart extends Visualization{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TimeSeriesCollection dataset;
	XYPlot plot;
	JFreeChart chart;
	int firstNum = 0;
	int secondNum = 0;
	XYShapeRenderer itemrenderer1;

	public JPanel createNewChart(ArrayList<DataForRegion> dataRegionList) {
        DataForRegion data = dataRegionList.get(dataRegionList.size() - 1);
        TimeSeries series1 = new TimeSeries("Values for Region " + data.region);
        int year = 0;
        int nowYear = 0;
        for(int i = 0; i < data.values.size(); i++) {
        	year = Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i)));
        	if(year != nowYear) {
        		nowYear = year;
        		double avg = this.getAverageForYear(data,Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i))));
        		series1.add(new Year(Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i)))), avg);
        	}
        }
		dataset = new TimeSeriesCollection();
		dataset.addSeries(series1);


		plot = new XYPlot();
		itemrenderer1 = new XYShapeRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, itemrenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis("Values"));

		plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis

		chart = new JFreeChart("Values for region",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		return chartPanel;

	}
	
	public JPanel CreateAddData(ArrayList<DataForRegion> dataRegionList) {
        DataForRegion data = dataRegionList.get(dataRegionList.size() - 1);
        TimeSeries series = new TimeSeries("Values for Region " + data.region);
        int year = 0;
        int nowYear = 0;
        for(int i = 0; i < data.values.size(); i++) {
        	year = Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i)));
        	if(year != nowYear) {
        		nowYear = year;
        		double avg = this.getAverageForYear(data,Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i))));
        		series.add(new Year(Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i)))), avg);
        	}
        }
		dataset.addSeries(series);
		plot.setDataset(1, dataset);
		firstNum++;
		secondNum++;
		JFreeChart chart = new JFreeChart("Values for regions",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		return chartPanel;

	}
	
	public JPanel CreateConfiguredChart(Color color, Shape shape, int width, int length, ArrayList<DataForRegion> dataRegionList) {
		TimeSeriesCollection dataset1 = new TimeSeriesCollection();
        int year = 0;
        int nowYear = 0;
        for(int a = 0; a < dataRegionList.size(); a++) {
			DataForRegion data = dataRegionList.get(a);
			TimeSeries series = new TimeSeries("Values for Region " + data.region);
			for(int i = 0; i < data.values.size(); i++) {
	        	year = Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i)));
	        	if(year != nowYear) {
	        		nowYear = year;
	        		double avg = this.getAverageForYear(data,Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i))));
	        		series.add(new Year(Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i)))), avg);
	        	}
	        }
			dataset1.addSeries(series);
			XYShapeRenderer itemrenderer2 = new XYShapeRenderer();
			itemrenderer2.setSeriesPaint(0, color);
			itemrenderer2.setSeriesShape(0,  shape);

			plot.setDataset(0, dataset1);
			plot.setRenderer(0, itemrenderer2);
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
		TimeSeriesCollection dataset1 = new TimeSeriesCollection();
		XYPlot plot = new XYPlot();
		for(int a = 0; a < dataRegionList.size(); a++) {
			DataForRegion data = dataRegionList.get(a);
			TimeSeries series = new TimeSeries("Values for Region " + data.region);
			for(int i = 0; i < data.values.size(); i++) {
				month = Integer.parseInt(ConvertStringFacade.convert("monthly", data.dates.get(i)));
				year = Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i)));
	        	if(month != nowMonth || year != nowYear) {
	        		nowMonth = month;
					nowYear = year;
	        		double avg = this.getAverageForMonth(data,Integer.parseInt(ConvertStringFacade.convert("monthly", data.dates.get(i))), year);
					series.add(new Month(Integer.parseInt(ConvertStringFacade.convert("monthly", data.dates.get(i))),Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i)))), avg);
	        	}
	        }
			dataset1.addSeries(series);
			XYShapeRenderer itemrenderer2 = new XYShapeRenderer();
			itemrenderer2.setSeriesPaint(0, color);
			itemrenderer2.setSeriesShape(0,  shape);

			plot.setDataset(1, dataset1);
			plot.setRenderer(1, itemrenderer2);
			DateAxis domainAxis = new DateAxis("Year");
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
