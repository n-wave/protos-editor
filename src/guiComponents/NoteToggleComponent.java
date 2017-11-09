package guiComponents;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Spinner;

import dataContainers.DataStructure;
import dataContainers.NoteVelocityToggleData;
import helpers.MidiPitchConverter;
import org.eclipse.swt.widgets.Combo;


public class NoteToggleComponent extends Composite {

	private Spinner channelSpinner;
	private Spinner pitchSpinner;
	private Spinner velocitySpinner;
	private Label activePitchLabel;
	
	private NoteVelocityToggleData noteVelocityToggleData;
	private Label toggleLabel;
	private Combo toggleCombo;
	
	String name = new String("NoteComponent");
	
	private MidiPitchConverter midiPitchConverter = new MidiPitchConverter();
	
	public NoteToggleComponent(Composite parent, int style) {
		super(parent, SWT.NONE);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.NORMAL));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setBounds(146, 42, 555, 354);
		
		initializeChannelSelection();
		initializeToggleSelection();
		initializePitchSelection();
		initializeVelocitySelection();
		
		initializeListeners();
	}

	public NoteToggleComponent(Composite parent, int style, DataStructure data) {
		super(parent, SWT.NONE);
		
		initializeDataStructure(data);

		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		setLayout(new GridLayout(3, false));
		
		initializeChannelSelection();
		initializeToggleSelection();
		initializePitchSelection();
		initializeVelocitySelection();
		
		initializeListeners();
		
		//System.out.println(this.toString());
	}

	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void initializeChannelSelection(){
		int channel = noteVelocityToggleData.getChannel();
		setLayout(null);
		
		Label channelLabel = new Label(this, SWT.NONE);
		channelLabel.setBounds(5, 10, 77, 19);
		channelLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		channelLabel.setText("Channel");

		channelSpinner = new Spinner(this, SWT.BORDER);
		channelSpinner.setBounds(98, 5, 88, 29);
		channelSpinner.setMaximum(16);
		channelSpinner.setMinimum(1);
		channelSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		channelSpinner.setSelection(channel);

	}
	
	private void initializeToggleSelection(){
		
		int toggleValue = noteVelocityToggleData.getToggleOption();
		
		toggleLabel = new Label(this, SWT.NONE);
		toggleLabel.setBounds(294, 10, 69, 19);
		toggleLabel.setText("Option");
		toggleLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		
		toggleCombo = new Combo(this, SWT.NONE);
		toggleCombo.setBounds(365, 8, 106, 24);
		toggleCombo.setToolTipText("Switch Behaviour");
		toggleCombo.setItems(new String[] {"Momentary", "Toggle"});
		toggleCombo.select(toggleValue);
	}
	
	private void initializePitchSelection(){
		int pitch = noteVelocityToggleData.getPitch();
		String note = midiPitchConverter.convertPitch(pitch);
		
		Label pitchLabel = new Label(this, SWT.NONE);
		pitchLabel.setBounds(5, 44, 55, 19);
		pitchLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		pitchLabel.setText("Pitch");
		
		pitchSpinner =  new Spinner(this, SWT.BORDER);
		pitchSpinner.setBounds(98, 39, 88, 29);
		pitchSpinner.setToolTipText("MIDI Pitch");
		pitchSpinner.setMaximum(127);
		pitchSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		pitchSpinner.setSelection(pitch);

		activePitchLabel = new Label(this, SWT.NONE);
		activePitchLabel.setBounds(191, 44, 55, 19);
		activePitchLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		activePitchLabel.setText(note);
	}
	
	private void initializeVelocitySelection(){
		int velocity = noteVelocityToggleData.getVelocity();
		

		Label velocityLabel = new Label(this, SWT.NONE);
		velocityLabel.setBounds(5, 78, 88, 19);
		velocityLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		velocityLabel.setText("Velocity");
								
		velocitySpinner = new Spinner(this, SWT.BORDER);
		velocitySpinner.setBounds(98, 73, 88, 29);
		velocitySpinner.setToolTipText("Static Velocity/Max Velocity");
		velocitySpinner.setMaximum(127);
		velocitySpinner.setSelection(velocity);
		velocitySpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
	}
	
	private void initializeDataStructure(DataStructure data){
		try{
		 if(data instanceof NoteVelocityToggleData){
			 noteVelocityToggleData = (NoteVelocityToggleData)data;
		 }	else {
			 System.err.println("Wrong DataStructure Component suplied");
		 }			
		} catch (Exception e){
			System.err.println("Error ocurred in NoteVelocityComponent");
			e.printStackTrace(System.err);
		}		
	}	
	

	public String toString(){
		String internalValues = new String("/** GuiComponent **/" +
				"\n" +
				name + 
				"\n");

		try{
			internalValues = internalValues + "\n" + noteVelocityToggleData.toString();
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
				noteVelocityToggleData.setChannel(value);
			}
		});
		
		toggleCombo.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = toggleCombo.getSelectionIndex();
				noteVelocityToggleData.setToggleOption(value);
			}
		});
		
		pitchSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = pitchSpinner.getSelection();
				String note = midiPitchConverter.convertPitch(value);
				noteVelocityToggleData.setPitch(value);
				activePitchLabel.setText(note);
			}
		});
		
		
	}
}
