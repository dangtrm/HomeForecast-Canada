package dataLoading;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import visualizations.*;

public class DataLoadingAdapter {
    private DataLoading dataLoading;

    public DataLoadingAdapter(DataLoading dataLoading) {
        this.dataLoading = dataLoading;
    }

    public void readDataBase() throws Exception {
        dataLoading.readDataBase();
    }

    public Vector<String> getCountries() throws SQLException {
        return dataLoading.getCountries();
    }

    public void putData() {
        dataLoading.putData();
    }

    public ArrayList<DataForRegion> getData() {
        return dataLoading.getData();
    }

    public Vector<String> getValues(String region, String startDate, String endDate) throws SQLException {
        return dataLoading.getValues(region, startDate, endDate);
    }

    public Vector<String> getDates(String region, String startDate, String endDate) throws SQLException {
        return dataLoading.getDates(region, startDate, endDate);
    }

    public void addToTable() {
        dataLoading.addToTable();
    }

    public void setVisualization(Visualization setVis) {
        dataLoading.setVisualization(setVis);
    }

    public void addToVisualization() {
        dataLoading.addToVisualization();
    }
}
