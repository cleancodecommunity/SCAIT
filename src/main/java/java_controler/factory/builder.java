package java_controler.factory;


import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.resolution.UnsolvedSymbolException;

import model.Data;
import model.artifacts.TypeEntity;
import model.artifacts.VariableEntity;
/**
 * 
 * @author farshad ghassemi toosi
 * 
 * 
 */
public abstract class builder {

	
	
	protected String getUniqueMethodName(MethodDeclaration mthNode) {
		
		if(mthNode.findAncestor(ClassOrInterfaceDeclaration.class).isPresent() && mthNode.findAncestor(ClassOrInterfaceDeclaration.class).get().getFullyQualifiedName().isPresent())
			return mthNode.getSignature().asString()+" "+mthNode.findAncestor(ClassOrInterfaceDeclaration.class).get().getFullyQualifiedName().get();
		else 
			if(mthNode.findAncestor(ClassOrInterfaceDeclaration.class).isPresent()) 
				return mthNode.getSignature().asString()+" "+mthNode.findAncestor(ClassOrInterfaceDeclaration.class).get().getNameAsString();
			else
				return mthNode.getSignature().asString();
	}
	
	protected String getClassUniqueName(ClassOrInterfaceDeclaration cl) {
		if(cl.getFullyQualifiedName().isPresent()) 
			return cl.getFullyQualifiedName().get();
		else if (cl.findCompilationUnit().get().getPackageDeclaration().isPresent())
			return cl.findCompilationUnit().get().getPackageDeclaration().get().getNameAsString()+"."+cl.getNameAsString();
		else
			return cl.getNameAsString();
	}
	
	
	protected String getUniqueConstrcutorName(ConstructorDeclaration consl) {
		if(consl.findAncestor(ClassOrInterfaceDeclaration.class).isPresent() && consl.findAncestor(ClassOrInterfaceDeclaration.class).get().getFullyQualifiedName().isPresent())
			return consl.getSignature().asString()+" "+consl.findAncestor(ClassOrInterfaceDeclaration.class).get().getFullyQualifiedName().get();
		else 
			if(consl.findAncestor(ClassOrInterfaceDeclaration.class).isPresent()) 
				return consl.getSignature().asString()+" "+consl.findAncestor(ClassOrInterfaceDeclaration.class).get().getNameAsString();
			else
				return consl.getSignature().asString();
	}
	
	protected String getUniqueFiledName(FieldDeclaration fl) {
		
		return "";
	}
	protected String getUniqueVariableName(Parameter pr) {
		
		
		return "";
	}

	
	protected void setType(VariableEntity vr, Data project) {
		try {
			if(vr.getVariableJava().getType().resolve().isReferenceType()) {
				if(vr.getVariableJava().getType().resolve().asReferenceType().getTypeDeclaration() != null) {
					if(vr.getVariableJava().getType().resolve().asReferenceType().getTypeDeclaration().get().isClass()) {
						if(vr.getVariableJava().getType().resolve().asReferenceType().getTypeDeclaration().get().asClass().toAst().isPresent()) {
							ClassOrInterfaceDeclaration claz = (ClassOrInterfaceDeclaration) vr.getVariableJava().getType().resolve().asReferenceType().getTypeDeclaration().get().asClass().toAst().get();
							TypeEntity type = new TypeEntity(project.getCalzes().get(claz.getFullyQualifiedName()), claz.getNameAsString(),  true);
							vr.setType(type);
						}
					}
					if(vr.getVariableJava().getType().resolve().asReferenceType().getTypeDeclaration().get().isInterface()) {
						if(vr.getVariableJava().getType().resolve().asReferenceType().getTypeDeclaration().get().asInterface().toAst().isPresent()) {
							ClassOrInterfaceDeclaration claz = (ClassOrInterfaceDeclaration) vr.getVariableJava().getType().resolve().asReferenceType().getTypeDeclaration().get().asInterface().toAst().get();
							TypeEntity type = new TypeEntity(project.getCalzes().get(claz.getFullyQualifiedName()), claz.getNameAsString(),  true);
							vr.setType(type);
						}
					}
				}
			}
			else {
				TypeEntity type = new TypeEntity(vr.getVariableJava().getType().toString(), vr.getVariableJava().getType().toString(), false);
				vr.setType(type);
			}
		}
		catch(RuntimeException e) {
			TypeEntity type = new TypeEntity(vr.getVariableJava().getType().toString(), vr.getVariableJava().getType().toString(),  false);
			vr.setType(type);
		}
		if(vr.getType() == null) {
			TypeEntity type = new TypeEntity(vr.getVariableJava().getType().toString(), vr.getVariableJava().getType().toString(),  false);
			vr.setType(type);
		}
		if(vr.getVariableJava().getInitializer() == null) {
			vr.setRight("");
		}
		else {
			if(vr.getVariableJava().getInitializer().isPresent())
				vr.setRight(vr.getVariableJava().getInitializer().get().toString());
			else
				vr.setRight("");
		}
		
		//System.out.println(vr.getName());
		//System.out.println("\t"+vr.getType().getName());
	}
	
	
	
}
