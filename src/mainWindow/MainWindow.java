package mainWindow;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.PrintWriter;

import guiComponents.SceneGroup;
import helpers.Preset;
import helpers.ProtocolParser;
import helpers.TestParser;
import serialCommunication.SerialBlocksGHMC;
import serialCommunication.SerialCommunication;






public class MainWindow {

	protected Shell shell;
	protected Display display;

	private ToolItem newItem;
	private ToolItem openItem;
	private ToolItem saveItem;
	private ToolItem uploadItem;
	private ToolItem printItem;
	
	private TabFolder tabFolder;
	private String[] sceneTabNames = {
									  "Scene One", 
									  "Scene Two",
									  "Scene Three",
									  "Scene Four"
									  };
	
	private final int numberOfScenes = 4;
	
	Preset preset = new Preset("default");
	TabItem[] tabs = new TabItem[numberOfScenes]; 
	SceneGroup[] scenes = new SceneGroup[numberOfScenes];
	
	/** Test Serial Communication **/;
	
	//SerialCommunication serial = new SerialCommunication(); 
	//private Thread thread = new Thread(serial, "serial"); 
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
	    display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
			
		}


	}

	/**
	 * Create contents of the window.
	 */
	private void createContents() {
		shell = new Shell();
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		shell.setSize(800, 500);

		shell.setText("Guitar Hero Editor : " + preset.getName());
		
		initializeToolBar();
		initializeTabFolder();	
		initializeListeners();

		//printValues();
	}
	
	private void initializeToolBar(){
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(0, 0, 798, 28);
		
		newItem = new ToolItem(toolBar, SWT.NONE);	
		newItem.setWidth(20);
		newItem.setText("New");
			
		openItem = new ToolItem(toolBar, SWT.NONE);
		openItem.setSelection(true);
		openItem.setText("Open");
			
		saveItem = new ToolItem(toolBar, SWT.NONE);
		saveItem.setText("save");
			
		uploadItem = new ToolItem(toolBar, SWT.NONE);
		uploadItem.setText("upload");
		
		printItem = new ToolItem(toolBar, SWT.NONE);
		printItem.setText("print");
	}
	
	
	private void initializeTabFolder(){
		
	    tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.NORMAL));
		tabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		tabFolder.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		tabFolder.setBounds(10, 34, 780, 435);
		
		try{
			for(int i=0; i < scenes.length; i++){
				/** Initialize TabFolders **/
				tabs[i] = new TabItem(tabFolder, SWT.NONE);
				tabs[i].setText(new String(sceneTabNames[i]));
			
				/**bind Composite 'SceneGroup' to tabfolde**/ 
				scenes[i] = new SceneGroup(tabFolder, sceneTabNames[i]);
				tabs[i].setControl(scenes[i]);

			}
		} catch (Exception e){
			
		}
		
	}
	

	public String getStringValues(){
		String controllerValues = new String();
		
		try{
			for(int i = 0; i < scenes.length; i++){
				controllerValues += sceneTabNames[i] + "\n";
				controllerValues += scenes[i].toString() + "\n"; 
			}
		} catch(Exception e){
			System.err.println("Error ocurred while gathering values from scenes");
			e.printStackTrace(System.err);
		}
		
		return controllerValues;		
	}
	
	private void initializeListeners(){
		newItem.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				MessageBox newDialogWindow = new MessageBox(shell, SWT.ICON_QUESTION | SWT.NO | SWT.YES);
				newDialogWindow.setMessage("Discard Changes?");
				
				int rc = newDialogWindow.open();
				
				if(rc == SWT.YES){
					System.out.println("New Item");
				}
			}
		});
	
		openItem.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				FileDialog openDialogWindow = new FileDialog(shell, SWT.OPEN);
				
				String[] filterNames = new String[]{"Presets", "All files (*)"};
				String[] filterExtensions = new String[]{"*.preset", "*"};
				
				openDialogWindow.setFilterNames(filterNames);
				openDialogWindow.setFilterExtensions(filterExtensions);
				
				String path = openDialogWindow.open();

				if(path != null){
					System.out.println(path);
					try{

						int index = tabFolder.getSelectionIndex();
						
						FileInputStream fis = new FileInputStream(path);
						ObjectInputStream in = new ObjectInputStream(fis);
						
						Object obj = in.readObject();
						in.close();
						
						/** Downcast to Preset object **/ 
						
						if(obj instanceof Preset){
							preset = (Preset)obj;
						}
						
			
						/**load Presets**/
						for(int i=0; i < numberOfScenes; i++){
							scenes[i].setDataContainer(preset.getDataContainer(i));
						}
						
						scenes[index].redrawGuiComponent();
						shell.setText("Guitar Hero Editor : " + preset.getName());
						
					} catch(Exception e){
						System.err.println("Error ocurred while loading file :" + path);
						e.printStackTrace(System.err);
					}
					
					
				}
			}
		});
		
		saveItem.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				FileDialog saveDialogWindow = new FileDialog(shell, SWT.SAVE);
				
				String[] filterNames = new String[]{"Presets", "All files (*)"};
				String[] filterExtensions = new String[]{"*.preset", "*"};
				
				saveDialogWindow.setFilterNames(filterNames);
				saveDialogWindow.setFilterExtensions(filterExtensions);
				
				String path = saveDialogWindow.open();
				String fileName = saveDialogWindow.getFileName();
				String name[] = fileName.split(".preset");
							
				if(path != null){				
					try{
						int index = tabFolder.getSelectionIndex();
						/** 
						 *  Get current Active DataContainers 
						 *  And Place them in the preset wrapper 
						 *  Object 
						 */
						for(int i=0; i < numberOfScenes; i++){
							preset.setDataContainer(scenes[i].getDataContainer(), i);
						}
						
						preset.setName(name[0]);
						
						FileOutputStream fos = new FileOutputStream(path);
						ObjectOutputStream out = new ObjectOutputStream(fos);
						out.writeObject(preset);
						out.close();
				

						scenes[index].redrawGuiComponent();
						shell.setText("Guitar Hero Editor : " + preset.getName());
						
					} catch(Exception e){
						System.err.println("Error occured while saving file :" + path);
						e.printStackTrace(System.err);
					}					
				}
			}
		});
		
		/*
		 *  Upload Messages for the Serial Communication to the EEPROM 
		 *  Memory. Afther the Identification of the hardware device 
		 *  has been confirmed start sending the bytes in chunks of 
		 *  32 Bytes. The array contains 1984 Bytes 1984/32 = 62;
		 *  
		 *  The last chunk contains the CRC extract and calculate 
		 *  
		 * 
		 * 	"STARTC"										
		 *  "CALCRC"
		 *  
		 *  Using SerialCommunication v-036 in Teensy-3.1. and verified
		 */
		
		
	/** Tested the Routine with SerialCommunication-v0.36 **/	
		
	uploadItem.addSelectionListener(new SelectionAdapter(){
		@Override
		public void widgetSelected(SelectionEvent event){
			String result = new String("");
			
			try{
				ProtocolParser parser = new ProtocolParser();
				

				
				
				byte messageBuffer[] = new byte[6];	//Holds write Messages
				byte dataBuffer[];  //Holds Complete dataBuffer

				int state = 0;

				
				SerialBlocksGHMC serialBlocks = new SerialBlocksGHMC();
				SerialCommunication serial = new SerialCommunication(serialBlocks);
				Thread thread = new Thread(serial);
				thread.setDaemon(true);
						
				/* 
				 * Get all the dataStructures from the scenes and 
				 * place them in the preset Object
				 */
				
				Preset tmpPreset = new Preset("tmp");
				
				for(int i=0; i < numberOfScenes; i++){
					tmpPreset.setDataContainer(scenes[i].getDataContainer(), i);
				}
				
				/* Parse Protocol and get the ByteArray */
				parser.parsePreset(tmpPreset);
				dataBuffer = parser.getByteArray();
				
				
				/* Get serialPorts if available */
				String commPort;
				String serialPorts[] = serial.getPorts();
				
				if(serialPorts.length > 0){
					commPort = serialPorts[0];
					System.out.println("COMM port : " + commPort + "\n");
				} else {
					commPort = new String("Not Available");
				}
				
				System.out.println(serial.openPort(commPort));
				
				if(!thread.isAlive()){
					thread.start();
				}
				
				serial.enablePolling();
				
				messageBuffer = serialBlocks.getWriteBlock("STARTC");
				
				System.out.println("Sending STARTC");
				serial.writeDataToCommunicationPort(messageBuffer);
				Thread.sleep(150);
				state = serial.getState(); 
				result = serialBlocks.getString(state);
				
				/** ID01GH has been found start sending the data**/ 
				if(state == 1){
					System.out.println("Current State : " + result);
					Thread.sleep(100);
					System.out.println("Starting Upload");
					
					boolean run = true;
					int byteArrayIndex = 0;
					int nrOfUploads = 62; // 1984/32 = 62;
					
					
					do{
						byte writeBuffer[] = new byte [32]; //Holds write Buffer						
						System.out.println("Byte Blocks left: " + nrOfUploads);
						
						if(state == 1 || state == 2){
							for(int i=0; i<32; i++){
								writeBuffer[i] = dataBuffer[byteArrayIndex];
								byteArrayIndex++;
							}
							
							serial.writeDataToCommunicationPort(writeBuffer);
							Thread.sleep(100);
						}
						
						state = serial.getState();
						
						if(nrOfUploads == 1){
							run = false;
							messageBuffer = serialBlocks.getWriteBlock("CALCRC");
							serial.writeDataToCommunicationPort(messageBuffer);
							Thread.sleep(250);
							state = serial.getState();
							System.out.println(state);
							serial.writeDataToCommunicationPort(messageBuffer);
							state = serial.getState();
							System.out.println(byteArrayIndex);
							/* Sending the data and the internal CRC 
							 * Calculation has been a succes. Upload
							 * has Succeeded 
							 */
							if(state == 3){
								run = false; 
								System.out.println("Data has been Upload succesfully");
							} else if(state == -1){
								System.out.println("Upload has failed Try Again");
								run = false;
							}						
						}
						
						nrOfUploads--;
					} while(run);
					
				}
				
				serial.disablePolling();
				Thread.sleep(250);
				serial.closePort(commPort);
				Thread.sleep(250);
				thread.interrupt();
				
			} catch(Exception e){
				System.err.println("Error ocurred in MainWindow while trying to upload preset");
				e.printStackTrace(System.err);
			}		
		}	
	});	
	
	/** Print contents of dataStructures in preset to the console **/
	
	printItem.addSelectionListener(new SelectionAdapter(){
		@Override
		public void widgetSelected(SelectionEvent event){
			try{
				String test = new String("**All Presets Data** \n" + 
						 "--------------------" + "\n");
				
				System.out.println(test);
				
				Preset tmpPreset = new Preset("tmp");
				TestParser testVar = new TestParser();
				
				for(int i=0; i < numberOfScenes; i++){
					tmpPreset.setDataContainer(scenes[i].getDataContainer(), i);
				}
				
				testVar.parsePreset(tmpPreset);		
				
			} catch(Exception e){
				System.err.println("Error occurred in MainWindow printItem.addSelecetionListener(new SelectionAdapter(");
				e.printStackTrace(System.err);
			}
		}
	});
	
	shell.addDisposeListener(new DisposeListener(){
		@Override
		public void widgetDisposed(DisposeEvent event) {
			
			/**
			try{
				serial.disablePolling();
				serial.closePort(" ");
			
				if(thread.isAlive()){
					thread.interrupt();
				}
	
				System.out.println("Program Closed");


				} 	catch(Exception e){
					e.printStackTrace(System.err);
				}
			**/
		}
	});
  } //end initializeListeners	
}
