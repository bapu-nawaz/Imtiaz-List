package gks.imtiazlist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nawaz on 09/01/2016.
 */
public class Adapter extends ArrayAdapter<String>{

    private Activity me;
    private ArrayList<String> Name, Desc;
    private ArrayList<Integer> Check;

    private TextView name, desc;
    private CheckBox check;
    private View rowView;

    public Adapter(Activity context, ArrayList<String> name, ArrayList<String> desc, ArrayList<Integer> check) {
        super(context, R.layout.single_item_view, name);
        me = context;
        Name = name;
        Desc = desc;
        Check = check;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator = me.getLayoutInflater();
        rowView = inflator.inflate(R.layout.single_item_view, parent, true);

        init();
        setValues(position);

        return rowView;
    }

    private void init() {
        name = (TextView)rowView.findViewById(R.id.name);
        desc = (TextView)rowView.findViewById(R.id.desc);
        check = (CheckBox)rowView.findViewById(R.id.check);
    }

    private void setValues(int index) {
        name.setText(Name.get(index));
        desc.setText(Desc.get(index));
        check.setChecked( (Check.get(index) > 0) );
    }
}
