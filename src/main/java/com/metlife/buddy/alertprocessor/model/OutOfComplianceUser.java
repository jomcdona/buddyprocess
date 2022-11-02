package com.metlife.buddy.alertprocessor.model;

public class OutOfComplianceUser 
{
    String empId;
    String name;
    String empEmail;
    String mgrEmail;
    int bookedHours;
    int expectedHours;
    
    public OutOfComplianceUser(String empId, String name, String empEmail, String mgrEmail, int bookedHours,
            int expectedHours) {
        this.empId = empId;
        this.name = name;
        this.empEmail = empEmail;
        this.mgrEmail = mgrEmail;
        this.bookedHours = bookedHours;
        this.expectedHours = expectedHours;
    }
    public String getEmpId() {
        return empId;
    }
    public void setEmpId(String empId) {
        this.empId = empId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmpEmail() {
        return empEmail;
    }
    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }
    public String getMgrEmail() {
        return mgrEmail;
    }
    public void setMgrEmail(String mgrEmail) {
        this.mgrEmail = mgrEmail;
    }
    public int getBookedHours() {
        return bookedHours;
    }
    public void setBookedHours(int bookedHours) {
        this.bookedHours = bookedHours;
    }
    public int getExpectedHours() {
        return expectedHours;
    }
    public void setExpectedHours(int expectedHours) {
        this.expectedHours = expectedHours;
    }
    
}
