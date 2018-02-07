package com.zhanghao.speed.temp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhanghao.speed.R;

import java.util.List;
import java.util.Map;

/**
 * 作者： zhanghao on 2018/2/5.
 * 功能：${des}
 */

public class TestGroupAdapter2 extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private Context mContext;

    private List<String> groups;
    private Map<String, List<SectionInfo>> mMembers;
    private int selectedGroupId = -1, selectedChildId;

    public TestGroupAdapter2(Context mContext, List<String> groups, Map<String, List<SectionInfo>> mMembers) {
        this.mContext = mContext;
        this.groups = groups;
        this.mMembers = mMembers;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }


    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mMembers.get(groups.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group, null);
            groupHolder = new GroupHolder();
            groupHolder.groupname = (TextView) convertView.findViewById(R.id.groupName);
            groupHolder.contentNum = (TextView) convertView.findViewById(R.id.contentNum);
            groupHolder.tag = (ImageView) convertView.findViewById(R.id.groupTag);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        if (isExpanded) {
            groupHolder.tag.setBackgroundResource(com.tencent.qcloud.timchat.R.drawable.open);
        } else {
            groupHolder.tag.setBackgroundResource(com.tencent.qcloud.timchat.R.drawable.close);
        }
        groupHolder.groupname.setText(groups.get(groupPosition));
        return convertView;
    }

    @Override
    public View getRealChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildrenHolder itemHolder;
        if (convertView == null) {
            itemHolder = new ChildrenHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group_child, null);
            itemHolder.gdChild = (MyGridView) convertView.findViewById(R.id.gdChild);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ChildrenHolder) convertView.getTag();
        }
        final List<SectionInfo> data = mMembers.get(getGroup(groupPosition));
        final GroupChildAdapter adapter = new GroupChildAdapter(mContext, R.layout.item_group_child_2, data);
        itemHolder.gdChild.setAdapter(adapter);
        itemHolder.gdChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedGroupId != -1) {
                    mMembers.get(getGroup(selectedGroupId)).get(selectedChildId).isSelected = false;
                }
                data.get(position).isSelected = true;
                selectedGroupId = groupPosition;
                selectedChildId = position;
                //ToastUtils.showLongToast(data.get(position).name);
                adapter.notifyDataSetChanged();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return 1;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class GroupHolder {
        public TextView groupname;
        public TextView contentNum;
        public ImageView tag;
    }

    class ChildrenHolder {
        public MyGridView gdChild;

    }
}
