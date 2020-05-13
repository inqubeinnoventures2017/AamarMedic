
package com.inqube.aamarmedic.model.appointment;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("booked_by")
    private String mBookedBy;
    @SerializedName("city")
    private String mCity;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("district")
    private String mDistrict;
    @SerializedName("doctor_schedule")
    private String mDoctorSchedule;
    @SerializedName("is_blocked")
    private Long mIsBlocked;
    @SerializedName("is_deleted")
    private Long mIsDeleted;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("modified_at")
    private Object mModifiedAt;
    @SerializedName("patient_name")
    private String mPatientName;
    @SerializedName("pin_code")
    private String mPinCode;
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

    public String getBookedBy() {
        return mBookedBy;
    }

    public void setBookedBy(String bookedBy) {
        mBookedBy = bookedBy;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDistrict() {
        return mDistrict;
    }

    public void setDistrict(String district) {
        mDistrict = district;
    }

    public String getDoctorSchedule() {
        return mDoctorSchedule;
    }

    public void setDoctorSchedule(String doctorSchedule) {
        mDoctorSchedule = doctorSchedule;
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

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public Object getModifiedAt() {
        return mModifiedAt;
    }

    public void setModifiedAt(Object modifiedAt) {
        mModifiedAt = modifiedAt;
    }

    public String getPatientName() {
        return mPatientName;
    }

    public void setPatientName(String patientName) {
        mPatientName = patientName;
    }

    public String getPinCode() {
        return mPinCode;
    }

    public void setPinCode(String pinCode) {
        mPinCode = pinCode;
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
