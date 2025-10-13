package com.bedrye.bjge.GameEngine.Objects.Editor;

import com.bedrye.bjge.GameEngine.BJEMeshRenderer;
import com.bedrye.bjge.GameEngine.Scripts.Box3DCollider;
import com.bedrye.bjge.GameEngine.Scripts.MainBehaviour;
import com.bedrye.bjge.GameEngine.Scripts.SimpleCameraControl;
import com.bedrye.bjge.GameEngine.Scripts.TestBehaviour;
import com.bedrye.bjge.GameEngine.Util.*;


import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BJEResourceManager {

    private HashMap<String ,MainBehaviour> scripts = new HashMap<>();
    private Set<Path> listToAdd  = new ConcurrentSkipListSet<>();
    private Map<String , BJEResource> resources  = new ConcurrentHashMap<>();

    public HashMap<String, MainBehaviour> getScripts() {
        return scripts;
    }


    public void addScript(MainBehaviour mainBehaviour){
        scripts.put(mainBehaviour.getClass().getName(),mainBehaviour);


    }

    private final WatchService watchService;

    public Path getAssetsPath() {
        return assetsPath;
    }

    public BJEFolder getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(BJEFolder currentPath) {
        this.currentPath = currentPath;
    }

    private BJEFolder currentPath;
    private final Path assetsPath;
    private final ExecutorService watchThread;

    private boolean running = true;

    public BJEResourceManager(String assetDir) throws IOException {

        scripts.put(SimpleCameraControl.class.getName(),new SimpleCameraControl());
        scripts.put(BJEMeshRenderer.class.getName(),new BJEMeshRenderer());
        scripts.put(TestBehaviour.class.getName(),new TestBehaviour());
        scripts.put(Box3DCollider.class.getName(),new Box3DCollider());
        resources.put("INTERNAL\\folderbutton.png",new BJETexture("INTERNAL","folderbutton.png"));
        resources.put("INTERNAL\\runbutton.png",new BJETexture("INTERNAL","runbutton.png"));
        resources.put("INTERNAL\\stopbutton.png",new BJETexture("INTERNAL","stopbutton.png"));
        resources.put("INTERNAL\\3dobjectIcon.png",new BJETexture("INTERNAL","3dobjectIcon.png"));
        resources.put("INTERNAL\\whitetexture.png",new BJETexture("INTERNAL","whitetexture.png"));
        resources.put("INTERNAL\\textbutton.png",new BJETexture("INTERNAL","textbutton.png"));
        resources.put("INTERNAL\\scenebutton.png",new BJETexture("INTERNAL","scenebutton.png"));
        this.assetsPath = Paths.get(assetDir);

        if (!Files.exists(assetsPath)) {
            Files.createDirectories(assetsPath);
        }
        this.currentPath = new BJEFolder( Paths.get(assetDir).toString(),"Assets");

        this.watchService = FileSystems.getDefault().newWatchService();
        this.watchThread = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "BJE-Asset-Watcher");
            t.setDaemon(true);
            return t;
        });


    }
    public void  init() throws IOException {

        loadAllResources();
        currentPath.init(null);
        startWatching();
    }
    public void update(){
        listToAdd.forEach(this::loadResource);
        listToAdd.clear();

    }
    private void loadAllResources() throws IOException {
        Files.walk(assetsPath)
                .filter(Files::isRegularFile)
                .forEach(this::loadResource);
    }

    private void loadResource(Path path) {
        String ext = getFileExtension(path);
        String name = path.getFileName().toString();
        try {
            BJEResource resource = switch (ext.toLowerCase()) {
                case "obj" -> new BJEObjFile(path.toString(), name);
                case "png" -> new BJETexture(path.toString(), name);
                case "jpg", "jpeg" -> new BJEJPGTexture(path.toString(), name);
                case "java" -> new BJEScriptResource(path.toString(), name);
                case "bjes" -> new BJESceneFile(path.toString(), name);
                default -> new BJETextFile(path.toString(), name);
            };
                resources.put(path.toString(), resource);
                System.out.println("[BJE] Loaded resource: " + path);

        } catch (Exception e) {
            System.err.println("[BJE] Failed to load resource " + path + ": " + e.getMessage());
        }
    }

    private void startWatching() throws IOException {
        Files.walk(assetsPath)
                .filter(Files::isDirectory)
                .forEach(dir -> {
                    try {
                        dir.register(
                                watchService,
                                StandardWatchEventKinds.ENTRY_CREATE,
                                StandardWatchEventKinds.ENTRY_MODIFY,
                                StandardWatchEventKinds.ENTRY_DELETE
                        );
                    } catch (IOException e) {
                        System.err.println("[BJE] Failed to watch " + dir);
                    }
                });

        watchThread.submit(this::watchLoop);
    }

    private void watchLoop() {
        while (running) {
            try {
                WatchKey key = watchService.take();
                Path dir = (Path) key.watchable();

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    Path filename = (Path) event.context();
                    Path fullPath = dir.resolve(filename);

                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        listToAdd.add(fullPath);


                    } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        reloadResource(fullPath);
                    } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        removeResource(fullPath);
                    }

                }
                key.reset();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void reloadResource(Path path) {
        System.out.println("[BJE] Reloading resource: " + path);
        listToAdd.add(path);
    }

    private void removeResource(Path path) {
        resources.remove(path.toString());
        System.out.println("[BJE] Removed resource: " + path);
    }

    public Collection<BJEResource> getAllResources() {
        return resources.values();
    }

    public <T extends BJEResource> List<T> getResourcesOfType(Class<T> type) {
        List<T> list = new ArrayList<>();
        for (BJEResource res : resources.values()) {
            if (type.isInstance(res)) list.add(type.cast(res));
        }
        return list;
    }
    public BJEResource getByPath(String path) {
        return resources.get(path);
    }
    public BJEResource getByName(String name) {
        return resources.values().stream()
                .filter(r -> r.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void stop() {
        running = false;
        watchThread.shutdownNow();
    }

    private String getFileExtension(Path path) {
        String fileName = path.getFileName().toString();
        int i = fileName.lastIndexOf('.');
        return (i > 0) ? fileName.substring(i + 1) : "";
    }



}
