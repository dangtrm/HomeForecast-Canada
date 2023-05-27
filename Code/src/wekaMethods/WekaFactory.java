package wekaMethods;
import dataLoading.*;


public class WekaFactory {
	public WekaMethods wekaMethods;
	public void buildMethod(DataForRegion dataRegion, int numMonths, String method) throws Exception {
		if(method == "Prediction") {
			wekaMethods = new WekaTimeSeriesPrediction();
			wekaMethods.buildMethod(dataRegion, numMonths);
		}
		else {
			wekaMethods = new WekaTimeSeriesForecasting();
			wekaMethods.buildMethod(dataRegion, numMonths);
		}
		
	}
	
	

}
