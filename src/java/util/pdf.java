package util;

import com.pdfcrowd.*;
import java.io.*;

public class pdf {

    public static String htmlToPdf(String html) {
        String fileName = "";
        try {
            FileOutputStream fileStream;
            System.setProperty("http.proxyHost", "192.168.5.100");
            System.setProperty("http.proxyPort", "80");

            // create an API client instance
            Client client = new Client("ggvishnu", "0f526ad01bf20de68f0945e9703b1cb2");

            // convert a web page and save the PDF to a file
            Long n = System.currentTimeMillis();
            fileName = n.toString();
            fileStream = new FileOutputStream("e:/pdfs/" + fileName + ".pdf");
            //client.convertURI("http://google.com", fileStream);
            //fileStream.close();

            // convert an HTML string and store the PDF into a byte array
            ByteArrayOutputStream memStream = new ByteArrayOutputStream();
            //String html = html;
            client.convertHtml(html, memStream);
            memStream.writeTo(fileStream);

            // convert an HTML file
            //fileStream = new FileOutputStream("file.pdf");
            //client.convertFile("/path/to/local/file.html", fileStream);
            fileStream.close();

            // retrieve the number of tokens in your account
            Integer ntokens = client.numTokens();
            System.out.println(ntokens);

        } catch (PdfcrowdError why) {
            System.err.println(why.getMessage());
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
        return fileName + ".pdf";
    }
}
