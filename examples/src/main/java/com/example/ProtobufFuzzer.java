package com.example;

import com.code_intelligence.jazzer.junit.FuzzTest;
import com.code_intelligence.jazzer.mutation.annotation.NotNull;
import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.Descriptors.DescriptorValidationException;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.DynamicMessage;

public class ProtobufFuzzer {

    @FuzzTest
    public static void fuzzerTestOneInput(@NotNull DescriptorProto message) {
        FileDescriptorProto file = FileDescriptorProto.newBuilder()
                .setName("my_protos.proto")
                .addMessageType(message)
                .build();
        try {
            DynamicMessage.getDefaultInstance(FileDescriptor.buildFrom(file, new FileDescriptor[0])
                    .findMessageTypeByName(message.getName()));
        } catch (DescriptorValidationException ignored) {
        }
    }
}