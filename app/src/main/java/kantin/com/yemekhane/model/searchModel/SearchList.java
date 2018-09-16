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


    public String getPaymentFinishdate() {
        return paymentFinishdate;
    }

    public void setPaymentFinishdate(String paymentFinishdate) {
        this.paymentFinishdate = paymentFinishdate;
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

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(int paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

}


