package model.artifacts;

import java.io.Serializable;

import model.Data;
/**
 * 
 * @author farshad ghassemi toosi
 * 
 * 
 */
public class ParameterEntity implements Entity , Serializable{
	private static final long serialVersionUID = 25L;
	private boolean isClass;
	private boolean isPrimitive;
	private boolean isPartOfSystem;
	private boolean isMethodParameter;
	
	private String uniqueName;
	private String Name;
	private MethodEntity methodContainer;
	private ConstructorEntity constructorEntity;
	
	private ClassEntity classRefrence;
	private Object objectRefrence;
	
	private Data project;
	
	private String type;
	
	public ParameterEntity() {
		
	}
	
	
	public ParameterEntity(String name, MethodEntity method, boolean isMethodParameter, Data project) {
		this.Name = name;
		this.isMethodParameter = isMethodParameter;
		this.methodContainer = method;
		this.methodContainer.addParameterMethod(this);
		this.uniqueName = name+" "+method.getUniqueName();
		this.project = project;
	}
	
	public ParameterEntity(String name, ConstructorEntity cons, boolean isMethodParameter, Data project) {
		this.Name = name;
		this.isMethodParameter = isMethodParameter;
		this.constructorEntity = cons;
		this.constructorEntity.addParameterMethod(this);
		this.uniqueName = name+" "+cons.getUniqueName();
		this.project = project;
	}
	
	
	
	@Override
	public String getUniqueName() {
		
		return this.uniqueName;
	}

	@Override
	public Data getProject() {
		return this.project;
	}

	@Override
	public String getName() {
		return this.Name;
	}

	
	public PackageEntity getPackgeName() {
		// TODO Auto-generated method stub
		return this.methodContainer.getPackgeName();
	}

	@Override
	public void setUniqueName(String UniqueName) {
		this.uniqueName = UniqueName;
		
	}

	@Override
	public void setProject(Data project) {
		this.project = project;
	}

	@Override
	public void setName(String Name) {
		this.Name = Name;
		
	}

	public boolean isMethodParameter() {
		return this.isMethodParameter;
	}
	
	public boolean isPartOfProject() {
		return this.isPartOfSystem;
	}
	
	public void setPartofProject(boolean part) {
		this.isPartOfSystem = part;
	}

	public boolean isPrimitive() {
		return this.isPrimitive;
	}
	
	public void setPrimitive(boolean prm) {
		this.isPrimitive = prm;
	}
	
	public Object getClassRefrence() {
		
		if(this.classRefrence instanceof ClassEntity)
			return this.classRefrence ;
		else
			return this.objectRefrence;
	}
	
	public void setClassRefrence(Object claz) {
		if(claz instanceof ClassEntity)
			this.classRefrence = (ClassEntity) claz;
		else
			this.objectRefrence = claz;
	}
	
	public String getType() {
		
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
