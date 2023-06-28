package com.example;

import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.SourceFile;
import com.code_intelligence.jazzer.mutation.annotation.NotNull;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SuppressWarnings("unused")
public class ClosureFuzzer2 {
    public static void fuzzerTestOneInput(@NotNull String input) { compile(input); }


    public static void compile(String input) {
        Compiler compiler = new Compiler(new PrintStream(new ByteArrayOutputStream(), false));
        CompilerOptions options = new CompilerOptions();
        SourceFile externs = SourceFile.fromCode("externs", "");
        // compiler initialization options
        compiler.disableThreads();
        options.setPrintConfig(false);
        CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);

        SourceFile src = SourceFile.fromCode("input", input);
        com.google.javascript.jscomp.Result result = compiler.compile(externs, src, options);
    }
}
