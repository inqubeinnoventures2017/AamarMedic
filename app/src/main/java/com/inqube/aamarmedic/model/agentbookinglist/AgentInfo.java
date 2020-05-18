
package com.inqube.aamarmedic.model.agentbookinglist;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AgentInfo {

    @SerializedName("agent_id")
    private String mAgentId;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("fcm_token")
    private String mFcmToken;
    @SerializedName("image_name")
    private String mImageName;
    @SerializedName("image_url")
    private String mImageUrl;
    @SerializedName("language_id")
    private Object mLanguageId;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("name")
    private String mName;
    @SerializedName("username")
    private String mUsername;
    @SerializedName("_id")
    private String m_id;

    public String getAgentId() {
        return mAgentId;
    }

    public void setAgentId(String agentId) {
        mAgentId = agentId;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFcmToken() {
        return mFcmToken;
    }

    public void setFcmToken(String fcmToken) {
        mFcmToken = fcmToken;
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

    public Object getLanguageId() {
        return mLanguageId;
    }

    public void setLanguageId(Object languageId) {
        mLanguageId = languageId;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
