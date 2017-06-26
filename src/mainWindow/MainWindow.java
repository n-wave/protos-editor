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
import helpers.TestParser;






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

	/** Test Parser **/
	
	TestParser testParser = new TestParser();

	
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

		try{
		

	
		//	thread.setDaemon(true);

			
			//System.out.println(serial.closePort(ports[0]));
			
		

			//testParser.testTwo();
			
			//testParser.testThree();
			
			//testParser.testFour();
			//testParser.testFive();
			//testParser.testSix();
			//testParser.testSeven();
		} catch(Exception e){
			System.err.println("Error Ocurred while testing parser");
			e.printStackTrace(System.err);
		}	
		
		
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

					} catch(Exception e){
						System.err.println("Error occured while saving file :" + path);
						e.printStackTrace(System.err);
					}					
				}
			}
		});
		
	uploadItem.addSelectionListener(new SelectionAdapter(){
		@Override
		public void widgetSelected(SelectionEvent event){
		
			try{
	
				
				
			} catch(Exception e){
				System.err.println("Error ocurred in MainWindow");
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
				
				testVar.testEight(tmpPreset);		
				
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
