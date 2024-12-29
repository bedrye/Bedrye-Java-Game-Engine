package com.bedrye.bjge.GameEngine;

import java.io.File;
import java.io.IOException;

public final class EngineFileSystem {
    public EngineFileSystem(String string) {
        projectDir = new File(string);
        currentDir = new File(string);
        getSubDirStructure();
    }
    public EngineFileSystem() {
        projectDir = new File("Assets");
        currentDir = new File("Assets");
        getSubDirStructure();
    }

    private File projectDir;
    private File currentDir;

    public File getCurrentDir() {
        return currentDir;
    }

    public File getProjectDir() {
        return projectDir;
    }

    public void setCurrentDir(File currentDir) {
        this.currentDir = currentDir;
    }

    private void update(){


    }
    private void initialize(){



    }

    public void getSubDirStructure(){
        File[] files = currentDir.listFiles();
        try {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(
                            "directory:"
                                    + file.getCanonicalPath());
                }


            else{
                        System.out.println(
                                "     file:"
                                        + file.getCanonicalPath());
                    }
                }
            }
        catch (IOException e)
        {
                    throw new RuntimeException(e);
        }




    }


}
