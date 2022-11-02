package com.metlife.buddy.alertprocessor.model;

import java.util.Date;

public class BuddyProfile {
    String name;
    String owner;
    String divisionId;
    String leaders;
    String notificationTemplate;
    String alertTemplate;
    String notificationDL;
    String completionDL;
    int forceCutOffDay;
    
   
    public BuddyProfile()
    {

    }
    public BuddyProfile(String name, String owner, String divisionId, String leaders, String notificationTemplate,
            String alertTemplate, String notificationDL, String completionDL, int forceCutOffDay) 
    {
        this.name = name;
        this.owner = owner;
        this.divisionId = divisionId;
        this.leaders = leaders;
        this.notificationTemplate = notificationTemplate;
        this.alertTemplate = alertTemplate;
        this.notificationDL = notificationDL;
        this.completionDL = completionDL;
        this.forceCutOffDay = forceCutOffDay;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getDivisionId() {
        return divisionId;
    }
    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }
    public String getLeaders() {
        return leaders;
    }
    public void setLeaders(String leaders) {
        this.leaders = leaders;
    }
    public String getNotificationTemplate() {
        return notificationTemplate;
    }
    public void setNotificationTemplate(String notificationTemplate) {
        this.notificationTemplate = notificationTemplate;
    }
    public String getAlertTemplate() {
        return alertTemplate;
    }
    public void setAlertTemplate(String alertTemplate) {
        this.alertTemplate = alertTemplate;
    }
    public String getNotificationDL() {
        return notificationDL;
    }
    public void setNotificationDL(String notificationDL) {
        this.notificationDL = notificationDL;
    }
    public String getCompletionDL() {
        return completionDL;
    }
    public void setCompletionDL(String completionDL) {
        this.completionDL = completionDL;
    }

    public int getForceCutOffDay() {
        return forceCutOffDay;
    }
    public void setForceCutOffDay(int forceCutOffDay) {
        this.forceCutOffDay = forceCutOffDay;
    }
    @Override
    public String toString() {
        return "BuddyProfile [name=" + name + ", owner=" + owner + ", divisionId=" + divisionId + ", leaders=" + leaders
                + ", notificationTemplate=" + notificationTemplate + ", alertTemplate=" + alertTemplate
                + ", notificationDL=" + notificationDL + ", completionDL=" + completionDL + ", forceCutOffDay="
                + forceCutOffDay + "]";
    }
    
}
