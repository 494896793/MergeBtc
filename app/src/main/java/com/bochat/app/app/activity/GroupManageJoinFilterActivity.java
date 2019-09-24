package com.bochat.app.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bochat.app.R;
import com.bochat.app.common.contract.conversation.GroupManageJoinFilterContract;
import com.bochat.app.common.router.RouterGroupManageJoinFilter;
import com.bochat.app.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Author      : FJ
 * CreateDate  : 2019/04/25 17:44
 * Description :
 */
@Route(path = RouterGroupManageJoinFilter.PATH)
public class GroupManageJoinFilterActivity extends BaseActivity<GroupManageJoinFilterContract.Presenter> implements GroupManageJoinFilterContract.View{
    private int selectPosition = -1;//用于记录用户选择的变量
    private int[] joinTypeInItem = {R.id.join_type_1,R.id.join_type_2,R.id.join_type_3};
    private int[] imageResource = {R.mipmap.administrators_choice,R.mipmap.administrators_choice_sel};
    public  int joinType;
    public  String questionText1 = "";
    public  String questionText2 = "";
    public  String answerText = "";
    public  EditText question1;
    public  EditText question2 ;
    public  EditText answer;

    public static final int TYPE_ALL = 1;
    public static final int TYPE_INVITE = 2;
    public static final int TYPE_NONE = 3;
    public static final int TYPE_CHECK_ANSWER = 6;
    public static final int TYPE_MANAGER_AGREE = 5;
    public static final int TYPE_SEND_ANSWER = 4;

    @Inject
    GroupManageJoinFilterContract.Presenter presenter;

    @BindView(R.id.cv_group_manage_join_type_list)
    ListView listView;
    @BindView(R.id.commit_join_type)
    Button commitType;
    
    private GroupManageJoinFilterAdapter adapter;
    
    private ArrayList<String> list = new ArrayList<>();
    
    @Override
    protected void initInjector() {
        getActivityComponent().inject(this);
    }

    @Override
    protected GroupManageJoinFilterContract.Presenter initPresenter() {
        return presenter;
    }

    @Override
    protected void setRootView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cv_group_manage_join_filter);
    }

    @Override
    protected void initWidget() {

        commitType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(question1 != null){
                    questionText1 = question1.getText()+"";
                }
                if(question2 != null){
                    questionText2 = question2.getText()+"";
                }
                if(answer != null){
                    answerText = answer.getText()+"";
                }
                if(joinType == TYPE_SEND_ANSWER){
                    presenter.setJoinType(joinType, questionText1, "");
                } else if(joinType == TYPE_CHECK_ANSWER){
                    presenter.setJoinType(joinType, questionText2, answerText);
                } else {
                    presenter.setJoinType(joinType,"","");
                }
            }
        });

        adapter = new GroupManageJoinFilterAdapter(list,this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = position;
                adapter.notifyDataSetChanged();
                if (position == 1){

                } else {
                    if (position == 0) {
                        joinType = TYPE_ALL;
                    } else if(position == 2){
                        joinType = TYPE_INVITE;
                    } else {
                        joinType = TYPE_NONE;
                    }
                }
            }
        });
    }

    @Override
    public void updateJoinType(int type) {
        joinType = type;
        list.clear();
        list.add("允许任何人加入群聊");
        list.add("需要验证加入群聊");
        list.add("只允许群成员邀请入群");
        list.add("不允许任何人加入群聊");
        adapter.notifyDataSetChanged();
        switch (type) {
            case TYPE_ALL:
                selectPosition = 0;
            break;
            case TYPE_MANAGER_AGREE:
            case TYPE_SEND_ANSWER:
            case TYPE_CHECK_ANSWER:
                selectPosition = 1;
            break;
            case TYPE_INVITE:
                selectPosition = 2;
            break;
            case TYPE_NONE:
                selectPosition = 3;
            break;

            default:
                break;
        }
    }

    @Override
    public void updateQuestion(String question) {
        questionText1 = question;
        question1.setText(questionText1);
    }

    @Override
    public void updateQuestionAndAnswer(String question, String answer) {
        questionText2 = question;
        answerText = answer;
        question2.setText(questionText2);
        this.answer.setText(answerText);
    }

    class GroupManageJoinFilterAdapter extends BaseAdapter {
        private List<String> mDate;
        private Map<Integer,Boolean> isCheckMap = new HashMap<>();
        private LayoutInflater mInflater;

        public GroupManageJoinFilterAdapter(List<String> mDate, Context mContext) {
            this.mDate = mDate;
            for (int i = 0;i<mDate.size();i++){
                isCheckMap.put(i,false);
            }
            mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mDate.size();
        }

        @Override
        public Object getItem(int position) {
            return mDate.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.item_group_manage_join_type,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.strType = (TextView)convertView.findViewById(R.id.group_join_type_title);
                viewHolder.checkImag = (ImageView)convertView.findViewById(R.id.group_join_type_icon);
                viewHolder.itemLinearLayout = (LinearLayout)convertView.findViewById(R.id.layout_item_join);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }

            viewHolder.strType.setText(mDate.get(position));

            if(selectPosition == position){
                viewHolder.checkImag.setVisibility(View.VISIBLE);
                if (position == 1) {
                    viewHolder.itemLinearLayout.setVisibility(View.VISIBLE);
                    final ImageView check0 = viewHolder.itemLinearLayout.findViewById(R.id.check_1);
                    final ImageView check1 = viewHolder.itemLinearLayout.findViewById(R.id.check_2);
                    final ImageView check2 = viewHolder.itemLinearLayout.findViewById(R.id.check_3);
                    check0.setImageResource(imageResource[0]);
                    check1.setImageResource(imageResource[0]);
                    check2.setImageResource(imageResource[0]);
                    if(joinType == TYPE_MANAGER_AGREE){
                        check0.setImageResource(imageResource[1]);
                    } else if(joinType == TYPE_SEND_ANSWER){
                        check1.setImageResource(imageResource[1]);
                    } else if(joinType == TYPE_CHECK_ANSWER){
                        check2.setImageResource(imageResource[1]);
                    }

                    RelativeLayout relativeLayout0 = viewHolder.itemLinearLayout.findViewById(joinTypeInItem[0]);
                    RelativeLayout relativeLayout1 = viewHolder.itemLinearLayout.findViewById(joinTypeInItem[1]);
                    RelativeLayout relativeLayout2 = viewHolder.itemLinearLayout.findViewById(joinTypeInItem[2]);

                    question1 = viewHolder.itemLinearLayout.findViewById(R.id.edit_question_1);
                    question2 = viewHolder.itemLinearLayout.findViewById(R.id.edit_question_2);
                    answer = viewHolder.itemLinearLayout.findViewById(R.id.answer_edit);

                    question1.setText(questionText1);
                    question2.setText(questionText2);
                    answer.setText(answerText);

                    relativeLayout0.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            joinType = TYPE_MANAGER_AGREE;
                            check0.setImageResource(imageResource[1]);
                            check1.setImageResource(imageResource[0]);
                            check2.setImageResource(imageResource[0]);
                        }
                    });
                    relativeLayout1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            joinType = TYPE_SEND_ANSWER;
                            check1.setImageResource(imageResource[1]);
                            check0.setImageResource(imageResource[0]);
                            check2.setImageResource(imageResource[0]);
                        }
                    });
                    relativeLayout2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            joinType = TYPE_CHECK_ANSWER;
                            check2.setImageResource(imageResource[1]);
                            check0.setImageResource(imageResource[0]);
                            check1.setImageResource(imageResource[0]);
                        }
                    });

                } else {
                    viewHolder.itemLinearLayout.setVisibility(View.GONE);
                }
            } else {
                viewHolder.checkImag.setVisibility(View.GONE);
                viewHolder.itemLinearLayout.setVisibility(View.GONE);
            }
            return convertView;
        }

        public class ViewHolder {
            TextView strType;
            ImageView checkImag;
            LinearLayout itemLinearLayout;
        }
    }
}