package com.fjhidalgo.restaurante.util.pdf;

import android.os.Environment;

import java.io.FileOutputStream;

public class PdfUtils {

    public static void createandDisplayPdf(String text) {

//        Document doc = new Document();
//
//        try {
//            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";
//
//            File dir = new File(path);
//            if(!dir.exists())
//                dir.mkdirs();
//
//            File file = new File(dir, "newFile.pdf");
//            FileOutputStream fOut = new FileOutputStream(file);
//
//            PdfWriter.getInstance(doc, fOut);
//
//            //open the document
//            doc.open();
//
//            Paragraph p1 = new Paragraph(text);
//            Font paraFont= new Font(Font.COURIER);
//            p1.setAlignment(Paragraph.ALIGN_CENTER);
//            p1.setFont(paraFont);
//
//            //add paragraph to document
//            doc.add(p1);
//
//        } catch (DocumentException de) {
//            Log.e("PDFCreator", "DocumentException:" + de);
//        } catch (IOException e) {
//            Log.e("PDFCreator", "ioException:" + e);
//        }
//        finally {
//            doc.close();
//        }
//
//        viewPdf("newFile.pdf", "Dir");
    }
}
