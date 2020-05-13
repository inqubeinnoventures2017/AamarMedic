
package com.inqube.aamarmedic.model.agentbookinglist;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class District {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("district_name")
    private String mDistrictName;
    @SerializedName("is_blocked")
    private Long mIsBlocked;
    @SerializedName("is_deleted")
    private Long mIsDeleted;
    @SerializedName("modified_at")
    private Object mModifiedAt;
    @SerializedName("state_id")
    private String mStateId;
    @SerializedName("__v")
    private Long m_V;
    @SerializedName("_id")
    private String m_id;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDistrictName() {
        return mDistrictName;
    }

    public void setDistrictName(String districtName) {
        mDistrictName = districtName;
    }

    public Long getIsBlocked() {
        return mIsBlocked;
    }

    public void setIsBlocked(Long isBlocked) {
        mIsBlocked = isBlocked;
    }

    public Long getIsDeleted() {
        return mIsDeleted;
    }

    public void setIsDeleted(Long isDeleted) {
        mIsDeleted = isDeleted;
    }

    public Object getModifiedAt() {
        return mModifiedAt;
    }

    public void setModifiedAt(Object modifiedAt) {
        mModifiedAt = modifiedAt;
    }

    public String getStateId() {
        return mStateId;
    }

    public void setStateId(String stateId) {
        mStateId = stateId;
    }

    public Long get_V() {
        return m_V;
    }

    public void set_V(Long _V) {
        m_V = _V;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
