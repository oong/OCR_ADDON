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
 * Created by bob.sun on 15/10/21.
 */
public class FragmentSetting extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        Button testPageButton;
        //test_page_button
        View view = inflater.inflate(R.layout.fragment_setting, null);
        testPageButton = (Button)view.findViewById(R.id.test_page_button);
        testPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), TestActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
