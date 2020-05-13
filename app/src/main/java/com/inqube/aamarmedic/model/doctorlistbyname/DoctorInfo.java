
package com.inqube.aamarmedic.model.doctorlistbyname;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DoctorInfo {

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
    @SerializedName("last_name")
    private String mLastName;
    @SerializedName("mobile_no")
    private String mMobileNo;
    @SerializedName("specialization_info")
    private SpecializationInfo mSpecializationInfo;
    @SerializedName("_id")
    private String m_id;

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

    public SpecializationInfo getSpecializationInfo() {
        return mSpecializationInfo;
    }

    public void setSpecializationInfo(SpecializationInfo specializationInfo) {
        mSpecializationInfo = specializationInfo;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
