package com.bedrye.bjge.GameEngine.Util.Shaders;

public abstract class ShaderProgram {
    private int programId;

    public abstract void Run();
    public abstract void Compile();
    public abstract void Clear();


    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }
}
