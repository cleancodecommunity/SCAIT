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
		  File file = new File("/Users/farshad.toosi/eclipse-workspace/SCAIT1/src/main/java/python_control/ObjectPython.txt");
		  Scanner in = new Scanner(file);
		  
		  while (in.hasNextLine()) {
			  jsonString += in.nextLine();
		  }
		  in.close();
		  
		  Data pyProject = new Data();
		  
		  
		  JSONObject object = new JSONObject (jsonString);

		  ClassCreation classes = new ClassCreation(pyProject, object);
		  
		  
		  
		  
		  pyProject.getCalzes().forEach((a, b) -> {
			 System.out.println(b.getName());
			 System.out.println(b.getUniqueName());
			 System.out.println("--------");
			 
			 b.getAllOutgoingClasses().forEach(cl -> {
				 System.out.println(b.getName()+"\t"+cl.getName());
			 });
			 
			 
			 b.getAllMethods().forEach(met -> {
				 System.out.println("\t"+met.getName());
				 System.out.println("\t"+met.getUniqueName());
				 System.out.println("\t"+met.getSigniture());
				 System.out.println("\t"+met.getLineNum());
				 System.out.println("\t"+met.isPrivate());
				 System.out.println("\t"+met.isPublic());
				 System.out.println("\t"+met.isStatic());
				 
				 
				 
				 met.getAllOutgoingMethod().forEach(m -> {
					 System.out.println("\t\t"+m.getName());
					 System.out.println("\t\t"+m.getUniqueName());
					 System.out.println("\t\t"+m.getSigniture());
				 });
				 
			 });
		  });
		  
		 
	  }
	 
	  
	  
	}