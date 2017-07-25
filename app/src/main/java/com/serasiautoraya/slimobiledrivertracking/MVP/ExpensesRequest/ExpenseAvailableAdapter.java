package com.serasiautoraya.slimobiledrivertracking.MVP.ExpensesRequest;

/**
 * Created by randi on 03/07/2017.
 */

public class ExpenseAvailableAdapter {
    private ExpenseAvailableResponseModel expenseAvailableResponseModel;

    public ExpenseAvailableAdapter(ExpenseAvailableResponseModel expenseAvailableResponseModel) {
        this.expenseAvailableResponseModel = expenseAvailableResponseModel;
    }

    public ExpenseAvailableResponseModel getExpenseAvailableResponseModel() {
        return expenseAvailableResponseModel;
    }

    @Override
    public String toString() {
        return expenseAvailableResponseModel.getExpenseTypeName();
    }
}
