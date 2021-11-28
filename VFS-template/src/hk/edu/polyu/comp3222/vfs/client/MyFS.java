package hk.edu.polyu.comp3222.vfs.client;
import hk.edu.polyu.comp3222.vfs.Util.ConsoleIO;

import hk.edu.polyu.comp3222.vfs.controller.SerializationController;
import hk.edu.polyu.comp3222.vfs.core.handler.*;
import hk.edu.polyu.comp3222.vfs.core.vfs.VFSDirectory;
import hk.edu.polyu.comp3222.vfs.core.vfs.VirtualDisk;

import java.io.*;
import java.util.*;

/**
 * Created by Isaac on 1/24/17.
 */
public class MyFS {

    private BufferedReader read;
    private PrintWriter output;
    private static LinkedList<ResponseHandler> commandStack = new LinkedList<>();
    private BufferedReader input;

    /**
     * create the map for retriveing responsehandler while avoid multi-(if else)s
     */
    private static final Map<String, ResponseHandler> themap = new HashMap<>();
    static{
        //command handlers
        themap.put("cd", new DirectResponseHandler());
        themap.put("ls", new ListResponseHandler());
        themap.put("mv", new MoveResponseHandler());
        themap.put("cp", new CopyResponseHandler());
        themap.put("mkdir", new MkdirHandler());
        themap.put("touch", new CreateHandler());
        themap.put("cat", new CatHandler());
        themap.put("import", new ImportResponseHandler());
        themap.put("export", new ExportResponseHandler());
        themap.put("search", new SearchResponseHandler());
        themap.put("remove", new RemoveHandler());
        themap.put("rename", new RenameHandler());
        themap.put("query", new QueryHandler());
        themap.put("help", new HelpHandler());
        themap.put("quit", new QuitResponseHandler());
    }

    private String userName;
    private String passwd;


    /**
     * user need to output command to server for synchronization
     * @return the client socket
     * @throws IOException IO exception
     */
    public void MyFS() throws IOException {

        DataInputStream in = new DataInputStream(System.in);

        String username = ConsoleIO.readLine("Please input your username: ");
        System.out.println(username);

        VirtualDisk disk = SerializationController.getInstance().deserialize(username);

        if (disk != null) {
            System.out.println("User exists, please input password:");

            this.userName = username;
            this.passwd = disk.getPassword();

            while(true) {
                String password;
                if ((password = input.readLine()) != null) {
                    ConsoleIO.printLine("SERVER SIDE: " + password);
                    if (password.equals(passwd)) {
                        System.out.println("Welcome, " + username);

                    } else {
                        output.println("Login Failed");
                    }
                    break;
                }
            }

        }else{
            System.out.println("User not existed, you may create a new one:");

            String newPasswd = ConsoleIO.readLine("Please input your username: ");
            while(true) {
                if (newPasswd != null) {

                    ConsoleIO.printLine("SERVER SIDE: new password: " + newPasswd);

                    int newDiskSize = Integer.parseInt(ConsoleIO.readLine("Please input size: "));
                    disk = new VirtualDisk(username, newPasswd, newDiskSize);


                    break;
                }
            }
        }
        String[] cmd_segments;
        ConsoleIO.printLine(disk.getName());
        while (true){
            ConsoleIO.printLine("Current Working Directory is:");
            ConsoleIO.printLine(disk.getCurrentDir().getPath());
            cmd_segments = ConsoleIO.readLine("-->").split(" ");
            ResponseHandler cmd = themap.get(cmd_segments[0]);


            /*-------------------command line implementation--------------------------*/
            if(cmd != null){

                disk.setCurrentDir((VFSDirectory) cmd.handlerResponse(cmd_segments, disk,disk.getROOT_FS(), disk.getCurrentDir()));
                commandStack.add(cmd);
                if(disk.getCurrentDir() == null){
                    //return "save";
                }
            }else{
                ConsoleIO.printLine("wrong command, try again");
            }

        }
            //output.close();

    }

}
