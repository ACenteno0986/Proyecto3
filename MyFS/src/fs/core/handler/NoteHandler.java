package fs.core.handler;

import fs.Util.ConsoleIO;
import fs.core.fs.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NoteHandler extends ResponseHandler{
    @Override
    public FSunit handlerResponse(String[] cmd, VirtualDisk currentDisk, FSDirectory root, FSDirectory CurrentDir) {

        FSunit tempUnit;
        FSFile tempFile;
        if(cmd.length < 2 ){
            ConsoleIO.printLine("cat rquiere al menos 1 argumento");
        }else {
            String[] searchPath = cmd[1].split("/");
            tempUnit = CurrentDir.getItem(searchPath);
            if (tempUnit == null) {
                ConsoleIO.printLine("No existe el archivo en el directorio actual");
            } else {
                if (tempUnit.getClass() == FSDirectory.class) {
                    ConsoleIO.printLine("El archivo objetivo es un directorio cat no aplica");
                }else {

                    tempFile = (FSFile) tempUnit;

                    if(ValidateAccess(tempUnit, currentDisk)){
                        //ConsoleIO.printLine(tempFile.getContent());
                        JTextArea ta = new JTextArea(tempFile.getContent());
                        JFrame frame=new JFrame("CLOSE JFRAME USING KEYBOARD");
                        KeyListener kl=new KeyAdapter()
                        {

                            boolean ctrl = false;
                            boolean x = false;
                            public void keyPressed(KeyEvent evt)
                            {
                                //If someone click Esc key, this program will exit
                                if(evt.getKeyCode()==KeyEvent.VK_CONTROL)
                                {
                                    ctrl =true;

                                }
                                if(evt.getKeyCode()==KeyEvent.VK_X && ctrl){
                                    x = true;
                                }
                                if(ctrl == true && x == true){
                                    tempFile.setContent(ta.getText().getBytes());
                                    frame.dispose();

                                }
                            }
                        };

                        //Create a JFrame with title ( CLOSE JFRAME USING KEYBOARD )


                        //add Key Listener to JFrame
                        frame.addKeyListener(kl);

                        //Set default close operation for JFrame
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        //Set JFrame size to :
                        //Width : 400 pixels
                        //Height : 400 pixels
                        frame.setSize(800,500);

                        JPanel p = new JPanel();
                        ta.addKeyListener(kl);
                        p.setLayout(new BorderLayout());
                        p.add(new JScrollPane(ta));

                        frame.add(p);
                        frame.setLocationRelativeTo(null);
                        frame.setUndecorated(true);
                        //Make JFrame visible. So we can see it.
                        frame.setVisible(true);

                    }else{
                        System.out.println("El usuario no posee el nivel de acceso necesario al archivo.");
                    }

                }

            }
        }
        return this.saveState(cmd, currentDisk, root, CurrentDir);
    }
    private boolean ValidateAccess(FSunit unit, VirtualDisk currentDisk) {

        String userAct = currentDisk.getName();
        FSGroup newGroup = currentDisk.UserExist(currentDisk.getName()).GroupExist(unit.getGroup());

        if ((userAct.equals("root")) || (userAct.equals(unit.getOwner()) &&
                (unit.getOwnerAccessLvl() == 4 || unit.getOwnerAccessLvl() == 5 ||
                        unit.getOwnerAccessLvl() == 6 || unit.getOwnerAccessLvl() == 7))) {
            return true;

        } else if (((newGroup != null) ||
                (currentDisk.UserExist(currentDisk.getName()).getPrimaryGroups().getName().equals(unit.getGroup()))
                        && (unit.getGroupAccessLvl() == 4 || unit.getGroupAccessLvl() == 5
                        || unit.getGroupAccessLvl() == 6 || unit.getGroupAccessLvl() == 7))) {
            return true;

        } else {
            return false;
        }

    }
}
