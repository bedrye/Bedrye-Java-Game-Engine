package com.bedrye.bjge.GameEngine.Util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class InternalFileDecoder {

    public static String readInternal(String internalPath) {
        try (InputStream in = InternalFileDecoder.class.getResourceAsStream(internalPath)) {
            if (in == null) {
                throw new FileNotFoundException("[BJE] Internal resource not found: " + internalPath);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\r\n"));
            }
        } catch (IOException e) {
            throw new RuntimeException("[BJE] Failed to load internal resource: " + internalPath, e);
        }

    }
}
