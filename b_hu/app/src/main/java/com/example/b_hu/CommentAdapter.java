package com.example.b_hu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<CommentContent> {
    private int resourceID;
    public CommentAdapter(Context context, int textViewID, List<CommentContent> objects){
        super(context,textViewID,objects);
        resourceID=textViewID;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        CommentContent comment=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        TextView comment_possessor = (TextView)view.findViewById(R.id.solution_listview_comment_possessor);
        TextView comment_discuss = (TextView)view.findViewById(R.id.solution_listview_comment_discuss);
        TextView comment_sequence = (TextView)view.findViewById(R.id.solution_listview_comment_sequence);
        TextView comment_floor = (TextView)view.findViewById(R.id.solution_listview_comment_floor);
        TextView comment_tier = (TextView)view.findViewById(R.id.solution_listview_comment_tier);
        TextView comment_little_tier = (TextView)view.findViewById(R.id.solution_listview_comment_little_tier);
        TextView comment_star = (TextView)view.findViewById(R.id.solution_listview_comment_star);
        TextView comment_praise = (TextView)view.findViewById(R.id.solution_listview_comment_praise);
        TextView comment_exist = (TextView)view.findViewById(R.id.solution_listview_comment_exist);
        comment_possessor.setText(comment.getComment_possessor());
        comment_discuss.setText(comment.getComment_discuss());
        comment_sequence.setText(comment.getComment_sequence());
        comment_floor.setText(comment.getComment_floor());
        comment_tier.setText(comment.getComment_tier());
        comment_little_tier.setText(comment.getComment_little_tier());
        comment_star.setText(comment.getComment_star());
        comment_praise.setText(comment.getComment_praise());
        comment_exist.setText(comment.getComment_exist());
        return view;
    }
}