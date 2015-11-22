package sun.bob.mcalendar.activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;

import sun.bob.mcalendar.R;
import sun.bob.mcalendar.fragments.FragmentAddNutrient;
import sun.bob.mcalendar.fragments.FragmentNutrientGraph;
import sun.bob.mcalendar.fragments.FragmentNutrientSettings;
import sun.bob.tabbar.OnTabClickedListener;
import sun.bob.tabbar.TabBar;

public class NutrientMainActivity extends BaseCompatActivity {
    TabBar tabbar;
    private Fragment currentFragment;
    private ArrayList<Fragment> fragments;

    private FragmentAddNutrient cameraFragment;
    private FragmentNutrientGraph graphFragment;
    private FragmentNutrientSettings settingFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient_main);
        initUI();
    }
    public class OnTabClickedAction implements OnTabClickedListener {

        @Override
        public void onTabClicked(int index) {
            NutrientMainActivity that = NutrientMainActivity.this;
            that.getSupportFragmentManager().beginTransaction().hide(currentFragment).show(fragments.get(index)).commit();
            currentFragment = fragments.get(index);
        }
    }

    @Override
    public void initUI() {
        ((TabBar) findViewById(R.id.id_tabbar_nutrient))
                .addTab(R.drawable.tabbar_add_ic, R.drawable.tabbar_add_ic, "Calendar", Color.GRAY, Color.rgb(34, 145, 255))
                .addTab(R.drawable.tabbar_graph_ic, R.drawable.tabbar_graph_ic_filled, "Stat", Color.GRAY, Color.rgb(34, 145, 255))
                .addTab(R.drawable.settings, R.drawable.settings_filled, "Settings", Color.GRAY, Color.rgb(34, 145, 255))
                .setOnTabClickedListener(new OnTabClickedAction());

        cameraFragment = new FragmentAddNutrient();
        graphFragment = new FragmentNutrientGraph();
        settingFragment = new FragmentNutrientSettings();
        fragments = new ArrayList<>();
        fragments.add(cameraFragment);
        fragments.add(graphFragment);
        fragments.add(settingFragment);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.id_nutrient_fragment_container, cameraFragment)
                .add(R.id.id_nutrient_fragment_container, graphFragment)
                .hide(graphFragment)
                .add(R.id.id_nutrient_fragment_container, settingFragment)
                .hide(settingFragment)
                .commit();
        currentFragment = graphFragment;
        changeStatusBarColor();
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT > 18) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(false);
            tintManager.setTintColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
}
