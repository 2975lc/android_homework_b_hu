package com.example.b_hu;

import java.io.Serializable;

public class StateContent implements Serializable {
    private String state;
    public StateContent(String state){
        this.state=state;
    }
    public String getState(){
        return state;
    }

    public void setState(){
        this.state=state;
    }
}