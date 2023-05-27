package dataLoading;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import userInterfaceModule.*;
import visualizations.*;
public class DataLoading {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private ArrayList<DataForRegion> datas = new ArrayList<>();
    private Table table;
    private Visualization visualization;
    private DataForRegion dataForRegion;
    private DataLoadingAdapter dataLoadingAdapter;
    
    public DataLoading() throws Exception {
    	
    	 Class.forName("com.mysql.cj.jdbc.Driver");
         // Setup the connection with the DB
         connect = DriverManager
                 .getConnection("jdbc:mysql://localhost:3306/database_3311", "root", "Ai1130611!"); //third column for password
//         visualization = new Visualization();
         dataLoadingAdapter = new DataLoadingAdapter(this);

    }

    public void readDataBase() throws Exception {
        try {
            // Statements allow to issue SQL queries to the database
            Statement stmnt = connect.createStatement();
            stmnt
                    .execute(
                    		" LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/data_values.csv' "
                    		+ " INTO TABLE NHPI "
                    		+ " FIELDS TERMINATED BY ',' "
                    		+ " ENCLOSED BY '\"'"
                    		+ " LINES TERMINATED BY '\r\n'"
                    		+ " IGNORE 1 ROWS;");

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }
    
    public Vector<String> getCountries() throws SQLException{
    	Statement stm = connect.createStatement();
        String sql = "select distinct GEO from nhpi";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        Vector<String> countries = new Vector<String>();
        while (rst.next()) {
            countries.add(rst.getString(1));
        }
        return countries;
    }
    
    public void putData() {
    	datas.add(dataForRegion);
    }
    
    public ArrayList<DataForRegion> getData() {
    	return datas;
    }

	public Vector<String> getValues(String region, String startDate, String endDate) throws SQLException {
		Statement stm = connect.createStatement();
        String sql = "select VALUE_ from nhpi where geo = '" + region + "' AND REF_DATE >= '" + startDate + "' AND REF_DATE <= '" + endDate + "'";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        Vector<String> values = new Vector<String>();
        while (rst.next()) {
        	values.add(rst.getString(1));
        }
        dataForRegion = new DataForRegion();
        dataForRegion.setRegion(region);
        dataForRegion.setValues(values);
        
        table = new Table();
        getDates(region, startDate, endDate);
        return values;
		
	}
	public Vector<String> getDates(String region, String startDate, String endDate) throws SQLException {
		Statement stm = connect.createStatement();
        String sql = "select REF_DATE from nhpi where geo = '" + region + "' AND REF_DATE >= '" + startDate + "' AND REF_DATE <= '" + endDate + "'";
        ResultSet rst;
        rst = stm.executeQuery(sql);
        Vector<String> dates = new Vector<String>();
        while (rst.next()) {
        	dates.add(rst.getString(1));
        }
        dataForRegion.setDates(dates);
//        visualization.addDataForRegion(dataForRegion);
        return dates;
	}

	// You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
    
    public void addToTable() {
    	table.display(dataLoadingAdapter);
    }
    
    public void setVisualization(Visualization setVis) {
    	visualization = setVis;
    }
    
    public void addToVisualization() {
    	visualization.addDataForRegion(dataForRegion);
    }
    
    public static void main(String[] args) throws Exception {
    	//only use this once to store data into MySQL
		DataLoading d = new DataLoading();
		d.readDataBase();
	}


}