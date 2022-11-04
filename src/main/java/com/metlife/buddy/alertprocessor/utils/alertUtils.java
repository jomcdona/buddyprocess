package com.metlife.buddy.alertprocessor.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metlife.buddy.alertprocessor.model.BuddyProfile;
import com.metlife.buddy.alertprocessor.model.OutOfComplianceUser;
//import com.metlife.buddy.alertprocessor.utils.ExtractOffsets;


public class alertUtils 
{
    public static BuddyProfile createBuddyFromInput(String jsonBuddy)
    {
        BuddyProfile bp = null;
        try
        {
            System.out.println("Buddy Received: " + jsonBuddy);
            ObjectMapper objectMapper = new ObjectMapper();
            bp = objectMapper.readValue(jsonBuddy, BuddyProfile.class);
        }

        catch (JsonProcessingException jpe)
        {
            System.out.println("Error processing JSON " + jpe.getMessage());
        }

        return bp;
    }

    public static int processAlerts(BuddyProfile bp)
    {
        //default return to incomplete to keep process alive
        int retval = 0; 
        //obtain group divsion from buddy profile
        int divisionID = Integer.parseInt(bp.getDivisionId());
        //obtain the day of the month for group specific cut off day from buddy profile
        int forceCutOffDay = bp.getForceCutOffDay();

        //obtain current system day of the month to use to base determination of force cut off of process
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        
        System.out.println("Processing for Division " + divisionID + " Day of month " + dayOfMonth + " forceDate " + forceCutOffDay);
        try
        {
            String ExtractFilePath = System.getenv("ExtractPath");
            FileReader fr = new FileReader(ExtractFilePath);
            String curline;
            if (fr != null)
            {
                BufferedReader br = new BufferedReader(fr);
                int lines_read = 0;
                ArrayList<OutOfComplianceUser> oocUsers = new ArrayList<OutOfComplianceUser>();
                while ((curline = br.readLine()) != null)
                {
                    if (lines_read > 0)
                    {
                        String[] lineentries = curline.split(";");
                        if (lineentries.length > 0)
                        {
                            int curDivision = Integer.parseInt(lineentries[ExtractOffsets.DIVISION_OFFSET]);
                            if (curDivision == divisionID)
                            {
                                int standard_hours = Integer.parseInt(lineentries[ExtractOffsets.STANDARDHOURS_OFFSET]);
                                int booked_hours = ((lineentries[ExtractOffsets.BOOKEDHOURS_OFFSET].length() > 0)) ? 
                                Integer.parseInt(lineentries[ExtractOffsets.BOOKEDHOURS_OFFSET]):0;
                                int exempt = Integer.parseInt(lineentries[ExtractOffsets.EMP_EXEMPT_OFFSET]);
                                boolean out_of_compliance = ((booked_hours < standard_hours) && (exempt == 0));
                                if (out_of_compliance)
                                {
                                    OutOfComplianceUser oocu = new OutOfComplianceUser(lineentries[ExtractOffsets.EMPID_OFFSET], 
                                    lineentries[ExtractOffsets.EMPNAME_OFFSET], lineentries[ExtractOffsets.EMPEMAIL_OFFSET], 
                                    lineentries[ExtractOffsets.MANAGERNAME_OFFSET], standard_hours, booked_hours);

                                    oocUsers.add(oocu);

                                } //end if user is out of compliance
                            } //end divison check
                        }//end line entry length check
                    }
                    ++lines_read;
                } //end while
                
                //close file i/o
                br.close();
                fr.close();
                boolean forceProcssClose = (dayOfMonth > forceCutOffDay);

                if (forceProcssClose && (oocUsers.size() > 0))
                {
                    sendProcessIncompleteNotification(bp);
                    System.out.println("Process is being forceclosed");
                    retval = 1;
                }
                else if (oocUsers.size() > 0)
                {
                    sendAlert(bp, oocUsers);
                    retval = 0;
                }
                else //all time entered successfully
                {
                    sendProcessCompleteNotification(bp);
                    retval = 1;
                }
            }
        }

        catch(FileNotFoundException fne)
        {
            //todo: use log4j to output this to a log file
            //note: by intent this only needs to be logged for trouble shooting
            //versus more intentional exeption handling.  The process should return a 
            //200 ok response with a value of 0 to indicate that the workflow process
            //that invoked this service needs to stay running and make another attempt
            //to access the file at the next scheduled interval.
            System.out.println("File Not Located " + fne.getMessage());
        }

        catch(IOException ioe)
        {
            //todo: use log4j to output this to a log file
            //note: by intent this only needs to be logged for trouble shooting
            //versus more intentional exeption handling.  The process should return a 
            //200 ok response with a value of 0 to indicate that the workflow process
            //that invoked this service needs to stay running and make another attempt
            //to access the file at the next scheduled interval.
            System.out.println("Error reading file " + ioe.getMessage());
        } 
        
        return retval;
    }

    private static void sendAlert(BuddyProfile bp, ArrayList<OutOfComplianceUser> oocUsers)
    {
        System.out.println("Sending Alert to users\n");
        for (int i = 0; i < oocUsers.size(); ++i)
        {
            OutOfComplianceUser oocu = oocUsers.get(i);
            System.out.println(oocu.getEmpEmail());

        }

        BuddyTemplateEngine bte = new BuddyTemplateEngine(bp);
        String alertBody = bte.buildAlertNotification();
        System.out.println(alertBody);
    }

    private static void sendProcessIncompleteNotification(BuddyProfile bp)
    {
        System.out.println("Here is where a process INCOMPLETE notifcation will be sent to group leaders at " + bp.getLeaders());
    }

    private static void sendProcessCompleteNotification(BuddyProfile bp)
    {
        System.out.println("Here is where a process COMPLETE notifcation will be sent to group leaders at " + bp.getLeaders());
    }
}
