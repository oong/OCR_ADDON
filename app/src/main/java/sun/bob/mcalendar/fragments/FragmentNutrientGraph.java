package sun.bob.mcalendar.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sun.bob.mcalendar.R;

/**
 * Created by oong on 2015-11-22.
 */
public class FragmentNutrientGraph extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrient_graph, null);
        return view;
    }
}
