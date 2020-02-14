package com.example.b_hu;

import java.io.Serializable;

public class SolutionContent implements Serializable {
    private String solution_possessor;
    private String solution_answer;
    private String solution_sequence;
    private String solution_floor;
    private String solution_praise;
    private String solution_exist;
    public SolutionContent(String solution_possessor,String solution_answer,String solution_sequence,String solution_floor,String solution_praise,String solution_exist){
        this.solution_possessor=solution_possessor;
        this.solution_answer =solution_answer;
        this.solution_sequence=solution_sequence;
        this.solution_floor=solution_floor;
        this.solution_praise=solution_praise;
        this.solution_exist=solution_exist;
    }
    public String getSolution_possessor(){
        return solution_possessor;
    }
    public String getSolution_answer(){
        return solution_answer;
    }
    public String getSolution_sequence() {
        return solution_sequence;
    }
    public String getSolution_floor() {
        return solution_floor;
    }
    public String getSolution_praise() {
        return solution_praise;
    }
    public String getSolution_exist() {
        return solution_exist;
    }

    public void setSolution_possessor(){
        this.solution_possessor=solution_possessor;
    }
    public void setSolution_messgae(){
        this.solution_answer = solution_answer;
    }
    public void setSolution_sequence(){
        this.solution_sequence=solution_sequence;
    }
    public void setSolution_floor(){
        this.solution_floor=solution_floor;
    }
    public void setSolution_praise(){
        this.solution_praise=solution_praise;
    }
    public void setSolution_exist(){
        this.solution_exist=solution_exist;
    }
}
