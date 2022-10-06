package model.artifacts;

import java.io.Serializable;
import java.util.ArrayList;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;

import model.Data;
/**
 * 
 * @author farshad ghassemi toosi
 * 
 * 
 */
public class ClassEntity implements Entity , Serializable{
	
	private String name;
	private String UniqueName="";
	
	private int LinesOfCode=-1;
	private String code;
	private boolean isinner;
	private boolean isnested;
	private boolean isstatic;
	private boolean isinterface;
	private boolean isabstarct;
	private boolean ispublic;
	private boolean isprivate;
	private boolean isconcret;
	private PackageEntity packageContainer;
	
	private ClassEntity containerClass; //if the class is inner or nested.
	
	private static final long serialVersionUID = 22L;
	
	
	private ArrayList<ClassEntity> parentSuperClasses = new ArrayList<ClassEntity>();
	private ArrayList<ClassEntity> AnccestorSuperClasses = new ArrayList<ClassEntity>();
	
	private ArrayList<ClassEntity> childSubClasses = new ArrayList<ClassEntity>();
	private ArrayList<ClassEntity> DeccendantSubClasses = new ArrayList<ClassEntity>();
	
	private ArrayList<ClassEntity> innerOrNestedClasses = new ArrayList<ClassEntity>();
	
	private ArrayList<ClassEntity> incomingClasses = new ArrayList<ClassEntity>();
	private ArrayList<ClassEntity> outgoingClasses = new ArrayList<ClassEntity>();
	
	private ArrayList<MethodEntity> ContainedMethods = new ArrayList<MethodEntity>();
	private ArrayList<ConstructorEntity> ContainedConstructors = new ArrayList<ConstructorEntity>();
	
	
	private ArrayList<VariableEntity> variableDeclaration = new ArrayList<VariableEntity>();
	
	private ArrayList<VariableEntity> variableAccessed = new ArrayList<VariableEntity>();
	
	private ArrayList<ConstructorEntity> objectCreated = new ArrayList<ConstructorEntity>();
	
	private ArrayList<CommentEntity> comments = new ArrayList<CommentEntity>();
	
	private transient ResolvedWrapper res = new ResolvedWrapper();
	
	private Data project;
	
	
	public ClassEntity() {
		
	}
	
	public ClassEntity(Data project, String UniqueName, String name) {
		this.UniqueName = UniqueName;
		this.name = name;
		this.project = project;
		this.project.addCalss(UniqueName, this);
	}
	
	public ClassEntity(Data project, String UniqueName, String name, ResolvedReferenceTypeDeclaration res) { // this constructor is particulrarly for Java
		this.UniqueName = UniqueName;
		this.res.setResolved(res);
		this.name = name;
		this.project = project;
		this.project.addCalss(UniqueName, this);
	}
	
	
	public ResolvedReferenceTypeDeclaration getResolve() {
		return this.res.getRes();
	}
	
	public String getUniqueName() {
		return this.UniqueName;
	}
	
	public Data getProject() {
		return this.project;
	}
	
	
	public void setAllParentSupers(ArrayList<ClassEntity> supers) {
		this.parentSuperClasses = supers;
	}
	
	public ArrayList<ClassEntity> getAllParentSupers() {
		return this.parentSuperClasses;
	}
	
	public void setAllAnccestors(ArrayList<ClassEntity> supers) {
		this.AnccestorSuperClasses = supers;
	}
	
	public ArrayList<ClassEntity> getAllAnccestors() {
		return this.AnccestorSuperClasses;
	}
	
	public void setAllDeccendants(ArrayList<ClassEntity> supers) {
		this.DeccendantSubClasses = supers;
	}
	
	public ArrayList<ClassEntity> getAllDeccendants() {
		return this.DeccendantSubClasses;
	}
	
	public ArrayList<ClassEntity> getAllChildrenSubs() {
		return this.childSubClasses;
	}
	
	public ArrayList<ClassEntity> getInnerOrNestedClasses() {
		return this.innerOrNestedClasses;
	}
	
	public ArrayList<ConstructorEntity> getAllObjectCreated() {
		return this.objectCreated;
	}
	
	
	public void AddAChildSub(ClassEntity sub) {
		this.childSubClasses.add(sub);
	}
	
	public void AddDecendantSub(ClassEntity sub) {
		this.DeccendantSubClasses.add(sub);
	}
	public void AddObjectCreated(ConstructorEntity cons) {
		this.objectCreated.add(cons);
	}
	
	
	
	
	
	
	
	public ArrayList<ClassEntity> getAllOutgoingClasses() {
		return this.outgoingClasses;
	}
	
	public void setAllOutgoingClasses(ArrayList<ClassEntity> outgoings) {
		this.outgoingClasses = outgoings;
	}
	
	public ArrayList<ClassEntity> getAllIncomingClasses() {
		return this.incomingClasses;
	}
	
	public void addAnIncomingClasses(ClassEntity incoming) {
		this.incomingClasses.add(incoming);
	}
	
	public void addInnerNestedClasses(ClassEntity inner) {
		this.innerOrNestedClasses.add(inner);
	}

	public void addMethod(MethodEntity method) {
		this.ContainedMethods.add(method);
	}
    
	public ArrayList<MethodEntity> getAllMethods() {
		return this.ContainedMethods;
	}
	
	
	public void addConstructor(ConstructorEntity cons) {
		this.ContainedConstructors.add(cons);
	}
    
	public ArrayList<ConstructorEntity> getAllConstructors() {
		return this.ContainedConstructors;
	}
	
	
	public void addVariable(VariableEntity var) {
		this.variableDeclaration.add(var);
	}
	
	public ArrayList<VariableEntity> getAllDeclaraedVariables() {
		return this.variableDeclaration;
	}
	
	public void addVariableAccessed(VariableEntity var) {
		this.variableAccessed.add(var);
	}
	
	public ArrayList<VariableEntity> getAllAccessedVariables() {
		return this.variableAccessed;
	}
	
	public ArrayList<CommentEntity> getAllComments() {
		return this.comments;
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
	public void setName(String name) {
		this.name = name;
	}
	
	public void setInner(boolean inner) {
		this.isinner = inner;
	}
	public void setNested(boolean nested) {
		this.isnested = nested;
	}
	public void setStatic(boolean statik) {
		this.isstatic = statik;
	}
	public void setInterface(boolean interfase) {
		this.isinterface = interfase;
	}
	public void setAbstract(boolean abstrakt) {
		this.isabstarct = abstrakt;
	}
	public void setPublic(boolean publik) {
		this.ispublic = publik;
	}
	public void setPrivate(boolean priv) {
		this.isprivate = priv;
	}
	public void setConcrete(boolean koncrete) {
		this.isconcret = koncrete;
	}
	
	public void addComment(CommentEntity comment) {
		this.comments.add(comment);
	}

	
	
	public boolean isInner() {
		return this.isinner;
	}
	public boolean isNested() {
		return this.isnested;
	}
	public boolean isStatic() {
		return this.isstatic;
	}
	public boolean isInterface() {
		return this.isinterface;
	}
	public boolean isAbstract() {
		return this.isabstarct;
	}
	public boolean isPublic() {
		return this.ispublic;
	}
	public boolean isPrivate() {
		return this.isprivate;
	}
	public boolean isConcrete() {
		return this.isconcret;
	}
	
	
	public PackageEntity getPackge() {
		return this.packageContainer;
	}

	
	public void setPackge(PackageEntity pckg) {
		this.packageContainer = pckg;
	}
	
	public void setLOC(int loc) {
		this.LinesOfCode = loc;
	}
	
	public int getLOC() {
		return this.LinesOfCode;
	}
	
	public void setClassContainer(ClassEntity claz) {
		this.containerClass = claz;
	}
	
	public ClassEntity getClassContainer() {
		return this.containerClass;
	}
	
	public String getCode() {
		
		return this.code;
	}
	
	public void setCode(String code) {
		
		this.code = code; 
	}
}
