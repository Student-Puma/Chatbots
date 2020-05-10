package gui;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;

import java.io.*;
import java.io.File;

import java.util.*;
import java.util.logging.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;


/** internal action that allow agents to use freetts to speak conversations */

public class talking extends DefaultInternalAction {

    //int runCount = 0;

    @Override
    public Object execute(final TransitionSystem ts, Unifier un, Term[] args) throws Exception {

        // get the parameters
        String voice = ((StringTerm)args[0]).getString();	
		//String toTalk = ((StringTerm)args[1]).getString();	
		String toTalk = ((Term)args[1]).toString();	
		Boolean isMac = false;
		
		System.out.println(voice+" debe hablar: "+toTalk);
		
		Voice voiceLabel; //Voice Class
		voiceLabel = VoiceManager.getInstance().getVoice("kevin16"); //Getting kevin voice for agent
		//voiceLabel = VoiceManager.getInstance().getVoice(voice); //Getting kevin voice for agent
		
		if (voiceLabel != null) {
                voiceLabel.allocate();//Allocating Voice
            }
			
		try {
			/*
			Firstly identify the OS in which JASON is running
			*/
			if (System.getProperty("os.name").toLowerCase().indexOf("mac") >=0) {
					isMac = true;
					System.out.println("El sistema está ejecutándose en un sistema MacOs X");
				};
				
			// Next is just another way valid on MacOsX
			
			if (isMac) {
				//System.out.println(voice+" debe hablar: "+toTalk);
				Process p = Runtime.getRuntime().exec("say -v " +voice+ " -r 180 "+toTalk);
			} else {
				voiceLabel.setRate(120);//Setting the rate of the voice
				voiceLabel.setPitch(140);//Setting the Pitch of the voice
				voiceLabel.setVolume(4);//Setting the volume of the voice
			
				voiceLabel.speak(toTalk); //Calling speak() method
			};

			String resourcesPath = getResourcesPath();
			System.out.println(resourcesPath);
						
		} catch (Exception eLabel) {
			eLabel.printStackTrace();
		};
				
        return true;
    }
	
	private static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		System.out.println(path);
		//logger.info(path);
		String resourcesPath = path + File.separator + "src" + File.separator + "resources";
		return resourcesPath;
	}
	
}
