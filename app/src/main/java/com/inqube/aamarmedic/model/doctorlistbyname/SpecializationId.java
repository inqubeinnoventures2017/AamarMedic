
package com.inqube.aamarmedic.model.doctorlistbyname;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SpecializationId {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("is_blocked")
    private Long mIsBlocked;
    @SerializedName("is_deleted")
    private Long mIsDeleted;
    @SerializedName("specialization_name")
    private String mSpecializationName;
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

    public String getSpecializationName() {
        return mSpecializationName;
    }

    public void setSpecializationName(String specializationName) {
        mSpecializationName = specializationName;
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
