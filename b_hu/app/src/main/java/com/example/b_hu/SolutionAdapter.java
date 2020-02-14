package com.example.b_hu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SolutionAdapter extends ArrayAdapter<SolutionContent> {
    private int resourceID;
    public SolutionAdapter(Context context, int textViewID, List<SolutionContent> objects){
        super(context,textViewID,objects);
        resourceID=textViewID;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        SolutionContent solutionConnet=getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        TextView solution_possessor = (TextView)view.findViewById(R.id.issue_listview_possessor);
        TextView solution_answer = (TextView)view.findViewById(R.id.issue_listview_answer);
        TextView solution_sequence = (TextView)view.findViewById(R.id.issue_listview_sequence);
        TextView solution_floor = (TextView)view.findViewById(R.id.issue_listview_floor);
        TextView solution_praise = (TextView)view.findViewById(R.id.issue_listview_praise);
        TextView solution_exist = (TextView)view.findViewById(R.id.issue_listview_exist);
        solution_possessor.setText(solutionConnet.getSolution_possessor());
        solution_answer.setText(solutionConnet.getSolution_answer());
        solution_sequence.setText(solutionConnet.getSolution_sequence());
        solution_floor.setText(solutionConnet.getSolution_floor());
        solution_praise.setText(solutionConnet.getSolution_praise());
        solution_exist.setText(solutionConnet.getSolution_exist());
        return view;
    }
}