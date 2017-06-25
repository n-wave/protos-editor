package guiComponents;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.wb.swt.SWTResourceManager;

import dataContainers.DataStructure;
import dataContainers.PitchBendNoteData;
import helpers.MidiPitchConverter;

public class PitchBendNoteComponent extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	
	private Spinner channelSpinner;
	private Spinner pitchSpinner;
	private Spinner velocitySpinner;
	
	private Label activePitchLabel;
	
	private PitchBendNoteData pitchBendNoteData;
	private MidiPitchConverter midiPitchConverter = new MidiPitchConverter();
	
	private String name = new String("PitchBendNoteComponent");
	
	public PitchBendNoteComponent(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 14, SWT.BOLD));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		setLayout(new GridLayout(3, false));
		
		initializeChannelSelection();
		initializePitchSelection();
		initializeVelocitySelection();
		
		initializeListeners();
	}

	public PitchBendNoteComponent(Composite parent, int style, DataStructure data) {
		super(parent, style);
		
		initializeDataStructure(data);
		
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		setFont(SWTResourceManager.getFont("Noto Mono", 14, SWT.BOLD));
		setForeground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		setLayout(new GridLayout(3, false));
		
		initializeChannelSelection();
		initializePitchSelection();
		initializeVelocitySelection();
		
		initializeListeners();
		
		//System.out.println(this.toString());
	}
	
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void initializeChannelSelection()
	{
		int channel = pitchBendNoteData.getChannel();
		
		Label ChannelLabel = new Label(this, SWT.NONE);
		ChannelLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		ChannelLabel.setText("Channel");
		
		channelSpinner = new Spinner(this, SWT.BORDER);
		channelSpinner.setMaximum(16);
		channelSpinner.setMinimum(1);
		channelSpinner.setSelection(channel);
		channelSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
	}

	private void initializePitchSelection()
	{
		int pitch = pitchBendNoteData.getPitch();
		String note = midiPitchConverter.convertPitch(pitch);
		
		new Label(this, SWT.NONE);
		Label pitchLabel = new Label(this, SWT.NONE);
		pitchLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		pitchLabel.setText("Pitch");
	
		pitchSpinner = new Spinner(this, SWT.BORDER);
		pitchSpinner.setToolTipText("Base Pitch");
		pitchSpinner.setMaximum(127);
		pitchSpinner.setSelection(pitch);
		pitchSpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		
		activePitchLabel = new Label(this, SWT.NONE);
		activePitchLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		activePitchLabel.setText("----");	
		activePitchLabel.setText(note);
	}

	private void initializeVelocitySelection()
	{
		int velocity = pitchBendNoteData.getVelocity();
		
		Label velocityLabel = new Label(this, SWT.NONE);
		velocityLabel.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.BOLD));
		velocityLabel.setText("Velocity");
	
					
		velocitySpinner = new Spinner(this, SWT.BORDER);
		velocitySpinner.setMaximum(127);
		velocitySpinner.setSelection(velocity);
		velocitySpinner.setFont(SWTResourceManager.getFont("Noto Mono", 10, SWT.NORMAL));
		new Label(this, SWT.NONE);
	}


	private void initializeDataStructure(DataStructure data){
		try{
		 if(data instanceof PitchBendNoteData){
			 pitchBendNoteData = (PitchBendNoteData)data;
		 }	else {
			 System.err.println("Wrong DataStructure Component suplied");
		 }			
		} catch (Exception e){
			System.err.println("Error ocurred in PitchBendNoteComponent");
			e.printStackTrace(System.err);
		}		
	}

	public String toString(){
		String internalValues = new String("/** GuiComponent **/" +
											"\n" +
											name + 
											"\n");

		try{
			internalValues = internalValues + "\n" + pitchBendNoteData.toString();
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
				pitchBendNoteData.setChannel(value);
			}
		});
		
		pitchSpinner.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				int value = pitchSpinner.getSelection();
				String note = midiPitchConverter.convertPitch(value);
				pitchBendNoteData.setPitch(value);
				activePitchLabel.setText(note);
			}
		});
		
		velocitySpinner.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				int value = velocitySpinner.getSelection();
				pitchBendNoteData.setVelocity(value);
			}
		});
	}
	
}

