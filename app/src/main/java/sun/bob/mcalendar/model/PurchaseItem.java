package sun.bob.mcalendar.model;

/**
 * Created by oong on 2015-11-23.
 */
public class PurchaseItem {
    private String itemName;
    private int itemCount;
    private int unitPrice;
    private int sumPrice;

    public PurchaseItem(String itemName, int itemCount, int unitPrice, int sumPrice) {
        super();
        this.itemName = itemName;
        this.itemCount = itemCount;
        this.unitPrice = unitPrice;
        this.sumPrice = sumPrice;
    }

    @Override
    public String toString() {
        return "PurchaseItem [itemName=" + itemName + ", itemCount=" + itemCount + ", unitPrice=" + unitPrice
                + ", sumPrice=" + sumPrice + "]";
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(int sumPrice) {
        this.sumPrice = sumPrice;
    }
}
