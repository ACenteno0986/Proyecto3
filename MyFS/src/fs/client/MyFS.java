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
 *
 */
public class MyFS {

    private BufferedReader read;
    private PrintWriter output;
    private static LinkedList<ResponseHandler> commandStack = new LinkedList<>();
    private BufferedReader input;

    /**
     *
     */
    private static final Map<String, ResponseHandler> themap = new HashMap<>();
    static{
        //command handlers
        themap.put("cd", new DirectResponseHandler());
        themap.put("ls", new ListResponseHandler());
        themap.put("mv", new MoveResponseHandler());
        themap.put("mkdir", new MkdirHandler());
        themap.put("touch", new CreateHandler());
        themap.put("cat", new CatHandler());
        themap.put("rm", new RemoveHandler());
        themap.put("exit", new QuitResponseHandler());
        themap.put("useradd", new UserHandler() );
        themap.put("whoami", new WhoamiHandler());
        themap.put("groupadd", new GroupHandler());
        themap.put("passwd", new PasswdHandler());
        themap.put("su", new SuHandler());
        themap.put("pwd", new PwdHandler());
        themap.put("clear", new ClearHandler());
        themap.put("whereis", new SearchResponseHandler());
        //themap.put("ln", new WhoamiHandler());
        themap.put("chown", new ChownHandler());
        themap.put("chgrp", new ChgrpHandler());
        themap.put("chmod", new ChmodHandler());
        themap.put("openFile", new OpenFileHandler());
        themap.put("closeFile", new CloseFileHandler());
        themap.put("viewFilesOpen", new ViewFilesOpenHandler());
        themap.put("viewFCB", new ViewFCBHandler());
        themap.put("infoFS", new InfoFSHandler());
    }

    private String fsName;
    private String passwd;


    /**
     *
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


            }else{
            if (commandStack != null) {
                //for (ResponseHandler e : commandStack) {
                //    disk.setCurrentDir((FSDirectory) e.handlerOnServer());
                //}
                SerializationController.getInstance().serialize(disk);
            }}
            if(disk.getCurrentDir() == null){
                System.exit(0);
            }
            String cmd_in = ConsoleIO.readLine(disk.getName() + "@" + disk.getCurrentDir() + ": ");
            cmd_segments = cmd_in.split(" ");
            if(cmd_segments[0].equals("touch") && cmd_segments.length >2){
                cmd_segments[2] = cmd_in.split("\"")[1];
            }

            ResponseHandler cmd = themap.get(cmd_segments[0]);



            if(cmd != null){
                disk.setCurrentDir((FSDirectory) cmd.handlerResponse(cmd_segments, disk,disk.getROOT_FS(), disk.getCurrentDir()));
                commandStack.add(cmd);
                if(disk.getCurrentDir() == null){

                }
            }else{
                ConsoleIO.printLine("comando equivocado intente de nuevo");
            }

        }
    }

}
