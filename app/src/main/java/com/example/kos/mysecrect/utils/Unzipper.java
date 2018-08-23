package com.example.kos.mysecrect.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Unzipper {

    public static File unzip(final File file, final File destination) throws IOException {
        File fileJson = null;
        FileInputStream fIS ;
        fIS = new FileInputStream(file);
        String workingDir = destination.getAbsolutePath() + "/";
        byte[] buffer = new byte[4096];
        try (ZipInputStream zin = new ZipInputStream(fIS)) {
            fileJson = getFile(fileJson, fIS, workingDir, buffer, zin);
        }
        return fileJson;
    }

    private static File getFile(File fileJson, FileInputStream fIS, String workingDir, byte[] buffer, ZipInputStream zin) throws IOException {
        ZipEntry entry;
        while ((entry = zin.getNextEntry()) != null) {
            File dir = new File(workingDir, entry.getName());
            if (entry.isDirectory()) {
                if (!dir.exists() && dir.mkdir()) {
                    fileJson = dir;
                    continue;
                }
                fileJson = dir;
            } else {
                fileJson = dir;
                writeFile(workingDir, buffer, zin, entry);
            }
        }
        zin.close();
        fIS.close();
        return fileJson;
    }

    private static void writeFile(String workingDir, byte[] buffer, ZipInputStream zin, ZipEntry entry) throws IOException {
        int bytesRead;
        try (FileOutputStream fos = new FileOutputStream(workingDir + entry.getName())) {
            while ((bytesRead = zin.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.close();
        }
    }

//    public static File unzip(final File file, final File destination) throws IOException {
//        File fileJson = null;
//        FileInputStream fIS ;
//        FileOutputStream fos ;
//        ZipInputStream zin ;
//
//        fIS = new FileInputStream(file);
//        zin = new ZipInputStream(fIS);
//
//        String workingDir = destination.getAbsolutePath() + "/";
//
//        byte[] buffer = new byte[4096];
//        int bytesRead;
//        ZipEntry entry;
//        while ((entry = zin.getNextEntry()) != null) {
//            File dir = new File(workingDir, entry.getName());
//            if (entry.isDirectory()) {
//                if (!dir.exists() && dir.mkdir()) {
//                    fileJson = dir;
//                    continue;
//                }
//                fileJson = dir;
//            } else {
//                fileJson = dir;
//                fos = new FileOutputStream(workingDir + entry.getName());
//                while ((bytesRead = zin.read(buffer)) != -1) {
//                    fos.write(buffer, 0, bytesRead);
//                }
//                fos.close();
//            }
//        }
//        zin.close();
//        fIS.close();
//        return fileJson;
//    }
}