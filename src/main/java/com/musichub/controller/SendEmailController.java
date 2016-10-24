package com.musichub.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class SendEmailController {
	@Autowired
	private JavaMailSender mailSender;
	
	
	public SendEmailController()
	{
		System.out.println("inside controller");
	}
	@RequestMapping("/")
	public String helloWorld()
	{
		return "index";
	}
     @RequestMapping("/index")
     public String gotoIndex()
     {
    	 return "index";
     }
     
     @RequestMapping("/result")
     public String gotoResult()
     {
    	 return "result";
     }
     @RequestMapping("/errPage")
     public String gotoError()
     {
    	 return "errPage";
     }
     
     /*@RequestMapping(value="sendEmail",method=RequestMethod.POST)
	public String sendMail(HttpServletRequest request)
	{
		String recipientAddress = request.getParameter("recipient");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
         
        // prints debug info
        System.out.println("To: " + recipientAddress);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
         
        // creates a simple e-mail object
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
         
        // sends the e-mail
        mailSender.send(email);
         
        // forwards to the view named "Result"
        return "result";
	}*/
    /* 
     @RequestMapping(value="sendEmail",method=RequestMethod.POST)
     public String tosendEmail(String recipientAddress, String fromAddress, String subject, String msgBody,HttpServletRequest request) 
     {
    	 try
    	 {
    		 
    	 
    	recipientAddress = request.getParameter("recipient");
        subject = request.getParameter("subject");
         msgBody = request.getParameter("message");
        //fromAddress=request.getParameter("sender");
         fromAddress="niyamath.noori@gmail.com";
          
         // prints debug info
         System.out.println("To: " + recipientAddress);
         System.out.println("Subject: " + subject);
         System.out.println("Message: " + msgBody);
         System.out.println("Sender:"+fromAddress);
    	 
    	 
    	 SimpleMailMessage msg = new SimpleMailMessage();
 		msg.setFrom(fromAddress);
 		msg.setTo(recipientAddress);
 		msg.setSubject(subject);
 		msg.setText(msgBody);
 		mailSender.send(msg);
    	 }
    	 catch(Exception ex)
    	 {
    		 System.out.println("Exception occurred..."+ex);
    	 }
 		return "result";
 	}*/
     
     @RequestMapping(value="sendEmail",method=RequestMethod.POST)
     public String sendEmail(HttpServletRequest request,
             final @RequestParam CommonsMultipartFile attachFile) {
  
         // reads form input
         final String emailTo = request.getParameter("recipient");
         final String subject = request.getParameter("subject");
         final String message = request.getParameter("message");
  
         // for logging
         System.out.println("emailTo: " + emailTo);
         System.out.println("subject: " + subject);
         System.out.println("message: " + message);
         System.out.println("attachFile: " + attachFile.getOriginalFilename());
  
         mailSender.send(new MimeMessagePreparator() {
  
             public void prepare(MimeMessage mimeMessage) throws Exception {
                 MimeMessageHelper messageHelper = new MimeMessageHelper(
                         mimeMessage, true, "UTF-8");
                 messageHelper.setTo(emailTo);
                 messageHelper.setSubject(subject);
                 messageHelper.setText(message);
                  
                 // determines if there is an upload file, attach it to the e-mail
                 String attachName = attachFile.getOriginalFilename();
                 if (!attachFile.equals("")) {
  
                     messageHelper.addAttachment(attachName,new InputStreamSource() {
                          
                         public InputStream getInputStream() throws IOException {
                             return attachFile.getInputStream();
                         }
                     });
                 }
                  
             }
  
         });
  
         return "result";
     }
}
