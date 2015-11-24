package sun.bob.mcalendar.beans;

import android.database.Cursor;
import android.provider.CalendarContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import sun.bob.mcalendar.model.PurchaseItem;
import sun.bob.mcalendar.utils.TimeStampUtil;
import sun.bob.mcalendarview.vo.DateData;

/**
 * Created by bob.sun on 15/10/23.
 */
public class TaskBean implements Comparable {
    private String id;
    private String allDay;
    private String title;
    private String description;
    private String startDate;
    private String endDate;

    private String rDate;
    private String rRule;
    private String exDate;
    private String exRule;

    private int totalPrice;
    private List<PurchaseItem> itemList;

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    private void setTotalPrice() {
        if(itemList == null) {
            Log.d("OONG", "itemList was not initialized");
            return;
        } else {
            int sum = 0;
            for(PurchaseItem tempItem : itemList) {
                sum += tempItem.getSumPrice();
            }
            this.totalPrice = sum;
        }

    }


    public List<PurchaseItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<PurchaseItem> itemList) {
        this.itemList = itemList;
    }

    public TaskBean(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getAllDay() {
        return allDay == "1";
    }

    public void setAllDay(String allDay) {
        this.allDay = allDay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateData getStartDate() {
        return TimeStampUtil.toDateData(startDate);
    }

    public Long getStartDateLong(){
        return Long.parseLong(startDate);
    }

    public String getStartDateString(){
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public DateData getEndDate() {
        return TimeStampUtil.toDateData(endDate);
    }

    public String getEndDateString(){
        return endDate;
    }

    public Long getEndDateLong(){
        return Long.parseLong(endDate);
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getrDate() {
        return rDate;
    }

    public void setrDate(String rDate) {
        this.rDate = rDate;
    }

    public String getrRule() {
        return rRule;
    }

    public void setrRule(String rRule) {
        this.rRule = rRule;
    }

    public String getExDate() {
        return exDate;
    }

    public void setExDate(String exDate) {
        this.exDate = exDate;
    }

    public String getExRule() {
        return exRule;
    }

    public void setExRule(String exRule) {
        this.exRule = exRule;
    }

    public TaskBean populate(Cursor cursor){
        this.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events.TITLE)));
        this.setId(cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events._ID)));
        this.setAllDay(cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events.ALL_DAY)));
        this.setStartDate(cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events.DTSTART)));
        this.setEndDate(cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events.DTEND)));
        this.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events.DESCRIPTION)));
        this.setrDate(cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events.RDATE)));
        this.setrRule(cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events.RRULE)));
        this.setExDate(cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events.EXDATE)));
        this.setExRule(cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events.EXRULE)));
        List<PurchaseItem> list = this.objectifyDescription(this.description);
        if(list != null) {
            this.setItemList(list);
        }
        this.setTotalPrice();
        return this;
    }

    public static boolean isRecurrence(Cursor cursor){
        return cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.Events.RRULE)) == null;
    }

    public TaskBean populate(TaskBean taskBean){
        this.setTitle(taskBean.getTitle());
        this.setAllDay(taskBean.getAllDay() ? "1" : "0");
        this.setDescription(taskBean.getDescription());
        this.setEndDate(taskBean.endDate);
        this.setStartDate(taskBean.startDate);
        this.setExDate(taskBean.getExDate());
        this.setExRule(taskBean.getExRule());
        this.setId(taskBean.getId());
        this.setrDate(taskBean.getrDate());
        this.setrRule(taskBean.getrRule());
        this.setItemList(taskBean.getItemList());
        this.setTotalPrice(taskBean.getTotalPrice());
        List<PurchaseItem> list = this.objectifyDescription(this.description);
        if(list != null) {
            this.setItemList(list);
        }
        this.setTotalPrice();
        return this;
    }

    @Override
    public int compareTo(Object another) {
        return (int) (Long.parseLong(this.startDate) / 1000 - Long.parseLong(((TaskBean) another).startDate) / 1000);
    }

    @Override
    public String toString() {
        return "TaskBean{" +
                "id='" + id + '\'' +
                ", allDay='" + allDay + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", rDate='" + rDate + '\'' +
                ", rRule='" + rRule + '\'' +
                ", exDate='" + exDate + '\'' +
                ", exRule='" + exRule + '\'' +
                ", itemList=" + itemList +
                ", totalPrice=" + totalPrice +
                '}';
    }

    private List<PurchaseItem> objectifyDescription(String description) {
        ArrayList<PurchaseItem> itemList = new ArrayList<PurchaseItem>();
        StringTokenizer stok = new StringTokenizer(description, " \n");
        while (stok.hasMoreTokens()) {
            PurchaseItem item = null;
            try {
                String itemName = stok.nextToken();
                String itemCount = stok.nextToken();
                String unitPrice = stok.nextToken();
                String sumPrice = stok.nextToken();
                item = new PurchaseItem(itemName, Integer.parseInt(itemCount), Integer.parseInt(unitPrice), Integer.parseInt(sumPrice));
            } catch (NoSuchElementException e) {
                return null;
            } catch (NumberFormatException e) {
                return null;
            }
            itemList.add(item);
        }
        return itemList;
    }
}
