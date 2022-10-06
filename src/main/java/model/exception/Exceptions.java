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
public class Exceptions implements Serializable, Entity{
	
	private ClassEntity ClassContainer;
	private MethodEntity MethodContainer;
	private ArrayList<Cach> Catches = new ArrayList<Cach>();
	private Try tri;
	
	
	private String index;
	
	private Data project;
	
	private boolean hasFinally;
	
	private int FinalyLOC;
	
	
	private static final long serialVersionUID = 23L;
	
	public Exceptions() {
		
	}
	
	public Exceptions(String ind, Data project, MethodEntity MethodContainer) {
		this.MethodContainer = MethodContainer;
		this.ClassContainer = this.MethodContainer.getClassContainer();
		
		this.project = project;
		this.index = ind;
		this.MethodContainer.addExceptions(this);
		this.project.addExceptions(this);
	}
	
	
	@Override
	public String getUniqueName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Data getProject() {
		// TODO Auto-generated method stub
		return this.project;
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
		
	}

	@Override
	public void setProject(Data project) {
		this.project = project;
		
	}

	@Override
	public void setName(String Name) {
		
	}

	

	public PackageEntity getPackage() {
		return this.ClassContainer.getPackge();
	}
	
	public void setFinaly(boolean finaly) {
		this.hasFinally = finaly;
	}
	public void setElse(boolean elcse) {
		this.hasFinally = elcse;
	}
	
	public boolean HasFinaly() {
		return this.hasFinally;
	}
	
	
	public void setLOCFinaly(int line) {
		this.FinalyLOC = line;
	}
	
	public int getLOCFinaly() {
		return this.FinalyLOC;
	}
	
	public void addCatch(Cach cach) {
		this.Catches.add(cach);
	}
	
	public ArrayList<Cach> getCatches() {
		return this.Catches;
	}
	public ClassEntity getClassContainer() {
		return this.ClassContainer;
	}
	
	public MethodEntity getMethodContainer() {
		return this.MethodContainer;
	}
	
	public Try geTry() {
		return this.tri;
	}
	
	public void setTry(Try tri) {
		this.tri = tri;
	}
	
	public String geIndex() {
		return this.index;
	}
	
	public void setIndex(String ind) {
		this.index = ind;
	}
	
}
