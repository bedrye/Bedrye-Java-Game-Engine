package com.bedrye.bjge.GameEngine.Util;

import org.joml.*;


public class BJEVertex {

    public final static int SIZE=12;

    public BJEVertex(Vector3f position, Vector4f color, Vector2f textureCoord,Vector3f normal) {
        this.position = position;
        this.color = color;
        this.textureCoord = textureCoord;
        this.normal = normal;
    }
    public BJEVertex(Vector3f position, Vector2f textureCoord,Vector3f normal) {
        this.position = position;
        this.color = new Vector4f(1,1,1,1);
        this.textureCoord = textureCoord;
        this.normal = normal;
    }

    public Vector3f position;
    public Vector4f color;
    public Vector2f textureCoord;
    public Vector3f normal;

    public float[] GetBuffer(){

        return  new float[]{position.x,position.y,position.z,color.x,color.y,color.z,color.w,
                textureCoord.x,textureCoord.y,normal.x,normal.y,normal.z};
    }

    @Override
    public String toString() {
        return "BJEVertex{" +
                "position=" + position +
                ", color=" + color +
                ", textureCoord=" + textureCoord +
                ", normal=" + normal +
                '}';
    }
}
