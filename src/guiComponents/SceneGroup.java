package guiComponents;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.wb.swt.SWTResourceManager;

import builders.*;
import dataContainers.DataContainer;

import org.eclipse.swt.widgets.Combo;

public class SceneGroup extends Group {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	final private int numberOfControls = 29;
	
	private DataContainer data;
	
	private Controller controllerArray[] = new Controller[numberOfControls];
	private String controls[] = new String[numberOfControls];
		
	private int currentIndex = 0; //Active Gui Component

	private Combo actionCombo;
	private List controlList;
	private Label activeControlLabel;
	
	private String name;
	private Composite thisObject; /** Object Reference for callback listeners **/
	private Composite composite;
	
	
	public SceneGroup(Composite parent, String name) {
		super(parent, SWT.NONE);
		
		this.name = name;
		thisObject = this;
		
		/** initialize Controllers **/
		initializeControllerArray();
		/** initialize DataContainer **/
		data = new DataContainer(new String(this.name + " : Data"),controllerArray);
		
		/** initialize DataContainer
		 *  this allocates the memory 
		 *  for the DataStructures 
		 *  inside the Controllers
		 */
			
		/** initialize Gui Components **/
		setBounds(10, 10, 680, 420);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		
		initializeListSelection();
		initializeComboSelection();
		
		initializeListeners();
		
		/** 
		 *  Initialize Composite Used as 
		 *  GuiComponent Container
		 */
		
		Label separatorLabel = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		separatorLabel.setBounds(10, 22, 758, 18);
		separatorLabel.setText("line");
		
	    composite = new Composite(this, SWT.NONE);
	    composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		composite.setBounds(146, 42, 450, 354);
		
		//System.out.println(this.toString());
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	/**
	 *  Redraw Screen when a new Gui is selected 
	 */
	
	public void redrawGuiComponent(){
		try{
			 String[] tmpOptionList = controllerArray[currentIndex].getOptionList();
			 int tmpCurrentOption = controllerArray[currentIndex].getOptionIndex();
			 
			 composite.dispose();
			 composite = controllerArray[currentIndex].getGuiComponent(thisObject, tmpCurrentOption);
			 composite.setBounds(146, 34, 450, 280);
			 composite.redraw();
			 
			 
			 actionCombo.setItems(tmpOptionList);
			 actionCombo.select(tmpCurrentOption);	
		} catch(Exception e){
			System.err.println("Error Ocurred while redrawing Gui Component");
			e.printStackTrace(System.err);
		}
	}
	
	/**
	 *  OptionList for all the controllers 
	 * 	Individual controllers can be 
	 *  selected from this list.
	 * 
	 */
	
	private void initializeListSelection() {
		controlList = new List(this, SWT.BORDER | SWT.V_SCROLL);
		controlList.setToolTipText("Select Control");
		controlList.setItems(controls);
		controlList.setBounds(10, 42, 130, 354);
		
		Label controlLabel = new Label(this, SWT.NONE);
		controlLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.NORMAL));
		controlLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		controlLabel.setBounds(10, 10, 97, 18);
		controlLabel.setText("Controls");
		
		activeControlLabel = new Label(this, SWT.NONE);
		activeControlLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		activeControlLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.NORMAL));
		activeControlLabel.setBounds(154, 10, 223, 18);
		activeControlLabel.setText("Undefined");		
	}
	
	/**
	 *  Actions for the specific controller 
	 *  can be selected here
	 */
	
	
	private void initializeComboSelection(){
		actionCombo = new Combo(this, SWT.NONE);
		actionCombo.setFont(SWTResourceManager.getFont("Noto Mono", 9, SWT.NORMAL));
		actionCombo.setToolTipText("Select Action");
		actionCombo.setBounds(586, 42, 182, 26);
		//actionCombo.setItems();
		//actionCombo.remove(0);
		
		Label actionLabel = new Label(this, SWT.NONE);
		actionLabel.setText("Action");
		actionLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		actionLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.NORMAL));
		actionLabel.setBounds(586, 10, 97, 18);
	}
	
	public DataContainer getDataContainer(){
		try{
			data.store(controllerArray);
		} catch(Exception e){
			System.err.println("Error Ocurred in SceneGroup DataContainer getDataContainer()");
			e.printStackTrace(System.err);
		}
		
		return data;
	}
	
	public void setDataContainer(DataContainer data){
		try{
			this.data = data;
			data.load(controllerArray);
			redrawGuiComponent();
		} catch(Exception e){
			System.err.println("Error Ocurred in SceneGroup DataContainer setDataContainer(DataContainer data)");
			e.printStackTrace(System.err);
		}
	}
	
	@Override
	public String toString(){
		String internalValues = new String();
		
		try{
			for(int i = 0; i < controllerArray.length; i++){
				internalValues += controllerArray[i].toString() + "\n";
			}
			
		} catch(Exception e){
			System.err.println("Error ocurred in SceneGroup");
			e.printStackTrace(System.err);
		}
		
		return internalValues;
	}
	
	private void initializeListeners(){
	
		/* 		
		 * 		Initialize List selection Listener 
		 * 		Changes Label based on selection
		 * 		Updates options in Combo Box
		 *  
		 */	
		controlList.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event){
				String selected[] = controlList.getSelection();
				int index[] = controlList.getSelectionIndices();
				
				 for (int i = 0; i < index.length; i++) {
			          currentIndex = index[i];			        
			          activeControlLabel.setText(selected[i] + " Options");		          
			     }		
				
				 redrawGuiComponent();				
			}
		});
		
		actionCombo.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event){
				int selectedIndex = actionCombo.getSelectionIndex();			

				//System.out.println("Selection Ocurred");							
				controllerArray[currentIndex].setOptionIndex(selectedIndex);
					
				
				/** 
				  *  Get gui component from the Controller object 	
				  *	as soon as one of the options change
				  * Object Reference holds the reference of 
				  * the parent class
				  *
				  */
				
					try{
							composite.dispose(); //free memory 
							composite = controllerArray[currentIndex].getGuiComponent(thisObject, selectedIndex);
							composite.setBounds(146, 34, 450, 300);
							composite.redraw();
					
						} 
					 catch (Exception e) {
						System.err.println("Error ocurred");
						e.printStackTrace(System.err);
					}
					//System.out.println("Current Option Index " + selectedIndex);
				}										
		});
	}


	
	
	private void initializeControllerArray(){
		try{
			controllerArray[0] = new Scene("Scene");
			controllerArray[1] = new RibbonController("Ribbon 1");
			controllerArray[2] = new RibbonController("Ribbon 2");
			controllerArray[3] = new PotentioMeter("Head Pot 1");
			controllerArray[4] = new PotentioMeter("Head Pot 2");
			controllerArray[5] = new PotentioMeter("Head Pot 3");
			controllerArray[6] = new PotentioMeter("Head Pot 4");
			controllerArray[7] = new PotentioMeter("Head Pot 5");
			controllerArray[8] = new PotentioMeter("Head Pot 6");
			controllerArray[9] = new PotentioMeter("Body Pot 1");
			controllerArray[10] = new PotentioMeter("Body Pot 2");
			controllerArray[11] = new PotentioMeter("Tremelo Pot");
			controllerArray[12] = new Pressure("Pressure 1");
			controllerArray[13] = new Pressure("Pressure 2");
			controllerArray[14] = new Pressure("Pressure 3");
			controllerArray[15] = new PotentioMeter("Joystick X");
			controllerArray[16] = new PotentioMeter("Joystick Y");
			controllerArray[17] = new Switch("Neck Switch 1");
			controllerArray[18] = new Switch("Neck Switch 2");
			controllerArray[19] = new Switch("Neck Switch 3");
			controllerArray[20] = new Switch("Neck Switch 4");
			controllerArray[21] = new Switch("Neck Switch 5");
			controllerArray[22] = new Switch("Neck Switch 6");
			controllerArray[23] = new Switch("Tab Switch 1");
			controllerArray[24] = new Switch("Tab Switch 2");
			controllerArray[25] = new Switch("Start Switch");
			controllerArray[26] = new Switch("Select Switch");
			controllerArray[27] = new Switch("Foot Switch");
			controllerArray[28] = new Switch("Joystick Switch");
			
			for(int i=0; i<controllerArray.length; i++){
				controls[i] = new String(controllerArray[i].getName());
			}
			
				
		} catch(Exception e){
			System.err.println("Error ocurred");
			e.printStackTrace(System.err);
		}
	}

}

