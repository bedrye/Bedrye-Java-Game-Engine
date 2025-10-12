package com.bedrye.bjge.GameEngine.Util;



import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import imgui.ImGui;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;


public class BJETexture extends  BJEResource{

    @JsonIgnore
    public int getTextureID() {
        return textureID;
    }
    @JsonIgnore
    protected transient  int textureID;


    protected boolean repeat=true;

    protected boolean pixelate=true;
    @JsonIgnore
    protected int height,width;

    @JsonCreator
    public BJETexture(@JsonProperty("path")String path, @JsonProperty("name")String name) {
        super(path,name);
        if(path.equals("INTERNAL"))
            try {
                loadFromStream(getClass().getResourceAsStream("/Internal/"+name));
            } catch (IOException e) {
                throw new RuntimeException("Failed to load texture from InputStream: " + name, e);
            }
        else
            initialize();


    }
    public BJETexture(int width,int height) {
        super("INTERNAL","INTERNAL");
        textureID=glGenTextures();
        glBindTexture(GL_TEXTURE_2D,textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexImage2D(GL_TEXTURE_2D,0,GL_RGB,width,height,0, GL_RGB,GL_UNSIGNED_BYTE,0);




    }

    private void loadFromStream(InputStream inputStream) throws IOException {

        byte[] data = inputStream.readAllBytes();
        ByteBuffer imageBuffer = BufferUtils.createByteBuffer(data.length);
        imageBuffer.put(data);
        imageBuffer.flip();

        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);

        ByteBuffer image = stbi_load_from_memory(imageBuffer, w, h, comp, 4);
        if (image == null) {
            throw new RuntimeException("[BJE] Failed to decode image: " + stbi_failure_reason());
        }

        width = w.get(0);
        height = h.get(0);

        textureID=glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0,
                GL_RGBA, GL_UNSIGNED_BYTE, image);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        stbi_image_free(image);
    }
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
            glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,width.get(0),height.get(0),0,
                    GL_RGBA,GL_UNSIGNED_BYTE,image);

        }
        stbi_image_free(image);
    }
    public void Bind(){
        glBindTexture(GL_TEXTURE_2D,textureID);

    }
    public void Unbind(){
        glBindTexture(GL_TEXTURE_2D,0);

    }

    @Override
    public void show() {

        ImGui.pushID(getPath());
        ImGui.image(textureID,12,12);
        ImGui.sameLine();
        ImGui.selectable(getName());



    }
    @Override
    public void DragStart() {
        if (ImGui.beginDragDropSource())
        {
            ImGui.setDragDropPayload("Resource",this);
            ImGui.text(getName());

            ImGui.endDragDropSource();


        }
    }


}
