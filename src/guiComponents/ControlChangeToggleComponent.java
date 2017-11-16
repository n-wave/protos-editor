package guiComponents;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.wb.swt.SWTResourceManager;
import dataContainers.ControlChangeToggleData;
import dataContainers.DataStructure;

public class ControlChangeToggleComponent extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */

	private ControlChangeToggleData ControlChangeToggleData;
	private Label ToggleLabel;
	private Combo toggleCombo;
	
	private Spinner channelSpinner;
	private Combo resolutionCombo;
	private Spinner controlChangeSpinner;
	private Spinner onValueSpinner;
	private Spinner offValueSpinner;
	
	private Label onValueLabel;
	private Label offValueLabel;
	
	private String name = new String("ControlChangeComponent");
	private boolean resolutionChanged = false;

	
	public ControlChangeToggleComponent(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 14, SWT.BOLD));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));

		initializeChannelSelection();
		initializeToggleSelection();
		initializeResolutionSelection();
		initializeControlChangeNumberSelection();
		initializeTopValueSelection();
		initializeBottomValueSelection();
		
		initializeListeners();
	}

	public ControlChangeToggleComponent(Composite parent, int style, DataStructure data) {
		super(parent, style);
		
		initializeDataStructure(data);
		
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 14, SWT.BOLD));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		setLayout(new GridLayout(2, false));

		initializeChannelSelection();
		initializeToggleSelection();
		initializeResolutionSelection();
		initializeControlChangeNumberSelection();
		initializeTopValueSelection();
		initializeBottomValueSelection();
		
		initializeListeners();

		int value = ControlChangeToggleData.getResolutionOption();
		setMaximumValues(value);
		//System.out.println(this.toString());
		
		/** 
		 * set ParentController to acces data[][] structure and be able to 
		 * change the setting store and retrieve them
		 *
		 */
		
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	
	private void initializeChannelSelection()
	{
		int channel = ControlChangeToggleData.getChannel();
		setLayout(null);
		
		Label ChannelLabel = new Label(this, SWT.NONE);
		ChannelLabel.setBounds(5, 10, 77, 19);
		ChannelLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		ChannelLabel.setText("Channel");
	    channelSpinner = new Spinner(this, SWT.BORDER);
	    channelSpinner.setBounds(120, 5, 106, 29);
		channelSpinner.setMaximum(16);
		channelSpinner.setMinimum(1);
		channelSpinner.setSelection(channel);
		channelSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
	}
	
	private void initializeToggleSelection(){
		
		ToggleLabel = new Label(this, SWT.NONE);
		ToggleLabel.setBounds(294, 10, 69, 19);
		ToggleLabel.setText("Option");
		ToggleLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		
		toggleCombo = new Combo(this, SWT.NONE);
		toggleCombo.setBounds(365, 8, 106, 24);
		toggleCombo.setToolTipText("Switch Behaviour");
		toggleCombo.setTextDirection(3355443);
		toggleCombo.setItems(new String[] {"Momentary", "Toggle"});
		toggleCombo.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		toggleCombo.select(0);
	}
	
	private void initializeResolutionSelection()
	{
		int resolution = ControlChangeToggleData.getResolutionOption();
		
		Label resolutionLabel = new Label(this, SWT.NONE);
		resolutionLabel.setBounds(5, 41, 110, 19);
		resolutionLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		resolutionLabel.setText("Resolution");
		
	    resolutionCombo = new Combo(this, SWT.NONE);
	    resolutionCombo.setBounds(120, 39, 107, 23);
		resolutionCombo.setTextDirection(3355443);
		resolutionCombo.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		resolutionCombo.setItems(new String[] {"7 Bit", "14 Bit"});
		resolutionCombo.select(resolution);
	}
	
	private void initializeControlChangeNumberSelection()
	{
		int number = ControlChangeToggleData.getControlChangeNumber();
		
		Label controlChangeLabel = new Label(this, SWT.NONE);
		controlChangeLabel.setBounds(5, 72, 99, 19);
		controlChangeLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		controlChangeLabel.setText("CC Number");
		
	    controlChangeSpinner = new Spinner(this, SWT.BORDER);
	    controlChangeSpinner.setBounds(120, 67, 106, 29);
	    controlChangeSpinner.setMaximum(127);
		controlChangeSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		controlChangeSpinner.setSelection(number);
	}
	
	private void initializeTopValueSelection()
	{
		int onValue = ControlChangeToggleData.getOnValue();
		
	    onValueLabel = new Label(this, SWT.NONE);
	    onValueLabel.setBounds(5, 106, 88, 19);
		onValueLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		onValueLabel.setText("On Value");
		
		onValueSpinner = new Spinner(this, SWT.BORDER);
		onValueSpinner.setBounds(120, 101, 106, 29);
		onValueSpinner.setMaximum(127);
		onValueSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		onValueSpinner.setSelection(onValue);
	}
	
	private void initializeBottomValueSelection()
	{
		int offValue = ControlChangeToggleData.getOffValue();
	
	    offValueLabel = new Label(this, SWT.NONE);
	    offValueLabel.setBounds(5, 140, 99, 19);
		offValueLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		offValueLabel.setText("Off Value");
		
		offValueSpinner = new Spinner(this, SWT.BORDER);
		offValueSpinner.setBounds(120, 135, 106, 29);
		offValueSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		offValueSpinner.setMaximum(127);
		offValueSpinner.setSelection(offValue);
	}
	
	private void setMaximumValues(int value){
		int onValue = ControlChangeToggleData.getOnValue();
		int offValue = ControlChangeToggleData.getOffValue();
		
		if(value == 1){	
			
			if(resolutionChanged){
				onValue *= 129;
				offValue *= 129;
			
				resolutionChanged = false;
			}
			
			ControlChangeToggleData.setOnValue(onValue);
			ControlChangeToggleData.setOffValue(offValue);
			
			onValueSpinner.setSelection(onValue);
			offValueSpinner.setSelection(offValue);
			
		} else {		
			
			if(resolutionChanged){
				onValue %= 128;
				offValue %= 128;
			
				resolutionChanged = false;
			}
			
			ControlChangeToggleData.setOnValue(onValue);
			ControlChangeToggleData.setOffValue(offValue);
			
			onValueSpinner.setSelection(onValue);
			offValueSpinner.setSelection(offValue);
		}
	}

	private void initializeDataStructure(DataStructure data){
		try{
		 if(data instanceof ControlChangeToggleData){
			 ControlChangeToggleData = (ControlChangeToggleData)data;
		 }	else {
			 System.err.println("Wrong DataStructure Object suplied in ControlChangeComponent::initializeDataStructure(DataStructure)");
		 }			
		} catch (Exception e){
			System.err.println("Error ocurred in ControlChangeComponent::initializeDataStructure(DataStructure)");
			e.printStackTrace(System.err);
		}		
	}
	
	@Override 
	public String toString(){
		String internalValues = new String("/** GuiComponent **/" +
											"\n" +
											name + 
											"\n");
			
		try{
			internalValues += "\n" + ControlChangeToggleData.toString();
		} catch(Exception e){
			System.err.println("Error ocurred in ControlChangeComponent");
			e.printStackTrace(System.err);
		}
		
		return internalValues;
	}
	
	
	private void initializeListeners(){
		channelSpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = channelSpinner.getSelection();
				ControlChangeToggleData.setChannel(value);
			}
		});
		
		toggleCombo.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = toggleCombo.getSelectionIndex();
				ControlChangeToggleData.setToggleOption(value);
			}
		});
		
		controlChangeSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = controlChangeSpinner.getSelection();
				ControlChangeToggleData.setControlChangeNumber(value);
			}
		});
		
		resolutionCombo.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = resolutionCombo.getSelectionIndex();
				resolutionChanged = true;
				ControlChangeToggleData.setResolutionOption(value);
			}
		});
		
		onValueSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = onValueSpinner.getSelection();			
				ControlChangeToggleData.setOnValue(value);				
				
			}
		});
		
		offValueSpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = offValueSpinner.getSelection();
				ControlChangeToggleData.setOffValue(value);
			}
		});
	}
}
