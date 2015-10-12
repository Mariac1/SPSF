package superponystrikeforce.remember;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ListViewLoader extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
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
        registerClickCallback();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 2) {
            Bundle bundle = data.getExtras();
            String AlarmDate = bundle.getString("AD");
            String TimeEvent = bundle.getString("TE");
            AlarmCollection.alarm_collection_arr.add(new AlarmCollection(AlarmDate,TimeEvent));
            //myItems.add();
            int longTimer = 5;
            int size =  AlarmCollection.alarm_collection_arr.size();
            for (int k = 0; k < size; k++) {
                AlarmCollection.alarm_collection_arr.remove( AlarmCollection.alarm_collection_arr.size() - 1);
            }
            timer(longTimer);
            populateListView();

        }
    }

    private void populateListView() {
        //create list of items

        // Build adapter
        ArrayAdapter<AlarmCollection> adapter = new ArrayAdapter<>(this, R.layout.alarm_list,  AlarmCollection.alarm_collection_arr); //////

        //configure the list view
        ListView listView = (ListView) findViewById(R.id.lv_alarm);
        listView.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView listView = (ListView) findViewById(R.id.lv_alarm);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                String message = "You clicked # " + position + ", which is string: " + textView.getText().toString();
                Toast.makeText(ListViewLoader.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void timer(int t){
        setContentView(R.layout.activity_alarm_main);

        //Create an offset from the current time in which the alarm will go off.
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.SECOND, t);

        //Create a new PendingIntent and add it to the AlarmManager
        Intent intent = new Intent(this, AlarmReceiverActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                12345, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am =
                (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                pendingIntent);
    }

}
