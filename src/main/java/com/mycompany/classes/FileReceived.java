/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.classes;

import java.io.Serializable;

public class FileReceived implements Serializable {
    String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public FileReceived(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "FileReceived{" + "filename=" + filename + '}';
    }

    
}
