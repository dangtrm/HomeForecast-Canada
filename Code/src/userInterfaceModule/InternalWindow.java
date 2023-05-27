package userInterfaceModule;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dataLoading.*;
import wekaMethods.*;

public class InternalWindow extends JFrame implements ActionListener, WindowStrategy{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserInterface userInterface;
	private JFrame frame;
	private String method;
	private JButton button;
	private JPanel panel;
	private JTextField inputBox;
	private JLabel label;
	private DataForRegion dataForRegion;
	private DataLoadingAdapter data;
	private String wekaMethods;
	private String vis_value;
	
	private JComboBox<DataForRegion> firstSelection;
	
	public InternalWindow(String method, UserInterface userInterface, DataLoadingAdapter dataLoadingAdapter, String visualization_value) {
		this.vis_value = visualization_value;
		this.userInterface = userInterface;
		this.wekaMethods = method;
		dataForRegion = new DataForRegion();
		this.data = dataLoadingAdapter;
		frame = new JFrame();
    	frame.setSize(900, 600);
	}
	public int getNumMonths(String months) {
		System.out.println(months);
		return Integer.parseInt(months);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button) {
			DataForRegion dataRegion = (DataForRegion) firstSelection.getSelectedItem();
			try {
				WekaFactory wekaFactory = new WekaFactory();
				wekaFactory.buildMethod(dataRegion, getNumMonths(inputBox.getText()), this.method);
				this.userInterface.createMonthlyData(dataRegion, this.vis_value);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	@Override
	public void createWindow() {
		Vector<DataForRegion> dataList = new Vector<>();
		for(DataForRegion dataReg: data.getData()) {
			dataList.add(dataReg);
		}
		JLabel firstData = new JLabel("Select your first data:");
		firstSelection = new JComboBox<>(dataList);
    	label = new JLabel("Enter number of months:");
        inputBox = new JTextField(20); // create a text field with 20 columns
        button = new JButton("Done");
        button.addActionListener(this);
        panel = new JPanel();
        panel.add(label);
        panel.add(inputBox);
        panel.add(button);

        panel.add(firstData);
        panel.add(firstSelection);
        frame.add(panel);
    	frame.pack();
    	frame.setVisible(true);
		
	}

}
