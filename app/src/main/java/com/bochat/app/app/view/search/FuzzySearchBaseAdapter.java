package com.bochat.app.app.view.search;

import android.text.TextUtils;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/23 11:49
 * Description :
 */

public abstract class FuzzySearchBaseAdapter<ITEM extends IFuzzySearchItem>
        extends BaseAdapter implements Filterable {

    private   FuzzySearchFilter mFilter;
    private   List<ITEM>        mBackDataList;
    protected List<ITEM>        mDataList;
    private   IFuzzySearchRule  mIFuzzySearchRule;

    public FuzzySearchBaseAdapter(IFuzzySearchRule rule) {
        this(rule, null);
    }

    public FuzzySearchBaseAdapter(IFuzzySearchRule rule, List<ITEM> dataList) {
        if (rule == null) {
            mIFuzzySearchRule = new DefaultFuzzySearchRule();
        } else {
            mIFuzzySearchRule = rule;
        }
        mBackDataList = dataList;
        mDataList = dataList;
    }

    public void setDataList(List<ITEM> dataList) {
        mBackDataList = dataList;
        mDataList = dataList;
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new FuzzySearchFilter();
        }
        return mFilter;
    }

    private class FuzzySearchFilter extends Filter {

        /**
         * 执行过滤操作,如果搜索的关键字为空，默认所有结果
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            List<ITEM> filterList;
            if (TextUtils.isEmpty(constraint)) {
                filterList = mBackDataList;
            } else {
                filterList = new ArrayList<>();
                for (ITEM item : mBackDataList) {
                    if (mIFuzzySearchRule.accept(constraint, item.getSourceKey(), item.getFuzzyKey())) {
                        filterList.add(item);
                    }
                }
            }
            result.values = filterList;
            result.count = filterList.size();
            return result;
        }

        /**
         * 得到过滤结果
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDataList = (List<ITEM>) results.values;
            notifyDataSetChanged();
        }
    }
}
