package util;

import com.pholser.junit.quickcheck.generator.Gen;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import edu.berkeley.cs.jqf.fuzz.guidance.StreamBackedRandom;
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.FastSourceOfRandomness;
import edu.berkeley.cs.jqf.fuzz.junit.quickcheck.NonTrackingGenerationStatus;
import java.io.ByteArrayInputStream;
import util.PaddedByteArrayInputStream;

public class Utils {

    public static <T> T generate(byte[] input, Gen<T> generator){
        return generate(input, generator, false);
    }
    public static <T> T generate(byte[] input, Gen<T> generator, boolean doPadding) {
        // Generate input values
        ByteArrayInputStream stream = doPadding ? new PaddedByteArrayInputStream(input) : new ByteArrayInputStream(input);
        StreamBackedRandom randomFile = new StreamBackedRandom(stream, Long.BYTES);
        SourceOfRandomness random = new FastSourceOfRandomness(randomFile);
        GenerationStatus genStatus = new NonTrackingGenerationStatus(random);
        return generator.generate(random, genStatus);
    }
}
