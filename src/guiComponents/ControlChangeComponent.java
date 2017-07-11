package guiComponents;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.wb.swt.SWTResourceManager;
import dataContainers.ControlChangeData;
import dataContainers.DataStructure;

public class ControlChangeComponent extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */

	private ControlChangeData controlChangeData;
	
	private Spinner channelSpinner;
	private Combo resolutionCombo;
	private Spinner controlChangeSpinner;
	private Spinner topValueSpinner;
	private Spinner bottomValueSpinner;
	
	private Label topValueLabel;
	private Label bottomValueLabel;
	
	private String name = new String("ControlChangeComponent");
	
	public ControlChangeComponent(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 14, SWT.BOLD));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		setLayout(new GridLayout(2, false));

		initializeChannelSelection();
		initializeResolutionSelection();
		initializeControlChangeNumberSelection();
		initializeTopValueSelection();
		initializeBottomValueSelection();
		
		initializeListeners();
	}

	public ControlChangeComponent(Composite parent, int style, DataStructure data) {
		super(parent, style);
		
		initializeDataStructure(data);
		
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 14, SWT.BOLD));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		setLayout(new GridLayout(2, false));

		initializeChannelSelection();
		initializeResolutionSelection();
		initializeControlChangeNumberSelection();
		initializeTopValueSelection();
		initializeBottomValueSelection();
		
		initializeListeners();

		int value = controlChangeData.getResolutionOption();
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
		int channel = controlChangeData.getChannel();
		
		Label ChannelLabel = new Label(this, SWT.NONE);
		ChannelLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		ChannelLabel.setText("Channel");
	    channelSpinner = new Spinner(this, SWT.BORDER);
	    GridData gd_channelSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
	    gd_channelSpinner.widthHint = 100;
	    channelSpinner.setLayoutData(gd_channelSpinner);
		channelSpinner.setMaximum(16);
		channelSpinner.setMinimum(1);
		channelSpinner.setSelection(channel);
		channelSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
	}
	
	private void initializeResolutionSelection()
	{
		int resolution = controlChangeData.getResolutionOption();
		
		Label resolutionLabel = new Label(this, SWT.NONE);
		resolutionLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		resolutionLabel.setText("Resolution");
		
	    resolutionCombo = new Combo(this, SWT.NONE);
		resolutionCombo.setTextDirection(3355443);
		resolutionCombo.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		resolutionCombo.setItems(new String[] {"7 Bit", "14 Bit"});
		resolutionCombo.select(resolution);		
		
		GridData gd_resolutionCombo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_resolutionCombo.widthHint = 106;
		resolutionCombo.setLayoutData(gd_resolutionCombo);
	}
	
	private void initializeControlChangeNumberSelection()
	{
		int number = controlChangeData.getControlChangeNumber();
		
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
	
	private void initializeTopValueSelection()
	{
		int topValue = controlChangeData.getTopValue();
		
	    topValueLabel = new Label(this, SWT.NONE);
		topValueLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		topValueLabel.setText("Top Value");
		
		topValueSpinner = new Spinner(this, SWT.BORDER);
		GridData gd_topValueSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_topValueSpinner.widthHint = 100;
		topValueSpinner.setLayoutData(gd_topValueSpinner);
		topValueSpinner.setMaximum(127);
		topValueSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		topValueSpinner.setSelection(topValue);
	}
	
	private void initializeBottomValueSelection()
	{	int bottomValue = controlChangeData.getBottomValue();
	
	    bottomValueLabel = new Label(this, SWT.NONE);
		bottomValueLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		bottomValueLabel.setText("Bottom Value");
		
		bottomValueSpinner = new Spinner(this, SWT.BORDER);
		GridData gd_bottomValueSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_bottomValueSpinner.widthHint = 100;
		bottomValueSpinner.setLayoutData(gd_bottomValueSpinner);
		bottomValueSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		bottomValueSpinner.setMaximum(127);
		bottomValueSpinner.setSelection(bottomValue);
	}
	
	public void changeLabelText(String labelOne, String labelTwo){
		topValueLabel.setText(labelOne);
		bottomValueLabel.setText(labelTwo);
	}
	
	private void setMaximumValues(int value){
		int top = controlChangeData.getTopValue();
		int bottom = controlChangeData.getBottomValue();
		
		if(value == 1){	
			
			top *= 129;
			bottom *= 129;
			
			topValueSpinner.setMaximum(16383);
			topValueSpinner.setMinimum(bottom+1);
			
			bottomValueSpinner.setMaximum(top-1);
			
			controlChangeData.setTopValue(top);
			controlChangeData.setBottomValue(bottom);
			
			topValueSpinner.setSelection(top);
			bottomValueSpinner.setSelection(bottom);
			
		} else {		
			top %= 128;
			bottom %= 128;
			
			topValueSpinner.setMaximum(127);
			
			topValueSpinner.setMinimum(bottom+1);		
			bottomValueSpinner.setMaximum(top-1);
			
			controlChangeData.setTopValue(top);
			controlChangeData.setBottomValue(bottom);
			
			topValueSpinner.setSelection(top);
			bottomValueSpinner.setSelection(bottom);
		}
	}

	private void initializeDataStructure(DataStructure data){
		try{
		 if(data instanceof ControlChangeData){
			 controlChangeData = (ControlChangeData)data;
		 }	else {
			 System.err.println("Wrong DataStructure Object suplied in ControlChangeComponent::initializeDataStructure(DataStructure)");
		 }			
		} catch (Exception e){
			System.err.println("Error ocurred in ControlChangeComponent::initializeDataStructure(DataStructure)");
			e.printStackTrace(System.err);
		}		
	}
	/** 
	 * 
	 * channelSpinner
	 * controlChangeSpinner
	 * resolutionCombo
	 * topValueSpinner
	 * bottomValueSpinner
	 * 
	 */

	
	@Override 
	public String toString(){
		String internalValues = new String("/** GuiComponent **/" +
											"\n" +
											name + 
											"\n");
			
		try{
			internalValues += "\n" + controlChangeData.toString();
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
				controlChangeData.setChannel(value);
			}
		});
		
		controlChangeSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = controlChangeSpinner.getSelection();
				controlChangeData.setControlChangeNumber(value);
			}
		});
		
		resolutionCombo.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = resolutionCombo.getSelectionIndex();
				setMaximumValues(value);
				controlChangeData.setResolutionOption(value);
			}
		});
		
		topValueSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = topValueSpinner.getSelection();			
				controlChangeData.setTopValue(value);				
				bottomValueSpinner.setMaximum(value-1);
				
			}
		});
		
		bottomValueSpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = bottomValueSpinner.getSelection();
				controlChangeData.setBottomValue(value);
				topValueSpinner.setMinimum(value+1);
			}
		});
	}
}
