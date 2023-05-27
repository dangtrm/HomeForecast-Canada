package visualizations;
import java.awt.Color;
import dataLoading.*;
import utilities.*;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;


public class Visualization extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ArrayList<Visualization> visualization;
	protected int index;
	protected ArrayList<DataForRegion> dataRegionList;	
	public HashMap<String, Shape> shapes;
	public HashMap<String, Color> colours;
	
	public Visualization() {
		this.visualization = new ArrayList<Visualization>();
		this.dataRegionList = new ArrayList<DataForRegion>();
		shapes = new HashMap<String, Shape>();
		colours = new HashMap<String, Color>();
	}
	
	public void addVisualization(Visualization v) {
		visualization.add(v);
	}
	
	public ArrayList<Visualization> getVisualization(){
		return this.visualization;
	}
	
	public void removeVisualization(Visualization v) {
		this.visualization.remove(v);
	}

	public void addDataForRegion(DataForRegion data) {
		this.dataRegionList.add(data);
	}

	public ArrayList<DataForRegion> getDataRegion() {
		return dataRegionList;
	}

	public void setDataRegion(ArrayList<DataForRegion> dataRegion) {
		this.dataRegionList = dataRegion;
	}
	
	public JPanel createNewChart(ArrayList<DataForRegion> arrayList) {
		JPanel panel = new JPanel();
		return panel;
	}
	
	public double getAverageForYear(DataForRegion data, int year) {
		double sum = 0;
		int count = 0;
		for(int i = 0; i < data.values.size(); i++) {
			if(Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i))) == year) {
				sum += Double.parseDouble(data.values.get(i));
				count++;
			}
		}
		double avg = sum / count;
		return avg;
		
	}
	public double getAverageForMonth(DataForRegion data, int month, int year) {
		double sum = 0;
		int count = 0;
		for(int i = 0; i < data.values.size(); i++) {
			if(Integer.parseInt(ConvertStringFacade.convert("monthly", data.dates.get(i))) == month && Integer.parseInt(ConvertStringFacade.convert("yearly", data.dates.get(i))) == year) {
				sum += Double.parseDouble(data.values.get(i));
				count++;
			}
		}
		double avg = sum / count;
		return avg;
		
	}
	
	public JPanel CreateAddData(ArrayList<DataForRegion> dataRegion) {
		return new JPanel();
	}
	
	public JPanel CreateConfiguredChart(Color c, Shape shape, int width, int length, ArrayList<DataForRegion> dataRegionList2) {
		return new JPanel();
	}

	public JPanel createMonthly(Color color, Shape shape, int width, int length,
			ArrayList<DataForRegion> dataRegionList2) {
		return null;
	}
	
	
	
	
}
