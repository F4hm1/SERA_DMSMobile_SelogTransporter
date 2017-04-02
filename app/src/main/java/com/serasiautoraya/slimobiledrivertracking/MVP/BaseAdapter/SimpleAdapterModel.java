package com.serasiautoraya.slimobiledrivertracking.MVP.BaseAdapter;

import java.util.List;

/**
 * Created by Randi Dwi Nandra on 01/04/2017.
 */

public interface SimpleAdapterModel {

    SimpleSingleList getItem(int position);

    SimpleSingleList remove(int position);

    void setItemList(List<SimpleSingleList> simpleSingleLists);

    void addItem(SimpleSingleList simpleSingleList);
}
