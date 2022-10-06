package model.artifacts;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.github.javaparser.resolution.MethodUsage;
import com.github.javaparser.resolution.declarations.ResolvedConstructorDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedFieldDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedTypeParameterDeclaration;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import com.github.javaparser.resolution.types.ResolvedType;
/**
 * 
 * @author farshad ghassemi toosi
 * 
 * 
 */
public class ResolvedWrapper implements Serializable {
	transient ResolvedReferenceTypeDeclaration res;
	private static final long serialVersionUID = 26L;
	public ResolvedWrapper() {
		
	}
	
	public void setResolved(ResolvedReferenceTypeDeclaration res) {
		this.res = res;
	}
	
	public ResolvedReferenceTypeDeclaration getRes() {
		return this.res;
	}
}
