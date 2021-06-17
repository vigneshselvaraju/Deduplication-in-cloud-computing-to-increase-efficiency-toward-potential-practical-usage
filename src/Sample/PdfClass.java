package Sample;

	import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
	public class PdfClass {

	   public static void main(String args[]) throws IOException {

	      //Loading an existing document
	      File file = new File("D://Program Files//Apache Software Foundation//Tomcat 7.0//webapps//Deduplicationn//Files//CReam A Smart Contract Enabled.pdf");
	      PDDocument document = PDDocument.load(file);

	      //Instantiate PDFTextStripper class
	      PDFTextStripper pdfStripper = new PDFTextStripper();

	      //Retrieving text from PDF document
	      String text = pdfStripper.getText(document);
	      System.out.println(text);

	      //Closing the document
	      document.close();

	   }
	}

