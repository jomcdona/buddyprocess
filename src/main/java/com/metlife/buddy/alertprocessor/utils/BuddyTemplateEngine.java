package com.metlife.buddy.alertprocessor.utils;
import java.io.StringWriter;
 
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class BuddyTemplateEngine {

    public String buildAlertNotification()
    {
        String alertTemplatePath = System.getenv("alertTemplatePath");
        VelocityEngine ve = new VelocityEngine();
        ve.init();
         
        Template t = ve.getTemplate(alertTemplatePath);
         
        VelocityContext vc = new VelocityContext();
        vc.put("empname", "John Doe");
        vc.put("standardhours", "60");
        vc.put("bookedhours", "88");
        vc.put("buddyowner", "Rudy Daniels");

             
        StringWriter sw = new StringWriter();
        t.merge(vc, sw);

        //System.out.println(sw);

        return sw.toString();
         
        
    }
    
}
