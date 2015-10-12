package superponystrikeforce.remember;

import java.util.ArrayList;

public class AlarmCollection {
    public String AlarmDate;
    public String AlarmTime;


    public static ArrayList<AlarmCollection> alarm_collection_arr = new ArrayList<>();

    public AlarmCollection(String date, String time) {

        this.AlarmDate = date;
        this.AlarmTime = time;

    }

}