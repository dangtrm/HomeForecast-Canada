package wekaMethods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import weka.classifiers.evaluation.Evaluation;

import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import dataLoading.*;

public class WekaTimeSeriesPrediction implements WekaMethods{
	
	public void buildMethod(DataForRegion datas, int months) throws Exception{
	    ArrayList<Attribute> attributes = new ArrayList<>();
	    attributes.add(new Attribute("Timestamp"));
	    attributes.add(new Attribute("Value"));
	    Vector<String> values = datas.values;
	    Vector<String> dates = datas.dates;

	    // Create the dataset
	    Instances data = new Instances("time_series", attributes, values.size());
	    data.setClassIndex(data.numAttributes() - 1); // Set the class attribute index

	    // Convert the date strings to timestamps
	    List<Long> timestamps = new ArrayList<>();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    for (int i = 0; i < dates.size(); i++) {
	        String dateString = dates.get(i);
	        Date date = dateFormat.parse(dateString);
	        long timestamp = date.getTime();
	        timestamps.add(timestamp);

	        // Add the instance to the dataset
	        Instance instance = new DenseInstance(attributes.size());
	        instance.setValue(attributes.get(0), timestamp);
	        instance.setValue(attributes.get(1), Double.parseDouble(values.get(i)));
	        data.add(instance);
	    }

	    // Convert the time series data to the required format
	    Filter filter = new weka.filters.unsupervised.attribute.TimeSeriesDelta();
	    filter.setInputFormat(data);
	    Instances transformedData = Filter.useFilter(data, filter);

	    // Split the data into training and testing sets
	    int trainSize = (int) Math.round(transformedData.numInstances() * 0.8);
	    int testSize = months; // predict the next n months
	    Instances trainData = new Instances(transformedData, 0, trainSize);
	    Instances testData = new Instances(transformedData, trainSize, testSize);

	    // Set the target attribute
	    trainData.setClassIndex(trainData.numAttributes() - 1);
	    testData.setClassIndex(testData.numAttributes() - 1);

	    // Remove unwanted attributes
	    Remove remove = new Remove();
	    remove.setAttributeIndices("1"); // Remove the first attribute (timestamp)
	    remove.setInputFormat(trainData);
	    Instances filteredTrainData = Filter.useFilter(trainData, remove);
	    Instances filteredTestData = Filter.useFilter(testData, remove);

	    // Train the linear regression model
	    LinearRegression lin = new LinearRegression();
	    lin.buildClassifier(filteredTrainData);

	    // Evaluate the linear regression model
	    Evaluation eval = new Evaluation(filteredTrainData);
	    eval.evaluateModel(lin, filteredTestData);

	    // Make predictions on new data
	    Instance newInstance = filteredTestData.lastInstance();
	    double prediction = lin.classifyInstance(newInstance);

	    // Loop through and get the predicted value for each future month
	    for (int i = 1; i <= months; i++) {
	        // Create a new instance with the timestamp of the next month
	        Instance newMonthInstance = new DenseInstance(filteredTestData.numAttributes());
	        long nextMonthTimestamp = timestamps.get(timestamps.size() - 1) + (i * 2629746000L); // 1 month = 2629746000L milliseconds
	        newMonthInstance.setValue(0, nextMonthTimestamp);

	        // Use the linear regression model to predict the value for the next month
	        double nextMonthPrediction = lin.classifyInstance(newMonthInstance);
	        values.add(String.valueOf(nextMonthPrediction));
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