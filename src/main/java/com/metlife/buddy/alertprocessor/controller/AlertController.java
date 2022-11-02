package com.metlife.buddy.alertprocessor.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.metlife.buddy.alertprocessor.model.BuddyProfile;
import com.metlife.buddy.alertprocessor.utils.alertUtils;

@RestController
//@ResponseBody
public class AlertController {
    private static final Class<BuddyProfile> BuddyProfile = null;

    @PostMapping(path="/processAlerts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    String processAlerts(@RequestBody String buddy)
    {
        BuddyProfile bp = alertUtils.createBuddyFromInput(buddy);
        if (bp != null)
        {
            System.out.println("Buddy Received " + bp.toString());
            int processStatus = alertUtils.processAlerts(bp);
            return String.valueOf(processStatus);
        }
        else{
            return "Problems here";
        }
    }
}
