package com.bedrye.bjge.GameEngine.Objects.Editor;

import com.bedrye.bjge.GameEngine.EngineWindowManager;

import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class BJEFileSystem {

    private final Path scriptDir;

    public BJEFileSystem(String scriptFolder) {
        this.scriptDir = Paths.get(scriptFolder);
    }

    public void startWatcher() throws IOException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        scriptDir.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        new Thread(() -> {
            while (true) {
                try {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path changed = (Path) event.context();
                        if (changed.toString().endsWith(".java")) {
                            System.out.println("Script changed: " + changed);
                            File scriptFile = scriptDir.resolve(changed).toFile();
                            compileAndLoad(scriptFile);
                        }
                    }
                    key.reset();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "ScriptWatcher").start();
    }

    private void compileAndLoad(File javaFile) {
        try {

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                throw new IllegalStateException("No Java compiler available!");
            }
            int result = compiler.run(null, null, null, javaFile.getPath());
            if (result != 0) {
                System.err.println("Compilation failed for " + javaFile.getName());
                return;
            }


            URL[] urls = {scriptDir.toUri().toURL()};
            try (URLClassLoader loader = new URLClassLoader(urls, MainBehaviour.class.getClassLoader())) {
                String className = javaFile.getName().replace(".java", "");
                Class<?> clazz = Class.forName(className, true, loader);

                if (!MainBehaviour.class.isAssignableFrom(clazz)) {
                    System.err.println("Class " + className + " does not extend MainBehaviour!");
                    return;
                }

                Constructor<?> ctor = clazz.getDeclaredConstructor();
                MainBehaviour script = (MainBehaviour) ctor.newInstance();
                EngineWindowManager.getInstance().getBjeResourceManager().addScript(script);
                System.out.println("Loaded script: " + script.getClass().getName());



            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

