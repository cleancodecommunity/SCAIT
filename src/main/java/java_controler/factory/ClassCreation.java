package java_controler.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.declarations.ResolvedConstructorDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;

import java_controler.Java.ClassParser;
import model.Data;
import model.artifacts.ClassEntity;
import model.artifacts.ConstructorEntity;
import model.artifacts.MethodEntity;
import model.artifacts.PackageEntity;
import model.artifacts.ParameterEntity;
import model.artifacts.TypeEntity;
import model.artifacts.VariableEntity;
/**
 * 
 * @author farshad ghassemi toosi
 * 
 * 
 */
public class ClassCreation extends builder implements Serializable{

	private Data project;
	private static final long serialVersionUID = 12L;
	private boolean existing = false;
	
	private File file = new File ("debugg.txt");
	private PrintWriter in;
	private int u = 0;
	public ClassCreation() {
		
	}
	
	public ClassCreation( Data project) throws FileNotFoundException {
		this.project = project;
		in = new PrintWriter(file);
		create();
	}
	
	public void create() {
		System.out.println("Starts actions...");
		calculateAllDirectSuperClasses();
		System.out.println("All Directed done...");
		calculateAllOutgoingIncommingClasses();
		System.out.println("All outgoingIncoming classes done...");
		calculateVariableDeclrationAndAccessed();
		System.out.println("All Variable Declaraed done...");
		setModifies();
		System.out.println("All Modifiers done...");
		
	}

	
	private void calculateVariableDeclrationAndAccessed() {
		this.project.getCalzes().forEach((a, b) -> {
			if( b.getResolve().isClass()) {
				ClassOrInterfaceDeclaration myClaz = (ClassOrInterfaceDeclaration) b.getResolve().asClass().toAst().get();
				//field declaration...
				myClaz.findAll(FieldDeclaration.class).forEach(fl -> {
					//TODO make sure multiple variable declarations in one line is taken care...
					
					
					VariableEntity var = new VariableEntity(fl.getVariable(0).getNameAsString(), true, a+" "+fl.getVariable(0).getName()+"", this.project, fl.getVariable(0));
					var = setVariableModifier(fl, var);
					setType(var, this.project);
					var.setClassContainer(b);
					b.addVariable(var);
					
					fl.findAll(ObjectCreationExpr.class).forEach(obj -> {
						try {
							if(obj.resolve().toAst().isPresent()) {
								ConstructorDeclaration cons = obj.resolve().toAst().get();
								//b.getAllObjectCreated().add(cons);
								b.getAllObjectCreated().add(this.project.getConstructor().get(cons.getSignature().toString()+cons.findAncestor(ClassOrInterfaceDeclaration.class).get().getFullyQualifiedName().get()));
								this.project.getConstructor().get(cons.getSignature().toString()+cons.findAncestor(ClassOrInterfaceDeclaration.class).get().getFullyQualifiedName().get()).addIncommingClasses(b);
							}
								
						}catch (RuntimeException e) {
							
						}
					});
				});
				
			}
		});
		
		this.project.getCalzes().forEach((a, b) -> {
			if( b.getResolve().isClass()) {
				ClassOrInterfaceDeclaration myClaz = (ClassOrInterfaceDeclaration) b.getResolve().asClass().toAst().get();
				//field access
				myClaz.findAll(FieldAccessExpr.class).forEach(ac -> {
				try {
					if(ac.resolve().isField() && ac.resolve().asField().declaringType().isClass()) {
						if(ac.resolve().asField().declaringType().asClass().toAst().isPresent()) {
							ClassOrInterfaceDeclaration myClazHost = (ClassOrInterfaceDeclaration) ac.resolve().asField().declaringType().asClass().toAst().get();
							
							if(this.project.getVariables().containsKey(getClassUniqueName(myClazHost)+" "+ac.resolve().asField().getName())) {
								b.addVariableAccessed(this.project.getVariables().get(getClassUniqueName(myClazHost)+" "+ac.resolve().asField().getName()));
							}
						}
					}
				}
				catch (RuntimeException e) {
						
				}
				});
			}
		});
	}
	
	private VariableEntity setVariableModifier(FieldDeclaration vr, VariableEntity var) {
		var.setFinal(vr.isFinal());
		var.setPrivate(vr.isPrivate());
		var.setProtected(vr.isProtected());
		
		var.setPublic(vr.isPublic());
		var.setStatic(vr.isStatic());
		return var;
		
	}
	

	private void calculateAllDirectSuperClasses() {
		this.project.getCalzes().forEach((a, b) -> {
			
			//constructors
			
			b.getResolve().getConstructors().forEach(cons -> {
			
				if(cons.toAst().isPresent()) {
				ConstructorEntity constuctor = new ConstructorEntity( this.project    ,getUniqueConstrcutorName(cons.toAst().get()),cons.getName(),cons, b);
				
				
				if(cons.toAst().isPresent()) {
					ConstructorDeclaration dec = cons.toAst().get();
					constuctor.setFinal(dec.isFinal());
					constuctor.setPrivate(dec.isPrivate());
					constuctor.setProtected(dec.isProtected());
					
					constuctor.setPublic(dec.isPublic()); 
					constuctor.setStatic(dec.isStatic());
					constuctor.setSuperKey(dec.findAll(SuperExpr.class).size()>0);
					constuctor.setLOC(dec.getRange().get().getLineCount());
				
					cons.toAst().get().findAll(ObjectCreationExpr.class).forEach(obj -> {
						try {
							if(obj.resolve().toAst().isPresent()) {
								ClassOrInterfaceDeclaration claz = obj.resolve().toAst().get().findAncestor(ClassOrInterfaceDeclaration.class).get();
								constuctor.addOutgoingClasses(this.project.getCalzes().get(getClassUniqueName(claz)));
							}
						}
						catch(RuntimeException e) {
							
						}
					});
					cons.toAst().get().findAll(Parameter.class).forEach(pr -> {
						try {
							
							if (pr.resolve().getType().asReferenceType().getTypeDeclaration().get().isClass()) {// check if it is an object of a class.
								ParameterEntity param = new ParameterEntity(pr.getNameAsString(), constuctor, false, this.project);
							}
						}
						catch(RuntimeException e) {
							
						}
					});
					
					
				}
					
				
				b.addConstructor(constuctor);
				this.project.addConstructor(getUniqueConstrcutorName(cons.toAst().get()), constuctor);
				}
			});
			
			
			//in.println(a+"\t"+"First");
			ArrayList<ClassEntity> supersDirect = new ArrayList<ClassEntity>();
			ArrayList<ClassEntity> anccesstors = new ArrayList<ClassEntity>();
			try {
				b.getResolve().getAncestors().forEach(sup -> {
					if(sup.getTypeDeclaration().get().isClass() && sup.getTypeDeclaration().get().asClass().toAst().isPresent() ||
							sup.getTypeDeclaration().get().isInterface() && sup.getTypeDeclaration().get().asInterface().toAst().isPresent()
							) {
						ClassOrInterfaceDeclaration newSuper;
						if(sup.getTypeDeclaration().get().isClass())
							 newSuper = (ClassOrInterfaceDeclaration) sup.getTypeDeclaration().get().asClass().toAst().get();
						else
							newSuper = (ClassOrInterfaceDeclaration) sup.getTypeDeclaration().get().asInterface().toAst().get();
						
						String qName = "";
						if(newSuper.getFullyQualifiedName().isPresent())
							qName = newSuper.getFullyQualifiedName().get();
						else
							qName = newSuper.getNameAsString();
						
						supersDirect.add(this.project.getCalzes().get(qName));
						this.project.getCalzes().get(qName).AddAChildSub(b);
					}
				});
				
				/////////
				
				b.getResolve().getAllAncestors().forEach(ancs -> {
					if(ancs.getTypeDeclaration().get().isClass() && ancs.getTypeDeclaration().get().asClass().toAst().isPresent() ||
							ancs.getTypeDeclaration().get().isInterface() && ancs.getTypeDeclaration().get().asInterface().toAst().isPresent()
							) {
						ClassOrInterfaceDeclaration newSuper;
						if(ancs.getTypeDeclaration().get().isClass())
							 newSuper = (ClassOrInterfaceDeclaration) ancs.getTypeDeclaration().get().asClass().toAst().get();
						else
							newSuper = (ClassOrInterfaceDeclaration) ancs.getTypeDeclaration().get().asInterface().toAst().get();
						
						String qName = "";
						if(newSuper.getFullyQualifiedName().isPresent())
							qName = newSuper.getFullyQualifiedName().get();
						else
							qName = newSuper.getNameAsString();
						
						anccesstors.add(this.project.getCalzes().get(qName));
						this.project.getCalzes().get(qName).AddDecendantSub(b);
					}
				});
				
			}
			catch(UnsolvedSymbolException | IllegalArgumentException e1) {
				
			}
			b.setAllParentSupers(supersDirect);
			b.setAllAnccestors(anccesstors);
			
			
			
			//managing the methods of the class....
			if(b.getResolve().isClass() && b.getResolve().asClass().toAst().isPresent()) {
				b.getResolve().getDeclaredMethods().forEach(meth -> {
					String sig = meth.toAst().get().getNameAsString();
					
					new MethodEntity(getUniqueMethodName(meth.toAst().get()) , meth.getName(), sig, meth, this.project, b);
					
					//calculateAssignExpressions(meth);
				});
			 }
		});
	}
	
	
	
	
	
	private void calculateAllOutgoingIncommingClasses() {
		this.project.getCalzes().forEach((a, b) -> {
			in.println(a);
			ArrayList<ClassEntity> outgoingClass = new ArrayList<ClassEntity>();
			
			if(b.getResolve().isClass() && b.getResolve().asClass().toAst().isPresent())
			
			b.getResolve().asClass().toAst().get().findAll(ObjectCreationExpr.class).forEach(cl -> {
				try {
					//in.println("\tA");
					if(!cl.getAnonymousClassBody().isPresent()) {
						//in.println("\tB");
						if(!cl.calculateResolvedType().isNull())
							if(cl.resolve().declaringType().isClass())
						if( cl.resolve().declaringType().asClass().toAst().isPresent() && this.project.getCalzes().containsKey(getClassUniqueName((ClassOrInterfaceDeclaration) cl.resolve().declaringType().asClass().toAst().get()))) {
							outgoingClass.add(this.project.getCalzes().get(     getClassUniqueName((ClassOrInterfaceDeclaration) cl.resolve().declaringType().asClass().toAst().get())    ));
							this.project.getCalzes().get(getClassUniqueName((ClassOrInterfaceDeclaration) cl.resolve().declaringType().asClass().toAst().get())).addAnIncomingClasses(b);
							//in.println("\tC");
							////
							
							/////
						}
					}
				}
				catch (Exception e) {
					//System.out.println("errro\t"+a);
				}
			});
			
			b.setAllOutgoingClasses(outgoingClass);
			
		});
	}
	
	
	public Data getProject() {
		return this.project;
	}
	
	private void setModifies() {
		this.project.getCalzes().forEach((a, b) -> {
			ClassOrInterfaceDeclaration cl = null;
			
			if(b.getResolve().isClass()) {
				cl = (ClassOrInterfaceDeclaration) b.getResolve().asClass().toAst().get();
			}
			if(b.getResolve().isInterface()) {
				cl = (ClassOrInterfaceDeclaration) b.getResolve().asInterface().toAst().get();
			}
			
			b.setAbstract(cl.isAbstract());
			b.setPrivate(cl.isPrivate());
			b.setPublic(cl.isPublic());
			b.setInner(cl.isInnerClass());
			b.setInterface(cl.isInterface());
			b.setConcrete(!cl.isAbstract() && !cl.isInterface());
			b.setNested(cl.isNestedType());
			b.setStatic(cl.isStatic());
			
			cl.findAll(ClassOrInterfaceDeclaration.class).forEach(cll -> {
				this.project.getCalzes().get(getClassUniqueName(cll)).setClassContainer(b);
				b.addInnerNestedClasses(this.project.getCalzes().get(getClassUniqueName(cll)));
			});
			
		});
	}
	
	
	

	
	
}
