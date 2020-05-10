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

    private Logger logger = Logger.getLogger("prueba.mas2j."+Chat.class.getName());
	private static final boolean TRACE_MODE = true;
	static String botName = "mybot";
	private String resourcesPath = getResourcesPath();

	private Bot bot= new Bot(botName, resourcesPath);
	private Chat chatSession = new Chat(bot);
	private String response = "No tengo nada que decir";

    /** Called before the MAS execution with the args informed in .mas2j */
    @Override
    public void init(String[] args) {
        super.init(args);
        addPercept(Literal.parseLiteral("bot(created)"));
		
		MagicBooleans.trace_mode = TRACE_MODE;
		bot.brain.nodeStats();

    }

   @Override
    public boolean executeAction(String ag, Structure action) {
        logger.info(ag+" doing: "+ action);
        try {
            if (action.getFunctor().equals("chat")) {
                String request = ((StringTerm)action.getTerm(0)).getString();
				//logger.info("???????????????????????????????????????????????????????????????");
				//logger.info("El entorno va a procesar la pregunta:>"+request);
				//logger.info("???????????????????????????????????????????????????????????????");
				
				response = chatSession.multisentenceRespond(request);
			
				while (response.contains("&lt;")) response = response.replace("&lt;", "<");
				while (response.contains("&gt;")) response = response.replace("&gt;", ">");

				addPercept(new LiteralImpl("answer").addTerms(new StringTermImpl(response)));
				
				//addPercept(Literal.parseLiteral("answer('"+response+"')"));

            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(200);
        } catch (Exception e) {}
        //informAgsEnvironmentChanged();
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

