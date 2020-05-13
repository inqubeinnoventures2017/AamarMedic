
package com.inqube.aamarmedic.model.doctorlistbyname;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ClinicId {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("clinic_name")
    private String mClinicName;
    @SerializedName("contact_no")
    private String mContactNo;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("is_blocked")
    private Long mIsBlocked;
    @SerializedName("is_deleted")
    private Long mIsDeleted;
    @SerializedName("__v")
    private Long m_V;
    @SerializedName("_id")
    private String m_id;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getClinicName() {
        return mClinicName;
    }

    public void setClinicName(String clinicName) {
        mClinicName = clinicName;
    }

    public String getContactNo() {
        return mContactNo;
    }

    public void setContactNo(String contactNo) {
        mContactNo = contactNo;
    }

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
