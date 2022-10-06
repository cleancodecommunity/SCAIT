package model.artifacts;

import java.io.Serializable;

import model.Data;
/**
 * 
 * @author farshad ghassemi toosi
 * 
 * 
 */
public class CommentEntity implements  Entity , Serializable{

	
	public static enum Type {
		Doc,
		Block,
		Line
	}
	private String content;
	private Type type;
	private Data project; 
	
	private ClassEntity claz;
	private PackageEntity pakage;
	private int LOC;
	public CommentEntity() {
		
	}
			
	public CommentEntity(Data project, String content,  ClassEntity claz) {
		this.content = content;
		this.project = project;
		this.claz = claz;
		this.claz.addComment(this);
		this.project.addComment(this);
	}
	
	@Override
	public String getUniqueName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Data getProject() {
		// TODO Auto-generated method stub
		return this.project;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public PackageEntity getPackge() {
		// TODO Auto-generated method stub
		return this.claz.getPackge();
	}

	@Override
	public void setUniqueName(String UniqueName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProject(Data project) {
		this.project = project;
		
	}

	@Override
	public void setName(String Name) {
		// TODO Auto-generated method stub
		
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public void setLOC(int loc) {
		this.LOC = loc;
	}
	
	public int getLOC() {
		return this.LOC;
	}

}
