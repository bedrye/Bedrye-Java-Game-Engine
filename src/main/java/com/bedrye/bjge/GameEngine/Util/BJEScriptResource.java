package com.bedrye.bjge.GameEngine.Util;

import com.bedrye.bjge.GameEngine.EngineManager;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

public class BJEScriptResource extends  BJEResource{
    public BJEScriptResource(@JsonProperty("path")String path, @JsonProperty("name")String name) {
        super(path, name);compileAndLoad();
    }
    private void compileAndLoad() {
        File javaFile = new File(getPath());
        try {

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                throw new IllegalStateException("[BJE] No Java compiler available!");
            }
            int result = compiler.run(null, null, null, javaFile.getPath());
            if (result != 0) {
                System.err.println("[BJE] Compilation failed for " + javaFile.getName());
                return;
            }


            URL[] urls = {Paths.get(EngineManager.getInstance().getBjeResourceManager().getAssetsPath()+"/scripts").toUri().toURL()};
            try (URLClassLoader loader = new URLClassLoader(urls, MainBehaviour.class.getClassLoader())) {
                String className = javaFile.getName().replace(".java", "");
                Class<?> clazz = Class.forName(className, true, loader);

                if (!MainBehaviour.class.isAssignableFrom(clazz)) {
                    System.err.println("[BJE] Class " + className + " does not extend MainBehaviour!");
                    return;
                }

                Constructor<?> ctor = clazz.getDeclaredConstructor();
                MainBehaviour script = (MainBehaviour) ctor.newInstance();
                EngineManager.getInstance().getBjeResourceManager().addScript(script);
                System.out.println("[BJE] Loaded script: " + script.getClass().getName());



            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
