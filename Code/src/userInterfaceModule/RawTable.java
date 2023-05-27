package userInterfaceModule;
import javax.swing.JScrollPane;
import dataLoading.*;

import javax.swing.JTable;

public class RawTable extends JScrollPane{
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void createTable(DataForRegion dataRegion) {
        String[] colName = new String[] { "Dates", "Values" };
        Object[][] products = new Object[dataRegion.values.size()][2];
        constructData(dataRegion, products);
        JTable table = new JTable(products, colName);
        setViewportView(table);
    }
    
    private void constructData(DataForRegion dataRegion, Object[][] products) {
        for (int i = 0; i < dataRegion.values.size(); i++) {
            for (int j = 0; j < 2; j++) {
                if (j == 0) {
                    products[i][j] = dataRegion.dates.get(i);
                } else {
                    products[i][j] = dataRegion.values.get(i);
                }
            }
        }
    }
}
