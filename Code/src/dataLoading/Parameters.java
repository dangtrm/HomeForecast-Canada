package dataLoading;

public class Parameters {
	public DataLoadingAdapter dataLoadingAdapter;
	public String region;
	public String startMonth;
	public String startYear;
	public String endMonth;
	public String endYear;
	public String startDate;
	public String endDate;

	public Parameters(String region, String startMonth, String startYear, String endMonth, String endYear) {
		this.region = region;
		this.startMonth = startMonth;
		this.startYear = startYear;
		this.endMonth = endMonth;
		this.endYear = endYear;
		startDate = this.startYear + "-";
		endDate = this.endYear + "-";
		if (Integer.parseInt(startMonth) < 10) {
			startDate += "0";
		}
		if(Integer.parseInt(endMonth) < 10) {
			endDate += "0";
		}
		startDate += this.startMonth + "-01";
		endDate += this.endMonth + "-01";


	}

	public void storeData() {
		try {
			boolean check = false;
			for (DataForRegion d : this.dataLoadingAdapter.getData()) {
				if (d.region.equals(region) && d.dates.get(0).equals(startDate)
						&& d.dates.get(d.dates.size() - 1).equals(endDate)) {
					check = true;
					break;
				}
			}
			if (!check) {
				this.dataLoadingAdapter.getValues(this.region, this.startDate, this.endDate);
				this.dataLoadingAdapter.putData();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendToTable() {
		this.dataLoadingAdapter.addToTable();
	}

	public void sendToVisualization() {
		this.dataLoadingAdapter.addToVisualization();
	}

	public void setDataLoading(DataLoadingAdapter dataLoadingAdapter) {
		this.dataLoadingAdapter = dataLoadingAdapter;
		
	}

}
