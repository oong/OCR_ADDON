package sun.bob.mcalendar.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sun.bob.mcalendar.R;
import sun.bob.mcalendar.activities.TestActivity;

/**
 * Created by oong on 2015-11-23.
 */
public class FragmentStatistics extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_statistics, null);
        return view;
    }

}
