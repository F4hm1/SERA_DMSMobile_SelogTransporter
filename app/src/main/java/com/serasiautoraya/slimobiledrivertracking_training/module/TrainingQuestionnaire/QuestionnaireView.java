package com.serasiautoraya.slimobiledrivertracking_training.module.TrainingQuestionnaire;

import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.BaseViewInterface;
import com.serasiautoraya.slimobiledrivertracking_training.module.BaseInterface.FormViewInterface;

import java.util.HashMap;

/**
 * Created by randi on 07/08/2017.
 */

public interface QuestionnaireView extends BaseViewInterface, FormViewInterface{

    void showConfirmationDialog();

    void showSuccessDialog();

    void onClickSubmit();

    void setQuestionnaireForm(HashMap<String, QuestionnaireInputModel> questionnaireInputModelHashMap, String[] questionnaireCodes);

}
