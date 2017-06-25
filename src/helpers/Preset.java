package helpers;

import java.io.Serializable;

import dataContainers.DataContainer;

public class Preset implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3041137446897522587L;

	private String name;
	
	DataContainer dataContainers[] = new DataContainer[4];
	
	public Preset(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getLength(){
		return 4;
	}
	
	public void setDataContainer(DataContainer data, int index){
		if(index >= 0 && index < 4){
			dataContainers[index] = data;
		}
	}
	
	public DataContainer getDataContainer(int index){
		DataContainer tmp = null;
		
		if(index >= 0 && index < 4){
			tmp = dataContainers[index];
		}
		return tmp;
	}
}
