/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.google.zxing.client.j2se;

import com.google.zxing.common.BitMatrix;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 *
 * @author avillarr
 */
public class MatrixToBMP {

  private static byte byte0(int x) {
    x = x & 0xff;
    return (byte) x;
  }

  private static byte byte1(int x) {
    x = (x >> 8) & 0xff;
    return (byte) x;
  }

  private static byte byte2(int x) {
    x = (x >> 16) & 0xff;
    return (byte) x;
  }

  private static byte byte3(int x) {
    x = (x >> 24) & 0xff;
    return (byte) x;
  }

  private static void fillheader(byte[] outbmp, int fsize, int mwidth2, int mheight2, int fimage, int pixperm) {
    for (int j = 0; j < 0x3e; j++) {
      outbmp[j] = (byte) 0x00;
    }
    outbmp[0x00] = (byte) 0x42;
    outbmp[0x01] = (byte) 0x4d;
    outbmp[0x02] = byte0(fsize);
    outbmp[0x03] = byte1(fsize);
    outbmp[0x04] = byte2(fsize);
    outbmp[0x05] = byte3(fsize);
    outbmp[0x0a] = (byte) 0x3e;
    outbmp[0x0e] = (byte) 0x28;
    outbmp[0x12] = byte0(mwidth2);
    outbmp[0x13] = byte1(mwidth2);
    outbmp[0x14] = byte2(mwidth2);
    outbmp[0x15] = byte3(mwidth2);
    outbmp[0x16] = byte0(mheight2);
    outbmp[0x17] = byte1(mheight2);
    outbmp[0x18] = byte2(mheight2);
    outbmp[0x19] = byte3(mheight2);
    outbmp[0x1a] = (byte) 0x01;
    outbmp[0x1c] = (byte) 0x01;
    outbmp[0x22] = byte0(fimage);
    outbmp[0x23] = byte1(fimage);
    outbmp[0x24] = byte2(fimage);
    outbmp[0x25] = byte3(fimage);
    outbmp[0x26] = byte0(pixperm);
    outbmp[0x27] = byte1(pixperm);
    outbmp[0x28] = byte2(pixperm);
    outbmp[0x29] = byte3(pixperm);
    outbmp[0x2a] = byte0(pixperm);
    outbmp[0x2b] = byte1(pixperm);
    outbmp[0x2c] = byte2(pixperm);
    outbmp[0x2d] = byte3(pixperm);
    outbmp[0x3a] = (byte) 0xff;
    outbmp[0x3b] = (byte) 0xff;
    outbmp[0x3c] = (byte) 0xff;

  }

  public static void MatrixToBMP1(BitMatrix matrix, Parameters parms, String fileString2
  ) throws FileNotFoundException, IOException {
    int mwidth = matrix.getWidth();
    int mheight = matrix.getHeight();
    int mrowsize = matrix.getRowSize();
    boolean found = false;
    int i1 = 0;
    int j1 = 0;
    if (matrix.get(j1, i1)) {
      System.out.println("error, no hay margen");
    }
    for (int i3 = 0; i3 < 64; i3++) {
      for (int j3 = 0; j3 < 64; j3++) {
        if (!found && matrix.get(j3, i3)) {
          i1 = i3;
          j1 = j3;
          found = true;
        }
      }
    }
    if (!found) {
      System.out.println("error: borde superior izquierdo no fue encontrado");
    } else {
      System.out.println("borde encontrado en fila " + i1 + ", columna " + j1);
    }
    found = false;
    int i2 = mheight - 1;
    int j2 = mwidth - 1;
    for (int i3 = 0; i3 < 64; i3++) {
      for (int j3 = 0; j3 < 32; j3++) {
        if (!found && matrix.get(mwidth - 1 - j3, mheight - 1 - i3)) {
          i2 = mheight - 1 - i3;
          j2 = mwidth - 1 - j3;
          found = true;
        }
      }
    }
    if (!found) {
      System.out.println("error: borde inferior derecho no fue encontrado");
    } else {
      System.out.println("borde inferior derecho fila: " + i2 + " columna: " + j2);
      while (matrix.get(j2, i2)) {
        j2--;
      }
      System.out.println("borde encontrado en fila " + i2 + ", columna " + j2);
      System.out.println("excluyendo linea extra");
    }
    System.out.println("width: " + mwidth + " padded width: " + mrowsize * 32 + " height: " + mheight);
    //for(int i3=0;i3<16;i3++){
    //  for(int j3=0;j3<mwidth;j3++){
    //    if(matrix.get(j3, i3)){
    //      System.out.print("O");
    //    }else{
    //      System.out.print(".");
    //    }
    //  }
    //  System.out.println();
    //}
    int j4 = 0;
    for (int j3 = 0; j3 < 256; j3++) {
      if (!matrix.get(j3 + j1, i1)) {
        j4 = j3;
        break;
      }
    }
    System.out.println("width of first bar: " + j4);
    int r6 = j4 / 8;
    System.out.println("rectangle width: " + r6);
    if ((j4 % 8) != 0) {
      System.out.println("error in rectangle width!");
    }
    int r4 = j2 - j1 + 1;
    System.out.println("width: " + r4 + "pixels plus " + r6 + " of extra line");
    int r5 = r4 / r6;
    System.out.println("width: " + r5 + " rectangles plus extra line plus margin");
    double scalex = (double) (parms.width - 1) / (double) (r5 + 3);
    System.out.println("x scale: " + scalex);
    int mwidth2 = parms.width;
    int mrowsize2 = mwidth2 / 32;
    if ((mwidth2 % 32) != 0) {
      mrowsize2++;
    }
    System.out.println("scaled total width: " + mwidth2 + " rowsize: " + mrowsize2);
    if ((r4 % r6) != 0) {
      System.out.println("error in rectangle width");
    }
    int r7 = r5 / 17;
    System.out.println("width: " + r7 + " columns including sentinels and checks");
    if ((r5 % 17) != 0) {
      System.out.println("error in column width");
    }
    int r1 = i2 - i1 + 1;
    System.out.println("height: " + r1 + " pixels");
    int r2 = j4 / 8 * 4;
    System.out.println("rectangle height: " + r2 + " pixels");
    int r3 = r1 / r2;
    System.out.println("height: " + r3 + " rectangles plus 2 border rectangles");
    double scaley = (double) (parms.height - 1) / (double) (r3 + 2);
    System.out.println("y scale: " + scaley);
    int mheight2 = parms.height;
    if ((r1 % r2) != 0) {
      System.out.println("error in rectangle height");
    }
    System.out.println("mheight2 " + mheight2 + " mwidth2 " + mwidth2 + " mrowsize2 " + mrowsize2);
    int fimage = mrowsize2 * 4 * mheight2;
    int fsize = fimage + 0x3e;
    int pixperm = parms.pixperm;
    System.err.println("Pixels per meter: " + pixperm);
    byte[] outbmp = new byte[fsize];
    byte[] outbmpe = new byte[fsize];
    byte[] outbmpo = new byte[fsize];
    byte[] outbmpee = new byte[fsize];
    byte[] outbmpeo = new byte[fsize];
    byte[] outbmpoe = new byte[fsize];
    byte[] outbmpoo = new byte[fsize];
    fillheader(outbmp, fsize, mwidth2, mheight2, fimage, pixperm);
    fillheader(outbmpe, fsize, mwidth2, mheight2, fimage, pixperm);
    fillheader(outbmpo, fsize, mwidth2, mheight2, fimage, pixperm);
    fillheader(outbmpee, fsize, mwidth2, mheight2, fimage, pixperm);
    fillheader(outbmpeo, fsize, mwidth2, mheight2, fimage, pixperm);
    fillheader(outbmpoe, fsize, mwidth2, mheight2, fimage, pixperm);
    fillheader(outbmpoo, fsize, mwidth2, mheight2, fimage, pixperm);
    int ifile = 0x3e;
    int iy = 0;
    double separation = parms.separation;
    double separationy = parms.separationy;
    double endpointy = 0.0;
    BitArray2 a1 = new BitArray2(true, mwidth2);
    while (iy < endpointy + scaley) {
      for (int i3 = 0; i3 < mrowsize2 * 4; i3++) {
        outbmp[ifile] = a1.a[i3];
        outbmpe[ifile] = a1.a[i3];
        outbmpo[ifile] = a1.a[i3];
        outbmpee[ifile] = a1.a[i3];
        outbmpeo[ifile] = a1.a[i3];
        outbmpoe[ifile] = a1.a[i3];
        outbmpoo[ifile] = a1.a[i3];
        ifile++;
      }
      iy++;
    }
    boolean eveny = false;
    for (int i3 = i2; i3 > i1; i3 -= r2) {
      boolean even = false;
      BitArray2 a = new BitArray2(true, mwidth2);
      BitArray2 ae = new BitArray2(true, mwidth2);
      BitArray2 ao = new BitArray2(true, mwidth2);
      a.append(true, scalex);
      ae.append(true, scalex);
      ao.append(true, scalex);
      for (int j3 = j1; j3 < j2 + r6; j3 += r6) {
        if (matrix.get(j3, i3)) {
          //System.out.print("O");
          if (even) {
            a.append(false, scalex - separation);
            a.append(true, separation);
            ae.append(false, scalex - separation);
            ae.append(true, separation);
            ao.append(true, scalex);
          } else {
            a.append(false, scalex - separation);
            a.append(true, separation);
            ae.append(true, scalex);
            ao.append(false, scalex - separation);
            ao.append(true, separation);
          }
        } else {
          //System.out.print(".");
          a.append(true, scalex);
          ae.append(true, scalex);
          ao.append(true, scalex);
        }
        even = !even;
      }
      while (iy < endpointy + scaley) {
        for (int i4 = 0; i4 < mrowsize2 * 4; i4++) {
          outbmp[ifile] = a.a[i4];
          outbmpe[ifile] = ae.a[i4];
          outbmpo[ifile] = ao.a[i4];
          if (eveny) {
            outbmpee[ifile] = a1.a[i4];
            outbmpeo[ifile] = ae.a[i4];
            outbmpoe[ifile] = a1.a[i4];
            outbmpoo[ifile] = ao.a[i4];
          } else {
            outbmpee[ifile] = ae.a[i4];
            outbmpeo[ifile] = a1.a[i4];
            outbmpoe[ifile] = ao.a[i4];
            outbmpoo[ifile] = a1.a[i4];
          }
          ifile++;
        }
        iy++;
      }
      //System.out.println();
      endpointy += scaley;
      eveny = !eveny;
    }
    while (ifile < fsize) {
      outbmp[ifile] = (byte) 0xff;
      outbmpe[ifile] = (byte) 0xff;
      outbmpo[ifile] = (byte) 0xff;
      outbmpee[ifile] = (byte) 0xff;
      outbmpeo[ifile] = (byte) 0xff;
      outbmpoe[ifile] = (byte) 0xff;
      outbmpoo[ifile] = (byte) 0xff;
      ifile++;
    }

    File file2 = new File(fileString2 + ".bmp");
    try (FileOutputStream stream2 = new FileOutputStream(file2)) {
      stream2.write(outbmp);
    }
    System.out.println("encoding outbmp, size: " + outbmp.length);
    String encoded = Base64.getEncoder().encodeToString(outbmp);
    System.out.println("encoded b64, size: " + encoded.length());
    File file4 = new File(fileString2 + ".bmp.b64");
    FileOutputStream file4s;
    file4s = new FileOutputStream(file4);
    file4s.write(encoded.getBytes());
    file4s.close();

    File file2e = new File(fileString2 + "e.bmp");
    try (FileOutputStream stream2e = new FileOutputStream(file2e)) {
      stream2e.write(outbmpe);
    }
    System.out.println("encoding outbmp even, size: " + outbmpe.length);
    String encodede = Base64.getEncoder().encodeToString(outbmpe);
    System.out.println("encoded b64, size: " + encodede.length());
    File file4e = new File(fileString2 + "e.bmp.b64");
    FileOutputStream file4se;
    file4se = new FileOutputStream(file4e);
    file4se.write(encodede.getBytes());
    file4se.close();

    File file2o = new File(fileString2 + "o.bmp");
    try (FileOutputStream stream2o = new FileOutputStream(file2o)) {
      stream2o.write(outbmpo);
    }
    System.out.println("encoding outbmp odd, size: " + outbmpo.length);
    String encodedo = Base64.getEncoder().encodeToString(outbmpo);
    System.out.println("encoded b64, size: " + encodedo.length());
    File file4o = new File(fileString2 + "o.bmp.b64");
    FileOutputStream file4so;
    file4so = new FileOutputStream(file4o);
    file4so.write(encodedo.getBytes());
    file4so.close();

    File file2ee = new File(fileString2 + "ee.bmp");
    try (FileOutputStream stream2ee = new FileOutputStream(file2ee)) {
      stream2ee.write(outbmpee);
    }
    System.out.println("encoding outbmp even even, size: " + outbmpee.length);
    String encodedee = Base64.getEncoder().encodeToString(outbmpee);
    System.out.println("encoded b64, size: " + encodedee.length());
    File file4ee = new File(fileString2 + "ee.bmp.b64");
    FileOutputStream file4see;
    file4see = new FileOutputStream(file4ee);
    file4see.write(encodedee.getBytes());
    file4see.close();

    File file2eo = new File(fileString2 + "eo.bmp");
    try (FileOutputStream stream2eo = new FileOutputStream(file2eo)) {
      stream2eo.write(outbmpeo);
    }
    System.out.println("encoding outbmp even odd, size: " + outbmpeo.length);
    String encodedeo = Base64.getEncoder().encodeToString(outbmpeo);
    System.out.println("encoded b64, size: " + encodedeo.length());
    File file4eo = new File(fileString2 + "eo.bmp.b64");
    FileOutputStream file4seo;
    file4seo = new FileOutputStream(file4eo);
    file4seo.write(encodedeo.getBytes());
    file4seo.close();

    File file2oe = new File(fileString2 + "oe.bmp");
    try (FileOutputStream stream2oe = new FileOutputStream(file2oe)) {
      stream2oe.write(outbmpoe);
    }
    System.out.println("encoding outbmp odd even, size: " + outbmpoe.length);
    String encodedoe = Base64.getEncoder().encodeToString(outbmpoe);
    System.out.println("encoded b64, size: " + encodedoe.length());
    File file4oe = new File(fileString2 + "oe.bmp.b64");
    FileOutputStream file4soe;
    file4soe = new FileOutputStream(file4oe);
    file4soe.write(encodedoe.getBytes());
    file4soe.close();

    File file2oo = new File(fileString2 + "oo.bmp");
    try (FileOutputStream stream2oo = new FileOutputStream(file2oo)) {
      stream2oo.write(outbmpoo);
    }
    System.out.println("encoding outbmp odd odd, size: " + outbmpoo.length);
    String encodedoo = Base64.getEncoder().encodeToString(outbmpoo);
    System.out.println("encoded b64, size: " + encodedoo.length());
    File file4oo = new File(fileString2 + "oo.bmp.b64");
    FileOutputStream file4soo;
    file4soo = new FileOutputStream(file4oo);
    file4soo.write(encodedoo.getBytes());
    file4soo.close();
  }
}
