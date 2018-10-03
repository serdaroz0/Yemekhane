package kantin.com.yemekhane.utils;


import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import kantin.com.yemekhane.model.searchModel.SearchList;


public class MyDiffCallback extends DiffUtil.Callback {

    private List<SearchList> oldPersons;
    private List<SearchList> newPersons;

    public MyDiffCallback(List<SearchList> newPersons, List<SearchList> oldPersons) {
        this.newPersons = newPersons;
        this.oldPersons = oldPersons;
    }

    @Override
    public int getOldListSize() {
        return oldPersons.size();
    }

    @Override
    public int getNewListSize() {
        return newPersons.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition).getId() == newPersons.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPersons.get(oldItemPosition).equals(newPersons.get(newItemPosition));
    }

    @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            //you can return particular field for changed item.
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
}

