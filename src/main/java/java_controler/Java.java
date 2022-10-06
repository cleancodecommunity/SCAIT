package java_controler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.SymbolResolver;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java_controler.factory.ClassCreation;
import java_controler.factory.MethodCreation;
import java_controler.factory.builder;
import model.Data;
import model.artifacts.ClassEntity;
import model.artifacts.CommentEntity;
import model.artifacts.PackageEntity;
import model.artifacts.CommentEntity.Type;
/**
 * 
 * @author farshad ghassemi toosi
 * 
 * 
 */

public class Java extends builder implements Serializable{

	
	private Data project = new Data();
	private transient ArrayList<TypeSolver> javaParserTypeSolver = new ArrayList<TypeSolver>();
	private transient CombinedTypeSolver combinedSolver;
	private transient JavaSymbolSolver symbolSolver; 
	
	//private int intTest = 9;
	private static final long serialVersionUID = 16L;
	private String root = "";
	private String rootPackage = "";
	
	public Java() {
		
	}
	
	public Java(String path) throws IOException {
		
		File file = new File(path);
		

		
		
		//javaParserTypeSolver= new JavaParserTypeSolver(file);
		
		combinedSolver = new CombinedTypeSolver(); 
		combinedSolver.add(new ReflectionTypeSolver());
		//combinedSolver.add(javaParserTypeSolver);
		getRootPackage(file);
		
		
		symbolSolver = new JavaSymbolSolver(combinedSolver); 
		
		StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
		
		listFilesForFolder(file, combinedSolver);
		
		
		ClassCreation classBuilder = new ClassCreation(project);
		project = classBuilder.getProject();
		

		MethodCreation methodBuilder = new MethodCreation(project);
		project = methodBuilder.getProject();
		
	}
	

	
	private String getRootPackage(File folder) throws IOException {
		List<Path> dirs = Files.walk(Paths.get(folder.getAbsolutePath().toString()), 100)
                .filter(Files::isDirectory)
                .collect(Collectors.toList());
		String finalRoot = "";
        for (Path addr : dirs) {
        	if(addr.toFile().isDirectory()) {
        		//System.out.println(addr);
        		if(addr.toAbsolutePath().toString().endsWith(".java"))
        			combinedSolver.add( new JavaParserTypeSolver(addr.toFile()));
        		
        	}
        }
        
        
		return finalRoot;
	}
	
	
	
	private String findRootPackage(File folder) throws FileNotFoundException {
		for (final File fileEntry : folder.listFiles()) {
	    	if (fileEntry.isDirectory() && !fileEntry.getName().startsWith(".")) {
	    		findRootPackage(fileEntry);
	        } 
	    	else {
	            if(fileEntry.getName().endsWith(".java")) {
	            	try {
		            		CompilationUnit cu =	StaticJavaParser.parse(fileEntry);
		            		
		            		if(cu.getPackageDeclaration().isPresent()) {
		            			if (cu.getPackageDeclaration().get() != null) {
		            				if( cu.getPackageDeclaration().get().getNameAsString().length()>0) {
		            				root = cu.getPackageDeclaration().get().getNameAsString().split("\\.")[0];
		            				
		            				return root;
		            				}
		            			}
		            		}
	            		}
	            		catch(ParseProblemException e) {
	            			
	            		}
	        	}
	        }
	    }	  
		return folder.getAbsolutePath();
	}
	
	public  void listFilesForFolder(File folder, CombinedTypeSolver combinedSolver ) throws IOException {
		
	    for (final File fileEntry : folder.listFiles()) {
	    	if (fileEntry.isDirectory() ) {
	        	listFilesForFolder(fileEntry, combinedSolver);
	        } else {
	            if(fileEntry.getName().endsWith(".java")) {
	            	recursiveFunc( fileEntry, combinedSolver);
	        	}
	        }
	    }	    
	}
	
	
	
	
	
	public Data getProject () {
		return project;
	}
	
	public  void recursiveFunc(File file, TypeSolver combinedSolver) throws FileNotFoundException {

		try {
		CompilationUnit cu =	StaticJavaParser.parse(file);
		
		VoidVisitor<?> mf = new  ClassParser();
		mf.visit(cu, null);
		}
		catch(ParseProblemException e) {
			
		}
	}
	
	
	
	 public class ClassParser  extends  VoidVisitorAdapter<Void> { 
		
		public void visit(ClassOrInterfaceDeclaration cl, Void arg)  {
			super.visit(cl, arg);
			
			ClassEntity newClass = new ClassEntity(project , getClassUniqueName(cl), cl.getNameAsString(), cl.resolve());
			if(cl.getRange().isPresent())
				newClass.setLOC(cl.getRange().get().getLineCount());
			
			cl.getAllContainedComments().forEach(cm -> {
				CommentEntity newComment = new CommentEntity(project, cm.getContent(), newClass);
				
				if(cm.getRange().isPresent()) {
					newComment.setLOC(cm.getRange().get().getLineCount());
					
				}
				
				if(cm.isBlockComment())
					newComment.setType(CommentEntity.Type.Block);
				if(cm.isJavadocComment())
					newComment.setType(CommentEntity.Type.Doc);
				if(cm.isLineComment())
					newComment.setType(CommentEntity.Type.Line);
			});
			
			
			
			String packageName = "";
			if(cl.findCompilationUnit() != null) {
				if(cl.findCompilationUnit().get().getPackageDeclaration() != null)
					try {
						packageName = cl.findCompilationUnit().get().getPackageDeclaration().get().getNameAsString();
					}
					catch(RuntimeException e) {
						
					}
			}
			
			if(!project.getPackages().containsKey(packageName)) {
				PackageEntity pakage = new PackageEntity(packageName, project); 
				newClass.setPackge(pakage);
				pakage.addNewClass(newClass);
				
			}
			else {
				project.getPackages().get(packageName).addNewClass(newClass);
				newClass.setPackge(project.getPackages().get(packageName));
				project.getPackages().get(packageName).addNewClass(newClass);
			}
			
			
			
		}
		
		
	}
	
	
	public static void writeObjectToFile(Object serObj, String filepath) throws IOException {

		
		
		String json = JsonWriter.objectToJson(serObj);
		
		
		File objectImage = new File(filepath);
		PrintWriter out = new PrintWriter(objectImage);
	    out.write(json);
	    out.close();
	    
	}
	
	
	public void stat(Data project) {
		System.out.println(project.getCalzes().size());
		
	}
	
	
	public static Java readObjectFromFile(String addr) throws ClassNotFoundException, IOException {
		 
		String content = new String(Files.readAllBytes(Paths.get(addr)), StandardCharsets.UTF_8);
		
		Java user2 = (Java) JsonReader.jsonToJava(content);
		return user2;
	}
	
	public String dump() {
		
		
		return root;
		
	}
	
}
