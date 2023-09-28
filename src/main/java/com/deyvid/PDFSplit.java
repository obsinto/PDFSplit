package com.deyvid;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;// representa um documento PDF
import org.apache.pdfbox.text.PDFTextStripper; //é usado para extrair o texto do documento PDF
import org.apache.pdfbox.pdmodel.PDPage;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URISyntaxException;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class PDFSplit {
    public static void main(String[] args) {
        Logger.getLogger("org.apache").setLevel(Level.OFF);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione um arquivo PDF");
        fileChooser.setMultiSelectionEnabled(true);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            File newDir = null;

            try {
                newDir = getFile();
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return;
            }

            for (File selectedFile : selectedFiles) {

                try {
                    PDDocument document = Loader.loadPDF(selectedFile);

                    int totalPageCount = 0;
                    for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {

                        PDDocument newDocument = new PDDocument();
                        newDocument.addPage(document.getPage(pageIndex));

                        try {

                            PDFTextStripper stripper = new PDFTextStripper();

                            String pageText = stripper.getText(newDocument);

                            String[] lines = pageText.split("\n");

                            // if (pageIndex == 0) {
                            // String[] copyLines = new String[lines.length - 3];
                            // System.arraycopy(lines, 3, copyLines, 0, copyLines.length);
                            // lines = copyLines;
                            // }

                            if (pageIndex == 0) {
                                lines = Arrays.copyOfRange(lines, 4, lines.length);
                            }

                            // if(pageIndex == document.getNumberOfPages()){
                            // lines = Arrays.copyOf(lines, lines.length -1);
                            // }
                            // if (pageIndex == document.getNumberOfPages() - 1) {
                            // lines = Arrays.copyOfRange(lines, 0, lines.length - 1);
                            // }

                            if (lines.length == 23) {
                                String beneficiaryName = lines[15].substring(12, 26).trim();
                                String valueLine = lines[17].substring(39).trim();

                                String newName = beneficiaryName + "_" + valueLine + "_" + pageIndex +
                                        ".pdf";

                                File file = new File(newDir, newName);

                                newDocument.save(file);
                                newDocument.close();

                            }

                            if (lines.length == 17) {
                                String beneficiaryName = lines[12].substring(8, 20).trim();
                                String valueLine = lines[10].substring(39).trim();

                                String newName = beneficiaryName + "_" + valueLine + "_" + pageIndex +
                                        ".pdf";

                                File file = new File(newDir, newName);

                                newDocument.save(file);
                                newDocument.close();
                            }

                            if (lines.length == 16) {
                                String beneficiaryName = lines[6].substring(4, 14).trim();
                                String valueLine = lines[9].substring(5).trim();

                                String newName = beneficiaryName + "_" + valueLine + "_" + pageIndex +
                                        ".pdf";

                                File file = new File(newDir, newName);

                                newDocument.save(file);
                                newDocument.close();
                            }

                            if (lines.length == 15) {
                                String beneficiaryName = lines[6].substring(4, 14).trim();
                                String valueLine = lines[9].substring(5).trim();

                                String newName = beneficiaryName + "_" + valueLine + "_" + pageIndex +
                                        ".pdf";

                                File file = new File(newDir, newName);

                                newDocument.save(file);
                                newDocument.close();
                            }

                            if (lines.length == 35) {
                                String beneficiaryName = lines[9].substring(23, 38).trim();
                                String valueLine = lines[14].substring(39).trim();

                                String newName = beneficiaryName + "_" + valueLine + "_" + pageIndex + ".pdf";

                                File file = new File(newDir, newName);

                                newDocument.save(file);
                                newDocument.close();

                            }
                            if (lines.length == 21) {
                                String beneficiaryName = lines[9].substring(16, 25).trim();
                                String valueLine = lines[12].substring(5).trim();

                                String newName = beneficiaryName + "_" + valueLine + "_" + pageIndex + ".pdf";

                                File file = new File(newDir, newName);

                                newDocument.save(file);
                                newDocument.close();

                            }
                            if (lines.length == 19) {
                                String beneficiaryName = lines[7].substring(16, 25).trim();
                                String valueLine = lines[10].substring(5).trim();

                                String newName = beneficiaryName + "_" + valueLine + "_" + pageIndex + ".pdf";

                                File file = new File(newDir, newName);

                                newDocument.save(file);
                                newDocument.close();

                            }
                            // if (lines.length == 19) {
                            // String beneficiaryName = lines[9].substring(16, 18).trim();
                            // String valueLine = lines[12].substring(6).trim();

                            // String newName = beneficiaryName + "_" + valueLine + "_" + pageIndex +
                            // ".pdf";

                            // File file = new File(newDir, newName);

                            // newDocument.save(file);
                            // newDocument.close();

                            // }
                            StringBuilder modifiedText = new StringBuilder();
                            int lineCount = 0;

                            for (String line : lines) {
                                if (!line.isEmpty()) {
                                    modifiedText.append(lineCount).append(".").append(line).append("\n");
                                    lineCount++;
                                }
                            }
                            totalPageCount++;
                            System.out.println("Número de linhas na página: " + lineCount);
                            System.out.println(modifiedText);
                            // System.out.println(Arrays.toString(lines));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    System.out.println("numero de paginas: " + totalPageCount);
                    document.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } else if (result == JFileChooser.CANCEL_OPTION) {

            JOptionPane.showMessageDialog(null, "Seleção de PDF cancelada.");
        }

    }

    private static File getFile() throws URISyntaxException {
        File jarFile = new File(PDFSplit.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        File jarDir = jarFile.getParentFile();

        File baseDir = new File(jarDir, "Lote de Comprovantes");
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        int dirNumber = 1;

        File newDir;
        do {
            newDir = new File(baseDir, "Lote de Comprovantes" + dirNumber);
            dirNumber++;
        } while (newDir.exists());
        newDir.mkdirs();
        return newDir;
    }
}