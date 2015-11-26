package sun.bob.mcalendar.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;

import sun.bob.mcalendar.App;
import sun.bob.mcalendar.R;
import sun.bob.mcalendar.beans.TaskBean;
import sun.bob.mcalendar.content.CalendarResolver;
import sun.bob.mcalendar.fragments.FragmentCalendar;
import sun.bob.mcalendar.fragments.FragmentReceiptList;
import sun.bob.mcalendar.fragments.FragmentSetting;
import sun.bob.mcalendar.fragments.FragmentStatistics;
import sun.bob.mcalendar.fragments.FragmentTask;
import sun.bob.mcalendarview.utils.CurrentCalendar;
import sun.bob.mcalendarview.vo.MarkedDates;
import sun.bob.tabbar.OnTabClickedListener;
import sun.bob.tabbar.TabBar;

public class MainActivity extends BaseCompatActivity {

    private Fragment currentFragment;
    private ArrayList<Fragment> fragments;
    public Toolbar toolbar;
    private FloatingActionButton fab;

    private FragmentCalendar fragmentCalendar;
    private FragmentTask fragmentTask;
    private FragmentSetting fragmentSetting;
    private FragmentStatistics fragmentStatistics;
    private FragmentReceiptList fragmentReceiptList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (App.appContext == null) {
            App.appContext = getApplicationContext();
        }

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(new StringBuilder().append("").append(CurrentCalendar.getCurrentDateData().getYear()).append(" - ").append(CurrentCalendar.getCurrentDateData().getMonth()).toString());

        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newTaskIntent = new Intent(MainActivity.this, TaskActivity.class);
                newTaskIntent.putExtra("year", CurrentCalendar.getCurrentDateData().getYear());
                newTaskIntent.putExtra("month", CurrentCalendar.getCurrentDateData().getMonth());
                newTaskIntent.putExtra("day", CurrentCalendar.getCurrentDateData().getDay());
                startActivity(newTaskIntent);
            }
        });
        initUI();
        populateCalendar();
    }

    @Override
    public void initUI() {
        ((TabBar) findViewById(R.id.id_tabbar))
                .addTab(R.drawable.calendar, R.drawable.calendar_filled, "Calendar", Color.GRAY, Color.rgb(34, 145, 255))
                .addTab(R.drawable.checklist, R.drawable.checklist_filled, "List", Color.GRAY, Color.rgb(34, 145, 255))
                .addTab(R.drawable.stat, R.drawable.stat_filled, "Stat", Color.GRAY, Color.rgb(34, 145, 255))
                .addTab(R.drawable.settings, R.drawable.settings_filled, "Settings", Color.GRAY, Color.rgb(34, 145, 255))
                .setOnTabClickedListener(new OnTabClickedAction());

        fragmentCalendar = new FragmentCalendar();
        fragmentReceiptList = new FragmentReceiptList();
        fragmentTask = new FragmentTask();
        fragmentSetting = new FragmentSetting();
        fragmentStatistics = new FragmentStatistics();
        fragments = new ArrayList<>();
        fragments.add(fragmentCalendar);
        fragments.add(fragmentReceiptList);
//        fragments.add(fragmentTask);
        fragments.add(fragmentStatistics);
        fragments.add(fragmentSetting);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.id_fragment_container, fragmentCalendar)
                .add(R.id.id_fragment_container, fragmentReceiptList)
                .hide(fragmentReceiptList)
                .add(R.id.id_fragment_container, fragmentStatistics)
                .hide(fragmentStatistics)
                .add(R.id.id_fragment_container, fragmentSetting)
                .hide(fragmentSetting)
                .commit();
        currentFragment = fragmentCalendar;

        changeStatusBarColor();
    }

    private void populateCalendar() {
        for (TaskBean taskBean : CalendarResolver.getStaticInstance(this).getAllEvents()) {
            MarkedDates.getInstance().add(taskBean.getStartDate());
        }
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


    public class OnTabClickedAction implements OnTabClickedListener {

        @Override
        public void onTabClicked(int index) {
            MainActivity that = MainActivity.this;
            that.getSupportFragmentManager().beginTransaction().hide(currentFragment).show(fragments.get(index)).commit();
            currentFragment = fragments.get(index);
            if (index == 2) {
                fab.setVisibility(View.GONE);
            } else {
                fab.setVisibility(View.VISIBLE);
            }
            if (index != 0) {
                findViewById(R.id.id_calendar_title).setVisibility(View.GONE);
            } else {
                findViewById(R.id.id_calendar_title).setVisibility(View.VISIBLE);
            }
        }
    }

}
