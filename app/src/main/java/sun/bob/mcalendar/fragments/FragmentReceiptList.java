package sun.bob.mcalendar.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import sun.bob.mcalendar.R;
import sun.bob.mcalendar.activities.TaskActivity;
import sun.bob.mcalendar.adapters.ExpandableReceiptsAdapter;
import sun.bob.mcalendar.beans.MonthBean;
import sun.bob.mcalendar.beans.TaskBean;
import sun.bob.mcalendarview.utils.CurrentCalendar;

/**
 * Created by oong on 2015-11-26.
 */
public class FragmentReceiptList extends Fragment {

    private ExpandableListView exListView;
    private ExpandableReceiptsAdapter exReceiptAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View ret = inflater.inflate(R.layout.fragment_list_view, null);
        exListView = (ExpandableListView)ret.findViewById(R.id.expandable_list_view);
        Spinner yearSpinner = createYearSpinner(ret);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentYear = ((TextView) view).getText().toString();
                ExpandableReceiptsAdapter exReceiptAdapter = new ExpandableReceiptsAdapter(Integer.parseInt(currentYear), getActivity().getApplicationContext());
                exListView.setAdapter(exReceiptAdapter);
                exListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        Log.d("OONG", "OnChildClickListener");
                        TaskBean taskBean = ((MonthBean) parent.getExpandableListAdapter().getGroup(groupPosition)).getTaskList().get(childPosition);
                        Intent newTaskIntent = new Intent(v.getContext(), TaskActivity.class);
                        newTaskIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        newTaskIntent.putExtra("year", CurrentCalendar.getCurrentDateData().getYear());
                        newTaskIntent.putExtra("month", CurrentCalendar.getCurrentDateData().getMonth());
                        newTaskIntent.putExtra("day", CurrentCalendar.getCurrentDateData().getDay());
                        newTaskIntent.putExtra("taskId", Long.parseLong(taskBean.getId()));
                        newTaskIntent.putExtra("edit", true);
                        v.getContext().startActivity(newTaskIntent);
                        return true;
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return ret;
    }

    private Spinner createYearSpinner(View inflatedView) {
        if(inflatedView == null) {
            return null;
        } else {
            ArrayList<String> years = new ArrayList<String>();
            int thisYear = Calendar.getInstance().get(Calendar.YEAR);
            for (int i = 1970; i <= thisYear; i++) {
                years.add(Integer.toString(i));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflatedView.getContext(), android.R.layout.simple_expandable_list_item_1, years);
            Spinner spinYear = (Spinner)inflatedView.findViewById(R.id.year_spinner);
            spinYear.setAdapter(adapter);
            spinYear.setSelection(years.size()-1);
            return spinYear;
        }
    }
}
