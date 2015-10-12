package superponystrikeforce.remember;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Calendar;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ListViewLoader extends Activity {

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_main);

        Button b = (Button) findViewById(R.id.btn_addAlarmEvent);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ListViewLoader.this, AlarmPopup.class), 1);
            }
        });

        populateListView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 2) {
            Bundle bundle = data.getExtras();
            String AlarmDate = bundle.getString("AD"); // 2015-10-13
            String TimeEvent = bundle.getString("TE"); // 22:10
            AlarmCollection.alarm_collection_arr.add(new AlarmCollection(AlarmDate, TimeEvent));

            Calendar AlarmTime = Calendar.getInstance();

            String[] DateSplitter;
            assert AlarmDate != null;
            DateSplitter = AlarmDate.split("-");

            int Year = Integer.parseInt(DateSplitter[0]);
            int Month = Integer.parseInt(DateSplitter[1]);
            int Day = Integer.parseInt(DateSplitter[2]);

            String[] TimeSplitter;
            assert TimeEvent != null;
            TimeSplitter = TimeEvent.split(":");

            int hour = Integer.parseInt(TimeSplitter[0]);
            int minute = Integer.parseInt(TimeSplitter[1]);

            AlarmTime.set(Year, Month, Day, hour, minute);

            long current = System.currentTimeMillis() - (System.currentTimeMillis()%1000);
            long settime = AlarmTime.getTimeInMillis();

            long time = settime - current;
            //time = time %100;
            long a = 2678400000l;
            time = time - a;
            time = time/1000;
            long longTimer = time;

            Thread thread = null;


            timing(longTimer);
            populateListView();

        }
    }

    private void populateListView() {
        adapter = new AndroidAlarmListAdapter(ListViewLoader.this, R.layout.alarm_list, AlarmCollection.alarm_collection_arr);
        ListView listView = (ListView) findViewById(R.id.lv_alarm);
        listView.setAdapter(adapter);
    }

    private void timing(long t) {
        setContentView(R.layout.activity_alarm_main);
        Calendar calendar = Calendar.getInstance();
        int timers = (int) t;
        calendar.add(Calendar.SECOND, timers);
        Intent intent = new Intent(ListViewLoader.this, AlarmReceiverActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ListViewLoader.this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

}
