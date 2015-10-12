package superponystrikeforce.remember;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AndroidAlarmListAdapter extends ArrayAdapter<AlarmCollection> {

    private final Context context;
    private final ArrayList<AlarmCollection> values;
    private ViewHolder viewHolder;
    private final int resourceId;

    public AndroidAlarmListAdapter(Context context, int resourceId, ArrayList<AlarmCollection> values) {
        super(context, resourceId, values);
        this.context = context;
        this.values = values;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_AlarmDate = (TextView) convertView.findViewById(R.id.tv_AlarmDate);
            viewHolder.tv_AlarmTime = (TextView) convertView.findViewById(R.id.tv_AlarmTime);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        AlarmCollection list_obj = values.get(position);
        viewHolder.tv_AlarmDate.setText(list_obj.AlarmDate);
        viewHolder.tv_AlarmTime.setText(list_obj.AlarmTime);
        return convertView;
    }

    public class ViewHolder {
        TextView tv_AlarmDate;
        TextView tv_AlarmTime;
    }
}