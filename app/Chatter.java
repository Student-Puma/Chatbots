// Environment code for project prueba.mas2j

import jason.asSyntax.*;
import jason.environment.*;
import java.util.logging.*;
import jason.environment.Environment;

import java.io.*;
import java.io.File;

import java.util.*;
import java.util.logging.Logger;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

public class Chatter extends Environment {

	private static String botName = "proyecto";
	private static final boolean TRACE_MODE = true;
	private String resourcesPath = getResourcesPath();
    private Logger logger = Logger.getLogger("proyecto.mas2j." + Chat.class.getName());

    private int contador = 0;
	private Bot bot = new Bot(botName, resourcesPath);
	private Chat chatSession = new Chat(bot);
	private String respuesta = "No tengo nada que decir";

    /**
      * Called before the MAS execution with the args informed in .mas2j
      */
    @Override
    public void init(String[] args) {
        super.init(args);
        addPercept(Literal.parseLiteral("bot(created)"));
		
		MagicBooleans.trace_mode = TRACE_MODE;
		bot.brain.nodeStats();
    }

    @Override
    public boolean executeAction(String ag, Structure action) {
        try {
            if (action.getFunctor().equals("chat")) {
                // Obtenemos la pregunta
                String pregunta = ((StringTerm) action.getTerm(0)).getString();
                logger.info(ag + " pregunta " + pregunta);
                // Obtenemos la respyesta
				respuesta = chatSession.multisentenceRespond(pregunta);
                // Normalizamos la respuesta
				while (respuesta.contains("&lt;")) respuesta = respuesta.replace("&lt;", "<");
				while (respuesta.contains("&gt;")) respuesta = respuesta.replace("&gt;", ">");
                // Añadimos la percepción de una nueva respuesta al agente
                // -- Versión antigua:
                //addPercept(new LiteralImpl("answer").addTerms(new StringTermImpl(response)));
				// -- Versión nueva:
                addPercept(new LiteralImpl("answer").addTerms(new NumberTermImpl(++this.contador), new StringTermImpl(respuesta)));
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(200);
        } catch (Exception e) {}
        // informAgsEnvironmentChanged();
        return true;
    }

    /** Called before the end of MAS execution */
    @Override
    public void stop() {
        super.stop();
    }
	
	private static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		//System.out.println(path);
		//logger.info(path);
		String resourcesPath = path + File.separator + "src" + File.separator + "resources";
		return resourcesPath;
	}
	

}

