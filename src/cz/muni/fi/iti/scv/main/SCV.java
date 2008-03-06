/*
 * Main class of the project.
 */

package cz.muni.fi.iti.scv.main;
import cz.muni.fi.iti.scv.props.LoggerConfigurator;
import cz.muni.fi.iti.scv.props.Properties;
import cz.muni.fi.iti.scv.scvgui.GuiMain;
import cz.muni.fi.iti.scv.scvgui.SourceAndXMLWindow;
import java.io.File;
import java.io.IOException;

import java.util.HashSet;
import java.util.Set;
import joptsimple.OptionException;
import org.apache.log4j.Logger;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.apache.log4j.Level;



/**
 * Main class of the project. Includes the static main method, which is started from the command line.
 * This class takes care of command line arguments.
 * 
 * @author xstastn
 * 
 * @version $Id$
 */
public class SCV {

    private static Logger logger = Logger.getLogger(GuiMain.class);
    
    public static Properties.VerbosityLevel VERBOSITY_LEVEL = Properties.VerbosityLevel.LOW;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Sets up the logging facility (reads the logging options from log4j.properties file.
        // This needs to be done only once
        new LoggerConfigurator();
        
        OptionParser optionsParser = new OptionParser() {
            {
                accepts("help", "prints help" );
                accepts("usage", "shows usage" );
                accepts("v", "Sets the level of verbosity").withOptionalArg().
                           describedAs("0 to 2 meaning 0 - silent, 1 - low level (default), 2 high level").
                           ofType(Integer.class);
                accepts("s", "Turns silent mode on. Same as -v 0" );
                accepts("nogui", "Don't start the GUI" );
                
            }
        };
        
        try {
            final OptionSet options = optionsParser.parse(args);
            
            
        if(options.wasDetected("help") || options.wasDetected("usage")) {
            try {
                optionsParser.printHelpOn(System.out);
            } catch (IOException ex) {
                Logger.getLogger(SCV.class.getName()).log(Level.FATAL, null, ex);
            }
        } else {
            if(options.wasDetected("v")) {
                switch((Integer) options.valueOf("v")) {
                    case 1:
                    default:
                        VERBOSITY_LEVEL = Properties.VerbosityLevel.LOW;
                    break;
                    case 2:
                        VERBOSITY_LEVEL = Properties.VerbosityLevel.HIGH;
                    break;
                    case 0:
                        VERBOSITY_LEVEL = Properties.VerbosityLevel.SILENT;
                    break;

                }
            }
            
            if(options.wasDetected("s")) {
                VERBOSITY_LEVEL = Properties.VerbosityLevel.SILENT;
            }
            
            // Start GUI?
            if(!options.wasDetected("nogui")) {
 
                 new LoggerConfigurator();
        
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        GuiMain gui = new GuiMain();
                        gui.setVisible(true);
                        if(!options.nonOptionArguments().isEmpty()) {
                            File file = new File((String)options.nonOptionArguments().get(0));
                            gui.openSourceFile(file);
                            
                            Set<File> files = new HashSet<File>();
                            for(int i = 1; i < options.nonOptionArguments().size(); i++) {
                                files.add(new File((String)options.nonOptionArguments().get(i)));
                            }
                            if(!files.isEmpty()) {
                                 ((SourceAndXMLWindow) gui.getJTabbedPane1().getSelectedComponent()).runStaticChecker(files);
                            }
                        }
                        
                    }
                });
                
            } else {
                // Don't start the GUI
                // TODO: Add the CLI behaviour
            }
        }
    
          // If an unknown argument is found
        } catch(OptionException ex) {
            try {
                optionsParser.printHelpOn(System.out);
            } catch (IOException ex1) {
                logger.log(Level.FATAL, null, ex1);
            }
        }
        
    }

}
