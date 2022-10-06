package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
/**
 * 
 * @author farshad ghassemi toosi
 * 
 * 
 */
public class WriteToFile implements Serializable{
	private static final long serialVersionUID = 21L;
	String address;
	PrintWriter out;
	File file;
	public WriteToFile() {
		
	}
	
	public WriteToFile(String address) throws FileNotFoundException {
		this.address = address;
		file = new File(address);
		out = new PrintWriter(file);
	}
	
	public void writeContent(String data) {
		this.out.write(data);
	}
	
	public void close() {
		this.out.close();
	}
	
	
}
