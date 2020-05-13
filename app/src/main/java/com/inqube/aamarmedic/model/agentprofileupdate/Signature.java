
package com.inqube.aamarmedic.model.agentprofileupdate;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Signature {

    @SerializedName("hash")
    private String mHash;
    @SerializedName("keyId")
    private String mKeyId;

    public String getHash() {
        return mHash;
    }

    public void setHash(String hash) {
        mHash = hash;
    }

    public String getKeyId() {
        return mKeyId;
    }

    public void setKeyId(String keyId) {
        mKeyId = keyId;
    }

}
