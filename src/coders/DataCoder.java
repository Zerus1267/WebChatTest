package coders;

import java.util.HashMap;

public class DataCoder {
    private static HashMap<String,String> months = new HashMap<String, String>();

    public DataCoder() {
        months.put("янв", "January");
        months.put("фев", "February");
        months.put("мар", "March");
        months.put("апр", "April");
        months.put("мая", "May");
        months.put("июн", "June");
        months.put("июл", "Jule");
        months.put("авг", "August");
        months.put("сен", "September");
        months.put("окт", "October");
        months.put("ноя", "November");
        months.put("дек", "December");
    }

    public String getMonth(String date){
        String month = date.substring(5,8);
        String DayTime = date.substring(12,17);
        String DayNum = date.substring(9,11);
        System.out.println("Test msg DayNum: " + DayNum);
        System.out.println(month);
        return DayTime + " AM | " + months.get(month) + " " + DayNum;
    }


}
