package com.bedrye.bjge.GameEngine.Util;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class BJEJPGTexture extends BJETexture{
    public BJEJPGTexture(String path, String name) {
        super(path, name);
    }

    public BJEJPGTexture(int width, int height) {
        super(width, height);
    }
    @Override
    public void initialize(){


        textureID=glGenTextures();
        glBindTexture(GL_TEXTURE_2D,textureID);
        if(repeat) {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        }
        if(pixelate){
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        }
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer channel = BufferUtils.createIntBuffer(1);
        ByteBuffer image = stbi_load(getPath(),width,height,channel,0);
        if(image!=null){
            glTexImage2D(GL_TEXTURE_2D,0,GL_RGB,width.get(0),height.get(0),0,
                    GL_RGB,GL_UNSIGNED_BYTE,image);

        }
        stbi_image_free(image);
    }
}
