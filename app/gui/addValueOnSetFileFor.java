package gui;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;

//import java.util.Properties;    

//import javax.mail.*;    
//import javax.mail.internet.*; 

import java.io.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.*;
import java.util.logging.Logger;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

/** internal action that allow agents to create a set file on the enviroment bot given  */

public class addValueOnSetFileFor extends DefaultInternalAction {

    //int runCount = 0;

    @Override
    public Object execute(final TransitionSystem ts, Unifier un, Term[] args) throws Exception {

		// get the new value to add
        String val = ((StringTerm)args[0]).getString();
		// get the name of Set file
        String file = ((StringTerm)args[1]).getString();
		// get the name of Bot
        String botName = ((StringTerm)args[2]).getString();
		// get the path of Bot Sets		
 		String setsPath = getSetsPath(botName);
		
		System.out.println("El path del directorio sets del bot: "+botName+" es "+setsPath);
		
		// declare the Buffer		
		File archivo = new File(setsPath + File.separator + file + ".txt");
		BufferedWriter bw;
			
		try {
			if(archivo.exists()) {
				System.out.println("El fichero de texto ya estaba creado.");
			} else {
				archivo.createNewFile();
				System.out.println("Acabo de crear el nuevo fichero de sets " + file + " en " + setsPath + File.separator );
			}
			
			// create the Buffer		
			bw = new BufferedWriter(new FileWriter(archivo,true));
			PrintWriter out = new PrintWriter(bw);
			
			// add the Value		
			out.println(val);
			out.close();
			bw.close();
          } 
		catch (Exception eLabel) {
			eLabel.printStackTrace();
		};   
				
        return true;
    }
	
private static String getSetsPath(String botName) {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		//System.out.println(path);
		//logger.info(path);
		String setsPath = path + File.separator + "src" + File.separator + "resources"+ File.separator + "bots" + File.separator + botName + File.separator + "sets";
		return setsPath;
	}
	
}
