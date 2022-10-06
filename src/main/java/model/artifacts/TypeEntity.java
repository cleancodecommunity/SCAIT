package model.artifacts;

import java.io.Serializable;

import model.Data;
/**
 * 
 * @author farshad ghassemi toosi
 * 
 * 
 */
public class TypeEntity implements  Entity , Serializable{

	
	
	
	private Object type;
	
	private boolean isFromProject;
	private String name;
	
	public TypeEntity() {
		
	}

	 public TypeEntity(Object type,String name, boolean isFromProject) {
		 this.type = type;
		 this.name = name;
		 this.isFromProject = isFromProject;
		 
	 }
	
	
	 
	 public void setType(Object type) {
		 this.type = type;
	 }
	
 
	 public Object getType() {
		 return this.type;
	 }

	@Override
	public String getUniqueName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Data getProject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public void setUniqueName(String UniqueName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProject(Data project) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setName(String Name) {
		this.name = Name;
		
	}
	
	

	
	public boolean isFromProject() {
		return this.isFromProject;
	}
	 
}
