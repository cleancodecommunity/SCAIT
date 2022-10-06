package model.artifacts;

import java.io.Serializable;
import java.util.ArrayList;

import model.Data;

public class PackageEntity implements Serializable{
	
	private String name;
	private int LinesOfCode=-1;
	
	private boolean isnested;
	private Data project;
	private ArrayList<ClassEntity> clazes = new ArrayList<ClassEntity>();
	
	private static final long serialVersionUID = 22L;
	
	public PackageEntity() {
		
	}

	public PackageEntity(String name, Data project) {
		this.name = name;
		this.project = project;
		this.project.addPackage(name, this);
	}
	
	
	public Data getProject() {
		// TODO Auto-generated method stub
		return this.project;
	}


	
	public String getName() {
		return this.name;
	}





	
	public void setProject(Data project) {
		this.project = project;
		
	}


	
	public void setName(String Name) {
		this.name = Name;
		
	}


	

	public ArrayList<ClassEntity> getContainedClasses() {
		return this.clazes;
	}
	
	public void setContainedClasses(ArrayList<ClassEntity> clazes) {
		this.clazes = clazes;
	}
	
	public void addNewClass(ClassEntity claz) {
		this.clazes.add(claz);
	}	
	
	public boolean isNested() {
		return this.isnested;
	}
	public void setIsNested(boolean nested) {
		this.isnested = nested;
	}
	
	/*
	 * public void setChildPackage(PackageEntity pckg) { this.childPackage = pckg; }
	 * public void setParentPackage(PackageEntity pckg) { this.parentPackage = pckg;
	 * }
	 * 
	 * public PackageEntity getChildPackage() { return this.childPackage; } public
	 * PackageEntity getParentPackage() { return this.parentPackage; }
	 */
}
