package superponystrikeforce.remember;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    File file = new File("CalendarList.txt");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        getList();
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
            int size = CalendarCollection.date_collection_arr.size();
            writeToCalendar(EventDate, EventName);
            for (int k = 0; k < size; k++) {
                CalendarCollection.date_collection_arr.remove(CalendarCollection.date_collection_arr.size() - 1);
            }
            getList();
            getWidget();
        }
    }

    private void writeToCalendar(String SDate, String SName) {
        String fileName = "CalendarList.txt";
        String string = SDate + "\n" + SName + "\n";
        FileOutputStream fileOutputStream = null;


        String message;
        StringBuffer stringBuffer = new StringBuffer();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            while ((message = bufferedReader.readLine()) != null) {
                stringBuffer.append(message).append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        }

        string = stringBuffer.toString() + string;

        try {
            fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            fileOutputStream.write(string.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getList() {
            String fileName = "CalendarList.txt";
            try {
                String message;
                FileInputStream fileInputStream = openFileInput(fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                while ((message = bufferedReader.readLine()) != null) {
                    stringBuffer.append(message).append("\n");
                }

                //String hantering
                String[] splitter;
                ArrayList<String> name = new ArrayList<>();
                ArrayList<String> date = new ArrayList<>();
                splitter = stringBuffer.toString().split("\n");
                for (int k = 0; k < splitter.length; k++)
                {
                    date.add(splitter[k]);
                    k++;
                    name.add(splitter[k]);
                }
                int size = CalendarCollection.date_collection_arr.size();
                for (int k = 0; k < size; k++) {
                    CalendarCollection.date_collection_arr.remove(CalendarCollection.date_collection_arr.size() - 1);
                }
                while ((!date.isEmpty()) && (!name.isEmpty())) {
                    CalendarCollection.date_collection_arr.add(new CalendarCollection(date.remove(date.size()-1), name.remove(name.size()-1)));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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