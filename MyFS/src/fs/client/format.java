package fs.client;

import java.io.IOException;

public class format {
    public static void main(String[] args) throws IOException {

        final int DISK_SIZE = 13356;
        MyFS myFS = new MyFS();
        //test.MyFS("root.fs");

        if(args.length == 0){
            myFS.MyFS("myFS.fs");

        }else if(args.length > 1){
            System.out.println("Comando incorrecto. Favor especificar solo el nombre del File System");

        }else {
            if(args[0].split("\\.")[1] == null){
                System.out.println("El nombre del File System debe cumplir el formato: File_System_Name.fs");

            }else if(!args[0].split("\\.")[1].equals("fs")){
                System.out.println(args[0].split("\\.")[1]);
                System.out.println("El nombre del File System debe cumplir el formato: File_System_Name.fs");

            }else{
                myFS.MyFS(args[0]);
            }
        }

    }
}
