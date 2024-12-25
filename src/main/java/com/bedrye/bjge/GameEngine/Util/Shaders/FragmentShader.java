package com.bedrye.bjge.GameEngine.Util.Shaders;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class FragmentShader extends Shader{
    public FragmentShader(String path) {
        super(path);
    }

    @Override
    public void Compile() {
        setShaderId(glCreateShader(GL_FRAGMENT_SHADER));
        glShaderSource(getShaderId(),getShader());
        glCompileShader(getShaderId());
        int errorInt = glGetShaderi(getShaderId(),GL_COMPILE_STATUS);
        if (errorInt==GL_FALSE){
            int l= glGetShaderi(getShaderId(),GL_INFO_LOG_LENGTH);
            System.out.println(glGetShaderInfoLog(getShaderId(),l));
            return;
        }
    }
}
