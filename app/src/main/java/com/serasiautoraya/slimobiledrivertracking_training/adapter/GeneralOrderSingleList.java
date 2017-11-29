package com.serasiautoraya.slimobiledrivertracking_training.adapter;

import com.serasiautoraya.slimobiledrivertracking_training.model.ModelActivityJourney;

/**
 * Created by Randi Dwi Nandra on 28/02/2017.
 */

public class GeneralOrderSingleList extends GeneralSingleList{

    private ModelActivityJourney modelActivityJourney;

    public GeneralOrderSingleList(ModelActivityJourney modelActivityJourney) {
        super(modelActivityJourney.getCode(), modelActivityJourney.getInformation(), modelActivityJourney.getStatus());
        this.modelActivityJourney = modelActivityJourney;
    }

    public ModelActivityJourney getModelActivityJourney() {
        return modelActivityJourney;
    }

    public void setModelActivityJourney(ModelActivityJourney modelActivityJourney) {
        this.modelActivityJourney = modelActivityJourney;
    }
}
