package userInterfaceModule;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import dataLoading.*;
public class Table {

    private JFrame frame;
    private JPanel scrollPanel;
    private JPanel regionPanel;
    private ArrayList<DataForRegion> data;
    private State currentState;

    public Table() {
    }

    public void display(DataLoadingAdapter d) {
    	frame = new JFrame("Simple Table Example");
    	frame.setLayout(new BorderLayout());

    	scrollPanel = new JPanel();
    	scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.X_AXIS));

    	regionPanel = new JPanel();
    	regionPanel.setLayout(new BoxLayout(regionPanel, BoxLayout.X_AXIS));

    	data = new ArrayList<DataForRegion>();

    	frame.add(regionPanel, BorderLayout.NORTH);
    	frame.add(scrollPanel, BorderLayout.CENTER);

    	currentState = new RawDataState(this);

    	frame.setSize(400, 600);
    	frame.setVisible(true);
        data = d.getData();
        for (int i = 0; i < data.size(); i++) {
            RawTable rawTable = new RawTable();
            rawTable.createTable(data.get(i));

            SummaryTable summaryTable = new SummaryTable();
            summaryTable.createTable(data.get(i));
            summaryTable.setVisible(false);

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.add(rawTable);
            contentPanel.add(summaryTable);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JButton button = new JButton("Switch");
            panel.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentState.switchTable(rawTable, summaryTable);
                }
            });
            panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), data.get(i).region,
                    TitledBorder.LEFT, TitledBorder.TOP));
            panel.add(contentPanel);

            regionPanel.add(panel);
            scrollPanel.add(panel);
        }
        frame.revalidate();
        frame.repaint();
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public State getRawDataState() {
        return new RawDataState(this);
    }

    public State getSummaryDataState() {
        return new SummaryDataState(this);
    }

}