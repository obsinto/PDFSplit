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
                        int lineCount = 0;
                        PDFTextStripper stripper = new PDFTextStripper();

                        String pageText = stripper.getText(newDocument);

                        String[] lines = pageText.split("\n");
                        if (lines.length == 17) {
                            String beneficiaryName = lines[12].substring(13).trim();
                            String valueLine = lines[16].substring(40).trim();

                            String newName = beneficiaryName + "_" + valueLine + "_" + pageIndex + ".pdf";

                            File file = new File(newDir, newName);

                            newDocument.save(file);
                            newDocument.close();
                        }
                        for (String line : lines) {
                            if (!line.isEmpty()) {
                                lineCount++;
                            }
                        }
                        System.out.println("Número de linhas na página: " + lineCount);

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