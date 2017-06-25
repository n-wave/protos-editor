package builders;

import org.eclipse.swt.widgets.Composite;

import dataContainers.DataStructure;

public abstract class Controller{
	
	/**
	 * 
	 */	
	protected String name;
	
	public Controller(String name){
		this.name = name;
	}
	
	abstract public int getNumberOfOptions();
	abstract public int getOptionIndex();
	abstract public void setOptionIndex(int optionIndex); 
	
	
    abstract public Composite getGuiComponent(Composite parent, int componentIndex);
	
	abstract public String getName();
	abstract public String[] getOptionList();
	
	abstract public void setDataStructure(DataStructure[] data);
	abstract public DataStructure[] getDataStructure();
	
}
