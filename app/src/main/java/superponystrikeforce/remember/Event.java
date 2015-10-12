package superponystrikeforce.remember;

import java.util.ArrayList;

public class Event {
    String EventName;
    String Date;

    public String date;
    public String event_message;

    public String getName() {
        return EventName;
    }

    public void setName(String name) {
        this.EventName = name;
    }

    public String getAddress() {
        return Date;
    }

    public void setAddress(String address) {
        this.Date = address;
    }

    public Event(String name, String address, String location) {
        super();
        this.EventName = name;
        this.Date = address;
    }

    public static ArrayList<CalendarCollection> date_collection_arr = new ArrayList<>();
    public Event(String date,String event_message){

        this.date=date;
        this.event_message=event_message;

    }
}






