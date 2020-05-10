package gui;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;

import java.util.Properties;    

import javax.mail.*;    
import javax.mail.internet.*; 

import java.io.*;
import java.io.File;

import java.util.*;
import java.util.logging.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;


/** internal action that allow agents to use freetts to speak conversations */

public class mailing extends DefaultInternalAction {

    //int runCount = 0;

    @Override
    public Object execute(final TransitionSystem ts, Unifier un, Term[] args) throws Exception {

        // get the receiver
		String to = ((StringTerm)args[0]).getString();	
		// get the subject
        String sub = ((StringTerm)args[1]).getString();	
		// get the message	
		String msg = ((StringTerm)args[2]).getString();	
			
        //Get properties object    
        Properties props = new Properties();   

        props.put("mail.smtp.host", "smtp.gmail.com");    
        props.put("mail.smtp.socketFactory.port", "465");    
        props.put("mail.smtp.socketFactory.class",    
                  "javax.net.ssl.SSLSocketFactory");    
        props.put("mail.smtp.auth", "true");    
        props.put("mail.smtp.port", "465");
    
          
		//get Session   
        Session session = Session.getDefaultInstance(props,    
           new javax.mail.Authenticator() {    
			   protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication("spam.kikefontanlorenzo@gmail.com","C0ntrasin4l");  
			   }
		   });    

        try {
				MimeMessage message = new MimeMessage(session);
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject(sub);
				message.setText(msg);    
           
				//send message           
				Transport.send(message);    
				System.out.println("message sent successfully");    
          } catch (MessagingException e) {
			  throw new RuntimeException(e);
		  }    
				
        return true;
    }
}
