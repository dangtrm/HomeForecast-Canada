package userInterfaceModule;

import java.awt.Component;
import java.awt.Font;
import dataLoading.*;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SummaryTable extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double avgVal, stdDev, maxVal, minVal;

	public SummaryTable() {
		avgVal = 0.0;
		stdDev = 0.0;
		maxVal = 0.0;
		minVal = 0.0;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setAlignmentY(Component.CENTER_ALIGNMENT);
	}

	public void createTable(DataForRegion dataRegion) {
		constructData(dataRegion);
		
		Font font = new Font("Serif", Font.PLAIN, 20);

		JLabel avg = new JLabel("Average value: " + String.format("%.2f", avgVal));
		avg.setHorizontalAlignment(JLabel.CENTER);
		avg.setVerticalAlignment(JLabel.CENTER);
		avg.setFont(font);
		add(avg);

		JLabel std = new JLabel("Standard Deviation value: " + String.format("%.2f", stdDev));
		std.setHorizontalAlignment(JLabel.CENTER);
		std.setVerticalAlignment(JLabel.CENTER);
		std.setFont(font);
		add(std);

		JLabel max = new JLabel("Max value: " + String.format("%.2f", maxVal));
		max.setHorizontalAlignment(JLabel.CENTER);
		max.setVerticalAlignment(JLabel.CENTER);
		max.setFont(font);
		add(max);

		JLabel min = new JLabel("Min value: " + String.format("%.2f", minVal));
		min.setHorizontalAlignment(JLabel.CENTER);
		min.setVerticalAlignment(JLabel.CENTER);
		min.setFont(font);
		add(min);
	}

	private void constructData(DataForRegion data) {
		int count = 0;
		double numAtIndex = 0.0;
		double total = 0.0;
		minVal = Float.MAX_VALUE;
		maxVal = Float.MIN_VALUE;
		for (int i = 0; i < data.values.size(); i++) {
			total += Double.parseDouble(data.values.get(i));
			numAtIndex = Double.parseDouble(data.values.get(i));
			maxVal = Math.max(maxVal, numAtIndex);
			minVal = Math.min(minVal, numAtIndex);
			count++;
		}
		avgVal = (count != 0) ? total / count : 0.0;
		minVal = (minVal == Float.MAX_VALUE) ? 0.0 : minVal;
		maxVal = (maxVal == Float.MIN_VALUE) ? 0.0 : maxVal;
		calStdDev(data);
	}

	private void calStdDev(DataForRegion data) {
		for(int i = 0; i < data.values.size(); i++) {
			stdDev += Math.pow(Double.parseDouble(data.values.get(i)) - avgVal, 2);
		}
		stdDev = Math.sqrt(stdDev / data.values.size());
	}

}