package model.exception;

import java.io.Serializable;

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
public class Cach implements Serializable, Entity{
	
	private ClassEntity ClassContainer;
	private MethodEntity MethodContainer;
	private Try Tri;
	private Data project;
	
	private String Return;
	private int LOC;
	private Exceptions exception;
	private boolean hasBody;
	
	private static final long serialVersionUID = 23L;
	
	public Cach() {
		
	}
	
	public Cach(Data project, MethodEntity MethodContainer, Try tri) {
		this.project = project;
		this.MethodContainer = MethodContainer;
		this.ClassContainer = this.MethodContainer.getClassContainer();
		this.Tri = tri;
		
		
	}
	
	
	public void setLOC(int LOC) {
		this.LOC = LOC;
	}
	public int getLOC() {
		return this.LOC;
	}

	@Override
	public String getUniqueName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Data getProject() {
		return this.project;
	}

	@Override
	public String getName() {
		return "";
	}

	
	public PackageEntity getPackge() {
		return this.ClassContainer.getPackge();
	}

	@Override
	public void setUniqueName(String UniqueName) {
		
	}

	@Override
	public void setProject(Data project) {
		this.project = project;
		
	}

	@Override
	public void setName(String Name) {
		
	}

	

	
	
	public Try getTry() {
		return this.Tri;
	}
	
	public void setReturn(String Return) {
		this.Return = Return;
	}
	
	public String getReturn() {
		return this.Return;
	}
	public void setException(Exceptions exception) {
		this.exception = exception;
	}
	public Exceptions getException() {
		return this.exception;
	}
	
	public void setBody(boolean body) {
		this.hasBody = body;
	}
	
	public boolean hasBody() {
		return this.hasBody;
	}
	
}
