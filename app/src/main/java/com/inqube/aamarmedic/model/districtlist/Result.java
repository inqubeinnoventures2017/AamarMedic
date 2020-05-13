
package com.inqube.aamarmedic.model.districtlist;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("district")
    private List<District> mDistrict;

    public List<District> getDistrict() {
        return mDistrict;
    }

    public void setDistrict(List<District> district) {
        mDistrict = district;
    }

}
