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

import dataContainers.DataStructure;
import dataContainers.NoteControlChangeToggleData;
import helpers.MidiPitchConverter;

public class NoteControlChangeToggleComponent extends Composite {

	private Label toggleLabel;
	private Combo toggleCombo;
	
	
	private Spinner channelSpinner;
	private Spinner pitchSpinner;
	private Spinner velocitySpinner;
	
	private Combo resolutionCombo;
	private Spinner controlChangeSpinner;
	private Spinner onValueSpinner;
	private Spinner offValueSpinner;
	
	private Label activePitchLabel;
	
	private NoteControlChangeToggleData NoteControlChangeToggleData = new NoteControlChangeToggleData();
	private MidiPitchConverter midiPitchConverter = new MidiPitchConverter();
	
	String name = new String("NoteControlChangeComponent");
	
	private boolean resolutionChanged = false;
	
	public NoteControlChangeToggleComponent(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
	
		initializeChannelSelection();
		initializeToggleOption();
		initializePitchSelection();
		initializeVelocitySelection();
		initializeResolutionSelection();
		initializeControlChangeNumberSelection();
		initializeOnValueSelection();
		initializeOffValueSelection();
		
		initializeListeners();
	}

	
	public NoteControlChangeToggleComponent(Composite parent, int style, DataStructure data) {
		super(parent, style);
		
		initializeDataStructure(data);

		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(3, false));
		
		initializeChannelSelection();
		initializeToggleOption();
		initializePitchSelection();
		initializeVelocitySelection();
		initializeResolutionSelection();
		initializeControlChangeNumberSelection();
		initializeOnValueSelection();
		initializeOffValueSelection();
		
		initializeListeners();
		
		//Set MaximumValues
		 int value = NoteControlChangeToggleData.getResolutionOption();
		 setMaximumValues(value);
		//System.out.println(this.toString());
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	

	
	private void initializeChannelSelection(){
		//NoteComponent	
		int channel = NoteControlChangeToggleData.getChannel();
		setLayout(null);
		
		Label channelLabel = new Label(this, SWT.NONE);
		channelLabel.setBounds(5, 10, 77, 19);
		channelLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		channelLabel.setText("Channel");

		channelSpinner = new Spinner(this, SWT.BORDER);
		channelSpinner.setBounds(120, 5, 106, 29);
		channelSpinner.setMaximum(16);
		channelSpinner.setMinimum(1);
		channelSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		channelSpinner.setSelection(channel);
	}
	
	private void initializeToggleOption(){
		
		toggleLabel = new Label(this, SWT.NONE);
		toggleLabel.setBounds(294, 10, 69, 19);
		toggleLabel.setText("Option");
		toggleLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		
		toggleCombo = new Combo(this, SWT.NONE);
		toggleCombo.setBounds(365, 8, 106, 24);
		toggleCombo.setToolTipText("Switch Behaviour");
		toggleCombo.setTextDirection(3355443);
		toggleCombo.setItems(new String[] {"Momentary", "Toggle"});
		toggleCombo.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		toggleCombo.select(0);
	}
	
	
	private void initializePitchSelection(){
		int pitch = NoteControlChangeToggleData.getPitch();
		String note = midiPitchConverter.convertPitch(pitch);
		
		
		Label pitchLabel = new Label(this, SWT.NONE);
		pitchLabel.setBounds(5, 44, 55, 19);
		pitchLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		pitchLabel.setText("Pitch");
		
		pitchSpinner = new Spinner(this, SWT.BORDER);
		pitchSpinner.setBounds(120, 39, 106, 29);
		pitchSpinner.setMaximum(127);
		pitchSpinner.setSelection(pitch);
		pitchSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));

		activePitchLabel = new Label(this, SWT.NONE);
		activePitchLabel.setBounds(232, 44, 55, 19);
		activePitchLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		activePitchLabel.setText("----");	
		activePitchLabel.setText(note);	
	}
	
	private void initializeVelocitySelection(){
		int velocity = NoteControlChangeToggleData.getVelocity();
		
		Label lblLinkVelocity = new Label(this, SWT.NONE);
		lblLinkVelocity.setBounds(5, 78, 88, 19);
		lblLinkVelocity.setText("Velocity");
		lblLinkVelocity.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
							
		velocitySpinner = new Spinner(this, SWT.BORDER);
		velocitySpinner.setBounds(120, 73, 106, 29);
		velocitySpinner.setMaximum(127);
		velocitySpinner.setSelection(velocity);
		velocitySpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		
	}
	
	private void initializeResolutionSelection()
	{
		int resolution = NoteControlChangeToggleData.getResolutionOption();
		
		
		Label resolutionLabel = new Label(this, SWT.NONE);
		resolutionLabel.setBounds(5, 109, 110, 19);
		resolutionLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		resolutionLabel.setText("Resolution");
		
		resolutionCombo = new Combo(this, SWT.NONE);
		resolutionCombo.setBounds(120, 107, 107, 23);
		resolutionCombo.setTextDirection(3355443);
		resolutionCombo.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		resolutionCombo.setItems(new String[] {"7 Bit", "14 Bit"});
		resolutionCombo.select(resolution);
	}
	
	private void initializeControlChangeNumberSelection()
	{
		int number = NoteControlChangeToggleData.getControlChangeNumber();
		
		Label controlChangeLabel = new Label(this, SWT.NONE);
		controlChangeLabel.setBounds(5, 140, 99, 19);
		controlChangeLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		controlChangeLabel.setText("CC Number");
		
		controlChangeSpinner = new Spinner(this, SWT.BORDER);
		controlChangeSpinner.setBounds(120, 135, 106, 29);
		controlChangeSpinner.setMaximum(127);
		controlChangeSpinner.setSelection(number);
		controlChangeSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		

	}
	
	private void initializeOnValueSelection()
	{
		int onValue = NoteControlChangeToggleData.getOnValue();
		
		Label onValueLabel = new Label(this, SWT.NONE);
		onValueLabel.setBounds(5, 174, 88, 19);
		onValueLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		onValueLabel.setText("On Value");
		
		onValueSpinner = new Spinner(this, SWT.BORDER);
		onValueSpinner.setBounds(120, 169, 106, 29);
		onValueSpinner.setMaximum(127);
		onValueSpinner.setSelection(onValue);
		onValueSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		
	
	}
	
	private void initializeOffValueSelection()
	{
		int offValue = NoteControlChangeToggleData.getOffValue();
		
		Label offValueLabel = new Label(this, SWT.NONE);
		offValueLabel.setBounds(5, 208, 99, 19);
		offValueLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		offValueLabel.setText("Off Value");
		
		offValueSpinner = new Spinner(this, SWT.BORDER);
		offValueSpinner.setBounds(120, 203, 106, 29);
		offValueSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		offValueSpinner.setMaximum(127);
		offValueSpinner.setSelection(offValue);
	}
	
	private void setMaximumValues(int value){
		int onValue = NoteControlChangeToggleData.getOnValue();
		int offValue = NoteControlChangeToggleData.getOffValue();
		
		if(value == 1){
			
			if(resolutionChanged){
				onValue *= 129;
				offValue *= 129;
			
				resolutionChanged = false;
			}
			
			onValueSpinner.setMaximum(16383);
			onValueSpinner.setMinimum(offValue+1);
			
			offValueSpinner.setMaximum(onValue-1);
			
			NoteControlChangeToggleData.setOnValue(onValue);
			NoteControlChangeToggleData.setOffValue(offValue);
			
			onValueSpinner.setSelection(onValue);
			offValueSpinner.setSelection(offValue);
		} else {
			
			if(resolutionChanged){
				onValue %= 128;
				offValue %= 128; 
			
				resolutionChanged = false;
			}
			
			onValueSpinner.setMaximum(127);
			onValueSpinner.setMinimum(offValue+1);
			
			offValueSpinner.setMaximum(onValue-1);
						
			NoteControlChangeToggleData.setOnValue(onValue);
			NoteControlChangeToggleData.setOffValue(offValue);
			
			onValueSpinner.setSelection(onValue);
			offValueSpinner.setSelection(offValue);
		}
	}
	
	
	private void initializeDataStructure(DataStructure data){
		try{
		 if(data instanceof NoteControlChangeToggleData){
			 NoteControlChangeToggleData = (NoteControlChangeToggleData)data;

			 
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
			internalValues = internalValues + "\n" + NoteControlChangeToggleData.toString();
		} catch(Exception e){
			System.err.println();
		}

		return internalValues;
	}
	
	private void initializeListeners(){
		toggleCombo.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = toggleCombo.getSelectionIndex();
				NoteControlChangeToggleData.setToggleOption(value);
			}
		});
		
		channelSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = channelSpinner.getSelection();
				NoteControlChangeToggleData.setChannel(value);
			}
		});
		
		pitchSpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = pitchSpinner.getSelection();
				String note = midiPitchConverter.convertPitch(value);
				NoteControlChangeToggleData.setPitch(value);
				activePitchLabel.setText(note);
			}
		});
		
		velocitySpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = velocitySpinner.getSelection();
				NoteControlChangeToggleData.setVelocity(value);
			}
		});
		
		resolutionCombo.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = resolutionCombo.getSelectionIndex();
				resolutionChanged = true;
				setMaximumValues(value);				
				NoteControlChangeToggleData.setResolutionOption(value);
			}
		});
		
		controlChangeSpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = controlChangeSpinner.getSelection();
				NoteControlChangeToggleData.setControlChangeNumber(value);
			}
		});
		
		onValueSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = onValueSpinner.getSelection();
				NoteControlChangeToggleData.setOnValue(value);
				offValueSpinner.setMaximum(value-1);
			}
		});
		
		offValueSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = offValueSpinner.getSelection();
				NoteControlChangeToggleData.setOffValue(value);
				onValueSpinner.setMinimum(value+1);
			}
		});
	}
	
	

}
