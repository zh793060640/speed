package com.tencent.qcloud.timchat.model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.qcloud.timchat.MyApplication;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ChatAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 群tips消息
 */
public class GroupTipMessage extends Message {


    public GroupTipMessage(TIMMessage message) {
        this.message = message;
    }


    /**
     * 显示消息
     *
     * @param viewHolder 界面样式
     * @param context    显示消息的上下文
     */
    @Override
    public void showMessage(ChatAdapter.ViewHolder viewHolder, Context context) {
        viewHolder.leftPanel.setVisibility(View.GONE);
        viewHolder.rightPanel.setVisibility(View.GONE);
        viewHolder.systemMessage.setVisibility(View.VISIBLE);
        //viewHolder.systemMessage.setText(getSummary());
        setSystemMessage(viewHolder.systemMessage);

    }

    /**
     * 获取消息摘要
     */
    @Override
    public String getSummary() {
        final TIMGroupTipsElem e = (TIMGroupTipsElem) message.getElement(0);
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, TIMGroupMemberInfo>> iterator = e.getChangedGroupMemberInfo().entrySet().iterator();
        switch (e.getTipsType()) {
            case CancelAdmin:
            case SetAdmin:
                return MyApplication.getContext().getString(R.string.summary_group_admin_change);
            case Join:
                while (iterator.hasNext()) {
                    Map.Entry<String, TIMGroupMemberInfo> item = iterator.next();
                    stringBuilder.append(getName(item.getValue()));
                    stringBuilder.append(" ");
                }
                return stringBuilder +
                        MyApplication.getContext().getString(R.string.summary_group_mem_add);
            case Kick:
                return e.getUserList().get(0) +
                        MyApplication.getContext().getString(R.string.summary_group_mem_kick);
            case ModifyMemberInfo:
                while (iterator.hasNext()) {
                    Map.Entry<String, TIMGroupMemberInfo> item = iterator.next();
                    stringBuilder.append(getName(item.getValue()));
                    stringBuilder.append(" ");
                }
                return stringBuilder +
                        MyApplication.getContext().getString(R.string.summary_group_mem_modify);
            case Quit:
                return e.getOpUser() +
                        MyApplication.getContext().getString(R.string.summary_group_mem_quit);
            case ModifyGroupInfo:
                return MyApplication.getContext().getString(R.string.summary_group_info_change);
        }
        return "";
    }


    public void setSystemMessage(TextView tv) {

        final TIMGroupTipsElem e = (TIMGroupTipsElem) message.getElement(0);
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, TIMGroupMemberInfo>> iterator = e.getChangedGroupMemberInfo().entrySet().iterator();
        switch (e.getTipsType()) {
            case CancelAdmin:
            case SetAdmin:
                tv.setText(MyApplication.getContext().getString(R.string.summary_group_admin_change));
            case Join:
                while (iterator.hasNext()) {
                    Map.Entry<String, TIMGroupMemberInfo> item = iterator.next();
                    stringBuilder.append(getName(item.getValue()));
                    stringBuilder.append(" ");
                    getUsersProfile(item.getValue().getUser(),MyApplication.getContext().getString(R.string.summary_group_mem_add),tv);
                }
//                return stringBuilder +
//                        MyApplication.getContext().getString(R.string.summary_group_mem_add);
            case Kick:

                tv.setText(e.getUserList().get(0) +
                        MyApplication.getContext().getString(R.string.summary_group_mem_kick));

            case ModifyMemberInfo:
                while (iterator.hasNext()) {
                    Map.Entry<String, TIMGroupMemberInfo> item = iterator.next();
                    stringBuilder.append(getName(item.getValue()));
                    stringBuilder.append(" ");
                    getUsersProfile(item.getValue().getUser(),MyApplication.getContext().getString(R.string.summary_group_mem_modify),tv);
                }
             //   tv.setText(MyApplication.getContext().getString(R.string.summary_group_mem_modify));

            case Quit:
                tv.setText(MyApplication.getContext().getString(R.string.summary_group_mem_quit));

            case ModifyGroupInfo:
                tv.setText(MyApplication.getContext().getString(R.string.summary_group_info_change));

        }

    }

    /**
     * 保存消息或消息文件
     */
    @Override
    public void save() {

    }

    private String getName(TIMGroupMemberInfo info) {
        if (info.getNameCard().equals("")) {
            return info.getUser();
        }
        return info.getNameCard();
    }

    public void getUsersProfile(String id, final String end, final TextView tvContent) {
        List<String> users = new ArrayList<>();
        users.add(id);
        TIMFriendshipManager.getInstance().getUsersProfile(users, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int code, String desc) {
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                Log.e(TAG, "getUsersProfile failed: " + code + " desc");
            }

            @Override
            public void onSuccess(List<TIMUserProfile> result) {
                Log.e(TAG, "getUsersProfile succ");
                for (TIMUserProfile res : result) {
                    Log.e(TAG, "identifier: " + res.getIdentifier() + " nickName: " + res.getNickName()
                            + " remark: " + res.getRemark());
                    tvContent.setText(res.getNickName() + end);
                }
            }
        });
    }
}
