package fs.client;
import fs.Util.ConsoleIO;

import fs.controller.SerializationController;
import fs.core.fs.FSDirectory;
import hk.edu.polyu.comp3222.vfs.core.handler.*;
import fs.core.fs.VirtualDisk;
import fs.core.handler.*;

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

    private String username;
    private String passwd;


    /**
     * user need to output command to server for synchronization
     * @return the client socket
     * @throws IOException IO exception
     */
    public void MyFS() throws IOException {

        DataInputStream in = new DataInputStream(System.in);
        VirtualDisk disk = null;
        String[] lineInput = null;

        while(true){

            String nextLine = ConsoleIO.readLine("--> ");
            lineInput = nextLine.split(" ");
            if(lineInput[0].contentEquals("format")){
                SerializationController.getInstance().deserialize(lineInput[1]);
                break;
            }else{
                System.out.println("Ejecute el comando format seguido por el nombre del File System");
            }
        }


        if (disk != null) {
            System.out.println("User exists, please input password:");

            this.username = lineInput[1];
            this.passwd = disk.getPassword();
            int restAttemp = 3;
            while(true) {
                String password;
                if ((password = ConsoleIO.readLine("Ingrese contraseña para usuario: ")) != null) {

                    if (password.equals(passwd)) {
                        System.out.println("Bienvenido: " + username);
                        disk.setCurrentDir(disk.getROOT_FS());
                        RunOperations(disk);

                    } else {
                        System.out.println("Contraseña incorrrecta");
                        System.out.println("Intentos restantes: " + restAttemp);
                        restAttemp--;
                    }
                    if(restAttemp < 0)
                        break;
                }
            }

        }else{
            System.out.println("El File System no existe. Creando nuevo...");

            String newPasswd = ConsoleIO.readLine("Ingrese contraseña para usuario root: ");
            while(true) {
                if (newPasswd != null) {

                    int newDiskSize = Integer.parseInt(ConsoleIO.readLine("Ingrese tamaño de la unidad: "));
                    disk = new VirtualDisk("root", newPasswd, newDiskSize);
                    RunOperations(disk);

                }
            }
        }
    }

    private void RunOperations( VirtualDisk disk){
        System.out.println(disk.getCurrentDir());
        String[] cmd_segments;
        ConsoleIO.printLine(disk.getName());
        while (true){
            ConsoleIO.printLine("Current Working Directory is:");
            if(disk.getCurrentDir() != null){

                    ConsoleIO.printLine(disk.getCurrentDir().getPath());

            }else{
            if (commandStack != null) {
                for (ResponseHandler e : commandStack) {
                    disk.setCurrentDir((FSDirectory) e.handlerOnServer());
                }
                SerializationController.getInstance().serialize(disk);
            }}

            cmd_segments = ConsoleIO.readLine("-->").split(" ");
            ResponseHandler cmd = themap.get(cmd_segments[0]);


            /*-------------------command line implementation--------------------------*/
            if(cmd != null){
                disk.setCurrentDir((FSDirectory) cmd.handlerResponse(cmd_segments, disk,disk.getROOT_FS(), disk.getCurrentDir()));
                commandStack.add(cmd);
                if(disk.getCurrentDir() == null){

                }
            }else{
                ConsoleIO.printLine("wrong command, try again");
            }

        }
    }

}
