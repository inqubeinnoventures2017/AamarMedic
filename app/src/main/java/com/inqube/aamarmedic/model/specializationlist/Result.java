
package com.inqube.aamarmedic.model.specializationlist;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Result {

    @SerializedName("spec")
    private List<Spec> mSpec;

    public List<Spec> getSpec() {
        return mSpec;
    }

    public void setSpec(List<Spec> spec) {
        mSpec = spec;
    }

}
