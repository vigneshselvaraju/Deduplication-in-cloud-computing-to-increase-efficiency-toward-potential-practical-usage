package Sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class newser  {

	
		  public static void main(String[] args) throws IOException {
			
		
			  File file = new File("D://Program Files//Apache Software Foundation//Tomcat 7.0//webapps//Deduplicationn//Files//guide.txt"); 
		     
		         double bytes = file.length();
		         double kilobytes = (bytes / 1024);
		         double megabytes = (kilobytes / 1024);
		         double gigabytes = (megabytes / 1024);
		         double terabytes = (gigabytes / 1024);
		         double petabytes = (terabytes / 1024);
		         double exabytes = (petabytes / 1024);
		         double zettabytes = (exabytes / 1024);
		         double yottabytes = (zettabytes / 1024);
		         
		         System.out.println("bytes : " + bytes);
		         System.out.println("kilobytes : " + kilobytes);
		         System.out.println("megabytes : " + megabytes);
		         System.out.println("gigabytes : " + gigabytes);
		         System.out.println("terabytes : " + terabytes);
		         System.out.println("petabytes : " + petabytes);
		         System.out.println("exabytes : " + exabytes);
		         System.out.println("zettabytes : " + zettabytes);
		         System.out.println("yottabytes : " + yottabytes);
		         FileInputStream fileStream = new FileInputStream(file); 
		         InputStreamReader input = new InputStreamReader(fileStream); 
		         BufferedReader reader = new BufferedReader(input); 
		           
		         String line; 
		           
		         // Initializing counters 
		         int countWord = 0; 
		         int sentenceCount = 0; 
		         int characterCount = 0; 
		         int paragraphCount = 1; 
		         int whitespaceCount = 0; 
		           
		         // Reading line by line from the  
		         // file until a null is returned 
		         while((line = reader.readLine()) != null) 
		         { 
		             if(line.equals("")) 
		             { 
		                 paragraphCount++; 
		             } 
		             if(!(line.equals(""))) 
		             { 
		                   
		                 characterCount += line.length(); 
		                   
		                 // \\s+ is the space delimiter in java 
		                 String[] wordList = line.split("\\s+"); 
		                   
		                 countWord += wordList.length; 
		                 whitespaceCount += countWord -1; 
		                   
		                 // [!?.:]+ is the sentence delimiter in java 
		                 String[] sentenceList = line.split("[!?.:]+"); 
		                   
		                 sentenceCount += sentenceList.length; 
		             } 
		         } 
		           
		         System.out.println("Total word count = " + countWord); 
		         System.out.println("Total number of sentences = " + sentenceCount); 
		         System.out.println("Total number of characters = " + characterCount); 
		         System.out.println("Number of paragraphs = " + paragraphCount); 
		         System.out.println("Total number of whitespaces = " + whitespaceCount); 
		    
		           Scanner sc = new Scanner(file); 
		         
		           while (sc.hasNextLine()) {
		             //System.out.println("--------*********--------"+sc.nextLine());
		           String lineup=sc.nextLine();
				 
		           System.out.println("--------*********--------"+lineup);
		           String[] strArray = line.split(".");
		           System.out.println(Arrays.toString(strArray));
		           
		           }
		           
		           
		           
		     
		   }
		}


