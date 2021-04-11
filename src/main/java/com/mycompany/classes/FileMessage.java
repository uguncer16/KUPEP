/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.classes;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.commons.io.FileUtils.readFileToByteArray;


public class FileMessage implements Serializable {
    File file;
    String username; 
    byte[] bytes;
    
    public FileMessage(File file){
        this.file = file;
        this.username = "";
        try {
            bytes = readFileToByteArray(file);
        } catch (IOException ex) {
            Logger.getLogger(FileMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public FileMessage(File file, String username){
        this.file = file;
        this.username = username;
        try {
            bytes = readFileToByteArray(file);
        } catch (IOException ex) {
            Logger.getLogger(FileMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


    public String getUsername() {
        return username;
    }


    

    public byte[] getBytes() {
        return bytes;
    }

    public File getFile() {
        return file;
    }


    
}
