package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import model.artifacts.AssignEntity;
import model.artifacts.ClassEntity;
import model.artifacts.CommentEntity;
import model.artifacts.ConstructorEntity;
import model.artifacts.MethodEntity;
import model.artifacts.PackageEntity;
import model.artifacts.VariableEntity;
import model.exception.Exceptions;

/**
 * 
 * @author farshad.toosi
 * Copyright by 2022 MTU 
 */



public class Data implements Serializable{
	
	
	
	
	
	
	private static final long serialVersionUID = 15L;
	private HashMap<String, ClassEntity> allClasses = new HashMap<String, ClassEntity>();
	private HashMap<String,MethodEntity> allMethods = new HashMap<String,MethodEntity>();
	private HashMap<String,ConstructorEntity> allConstructors = new HashMap<String,ConstructorEntity>();
	private HashMap<String,VariableEntity> allVariables = new HashMap<String,VariableEntity>();
	private ArrayList<Exceptions> allExceptions = new ArrayList<Exceptions>();
	private ArrayList<CommentEntity> allComments = new ArrayList<CommentEntity>();
	private HashMap<String,PackageEntity> allPackages = new HashMap<String,PackageEntity>();
	private ArrayList<AssignEntity> allAssigns = new ArrayList<AssignEntity>();
	
	
	public Data() {
		
	}
	
	public void addCalss(String qName, ClassEntity newClass) {
		allClasses.put(qName, newClass);
	}
	
	public void addMethod(String qName, MethodEntity newMethod) {
		allMethods.put(qName,newMethod);
	}
	public void addConstructor(String qName, ConstructorEntity newCons) {
		this.allConstructors.put(qName, newCons);
	}

	public void addVariables(String qName, VariableEntity newVariable) {
		allVariables.put(qName,newVariable);
	}
	public void addExceptions( Exceptions newException) {
		allExceptions.add( newException);
	}
	public void addPackage(String qName, PackageEntity newPackage) {
		allPackages.put(qName,newPackage);
	}
	public void addComment(CommentEntity newComment) {
		allComments.add(newComment);
	}
	public void addAssign(AssignEntity newAssign) {
		allAssigns.add(newAssign);
	}
	
	
	public HashMap<String, ClassEntity> getCalzes() {
		return this.allClasses;
	}
	public HashMap<String, MethodEntity> getMethods() {
		return this.allMethods;
	}
	public HashMap<String, ConstructorEntity> getConstructor() {
		return this.allConstructors;
	}
	public HashMap<String, VariableEntity> getVariables() {
		return this.allVariables;
	}
	public ArrayList< Exceptions> getExceptions() {
		return this.allExceptions;
	}
	public HashMap<String, PackageEntity> getPackages() {
		return this.allPackages;
	}
	public ArrayList< CommentEntity> getComments() {
		return this.allComments;
	}
	public ArrayList<AssignEntity> getAssign() {
		return this.allAssigns;
	}
	
	
}
