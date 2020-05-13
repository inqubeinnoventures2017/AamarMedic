
package com.inqube.aamarmedic.model.agentbookinglist;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DoctorId {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("degree")
    private String mDegree;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("image_name")
    private String mImageName;
    @SerializedName("image_url")
    private String mImageUrl;
    @SerializedName("is_blocked")
    private Long mIsBlocked;
    @SerializedName("is_deleted")
    private Long mIsDeleted;
    @SerializedName("last_name")
    private String mLastName;
    @SerializedName("mobile_no")
    private String mMobileNo;
    @SerializedName("specializationId")
    private SpecializationId mSpecializationId;
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

    public String getDegree() {
        return mDegree;
    }

    public void setDegree(String degree) {
        mDegree = degree;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getImageName() {
        return mImageName;
    }

    public void setImageName(String imageName) {
        mImageName = imageName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
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

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getMobileNo() {
        return mMobileNo;
    }

    public void setMobileNo(String mobileNo) {
        mMobileNo = mobileNo;
    }

    public SpecializationId getSpecializationId() {
        return mSpecializationId;
    }

    public void setSpecializationId(SpecializationId specializationId) {
        mSpecializationId = specializationId;
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
