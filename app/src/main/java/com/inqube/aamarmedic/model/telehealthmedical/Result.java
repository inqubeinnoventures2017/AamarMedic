
package com.inqube.aamarmedic.model.telehealthmedical;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("blood_sugar_fasting")
    private String mBloodSugarFasting;
    @SerializedName("blood_sugar_pp")
    private String mBloodSugarPp;
    @SerializedName("body_temperature")
    private Double mBodyTemperature;
    @SerializedName("booked_by")
    private String mBookedBy;
    @SerializedName("cough_no_of_days")
    private Boolean mCoughNoOfDays;
    @SerializedName("cough_pattern")
    private String mCoughPattern;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("is_allergic")
    private Boolean mIsAllergic;
    @SerializedName("is_blocked")
    private Long mIsBlocked;
    @SerializedName("is_blood_pressure")
    private Boolean mIsBloodPressure;
    @SerializedName("is_cough")
    private Boolean mIsCough;
    @SerializedName("is_deleted")
    private Long mIsDeleted;
    @SerializedName("is_shortness_of_breath")
    private Boolean mIsShortnessOfBreath;
    @SerializedName("last_blood_pressure")
    private String mLastBloodPressure;
    @SerializedName("modified_at")
    private Object mModifiedAt;
    @SerializedName("patient_id")
    private String mPatientId;
    @SerializedName("shortness_of_breath_type")
    private String mShortnessOfBreathType;
    @SerializedName("__v")
    private Long m_V;
    @SerializedName("_id")
    private String m_id;

    public String getBloodSugarFasting() {
        return mBloodSugarFasting;
    }

    public void setBloodSugarFasting(String bloodSugarFasting) {
        mBloodSugarFasting = bloodSugarFasting;
    }

    public String getBloodSugarPp() {
        return mBloodSugarPp;
    }

    public void setBloodSugarPp(String bloodSugarPp) {
        mBloodSugarPp = bloodSugarPp;
    }

    public Double getBodyTemperature() {
        return mBodyTemperature;
    }

    public void setBodyTemperature(Double bodyTemperature) {
        mBodyTemperature = bodyTemperature;
    }

    public String getBookedBy() {
        return mBookedBy;
    }

    public void setBookedBy(String bookedBy) {
        mBookedBy = bookedBy;
    }

    public Boolean getCoughNoOfDays() {
        return mCoughNoOfDays;
    }

    public void setCoughNoOfDays(Boolean coughNoOfDays) {
        mCoughNoOfDays = coughNoOfDays;
    }

    public String getCoughPattern() {
        return mCoughPattern;
    }

    public void setCoughPattern(String coughPattern) {
        mCoughPattern = coughPattern;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public Boolean getIsAllergic() {
        return mIsAllergic;
    }

    public void setIsAllergic(Boolean isAllergic) {
        mIsAllergic = isAllergic;
    }

    public Long getIsBlocked() {
        return mIsBlocked;
    }

    public void setIsBlocked(Long isBlocked) {
        mIsBlocked = isBlocked;
    }

    public Boolean getIsBloodPressure() {
        return mIsBloodPressure;
    }

    public void setIsBloodPressure(Boolean isBloodPressure) {
        mIsBloodPressure = isBloodPressure;
    }

    public Boolean getIsCough() {
        return mIsCough;
    }

    public void setIsCough(Boolean isCough) {
        mIsCough = isCough;
    }

    public Long getIsDeleted() {
        return mIsDeleted;
    }

    public void setIsDeleted(Long isDeleted) {
        mIsDeleted = isDeleted;
    }

    public Boolean getIsShortnessOfBreath() {
        return mIsShortnessOfBreath;
    }

    public void setIsShortnessOfBreath(Boolean isShortnessOfBreath) {
        mIsShortnessOfBreath = isShortnessOfBreath;
    }

    public String getLastBloodPressure() {
        return mLastBloodPressure;
    }

    public void setLastBloodPressure(String lastBloodPressure) {
        mLastBloodPressure = lastBloodPressure;
    }

    public Object getModifiedAt() {
        return mModifiedAt;
    }

    public void setModifiedAt(Object modifiedAt) {
        mModifiedAt = modifiedAt;
    }

    public String getPatientId() {
        return mPatientId;
    }

    public void setPatientId(String patientId) {
        mPatientId = patientId;
    }

    public String getShortnessOfBreathType() {
        return mShortnessOfBreathType;
    }

    public void setShortnessOfBreathType(String shortnessOfBreathType) {
        mShortnessOfBreathType = shortnessOfBreathType;
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
