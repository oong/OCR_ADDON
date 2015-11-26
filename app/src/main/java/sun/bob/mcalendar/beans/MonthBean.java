package sun.bob.mcalendar.beans;

import java.util.ArrayList;

/**
 * Created by oong on 2015-11-26.
 */
public class MonthBean {
    private int month;
    private ArrayList<TaskBean> taskList;

    public MonthBean(int month, ArrayList<TaskBean> taskList) {
        this.month = month;
        this.taskList = taskList;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public ArrayList<TaskBean> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<TaskBean> taskList) {
        this.taskList = taskList;
    }
}
