package com.tencent.qcloud.timchat.ui;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leeone.leeonecore.util.CheckDataUtils;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.model.FriendProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： zhanghao on 2017/12/1.
 * 功能：${des}
 */

public class ContactAdapter extends BaseAdapter implements SectionIndexer {
    List<FriendProfile> friends;
    List<FriendProfile> copyUserList = new ArrayList<>();
    Context context;
    private boolean notiyfyByFilter;
    private MyFilter myFilter;
    List<String> list;
    private SparseIntArray positionOfSection;
    private SparseIntArray sectionOfPosition;
    public ContactAdapter(List<FriendProfile> friends, Context context) {
        this.friends = friends;
        this.context = context;
        copyUserList.addAll(friends);
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public FriendProfile getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.ease_row_contact, null);
            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.headerView = (TextView) convertView.findViewById(R.id.header);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FriendProfile user = friends.get(position);
        String header = user.getInitialLetter();

        if (position == 0 || header != null && !header.equals(getItem(position - 1).getInitialLetter())) {
            if (CheckDataUtils.isEmpty(header)) {
                holder.headerView.setVisibility(View.GONE);
            } else {
                holder.headerView.setVisibility(View.VISIBLE);
                holder.headerView.setText(header);
            }
        } else {
            holder.headerView.setVisibility(View.GONE);
        }
        //设置昵称
        holder.nameView.setText(user.getName());
        if (user.getInitialLetter().equals("客服")) {
            holder.avatar.setImageResource(R.drawable.customer_default);
        } else {
            Glide.with(convertView.getContext()).load(user.getAvatarUrl()).into(holder.avatar);
        }

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (!notiyfyByFilter) {
            copyUserList.clear();
            copyUserList.addAll(friends);
        }
    }

    public void filter(CharSequence s){
        if (myFilter == null) {
            myFilter = new MyFilter(friends);
        }
        myFilter.filter(s);
    }

    @Override
    public Object[] getSections() {
        positionOfSection = new SparseIntArray();
        sectionOfPosition = new SparseIntArray();
        int count = getCount();
        list = new ArrayList<String>();
        list.add("搜");
        positionOfSection.put(0, 0);
        sectionOfPosition.put(0, 0);
        for (int i = 1; i < count; i++) {

            String letter = getItem(i).getInitialLetter();
            int section = list.size() - 1;
            if (list.get(section) != null && !list.get(section).equals(letter)) {
                list.add(letter);
                section++;
                positionOfSection.put(section, i);
            }
            sectionOfPosition.put(i, section);
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return positionOfSection.get(sectionIndex);
    }

    @Override
    public int getSectionForPosition(int position) {
        return sectionOfPosition.get(position);
    }


    protected class MyFilter extends Filter {
        List<FriendProfile> mOriginalList = null;

        public MyFilter(List<FriendProfile> myList) {
            this.mOriginalList = myList;
        }

        @Override
        protected synchronized FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mOriginalList == null) {
                mOriginalList = new ArrayList<FriendProfile>();
            }

            if (prefix == null || prefix.toString().trim().length() == 0) {
                results.values = copyUserList;
                results.count = copyUserList.size();
            } else {
                String prefixString = prefix.toString();
                final int count = mOriginalList.size();
                final ArrayList<FriendProfile> newValues = new ArrayList<FriendProfile>();
                for (int i = 0; i < count; i++) {
                    final FriendProfile user = mOriginalList.get(i);
                    String username = user.getName();

                    if (username.contains(prefixString)) {
                        newValues.add(user);
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected synchronized void publishResults(CharSequence constraint, FilterResults results) {
            friends.clear();
            friends.addAll((List<FriendProfile>) results.values);
            if (results.count > 0) {
                notiyfyByFilter = true;
                notifyDataSetChanged();
                notiyfyByFilter = false;
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    class ViewHolder {
        ImageView avatar;
        TextView nameView;
        TextView headerView;
    }
}
