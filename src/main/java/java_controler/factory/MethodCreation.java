package java_controler.factory;

import java.io.Serializable;
import java.util.ArrayList;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;

import model.Data;
import model.artifacts.ClassEntity;
import model.artifacts.MethodEntity;
import model.artifacts.ParameterEntity;
import model.artifacts.TypeEntity;
import model.artifacts.VariableEntity;
import model.exception.Cach;
import model.exception.Exceptions;
import model.exception.Try;
/**
 * 
 * @author farshad ghassemi toosi
 * 
 * 
 */
public class MethodCreation extends builder implements Serializable{
	private static final long serialVersionUID = 13L;
	private Data project;
	private ResolvedMethodDeclaration methodNode;
	private MethodEntity method;
	private MethodDeclaration methodParser;
	private int u =0;
	
	public MethodCreation() {
		
	}
	
	
	public MethodCreation(Data project) {
		this.project = project;
		creat();
		setModifies();
	}
	
	
	public void creat() {
		calculateAllIncommeingOutgoing();
	}
	
	private void calculateAllIncommeingOutgoing() {
		this.project.getMethods().forEach((a, b) -> {
			MethodDeclaration method = b.getMethodNode().toAst().get();
			
			// here to collect the method local variables....
			//TODO make sure all variables are collected...
			method.findAll(VariableDeclarationExpr.class).forEach(vr -> {
				
				VariableEntity var = new VariableEntity(vr.getVariable(0).getNameAsString(), false, b.getUniqueName()+" "+vr.getVariable(0).getName()+"", this.project, vr.getVariable(0));
				setType(var, this.project);
				var.setMethodContainer(b);
				this.project.getMethods().get(b.getUniqueName()).addVariableMethod(var);
				
			});
			
			
			
			//here to create the parameters....
			method.getParameters().forEach(pr -> {
				ParameterEntity param = new ParameterEntity(pr.getNameAsString(), b, true, this.project);
				if(!pr.getType().isPrimitiveType()) {
					try {
						if(pr.getType().resolve().isReferenceType())
							if(pr.getType().resolve().asReferenceType().getTypeDeclaration() != null)
								if(pr.getType().resolve().asReferenceType().getTypeDeclaration().get().isClass()) 
									if(pr.getType().resolve().asReferenceType().getTypeDeclaration().get().asClass().toAst().isPresent()) {
										param.setPrimitive(false);
										param.setType(pr.getType().toString());
										ClassOrInterfaceDeclaration claz = (ClassOrInterfaceDeclaration) pr.getType().resolve().asReferenceType().getTypeDeclaration().get().asClass().toAst().get();
										param.setClassRefrence(this.project.getCalzes().get(getClassUniqueName(claz)));
										param.setPartofProject(true);
									}
									else {
										param.setPrimitive(false);
										param.setType(pr.getType().toString());
										param.setPartofProject(false);
									}
						}
					catch(UnsolvedSymbolException e) {
						param.setPrimitive(false);
						param.setType(pr.getType().toString());
						param.setPartofProject(false);
					}
				}
				else {
					param.setPrimitive(true);
					param.setType(pr.getType().toString());
					param.setPartofProject(false);
				}
				
				this.project.getMethods().get(b.getUniqueName()).addParameterMethod(param);
			});
			
			
			
			
			method.findAll(MethodCallExpr.class).forEach(callee -> {
				try {
					/**TODO
					 * the method calls in listener classes are not detected...
					 */
					
					if(callee.resolve().toAst().isPresent()) {
						//System.out.println("..+++.."+u++);
						MethodDeclaration calleeSolved = callee.resolve().toAst().get();
						//System.out.println("..-----.."+getUniqueMethodName(calleeSolved));
						if(this.project.getMethods().containsKey(getUniqueMethodName(calleeSolved))) {
							
							this.project.getMethods().get(getUniqueMethodName(calleeSolved)).addIncommingMethod(b);
							this.project.getMethods().get(b.getUniqueName()).addOutgoingMethod(this.project.getMethods().get(getUniqueMethodName(calleeSolved)));
							//System.out.println("...."+u++);
							
						}
					}
				}
				catch(RuntimeException e) {
					//System.out.println(e);
					//System.out.println(getUniqueMethodName(method));
					//System.out.println("\t"+callee);
					
				}
			});
			
			
			////// Here is for exception handlings...
			u = 0;
			
			method.findAll(TryStmt.class).forEach(Tr -> {
				Exceptions exception = new Exceptions(u+" "+getUniqueMethodName(method), this.project, b);
				u++;
				Try tri = new Try(b, exception);
				tri.setNumLine(Tr.getRange().get().getLineCount());
				
				Tr.getCatchClauses().forEach(ch -> {
					Cach chach = new Cach(this.project, b, tri);
					chach.setLOC(ch.getRange().get().getLineCount());
					
					if(ch.findAll(ReturnStmt.class).size()>0)
						chach.setReturn(ch.findAll(ReturnStmt.class).get(0).toString());
					else
						chach.setReturn("");
					
					chach.setBody(!ch.getBody().isEmpty());
					
					exception.addCatch(chach);
				});
				exception.setFinaly(Tr.getFinallyBlock().isPresent());
				if(Tr.getFinallyBlock().isPresent())
					exception.setLOCFinaly(Tr.getFinallyBlock().get().getRange().get().getLineCount());
				else
					exception.setLOCFinaly(0);
				Tr.findAll(MethodCallExpr.class).forEach(mtCall -> {
					try {
						if(mtCall.resolve().toAst().isPresent()) {
							tri.addOutgoingMethod(this.project.getMethods().get(getUniqueMethodName(mtCall.resolve().toAst().get())));
						}
					}
					catch(RuntimeException e) {
						
					}
				});
				
				exception.setTry(tri);
				
			});
			
			
			
			// constructor calls
			method.findAll(ObjectCreationExpr.class).forEach(constructor -> {
				try {
					if(constructor.resolve().toAst().isPresent()) {
						b.addOutgoingConstructor(this.project.getConstructor().get(constructor.resolve().getSignature().toString()+getClassUniqueName(constructor.resolve().toAst().get().findAncestor(ClassOrInterfaceDeclaration.class).get())));
						this.project.getConstructor().get(getUniqueConstrcutorName(constructor.resolve().toAst().get())).addIncommingMethod(b);
					}
				}
				catch(RuntimeException e) {
					
				}
			});
		});
		
		
		//getting outgoing calls for constructors...
		this.project.getConstructor().forEach((a, b) ->{
			
				ConstructorDeclaration constrcutor = b.geConstructorNode().toAst().get();
				constrcutor.findAll(MethodCallExpr.class).forEach(callee -> {
					try {
						if(callee.resolve().toAst().isPresent()) {
							MethodDeclaration cale = callee.resolve().toAst().get();
							b.addOutgoingMethod(this.project.getMethods().get(getUniqueMethodName(cale)));
							this.project.getMethods().get(getUniqueMethodName(cale)).addIncommingConstructor(b);
							
						}
					}
					catch(RuntimeException e) {
						
					}
					
				});
			
		});
		
		
	}
	
	private MethodEntity getMethod() {
		return this.method;
	}
	
	public Data getProject() {
		return this.project;
	}
	
	
	
	
	
	private void setModifies() {
		this.project.getMethods().forEach((a, b) -> {
			MethodDeclaration mt = b.getMethodNode().toAst().get();
			
			b.setFinal(mt.isFinal());
			
			b.setPrivate(mt.isPrivate());
			b.setProtected(mt.isProtected());
			b.setPublic(mt.isPublic());
			b.setStatic(mt.isStatic());
			
			
			b.setLOC(mt.getRange().get().getLineCount());
			polymorphysm(b);
		});
	}
	
	
	
	
	
	
	
	private void polymorphysm(MethodEntity mt) {
		
		String name = mt.getSigniture();
		
		for(ClassEntity cls : mt.getClassContainer().getAllAnccestors()) {
			for(MethodEntity m : cls.getAllMethods()) {
				if(name.equals(m.getSigniture())) {
					mt.setOverriden(true);
					m.setSuperMethod(true);
					
					break;
				}
			}
		}
		
		for(MethodEntity m : mt.getClassContainer().getAllMethods()) {
			
			if(m.getSigniture().equals(mt.getSigniture())) {
				if(m != mt) {
					mt.setOverrloaded(true);
					m.setOverrloaded(true);
				}
			}
		}
		
		
		
	}
	
	
	

	
}
