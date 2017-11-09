package builders;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import dataContainers.ControlChangeData;
import dataContainers.ControlChangeFadeData;
import dataContainers.DataStructure;
import dataContainers.DisabledControllerData;
import dataContainers.NoteVelocityData;
import dataContainers.ProgramChangeData;
import guiComponents.*;

/**
 * @author mario
 *	26-04-2017
 *
 * Option ID's use for parsing 
 * and handling data in Teensy 3.1
 * 
 * Note Velocity 	 0xE1
 * Note and CC		 0xE2
 * PitchBend		 0xE3
 * PitchBendNote	 0xE4
 * ControlChange 	 0xE5
 * ControlChangeFade 0xE6
 * ProgramChange	 0xE7
 * 
 */
	
public class Switch extends Controller {

	private String[] optionList = {
								   "Disabled",
								   "Note Velocity", 
								   "Control Change",
								   "Control Change Fade", 
								   "Program Change"
								   };
	
	/* used for distinguishing gui
	 *  components and later for 
	 *  parsing data that is to 
	 *  be sent to the teensy
	 */
	private int optionIndex = 0; 
	private int numberOfOptions = 5;
	
	private DataStructure[] data = new DataStructure[5];
	
	public Switch(String name){
		super(name);
		
		data[0] = new DisabledControllerData();
		data[1] = new NoteVelocityData();
		data[2] = new ControlChangeData();
		data[3] = new ControlChangeFadeData();
		data[4] = new ProgramChangeData();
		
		//System.out.println(this.toString());
	}
	
	@Override
	public void setDataStructure(DataStructure data[]){
		try{
			this.data = data;
		} catch (Exception e){
			System.err.println("Error ocurred in Switch::setDataStructure");
			System.err.println("while setting DataStructure");
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
	public Composite getGuiComponent(Composite parent, int componentIndex){
		Composite guiComponent = null;
		
		try{
			switch(componentIndex){
				case 0:
					guiComponent = new DisabledControllerComponent(parent, SWT.NONE, data[0]);
					break;
				case 1:
					NoteComponent noteComponent = new NoteComponent(parent, SWT.NONE, data[1]);
					noteComponent.enableLinkOption(false);
					guiComponent = noteComponent;			
					break;
				case 2:
					ControlChangeComponent controlChangeComponent = new ControlChangeComponent(parent, SWT.NONE, data[2]);
					controlChangeComponent.changeLabelText("On Value", "Off Value");

					guiComponent = controlChangeComponent;						
					break;
				case 3:
					guiComponent = new ControlChangeFadeComponent(parent, SWT.NONE, data[3]);
					break;
				case 4:
					guiComponent = new ProgramChangeComponent(parent, SWT.NONE, data[4]);
					break;
				
			}
		} catch(Exception e) {
			System.err.println("Error ocurred while creating gui component");
			e.printStackTrace(System.err);
		}	
		return guiComponent;
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
				System.err.println("Error ocurred in Switch");
				e.printStackTrace(System.err);
		}
				
		internalValues += dataString;
		
		return internalValues;
	}
}
