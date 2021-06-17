package Sample;


	import java.io.File;

	public class FileDemo {
	   public static void main(String[] args) {      
	      File f = null;
	      int v;
	      boolean bool = false;
	      
	      try {
	         // create new file
	         f = new File("D:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\Deduplicationn\\Files\\Employee.class");
	         
	         // returns hash code for this abstract pathname
	         String file=f.toString();
	         System.out.println("--------file----------"+file);
	         v = f.hashCode();
	         MD5 m=new MD5();
	         String data=m.send("bairavi", file);
	         System.out.println("-----------data---------"+data);
	         // true if the file path exists
	         bool = f.exists();
	         
	         // if file exists
	         if(bool) {
	         
	            // prints
	            System.out.print("The hash code for this abstract pathname: "+v);
	         }
	         
	      } catch(Exception e) {
	         // if any error occurs
	         e.printStackTrace();
	      }
	   }
	}


