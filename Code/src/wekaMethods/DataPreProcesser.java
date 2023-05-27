package wekaMethods;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.TimeSeriesDelta;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataPreProcesser {

    public List<Long> convertDatesToTimestamps(List<String> dates) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Long> timestamps = new ArrayList<>();

        for (String dateString : dates) {
            Date date = dateFormat.parse(dateString);
            long timestamp = date.getTime();
            timestamps.add(timestamp);
        }
        return timestamps;
    }

    public Instances[] filterAndSplitData(Instances data, int months) throws Exception {
        // Convert the time series data to the required format
        Filter filter = new TimeSeriesDelta();
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

        return new Instances[]{filteredTrainData, filteredTestData};
    }
}

