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
import org.eclipse.swt.layout.GridData;

import dataContainers.ControlChangeFadeData;
import dataContainers.DataStructure;


public class ControlChangeFadeComponent extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	private ControlChangeFadeData controlChangeFadeData;
	
	private Spinner channelSpinner;
	private Combo resolutionCombo;
	private Spinner controlChangeSpinner;
	private Spinner startValueSpinner;
	private Spinner holdValueSpinner;
	private Spinner endValueSpinner;
	private Spinner fadeInSpinner;
	private Spinner fadeOutSpinner;

	private String name = new String("ControlChangeFadeComponent");
	
	public ControlChangeFadeComponent(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 14, SWT.BOLD));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(2, false));
		
		initializeChannelSelection();
		initializeResolutionSelection();
		initializeControlChangeNumberSelection();
		initializeStartValueSelection();
		initializeHoldValueSelection();
		initializeEndValueSelection();
		initializeFadeInSelection();
		initializeFadeOutSelection();
		
		initializeListeners();
		
	}

	public ControlChangeFadeComponent(Composite parent, int style, DataStructure data) {
		super(parent, style);
		
		initializeDataStructure(data);
		
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 14, SWT.BOLD));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(2, false));
		
		initializeChannelSelection();
		initializeResolutionSelection();
		initializeControlChangeNumberSelection();
		initializeStartValueSelection();
		initializeHoldValueSelection();
		initializeEndValueSelection();
		initializeFadeInSelection();
		initializeFadeOutSelection();
		
		initializeListeners();
		
		int value = controlChangeFadeData.getResolutionOption();
		setMaximumValues(value);
		//System.out.println(this.toString());
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	private void initializeChannelSelection()
	{
		int channel = controlChangeFadeData.getChannel();
				
		Label ChannelLabel = new Label(this, SWT.NONE);
		ChannelLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		ChannelLabel.setText("Channel");
		
		channelSpinner = new Spinner(this, SWT.BORDER);
		GridData gd_channelSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_channelSpinner.widthHint = 100;
		channelSpinner.setLayoutData(gd_channelSpinner);
		channelSpinner.setMaximum(16);
		channelSpinner.setMinimum(1);
		channelSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		channelSpinner.setSelection(channel);
	}
	
	private void initializeResolutionSelection()
	{
		int resolution = controlChangeFadeData.getResolutionOption();
		
		Label resolutionLabel = new Label(this, SWT.NONE);
		resolutionLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		resolutionLabel.setText("Resolution");
		
		resolutionCombo = new Combo(this, SWT.NONE);
		resolutionCombo.setTextDirection(3355443);
		resolutionCombo.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		resolutionCombo.setItems(new String[] {"7 Bit", "14 Bit"});
		GridData gd_resolutionCombo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_resolutionCombo.widthHint = 106;
		resolutionCombo.setLayoutData(gd_resolutionCombo);
		resolutionCombo.select(resolution);
	}
	
	private void initializeControlChangeNumberSelection()
	{
		int number = controlChangeFadeData.getControlChangeNumber();
		
		Label controlChangeLabel = new Label(this, SWT.NONE);
		controlChangeLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		controlChangeLabel.setText("CC Number");
		
		controlChangeSpinner = new Spinner(this, SWT.BORDER);
		GridData gd_controlChangeSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_controlChangeSpinner.widthHint = 100;
		controlChangeSpinner.setLayoutData(gd_controlChangeSpinner);
		controlChangeSpinner.setMaximum(127);
		controlChangeSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		controlChangeSpinner.setSelection(number);
		
	}
	
	private void initializeStartValueSelection()
	{
		int start = controlChangeFadeData.getStartValue();
		
		Label startValueLabel = new Label(this, SWT.NONE);
		startValueLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		startValueLabel.setText("Start Value");
		
		startValueSpinner = new Spinner(this, SWT.BORDER);
		GridData gd_startValueSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_startValueSpinner.widthHint = 100;
		startValueSpinner.setLayoutData(gd_startValueSpinner);
		startValueSpinner.setMaximum(127);
		startValueSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		startValueSpinner.setSelection(start);
	}
	
	private void initializeHoldValueSelection()
	{
		int hold = controlChangeFadeData.getHoldValue();
		
		Label holdValueLabel = new Label(this, SWT.NONE);
		holdValueLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		holdValueLabel.setText("Hold Value");
		
		holdValueSpinner = new Spinner(this, SWT.BORDER);
		GridData gd_holdValueSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_holdValueSpinner.widthHint = 100;
		holdValueSpinner.setLayoutData(gd_holdValueSpinner);
		holdValueSpinner.setMaximum(127);
		holdValueSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		holdValueSpinner.setSelection(hold);
	}	
	
	private void initializeEndValueSelection()
	{	
		int end = controlChangeFadeData.getEndValue();
		
		Label endValueLabel = new Label(this, SWT.NONE);
		endValueLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		endValueLabel.setText("End Value");
		
		endValueSpinner = new Spinner(this, SWT.BORDER);
		GridData gd_endValueSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_endValueSpinner.widthHint = 100;
		endValueSpinner.setLayoutData(gd_endValueSpinner);
		endValueSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		endValueSpinner.setMaximum(127);
		endValueSpinner.setSelection(end);
	}

	private void initializeFadeInSelection()
	{
		int fadeIn = controlChangeFadeData.getFadeInValue();
				
		Label fadeInLabel = new Label(this, SWT.NONE);
		fadeInLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		fadeInLabel.setText("Fade In");
		
		fadeInSpinner = new Spinner(this, SWT.BORDER);
		GridData gd_fadeInSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_fadeInSpinner.widthHint = 100;
		fadeInSpinner.setLayoutData(gd_fadeInSpinner);
		fadeInSpinner.setMaximum(2000);
		fadeInSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));		
		fadeInSpinner.setSelection(fadeIn);
	}
	
	private void initializeFadeOutSelection()
	{
		int fadeOut = controlChangeFadeData.getFadeOutValue();
		
		Label fadeOutLabel = new Label(this, SWT.NONE);
		fadeOutLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		fadeOutLabel.setText("Fade Out");
		
		fadeOutSpinner = new Spinner(this, SWT.BORDER);
		GridData gd_fadeOutSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_fadeOutSpinner.widthHint = 100;
		fadeOutSpinner.setLayoutData(gd_fadeOutSpinner);
		fadeOutSpinner.setMaximum(2000);
		fadeOutSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));	
		fadeOutSpinner.setSelection(fadeOut);

	}
	
	private void setMaximumValues(int value){
		int start = controlChangeFadeData.getStartValue();
		int hold = controlChangeFadeData.getHoldValue();
		int end = controlChangeFadeData.getEndValue();
		
		if(value == 1){			
			startValueSpinner.setMaximum(16383);
			holdValueSpinner.setMaximum(16383);
			endValueSpinner.setMaximum(16383);
			
			start *= 129;
			hold *= 129;
			end *= 129;
			
			controlChangeFadeData.setStartValue(start);
			controlChangeFadeData.setHoldValue(hold);
			controlChangeFadeData.setEndValue(end);
			
			startValueSpinner.setSelection(start);
			holdValueSpinner.setSelection(hold);
			endValueSpinner.setSelection(end);
			
		} else {
			startValueSpinner.setMaximum(127);
			holdValueSpinner.setMaximum(127);
			endValueSpinner.setMaximum(127);	
			
			start %= 128;
			hold %= 128;
			end &= 128;
		
			controlChangeFadeData.setStartValue(start);
			controlChangeFadeData.setHoldValue(hold);
			controlChangeFadeData.setEndValue(end);
			
			startValueSpinner.setSelection(start);
			holdValueSpinner.setSelection(hold);
			endValueSpinner.setSelection(end);
		}
	}
	
	private void initializeDataStructure(DataStructure data){
		try{
		 if(data instanceof ControlChangeFadeData){
			 controlChangeFadeData = (ControlChangeFadeData)data;
		 }	else {
			 System.err.println("Wrong DataStructure Component suplied");
		 }			
		} catch (Exception e){
			System.err.println("Error ocurred in ControlChangeFadeComponent");
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
			internalValues = internalValues + "\n" + controlChangeFadeData.toString();
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
				controlChangeFadeData.setChannel(value);
			}
		});
		
		resolutionCombo.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = resolutionCombo.getSelectionIndex();
				setMaximumValues(value);
				controlChangeFadeData.setResolutionOption(value);
			}
		});
		
		controlChangeSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = controlChangeSpinner.getSelection();
				controlChangeFadeData.setControlChangeNumber(value);
			}
		});
		
		startValueSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = startValueSpinner.getSelection();
				controlChangeFadeData.setStartValue(value);
			}
		});
		
		holdValueSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = holdValueSpinner.getSelection();
				controlChangeFadeData.setHoldValue(value);
			}
		});
		
		endValueSpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = endValueSpinner.getSelection();
				controlChangeFadeData.setEndValue(value);
			}
		});
		
		fadeInSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = fadeInSpinner.getSelection();
				controlChangeFadeData.setFadeInValue(value);
			}
		});
		
		fadeOutSpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = fadeOutSpinner.getSelection();
				controlChangeFadeData.setFadeOutValue(value);
			}
		});
	}
}
