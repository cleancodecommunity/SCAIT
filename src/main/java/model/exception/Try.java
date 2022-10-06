package model.exception;

import java.io.Serializable;
import java.util.ArrayList;

import model.Data;
import model.artifacts.ClassEntity;
import model.artifacts.Entity;
import model.artifacts.MethodEntity;
import model.artifacts.PackageEntity;
/**
 * 
 * @author farshad ghassemi toosi
 * 
 * 
 */
public class Try implements Serializable, Entity{
	
	private ClassEntity ClassContainer;
	private MethodEntity MethodContainer;
	
	
	private String packageName;
	private int LOC;
	private Exceptions exception;
	private Data project;
	private ArrayList<MethodEntity> IncomingMethods = new ArrayList<MethodEntity>();
	private static final long serialVersionUID = 23L;
	
	public Try() {
		
	}
	
	public Try ( MethodEntity MethodContainer, Exceptions exception) {
		this.MethodContainer = MethodContainer;
		this.ClassContainer = this.MethodContainer.getClassContainer();
		
		this.exception = exception;
		this.project = this.exception.getProject();
	}
	
	
	
	
	public void setNumLine(int line) {
		this.LOC = line;
	}
	
	public int getNumLine() {
		return this.LOC;
	}
	
	@Override
	public String getUniqueName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Data getProject() {
		// TODO Auto-generated method stub
		return this.exception.getProject();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "";
	}

	
	public PackageEntity getPackge() {
		// TODO Auto-generated method stub
		return this.MethodContainer.getPackage();
	}

	@Override
	public void setUniqueName(String UniqueName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProject(Data project) {
		this.project = project;
	}

	@Override
	public void setName(String Name) {
		// TODO Auto-generated method stub
		
	}

	

	
	public ClassEntity getClassContainer() {
		return this.ClassContainer;
	}
	
	public MethodEntity getMethodContainer() {
		return this.MethodContainer;
	}
	
	
	public Exceptions getException() {
		return this.exception;
	}
	
	public void addOutgoingMethod(MethodEntity Callee) {
		IncomingMethods.add(Callee);
	}
	
	public ArrayList<MethodEntity> getAllIncommingMethod() {
		return IncomingMethods;
	}
}
