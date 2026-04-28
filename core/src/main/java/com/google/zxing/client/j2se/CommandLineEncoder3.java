/*
 * Copyright 2011 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.zxing.client.j2se;

import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
//import com.google.zxing.pdf417.encoder.Compaction;
import com.google.zxing.pdf417.encoder.Dimensions;

import com.beust.jcommander.JCommander;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.pdf417.encoder.Compaction;
import java.io.BufferedOutputStream;
//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.Base64;

//import java.nio.file.Paths;
import java.util.EnumMap;
//import java.util.Locale;
import java.util.Map;

/**
 * Command line utility for encoding barcodes.
 *
 * @author Sean Owen
 */
public final class CommandLineEncoder3 {

  private CommandLineEncoder3() {
  }

  static String readhexfile(String s1) throws FileNotFoundException, IOException {
    System.out.println("hexa filename: " + s1);
    File infilehex = new File(s1);
    int infilehexsz = (int) infilehex.length();
    System.out.println("filesize: " + infilehexsz);
    if (infilehexsz % 2 == 1) {
      System.out.println("Error en el archivo hexa");
    }
    byte[] ba1 = new byte[infilehexsz];
    FileInputStream instream1 = new FileInputStream(infilehex);
    instream1.read(ba1);
    System.out.println("Size of hexa file: " + infilehexsz);
    StringBuilder strb1 = new StringBuilder(infilehexsz / 2);
    for (int i5 = 0; i5 < ba1.length; i5 += 2) {
      boolean goodhex = true;
      int l = 0;
      int m = 0;
      int j1 = ba1[i5];
      int k1 = ba1[i5 + 1];
      if (j1 > (48 - 1) && j1 < (48 + 10)) {
        l = j1 - 48;
      } else if (j1 > (65 - 1) && j1 < (65 + 7)) {
        l = j1 - 65 + 10;
      } else if (j1 > (97 - 1) && j1 < (97 + 7)) {
        l = j1 - 97 + 10;
      } else {
        goodhex = false;
        System.out.println("error en el archivo hexa");
      }
      if (k1 > (48 - 1) && k1 < (48 + 10)) {
        m = k1 - 48;
      } else if (k1 > (65 - 1) && k1 < (65 + 7)) {
        m = k1 - 65 + 10;
      } else if (k1 > (97 - 1) && k1 < (97 + 7)) {
        m = k1 - 97 + 10;
      } else {
        goodhex = false;
        System.out.println("error en el archivo hexa");
      }
      int n = l * 16 + m;
      char nc = (char) n;
      if (goodhex) {
        strb1.append(nc);
      }
    }
    return strb1.toString();
  }

  /**
   * Calculates the necessary number of rows as described in annex Q of ISO/IEC 15438:2001(E).
   *
   * @param m the number of source codewords prior to the additional of the Symbol Length Descriptor and any pad
   * codewords
   * @param k the number of error correction codewords
   * @param c the number of columns in the symbol in the data region (excluding start, stop and row indicator codewords)
   * @return the number of rows in the symbol (r)
   */
  static int calculateNumberOfRows2(int m, int k, int c) {
    int r = ((m + 1 + k) / c) + 1;
    if (c * r >= (m + 1 + k + c)) {
      r--;
    }
    return r;
  }

  static void process(String s1, Parameters parms) throws Exception {
    //String config_imageFormat = "BMP";
    BarcodeFormat config_barcodeFormat = BarcodeFormat.PDF_417;
    //String config_errorCorrectionLevel = "1";

    int separator = s1.indexOf('|');
    String config_basefilename = s1.substring(separator + 1);
    String config_infilename = s1.substring(0, separator);
    System.out.println("infilename: [" + config_infilename + "], basefilename: [" + config_basefilename + "]");
    String str1 = readhexfile(config_infilename);
    int width1 = (4 * 17) * (parms.columns + 4) + (2 * 4) + 4;
    int height1 = (calculateNumberOfRows2(str1.length(), 4, parms.columns) + 2) * 16;
    System.out.println("MultiFormatWriter called with width: " + width1 + " height: " + height1);
    BitMatrix matrix = new MultiFormatWriter().encode(str1, config_barcodeFormat, width1, height1, parms.hints);
    String fileString1 = config_basefilename;
    MatrixToBMP.MatrixToBMP1(matrix, parms, fileString1);
    //MatrixToImageWriter.writeToPath(matrix, config_imageFormat, Paths.get(fileString1));
    //File file1 = new File(fileString1);
    //byte [] ba = new byte [ (int) file1.length() ];
    //try (FileInputStream stream1 = new FileInputStream(file1)) {
    //  stream1.read(ba);
    //  ba[0x26] = (byte) 0x8c;
    //  ba[0x27] = (byte) 0xb8;
    //  ba[0x2a] = (byte) 0x8c;
    //  ba[0x2b] = (byte) 0xb8;
    //  System.out.println("Resolution: " + ba[0x26] + " " + ba[0x27] + " " + ba[0x2a] + " " + ba[0x2b]);
    //  //instream.close();
    //  File file3 = new File(fileString3);
    //  try (FileOutputStream stream3 = new FileOutputStream(file3)) {
    //    stream3.write(ba);
    //  }
    //File file3 = new File(fileString1);
    //byte [] ba2 = new byte [ (int) file3.length() ];
    //try (FileInputStream stream3 = new FileInputStream(file3)) {
    //  stream3.read(ba2);
    //} catch (IOException ex) {
    //  System.out.println("problem reading scaled bmp");
    //}
    //}
    //outstream.close();
  }

  public static void main(String[] args) throws Exception {
    Parameters parms = new Parameters(args);

    try {
      byte[] b1 = new byte[1024];
      ServerSocket ssoc;
      ssoc = new ServerSocket(4379);
      boolean testing = parms.test;
      boolean ends = false;
      String s1;
      while (!ends) {
        if (!testing) {
          System.out.println("waiting to accept");
          try (Socket soc = ssoc.accept()) {
            System.out.println("waiting for message");
            try (InputStream is = soc.getInputStream()) {
              OutputStream os = soc.getOutputStream();
              BufferedOutputStream bos;
              bos = new BufferedOutputStream(os);
              int r1 = is.read(b1);
              StringBuilder sb1 = new StringBuilder(r1);
              for (int i = 0; i < r1; i++) {
                char c1 = (char) b1[i];
                sb1.append(c1);
              }
              s1 = sb1.toString();
              System.out.println("received message: [" + s1 + "] length: " + s1.length());
              if (!"stop".equals(s1)) {
                process(s1, parms);
              }
              System.out.println("sending response");
              String s = "server response to: " + s1;
              byte[] b = s.getBytes();
              bos.write(b);
              bos.flush();
              bos.close();
            }
          }
          if ("stop".equals(s1)) {
            ends = true;
          }
        } else {
          s1 = parms.inargs;
          System.out.println("simulated message: [" + s1 + "] length: " + s1.length());
          process(s1, parms);
          ends = true;
        }
      }
      ssoc.close();
    } catch (IOException e) {
      System.out.println(e);
    }
    System.out.println("done");
  }
}
