package wekaMethods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.timeseries.*;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import dataLoading.*;

public class WekaTimeSeriesForecasting implements WekaMethods{
	
	public void buildMethod(DataForRegion datas, int months) throws Exception {
	    // Create the attribute list
	    ArrayList<Attribute> attributes = new ArrayList<>();
	    attributes.add(new Attribute("Timestamp"));
	    attributes.add(new Attribute("Value"));
	    
	    Vector<String> values = datas.values;
	    Vector<String> dates = datas.dates;
	    
	    Instances data = new Instances("time_series", attributes, values.size());

	 // Convert the date strings to timestamps
	 List<Long> timestamps = new ArrayList<>();
	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 HashSet<String> uniqueDates = new HashSet<>(); // To remove duplicates
	 for (int i = 0; i < dates.size(); i++) {
	     String dateString = dates.get(i);
	     if (uniqueDates.contains(dateString)) continue; // Skip duplicates
	     uniqueDates.add(dateString);
	     Date date = dateFormat.parse(dateString);
	     long timestamp = date.getTime();
	     timestamps.add(timestamp);

	     // Add the instance to the dataset
	     Instance instance = new DenseInstance(attributes.size());
	     instance.setValue(attributes.get(0), timestamp);
	     instance.setValue(attributes.get(1), Double.parseDouble(values.get(i)));
	     data.add(instance);
	 }

	 // Sort the instances by timestamp
	 data.sort(0);

	    // Set the class attribute index
	    data.setClassIndex(data.numAttributes() - 1);

	    // Convert the time series data to the required format
	    WekaForecaster forecaster = new WekaForecaster();
	    forecaster.setFieldsToForecast("Value");
	    forecaster.setBaseForecaster(new SMOreg());
	    forecaster.getTSLagMaker().setTimeStampField("Timestamp");
	    forecaster.getTSLagMaker().setMinLag(1);
	    forecaster.getTSLagMaker().setMaxLag(12);
	    forecaster.getTSLagMaker().setAddMonthOfYear(true);
	    forecaster.getTSLagMaker().setAddQuarterOfYear(false);

	    // Build the forecaster
	    forecaster.buildForecaster(data, System.out);

	    // Set the number of months to forecast
	    int numMonthsToForecast = months;

	    // Generate the forecast
	    forecaster.primeForecaster(data);
	    List<List<NumericPrediction>> forecast = forecaster.forecast(numMonthsToForecast, System.out);

	    // Print the forecast
	    for (int i = 0; i < numMonthsToForecast; i++) {
	        List<NumericPrediction> predsAtStep = forecast.get(i);
	        NumericPrediction predForTarget = predsAtStep.get(0);
	        values.add(String.valueOf(predForTarget.predicted()));
	        int dateMonth = Integer.parseInt(dates.get(dates.size()-1).substring(5, 7));
	        dateMonth = dateMonth % 12;
	        String dateYear = dates.get(dates.size()-1).substring(0, 4);
	        String dateString = dateYear + "-";
	        if(dateMonth < 10) {
	        	dateString += "0";
	        }
	        dateString += (dateMonth + 1) + "-01";
	        dates.add(dateString);
	    }
	}

}
