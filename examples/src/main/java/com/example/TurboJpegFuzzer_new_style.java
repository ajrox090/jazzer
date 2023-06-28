// Copyright 2021 Code Intelligence GmbH
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.example;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import org.libjpegturbo.turbojpeg.TJ;
import org.libjpegturbo.turbojpeg.TJDecompressor;
import org.libjpegturbo.turbojpeg.TJException;
import org.libjpegturbo.turbojpeg.TJTransform;
import org.libjpegturbo.turbojpeg.TJTransformer;
import com.code_intelligence.jazzer.mutation.annotation.InRange;

public class TurboJpegFuzzer_new_style {
  static byte[] buffer = new byte[128 * 128 * 4];

  public static void fuzzerInitialize() throws TJException {
    // Trigger an early load of the native library to show the coverage counters stats in libFuzzer.
    new TJDecompressor();
  }

  public static void fuzzerTestOneInput(int flagsDecompress, int flagsTransform,
                                        @InRange(min=TJ.PF_RGB, max=TJ.PF_CMYK) int pixelFormat,
                                        @InRange(min=1, max=128) int desiredWidth,
                                        @InRange(min=1, max=128) int desiredHeight,
                                        @InRange(min=TJTransform.OP_NONE, max=TJTransform.OP_ROT270) int transformOp,
                                        int transformOptions, boolean trans_width, boolean trans_height,
                                        boolean doTransform, byte[] remainingBytes) {
    try {
      int transformWidth = trans_width ? 128 : 64;
      int transformHeight = trans_height ? 128 : 64;
      TJDecompressor tjd;
      if (doTransform) {
        TJTransformer tjt = new TJTransformer(remainingBytes);
        TJTransform tjf = new TJTransform(
            0, 0, transformWidth, transformHeight, transformOp, transformOptions, null);
        tjd = tjt.transform(new TJTransform[] {tjf}, flagsTransform)[0];
      } else {
        tjd = new TJDecompressor(remainingBytes);
      }
      tjd.decompress(buffer, 0, 0, desiredWidth, 0, desiredHeight, pixelFormat, flagsDecompress);
    } catch (Exception ignored) {
      // We are not looking for Java exceptions, but segfaults and ASan reports.
    }
  }
}
