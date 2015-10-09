package superponystrikeforce.remember;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
    //File file = new File("CalendarList.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        //getList();
        getWidget();
        Button b = (Button) findViewById(R.id.btn_addEvent);
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ListViewActivity.this, Popup.class), 1);
            }
        });
    }

    private void getList() {
        /*if (file.exists()) {
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
                StringTokenizer tokens = new StringTokenizer(stringBuffer.toString(), "\n");
                ArrayList<String> name = new ArrayList<>();
                ArrayList<String> date = new ArrayList<>();
                while (tokens.nextToken() != "") {
                    date.add(tokens.nextToken());
                    name.add(tokens.nextToken());
                }
                System.out.println(date);
                System.out.println(name);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            String fileName = "CalendarList.txt";
            try {
                //write to the new diary file
                FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
                fileOutputStream.write(" ".getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 2) {
            Bundle bundle = data.getExtras();
            String EventDate = bundle.getString("date");
            String EventName = bundle.getString("name");
            CalendarCollection.date_collection_arr.add(new CalendarCollection(EventDate, EventName));
            //writeToCalendar(EventDate, EventName);
            getWidget();
        }
    }


    public void getWidget() {
        btn_calender = (Button) findViewById(R.id.btn_calender);
        btn_calender.setOnClickListener(this);

        lv_android = (ListView) findViewById(R.id.lv_android);
        lv_android.setAdapter(list_adapter);
        list_adapter = new AndroidListAdapter(ListViewActivity.this, R.layout.list_item, CalendarCollection.date_collection_arr);
    }

    private void writeToCalendar(String Date, String Name) {
        String fileName = "CalendarList.txt";
        String string = Date + "\n" + Name;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            fileOutputStream.write(string.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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