
package com.inqube.aamarmedic.model.agentbookinglist;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("agent_info")
    private AgentInfo mAgentInfo;
    @SerializedName("city_info")
    private CityInfo mCityInfo;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("district_info")
    private DistrictInfo mDistrictInfo;
    @SerializedName("doctor_schedule_info")
    private DoctorScheduleInfo mDoctorScheduleInfo;
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
    @SerializedName("remark")
    private String mRemark;
    @SerializedName("_id")
    private String m_id;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public AgentInfo getAgentInfo() {
        return mAgentInfo;
    }

    public void setAgentInfo(AgentInfo agentInfo) {
        mAgentInfo = agentInfo;
    }

    public CityInfo getCityInfo() {
        return mCityInfo;
    }

    public void setCityInfo(CityInfo cityInfo) {
        mCityInfo = cityInfo;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public DistrictInfo getDistrictInfo() {
        return mDistrictInfo;
    }

    public void setDistrictInfo(DistrictInfo districtInfo) {
        mDistrictInfo = districtInfo;
    }

    public DoctorScheduleInfo getDoctorScheduleInfo() {
        return mDoctorScheduleInfo;
    }

    public void setDoctorScheduleInfo(DoctorScheduleInfo doctorScheduleInfo) {
        mDoctorScheduleInfo = doctorScheduleInfo;
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

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String remark) {
        mRemark = remark;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
