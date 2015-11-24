package sun.bob.mcalendar.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import sun.bob.mcalendar.R;
import sun.bob.mcalendarview.vo.DateData;

/**
 * Created by oong on 2015-11-23.
 */
public class ReceiptCardViewHolder extends RecyclerView.ViewHolder {
    private TextView dateYMD, dateHM, name, totalPrice;
    public ReceiptCardViewHolder(View itemView) {
        super(itemView);
        dateYMD = (TextView) itemView.findViewById(R.id.id_receipt_card_receipt_date_ymd);
        dateHM  = (TextView) itemView.findViewById(R.id.id_receipt_card_receipt_date_hm);
        name = (TextView)itemView.findViewById(R.id.id_receipt_card_receipt_title);
        totalPrice = (TextView)itemView.findViewById(R.id.id_receipt_card_total_price);
    }

    public ReceiptCardViewHolder setDateYMD(String dateYMD) {
        this.dateYMD.setText(dateYMD);
        return this;
    }

    public ReceiptCardViewHolder setDateHM(String dateHM) {
        this.dateHM.setText(dateHM);
        return this;
    }

    public ReceiptCardViewHolder setName(String name) {
        this.name.setText(name);
        return this;
    }

    public ReceiptCardViewHolder setTotalPrice(String totalPrice) {
        this.totalPrice.setText(totalPrice);
        return this;
    }

    public ReceiptCardViewHolder populateWithDateData(DateData dateData) {
        int year = dateData.getYear(); int month = dateData.getMonth(); int day = dateData.getDay();
        String hour = dateData.getHourString(); String min = dateData.getMinuteString();
        this.setDateYMD(String.format("%d-%d-%d", year,month,day));
        this.setDateHM(String.format("%s:%s", hour, min));
        return this;
    }
}
