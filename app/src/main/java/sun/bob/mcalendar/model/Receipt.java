package sun.bob.mcalendar.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by oong on 2015-11-23.
 */
public class Receipt {

    static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    private String date;
    private String distributor;
    private ArrayList<PurchaseItem> purchaseList;
    private int totalPrice;

    public Receipt() {
        this("distributor", new ArrayList<PurchaseItem>(), 0);
    }

    public Receipt(String date, String distributor, ArrayList<PurchaseItem> purchaseList, int totalPrice) {
        this.date = date;
        this.distributor = distributor;
        this.purchaseList = purchaseList;
        this.totalPrice = totalPrice;
    }

    public Receipt(String distributor, ArrayList<PurchaseItem> purchaseList, int totalPrice) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date();
        this.date = dateFormat.format(date);
        this.distributor = distributor;
        this.purchaseList = purchaseList;
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public ArrayList<PurchaseItem> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(ArrayList<PurchaseItem> purchaseList) {
        this.purchaseList = purchaseList;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }



    @Override
    public String toString() {
        return "Receipt{" +
                "date='" + date + '\'' +
                ", distributor='" + distributor + '\'' +
                ", purchaseList=" + purchaseList +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
