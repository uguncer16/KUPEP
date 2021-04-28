/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.classes;

import java.io.Serializable;


public class TimeRemaining implements Serializable{
    private String timeRemaing;

    public TimeRemaining(String timeRemaing) {
        this.timeRemaing = timeRemaing;
    }

    public String getTimeRemaing() {
        return timeRemaing;
    }

    @Override
    public String toString() {
        return "TimeRemaining{" + "timeRemaing=" + timeRemaing + '}';
    }


    
}
