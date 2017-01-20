package com.example.dventus_hq.demohandheldapp.Database_and_File_Managment;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by dVentus-hq on 22/8/2016.
 */
public class FileCompressor {

    private String inputPath;
    private String outZip;


    public FileCompressor(String outZip) {
        this.outZip = outZip;
    }

    public  void zipFolder(File[] files) {
        try {
            //setting up the output file
            FileOutputStream fos = new FileOutputStream(outZip);
            ZipOutputStream zos = new ZipOutputStream(fos);

            //File srcFile = new File(inputPath);
           // File[] files = srcFile.listFiles();

          //  Log.d("", "Zip directory: " + srcFile.getName());

            for (int i = 0; i < files.length; i++) {
                //Log.d("", "Adding file: " + files[i].getName());
                byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(files[i]);
                zos.putNextEntry(new ZipEntry(files[i].getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
        } catch (IOException ioe) {
            Log.e("", ioe.getMessage());
        }
    }
}
