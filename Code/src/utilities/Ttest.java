package utilities;
import org.apache.commons.math3.stat.inference.TTest;

import dataLoading.*;

public class Ttest {

	public static String calculateTTest(DataForRegion data1, DataForRegion data2) {
        double[] test1 = new double[data1.values.size()];
        double[] test2 = new double[data2.values.size()];
        add(test1, data1);
        add(test2, data2);
        TTest tt = new TTest();
        String res = "p-value is " + tt.tTest(test1, test2) + ".";
        res += (tt.tTest(test1, test2, 0.5)) ? " We can reject the null hypothesis" : " We cannot reject the null hypothesis";
        return res;
    }
	
	private static void add(double[] data, DataForRegion d) {
		for(int i = 0; i < d.values.size(); i++) {
			data[i] = Double.parseDouble(d.values.get(i));
		}
	}

}
