package guiComponents;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import dataContainers.DataStructure;
import dataContainers.NoteControlChangeData;
import helpers.MidiPitchConverter;


public class NoteControlChangeComponent extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	private Spinner channelSpinner;
	private Spinner pitchSpinner;
	private Spinner velocitySpinner;
	
	private Combo resolutionCombo;
	private Spinner controlChangeSpinner;
	private Spinner topValueSpinner;
	private Spinner bottomValueSpinner;
	
	private Label activePitchLabel;
	private Button velocityCheckButton;
	
	private NoteControlChangeData noteControlChangeData = new NoteControlChangeData();
	private MidiPitchConverter midiPitchConverter = new MidiPitchConverter();
	
	String name = new String("NoteControlChangeComponent");
	
	public NoteControlChangeComponent(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(3, false));
		
		initializeChannelSelection();
		initializePitchSelection();
		initializeVelocitySelection();
		initializeResolutionSelection();
		initializeControlChangeNumberSelection();
		initializeTopValueSelection();
		initializeBottomValueSelection();
		
		initializeListeners();
	}

	
	public NoteControlChangeComponent(Composite parent, int style, DataStructure data) {
		super(parent, style);
		
		initializeDataStructure(data);

		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(3, false));
		
		initializeChannelSelection();
		initializePitchSelection();
		initializeVelocitySelection();
		initializeResolutionSelection();
		initializeControlChangeNumberSelection();
		initializeTopValueSelection();
		initializeBottomValueSelection();
		
		initializeListeners();
		
		//Set MaximumValues
		 int value = noteControlChangeData.getResolutionOption();
		 setMaximumValues(value);
		//System.out.println(this.toString());
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	private void initializeChannelSelection(){
		//NoteComponent	
		int channel = noteControlChangeData.getChannel();
		
		Label channelLabel = new Label(this, SWT.NONE);
		channelLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		channelLabel.setText("Channel");

		channelSpinner = new Spinner(this, SWT.BORDER);
		GridData gd_channelSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_channelSpinner.widthHint = 100;
		channelSpinner.setLayoutData(gd_channelSpinner);
		channelSpinner.setMaximum(16);
		channelSpinner.setMinimum(1);
		channelSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		channelSpinner.setSelection(channel);
	}
	
	private void initializePitchSelection(){
		int pitch = noteControlChangeData.getPitch();
		String note = midiPitchConverter.convertPitch(pitch);
		
		new Label(this, SWT.NONE);
		Label pitchLabel = new Label(this, SWT.NONE);
		pitchLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		pitchLabel.setText("Pitch");
		
		pitchSpinner = new Spinner(this, SWT.BORDER);
		GridData gd_pitchSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_pitchSpinner.widthHint = 100;
		pitchSpinner.setLayoutData(gd_pitchSpinner);
		pitchSpinner.setMaximum(127);
		pitchSpinner.setSelection(pitch);
		pitchSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));

		activePitchLabel = new Label(this, SWT.NONE);
		activePitchLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		activePitchLabel.setText("----");	
		activePitchLabel.setText(note);	
	}
	
	private void initializeVelocitySelection(){
		int velocity = noteControlChangeData.getVelocity();
		boolean option = noteControlChangeData.getVelocityOption();
		
		Label lblLinkVelocity = new Label(this, SWT.NONE);
		lblLinkVelocity.setText("Velocity");
		lblLinkVelocity.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
							
		velocitySpinner = new Spinner(this, SWT.BORDER);
		GridData gd_velocitySpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_velocitySpinner.widthHint = 100;
		velocitySpinner.setLayoutData(gd_velocitySpinner);
		velocitySpinner.setMaximum(127);
		velocitySpinner.setSelection(velocity);
		velocitySpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		
		velocityCheckButton = new Button(this, SWT.CHECK);
		velocityCheckButton.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		velocityCheckButton.setToolTipText("Static Velocity/Variable Velocity");
		velocityCheckButton.setSelection(option);
		
	}
	
	private void initializeResolutionSelection()
	{
		int resolution = noteControlChangeData.getResolutionOption();
		
		
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
		new Label(this, SWT.NONE);
	}
	
	private void initializeControlChangeNumberSelection()
	{
		int number = noteControlChangeData.getControlChangeNumber();
		
		Label controlChangeLabel = new Label(this, SWT.NONE);
		controlChangeLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		controlChangeLabel.setText("CC Number");
		
		controlChangeSpinner = new Spinner(this, SWT.BORDER);
		GridData gd_controlChangeSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_controlChangeSpinner.widthHint = 100;
		controlChangeSpinner.setLayoutData(gd_controlChangeSpinner);
		controlChangeSpinner.setMaximum(127);
		controlChangeSpinner.setSelection(number);
		controlChangeSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		new Label(this, SWT.NONE);
	}
	
	private void initializeTopValueSelection()
	{
		int top = noteControlChangeData.getTopValue();
		
		Label topValueLabel = new Label(this, SWT.NONE);
		topValueLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		topValueLabel.setText("Top Value");
		
		topValueSpinner = new Spinner(this, SWT.BORDER);
		GridData gd_topValueSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_topValueSpinner.widthHint = 100;
		topValueSpinner.setLayoutData(gd_topValueSpinner);
		topValueSpinner.setMaximum(127);
		topValueSpinner.setSelection(top);
		topValueSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		new Label(this, SWT.NONE);
	}
	
	private void initializeBottomValueSelection()
	{
		int bottom = noteControlChangeData.getBottomValue();
		
		Label bottomValueLabel = new Label(this, SWT.NONE);
		bottomValueLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		bottomValueLabel.setText("Bottom Value");
		
		bottomValueSpinner = new Spinner(this, SWT.BORDER);
		GridData gd_bottomValueSpinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_bottomValueSpinner.widthHint = 100;
		bottomValueSpinner.setLayoutData(gd_bottomValueSpinner);
		bottomValueSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		bottomValueSpinner.setMaximum(127);
		bottomValueSpinner.setSelection(bottom);
		new Label(this, SWT.NONE);
	}
	
	private void setMaximumValues(int value){
		int top = noteControlChangeData.getTopValue();
		int bottom = noteControlChangeData.getBottomValue();
		
		if(value == 1){
	
			top *= 129;
			bottom *= 129;
			
			topValueSpinner.setMaximum(16383);
			topValueSpinner.setMinimum(bottom+1);
			
			bottomValueSpinner.setMaximum(top-1);
			
			noteControlChangeData.setTopValue(top);
			noteControlChangeData.setBottomValue(bottom);
			
			topValueSpinner.setSelection(top);
			bottomValueSpinner.setSelection(bottom);
		} else {
			top %= 128;
			bottom %= 128; 
			
			topValueSpinner.setMaximum(127);
			topValueSpinner.setMinimum(bottom+1);
			
			bottomValueSpinner.setMaximum(top-1);
						
			noteControlChangeData.setTopValue(top);
			noteControlChangeData.setBottomValue(bottom);
			
			topValueSpinner.setSelection(top);
			bottomValueSpinner.setSelection(bottom);
		}
	}
	
	
	private void initializeDataStructure(DataStructure data){
		try{
		 if(data instanceof NoteControlChangeData){
			 noteControlChangeData = (NoteControlChangeData)data;

			 
		 }	else {
			 System.err.println("Wrong DataStructure Component suplied");
		 }			
		} catch (Exception e){
			System.err.println("Error ocurred in NoteControlChangeComponent");
			e.printStackTrace(System.err);
		}		
	}	
	
	public String toString(){
		String internalValues = new String("/** GuiComponent **/" +
				"\n" +
				name + 
				"\n");

		try{
			internalValues = internalValues + "\n" + noteControlChangeData.toString();
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
				noteControlChangeData.setChannel(value);
			}
		});
		
		pitchSpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = pitchSpinner.getSelection();
				String note = midiPitchConverter.convertPitch(value);
				noteControlChangeData.setPitch(value);
				activePitchLabel.setText(note);
			}
		});
		
		velocitySpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = velocitySpinner.getSelection();
				noteControlChangeData.setVelocity(value);
			}
		});
		
		velocityCheckButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				boolean value = velocityCheckButton.getSelection();
				noteControlChangeData.setVelocityOption(value);
			}
		});
		
		resolutionCombo.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = resolutionCombo.getSelectionIndex();
				
				setMaximumValues(value);				
				noteControlChangeData.setResolutionOption(value);
			}
		});
		
		controlChangeSpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = controlChangeSpinner.getSelection();
				noteControlChangeData.setControlChangeNumber(value);
			}
		});
		
		topValueSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = topValueSpinner.getSelection();
				noteControlChangeData.setTopValue(value);
				bottomValueSpinner.setMaximum(value-1);
			}
		});
		
		bottomValueSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = bottomValueSpinner.getSelection();
				noteControlChangeData.setBottomValue(value);
				topValueSpinner.setMinimum(value+1);
			}
		});
	}
	
	
}
