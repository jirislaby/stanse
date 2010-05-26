
package cz.muni.stanse.lockchecker;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.DocumentException;

import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.checker.CheckerCreator;
import cz.muni.stanse.checker.CheckerException;



/**
 * Class used to create Lock Checker
 * @author Radim Cebis
 *
 */
public final class LockCheckerCreator extends CheckerCreator {

    @Override
    public String getCheckerName() {
        return "LockChecker";
    }

    @Override
    public String getCheckerCreationInfo() {
        return "LockChecker is a static checker which detects places"
                +" where variable should be locked but is not.";
    }

       
    @Override
    public LinkedList<String> getDataFilesExtensions() {
        final LinkedList<String> result = new LinkedList<String>();
        result.add("xml");
        return result;
    }

    @Override
    public boolean checkArgumentList(final List<File> args) {
        return args.size() == 1 &&
               args.get(0).toString().toLowerCase().endsWith(".xml");
    }

   

    @Override
    public Checker createIntraprocedural(final List<File> args)
                                                       throws CheckerException {
    	return create(args);
    }

    @Override
    public Checker createInterprocedural(final List<File> args)
                                                       throws CheckerException {
    	return create(args);
    }

    private Checker create(final List<File> args)
    				throws CheckerException {
    	if(args.size()!=1) {
            throw new CheckerException("Wrong number of arguments");
        }        
    	try {
    		Configuration conf = new Configuration(args.get(0));
    		return new LockChecker(conf);
        } catch (DocumentException e) {
        	throw new CheckerException("Parsing config file '" +
                    args.get(0).toString() + "' has FAILED");
		}    	        
    }
}
