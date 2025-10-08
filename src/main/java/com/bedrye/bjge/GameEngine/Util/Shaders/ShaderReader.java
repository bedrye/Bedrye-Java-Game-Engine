package com.bedrye.bjge.GameEngine.Util.Shaders;

import java.util.ArrayList;

public class ShaderReader {

    public static ArrayList<Shader> getShadersFromString(String string){
        String[] split = string.split("(#section)( )+([a-zA-Z]+)");
        int index;
        int endl=0;
        ArrayList<Shader> shaders = new ArrayList<>();
        for (int i = 1; i < split.length ; i++) {
            index = string.indexOf("#section",endl)+9;
            endl = string.indexOf("\r\n",index);
            String pattern = string.substring(index,endl).trim().toLowerCase();
            shaders.add(getShaderType(pattern,split[i]));
        }
        return shaders ;

    }
        private static Shader getShaderType(String pattern,String s){
        Shader shader;
        switch (pattern){
            case "vertex":
                shader = new VertexShader(s);
            break;
            case "fragment":
                shader = new FragmentShader(s);
            break;
            default:
                throw new RuntimeException("Unexpected section identifier");

        }
        return shader;
        }
}
