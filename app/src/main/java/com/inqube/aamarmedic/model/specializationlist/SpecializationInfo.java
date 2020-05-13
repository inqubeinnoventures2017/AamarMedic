
package com.inqube.aamarmedic.model.specializationlist;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SpecializationInfo {

    @SerializedName("language_id")
    private String mLanguageId;
    @SerializedName("specialization_id")
    private String mSpecializationId;
    @SerializedName("specialization_name")
    private String mSpecializationName;
    @SerializedName("__v")
    private Long m_V;
    @SerializedName("_id")
    private String m_id;

    public String getLanguageId() {
        return mLanguageId;
    }

    public void setLanguageId(String languageId) {
        mLanguageId = languageId;
    }

    public String getSpecializationId() {
        return mSpecializationId;
    }

    public void setSpecializationId(String specializationId) {
        mSpecializationId = specializationId;
    }

    public String getSpecializationName() {
        return mSpecializationName;
    }

    public void setSpecializationName(String specializationName) {
        mSpecializationName = specializationName;
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
