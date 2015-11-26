package sun.bob.mcalendar.beans;

import java.util.ArrayList;

/**
 * Created by oong on 2015-11-26.
 */
public class YearBean {
    private int year;
    private ArrayList<MonthBean> monthList;

    public YearBean(ArrayList<MonthBean> monthList, int year) {
        this.monthList = monthList;
        this.year = year;
    }

    public YearBean() {
        this(1);
    }

    public YearBean(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<MonthBean> getMonthList() {
        return monthList;
    }

    public void setMonthList(ArrayList<MonthBean> monthList) {
        this.monthList = monthList;
    }
}
