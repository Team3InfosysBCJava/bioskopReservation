//package com.teamc.bioskop.Service;
//
//import org.thymeleaf.*;
//import org.thymeleaf.context.Context;
//import org.thymeleaf.templatemode.TemplateMode;
//import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//
//public class InvoiceService {
//
//    private String parseTemplate(){
//        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode(TemplateMode.HTML);
//
//        TemplateEngine templateEngine = new TemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver);
//
//        Context context = new Context();
//        context.setVariable("to", "Baeldung");
//        return templateEngine.process("thymeleaf_template", context);
//
//    }
//
//    public void generateInvoice(String html){
//        String outputFolder = System.getProperty("user.home") + File.separator + "thymeleaf.pdf";
//
//        OutputStream outputStream = new FileOutputStream(outputFolder);
//
//        ITextRenderer renderer = new ITextRenderer();
//
//        renderer.layout();
//        renderer.createPDF(outputStream);
//
//        outputStream.close();
//    }
//}
