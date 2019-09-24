package com.bochat.app.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Filter;
import android.widget.Filterable;

import com.bochat.app.app.view.search.DefaultFuzzySearchRule;
import com.bochat.app.app.view.search.IFuzzySearchItem;
import com.bochat.app.app.view.search.IFuzzySearchRule;
import com.zhy.adapter.abslistview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/29 17:24
 * Description :
 */

public abstract class SearchAdapter<T extends IFuzzySearchItem> extends CommonAdapter<T> implements Filterable {

    private Filter filter;

    private List<T> mDataList;
    private List<T> mBackDataList;
    
    public SearchAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
        mDataList = datas;
        mBackDataList = datas;
        filter = new FuzzySearchFilter();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public class FuzzySearchFilter extends Filter {

        IFuzzySearchRule fuzzySearchRule = new DefaultFuzzySearchRule();
        
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            List<T> filterList;
            if (TextUtils.isEmpty(constraint)) {
                filterList = mBackDataList;
            } else {
                filterList = new ArrayList<>();
                for (T item : mBackDataList) {
                    if (fuzzySearchRule.accept(constraint, item.getSourceKey(), item.getFuzzyKey())) {
                        filterList.add(item);
                    }
                }
            }
            result.values = filterList;
            result.count = filterList.size();
            return result;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDataList.clear();
            mDataList.addAll((List<T>)results.values);
            notifyDataSetChanged();
        }
    }
}
