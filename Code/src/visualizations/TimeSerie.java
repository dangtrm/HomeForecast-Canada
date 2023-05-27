package visualizations;
import dataLoading.*;
import utilities.*;

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
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

public class TimeSerie extends Visualization{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TimeSeriesCollection dataset;
	private XYPlot plot;
	private JFreeChart chart;
	private int firstNum = 0;
	private int secondNum = 0;
	private XYSplineRenderer splinerenderer1;
	public JPanel createNewChart(ArrayList<DataForRegion> dataRegionList) {
		String dateType = "yearly";
        DataForRegion data = dataRegionList.get(dataRegionList.size() - 1);
        TimeSeries series1 = new TimeSeries("Values for Region " + data.region);
        int year = 0;
        int nowYear = 0;
        for(int i = 0; i < data.values.size(); i++) {
        	year = Integer.parseInt(ConvertStringFacade.convert(dateType, data.dates.get(i)));
        	if(year != nowYear) {
        		nowYear = year;
        		double avg = this.getAverageForYear(data,Integer.parseInt(ConvertStringFacade.convert(dateType, data.dates.get(i))));
        		series1.add(new Year(Integer.parseInt(ConvertStringFacade.convert(dateType, data.dates.get(i)))), avg);
        	}
        }

		dataset = new TimeSeriesCollection();
		dataset.addSeries(series1);

		plot = new XYPlot();
		splinerenderer1 = new XYSplineRenderer();

		plot.setDataset(0, dataset);
		plot.setRenderer(0, splinerenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis("Values"));

		plot.mapDatasetToRangeAxis(firstNum, secondNum);// 1st dataset to 1st y-axis
		firstNum++;
		secondNum++;

		chart = new JFreeChart("Values for regions",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		
		return chartPanel;
	}
	
	public JPanel CreateAddData(ArrayList<DataForRegion> dataRegionList) {
		String dateType = "yearly";
        DataForRegion data = dataRegionList.get(dataRegionList.size() - 1);
        TimeSeries series = new TimeSeries("Values for Region " + data.region);
        int year = 0;
        int nowYear = 0;
        for(int i = 0; i < data.values.size(); i++) {
        	year = Integer.parseInt(ConvertStringFacade.convert(dateType, data.dates.get(i)));
        	if(year != nowYear) {
        		nowYear = year;
        		double avg = this.getAverageForYear(data,Integer.parseInt(ConvertStringFacade.convert(dateType, data.dates.get(i))));
        		series.add(new Year(Integer.parseInt(ConvertStringFacade.convert(dateType, data.dates.get(i)))), avg);
        	}
        }
		dataset.addSeries(series);
		XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
		plot.setDataset(1, dataset);
		plot.setRenderer(firstNum, splinerenderer2);
		plot.mapDatasetToRangeAxis(firstNum, secondNum); // 2nd dataset to 2nd y-axis
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
		String dateType = "yearly";
		int year = 0;
		int nowYear = 0;
		TimeSeriesCollection dataset1 = new TimeSeriesCollection();
		for(int a = 0; a < dataRegionList.size(); a++) {
			DataForRegion data = dataRegionList.get(a);
			TimeSeries series = new TimeSeries("Values for Region " + data.region);
			for(int i = 0; i < data.values.size(); i++) {
	        	year = Integer.parseInt(ConvertStringFacade.convert(dateType, data.dates.get(i)));
	        	if(year != nowYear) {
	        		nowYear = year;
	        		double avg = this.getAverageForYear(data,Integer.parseInt(ConvertStringFacade.convert(dateType, data.dates.get(i))));
	        		series.add(new Year(Integer.parseInt(ConvertStringFacade.convert(dateType, data.dates.get(i)))), avg);
	        	}
	        }
			dataset1.addSeries(series);
			XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
			splinerenderer2.setSeriesPaint(0, color);
			splinerenderer2.setSeriesShape(0,  shape);

			plot.setDataset(0, dataset1);
			plot.setRenderer(0, splinerenderer2);
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
		String dateType = "monthly";
		int month = 0;
		int nowMonth = 0;
		int year = 0;
		int nowYear = 0;
		TimeSeriesCollection dataset1 = new TimeSeriesCollection();
		for(int a = 0; a < dataRegionList.size(); a++) {
			DataForRegion data = dataRegionList.get(a);
			TimeSeries series = new TimeSeries("Values for Region " + data.region);
			for(int i = 0; i < data.values.size(); i++) {
				month = Integer.parseInt(ConvertStringFacade.convert(dateType, data.dates.get(i)));
				year = Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i)));
	        	if(month != nowMonth || year != nowYear) {
	        		nowMonth = month;
					nowYear = year;
	        		double avg = this.getAverageForMonth(data,Integer.parseInt(ConvertStringFacade.convert(dateType, data.dates.get(i))), year);
					series.add(new Month(Integer.parseInt(ConvertStringFacade.convert(dateType, data.dates.get(i))),Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i)))), avg);
	        	}
	        }
			dataset1.addSeries(series);
			XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
			splinerenderer2.setSeriesPaint(0, color);
			splinerenderer2.setSeriesShape(0,  shape);

			plot.setDataset(0, dataset1);
			plot.setRenderer(0, splinerenderer2);
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
