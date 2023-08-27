import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;// representa um documento PDF
import org.apache.pdfbox.text.PDFTextStripper; //é usado para extrair o texto do documento PDF

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SplitPDF {
    public static void main(String[] args) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione um arquivo PDF");
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fileChooser.getSelectedFile();

            String fileName = fileChooser.getName(selectedFile);

            JOptionPane.showMessageDialog(null, "Arquivo " + fileName + " Selecionado!");

            try {
                PDDocument document = Loader.loadPDF(selectedFile);

                File newDir = new File("Lote de Comprovantes");
                newDir.mkdirs();

                for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {

                    PDDocument newDocument = new PDDocument();
                    newDocument.addPage(document.getPage(pageIndex));

                    String newName = "pagina + " + (pageIndex + 1) + ".pdf";

                    File file = new File(newDir, newName);
                    newDocument.save(file);
                    newDocument.close();
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