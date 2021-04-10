/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.classes;

import java.io.Serializable;

public class ExamSetting implements Serializable{
    private String courseCode;


    private String examinersName;
    private String examinersSurname;
    private String examStartTime;
    private String examFile;
    private int examDuration;
    private boolean internetEnabled;
    private boolean pmEnabled;
    private boolean usbEnabled;
    private String bannedSites;
    private String permittedFileExtensions;

    public ExamSetting() {
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }
    public String getExamFile() {
        return examFile;
    }

    public void setExamFile(String examFile) {
        this.examFile = examFile;
    }
    
    


    public String getExaminersName() {
        return examinersName;
    }

    public void setExaminersName(String examinersName) {
        this.examinersName = examinersName;
    }

    public String getExaminersSurname() {
        return examinersSurname;
    }

    public void setExaminersSurname(String examinersSurname) {
        this.examinersSurname = examinersSurname;
    }

    public String getExamStartTime() {
        return examStartTime;
    }

    public void setExamStartTime(String examStartTime) {
        this.examStartTime = examStartTime;
    }

    public int getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(int examDuration) {
        this.examDuration = examDuration;
    }

    public boolean isInternetEnabled() {
        return internetEnabled;
    }

    public void setInternetEnabled(boolean internetEnabled) {
        this.internetEnabled = internetEnabled;
    }

    public boolean isPmEnabled() {
        return pmEnabled;
    }

    public void setPmEnabled(boolean pmEnabled) {
        this.pmEnabled = pmEnabled;
    }

    public boolean isUsbEnabled() {
        return usbEnabled;
    }

    public void setUsbEnabled(boolean usbEnabled) {
        this.usbEnabled = usbEnabled;
    }

    public String getBannedSites() {
        return bannedSites;
    }

    public void setBannedSites(String bannedSites) {
        this.bannedSites = bannedSites;
    }

    public String getPermittedFileExtensions() {
        return permittedFileExtensions;
    }

    public void setPermittedFileExtensions(String permittedFileExtensions) {
        this.permittedFileExtensions = permittedFileExtensions;
    }

    
    
}
