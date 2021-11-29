package fs.client;

import java.io.IOException;

public class format {
    public static void main(String[] args) throws IOException {

        final int DISK_SIZE = 13356;
        MyFS test = new MyFS();
        test.MyFS("root.fs");

        /*
        if(args.length == 0){
            test.MyFS("myFS.fs");

        }else if(args.length > 1){
            System.out.println("Comando incorrecto. Favor especificar solo el nombre del File System");

        }else {
            if(args[0].split(".")[1] == null){
                System.out.println("El nombre del File System debe cumplir el formato: File_System_Name.fs");

            }else if(args[0].split(".")[1] != "fs"){
                System.out.println("El nombre del File System debe cumplir el formato: File_System_Name.fs");
            }
        }

         */

    }
}
