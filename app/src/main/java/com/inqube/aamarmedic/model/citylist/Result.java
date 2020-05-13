
package com.inqube.aamarmedic.model.citylist;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("city")
    private List<City> mCity;

    public List<City> getCity() {
        return mCity;
    }

    public void setCity(List<City> city) {
        mCity = city;
    }

}
