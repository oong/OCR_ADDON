package sun.bob.mcalendar.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.alexvasilkov.foldablelayout.UnfoldableView;
import com.alexvasilkov.foldablelayout.shading.GlanceFoldShading;
import com.bumptech.glide.Glide;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.List;

import sun.bob.mcalendar.R;
import sun.bob.mcalendar.beans.TaskBean;
import sun.bob.mcalendar.constants.Constants;
import sun.bob.mcalendar.content.CalendarProvider;
import sun.bob.mcalendar.content.CalendarResolver;
import sun.bob.mcalendar.model.PurchaseItem;
import sun.bob.mcalendar.paint.Painting;
import sun.bob.mcalendar.utils.RecurrenceUtil;
import sun.bob.mcalendar.utils.TimeStampUtil;
import sun.bob.mcalendarview.vo.DateData;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {

    private final int PICK_START = 1;
    private final int PICK_END = 2;
    public static final int SPINNER_REPEAT = 3;
    public static final int SPINNER_REMINDER = 4;
    int year, month, day;
    public DateData dateStart, dateEnd;
    private EditText title, description;
    private Spinner repetition, reminder;
    long taskId;
    boolean edit;
    TaskBean taskBean;
    private View mListTouchInterceptor;
    private View mDetailsLayout;
    private UnfoldableView mUnfoldableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        initFolderableImageView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        year = getIntent().getIntExtra("year",0);
        month = getIntent().getIntExtra("month", 0);
        day = getIntent().getIntExtra("day", 0);

        edit = getIntent().getBooleanExtra("edit", false);
        taskId = getIntent().getLongExtra("taskId", -1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskBean taskBean = genTask();
                if (!edit){
                    if (taskBean != null){
                        // TODO: 15/11/3 Insert by calendar id.
                        taskId = CalendarProvider.getStaticInstance(TaskActivity.this).insertTask(taskBean, 1);
                    }
                } else {
                    CalendarProvider.getStaticInstance(TaskActivity.this).updateTask(taskId, taskBean);
                }
                int reminderSpinnerPos = reminder.getSelectedItemPosition();
                if ( reminderSpinnerPos != 0){
                    CalendarProvider.getStaticInstance(TaskActivity.this).insertReminderForTask(taskId, Constants.REMINDER_VALUE[reminderSpinnerPos], CalendarContract.Reminders.METHOD_DEFAULT);
                }
                TaskActivity.this.finish();
            }
        });

        initUI();
    }

    private void initUI(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.id_edit_start).setOnClickListener(new DatePickerTrigger(dateStart, PICK_START));
//        findViewById(R.id.id_edit_end).setOnClickListener(new DatePickerTrigger(dateEnd, PICK_END));
//        repetition = ((Spinner) findViewById(R.id.id_edit_repetition));
//        repetition.setAdapter(new DropdownAdapter(this, android.R.layout.simple_spinner_item).setWhich(SPINNER_REPEAT));
//        reminder = ((Spinner) findViewById(R.id.id_edit_reminder));
//        reminder.setAdapter(new DropdownAdapter(this, android.R.layout.simple_spinner_item).setWhich(SPINNER_REMINDER));
        title = (EditText) findViewById(R.id.id_task_title_text);
//        description = (EditText) findViewById(R.id.id_edit_description);

        if (edit){
            taskBean = CalendarResolver.getStaticInstance(this).getTaskById(taskId);
            if (taskBean == null)
                return;
            title.setText(taskBean.getTitle());
//            description.setText(taskBean.getDescription());
            dateStart = taskBean.getStartDate();
            ((TextView) findViewById(R.id.id_edit_start_date)).setText(dateStart.getYear() + "-" + dateStart.getMonthString() + "-" + dateStart.getDayString());
            ((TextView) findViewById(R.id.id_edit_start_time)).setText(dateStart.getHourString()+":"+dateStart.getMinuteString());
//            dateEnd = taskBean.getEndDate();
//            if (dateEnd == null){
//                dateEnd = dateStart;
//            }
//            ((TextView) findViewById(R.id.id_edit_end_date)).setText(dateEnd.getYear() +"-" + dateEnd.getMonthString() + "-" + dateEnd.getDayString());
//            ((TextView) findViewById(R.id.id_edit_end_time)).setText(dateEnd.getHourString()+":"+dateEnd.getMinuteString());
            //do table row with taskBean.getItenList()

            List<PurchaseItem> itemList = taskBean.getItemList();

            TableLayout tableLayout = (TableLayout)findViewById(R.id.purchase_list_table);
            for(PurchaseItem tempItem : itemList) {
                tableLayout.addView(createRow(tempItem));
            }
            tableLayout.addView(createLastRow(taskBean));
        }
    }
    private View createLastRow(TaskBean taskBean) {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TableRow lastRow = (TableRow)inflater.inflate(R.layout.purchase_total_price_row, null);
        ((TextView)lastRow.findViewById(R.id.receipt_total_price)).setText(String.valueOf(taskBean.getTotalPrice()));
        return lastRow;
    }
    private View createRow(PurchaseItem item) {
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TableRow row = (TableRow)inflater.inflate(R.layout.purchase_itemlist_row, null);
        String itemName = item.getItemName();
        String amount = String.valueOf(item.getItemCount());
        String unitPrice = String.valueOf(item.getUnitPrice());
        String price = String.valueOf(item.getSumPrice());
        ((TextView)row.findViewById(R.id.row_item_name)).setText(itemName);
        ((TextView)row.findViewById(R.id.row_amount)).setText(amount);
        ((TextView)row.findViewById(R.id.row_unit_price)).setText(unitPrice);
        ((TextView)row.findViewById(R.id.row_price)).setText(price);
        return row;
    }
    private TaskBean genTask(){
        String titleText = title.getText().toString();
        if (titleText == null || "".equalsIgnoreCase(titleText.trim())){
            Snackbar.make(title, "Title can not be empty!", Snackbar.LENGTH_SHORT).show();
            return null;
        }

        if (dateStart == null || dateEnd == null){
            Snackbar.make(title, "Please select start date & end date", Snackbar.LENGTH_SHORT).show();
            return null;
        }
        TaskBean task = new TaskBean();
        task.setTitle(title.getText().toString());
        task.setStartDate(TimeStampUtil.toUnixTimeStamp(dateStart));
        task.setEndDate(TimeStampUtil.toUnixTimeStamp(dateEnd));
        task.setDescription(description.getText().toString());
        RecurrenceUtil.populateRRule(task, repetition.getSelectedItemPosition());
        return task;
    }

    @Override
    public void onClick(View view) {
        Painting item = (Painting) view.getTag(R.id.thumb_img);
        if (view.getContext() instanceof TaskActivity) {
            ((TaskActivity) view.getContext()).openDetails(view, item);
        }
    }

    public class DatePickerTrigger implements View.OnClickListener{
        DateData dateData;
        int which;
        DatePickerTrigger(DateData dateData, int which){
            this.dateData = dateData;
            this.which = which;
        }
        @Override
        public void onClick(View v) {
            DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePickerDialog view, final int year, final int monthOfYear, final int dayOfMonth) {
                    Calendar now = Calendar.getInstance();
                    TimePickerDialog.newInstance(
                            new TimePickerDialog.OnTimeSetListener() {
                                 @Override
                                 public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                     dateData = new DateData(year, monthOfYear + 1, dayOfMonth);
                                     dateData.setHour(hourOfDay);
                                     dateData.setMinute(minute);
                                     String hourString = hourOfDay > 9 ? String.format("%d", hourOfDay) : String.format("0%d", hourOfDay);
                                     if (which == PICK_START){
                                         ((TextView) findViewById(R.id.id_edit_start_date))
                                                 .setText(new StringBuilder().append("").append(year).append("-").append(monthOfYear + 1).append("-").append(dayOfMonth).toString());
                                         ((TextView) findViewById(R.id.id_edit_start_time))
                                                 .setText(new StringBuilder().append("").append(hourString).append(":").append(minute).toString());
                                         TaskActivity.this.dateStart = dateData;
                                     } else {
                                         ((TextView) findViewById(R.id.id_edit_end_date))
                                                 .setText(new StringBuilder().append("").append(year).append("-").append(monthOfYear + 1).append("-").append(dayOfMonth).toString());
                                         ((TextView) findViewById(R.id.id_edit_end_time))
                                                 .setText(new StringBuilder().append("").append(hourString).append(":").append(minute).toString());
                                         TaskActivity.this.dateEnd = dateData;
                                     }
                                 }
                            }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true)
                            .show(getFragmentManager(), "TimePickerDialog");
                }
            },year, month - 1, day).show(getFragmentManager(), "DatePickerDialog");
        }
    }
    private static class ViewHolder {
        ImageView image;
        TextView title;
    }

    private void initFolderableImageView() {
        ViewHolder holder = new ViewHolder();
//        holder.image = Views.find(this, R.id.thumb_img);
        holder.image = (ImageView)findViewById(R.id.thumb_img);
        holder.image.setOnClickListener(this);
        holder.image.setTag(R.id.thumb_img, new Painting(R.id.thumb_img));
        Glide.with(getApplicationContext())
                .load(R.drawable.almond_blossoms)
                .dontTransform()
                .dontAnimate()
                .into(holder.image);
//        mListTouchInterceptor = Views.find(this, R.id.touch_interceptor_view);
        mListTouchInterceptor = findViewById(R.id.touch_interceptor_view);
        mListTouchInterceptor.setClickable(false);

//        mDetailsLayout = Views.find(this, R.id.details_layout);
        mDetailsLayout = findViewById(R.id.details_layout);
        mDetailsLayout.setVisibility(View.INVISIBLE);

//        mUnfoldableView = Views.find(this, R.id.unfoldable_view);
        mUnfoldableView = (UnfoldableView)findViewById(R.id.unfoldable_view);

        Bitmap glance = BitmapFactory.decodeResource(getResources(), R.drawable.unfold_glance);
        mUnfoldableView.setFoldShading(new GlanceFoldShading(glance));

        mUnfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
                mDetailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
                findViewById(R.id.touch_interceptor_view).setBackgroundColor(Color.argb(0, 255, 255, 255));
                mDetailsLayout.setVisibility(View.INVISIBLE);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (mUnfoldableView != null && (mUnfoldableView.isUnfolded() || mUnfoldableView.isUnfolding())) {
            mUnfoldableView.foldBack();
        } else {
            super.onBackPressed();
        }
    }

    public void openDetails(View coverView, Painting painting) {
//        ImageView image = Views.find(mDetailsLayout, R.id.details_image);
        ImageView image = (ImageView)findViewById(R.id.details_image);
        Log.d("OONG", "getImageId() : " + painting.getImageId());
        Glide.with(this)
                .load(R.drawable.almond_blossoms)
                .dontTransform()
                .dontAnimate()
                .into(image);
        findViewById(R.id.touch_interceptor_view).setBackgroundColor(Color.argb(170, 42, 42, 42));
//        SpannableBuilder builder = new SpannableBuilder(this);
//        builder
//                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
//                .append(R.string.year).append(": ")
//                .clearStyle()
//                .append(painting.getYear()).append("\n")
//                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
//                .append(R.string.location).append(": ")
//                .clearStyle()
//                .append(painting.getLocation());

        mUnfoldableView.unfold(coverView, mDetailsLayout);
    }
}
