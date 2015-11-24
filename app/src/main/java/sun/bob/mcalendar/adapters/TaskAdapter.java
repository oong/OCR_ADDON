package sun.bob.mcalendar.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sun.bob.mcalendar.R;
import sun.bob.mcalendar.beans.TaskBean;
import sun.bob.mcalendar.content.CalendarResolver;
import sun.bob.mcalendarview.vo.DateData;

/**
 * Created by bob.sun on 15/10/22.
 */
public class TaskAdapter extends RecyclerView.Adapter<ReceiptCardViewHolder> {

    private ArrayList<TaskBean> tasks;
    private Context context;

    public TaskAdapter(Context context, DateData date) {
        this.context = context;
        tasks = CalendarResolver.getStaticInstance(context).getEventsOn(date);
    }

    public TaskAdapter changeDate(DateData dateData) {
        tasks = CalendarResolver.getStaticInstance(context).getEventsOn(dateData);
        notifyDataSetChanged();
        return this;
    }

    @Override
    public ReceiptCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_small_receipt_card, parent, false);
        view.setOnClickListener(new OnTaskClickListener((RecyclerView) parent));
        //Pass the inflated view to viewholder so it can populate it's holders.
        return new ReceiptCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceiptCardViewHolder holder, int position) {
        TaskBean task = tasks.get(position);
        Log.d("OONG", task.toString());
//            holder.setName(task.getTitle())
//                .setDetail(task.getDescription())
//                .populateWithDateData(task.getStartDate())
//                .setEnd(String.format("%s:%s", task.getEndDate().getHourString(), task.getEndDate().getMinuteString()));
        holder.setName(task.getTitle())
            .populateWithDateData(task.getStartDate())
            .setTotalPrice(String.valueOf(task.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public ArrayList<TaskBean> getTasks() {
        return tasks;
    }
}
