package builders;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import dataContainers.ControlChangeData;
import dataContainers.DataStructure;
import dataContainers.DisabledControllerData;
import guiComponents.ControlChangeComponent;
import guiComponents.DisabledControllerComponent;

/**
 * @author mario
 *	26-04-2017
 *
 * Option ID's use for parsing 
 * and handling data in Teensy 3.1
 * 
 * ProgramChange	 0xE0
 * Note 		 	 0xE1
 * Note and CC		 0xE2
 * PitchBend		 0xE3
 * PitchBendNote	 0xE4
 * ControlChange 	 0xE5
 * ControlChangeFade 0xE6
 * 
 * 
 **/

public class PotentioMeter extends Controller {

	private String[] optionList = { 
									"Disabled",
									"Control Change"
								  };	
	
	private int optionIndex = 0;
	private int numberOfOptions = 2;
	
	private DataStructure[] data = new DataStructure[2];

	
	public PotentioMeter(String name){
		super(name);
		data[0] = new DisabledControllerData();
		data[1] = new ControlChangeData();
		
		//System.out.println(this.toString());
	}
	
	
	@Override
	public void setDataStructure(DataStructure data[]){
		try{
			this.data = data;
		} catch (Exception e){
			System.err.println("Error ocurred setting DataStructure");
			e.printStackTrace(System.err);
		}
	}
	
	@Override 
	public DataStructure[] getDataStructure(){
		return data;
	}
	
	@Override
	public int getNumberOfOptions(){
		return numberOfOptions;
	}
	
	
	@Override
	public String[] getOptionList() {
		// TODO Auto-generated method stub
		return optionList;
	}

	@Override
	public String getName(){
		return name;
	}
	
	@Override 
	public int getOptionIndex(){
		return optionIndex;
	}
	
	@Override 
	public String toString(){
		String internalValues = new String("/**Controller**/" + 
											"\n" + 
											name + 
											"\n");
		
		String dataString = new String();
		
		try{
			for(int i=0; i < data.length; i++){
				dataString += data[i].toString() + "\n";
			}
		}	catch(Exception e){
				System.err.println("Error ocurred in Potentiometer");
				e.printStackTrace(System.err);
		}
				
		internalValues += dataString;
		
		return internalValues;
	}
	
	@Override
	public void setOptionIndex(int optionIndex){
		if(optionIndex >= 0 || optionIndex < optionList.length){
			this.optionIndex = optionIndex;
		} else {
			System.err.println("Index Out of range option not available");
		}
	}
	
	@Override
	public Composite getGuiComponent(Composite parent, int componentIndex){
		Composite guiComponent = null;
		
		try{
			switch(componentIndex){
				case 0:
					guiComponent = new DisabledControllerComponent(parent, SWT.NONE, data[0]);
					break;
				case 1:
					guiComponent = new ControlChangeComponent(parent, SWT.NONE, data[1]);	
					break;
			}
			
	
		} catch(Exception e){
			System.err.println("Error ocurred while creating gui Component");
			e.printStackTrace(System.err);
		}
		return guiComponent;
	}
}

