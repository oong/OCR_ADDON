package sun.bob.mcalendar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sun.bob.mcalendar.R;
import sun.bob.mcalendar.beans.MonthBean;
import sun.bob.mcalendar.beans.TaskBean;
import sun.bob.mcalendar.content.CalendarResolver;

/**
 * Created by oong on 2015-11-26.
 */
public class ExpandableReceiptsAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private int currentYear;
    private ArrayList<MonthBean> monthList;

    public ExpandableReceiptsAdapter(int currentYear, Context mContext) {
        this.currentYear = currentYear;
        this.mContext = mContext;
        this.initMonthList();
    }
    private void initMonthList() {
        this.monthList = new ArrayList<MonthBean>();
        for(int i=1;i<=12;i++) {
            ArrayList<TaskBean> listPerMonth = CalendarResolver.getStaticInstance(mContext).getEventsByMonth(currentYear, i);
            monthList.add(new MonthBean(i, listPerMonth));
        }

    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return monthList.get(groupPosition).getTaskList().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    //ChildView에 데이터 뿌리기
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = getChildGenericView();
        } else {
            view = convertView;
        }
//        TextView yearTextView = (TextView) view.findViewById(R.id.text);
//        text.setText(yearList.get(groupPosition).getMonthList().get(childPosition).);
        TaskBean bean = monthList.get(groupPosition).getTaskList().get(childPosition);
        bean.setItemList(bean.objectifyDescription(bean.getDescription()));
        int year = bean.getStartDate().getYear();
        int month = bean.getStartDate().getMonth();
        int day = bean.getStartDate().getDay();
        String hour = bean.getStartDate().getHourString();
        String min = bean.getStartDate().getMonthString();
        String title = bean.getTitle();
        String totalPrice = String.valueOf(bean.getTotalPrice());
        ((TextView)view.findViewById(R.id.in_list_date_ymd)).setText(String.format("%d-%d-%d", year, month, day));
        ((TextView)view.findViewById(R.id.in_list_date_hm)).setText(String.format("%s:%s", hour, min));
        ((TextView)view.findViewById(R.id.in_list_title)).setText(title);
        ((TextView)view.findViewById(R.id.in_list_total_price)).setText(totalPrice);
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return monthList.get(groupPosition).getTaskList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return monthList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return monthList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //GroupView에 데이터 뿌리
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View view;
        if (convertView == null) {
            view = getParentGenericView();
        } else {
            view = convertView;
        }
        TextView tvMonth = ((TextView) view.findViewById(R.id.month_name));
        TextView tvCount = ((TextView) view.findViewById(R.id.receipt_count_per_month));
        int count = monthList.get(groupPosition).getTaskList().size();
        int month = monthList.get(groupPosition).getMonth();
        tvMonth.setText(String.valueOf(month)+"월");
        tvCount.setText(String.valueOf(count));
        return view;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        // TODO Auto-generated method stub
        return super.areAllItemsEnabled();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

    //Child의 View의 XML을 생성
    public View getChildGenericView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_child_receipt, null);
        return view;
    }

    //Parent(Group)의 View의 XML을 생성
    public View getParentGenericView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_parent_month, null);
        return view;
    }
}
