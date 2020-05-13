
package com.inqube.aamarmedic.model.specializationlist;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Spec {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("specialization_info")
    private List<SpecializationInfo> mSpecializationInfo;
    @SerializedName("specialization_title")
    private String mSpecializationTitle;
    @SerializedName("_id")
    private String m_id;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public List<SpecializationInfo> getSpecializationInfo() {
        return mSpecializationInfo;
    }

    public void setSpecializationInfo(List<SpecializationInfo> specializationInfo) {
        mSpecializationInfo = specializationInfo;
    }

    public String getSpecializationTitle() {
        return mSpecializationTitle;
    }

    public void setSpecializationTitle(String specializationTitle) {
        mSpecializationTitle = specializationTitle;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
