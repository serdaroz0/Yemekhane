package kantin.com.yemekhane.model.searchModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SearchListModel implements Serializable {

    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("data")
    @Expose
    private List<SearchList> data = null;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<SearchList> getData() {
        return data;
    }

    public void setData(List<SearchList> data) {
        this.data = data;
    }
}
