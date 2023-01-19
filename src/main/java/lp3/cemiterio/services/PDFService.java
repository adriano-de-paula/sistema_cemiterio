/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.services;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.List;
import lp3.cemiterio.models.Service;
import lp3.cemiterio.models.ServiceOrder;

public class PDFService {
    public void createBill(ServiceOrder so, List<Service> services) throws FileNotFoundException {
        Document document = new Document();
        try
        {
           PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("boleto"+so.getConcessionHolderCPF()+".pdf"));
           document.open();
           document.add(new Paragraph("ORDEM DE SERVIÇO Nº"+so.getId()));
           document.add(new Paragraph("CPF: "+so.getConcessionHolderCPF()));
           document.add(new Paragraph("DATA DE EMISSÃO: "+so.getCreationDate()));
           document.add(new Paragraph("VENCIMENTO: "+so.getExpiringDate()));
           document.add(new Paragraph(""));
           document.add(new Paragraph(""));
           document.add(new Paragraph("SERVIÇOS:"));
           for (Service s : services) {
               document.add(new Paragraph("     "+s.toString()));
           }
           document.add(new Paragraph(""));
           document.add(new Paragraph("TOTAL: "+NumberFormat.getCurrencyInstance().format(so.getTotal())));
           document.close();
           writer.close();
        } catch (DocumentException e)
        {
           e.printStackTrace();
        } catch (FileNotFoundException e)
        {
           e.printStackTrace();
        }
    }
}
