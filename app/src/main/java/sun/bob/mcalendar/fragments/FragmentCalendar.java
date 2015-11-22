package sun.bob.mcalendar.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import sun.bob.mcalendar.R;
import sun.bob.mcalendar.activities.MainActivity;
import sun.bob.mcalendar.adapters.TaskAdapter;
import sun.bob.mcalendar.constants.ClickedBackground;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;
import sun.bob.mcalendarview.mCalendarView;
import sun.bob.mcalendarview.utils.CurrentCalendar;
import sun.bob.mcalendarview.vo.DateData;

/**
 * Created by bob.sun on 15/10/21.
 */
public class FragmentCalendar extends Fragment {

    RecyclerView recyclerView;
    mCalendarView calendarView;
    View holder;
    LinearLayout listWrapper;
    LinearLayout calendarWrapper;
    //if true? => initial state;
    //if false? => changed state;
    /**
     * if true? > initial state;
     * if false? > changed state;
     */
    boolean mState = true;
    boolean calendarIsDrawn = false;
    boolean listViewIsDrawn = false;
    int initialCalednarViewHeight = 0;
    int initialListViewHeight = 0;

    int lastChangedCalendarViewHeight = 0;
    int lastChangedListViewHeight = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View ret = inflater.inflate(R.layout.fragment_calendar, null);
        calendarWrapper = (LinearLayout)ret.findViewById(R.id.calendar_wrapper);
        listWrapper = (LinearLayout)ret.findViewById(R.id.list_wrapper);
        listWrapper.setVerticalScrollBarEnabled(true);
        calendarView =
                ((mCalendarView) ret.findViewById(R.id.id_mcalendarview))
                .hasTitle(false)
                .setMarkedStyle(MarkStyle.DOT, Color.rgb(63, 81, 181))
                .setOnMonthChangeListener(new MonthChangeListener())
                .setOnDateClickListener(new DateClickListener());

        recyclerView = (RecyclerView) ret.findViewById(R.id.id_calendar_task);
        recyclerView.setAdapter(new TaskAdapter(getActivity(), CurrentCalendar.getCurrentDateData()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listWrapper.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(!listViewIsDrawn) {
                    listViewIsDrawn = true;
                    initialListViewHeight = listWrapper.getHeight();
                }
            }
        });
        calendarWrapper.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout() {
                if(!calendarIsDrawn) {
                    calendarIsDrawn = true;
                    initialCalednarViewHeight = calendarWrapper.getHeight();
                }
            }
        });


        holder = ret.findViewById(R.id.holder);
        holder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN :
                        if(mState) {
                            final ResizeAnimation resizeAnim1 = new ResizeAnimation(listWrapper);
                            resizeAnim1.setDuration((int)(initialListViewHeight / v.getContext().getResources().getDisplayMetrics().density));
                            resizeAnim1.setParams(initialListViewHeight, 700);

                            final ResizeAnimation resizeAnim2 = new ResizeAnimation(calendarWrapper);
                            resizeAnim2.setDuration((int)(initialCalednarViewHeight / v.getContext().getResources().getDisplayMetrics().density));
                            resizeAnim2.setParams(initialCalednarViewHeight, 150);

                            listWrapper.startAnimation(resizeAnim1);
                            calendarWrapper.startAnimation(resizeAnim2);

                        } else {
                            final ResizeAnimation resizeAnim1 = new ResizeAnimation(listWrapper);
                            resizeAnim1.setDuration((int)(initialListViewHeight / v.getContext().getResources().getDisplayMetrics().density));
                            resizeAnim1.setParams(700, initialListViewHeight);

                            final ResizeAnimation resizeAnim2 = new ResizeAnimation(calendarWrapper);
                            resizeAnim2.setDuration((int)(initialCalednarViewHeight / v.getContext().getResources().getDisplayMetrics().density));
                            resizeAnim2.setParams(150, initialCalednarViewHeight);

                            listWrapper.startAnimation(resizeAnim1);
                            calendarWrapper.startAnimation(resizeAnim2);
                        }
                        setCurrentState(mState ? false : true);
                        break;
                }
                return true;
            }
        });
        return ret;
    }

    class MonthChangeListener extends OnMonthChangeListener {

        @Override
        public void onMonthChange(int year, int month) {
            ((MainActivity) getActivity()).toolbar.setTitle(new StringBuilder().append("").append(year).append(" - ").append(month).toString());
        }
    }
    class DateClickListener extends OnDateClickListener{

        private View lastClicked;
        private DateData lastClickedDate;

        @Override
        public void onDateClick(View view, DateData date) {
            if (lastClicked != null){
                if (lastClickedDate.equals(CurrentCalendar.getCurrentDateData())){
                    lastClicked.setBackground(MarkStyle.todayBackground);
                } else {
                    lastClicked.setBackground(null);
                }
            }
            view.setBackground(ClickedBackground.background);
            lastClicked = view;
            lastClickedDate = date;
            ((TaskAdapter) recyclerView.getAdapter()).changeDate(date);
        }
    }
    private void setCurrentState(boolean state) {
        mState = state;
    }
    public class ResizeAnimation extends Animation {

        private int startHeight;
        private int deltaHeight; // distance between start and end height
        private View view;
        public ResizeAnimation(int startHeight, int endHeight, View view) {
            this.startHeight = startHeight;
            this.deltaHeight = endHeight - this.startHeight;
        }
        public ResizeAnimation (View v) {
            this.view = v;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            view.getLayoutParams().height = (int) (startHeight + deltaHeight * interpolatedTime);
            view.requestLayout();
        }

        public void setParams(int start, int end) {
            this.startHeight = start;
            deltaHeight = end - startHeight;
        }

        @Override
        public void setDuration(long durationMillis) {
            super.setDuration(durationMillis);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }
}
