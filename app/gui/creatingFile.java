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



/** internal action that allow the agent to create a file on the agent path */

public class creatingFile extends DefaultInternalAction {

    //int runCount = 0;

    @Override
    public Object execute(final TransitionSystem ts, Unifier un, Term[] args) throws Exception {

		// get the route
        String ruta = ((StringTerm)args[0]).getString();
		
		// get the path
 		String actualPath = getResourcesPath();
		System.out.println("El path actual es "+actualPath);
		
		// create the File
		File archivo = new File(actualPath+ruta);
        BufferedWriter bw;
		
		try {
			if(archivo.exists()) {
				System.out.println("El fichero de texto ya estaba creado.");
			} else {
				archivo.createNewFile();
				System.out.println("Acabo de crear el fichero de texto");
			}
			bw = new BufferedWriter(new FileWriter(archivo,true));
			bw.close();
          } 
		catch (Exception eLabel) {
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
		//String resourcesPath = path + File.separator + "src" + File.separator + "resources";
		return path;
	}
	
}
