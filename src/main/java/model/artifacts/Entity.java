package model.artifacts;

import java.io.Serializable;

import model.Data;

public interface Entity extends Serializable{
	
	public String getUniqueName();
	public Data getProject();
	public String getName();
	
	
	public void setUniqueName(String UniqueName);
	public void setProject(Data project);
	public void setName(String Name);
	
	
}
