package fs.Util;
import java.util.Scanner;
import java.io.PrintStream;

/**
 *
 */
public class ConsoleIO {

    private static PrintStream output = new PrintStream(System.out);

    /**
     *
     */
    public static void printInstructions(){
        output.println("This is the instruction!");
    }

    /**
     *
     */
    public static void printLine(String str) {
        output.println(str);
    }

    /**
     *
     */
    public static String readLine(String str){
        System.out.print(str);
        Scanner input = new Scanner(System.in);
        String nextLine = input.nextLine();
        return nextLine;
    }


    /**
     *
     */
    public static void printHelp(){
        System.out.println("Esta es la ayuda");
    }

}
