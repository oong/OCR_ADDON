package sun.bob.mcalendar.fragments;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sun.bob.mcalendar.R;

/**
 * Created by oong on 2015-11-22.
 */
public class FragmentAddNutrient extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_nutrient, null);
        return view;
    }
}
