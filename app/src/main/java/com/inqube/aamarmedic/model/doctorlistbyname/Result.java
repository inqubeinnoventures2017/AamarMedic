
package com.inqube.aamarmedic.model.doctorlistbyname;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("doc")
    private List<Doc> mDoc;

    public List<Doc> getDoc() {
        return mDoc;
    }

    public void setDoc(List<Doc> doc) {
        mDoc = doc;
    }

}
