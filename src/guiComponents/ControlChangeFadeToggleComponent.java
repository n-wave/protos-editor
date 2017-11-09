package guiComponents;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Combo;


import dataContainers.ControlChangeFadeToggleData;
import dataContainers.DataStructure;


public class ControlChangeFadeToggleComponent extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	private ControlChangeFadeToggleData controlChangeFadeToggleData;
	
	private Spinner channelSpinner;
	private Combo resolutionCombo;
	private Spinner controlChangeSpinner;
	private Spinner startValueSpinner;
	private Spinner holdValueSpinner;
	private Spinner endValueSpinner;
	private Spinner fadeInSpinner;
	private Spinner fadeOutSpinner;

	private String name = new String("ControlChangeFadeToggleComponent");
	private Label toggleLabel;
	private Combo toggleCombo;
	
	private boolean resolutionChanged = false;
	
	public ControlChangeFadeToggleComponent(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 14, SWT.BOLD));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		initializeChannelSelection();
		initializeToggleSelection();
		initializeResolutionSelection();
		initializeControlChangeNumberSelection();
		initializeStartValueSelection();
		initializeHoldValueSelection();
		initializeEndValueSelection();
		initializeFadeInSelection();
		initializeFadeOutSelection();
		
		initializeListeners();
		
	}

	public ControlChangeFadeToggleComponent(Composite parent, int style, DataStructure data) {
		super(parent, style);
		
		initializeDataStructure(data);
		
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 14, SWT.BOLD));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(2, false));
		
		initializeChannelSelection();
		initializeToggleSelection();
		initializeResolutionSelection();
		initializeControlChangeNumberSelection();
		initializeStartValueSelection();
		initializeHoldValueSelection();
		initializeEndValueSelection();
		initializeFadeInSelection();
		initializeFadeOutSelection();
		
		initializeListeners();
		
		int value = controlChangeFadeToggleData.getResolutionOption();
		setMaximumValues(value);
		//System.out.println(this.toString());
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	private void initializeChannelSelection()
	{
		int channel = controlChangeFadeToggleData.getChannel();
		setLayout(null);
				
		Label ChannelLabel = new Label(this, SWT.NONE);
		ChannelLabel.setBounds(5, 10, 77, 19);
		ChannelLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		ChannelLabel.setText("Channel");
		
		channelSpinner = new Spinner(this, SWT.BORDER);
		channelSpinner.setBounds(131, 5, 106, 29);
		channelSpinner.setMaximum(16);
		channelSpinner.setMinimum(1);
		channelSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		channelSpinner.setSelection(channel);
	}
	
	private void initializeToggleSelection(){
		int toggle = controlChangeFadeToggleData.getToggleOption();
		
		toggleLabel = new Label(this, SWT.NONE);
		toggleLabel.setBounds(294, 10, 69, 19);
		toggleLabel.setToolTipText("Switch Behaviour");
		toggleLabel.setText("Option");
		toggleLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		
		toggleCombo = new Combo(this, SWT.NONE);
		toggleCombo.setBounds(365, 8, 106, 24);
		toggleCombo.setToolTipText("Switch Behaviour");
		toggleCombo.setTextDirection(3355443);
		toggleCombo.setItems(new String[] {"Momentary\t", "Toggle"});
		toggleCombo.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		toggleCombo.select(toggle);
	}
	
	private void initializeResolutionSelection()
	{
		int resolution = controlChangeFadeToggleData.getResolutionOption();
			
		Label resolutionLabel = new Label(this, SWT.NONE);
		resolutionLabel.setBounds(5, 41, 110, 19);
		resolutionLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		resolutionLabel.setText("Resolution");
		
		resolutionCombo = new Combo(this, SWT.NONE);
		resolutionCombo.setBounds(131, 39, 106, 23);
		resolutionCombo.setTextDirection(3355443);
		resolutionCombo.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		resolutionCombo.setItems(new String[] {"7 Bit", "14 Bit"});
		resolutionCombo.select(resolution);
	}
	
	private void initializeControlChangeNumberSelection()
	{
		int number = controlChangeFadeToggleData.getControlChangeNumber();
		
		Label controlChangeLabel = new Label(this, SWT.NONE);
		controlChangeLabel.setBounds(5, 72, 99, 19);
		controlChangeLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		controlChangeLabel.setText("CC Number");
		
		controlChangeSpinner = new Spinner(this, SWT.BORDER);
		controlChangeSpinner.setBounds(131, 67, 106, 29);
		controlChangeSpinner.setMaximum(127);
		controlChangeSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		controlChangeSpinner.setSelection(number);
		
	}
	
	private void initializeStartValueSelection()
	{
		int start = controlChangeFadeToggleData.getStartValue();
		
		Label startValueLabel = new Label(this, SWT.NONE);
		startValueLabel.setBounds(5, 106, 121, 19);
		startValueLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		startValueLabel.setText("Start Value");
		
		startValueSpinner = new Spinner(this, SWT.BORDER);
		startValueSpinner.setBounds(131, 101, 106, 29);
		startValueSpinner.setMaximum(127);
		startValueSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		startValueSpinner.setSelection(start);
	}
	
	private void initializeHoldValueSelection()
	{
		int hold = controlChangeFadeToggleData.getHoldValue();
		
		Label holdValueLabel = new Label(this, SWT.NONE);
		holdValueLabel.setBounds(5, 140, 110, 19);
		holdValueLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		holdValueLabel.setText("Hold Value");
		
		holdValueSpinner = new Spinner(this, SWT.BORDER);
		holdValueSpinner.setBounds(131, 135, 106, 29);
		holdValueSpinner.setMaximum(127);
		holdValueSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		holdValueSpinner.setSelection(hold);
	}	
	
	private void initializeEndValueSelection()
	{	
		int end = controlChangeFadeToggleData.getEndValue();
		
		Label endValueLabel = new Label(this, SWT.NONE);
		endValueLabel.setBounds(5, 174, 99, 19);
		endValueLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		endValueLabel.setText("End Value");
		
		endValueSpinner = new Spinner(this, SWT.BORDER);
		endValueSpinner.setBounds(131, 169, 106, 29);
		endValueSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		endValueSpinner.setMaximum(127);
		endValueSpinner.setSelection(end);
	}

	private void initializeFadeInSelection()
	{
		int fadeIn = controlChangeFadeToggleData.getFadeInValue();
				
		Label fadeInLabel = new Label(this, SWT.NONE);
		fadeInLabel.setBounds(5, 208, 77, 19);
		fadeInLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		fadeInLabel.setText("Fade In");
		
		fadeInSpinner = new Spinner(this, SWT.BORDER);
		fadeInSpinner.setBounds(131, 203, 106, 29);
		fadeInSpinner.setMaximum(2000);
		fadeInSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));		
		fadeInSpinner.setSelection(fadeIn);
	}
	
	private void initializeFadeOutSelection()
	{
		int fadeOut = controlChangeFadeToggleData.getFadeOutValue();
		
		Label fadeOutLabel = new Label(this, SWT.NONE);
		fadeOutLabel.setBounds(5, 242, 88, 19);
		fadeOutLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		fadeOutLabel.setText("Fade Out");
		
		fadeOutSpinner = new Spinner(this, SWT.BORDER);
		fadeOutSpinner.setBounds(131, 237, 106, 29);
		fadeOutSpinner.setMaximum(2000);
		fadeOutSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));	
		fadeOutSpinner.setSelection(fadeOut);

	}
	
	private void setMaximumValues(int value){
		int start = controlChangeFadeToggleData.getStartValue();
		int hold = controlChangeFadeToggleData.getHoldValue();
		int end = controlChangeFadeToggleData.getEndValue();
		
		if(value == 1){			
			startValueSpinner.setMaximum(16383);
			holdValueSpinner.setMaximum(16383);
			endValueSpinner.setMaximum(16383);
			
			if(resolutionChanged){
				start *= 129;
				hold *= 129;
				end *= 129;
			
				resolutionChanged = false;
			}
			
			controlChangeFadeToggleData.setStartValue(start);
			controlChangeFadeToggleData.setHoldValue(hold);
			controlChangeFadeToggleData.setEndValue(end);
			
			startValueSpinner.setSelection(start);
			holdValueSpinner.setSelection(hold);
			endValueSpinner.setSelection(end);
			
		} else {
			startValueSpinner.setMaximum(127);
			holdValueSpinner.setMaximum(127);
			endValueSpinner.setMaximum(127);	
			
			if(resolutionChanged){
				start %= 128;
				hold %= 128;
				end %= 128;
			
				resolutionChanged = false;
			}
		
			controlChangeFadeToggleData.setStartValue(start);
			controlChangeFadeToggleData.setHoldValue(hold);
			controlChangeFadeToggleData.setEndValue(end);
			
			startValueSpinner.setSelection(start);
			holdValueSpinner.setSelection(hold);
			endValueSpinner.setSelection(end);
		}
	}
	
	private void initializeDataStructure(DataStructure data){
		try{
		 if(data instanceof ControlChangeFadeToggleData){
			 controlChangeFadeToggleData = (ControlChangeFadeToggleData)data;
		 }	else {
			 System.err.println("Wrong DataStructure Component suplied");
		 }			
		} catch (Exception e){
			System.err.println("Error ocurred in ControlChangeFadeToggleComponent::setDataStructure");
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
			internalValues = internalValues + "\n" + controlChangeFadeToggleData.toString();
		} catch(Exception e){
			System.err.println();
		}
		
		return internalValues;
	}
	
	
	private void initializeListeners(){
		channelSpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = channelSpinner.getSelection();
				controlChangeFadeToggleData.setChannel(value);
			}
		});
		
		toggleCombo.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = toggleCombo.getSelectionIndex();
				controlChangeFadeToggleData.setToggleOption(value);
			}
		});
		
		resolutionCombo.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = resolutionCombo.getSelectionIndex();
				resolutionChanged = true;
				setMaximumValues(value);
				controlChangeFadeToggleData.setResolutionOption(value);
			}
		});
		
		controlChangeSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = controlChangeSpinner.getSelection();
				controlChangeFadeToggleData.setControlChangeNumber(value);
			}
		});
		
		startValueSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = startValueSpinner.getSelection();
				controlChangeFadeToggleData.setStartValue(value);
			}
		});
		
		holdValueSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = holdValueSpinner.getSelection();
				controlChangeFadeToggleData.setHoldValue(value);
			}
		});
		
		endValueSpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = endValueSpinner.getSelection();
				controlChangeFadeToggleData.setEndValue(value);
			}
		});
		
		fadeInSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = fadeInSpinner.getSelection();
				controlChangeFadeToggleData.setFadeInValue(value);
			}
		});
		
		fadeOutSpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = fadeOutSpinner.getSelection();
				controlChangeFadeToggleData.setFadeOutValue(value);
			}
		});
	}
}
