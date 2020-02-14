package com.example.b_hu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RecommentAdapter extends ArrayAdapter<RecommentContent> {
    private int resourceID;
    public RecommentAdapter(Context context,int textViewID, List<RecommentContent> objects){
        super(context,textViewID,objects);
        resourceID=textViewID;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        RecommentContent recomment=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        TextView issue_sequence = (TextView)view.findViewById(R.id.recomment_listview_issue_sequence);
        TextView issue_exist = (TextView)view.findViewById(R.id.recomment_listview_issue_exist);
        TextView issue_quiz = (TextView)view.findViewById(R.id.recomment_listview_issue_quiz);
        TextView issue_quizzer = (TextView)view.findViewById(R.id.recomment_listview_issue_quizzer);
        TextView issue_quiz_describe = (TextView)view.findViewById(R.id.recomment_listview_quiz_issue_quiz_describe);
        issue_sequence.setText(recomment.getIssue_sequence());
        issue_exist.setText(recomment.getIssue_exist());
        issue_quiz.setText(recomment.getIssue_quiz());
        issue_quizzer.setText(recomment.getIssue_quizzer());
        issue_quiz_describe.setText(recomment.getIssue_quiz_describe());
        return view;
    }
}
