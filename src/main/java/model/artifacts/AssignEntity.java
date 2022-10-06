package model.artifacts;

import java.io.Serializable;

import model.Data;
/**
 * 
 * @author farshad ghassemi toosi
 * 
 * 
 */
public class AssignEntity implements Entity , Serializable{

	private String name;
	private String uniqueName;
	private MethodEntity methodContainer;
	
	private VariableEntity left;
	private Entity right;
	private Data project;
	
	public AssignEntity() {
		
	}
	
	
	public AssignEntity(VariableEntity left, Entity right, Data project) {
		this.name = left.getName() +"="+right.getName();
		this.left = left;
		this.right = right;
		this.project = project;
		this.uniqueName = this.name+" "+this.methodContainer.getUniqueName();
		this.project.addAssign(this);
	}
	
	
	@Override
	public String getUniqueName() {
		// TODO Auto-generated method stub
		return this.uniqueName;
	}

	@Override
	public Data getProject() {
		// TODO Auto-generated method stub
		return this.project;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
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
		this.name = Name;
	}
	
	public VariableEntity getLeft() {
		return this.left;
	}

	public Entity getRight() {
		return this.right;
	}
	
	public MethodEntity getMethodContainer() {
		return this.methodContainer;
	}
	
	public void setMethodContainer(MethodEntity method) {
		this.methodContainer = method;
	}
	
	public PackageEntity getPackageContainer() {
		return this.methodContainer.getPackage();
	}
	
}
