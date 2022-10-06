package model.artifacts;

import java.io.Serializable;
import java.util.ArrayList;

import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;

import model.Data;
import model.exception.Exceptions;
/**
 * 
 * @author farshad ghassemi toosi
 * 
 * 
 */
public class MethodEntity implements Serializable, Entity {
	private static final long serialVersionUID = 24L;
	
	private ClassEntity classContainer;
	private String name;
	private String UniqueName;
	private String Signiture;
	
	private String code;
	
	private int LinesOfCode;
	private int Linenum = -1;
	
	private boolean isStatic;
	private boolean isPublic;
	private boolean isPrivate;
	private boolean isProtected;
	private boolean isOverriden;
	private boolean isOverloaded;
	private boolean isSuperMethod;
	
	
	private boolean isFinal;
	
	private ArrayList<MethodEntity> IncomingMethods = new ArrayList<MethodEntity>();
	private ArrayList<MethodEntity> OutgoingMethods = new ArrayList<MethodEntity>();
	
	private ArrayList<ConstructorEntity> IncomingConstructors = new ArrayList<ConstructorEntity>();
	private ArrayList<ConstructorEntity> OutgoingConstructors = new ArrayList<ConstructorEntity>();
	
	private ArrayList<ParameterEntity> parameters = new ArrayList<ParameterEntity>();
	private ArrayList<Exceptions> allExceptions = new ArrayList<Exceptions>();
	private ArrayList<VariableEntity> Variables = new ArrayList<VariableEntity>();
	
	
	private Data project;
	
	transient ResolvedMethodDeclaration method;
	
	public MethodEntity() {
		
	}
	
	public MethodEntity(String qName, String name, String signiture,  Data project, ClassEntity claz) {
		this.UniqueName = qName;
		this.Signiture = signiture;
		this.name = name;
		this.classContainer = claz;
		this.classContainer.addMethod(this);
		this.project = project;
		this.project.addMethod(qName, this);
	}
	
	
	public MethodEntity(String qName, String name, String signiture, ResolvedMethodDeclaration mth, Data project, ClassEntity claz) {
		this.UniqueName = qName;
		this.Signiture = signiture;
		this.name = name;
		this.method = mth;
		this.classContainer = claz;
		this.classContainer.addMethod(this);
		this.project = project;
		this.project.addMethod(qName, this);
	}
	
	public void addOutgoingMethod(MethodEntity Callee) {
		OutgoingMethods.add(Callee);
	}
	public void addOutgoingConstructor(ConstructorEntity Callee) {
		OutgoingConstructors.add(Callee);
	}
	
	public void addIncommingMethod(MethodEntity Caller) {
		IncomingMethods.add(Caller);
	}
	public void addIncommingConstructor(ConstructorEntity Caller) {
		this.IncomingConstructors.add(Caller);
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
	public ArrayList<ConstructorEntity> getAllIncommingConstructor() {
		return this.IncomingConstructors;
	}
	public ArrayList<MethodEntity> getAllOutgoingMethod() {
		return OutgoingMethods;
	}
	public ArrayList<ConstructorEntity> getAllOutgoingConstructor() {
		return this.OutgoingConstructors;
	}
	public ArrayList<ParameterEntity> getAllParameterMethod() {
		return this.parameters;
	}
	public ArrayList<VariableEntity> getAllVariablesMethod() {
		return this.Variables;
	}
	
	
	
	public ResolvedMethodDeclaration getMethodNode() {
		return this.method;
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
	public void setOverrloaded(boolean overload) {
		this.isOverloaded = overload;
	}
	public void setSuperMethod(boolean superMethod) {
		this.isSuperMethod = superMethod;
	}
	
	public void setFinal(boolean phinal) {
		this.isFinal = phinal;
	}
	public void setLOC(int loc) {
		this.LinesOfCode = loc;
	}
	public void setLineNum(int ln) {
		this.Linenum = ln;
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
	public boolean isOverloaded() {
		return this.isOverloaded;
	}
	public boolean isSuperMethod() {
		return this.isSuperMethod;
	}
	
	public boolean isFinal() {
		return this.isFinal;
	}
	public int getLOC() {
		return this.LinesOfCode;
	}
	public int getLineNum() {
		return this.Linenum;
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
		return this.project;
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
	
	public String getSigniture() {
		
		return this.Signiture;
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
	
	public PackageEntity getPackage() {
		return this.classContainer.getPackge();
	}
	
	public String getCode() {
		
		return this.code;
	}
	
	public void setCode(String code) {
		
		this.code = code; 
	}
	
	
	
}
