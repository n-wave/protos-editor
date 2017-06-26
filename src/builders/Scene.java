package builders;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import dataContainers.DataStructure;
import dataContainers.SceneData;
import guiComponents.SceneComponent;

public class Scene extends Controller {
	
	
	private DataStructure[] sceneData = {new SceneData()};
	
	private String[]optionList = {"No program change",
								  "One Channel",
								  "Two Channels",
								  "Three Channels",
								  "Four Channels"
								 };
	
	int optionIndex = 0;
	int numberOfOptions = 5;
	
	public Scene(String name) {
		super(name);

	}

	@Override
	public void setDataStructure(DataStructure[] data) {
		try{
			sceneData = data;
		} catch (Exception e){
			System.err.println("Error ocurred setting DataStructure");
			e.printStackTrace(System.err);
		}
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
		// TODO Auto-generated method stub
		this.optionIndex = optionIndex;
	}

	@Override
	public Composite getGuiComponent(Composite parent, int componentIndex) {
		SceneComponent sceneComponent = null;
		optionIndex = componentIndex;
		
		
		try{
			sceneComponent = new SceneComponent(parent, SWT.NONE, sceneData[0]);
			sceneComponent.enableProgramChangeBlock(optionIndex);
			sceneComponent.setOption(optionIndex);
			
		} catch (Exception e){
			System.err.println("Error ocurred while creating Scene Component");
			e.printStackTrace(System.err);
		}
		// TODO Auto-generated method stub
		return sceneComponent;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String[] getOptionList() {
		return optionList;
	}

	@Override
	public DataStructure[] getDataStructure() {
		return sceneData;
	}

}
