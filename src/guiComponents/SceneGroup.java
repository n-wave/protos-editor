package guiComponents;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
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
	private Rectangle compositeBounds;
	private Label switchLabel;
	
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
		separatorLabel.setBounds(10, 24, 798, 18);
		separatorLabel.setText("line");
		
		compositeBounds = new Rectangle(146, 35, 490, 355);

		
	    composite = new Composite(this, SWT.NONE);
	    composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		composite.setBounds(compositeBounds);
		
		switchLabel = new Label(this, SWT.NONE);
		switchLabel.setText("Switch");
		switchLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		switchLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.NORMAL));
		switchLabel.setBounds(440, 12, 160, 18);
		switchLabel.setVisible(false);
		
		//System.out.println(this.toString());
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
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
		controlLabel.setBounds(10, 12, 97, 18);
		controlLabel.setText("Controls");
		
		activeControlLabel = new Label(this, SWT.NONE);
		activeControlLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		activeControlLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.NORMAL));
		activeControlLabel.setBounds(154, 12, 280, 18);
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
		actionCombo.setBounds(637, 42, 171, 26);
		//actionCombo.setItems();
		//actionCombo.remove(0);
		
		Label actionLabel = new Label(this, SWT.NONE);
		actionLabel.setText("Action");
		actionLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		actionLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.NORMAL));
		actionLabel.setBounds(637, 12, 97, 18);
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
			          activeControlLabel.setText(selected[i] + " Parameters");		          
			     }		
				 
				 redrawGuiComponent();				
			}
		});
		
		actionCombo.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event){
				int selectedIndex = actionCombo.getSelectionIndex();			

				//System.out.println("Selection Ocurred");							
				controllerArray[currentIndex].setOptionIndex(selectedIndex);
					
				redrawGuiComponent();			
			}									
		});
	}

	public void redrawGuiComponent(){
		try{
			
			 String[] tmpOptionList = controllerArray[currentIndex].getOptionList();
			 int tmpCurrentOption = controllerArray[currentIndex].getOptionIndex();
			 
			 composite.dispose();
			 composite = controllerArray[currentIndex].getGuiComponent(thisObject, tmpCurrentOption);
			 composite.setBounds(compositeBounds);
			 composite.redraw();
			 
			 
			 actionCombo.setItems(tmpOptionList);
			 actionCombo.select(tmpCurrentOption);	
			 
			 if(currentIndex == 27 || currentIndex == 28){
				 if(tmpCurrentOption >= 1 && tmpCurrentOption < 4){
					 switchLabel.setVisible(true);
				 } else {
					 switchLabel.setVisible(false);
				 }
			 }
				 
		} catch(Exception e){
			System.err.println("Error Ocurred while redrawing Gui Component");
			e.printStackTrace(System.err);
		}
	}
	
	
	private void initializeControllerArray(){
		try{
			controllerArray[0] = new Scene("Scene");

			controllerArray[1] = new PotentioMeter("Head Pot 1");
			controllerArray[2] = new PotentioMeter("Head Pot 2");
			controllerArray[3] = new PotentioMeter("Head Pot 3");
			controllerArray[4] = new PotentioMeter("Head Pot 4");
			controllerArray[5] = new PotentioMeter("Head Pot 5");
			controllerArray[6] = new PotentioMeter("Head Pot 6");
	
			controllerArray[7] = new RibbonController("Ribbon 1");
			controllerArray[8] = new RibbonController("Ribbon 2");

			controllerArray[9] = new Pressure("Pressure 1");
			controllerArray[10] = new Pressure("Pressure 2");
			controllerArray[11] = new Pressure("Pressure 3");
			controllerArray[12] = new PotentioMeter("Body Pot 1");
			controllerArray[13] = new PotentioMeter("Body Pot 2");
			controllerArray[14] = new PotentioMeter("Body Pot 3");
			controllerArray[15] = new PotentioMeter("Joy Stick X");
			controllerArray[16] = new PotentioMeter("Joy Stick Y");
			
			controllerArray[17] = new Switch("Neck Switch 1");
			controllerArray[18] = new Switch("Neck Switch 2");
			controllerArray[19] = new Switch("Neck Switch 3");
			controllerArray[20] = new Switch("Neck Switch 4");
			controllerArray[21] = new Switch("Neck Switch 5");
			controllerArray[22] = new Switch("Neck Switch 6");
			
			controllerArray[23] = new Switch("Tab Up Switch");
			controllerArray[24] = new Switch("Tab Down Switch");
			controllerArray[25] = new Switch("Start Switch");
			controllerArray[26] = new Switch("Select Switch");
			controllerArray[27] = new ToggleSwitch("Pot Switch");
			controllerArray[28] = new ToggleSwitch("joy Stick Switch");
			
			for(int i=0; i<controllerArray.length; i++){
				controls[i] = new String(controllerArray[i].getName());
			}
			
				
		} catch(Exception e){
			System.err.println("Error ocurred");
			e.printStackTrace(System.err);
		}
	}
}

