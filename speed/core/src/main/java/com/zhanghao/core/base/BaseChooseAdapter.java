package com.zhanghao.core.base;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * 作者： zhanghao on 2017/11/22.
 * 功能：${des}
 */

public abstract class BaseChooseAdapter<T> extends CommonAdapter {
    public static final int CHOICE_MODE_NONE = 0;
    public static final int CHOICE_MODE_SINGLE = 1;
    public static final int CHOICE_MODE_MULTIPLE = 2;

    private SparseBooleanArray mCheckStates;
    private int lastSelectedPosition = -1;
    private int mChoiceMode = CHOICE_MODE_NONE;
    private int mCheckedItemCount;

    public BaseChooseAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
    }


    public void setChoiceMode(int choiceMode) {
        mChoiceMode = choiceMode;
        if (mChoiceMode != CHOICE_MODE_NONE) {
            if (mCheckStates == null) {
                mCheckStates = new SparseBooleanArray(0);
            }
        }
    }

    public class ChooseListener implements View.OnClickListener {
        int position = 0;

        public ChooseListener(int index) {
            position = index;
        }

        @Override
        public void onClick(View v) {
            if (mChoiceMode == CHOICE_MODE_SINGLE) {
                boolean checked = !mCheckStates.get(position, false);
                if (mCheckedItemCount == 1 && mCheckStates.valueAt(0)) {
                    int lastSelectedPosition = mCheckStates.keyAt(0);
                 //   ((Checkable) mData.get(lastSelectedPosition)).setChecked(false);
                    notifyItemChanged(lastSelectedPosition);
                }
                if (checked) {
                    mCheckStates.clear();
                    mCheckStates.put(position, true);
                    mCheckedItemCount = 1;
                 //   ((Checkable) mData.get(position)).setChecked(true);
                } else {
                    mCheckStates.clear();
                    mCheckedItemCount = 0;
                }
            } else if (mChoiceMode == CHOICE_MODE_MULTIPLE) {
                boolean checked = !mCheckStates.get(position, false);
                mCheckStates.put(position, checked);
                //((Checkable) mData.get(position)).toggle();
                if (checked) {
                    mCheckedItemCount++;
                } else {
                    mCheckedItemCount--;
                }
            }
        }
    }

}
