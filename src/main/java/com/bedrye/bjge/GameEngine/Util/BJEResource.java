package com.bedrye.bjge.GameEngine.Util;

import java.io.*;

public class BJEResource implements Serializable {
    protected final String path;

    public BJEResource(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "BJEResource{" +
                "path='" + path + '\'' +
                '}';
    }
    public final void Save() throws IOException {

        FileOutputStream fos =  new FileOutputStream(getPath()+".bjer");


        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
        fos.close();
    }

}
