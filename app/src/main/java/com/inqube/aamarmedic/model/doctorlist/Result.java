
package com.inqube.aamarmedic.model.doctorlist;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("clinic_info")
    private ClinicInfo mClinicInfo;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("day")
    private String mDay;
    @SerializedName("doctor_info")
    private DoctorInfo mDoctorInfo;
    @SerializedName("end_time")
    private String mEndTime;
    @SerializedName("is_blocked")
    private Long mIsBlocked;
    @SerializedName("is_deleted")
    private Long mIsDeleted;
    @SerializedName("start_time")
    private String mStartTime;
    @SerializedName("_id")
    private String m_id;

    public ClinicInfo getClinicInfo() {
        return mClinicInfo;
    }

    public void setClinicInfo(ClinicInfo clinicInfo) {
        mClinicInfo = clinicInfo;
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

    public DoctorInfo getDoctorInfo() {
        return mDoctorInfo;
    }

    public void setDoctorInfo(DoctorInfo doctorInfo) {
        mDoctorInfo = doctorInfo;
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

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
