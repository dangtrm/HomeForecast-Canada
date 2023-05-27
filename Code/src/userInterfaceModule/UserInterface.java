package userInterfaceModule;

/*************************************************
 * FALL 2022
 * EECS 3311 GUI SAMPLE CODE
 * ONLT AS A REFERENCE TO SEE THE USE OF THE jFree FRAMEWORK
 * THE CODE BELOW DOES NOT DEPICT THE DESIGN TO BE FOLLOWED 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import dataLoading.*;
import visualizations.*;
import wekaMethods.*;



public class UserInterface extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JButton addView;
	public JButton removeView;
	public JButton load;
	public JButton configure;
	public JComboBox<String> viewsList;
	public JComboBox<String> geoList;
	public JComboBox<String> fromListYears;
	public JComboBox<String> fromListMonths;
	public JComboBox<String> toListYears;
	public JComboBox<String> toListMonths;
	public JComboBox<String> forecastingMethods;
	public JButton selectMethod;
	public JButton addAdditionalParameters;
	public JButton clearData;
	public DataLoading data;
	public Color color;
	public Shape shape;
	public int width;
	public int length;
	public DataLoadingAdapter dataLoadingAdapter;
	
	public JPanel west;
	public Visualization visualization;
	public HashMap<String, Visualization> mapToVisualization; 
	public HashMap<String, JPanel> mapToPanel;
	public HashMap<String, WekaMethods> wekaMethods;
	public JButton compare;
	public static JFrame frame;
	private static UserInterface instance;

	public static UserInterface getInstance() throws Exception {
		if (instance == null)
			instance = new UserInterface();

		return instance;
	}

	private UserInterface() throws Exception {
		// Set window title
		super("Country Statistics");
		data = new DataLoading();
		dataLoadingAdapter = new DataLoadingAdapter(data);

		GridLayout gridLayout = new GridLayout(2, 2);
        setLayout(gridLayout);
		mapToVisualization = new HashMap<String, Visualization>();
		mapToPanel = new HashMap<String, JPanel>();
		wekaMethods = new HashMap<String, WekaMethods>();
		// Set top bar
		visualization = new Visualization();
		JLabel chooseGeoParameter = new JLabel("Choose a geographical parameter: ");
		Vector<String> geoNames = dataLoadingAdapter.getCountries();

		geoList = new JComboBox<String>(geoNames);
		
		Vector<String> forecastingList = new Vector<String>();
		forecastingList.add("Prediction");
		forecastingList.add("Forecasting");
		
		forecastingMethods = new JComboBox<String>(forecastingList);
		
		selectMethod = new JButton("Select Method");
		selectMethod.addActionListener(this);
		
		JLabel from = new JLabel("From");
		JLabel to = new JLabel("To");
		Vector<String> years = new Vector<String>();
		Vector<String> months = new Vector<String>();
		for (int i = 2022; i >= 1981; i--) {
			years.add("" + i);
		}
		for(int i = 1; i < 13; i++) {
			months.add("" + i);
		}
		load = new JButton("Load Data");
		load.addActionListener(this);
		clearData = new JButton("Clear Data");
		clearData.addActionListener(this);
		fromListYears = new JComboBox<String>(years);
		fromListMonths = new JComboBox<String>(months);
		toListYears = new JComboBox<String>(years);
		toListMonths = new JComboBox<String>(months);
		
		addAdditionalParameters = new JButton("Add additional parameters");
		addAdditionalParameters.addActionListener(this);

		JPanel north = new JPanel();
		north.add(chooseGeoParameter);
		north.add(geoList);
		north.add(from);
		north.add(fromListMonths);
		north.add(fromListYears);
		north.add(to);
		north.add(toListMonths);
		north.add(toListYears);
		north.add(load);
		north.add(addAdditionalParameters);
		
		compare = new JButton("Compare");
		compare.addActionListener(this);
		updateButtonState();
		north.add(compare);
		north.add(clearData);
		updateButtonState();

		// Set bottom bar

		JLabel viewsLabel = new JLabel("Available Views: ");

		Vector<String> viewsNames = new Vector<String>();
		viewsNames.add("Bar Chart");
		viewsNames.add("Scatter Chart");
		viewsNames.add("Time Series");
		viewsList = new JComboBox<String>(viewsNames);
		addView = new JButton("+");
		removeView = new JButton("-");
		addView.addActionListener(this);
		removeView.addActionListener(this);
		configure = new JButton("Configure Visualization");
		configure.addActionListener(this);
		initMapping(); //init map
		initMethodMap();

		JPanel south = new JPanel();
		south.add(viewsLabel);
		south.add(viewsList);
		south.add(addView);
		south.add(removeView);
//		

		JPanel east = new JPanel();
		east.add(forecastingMethods);
		east.add(selectMethod);

		// Set charts region
		west = new JPanel();
		west.setLayout(new GridLayout(2, 0));
		west.add(configure);

		getContentPane().add(north, BorderLayout.NORTH);
		getContentPane().add(east, BorderLayout.EAST);
		getContentPane().add(south, BorderLayout.SOUTH);
		getContentPane().add(west, BorderLayout.WEST);
	}
	
	private void updateButtonState() {
		compare.setEnabled(dataLoadingAdapter.getData().size() > 1);
		clearData.setEnabled(dataLoadingAdapter.getData().size() > 0);
	}
	
	private void initMapping() {
		Visualization barchart = new BarChart();
		mapToVisualization.put("Bar Chart", barchart);
		Visualization scatterchart = new ScatterChart();
		mapToVisualization.put("Scatter Chart", scatterchart);
		Visualization timeseries = new TimeSerie();
		mapToVisualization.put("Time Series", timeseries);
	}
	
	public void initMethodMap() {
		wekaMethods.put("Prediction", new WekaTimeSeriesPrediction());
		wekaMethods.put("Forecasting",  new WekaTimeSeriesForecasting());

	}
	
	public void createMonthlyData(DataForRegion dataForRegion, String vis_value) {
		Visualization vis = this.visualization.getVisualization().get(this.visualization.getVisualization().size()-1);
		visualization.removeVisualization(vis);
		JPanel remove_panel = mapToPanel.get(vis_value);
		this.remove(remove_panel);
		visualization.addVisualization(vis);
		JPanel new_panel = vis.createMonthly(this.color,this.shape, this.width, this.length, this.visualization.getDataRegion());
		this.add(new_panel, BorderLayout.WEST);
		mapToPanel.put(vis_value, new_panel);
		this.validate();
	}
	
	public void addToVisualization(Visualization visualization_addition) {
		if(visualization_addition == null) {
			
		}
		if(visualization.getVisualization().size()==0) {
			
		}
		else {
			visualization_addition.CreateAddData(visualization.getDataRegion());
			this.validate();
		}
	}
		
	public void deselect(String visualization_value, Visualization vis_delete) {
		JPanel remove_panel = mapToPanel.get(visualization_value);
		this.remove(remove_panel);
		visualization.removeVisualization(vis_delete);
		dataLoadingAdapter.getData().removeAll(dataLoadingAdapter.getData());
		visualization.getDataRegion().removeAll(visualization.getDataRegion());
		this.revalidate();
		this.repaint();
	}
	
	public void removeConfiguredVisualization(Visualization visualization_type, String visualization_value) {
		JPanel remove_panel = mapToPanel.get(visualization_value);
		this.remove(remove_panel);
		visualization.removeVisualization(visualization_type);
		this.revalidate();
		this.repaint();
	}
	
	public void select(String addValue, Visualization addVis) {
		if(visualization.getVisualization().size() < 2) {
			visualization.addVisualization(addVis);
			JPanel holder = addVis.createNewChart(visualization.getDataRegion());
			this.add(holder, BorderLayout.WEST);
			mapToPanel.put(addValue, holder);
			this.validate();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
        if(e.getSource()==addView){
        	dataLoadingAdapter.setVisualization(visualization);
        	Parameters parameter = new Parameters(geoList.getSelectedItem().toString(), 
        			fromListMonths.getSelectedItem().toString(),fromListYears.getSelectedItem().toString(), 
        			toListMonths.getSelectedItem().toString(), toListYears.getSelectedItem().toString());
        	parameter.setDataLoading(dataLoadingAdapter);
        	parameter.storeData();
        	parameter.sendToVisualization();
        	String visualization_value = viewsList.getSelectedItem().toString();
        	Visualization visualization_addition = mapToVisualization.get(visualization_value);
        	select(visualization_value,visualization_addition);
        }
        if(e.getSource() == removeView) {
        	String visualization_value = viewsList.getSelectedItem().toString();
        	Visualization visualization_deletion = mapToVisualization.get(visualization_value);
        	deselect(visualization_value, visualization_deletion);
        }
        if(e.getSource() == load) {
        	Parameters parameter = new Parameters(geoList.getSelectedItem().toString(), 
        			fromListMonths.getSelectedItem().toString(),fromListYears.getSelectedItem().toString(), 
        			toListMonths.getSelectedItem().toString(), toListYears.getSelectedItem().toString());
        	parameter.setDataLoading(dataLoadingAdapter);
        	parameter.storeData();
        	parameter.sendToTable();
        	updateButtonState();
        }
        if(e.getSource() == addAdditionalParameters) {
			data.setVisualization(visualization);
        	String additionalParam = viewsList.getSelectedItem().toString();
        	Parameters parameter = new Parameters(geoList.getSelectedItem().toString(), 
        			fromListMonths.getSelectedItem().toString(),fromListYears.getSelectedItem().toString(), 
        			toListMonths.getSelectedItem().toString(), toListYears.getSelectedItem().toString());
        	parameter.setDataLoading(dataLoadingAdapter);
        	parameter.storeData();
        	parameter.sendToVisualization();
        	String visualization_value = viewsList.getSelectedItem().toString();
        	Visualization visualization_addition = mapToVisualization.get(visualization_value);
        	addToVisualization(visualization_addition);
        	updateButtonState();
        }
        
        if(e.getSource() == configure) {
        	String visualization_value = viewsList.getSelectedItem().toString();
        	Visualization visualization_addition = mapToVisualization.get(visualization_value);
        	WindowStrategy configureWindow = new ConfigureWindow(this.visualization,visualization_addition,visualization_value, this);
        	configureWindow.createWindow();
        }
        
        if(e.getSource() == selectMethod) {
        	String visualization_value = viewsList.getSelectedItem().toString();
        	String method = forecastingMethods.getSelectedItem().toString();
        	WekaMethods wekaMethod = wekaMethods.get(method);
        	WindowStrategy window = new InternalWindow(method,this, dataLoadingAdapter, visualization_value);
        	window.createWindow();
        }
        if (e.getSource() == compare) {
			WindowStrategy ttestWindow = new TTestWindow(dataLoadingAdapter);
			ttestWindow.createWindow();
		}
        if(e.getSource() == clearData) {
        	dataLoadingAdapter.getData().removeAll(dataLoadingAdapter.getData());
        	visualization.getDataRegion().removeAll(visualization.getDataRegion());
        	updateButtonState();
        }
    }

	public static void main(String[] args) throws Exception {
		frame = UserInterface.getInstance();
		frame.setSize(900, 600);
		frame.pack();
		frame.setVisible(true);
	}
	// TODO Auto-generated method stub

	public void createConfiguredChart(Color color, Shape shape, int width, int length, Visualization visualization_type, String viz_value, String timePeriod) {
		this.visualization.addVisualization(visualization_type);
		mapToVisualization.put(viz_value, visualization_type);
		JPanel holder;
		if(timePeriod.equals("Yearly")) {
			holder = visualization_type.CreateConfiguredChart(color, shape, width, length, this.visualization.getDataRegion());
		}
		else {
			holder = visualization_type.createMonthly(color, shape, width, length, this.visualization.getDataRegion());
		}
		this.add(holder, BorderLayout.WEST);
		mapToPanel.put(viz_value, holder);
		this.validate();
	}

	public void setVisParams(Color color, Shape shape, int width, int length) {
		this.color = color;
		this.shape = shape;
		this.width = width;
		this.length = length;
	}

}

