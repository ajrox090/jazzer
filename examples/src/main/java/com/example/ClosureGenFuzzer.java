package com.example;

import static util.Utils.generate;
import edu.berkeley.cs.jqf.examples.js.JavaScriptCodeGenerator;
import com.example.ClosureFuzzer;
import com.code_intelligence.jazzer.mutation.annotation.NotNull;



public class ClosureGenFuzzer{

    public static void fuzzerTestOneInput(byte @NotNull [] input){
        ClosureFuzzer.compile(generate(input, new JavaScriptCodeGenerator()));
    }
}