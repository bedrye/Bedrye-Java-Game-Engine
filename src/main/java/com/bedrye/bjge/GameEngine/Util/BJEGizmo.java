package com.bedrye.bjge.GameEngine.Util;

import com.bedrye.bjge.GameEngine.Util.Shaders.ShaderProgram;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL11.*;

public class BJEGizmo {
        private int vao, vbo;
        private final float[] vertices = {
                // X axis (red)
                0f, 0f, 0f,  2f, 0f, 0f,
                // Y axis (green)
                0f, 0f, 0f,  0f, 2f, 0f,
                // Z axis (blue)
                0f, 0f, 0f,  0f, 0f, 2f,
        };

        public BJEGizmo() {
            vao = GL30.glGenVertexArrays();
            vbo = GL30.glGenBuffers();
            GL30.glBindVertexArray(vao);
            GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vbo);
            GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertices, GL30.GL_STATIC_DRAW);
            GL30.glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
            GL30.glEnableVertexAttribArray(0);
        }

        public void draw(Matrix4f projection, Matrix4f view, Matrix4f model, ShaderProgram shader) {
            shader.Run();
            shader.UploadMatrix(projection,"projectionMatrix");
            shader.UploadMatrix(view,"viewMatrix");

            shader.UploadMatrix( new Matrix4f(model),"modelMatrix");
            shader.uploadVec3f(new Vector3f(1, 0, 0),"uColor" );
            GL30.glBindVertexArray(vao);
            glLineWidth(30.0f);
            glDrawArrays(GL_LINES, 0, 2);

            shader.uploadVec3f(new Vector3f(0, 1, 0),"uColor" );
            glDrawArrays(GL_LINES, 2, 2);


            shader.uploadVec3f(new Vector3f(0, 0, 1),"uColor" );
            glDrawArrays(GL_LINES, 4, 2);
            glLineWidth(1.0f);
        }

}
