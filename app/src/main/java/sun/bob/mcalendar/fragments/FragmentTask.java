package sun.bob.mcalendar.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import sun.bob.mcalendar.R;
import sun.bob.mcalendar.adapters.DayAdapter;
import sun.bob.mcalendar.content.CalendarResolver;

/**
 * Created by bob.sun on 15/10/21.
 */
public class FragmentTask extends Fragment {
//    TaskAdapter taskAdapter;
    DayAdapter dayAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View ret = inflater.inflate(R.layout.fragment_task, null);
        Spinner yearSpinner = createYearSpinner(ret);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("OONG", ((TextView)view).getText()+"");
                //send a message to Handler that is contains selected yaer
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RecyclerView recyclerView = (RecyclerView) ret.findViewById(R.id.id_task_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
//        if (taskAdapter == null)
//            taskAdapter = new TaskAdapter(this.getActivity());
//        recyclerView.setAdapter(taskAdapter);
        if (dayAdapter == null){
            CalendarResolver.getStaticInstance(getActivity()).refreshAllEvents();
            dayAdapter = new DayAdapter(getActivity());

        }
        Log.d("OONG", dayAdapter.toString());
        recyclerView.setAdapter(dayAdapter);
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
