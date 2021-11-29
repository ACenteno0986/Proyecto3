package fs.Util;

import java.io.Serializable;

/**
 *
 */
public interface IOService extends Serializable{
    /**
     *
     */
    abstract String readLine(String str);

    /**
     *
     */
    abstract void printLine(String str);

    /**
     *
     */
    abstract void printConsole(String str, Boolean inline);

    /**
     *
     */
    abstract void printInstructions();

    /**
     *
     */
    abstract void printHelp();
}
