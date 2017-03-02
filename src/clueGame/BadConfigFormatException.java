package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception{
	public BadConfigFormatException() throws FileNotFoundException{
		super("Bad config format");
		
		PrintWriter out = new PrintWriter("errorLog.txt");
		out.print("Bad config format");
	}
	
	public BadConfigFormatException(String badFile) throws FileNotFoundException{
		super("Bad config file: " + badFile);
		
		//opens an error log and appends to it rather than overwriting what exists
		PrintWriter out = new PrintWriter(new FileOutputStream(new File("errorLog.txt"),true));;
		out.println("Bad config file: " + badFile);
		out.close();
	}
}
