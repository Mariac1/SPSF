package superponystrikeforce.remember;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Diary extends AppCompatActivity {

    EditText editText;
    TextView CD;
    int currentWorkingFile;
    int previousWorkingFile;
    int nextWorkingFile;
    String CurrentDate = "";
    String prevDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        int nr = NrOfFiles();

        CD = (TextView) findViewById(R.id.textViewDate);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
        CD.setText(format.format(calendar.getTime()));
        prevDate = CurrentDate;
        CurrentDate = format.format(calendar.getTime());
        editText = (EditText) findViewById(R.id.editText1);

        String currentFile = Integer.toString(nr) + "DiaryStorage.txt";

        readMessage(currentFile);
    }

    public int NrOfFiles(){
        String fileName = "NrOfDiaryFiles.txt";
        File file = new File(fileName);

        if (file.exists()){
            String message = "0";
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = openFileInput(fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                while ((message = bufferedReader.readLine()) != null)
                {
                    stringBuffer.append(message).append("\n");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Integer.parseInt(message);
        }
        return 0;
    }

    public void writeToFile(View view){
        String Message2 = editText.getText().toString();
        String fileName = "DiaryStorage.txt";
        String fileName2 = "NrOfDiaryFiles.txt";
        try {
            String message;
            //get nr of the NrOfDiaryFiles.txt
            FileInputStream fileInputStream = openFileInput(fileName2);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while ((message = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(message);
            }

            //add nr.
            FileOutputStream fileOutputStream2 = openFileOutput(fileName2, MODE_PRIVATE);
            int a = Integer.parseInt(stringBuffer.toString());
            fileName = Integer.toString(a) + fileName; //add nr to file name

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
            if (!prevDate.equals(format.format(calendar.getTime()))){
                a++;
            }
            fileName = Integer.toString(a) + "DiaryStorage.txt";
            fileOutputStream2.write(a);
            fileOutputStream2.close();

            //write to the new diary file
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            fileOutputStream.write(Message2.getBytes());
            fileOutputStream.close();
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readMessage(String CurrentFile){
        String fileName = CurrentFile;
        try {
            String message;
            FileInputStream fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while ((message = bufferedReader.readLine()) != null) {
                stringBuffer.append(message).append("\n");
            }
            editText.setText(stringBuffer.toString());
            editText.setVisibility(View.VISIBLE);
            return message;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void PreviousDiaryEntry(View view){
        currentWorkingFile = NrOfFiles();
        previousWorkingFile = currentWorkingFile - 1;
        nextWorkingFile = currentWorkingFile + 1;

        File file = new File(Integer.toString(nextWorkingFile) + "DiaryStorage.txt");
        if (file.exists())
            nextWorkingFile += 1;
        else
            nextWorkingFile = currentWorkingFile;

        if (currentWorkingFile != 0) {
            previousWorkingFile -= 1;
            editText.setText(readMessage(Integer.toString(NrOfFiles()) + "DiaryStorage.txt"));
        }
        else
            previousWorkingFile = currentWorkingFile;
    }

    public void NextDiaryEntry(View view){
        currentWorkingFile = NrOfFiles();
        previousWorkingFile = currentWorkingFile - 1;
        nextWorkingFile = currentWorkingFile + 1;

        File file = new File(Integer.toString(nextWorkingFile) + "DiaryStorage.txt");
        if (file.exists()) {
            nextWorkingFile += 1;
            editText.setText(readMessage(Integer.toString(NrOfFiles()) + "DiaryStorage.txt"));
        }
        else
            nextWorkingFile = currentWorkingFile;

        if (currentWorkingFile != 0)
            previousWorkingFile -= 1;
        else
            previousWorkingFile = currentWorkingFile;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

