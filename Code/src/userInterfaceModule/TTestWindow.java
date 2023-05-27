package userInterfaceModule;

import java.awt.Dimension;

import dataLoading.*;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utilities.*;

public class TTestWindow implements ActionListener, WindowStrategy {
	private JButton button;
	private JComboBox<DataForRegion> firstSelection;
	private JComboBox<DataForRegion> secondSelection;
	private JLabel result;
	private JFrame frame;
	private JPanel panel;
	private DataLoadingAdapter data;
	public TTestWindow(DataLoadingAdapter dataLoadingAdapter) {
		this.data = dataLoadingAdapter;
		frame = new JFrame();
		frame.setSize(900, 600);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button) {
			DataForRegion d1 = (DataForRegion) firstSelection.getSelectedItem();
			DataForRegion d2 = (DataForRegion) secondSelection.getSelectedItem();
			result.setText("Result: " + Ttest.calculateTTest(d1, d2));
			result.setHorizontalAlignment(JLabel.CENTER);
			result.setVerticalAlignment(JLabel.CENTER);
			Font font = new Font("Serif", Font.PLAIN, 20);
			result.setFont(font);
			frame.revalidate();
			frame.repaint();
		}
		
	}
	@Override
	public void createWindow() {
		Vector<DataForRegion> dataList = new Vector<>();
		for(DataForRegion dataReg: this.data.getData()) {
			dataList.add(dataReg);
		}

		JLabel firstData = new JLabel("Select your first data:");
		firstSelection = new JComboBox<>(dataList);
		
		JLabel secondData = new JLabel("Select your second data:");
		secondSelection = new JComboBox<>(dataList);
		
		button = new JButton("Operate tests");
		button.addActionListener(this);
		
		result = new JLabel();
		result.setLocation(new Point(button.getX(), button.getY() + 5));
		
		panel = new JPanel(new FlowLayout());
		panel.setPreferredSize(new Dimension(900, 300));
		panel.add(firstData);
		panel.add(firstSelection);
		panel.add(secondData);
		panel.add(secondSelection);
		panel.add(button);
		panel.add(result);
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
	}
}
