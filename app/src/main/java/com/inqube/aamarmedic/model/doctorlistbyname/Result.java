
package com.inqube.aamarmedic.model.doctorlistbyname;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("clinicId")
    private ClinicId mClinicId;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("day")
    private String mDay;
    @SerializedName("doctorId")
    private DoctorId mDoctorId;
    @SerializedName("end_time")
    private String mEndTime;
    @SerializedName("is_blocked")
    private Long mIsBlocked;
    @SerializedName("is_deleted")
    private Long mIsDeleted;
    @SerializedName("start_time")
    private String mStartTime;
    @SerializedName("__v")
    private Long m_V;
    @SerializedName("_id")
    private String m_id;

    public ClinicId getClinicId() {
        return mClinicId;
    }

    public void setClinicId(ClinicId clinicId) {
        mClinicId = clinicId;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public DoctorId getDoctorId() {
        return mDoctorId;
    }

    public void setDoctorId(DoctorId doctorId) {
        mDoctorId = doctorId;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
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

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
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
