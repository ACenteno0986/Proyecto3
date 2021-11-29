package fs.client;
import fs.Util.ConsoleIO;

import fs.controller.SerializationController;
import fs.core.fs.FSDirectory;
import fs.core.fs.FSUser;
import fs.core.handler.*;
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
        themap.put("useradd", new UserHandler() );
        themap.put("whoami", new WhoamiHandler());
        //themap.put("groupadd", new WhoamiHandler());
        themap.put("passwd", new PasswdHandler());
        themap.put("su", new SuHandler());
        themap.put("pwd", new PwdHandler());
        //themap.put("mv", new WhoamiHandler());
        themap.put("clear", new ClearHandler());
        //themap.put("whereis", new WhoamiHandler());
        //themap.put("ln", new WhoamiHandler());
        //themap.put("chown", new WhoamiHandler());
        //themap.put("chgrp", new WhoamiHandler());
        //themap.put("chmod", new WhoamiHandler());
        themap.put("openFile", new OpenFileHandler());
        themap.put("closeFile", new CloseFileHandler());
        themap.put("viewFilesOpen", new ViewFilesOpenHandler());
        //themap.put("viewFCB", new WhoamiHandler());
        //themap.put("infoFS", new WhoamiHandler());
    }

    private String fsName;
    private String passwd;


    /**
     * user need to output command to server for synchronization
     * @return the client socket
     * @throws IOException IO exception
     */
    public void MyFS(String fsName) throws IOException {

        DataInputStream in = new DataInputStream(System.in);
        VirtualDisk disk = SerializationController.getInstance().deserialize(fsName);
        String[] lineInput = null;

        if (disk != null) {
            System.out.println("El File System ya existe, ingrese usuario y contraseña para acceder: ");

            this.fsName = fsName;
            int restAttemp = 3;
            while(true) {
                String password;
                String user;
                FSUser accessUser;
                if ((user = ConsoleIO.readLine("Ingrese usuario para continuar: ")) != null){

                    if((accessUser = disk.UserExist(user)) != null){
                        while(true){
                            if ((password = ConsoleIO.readLine("Ingrese contraseña para usuario: ")) != null) {

                                if (password.equals(accessUser.getPasswrd())) {
                                    System.out.println("Bienvenido: " + accessUser.getUsername());
                                    disk.setCurrentUser(accessUser.getUsername());
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
                        if(restAttemp < 0)
                            break;

                    }else{
                        System.out.println("Usuario no existe. Ingrese un usuario valido para continuar");
                    }

                }

            }

        }else{
            System.out.println("El File System no existe. Ejecute el comando format");

            while(true){

                String nextLine = ConsoleIO.readLine("--> ");
                lineInput = nextLine.split(" ");
                if(lineInput[0].contentEquals("format")){
                    break;
                }else{
                    System.out.println("Ejecute el comando format para crear el File System");
                }
            }

            String newPasswd = ConsoleIO.readLine("Ingrese contraseña para usuario root: ");
            while(true) {
                if (newPasswd != null) {

                    int newDiskSize = Integer.parseInt(ConsoleIO.readLine("Ingrese tamaño de la unidad: "));
                    int newBlockSize = Integer.parseInt(ConsoleIO.readLine("Ingrese tamaño del bloque: "));
                    disk = new VirtualDisk(fsName, newPasswd, newDiskSize, newBlockSize);
                    RunOperations(disk);

                }
            }
        }
    }

    private void RunOperations( VirtualDisk disk){

        String[] cmd_segments;

        while (true){

            if(disk.getCurrentDir() != null){
                //ConsoleIO.printLine(disk.getCurrentDir().getPath());

            }else{
            if (commandStack != null) {
                //for (ResponseHandler e : commandStack) {
                //    disk.setCurrentDir((FSDirectory) e.handlerOnServer());
                //}
                SerializationController.getInstance().serialize(disk);
            }}

            cmd_segments = ConsoleIO.readLine(disk.getName() + "@" + disk.getCurrentDir() + ": ").split(" ");
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
