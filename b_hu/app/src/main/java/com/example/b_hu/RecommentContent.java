package com.example.b_hu;

import java.io.Serializable;

public class RecommentContent implements Serializable {
    private String issue_sequence;
    private String issue_exist;
    private String issue_quiz;
    private String issue_quizzer;
    private String issue_quiz_describe;
    public RecommentContent(String issue_sequence,String issue_exist,String issue_quiz,String issue_quizzer,String issue_quiz_describe){
        this.issue_sequence=issue_sequence;
        this.issue_exist=issue_exist;
        this.issue_quiz=issue_quiz;
        this.issue_quizzer=issue_quizzer;
        this.issue_quiz_describe=issue_quiz_describe;
    }
    public String getIssue_sequence(){
        return issue_sequence;
    }
    public String getIssue_exist(){
        return issue_exist;
    }
    public String getIssue_quiz() {
        return issue_quiz;
    }
    public String getIssue_quizzer() {
        return issue_quizzer;
    }
    public String getIssue_quiz_describe() {
        return issue_quiz_describe;
    }

    public void setIssue_sequence(){
        this.issue_sequence=issue_sequence;
    }
    public void setIssue_exist(){
        this.issue_sequence=issue_exist;
    }
    public void setIssue_quiz(){
        this.issue_sequence=issue_quiz;
    }
    public void setIssue_quizzer(){
        this.issue_sequence=issue_quizzer;
    }
    public void setIssue_quiz_describe(){
        this.issue_sequence=issue_quiz_describe;
    }
}
