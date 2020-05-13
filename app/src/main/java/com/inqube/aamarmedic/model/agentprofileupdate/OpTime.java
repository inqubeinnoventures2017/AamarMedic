
package com.inqube.aamarmedic.model.agentprofileupdate;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class OpTime {

    @SerializedName("t")
    private Long mT;
    @SerializedName("ts")
    private String mTs;

    public Long getT() {
        return mT;
    }

    public void setT(Long t) {
        mT = t;
    }

    public String getTs() {
        return mTs;
    }

    public void setTs(String ts) {
        mTs = ts;
    }

}
