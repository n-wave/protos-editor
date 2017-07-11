package builders;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import dataContainers.ControlChangeData;
import dataContainers.DataStructure;
import dataContainers.DisabledControllerData;
import dataContainers.NoteControlChangeData;
import dataContainers.NoteVelocityData;
import guiComponents.*;

/**
 * @author mario
 *	26-04-2017
 * 
 */
		
	
public class Pressure extends Controller {

	/**
	 * 
	 */

	private String[] optionList = {
								   "Disabled",
								   "Note Velocity",
								   "Note Control Change",
								   "Control Change"
								  };
	
	private int optionIndex = 0; 
	private int numberOfOptions = 4;
	private DataStructure[] data = new DataStructure[4];
	
	public Pressure(String name){
		super(name);
		
		data[0] = new DisabledControllerData();
		data[1] = new NoteVelocityData();
		data[2] = new NoteControlChangeData();
		data[3] = new ControlChangeData();
		
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
	public void setOptionIndex(int optionIndex){
		if(optionIndex >= 0 || optionIndex < optionList.length){
			this.optionIndex = optionIndex;
		} else {
			System.err.println("Index Out of range option not available");
		}
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
	public Composite getGuiComponent(Composite parent, int componentIndex){
		Composite guiComponent = null;
		try{
			switch(componentIndex){
				case 0:
					guiComponent = new DisabledControllerComponent(parent, SWT.NONE, data[0]);
					break;
				case 1:
					guiComponent = new NoteComponent(parent, SWT.NONE, data[1]);
					break;
				case 2:
					guiComponent = new NoteControlChangeComponent(parent, SWT.NONE, data[2]); 
					break;
				case 3:
					guiComponent = new ControlChangeComponent(parent, SWT.NONE, data[3]);
					break;
			}
			
		} catch(Exception e){
			System.err.println("Error ocurred while creating gui Component");
			e.printStackTrace(System.err);
		}
		return guiComponent;
	}
}
