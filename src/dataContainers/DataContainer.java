package dataContainers;

import java.io.Serializable;
import dataContainers.DataStructure;
import builders.Controller;

public class DataContainer implements Serializable {
	
	private static final long serialVersionUID = 4644815646049162554L;
	
	private DataStructure[][] dataStructures;
	private int options[];
	private String name;

	public DataContainer(String name, Controller[] data){
		this.name = name;
		
		allocateDataStructureArray(data);
	}
	
	/**Call after the controller array is successfully initialized in scene Group 
	 * Allocates memory for the array, when successfully allocated. setters for
	 * loading in presets en getters for saving presets.
	 * @param data
	 */
		
	private void allocateDataStructureArray(Controller[] data){
		if(data != null){
			int rowSize = data.length;
			int columnSize = 0;
			
			try{
				/**allocate rows**/
				dataStructures = new DataStructure[rowSize][];
				options = new int[rowSize];
							
				/**allocate columns**/
				for(int i = 0; i < rowSize; i++){
					columnSize = data[i].getNumberOfOptions();				
					dataStructures[i] = new DataStructure[columnSize];
				}						
			} catch (Exception e){
				System.err.println("Error ocurred in DataContainer." +  "\n" +  
								   "Couldn't Allocate Memory");
				e.printStackTrace(System.err);
			}
			

		
		} else {
			System.err.println("Controller hasn't been initialized");
		}
		
	}
	
	/**
	 * DataStructure[] getActiveDataStructures[]
	 * 
	 * Compiles the active DataStructures in the 
	 * DataContainer for a SceneGroup in a single
	 * DataStructure Array. Used for supplying 
	 * the Protocol Parser the data, that will
	 * be uploaded to the device.
	 * 
	 * @return DataStructures
	 * 
	 * 
	 */
	
	public DataStructure[] getActiveDataStructures(){
		int size = dataStructures.length;
		DataStructure tmp[] = new DataStructure[size];
				
		try{
			for(int i=0; i<size; i++){
				int currentOption = options[i];
				tmp[i] = dataStructures[i][currentOption];
			}
				
		} catch(Exception e){
			System.err.println("Error Ocurred in DataStructure[] getActiveDataStructures()");
			e.printStackTrace(System.err);
		}		
		
		return tmp;
	}
	
	public void load(Controller data[]){
		try{
			loadDataStructures(data);
			loadOptions(data);
		} catch(Exception e){
			System.err.println("Error Ocurred in DataContainer load store(Controller data[])");
			e.printStackTrace(System.err);
		}
	}
	
	public void store(Controller data[]){
		try{
			storeDataStructures(data);
			storeOptions(data);
		} catch(Exception e){
			System.err.println("Error Ocurred in DataContainer void store(Controller data[])");
			e.printStackTrace(System.err);
		}
	}
	
	private void loadOptions(Controller data[]){
		try{
			for(int i=0; i<data.length; i++){
				data[i].setOptionIndex(options[i]);
			}
		} catch(Exception e){
			System.err.println("Error Ocurred in DataContainer void loadOptions(Controller data[]");
			e.printStackTrace(System.err);
		}
	}
	
	private void storeOptions(Controller data[]){
		try{
			for(int i=0; i< data.length; i++){
				options[i] = data[i].getOptionIndex();
			}
		} catch(Exception e){
			System.err.println("Error ocurred in DataContainer void storeOptions(Controller data[])");
			e.printStackTrace(System.err);
		}
	}
	
	private void loadDataStructures(Controller data[]){
		try{
			for(int i = 0; i < data.length; i++){
				data[i].setDataStructure(dataStructures[i]);
			}
		} catch(Exception e){
			System.err.println("Error ocured in DataContainer void loadDataStuctures(Controller data[])");
			e.printStackTrace(System.err);
		}
	}
	
	private void storeDataStructures(Controller data[]){
		try{
			for(int i = 0; i < data.length; i++){
				dataStructures[i] = data[i].getDataStructure();
			}
		} catch(Exception e){
			System.err.println("Error Ocurred in DataContainer void storeDataStructures(Controllers data[])");
			e.printStackTrace(System.err);
		}
	}
	
	public String getName(){
		return name;
	}
	
	//**contenate string as double check which values are saved in the overall preset **//
	
}
