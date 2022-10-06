package java_controler;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.lucene.queryparser.classic.ParseException;


import model.WriteToFile;
import model.artifacts.TypeEntity;





public class test implements Serializable{
	

	/**
	 * 
	 * @author farshad.toosi
	 * This is a test class that use JHotDraw project as a subject system.
	 */
	
	
	public static void main(String[] args) throws IOException {
		
		
		//String addr = "/Users/farshad.toosi/eclipse-workspace/SCAIT1/benford";
		String addr = "/Users/farshad.toosi/eclipse-workspace/test/src/main/java";
		//String addr = "/Users/farshad.toosi/eclipse-workspace/SCAIT/src/main/resources/jhotdraw/JHotDraw7/src/main/java";
		
		//String addr = "/Users/farshad.toosi/eclipse-workspace/SCAIT1/src/main/resources/test/src/main/java";
		Java jhot = new Java(addr);
		
		
		jhot.writeObjectToFile(jhot, "dump.txt");
	}
	
	
}
