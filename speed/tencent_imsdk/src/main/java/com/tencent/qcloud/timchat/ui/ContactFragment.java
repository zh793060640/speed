package com.tencent.qcloud.timchat.ui;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.leeone.leeonecore.constant.Constance;
import com.leeone.leeonecore.util.InterfaceUtil;
import com.leeone.leeonecore.util.ToastUtil;
import com.leeone.leeonecore.util.Utils;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.eventbean.ShowContactlist;
import com.tencent.qcloud.timchat.hxui.ContactItemView;
import com.tencent.qcloud.timchat.model.FriendProfile;
import com.tencent.qcloud.timchat.model.FriendshipInfo;
import com.tencent.qcloud.timchat.model.GroupInfo;
import com.tencent.qcloud.timchat.ui.customview.TemplateTitle;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * 联系人界面
 */
public class ContactFragment extends Fragment implements  View.OnClickListener, Observer {

    private View view;
    Map<String, List<FriendProfile>> friends;
    private ListView lvContact;
    private ContactItemView applicationItem;
    private ContactAdapter contactAdapter;
    protected ImageButton clearSearch;
    protected EditText query;
    protected TextView query1;
    private TemplateTitle title;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null){
            view = inflater.inflate(R.layout.fragment_contact, container, false);
            lvContact = (ListView) view.findViewById(R.id.lvContact);
            TemplateTitle title = (TemplateTitle) view.findViewById(R.id.contact_antionbar);
            //搜索框
            query = (EditText)view.findViewById(R.id.query);
            query1 = (TextView) view.findViewById(R.id.query1);
            clearSearch = (ImageButton) view.findViewById(R.id.search_clear);
initListener();

            friends = FriendshipInfo.getInstance().getFriends();
            FriendshipInfo.getInstance().addObserver(this);
            addHeaderView();
            contactAdapter = new ContactAdapter(FriendshipInfo.getInstance().getAllContacts(),getActivity());
            lvContact.setAdapter(contactAdapter);

        }
        return view;
    }

    private void initListener() {
        query.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null) {
                    return;
                }

                if (contactAdapter != null) {
                    contactAdapter.filter(s);
                }

                if (s.length() > 0) {
                    clearSearch.setVisibility(View.VISIBLE);
                    query1.setVisibility(View.GONE);
                } else {
                    clearSearch.setVisibility(View.INVISIBLE);
                    query1.setVisibility(View.VISIBLE);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });

        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query1.setVisibility(View.VISIBLE);
                query.getText().clear();
            }
        });

        title.setMoreImgAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchFriendActivity.class);
                getActivity().startActivity(intent);
                // showMoveDialog();
            }
        });
        title.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ShowContactlist(false));
            }
        });

        lvContact.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FriendProfile oEaseUser = contactAdapter.getItem(position);
                String username = oEaseUser.getIdentify();
                if (Utils.getUserDataBean().getImId().equals(username)) {
                    ToastUtil.makeToast("不能和自己聊天！");
                } else if (oEaseUser.getInitialLetter().equals("客服")) {
                    // demo中直接进入聊天页面，实际一般是进入用户详情页.聊天需要HxId
                    boolean isCustommer = false;
                    startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userId", username).putExtra
                            ("isCustomer", isCustommer));
                } else {
//                    Intent intent = new Intent(getActivity(), ShowUserInfoActivity.class);
//                    intent.putExtra(ShowUserInfoActivity.USERID, oEaseUser.getUserId());
//                    intent.putExtra(ShowUserInfoActivity.ISFRIEND, true);
//                    startActivity(intent);
                }
            }
        });

    }


    @Override
    public void onResume(){
        super.onResume();
        contactAdapter.notifyDataSetChanged();
    }



    @Override
    public void onClick(View view) {

    }

    private Dialog inviteDialog;
    private TextView addFriend, managerGroup,addGroup;

    private void showMoveDialog() {
        inviteDialog = new Dialog(getActivity(), R.style.dialog);
        inviteDialog.setContentView(R.layout.contact_more);
        addFriend = (TextView) inviteDialog.findViewById(R.id.add_friend);
        managerGroup = (TextView) inviteDialog.findViewById(R.id.manager_group);
        addGroup = (TextView) inviteDialog.findViewById(R.id.add_group);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchFriendActivity.class);
                getActivity().startActivity(intent);
                inviteDialog.dismiss();
            }
        });
        managerGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ManageFriendGroupActivity.class);
                getActivity().startActivity(intent);
                inviteDialog.dismiss();
            }
        });
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchGroupActivity.class);
                getActivity().startActivity(intent);
                inviteDialog.dismiss();
            }
        });
        Window window = inviteDialog.getWindow();
        window.setGravity(Gravity.TOP | Gravity.RIGHT);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setAttributes(lp);
        inviteDialog.show();
    }


    private void showGroups(String type){
        Intent intent = new Intent(getActivity(), GroupListActivity.class);
        intent.putExtra("type", type);
        getActivity().startActivity(intent);
    }

    /**
     * This method is called if the specified {@code Observable} object's
     * {@code notifyObservers} method is called (because the {@code Observable}
     * object has been updated.
     *
     * @param observable the {@link Observable} object.
     * @param data       the data passed to {@link Observable#notifyObservers(Object)}.
     */
    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof FriendshipInfo){
            //mGroupListAdapter.notifyDataSetChanged();
            contactAdapter.notifyDataSetChanged();
        }
    }

    public void addHeaderView(){
        View headeView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_header_hx_contacts, null);
        HeaderItemClickListener clickListener = new HeaderItemClickListener();
        applicationItem = (ContactItemView) headeView.findViewById(R.id.application_item);
        applicationItem.setOnClickListener(clickListener);

        if (TextUtils.equals(InterfaceUtil.getGETUI().getApptypeValue(), Constance.APP_TYPE_PARENT)) {
            headeView.findViewById(R.id.chat_organization_item).setVisibility(View.GONE);
        }

        headeView.findViewById(R.id.group_item).setOnClickListener(clickListener);
        headeView.findViewById(R.id.chat_organization_item).setOnClickListener(clickListener);
        headeView.findViewById(R.id.chat_system_item).setOnClickListener(clickListener);
        headeView.findViewById(R.id.chat_room_item).setOnClickListener(clickListener);

        lvContact.addHeaderView(headeView);
    }

    protected class HeaderItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.application_item) {// 进入申请与通知页面
             //   startActivity(new Intent(getActivity(), NewFriendsMsgActivity.class));

            } else if (i == R.id.group_item) {// 进入群聊列表页面
                showGroups(GroupInfo.publicGroup);

            } else if (i == R.id.chat_room_item) {//进入聊天室列表页面
                showGroups(GroupInfo.chatRoom);

            } else if (i == R.id.chat_organization_item) {//组织架构
                Intent intent = new Intent();
                intent.setClassName(getActivity(), "com.leeone.common.tp.activity.DirectoriesActivity");
                intent.putExtra("directorytag", "contact");
                intent.putExtra("isOrganiza", true);
                startActivity(intent);
            } else if (i == R.id.chat_system_item) {//乐望教育
                startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userId", "admin"));
            } else {
            }
        }

    }

}
