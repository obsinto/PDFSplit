import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;// representa um documento PDF
import org.apache.pdfbox.text.PDFTextStripper; //é usado para extrair o texto do documento PDF

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class SplitPDF {
    public static void main(String[] args) {
        Logger.getLogger("org.apache").setLevel(Level.OFF);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione um arquivo PDF");
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fileChooser.getSelectedFile();

            String fileName = fileChooser.getName(selectedFile);

            JOptionPane.showMessageDialog(null, "Arquivo " + fileName + " Selecionado!");

            try {
                PDDocument document = Loader.loadPDF(selectedFile);

                File baseDir = new File("Lote de Comprovantes");
                baseDir.mkdirs();

                int dirNumber = 1;
                File newDir;
                do {
                    newDir = new File(baseDir, "Lote de Comprovantes" + dirNumber);
                    dirNumber++;
                } while (newDir.exists());
                newDir.mkdirs();

                for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {

                    PDDocument newDocument = new PDDocument();
                    newDocument.addPage(document.getPage(pageIndex));

                    try {

                        PDFTextStripper stripper = new PDFTextStripper();

                        String pageText = stripper.getText(newDocument);

                        String[] lines = pageText.split("\n");

                        if (pageIndex == 0) {
                            String[] copyLines = new String[lines.length - 3];
                            System.arraycopy(lines, 3, copyLines, 0, copyLines.length);
                            lines = copyLines;

                        }

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
                        if (lines.length == 35) {
                            String beneficiaryName = lines[9].substring(23, 38).trim();
                            String valueLine = lines[14].substring(39).trim();

                            String newName = beneficiaryName + "_" + valueLine + "_" + pageIndex + ".pdf";

                            File file = new File(newDir, newName);

                            newDocument.save(file);
                            newDocument.close();

                        }

                        StringBuilder modifiedText = new StringBuilder();
                        int lineCount = 0;

                        for (String line : lines) {
                            if (!line.isEmpty()) {
                                modifiedText.append(lineCount).append(".").append(line).append("\n");
                                lineCount++;
                            }
                        }

                        System.out.println("Número de linhas na página: " + lineCount);
                        // System.out.println(modifiedText);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                document.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (result == JFileChooser.CANCEL_OPTION) {

            JOptionPane.showMessageDialog(null, "Seleção de PDF cancelada.");
        }

    }
}