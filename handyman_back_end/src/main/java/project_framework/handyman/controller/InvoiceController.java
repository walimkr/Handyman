package project_framework.handyman.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import project_framework.handyman.Services.Interfaces.InvoiceService;
import project_framework.handyman.models.Contract;
import project_framework.handyman.models.Invoice;

import java.io.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class InvoiceController {
    private InvoiceService invoiceService;
@Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoice")
    public Invoice getInvoice(@RequestParam int id){
        return invoiceService.findById(id);
    }

    byte[] createPdf(@RequestParam int id) throws IOException {
        Invoice invoice = getInvoice(id);
        String fileName = invoice.getUrl_pdf_invoice();
        System.out.println("*********************************************************************");
        System.out.println(fileName);
        // System.out.println(contract.getProject_id().getAddress());

        /* first, get and initialize an engine */
        VelocityEngine ve = new VelocityEngine();

        /* next, get the Template */
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class",
                ClasspathResourceLoader.class.getName());
        ve.init();
        Template t = ve.getTemplate("templates/invoice.vm");
        /* create a context and add data */
        VelocityContext context = new VelocityContext();
        context.put("invoice", invoice);
        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        /* show the World */
        System.out.println(writer.toString());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        baos = generatePdf(writer.toString(), fileName);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + fileName.replace(" ", "_"));
        header.setContentLength(baos.toByteArray().length);

        return baos.toByteArray();

    }

    public ByteArrayOutputStream generatePdf(String html, String fileName) {
System.out.println(fileName);
        String pdfFilePath = "C:\\Users\\hp\\Pictures";
        PdfWriter pdfWriter = null;

        // create a new document
        Document document = new Document();
        try {

            document = new Document();
            // document header attributes
            document.addAuthor("Lina Dridi");
            document.addCreationDate();
            document.addProducer();
            document.addCreator("lina.dridi.97");
            document.addTitle("HTML to PDF using itext");
            document.setPageSize(PageSize.LETTER);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);

            // open document
            document.open();

            XMLWorkerHelper xmlWorkerHelper = XMLWorkerHelper.getInstance();
            xmlWorkerHelper.getDefaultCssResolver(true);
            xmlWorkerHelper.parseXHtml(pdfWriter, document, new StringReader(
                    html));
            // close the document
            document.close();
            System.out.println("PDF generated successfully");
            FileOutputStream fos = new FileOutputStream("C:\\Users\\User\\Desktop\\Handyman backup\\Handyman\\handyman_back_end\\src\\main\\resources\\" + fileName);
            fos.write(baos.toByteArray());
            fos.close();
            return baos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
    //**************************************************
}
