package kantin.com.yemekhane.model.searchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchList {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("paymentPeriod")
    @Expose
    private int paymentPeriod;
    @SerializedName("className")
    @Expose
    private String schoolNumber;
    @SerializedName("isSelected")
    @Expose
    private Boolean isSelected;
    @SerializedName("menu")
    @Expose
    private String menu;
    @SerializedName("paymentFinishdate")
    @Expose
    private String paymentFinishdate;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("menuPicktime")
    @Expose
    private String menuPicktime;

    public String getMenuPicktime() {
        return menuPicktime;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPaymentFinishdate() {
        return paymentFinishdate;
    }


    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }


    public int getPaymentPeriod() {
        return paymentPeriod;
    }


    public String getSchoolNumber() {
        return schoolNumber;
    }


    public Boolean getIsSelected() {
        return isSelected;
    }


}


