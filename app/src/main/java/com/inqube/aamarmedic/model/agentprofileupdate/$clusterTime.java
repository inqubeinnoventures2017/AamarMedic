
package com.inqube.aamarmedic.model.agentprofileupdate;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class $clusterTime {

    @SerializedName("clusterTime")
    private String mClusterTime;
    @SerializedName("signature")
    private Signature mSignature;

    public String getClusterTime() {
        return mClusterTime;
    }

    public void setClusterTime(String clusterTime) {
        mClusterTime = clusterTime;
    }

    public Signature getSignature() {
        return mSignature;
    }

    public void setSignature(Signature signature) {
        mSignature = signature;
    }

}
