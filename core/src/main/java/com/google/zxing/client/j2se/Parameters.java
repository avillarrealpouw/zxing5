/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.google.zxing.client.j2se;

//import com.beust.jcommander.JCommander;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.pdf417.encoder.Compaction;
import com.google.zxing.pdf417.encoder.Dimensions;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author avillarr
 */
public class Parameters {
  public int columns;
  public int width;
  public int height;
  public double scalex;
  public double scaley;
  public boolean test;
  public int pixperm;
  public String errorCorrectionLevel1;
  public double separation;
  public double separationy;
  public String inargs;
  public int height2;
  public Map<EncodeHintType, Object> hints;
  public BarcodeFormat conf_barcodeFormat;
  public String conf_outputFileBase;
  public int conf_width;
  public int conf_height;
  public String conf_imageFormat;
  public boolean conf_help;
  public String conf_contents;
  public Parameters(String[] args){
    this.columns=10;
    String config_linux_s="1";
    String parameterName = "c:\\Temp\\parameters2.txt";
    if(config_linux_s.equals("1")){
      parameterName = "/home/avillarr/tmp/parameters2.txt";
    }
    String config_width_s="";
    String config_height_s="";
    String config_columns_s="";
    String config_errorCorrectionLevel_s="";
    String config_pixperm_s="";
    String config_separation_s="";
    String config_separationy_s="";
    String config_test_s="";
    String config_args_s="";
    try (BufferedReader reader1 = new BufferedReader(new FileReader(parameterName))){
      config_width_s = reader1.readLine();
      config_height_s = reader1.readLine();
      config_columns_s = reader1.readLine();
      config_errorCorrectionLevel_s = reader1.readLine();
      config_pixperm_s = reader1.readLine();
      config_separation_s = reader1.readLine();
      config_separationy_s = reader1.readLine();
      config_test_s = reader1.readLine();
      config_args_s = reader1.readLine();
      reader1.close();
    } catch (IOException e) {
      System.out.println("error reading parameters");
    }
    try {
      width = Integer.parseInt(config_width_s);
      height = Integer.parseInt(config_height_s);
      columns = Integer.parseInt(config_columns_s);
      errorCorrectionLevel1 = config_errorCorrectionLevel_s;
      pixperm = Integer.parseInt(config_pixperm_s);
      separation = Double.parseDouble(config_separation_s);
      separationy = Double.parseDouble(config_separationy_s);
      test = Integer.parseInt(config_test_s)==1;
      inargs = config_args_s;
    } catch (NumberFormatException e) {
      System.out.println("error interpreting numeric values");
    }
    hints = new EnumMap<>(EncodeHintType.class);
    Dimensions dimensions = new Dimensions(columns, columns, 1, 10000);
    hints.put(EncodeHintType.AZTEC_LAYERS, "x");
    hints.put(EncodeHintType.CHARACTER_SET, "ISO-8859-1");
    hints.put(EncodeHintType.CODE128_COMPACT, "x");
    hints.put(EncodeHintType.DATA_MATRIX_COMPACT, "x");
    hints.put(EncodeHintType.DATA_MATRIX_SHAPE, "x");
    hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel1);
    hints.put(EncodeHintType.FORCE_C40, "x");
    hints.put(EncodeHintType.FORCE_CODE_SET, "x");
    hints.put(EncodeHintType.GS1_FORMAT, "x");
    hints.put(EncodeHintType.MARGIN, "1");
    //hints.put(EncodeHintType.MAX_SIZE, "x");
    //hints.put(EncodeHintType.MIN_SIZE, "x");
    hints.put(EncodeHintType.PDF417_COMPACT, "x");
    hints.put(com.google.zxing.EncodeHintType.PDF417_COMPACTION, Compaction.AUTO);
    hints.put(com.google.zxing.EncodeHintType.PDF417_DIMENSIONS, dimensions);
    hints.put(EncodeHintType.PDF417_AUTO_ECI, "true");
    hints.put(EncodeHintType.QR_COMPACT, "x");
    hints.put(EncodeHintType.QR_MASK_PATTERN, "x");
    hints.put(EncodeHintType.QR_VERSION, "x");
    //EncoderConfig config = new EncoderConfig();
    //JCommander jCommander = new JCommander(config);
    //jCommander.parse(args);
    //jCommander.setProgramName(CommandLineEncoder3.class.getSimpleName());
    //if (config.help) {
    //  jCommander.usage();
    //}
    //conf_barcodeFormat=config.barcodeFormat;
    //conf_outputFileBase=config.outputFileBase;
    //conf_width=config.width;
    //conf_height=config.height;
    //conf_errorCorrectionLevel=errorCorrectionLevel1;
    //conf_imageFormat=config.imageFormat;
    //conf_help=config.help;
    //conf_contents="";
    //for(int i=0;i<config.contents.size();i++){
    //  conf_contents+=config.contents.get(i);
    //  if(i<config.contents.size()-1){
    //    conf_contents+=" ";
    //  }
    //}
  }
}
