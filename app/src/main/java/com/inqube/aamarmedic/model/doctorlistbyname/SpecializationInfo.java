
package com.inqube.aamarmedic.model.doctorlistbyname;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SpecializationInfo {

    @SerializedName("specialization_details")
    private List<SpecializationDetail> mSpecializationDetails;
    @SerializedName("specialization_title")
    private String mSpecializationTitle;
    @SerializedName("_id")
    private String m_id;

    public List<SpecializationDetail> getSpecializationDetails() {
        return mSpecializationDetails;
    }

    public void setSpecializationDetails(List<SpecializationDetail> specializationDetails) {
        mSpecializationDetails = specializationDetails;
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
