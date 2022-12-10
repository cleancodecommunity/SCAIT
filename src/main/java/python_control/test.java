package python_control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.python.core.AstList;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import model.Data;
import python_control.factory.ClassCreation;

public class test {
	  public static void main(String[] args) throws IOException, JSONException {
	   	
	        //PySystemState sys = Py.getSystemState();
	        //sys.path.append(new PyString("/Users/farshad.toosi/eclipse-workspace/SCAIT1/src/main/java/python_control"));
	    	//PythonInterpreter python = new PythonInterpreter(null, sys);
	        //python.execfile("/Users/farshad.toosi/eclipse-workspace/SCAIT1/src/main/java/python_control/main.py");
		    
	    	//Process process = Runtime.getRuntime().exec("python /Users/farshad.toosi/eclipse-workspace/SCAIT1/src/main/java/python_control/main.py");
	    	parseJson();
	    	
	    }
	  
	  
	  public static void parseJson() throws FileNotFoundException, JSONException {
		  
		  
		  String jsonString = "";
		  File file = new File("/Users/farshad.toosi/eclipse-workspace/SCAIT/src/main/java/python_control/ObjectPython.txt");
		  Scanner in = new Scanner(file);
		  
		  while (in.hasNextLine()) {
			  jsonString += in.nextLine();
		  }
		  in.close();
		  
		  Data pyProject = new Data();
		  
		  
		  JSONObject object = new JSONObject (jsonString);

		  ClassCreation classes = new ClassCreation(pyProject, object);
		  
		  
		  
		  
		  pyProject.getCalzes().forEach((a, b) -> {
			  System.out.println("--------");
				 
			 System.out.println("Class Name:\t"+b.getName());
			 
			 

			


			

			 
			
			 b.getAllOutgoingMethods().forEach(mt -> {
				 System.out.println("Outgoing Method Name:\t"+mt.getName());
				 System.out.println("Outgoing Method Signiture:\t"+mt.getSigniture());
				 System.out.println("Is Outgoing Method Private:\t"+mt.isPrivate());
				 System.out.println("Is Outgoing Method Public:\t"+mt.isPublic());
				 System.out.println("Is Outgoing Method Static:\t"+mt.isStatic());
				 System.out.println("--");
			 });
			 
			 
			 b.getAllMethods().forEach(met -> {
				 System.out.println("\tClass Method Name:\t"+met.getName());
				 
				 System.out.println("\tClass Method Signiture:\t"+met.getSigniture());
				 System.out.println("\tIs Class Method Private:\t"+met.isPrivate());
				 System.out.println("\tIs Class Method Public:\t"+met.isPublic());
				 System.out.println("\tIs Class Method Static:\t"+met.isStatic());
				 
				 
				 met.getAllIncommingMethod().forEach(inM -> {
					 System.out.println("\t\tIncoming Method Name:\t"+inM.getName());
					 System.out.println("\t\tIncoming Method Signiture:\t"+inM.getSigniture());
					 System.out.println("\t\tIs Incoming Method Private:\t"+inM.isPrivate());
					 System.out.println("\t\tIs Incoming Method Public:\t"+inM.isPublic());
					 System.out.println("\t\tIs Incoming Method Static:\t"+inM.isStatic());
					 System.out.println("\t\t--------");
				 });
				 
				
				 met.getAllOutgoingMethod().forEach(outM -> {
					 System.out.println("\t\tOutgoing Method Name:\t"+outM.getName());
					 System.out.println("\t\tOutgoing Method Signiture:\t"+outM.getSigniture());
					 System.out.println("\t\tIs Outgoing Method Private:\t"+outM.isPrivate());
					 System.out.println("\t\tIs Outgoing Method Public:\t"+outM.isPublic());
					 System.out.println("\t\tIs Outgoing Method Static:\t"+outM.isStatic());
					 System.out.println("\t\t--------");
				 });
				 
				 
				 System.out.println("\t--------");
			 });
			 System.out.println("--------");
			 
		  });
		  
		 
	  }
	 
	  
	  
	}