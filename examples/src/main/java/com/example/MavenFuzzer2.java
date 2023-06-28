package com.example;
import org.apache.maven.model.io.DefaultModelReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import com.code_intelligence.jazzer.mutation.annotation.NotNull;

@SuppressWarnings("unused")
public class MavenFuzzer2 {
    public static void fuzzerTestOneInput(@NotNull String input) {
        read(new ByteArrayInputStream(input.getBytes()));
    }

    public static void read(InputStream stream) {
        DefaultModelReader reader = new DefaultModelReader();
        try {
            reader.read(stream, null);
        } catch (IOException e) {
            // Ignored
        }
    }
}
