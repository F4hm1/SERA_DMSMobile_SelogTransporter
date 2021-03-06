package com.serasiautoraya.slimobiledrivertracking_training.module.BaseAdapter;

import com.serasiautoraya.slimobiledrivertracking_training.module.BaseModel.Model;

import java.util.List;

/**
 * Created by Randi Dwi Nandra on 01/04/2017.
 */

public interface SimpleAdapterModel<T extends Model> {

    Model getItem(int position);

    Model remove(int position);

    void setItemList(List<T> simpleSingleLists);

    void addItem(T simpleSingleList);
}
