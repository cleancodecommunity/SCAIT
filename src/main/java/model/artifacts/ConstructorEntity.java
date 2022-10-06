package model.artifacts;

import java.io.Serializable;
import java.util.ArrayList;

import com.github.javaparser.resolution.declarations.ResolvedConstructorDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;

import model.Data;
import model.exception.Exceptions;



/**
 * 
 * @author farshad ghassemi toosi
 * 
 * 
 */


public class ConstructorEntity implements Serializable, Entity {
	private static final long serialVersionUID = 24L;
	
	private ClassEntity classContainer;
	private String name;
	private String UniqueName;
	
	
	private int LinesOfCode;
	private boolean isStatic;
	private boolean isPublic;
	private boolean isPrivate;
	private boolean isProtected;
	private boolean isOverriden;
	private boolean hasSuperKey;
	
	private boolean isFinal;
	
	private ArrayList<MethodEntity> IncomingMethods = new ArrayList<MethodEntity>();
	private ArrayList<MethodEntity> OutgoingMethods = new ArrayList<MethodEntity>();
	
	private ArrayList<ClassEntity> IncomingClasses = new ArrayList<ClassEntity>();
	private ArrayList<ClassEntity> OutgoingClasses = new ArrayList<ClassEntity>();
	
	private ArrayList<ParameterEntity> parameters = new ArrayList<ParameterEntity>();
	private ArrayList<Exceptions> allExceptions = new ArrayList<Exceptions>();
	private ArrayList<VariableEntity> Variables = new ArrayList<VariableEntity>();
	
	private Data project;
	//private Data project;
	
	transient ResolvedConstructorDeclaration cons;
	public ConstructorEntity() {
		
	}
	public ConstructorEntity(Data project, ClassEntity classContainer, String qName, String name) {
		this.UniqueName = qName;
		this.name = name;
		this.classContainer.addConstructor(this);
		this.project = project;
		this.project.addConstructor(qName, this);
	}
	
	public ConstructorEntity(Data project, String qName, String name, ResolvedConstructorDeclaration cons, ClassEntity classContainer) { // this is for Java
		this.UniqueName = qName;
		this.name = name;
		this.cons = cons;
		this.classContainer = classContainer;
		this.classContainer.addConstructor(this);
		this.project = project;
		this.project.addConstructor(qName, this);
	}
	
	public void addOutgoingMethod(MethodEntity Callee) {
		IncomingMethods.add(Callee);
	}
	public void addIncommingMethod(MethodEntity Caller) {
		OutgoingMethods.add(Caller);
	}
	public void addOutgoingClasses(ClassEntity Callee) {
		OutgoingClasses.add(Callee);
	}
	public void addIncommingClasses(ClassEntity Caller) {
		IncomingClasses.add(Caller);
	}
	public void addParameterMethod(ParameterEntity par) {
		parameters.add(par);
	}
	public void addVariableMethod(VariableEntity var) {
		Variables.add(var);
	}
	
	
	public ArrayList<MethodEntity> getAllIncommingMethod() {
		return IncomingMethods;
	}
	public ArrayList<MethodEntity> getAllOutgoingMethod() {
		return OutgoingMethods;
	}
	public ArrayList<ClassEntity> getAllIncommingClasses() {
		return this.IncomingClasses;
	}
	public ArrayList<ClassEntity> getAllOutgoingClasses() {
		return this.OutgoingClasses;
	}
	public ArrayList<ParameterEntity> getAllParameterMethod() {
		return this.parameters;
	}
	public ArrayList<VariableEntity> getAllVariablesMethod() {
		return this.Variables;
	}
	
	
	
	public ResolvedConstructorDeclaration geConstructorNode() {
		return this.cons;
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
	public void setOverriden(boolean override) {
		this.isOverriden = override;
	}
	public void setSuperKey(boolean hasSuperKey) {
		this.hasSuperKey = hasSuperKey;
	}
	
	
	
	public void setFinal(boolean phinal) {
		this.isFinal = phinal;
	}
	public void setLOC(int loc) {
		this.LinesOfCode = loc;
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
	public boolean isOverriden() {
		return this.isOverriden;
	}
	public boolean hasSuper() {
		return this.hasSuperKey;
	}
	
	public boolean isFinal() {
		return this.isFinal;
	}
	public int getLOC() {
		return this.LinesOfCode;
	}
	
	
	
	

	public PackageEntity getPackgeName() {
		return this.classContainer.getPackge();
	}

	
	
	@Override
	public String getUniqueName() {
		return this.UniqueName;
	}

	@Override
	public Data getProject() {
		return this.classContainer.getProject();
	}

	@Override
	public String getName() {
		
		return this.name;
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
	
	
	public void setClassContainer(ClassEntity classContainer) {
		this.classContainer = classContainer;
	}
	
	public ClassEntity getClassContainer() {
		return this.classContainer;
	}
	
	public ArrayList< Exceptions> getExceptions() {
		return allExceptions;
	}
	public void addExceptions( Exceptions newException) {
		allExceptions.add( newException);
	}
}
