
package com.inqube.aamarmedic.model.doctorlistbyname;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ClinicInfo {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("clinic_name")
    private String mClinicName;
    @SerializedName("contact_no")
    private String mContactNo;
    @SerializedName("_id")
    private String m_id;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getClinicName() {
        return mClinicName;
    }

    public void setClinicName(String clinicName) {
        mClinicName = clinicName;
    }

    public String getContactNo() {
        return mContactNo;
    }

    public void setContactNo(String contactNo) {
        mContactNo = contactNo;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
