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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import guiComponents.SceneGroup;
import helpers.Preset;
import helpers.ProtocolParser;
//import helpers.TestParser;
import serialCommunication.SerialBlocksGHMC;
import serialCommunication.SerialCommunication;






public class MainWindow {

	protected Shell shell;
	protected Display display;

	private ToolItem newItem;
	private ToolItem openItem;
	private ToolItem saveItem;
	private ToolItem uploadItem;
	//private ToolItem printItem;
	
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
		shell = new Shell(display, SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.TITLE);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		shell.setSize(837, 507);

		shell.setText("Protos Editor : " + preset.getName());
		
		initializeToolBar();
		initializeTabFolder();	
		initializeListeners();

		//printValues();
	}
	
	private void initializeToolBar(){
		
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(0, 0, 836, 28);
		
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
		
		/* Only used for debugging */
		//printItem = new ToolItem(toolBar, SWT.NONE);
		//printItem.setText("print");
	}
	
	
	private void initializeTabFolder(){
		
	    tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setFont(SWTResourceManager.getFont("Noto Mono", 12, SWT.NORMAL));
		tabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		tabFolder.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		tabFolder.setBounds(5, 33, 821, 438);
		
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
			System.err.println("Error ocurred while gathering values from the scenes");
			e.printStackTrace(System.err);
		}
		
		return controllerValues;		
	}
	
	public String getFileNameWithoutExtension(String filename){
		String name = new String();
		String result[];
		String extension = new String(".protos");
		
		try{
			result = filename.split(extension);
			name = result[0];
		} catch(Exception e){
			System.err.println("Error Occurred in MainWindow::getFileNameWithExtension");
			e.printStackTrace(System.err);
		}

		
		return name;
	}
	
	private void initializeListeners(){
		newItem.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent event){
				MessageBox newDialogWindow = new MessageBox(shell, SWT.ICON_QUESTION | SWT.NO | SWT.YES);
				newDialogWindow.setMessage("Discard Changes?");
				
				int windowResult = newDialogWindow.open();
				
				if(windowResult == SWT.YES){
					int index = tabFolder.getSelectionIndex();
					/* load up empty preset located in the .default folder */
					
					String path = new String (".default/default.protos");
					
					if(path != null){								
						try {
							
							preset = new Preset("default");
									
							FileInputStream fis = new FileInputStream(path);
							ObjectInputStream in = new ObjectInputStream(fis);
							
							Object obj = in.readObject();
							in.close();
							
							if(obj instanceof Preset){
								preset = (Preset)obj;
							}
							
						} catch(Exception e){
							System.err.println("Error occurred while loading default preset");
							e.printStackTrace(System.err);
						}
					
						for(int i=0; i < numberOfScenes; i++){
							scenes[i].setDataContainer(preset.getDataContainer(i));
						}
						
						String presetName = getFileNameWithoutExtension(preset.getName());
						shell.setText("Protos Editor : " + presetName);
						scenes[index].redrawGuiComponent();							
					}
					//scenes[index].redrawGuiComponent();
				}
			}
		});
	
		openItem.addSelectionListener(new SelectionAdapter(){
			@Override 
			public void widgetSelected(SelectionEvent event){
				FileDialog openDialogWindow = new FileDialog(shell, SWT.OPEN);
				
				String[] filterNames = new String[]{"Presets", "All files (*)"};
				String[] filterExtensions = new String[]{"*.protos", "*"};
				
				openDialogWindow.setFilterNames(filterNames);
				openDialogWindow.setFilterExtensions(filterExtensions);
				
				String path = openDialogWindow.open();

				if(path != null){
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
						
						String presetName = getFileNameWithoutExtension(preset.getName());
						shell.setText("Protos Editor : " + presetName);
						scenes[index].redrawGuiComponent();	
						
						System.out.println("Successfully Loaded : " + preset.getName());
						
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
				String[] filterExtensions = new String[]{"*.protos", "*"};
				
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
				
						String presetName = getFileNameWithoutExtension(preset.getName());
						shell.setText("Protos Editor : " + presetName);
						scenes[index].redrawGuiComponent();	
						
						System.out.println("Successfully Saved : " + preset.getName());
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
			
			MessageBox newDialogWindow = new MessageBox(shell, SWT.ICON_QUESTION | SWT.NO | SWT.YES);
			newDialogWindow.setMessage("Upload to controller?");
			
			int windowResult = newDialogWindow.open();

			/* If Upload isn't required exit */
			
			if(windowResult == SWT.NO){
				return;
			}
					
			try{
				ProtocolParser parser = new ProtocolParser();
								
				byte messageBuffer[] = new byte[6];	//Holds write Messages
				byte dataBuffer[];  //Holds Complete dataBuffer

				int state = 0;

				
				SerialBlocksGHMC serialBlocks = new SerialBlocksGHMC();
				SerialCommunication serial = new SerialCommunication(serialBlocks);
				Thread thread = new Thread(serial);
				//thread.setDaemon(true);
						

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
				
				
				/** 
				 * Get serialPorts if available
				 * Create a dialog window if the 
				 * commport isn't available. And 
				 * Query for continuation 
				 * 
				 **/
				
				boolean comportAvailable = false;					
				String serialPorts[];
				
				do{
					serialPorts = serial.getPorts();																	
					
					if(serialPorts.length > 0){			
						comportAvailable = true;
					} else {
						
						MessageBox errorDialog = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
						errorDialog.setMessage("Could not open device! Try again?");
						
						int errorResult = errorDialog.open();
						
						
						if(errorResult == SWT.YES){
								
						} else if(errorResult == SWT.NO){
							return;
						} 
					}
					
					
				}while(!comportAvailable);	
		
					boolean success = false;
					boolean run = false;
				
					String commPort = serialPorts[0];
					serial.openPort(commPort);
					
					System.out.println(commPort);
				
					if(!thread.isAlive()){
						thread.start();
						System.out.println("Thread Started");

					}
					serial.enablePolling();
				
					messageBuffer = serialBlocks.getWriteBlock("STARTC");
				
					System.out.println("Sending STARTC");
					serial.writeDataToCommunicationPort(messageBuffer);
					Thread.sleep(50);
					state = serial.getState(); 
					result = serialBlocks.getString(state);
				
				/** ID01GH has been found start sending the data**/ 
					if(state == 1){
						
						/* ToDo ProgressDialog Bar */		
						
						System.out.println("Current State : " + result);
						Thread.sleep(50);
						System.out.println("Starting Upload");
					 

					    run = true;
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
								Thread.sleep(50);
							}

										
							if(nrOfUploads == 1){
								run = false;
								messageBuffer = serialBlocks.getWriteBlock("CALCRC");
								serial.writeDataToCommunicationPort(messageBuffer);
								Thread.sleep(50);
								state = serial.getState();
								serial.writeDataToCommunicationPort(messageBuffer);
								state = serial.getState();
								
								System.out.print("\r");
								System.out.print(byteArrayIndex);
								System.out.print(" bytes transferred");
								System.out.print("\r");

								/* Sending the data and the internal CRC 
							 	* Calculation has been a succes. Upload
							 	* has Succeeded 
							 	*/
					
							}
						
							nrOfUploads--;
						} while(run);
					}
				
					
					/* run and catch success or failed message */
					run = true;
					 while(run){
						 state = serial.getState();
						
						 Thread.sleep(50);
						 
							if(state == 3){
								run = false; 
								success = true;
								System.out.println("Data transferred succesfully to device");
							} 	else if(state == -1){
									run = false;
									success = false;
									System.out.println("Data transfer has failed, try again");
							}						 
					 }
					
					serial.disablePolling();
					Thread.sleep(100);
					serial.closePort(commPort);
					Thread.sleep(100);
					thread.interrupt();
				
					MessageBox uploadComplete = new MessageBox(shell, SWT.NONE);
					
					if(success){
						uploadComplete.setMessage("Data transferred succesfully to device.");
					} else {
						uploadComplete.setMessage("Data transfer has failed, try again");
					}
						
					uploadComplete.open();
					
					
				} catch(Exception e){
					System.err.println("Error ocurred in MainWindow while trying to upload preset");
					e.printStackTrace(System.err);
				}		
			}
		
	});	
	
	/** Print contents of dataStructures in preset to the console **/
/*	
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
*/
  } //end initializeListeners	
}
