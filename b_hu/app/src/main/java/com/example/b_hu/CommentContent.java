package com.example.b_hu;

import java.io.Serializable;

public class CommentContent implements Serializable {
    private String comment_possessor;
    private String comment_discuss;
    private String comment_sequence;
    private String comment_floor;
    private String comment_tier;
    private String comment_little_tier;
    private String comment_star;
    private String comment_praise;
    private String comment_exist;
    public CommentContent(String comment_possessor,String comment_discuss,String comment_sequence,String comment_floor,String comment_tier,String comment_little_tier,String comment_star,String comment_praise,String comment_exist){
        this.comment_possessor=comment_possessor;
        this.comment_discuss=comment_discuss;
        this.comment_sequence=comment_sequence;
        this.comment_floor=comment_floor;
        this.comment_tier=comment_tier;
        this.comment_little_tier=comment_little_tier;
        this.comment_star=comment_star;
        this.comment_praise=comment_praise;
        this.comment_exist=comment_exist;
    }
    public String getComment_possessor(){
        return comment_possessor;
    }
    public String getComment_discuss(){
        return comment_discuss;
    }
    public String getComment_sequence() {
        return comment_sequence;
    }
    public String getComment_floor() {
        return comment_floor;
    }
    public String getComment_tier() {
        return comment_tier;
    }
    public String getComment_little_tier(){
        return comment_little_tier;
    }
    public String getComment_star() {
        return comment_star;
    }
    public String getComment_praise() {
        return comment_praise;
    }
    public String getComment_exist() {
        return comment_exist;
    }

    public void setComment_possessor(){
        this.comment_possessor=comment_possessor;
    }
    public void setComment_discuss(){
        this.comment_discuss=comment_discuss;
    }
    public void setComment_sequence(){
        this.comment_sequence=comment_sequence;
    }
    public void setComment_floor(){
        this.comment_floor=comment_floor;
    }
    public void setComment_tier(){
        this.comment_tier=comment_tier;
    }
    public void setComment_little_tier(){
        this.comment_little_tier=comment_little_tier;
    }
    public void setComment_star(){
        this.comment_star=comment_star;
    }
    public void setComment_praise(){
        this.comment_praise=comment_praise;
    }
    public void setComment_exist(){
        this.comment_exist=comment_exist;
    }
}
