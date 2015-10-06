package superponystrikeforce.remember;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ListViewActivity extends Activity implements OnClickListener {

    private ListView lv_android;
    private AndroidListAdapter list_adapter;
    private Button btn_calender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        getWidget();
        Button b = (Button) findViewById(R.id.btn_addEvent);
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ListViewActivity.this, Popup.class), 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 2) {
            Bundle bundle = data.getExtras();
            String EventDate = bundle.getString("date");
            String EventName = bundle.getString("name");
            CalendarCollection.date_collection_arr.add(new CalendarCollection(EventDate, EventName));
            getWidget();
        }
    }


    public void getWidget() {
        btn_calender = (Button) findViewById(R.id.btn_calender);
        btn_calender.setOnClickListener(this);

        lv_android = (ListView) findViewById(R.id.lv_android);
        list_adapter = new AndroidListAdapter(ListViewActivity.this, R.layout.list_item, CalendarCollection.date_collection_arr);
        lv_android.setAdapter(list_adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_calender:
                startActivity(new Intent(ListViewActivity.this, CalenderActivity.class));

                break;

            default:
                break;
        }

    }
}