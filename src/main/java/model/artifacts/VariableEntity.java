package model.artifacts;

import java.io.Serializable;

import com.github.javaparser.ast.body.VariableDeclarator;

import model.Data;
/**
 * 
 * @author farshad ghassemi toosi
 * 
 * 
 */
public class VariableEntity implements Serializable, Entity {
	private static final long serialVersionUID = 27L;
	private boolean isClassField;
	
	private String name;
	private String UniqueName;
	private boolean isStatic;
	private boolean isPublic;
	private boolean isPrivate;
	private boolean isProtected;
	private boolean isFinal;
	
	private ClassEntity ClassContainer;
	private MethodEntity methodContainer;
	
	private String right;
	private MethodEntity [] IncomingMethods;
	private ClassEntity [] IncomingClasses;
	
	private Entity VarType;
	
	private Data project;
	private String packageName;

	private transient VariableDeclarator variable;
	
	public VariableEntity() {
		
	}
	
	public VariableEntity(String name, boolean isClassField, String UniqueName, Data project) { 
		this.name = name;
		this.isClassField = isClassField;
		this.UniqueName = UniqueName;
		this.project = project;
		this.project.addVariables(UniqueName, this);
	}
	
	
	public VariableEntity(String name, boolean isClassField, String UniqueName, Data project, VariableDeclarator variable) { // this is only for Java
		this.name = name;
		this.isClassField = isClassField;
		this.UniqueName = UniqueName;
		this.project = project;
		this.variable = variable;
		this.project.addVariables(UniqueName, this);
	}
	
	public void setClassContainer(ClassEntity claz) {
		this.ClassContainer = claz;
	}
	
	public ClassEntity getClassContainer() {
		return this.ClassContainer;
	}
	
	public void setMethodContainer(MethodEntity mth) {
		this.methodContainer = mth;
	}
	
	public MethodEntity getMethodContainer() {
		return this.methodContainer;
	}
	
	
	public void setContainerType(ClassEntity claz) {
		this.ClassContainer = claz;
	}
	
	public boolean hasClassContainer() {
		return this.isClassField;
	}
	
	
	@Override
	public String getUniqueName() {
		// TODO Auto-generated method stub
		return this.UniqueName;
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
	
	public PackageEntity getPackge() {
		// TODO Auto-generated method stub
		return this.ClassContainer.getPackge();
	}
	@Override
	public void setUniqueName(String UniqueName) {
		this.UniqueName = UniqueName;
		
	}
	@Override
	public void setProject(Data project) {
		this.project = project;
		
	}
	@Override
	public void setName(String Name) {
		this.name = Name;
		
	}
	

	
	
	
	
	public void setPublic(boolean publik) {
		this.isPublic = publik;
	}
	public void setPrivate(boolean priv) {
		this.isPrivate= priv;
	}
	public void setStatic(boolean statik) {
		this.isStatic = statik;
	}
	public void setProtected(boolean protekted) {
		this.isProtected = protekted;
	}
	
	
	public void setFinal(boolean phinal) {
		this.isFinal = phinal;
	}
	
	
	
	
	
	
	public boolean isPublic() {
		return this.isPublic;
	}
	public boolean isPrivate() {
		return this.isPrivate;
	}
	public boolean isStatic() {
		return this.isStatic;
	}
	public boolean isProtected() {
		return this.isProtected;
	}
	
	
	public boolean isFinal() {
		return this.isFinal;
	}
	
	public void setRight(String entity) {
		this.right = entity;
	}
	
	public String getRight() {
		return this.right;
	}
	
	public VariableDeclarator getVariableJava() {
		return this.variable;
	}
	
	public void setType(Entity entity) {
		this.VarType = entity;
	}
	
	public Entity getType() {
		return this.VarType;
	}
	
}
