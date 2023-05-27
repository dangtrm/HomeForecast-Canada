package utilities;

class ConvertString {
    public static String convert(YearString yearString) {
        // Perform the conversion logic for year string
        return yearString.getYear().substring(0, 4); // Return first 4 characters
    }

    public static String convert(MonthString monthString) {
        // Perform the conversion logic for month string
        return monthString.getMonth().substring(5, 7);
    }
}
