package utilities;

public class ConvertStringFacade {
	
	public static String convert(String type, String dateString) {
        if (type.equalsIgnoreCase("yearly")) {
            YearString yearString = new YearString(dateString);
            return ConvertString.convert(yearString);
        } else if (type.equalsIgnoreCase("monthly")) {
            MonthString monthString = new MonthString(dateString);
            return ConvertString.convert(monthString);
        } else {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

}
