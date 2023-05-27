package dataLoading;

import java.util.Vector;

public class DataForRegion {
	public String region;
	public Vector<String> values;
	public Vector<String> dates;
	
	public DataForRegion() {
		values = new Vector<String>();
		dates = new Vector<String>();
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	
	public void setValues(Vector<String> vals) {
		this.values = vals;
	}
	
	public void setDates(Vector<String> date) {
		this.dates = date;
	}
	
	public String toString() {
		return region + " from " + dates.get(0) + " to " + dates.get(dates.size() - 1);
	}
}
