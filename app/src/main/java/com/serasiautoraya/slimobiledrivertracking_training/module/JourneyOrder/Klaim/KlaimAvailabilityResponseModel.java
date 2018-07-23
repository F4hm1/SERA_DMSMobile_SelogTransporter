package com.serasiautoraya.slimobiledrivertracking_training.module.JourneyOrder.Klaim;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.Model;

/**
 * Created by Fahmi Hakim on 04/06/2018.
 * for SERA
 */

public class KlaimAvailabilityResponseModel extends Model {

    @SerializedName("ClaimAvailability")
    @Expose
    private String ClaimAvailability;

    public KlaimAvailabilityResponseModel(String claimAvailability) {
        this.ClaimAvailability = claimAvailability;
    }

    public String getClaimAvailability() {
        return ClaimAvailability;
    }
}
