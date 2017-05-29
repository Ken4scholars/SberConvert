package ru.sberbank.sberconvert.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map.Entry;

import ru.sberbank.sberconvert.R;

/**
 * Created by kenneth on 28.05.17.
 */

public class TupleArrayAdapter extends ArrayAdapter<Entry> {

    private Context context;
    private List<Entry> data;
    private Resources res;
    private LayoutInflater inflater;

    public TupleArrayAdapter(Context context, int resourceId, List<Entry> data) {
        super(context, resourceId, data);
        this.context = context;
        this.res = context.getResources();
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = inflater.inflate(R.layout.spinner_row, parent, false);
        Entry entry = data.get(position);

        TextView key = (TextView) view.findViewById(R.id.spinner_text);
        key.setText(entry.getKey().toString());

        return key;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_dropdown_row, parent, false);
        Entry entry = data.get(position);

        TextView value = (TextView) row.findViewById(R.id.entry_value);
        TextView key = (TextView) row.findViewById(R.id.entry_key);
        value.setText(entry.getValue().toString());
        key.setText(entry.getKey().toString());
        if(position%2 == 1){
            row.setBackgroundColor(res.getColor(R.color.colorIcons));
        }else {
            row.setBackgroundColor(res.getColor(R.color.colorPrimaryLight));
        }
        return row;
    }

}
