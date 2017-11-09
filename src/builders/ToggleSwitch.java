package builders;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import dataContainers.ControlChangeFadeToggleData;
import dataContainers.ControlChangeToggleData;
import dataContainers.DataStructure;
import dataContainers.DisabledControllerData;
import dataContainers.NoteControlChangeToggleData;
import dataContainers.NoteVelocityToggleData;
import dataContainers.ProgramChangeData;
import guiComponents.ControlChangeFadeToggleComponent;
import guiComponents.ControlChangeToggleComponent;
import guiComponents.DisabledControllerComponent;
import guiComponents.NoteControlChangeToggleComponent;
import guiComponents.NoteToggleComponent;
import guiComponents.ProgramChangeComponent;

public class ToggleSwitch extends Controller {

	private String[] optionList = {
			   						"Disabled",
			   						"Note Velocity", 		//Note Velocity Toggle
			   						"Control Change",		//Control Change Toggle
			   						"Control Change Fade",
			   						"Note Control Change",
			   						"Program Change"
			   					  };
	
	private int optionIndex = 0; 
	private int numberOfOptions = 6;
	
	private DataStructure[] data = new DataStructure[6];
	
	public ToggleSwitch(String name) {
		super(name);
		
		data[0] = new DisabledControllerData();
		data[1] = new NoteVelocityToggleData();
		data[2] = new ControlChangeToggleData();
		data[3] = new ControlChangeFadeToggleData();
		data[4] = new NoteControlChangeToggleData();
		data[5] = new ProgramChangeData();
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getNumberOfOptions() {
		// TODO Auto-generated method stub
		return numberOfOptions;
	}

	@Override
	public int getOptionIndex() {
		// TODO Auto-generated method stub
		return optionIndex;
	}

	@Override
	public void setOptionIndex(int optionIndex) {
		if(optionIndex >= 0 && optionIndex < numberOfOptions){
			this.optionIndex = optionIndex;
		} else {
			System.err.println("Error Ocurred in ToggleSwitch");
			System.err.println("Option index outside of range");
		}
	}

	@Override
	public String getName() {
			return name;
	}

	@Override
	public String[] getOptionList() {
		// TODO Auto-generated method stub
		return optionList;
	}

	@Override
	public void setDataStructure(DataStructure[] data) {
		try{
			this.data = data;			
		} catch(Exception e){
			System.err.println("Error Ocurred in ToggleSwitch::setDataStructure");
			e.printStackTrace(System.err);
		}
	}

	@Override
	public DataStructure[] getDataStructure() {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public Composite getGuiComponent(Composite parent, int componentIndex) {
		Composite guiComponent = null;
		
		try{
			switch(componentIndex){
				case 0: 
					guiComponent = new DisabledControllerComponent(parent, SWT.NONE, data[0]);
					break;
				case 1: 
					guiComponent = new NoteToggleComponent(parent, SWT.NONE, data[1]); 
					break;
				case 2:
					guiComponent = new ControlChangeToggleComponent(parent, SWT.NONE, data[2]);
					break;
				case 3:
					guiComponent = new ControlChangeFadeToggleComponent(parent, SWT.NONE, data[3]);
					break;
				case 4:
					guiComponent = new NoteControlChangeToggleComponent(parent, SWT.NONE, data[4]);
					break;
				case 5:
					guiComponent = new ProgramChangeComponent(parent, SWT.NONE, data[5]);
					break;
			}
		} catch(Exception e){
			System.err.println("Error ocurred in ToggleSwitch");
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
