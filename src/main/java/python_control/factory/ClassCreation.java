package python_control.factory;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import model.Data;
import model.artifacts.ClassEntity;
import model.artifacts.MethodEntity;

public class ClassCreation implements Serializable {
	
	
	private Data project;
	public HashMap<String, JSONObject> allMethods = new HashMap<String, JSONObject>();
	public HashMap<String, JSONObject> allClasses = new HashMap<String, JSONObject>();
	 
	public ClassCreation( Data project, Object object) {
		this.project = project;
		try {
			this.objectTravers(object);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			addClasses();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private void addClasses() throws JSONException {
		
		this.allClasses.forEach((a, claz) -> {
			String name = "";
			String fullName = "";
			String kode = "";
			
			JSONArray methods = null;
			for(int i=0 ;i <  claz.names().length(); i++) {
				  //System.out.println(this.claz.names().get(i)+"\t"+ this.claz.get(this.claz.names().get(i).toString()) );
				  try {
					  if(claz.names().get(i).toString().equals("name")) {
						  name = claz.get(claz.names().get(i).toString()).toString();
					  }
					  if(claz.names().get(i).toString().equals("?Code")) {
						  kode = claz.get(claz.names().get(i).toString()).toString();
					  }
					  if(claz.names().get(i).toString().equals("fullName")) {
						  fullName = claz.get(claz.names().get(i).toString()).toString();
					  }
				  } catch (JSONException e) {
						e.printStackTrace();
				   }
			}
			ClassEntity newClass = new ClassEntity(this.project, kode+"."+fullName,  name, kode);
			try {
				addMethod(claz, newClass);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		this.project.getMethods().forEach((a, b) -> {
			
			try {
				addMethodCallsMethod(this.allMethods.get(b.getCode()), b);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		});
		
		this.project.getCalzes().forEach((a, b) -> {
			
			try {
				
				addMethodCallsClass(this.allClasses.get(b.getCode()), b);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		});
		
	}
	

	

	public void addMethod(JSONObject clazObj, ClassEntity claz) throws JSONException {
		String name = "";
		String code = "";
		String signiture = "";
		int lineNum = -1;
		
		
		JSONArray containedMethods = (JSONArray) clazObj.get("containedMethods");
		
		for(int j=0; j<containedMethods.length(); j++) {
			String kode =  ((JSONObject) containedMethods.get(j)).get("?Code").toString() ;
			JSONObject meth =  (JSONObject) this.allMethods.get(kode);
		
			for(int i=0 ;i <  meth.names().length(); i++) {
				  //System.out.println(this.claz.names().get(i)+"\t"+ this.claz.get(this.claz.names().get(i).toString()) );
				  if(meth.names().get(i).toString().equals("name")) {
					  name = meth.get(meth.names().get(i).toString()).toString();
				  }
				  if(meth.names().get(i).toString().equals("?Code")) {
					  code = meth.get(meth.names().get(i).toString()).toString();
				  }
				  if(meth.names().get(i).toString().equals("signiture")) {
					  signiture = meth.get(meth.names().get(i).toString()).toString();
				  }
				  if(meth.names().get(i).toString().equals("lineNum")) {
					  lineNum = Integer.parseInt(meth.get(meth.names().get(i).toString()).toString());
				  }
			}
			MethodEntity newMethdod = new MethodEntity(code+"."+claz.getUniqueName()+"."+name, name, signiture, this.project, claz);
			
			if(name.startsWith("__"))
				newMethdod.setPrivate(true);
			else
				newMethdod.setPublic(true);
			if(dicToHashMap(signiture).size()==0) 
				newMethdod.setStatic(true);
			newMethdod.setCode(code);
			newMethdod.setLineNum(lineNum);
		}
		
		
		
	}
	

	public void addMethodCallsMethod(JSONObject methObject, MethodEntity method) throws JSONException {
		
		JSONArray outMethods = (JSONArray) methObject.get("outgoingMethods");
		
		this.project.getMethods().forEach((a, b) -> {
			
			if(method.getCode().equals(b.getCode())) {
				String kode;
				
				for(int i=0; i<outMethods.length(); i++) {
					try {
						kode = ((JSONObject) outMethods.get(i)).get("?Code").toString();
						
						for(MethodEntity meth: this.project.getMethods().values()) {
							if(kode.equals( meth.getCode())) {
								method.addOutgoingMethod(meth);
								meth.addIncommingMethod(method);
							}
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
			}
		});
	}
	
	
	public void addMethodCallsClass(JSONObject methObject, ClassEntity Claz) throws JSONException {
		
		if(methObject.has("outgoingMethods")) {
			JSONArray outMethods = (JSONArray) methObject.get("outgoingMethods");
															   
			
			this.project.getCalzes().forEach((a, b) -> {
				
				if(Claz.getCode().equals(b.getCode())) {
					String kode;
					//System.out.println(Claz.getName()+"\t");
					for(int i=0; i<outMethods.length(); i++) {
						try {
							
							kode = ((JSONObject) outMethods.get(i)).get("?Code").toString();
							
							for(MethodEntity meth: this.project.getMethods().values()) {
								
								if(kode.equals( meth.getCode())) {
									//if(Claz.getName().equals("?Root"))
									//	System.out.println(Claz.getName()+"\t"+((JSONObject)outMethods.get(i)).get("?Code")+"\t");
									Claz.addOutgoingMethod(meth);
									meth.addIncommingClass(Claz);
								}
							}
							
						} catch (JSONException e) {
							
							e.printStackTrace();
						}
						
					}
				}
			});
		}
	}
	
	  
	  private  void objectTravers(Object object) throws JSONException {
		  
		  if(object instanceof JSONObject) {
			  JSONObject newObj = ((JSONObject) object);
			  Iterator<String> keys = newObj.keys();
			  while(keys.hasNext()) {
				  String key = keys.next().toString();
				     if(newObj.get(key) instanceof JSONObject) {
				    	 objectTravers(newObj.get(key));
				     }
				     if(newObj.get(key) instanceof JSONArray) {
				    	 objectTravers(newObj.get(key));
				     }
				     if(key.toString().equals("?Name") && newObj.get(key).toString().contains("classEntity") && newObj.get(key).toString().contains("False")) {
				    	 allClasses.put(newObj.getString("?Code"), newObj);
				     }
				     if(key.toString().equals("?Name") && newObj.get(key).toString().contains("methodEntity") && newObj.get(key).toString().contains("False")) {
				    	 allMethods.put(newObj.getString("?Code"), newObj);
				     }
				    
			  }
		  }
		  if(object instanceof JSONArray) {
			  JSONArray newArray = ((JSONArray) object);
			  for(int j=0; j<newArray.length();j++) {
				  if( newArray.get(j) instanceof JSONObject) {
					  objectTravers( newArray.get(j));
				  }
				  else {
					  //System.out.println(newArray.get(j));
					  
				  }
			  }
		  }
	  }
	  
	 
	
	public Data getProject() {
		return this.project;
	}
	
	private HashMap<String, String> dicToHashMap(String dic) {
		 HashMap<String, String> signiture = new  HashMap<String, String>();
		 String dict = dic.substring(1, dic.length()-1);
		 
		 String [] args = dict.split(",");
		 for(int i=0; i< args.length;i++) {
			 String cooked = args[i].replace("\"","");
			 String [] keys = cooked.split(":");
 			 if(keys.length == 2)
 				 signiture.put(keys[0],keys[1]);
		 }
		 
		return signiture;
		
		 
	}
	
}
