/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.classes;

import java.io.Serializable;
import java.util.ArrayList;


public class BannedSites implements Serializable {
        ArrayList<String> sites;

    public BannedSites(ArrayList<String> sites) {
        this.sites = sites;
    }

    public ArrayList<String> getSites() {
        return sites;
    }

    public void setSites(ArrayList<String> sites) {
        this.sites = sites;
    }

        
}
