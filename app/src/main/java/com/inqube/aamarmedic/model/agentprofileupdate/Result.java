
package com.inqube.aamarmedic.model.agentprofileupdate;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("$clusterTime")
    private com.inqube.aamarmedic.model.agentprofileupdate.$clusterTime m$clusterTime;
    @SerializedName("electionId")
    private String mElectionId;
    @SerializedName("n")
    private Long mN;
    @SerializedName("nModified")
    private Long mNModified;
    @SerializedName("ok")
    private Long mOk;
    @SerializedName("opTime")
    private OpTime mOpTime;
    @SerializedName("operationTime")
    private String mOperationTime;

    public com.inqube.aamarmedic.model.agentprofileupdate.$clusterTime get$clusterTime() {
        return m$clusterTime;
    }

    public void set$clusterTime(com.inqube.aamarmedic.model.agentprofileupdate.$clusterTime $clusterTime) {
        m$clusterTime = $clusterTime;
    }

    public String getElectionId() {
        return mElectionId;
    }

    public void setElectionId(String electionId) {
        mElectionId = electionId;
    }

    public Long getN() {
        return mN;
    }

    public void setN(Long n) {
        mN = n;
    }

    public Long getNModified() {
        return mNModified;
    }

    public void setNModified(Long nModified) {
        mNModified = nModified;
    }

    public Long getOk() {
        return mOk;
    }

    public void setOk(Long ok) {
        mOk = ok;
    }

    public OpTime getOpTime() {
        return mOpTime;
    }

    public void setOpTime(OpTime opTime) {
        mOpTime = opTime;
    }

    public String getOperationTime() {
        return mOperationTime;
    }

    public void setOperationTime(String operationTime) {
        mOperationTime = operationTime;
    }

}
