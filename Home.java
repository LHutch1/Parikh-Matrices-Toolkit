import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Home{
	public static JFrame homeFrame;
	public static JLabel res;
	private static final String INVALID_SIZE = "Alphabet must be of at least size 2.";
	public static JTextField sizeField;
	public static JLabel matError;
	public static SwingWorker<Void, Void> mySwingWorker;
	public JFrame frame;
	public static JFileChooser chooser;
	public JFileChooser chooserSave;
	public int iteration;
	public static File uploadedFile;
	public static JPanel resultsPanel;
	public static JTextArea alphabetInput;
	public static JTextArea wordInput;
	public static JPanel centraliser;
	public static JCheckBox save;
	public static JTextArea setInput;
	public static JTextField[][] inpText;
	public static ActionEvent e;
	public static JButton stop;
	public static Component glassPane;
	public static JDialog dialog;
	public static String filePath;

	public static void main(String[] args) {
		homeFrame = new JFrame("Parikh Matrices Toolkit");
		homeFrame.setLayout(new BorderLayout());
		centraliser = new JPanel(new GridBagLayout());
		centraliser.setBorder(new EmptyBorder(0,30,30,30));
		GridBagConstraints c = new GridBagConstraints();
		JPanel choice1 = new JPanel();
		JPanel choice2 = new JPanel(new GridLayout(0,3));
		JPanel choiceholder = new JPanel(new GridLayout(0,1));
		choiceholder.setBorder(new EmptyBorder(0,30,0,30));
		JLabel choiceInfo = new JLabel("Please select your input method:");
		choiceInfo.setBorder(new EmptyBorder(0,0,10,0));
		choiceInfo.setFont(choiceInfo.getFont().deriveFont(26.0f));
		JButton wordBtn = new JButton("Enter Word");
		Dimension btnsize = new Dimension(250,50);
		wordBtn.setPreferredSize(btnsize);
		JButton matrixBtn = new JButton("Enter Matrix");
		matrixBtn.setPreferredSize(btnsize);
		JButton fileBtn = new JButton("Upload File");
		fileBtn.setPreferredSize(btnsize);
		choice1.add(choiceInfo);
		choice2.add(wordBtn);
		choice2.add(matrixBtn);
		choice2.add(fileBtn);
		choiceholder.add(choice1);
		choiceholder.add(choice2);
		c.gridx=0;
		c.gridy=0;
		c.anchor=GridBagConstraints.PAGE_START;
		c.fill=GridBagConstraints.VERTICAL;
		centraliser.add(choiceholder,c);
		c.weighty=0;
		JPanel inputholder = new JPanel(new BorderLayout());
		inputholder.setBorder(new EmptyBorder(30,30,0,30));
		inputholder.setLayout(new BoxLayout(inputholder, BoxLayout.Y_AXIS));
		inputholder.setPreferredSize(new Dimension(1000,305)); 
		JPanel panel = new JPanel(new GridLayout(0,3));
		resultsPanel = new JPanel(new GridLayout(0,1));
		resultsPanel.setMinimumSize(new Dimension(500,500));
		panel.setBorder(new EmptyBorder(0,30,0,30));
		resultsPanel.setBorder(new EmptyBorder(0,30,0,30));
		filePath=System.getProperty("user.home");
		//JComponent loading = new JPanel(new BorderLayout());
		//ImageIcon loadingI = new ImageIcon("ajax-loader.gif");
		//loading.add(new JLabel("loading... ", loadingI, JLabel.CENTER),BorderLayout.CENTER);
		//homeFrame.setGlassPane(loading);
		fileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e3){
				//Panel to hold word input
				Component[] icomponents = inputholder.getComponents();
				for (Component icomponent : icomponents) {
					inputholder.remove(icomponent);  
				}
				Component[] pcomponents = panel.getComponents();
				for (Component pcomponent : pcomponents) {
					panel.remove(pcomponent);  
				}
				Component[] rcomponents = resultsPanel.getComponents();
				for (Component rcomponent : rcomponents) {
					resultsPanel.remove(rcomponent);  
				}
				inputholder.setLayout(new BoxLayout(inputholder, BoxLayout.Y_AXIS));
				inputholder.setPreferredSize(new Dimension(1000,305)); 
				panel.setLayout(new GridLayout(0,3));
				JPanel inst = new JPanel(new GridBagLayout());
				JLabel instructions = new JLabel("<html><center>Please upload a .txt file. When you select a function, you will then be asked to select the location you wish to save your output files in.</center><html>");
				instructions.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
				JLabel instructions2 = new JLabel("<html><center>If you are using either the Parikh or L-Parikh matrix functions, your text file may use either commas or new lines as delimiters.</center><html>");
				instructions2.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
				JLabel instructions3 = new JLabel("<html><center>If you are using either of the P-Parikh matrix functions, your text file must use a new line as the delimiter and each<br>word must be followed in the next line by the projection set to be used on it. Your projection set may be written<br>in any form. For example, your text file may look like this:</center><html>");
				instructions3.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
				JLabel instructions4 = new JLabel("<html><center>abbcca<br>{a,b}<br>ddaccbd<br>[0 2]</center><html>");
				instructions4.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
				GridBagConstraints c1 = new GridBagConstraints();
				c1.gridx=0;
				c1.gridy=0;
				inst.add(instructions,c1);
				c1.gridx=0;
				c1.gridy=1;
				inst.add(instructions2,c1);
				c1.gridx=0;
				c1.gridy=2;
				inst.add(instructions3,c1);
				c1.gridx=0;
				c1.gridy=3;
				inst.add(instructions4,c1);
				inputholder.add(inst,BorderLayout.NORTH);
				JPanel filepanel = new JPanel(new GridLayout(0,1));
				JButton browse  = new JButton("Browse...");
				JPanel browsePanel = new JPanel();
				browse.setPreferredSize(btnsize);
				browsePanel.add(browse);
				filepanel.add(browsePanel);
				inputholder.add(filepanel, BorderLayout.CENTER);
				JPanel confirmation = new JPanel();
				browse.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e3) {
						try {
							Home.dialog.dispose();
						}
						catch (Exception e){
						}
						try {
							Component[] bcomponents = confirmation.getComponents();
							for (Component bcomponent : bcomponents) {
								confirmation.remove(bcomponent);  
							}
							//Create file chooser for user to upload file of words
							chooser = new JFileChooser();
							chooser.setCurrentDirectory(new File(filePath));
							FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
							chooser. setAcceptAllFileFilterUsed(false);
							chooser.setFileFilter(filter);
							chooser.setDialogTitle("Select Input File");
							int result = chooser.showOpenDialog(null);
							//if user uploads a file
							if (result==JFileChooser.APPROVE_OPTION) {
								uploadedFile = chooser.getSelectedFile();
								filePath = uploadedFile.getParent();
								JLabel fileinfo = new JLabel("You have uploaded the file:");
								JLabel filename = new JLabel(uploadedFile.getName());
								confirmation.add(fileinfo);
								confirmation.add(filename);
								filepanel.add(confirmation,BorderLayout.CENTER);
							}
							centraliser.revalidate();
							centraliser.repaint();
							homeFrame.revalidate();
							homeFrame.repaint();
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				c.gridx=0;
				c.gridy=2;
				centraliser.add(inputholder,c);
				//File home = FileSystemView.getFileSystemView().getHomeDirectory(); 
				/*JButton amiableWords = new JButton("<html><center>Find all words that share<br>a Parikh matrix with your word.</center><html>");
				JButton parikhMatrix = new JButton("<html><center>Find the Parikh<br>matrix of your word.</center><html>");
				JButton lParikhMatrix = new JButton("<html><center>Find the L-Parikh<br>matrix of your word.</center><html>");
				JButton lAmiableWords = new JButton("<html><center>Find the words that share an<br>L-Parikh matrix with your word.</center><html>");
				JButton pParikhMatrix = new JButton("<html><center>Find the P-Parikh matrix of your word.</center><html>");
				JButton pAmiableWords = new JButton("<html><center>Find the words that share a<br>P-Parikh matrix with your word.</center><html>");*/
				JButton amiableWords = new JButton("<html><center>Amiable Words</center><html>");
				JButton parikhMatrix = new JButton("<html><center>Parikh Matrix</center><html>");
				JButton lParikhMatrix = new JButton("<html><center>L-Parikh Matrix</center><html>");
				JButton lAmiableWords = new JButton("<html><center>L-Amiable Words</center><html>");
				JButton pParikhMatrix = new JButton("<html><center>P-Parikh Matrix</center><html>");
				JButton pAmiableWords = new JButton("<html><center>P-Amiable Words</center><html>");
				parikhMatrix.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){
					try {
						HomeDialogWait wait = new HomeDialogWait();
						mySwingWorker = new SwingWorker<Void, Void>() {@Override protected Void doInBackground() throws Exception {
							ParikhMatrixUploadInterface.actioned();
							return null;
						}};
						mySwingWorker.execute();
						wait.makeWait(e);
						if (mySwingWorker.isDone()){
							wait.close();
							Home.dialog.setVisible(false);
						}
						wait.close();
						//Home.res.setVisible(false);
					} catch (OutOfMemoryError oome) {
						Home.res.setText("Error - out of memory.");
						Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
						res.setForeground(Color.RED);
						Home.res.setVisible(true);
						centraliser.revalidate();
						centraliser.repaint();
						homeFrame.revalidate();
						homeFrame.repaint();
					}
				}}); 
				amiableWords.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){
					try {
						HomeDialogWait wait = new HomeDialogWait();
						mySwingWorker = new SwingWorker<Void, Void>() {@Override protected Void doInBackground() throws Exception {
							AmiableWordsUploadInterface.actioned();
							return null;
						}};
						mySwingWorker.execute();
						wait.makeWait(e);
						if (mySwingWorker.isDone()){
							wait.close();
							Home.dialog.setVisible(false);
						}
						wait.close();
					} catch (OutOfMemoryError oome) {
						Home.res.setText("Error - out of memory.");
						Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
						res.setForeground(Color.RED);
						Home.res.setVisible(true);
						centraliser.revalidate();
						centraliser.repaint();
						homeFrame.revalidate();
						homeFrame.repaint();
					}
				}}); 
				lParikhMatrix.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){
					try {
						HomeDialogWait wait = new HomeDialogWait();
						mySwingWorker = new SwingWorker<Void, Void>() {@Override protected Void doInBackground() throws Exception {
							LParikhMatrixUploadInterface.actioned();
							return null;
						}};
						mySwingWorker.execute();
						wait.makeWait(e);
						if (mySwingWorker.isDone()){
							wait.close();
							Home.dialog.setVisible(false);
						}
						wait.close();
					} catch (OutOfMemoryError oome) {
						Home.res.setText("Error - out of memory.");
						Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
						res.setForeground(Color.RED);
						Home.res.setVisible(true);
						centraliser.revalidate();
						centraliser.repaint();
						homeFrame.revalidate();
						homeFrame.repaint();
					}
				}}); 
				lAmiableWords.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){
					try {
						HomeDialogWait wait = new HomeDialogWait();
						mySwingWorker = new SwingWorker<Void, Void>() {@Override protected Void doInBackground() throws Exception {
							LAmiableWordsUploadInterface.actioned();
							return null;
						}};
						mySwingWorker.execute();
						wait.makeWait(e);
						if (mySwingWorker.isDone()){
							wait.close();
							Home.dialog.setVisible(false);
						}
						wait.close();
					} catch (OutOfMemoryError oome) {
						Home.res.setText("Error - out of memory.");
						Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
						res.setForeground(Color.RED);
						Home.res.setVisible(true);
						centraliser.revalidate();
						centraliser.repaint();
						homeFrame.revalidate();
						homeFrame.repaint();
					}
				}}); 
				pParikhMatrix.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){
					try {
						HomeDialogWait wait = new HomeDialogWait();
						mySwingWorker = new SwingWorker<Void, Void>() {@Override protected Void doInBackground() throws Exception {
							PParikhMatrixUploadInterface.actioned();
							return null;
						}};
						mySwingWorker.execute();
						wait.makeWait(e);
						if (mySwingWorker.isDone()){
							wait.close();
							Home.dialog.setVisible(false);
						}
						wait.close();
					} catch (OutOfMemoryError oome) {
						Home.res.setText("Error - out of memory.");
						Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
						res.setForeground(Color.RED);
						Home.res.setVisible(true);
						centraliser.revalidate();
						centraliser.repaint();
						homeFrame.revalidate();
						homeFrame.repaint();
					}
				}}); 
				pAmiableWords.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){
					try {
						HomeDialogWait wait = new HomeDialogWait();
						mySwingWorker = new SwingWorker<Void, Void>() {@Override protected Void doInBackground() throws Exception {
							PAmiableWordsUploadInterface.actioned();
							return null;
						}};
						mySwingWorker.execute();
						wait.makeWait(e);
						if (mySwingWorker.isDone()){
							wait.close();
							Home.dialog.setVisible(false);
						}
						wait.close();
					} catch (OutOfMemoryError oome) {
						Home.res.setText("Error - out of memory.");
						Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
						res.setForeground(Color.RED);
						Home.res.setVisible(true);
						centraliser.revalidate();
						centraliser.repaint();
						homeFrame.revalidate();
						homeFrame.repaint();
					}
				}}); 
				//JButton space3 = new JButton();
				//space3.setVisible(false);
				amiableWords.setPreferredSize(btnsize);
				parikhMatrix.setPreferredSize(btnsize);
				lParikhMatrix.setPreferredSize(btnsize);
				lAmiableWords.setPreferredSize(btnsize);
				pParikhMatrix.setPreferredSize(btnsize);
				pAmiableWords.setPreferredSize(btnsize);
				//space3.setPreferredSize(btnsize);
				panel.add(parikhMatrix);
				panel.add(lParikhMatrix);
				panel.add(pParikhMatrix);
				panel.add(amiableWords);
				panel.add(lAmiableWords);
				panel.add(pAmiableWords);
				c.gridx=0;
				c.gridy=3;
				centraliser.add(panel,c);
				c.gridx=0;
				c.gridy=4;
				centraliser.add(resultsPanel,c);
				centraliser.revalidate();
				centraliser.repaint();
				homeFrame.revalidate();
				homeFrame.repaint();
			}
		});
		matrixBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e3){
				//Panel to hold word input
				Component[] icomponents = inputholder.getComponents();
				for (Component icomponent : icomponents) {
					inputholder.remove(icomponent);  
				}
				Component[] pcomponents = panel.getComponents();
				for (Component pcomponent : pcomponents) {
					panel.remove(pcomponent);  
				}
				Component[] rcomponents = resultsPanel.getComponents();
				for (Component rcomponent : rcomponents) {
					resultsPanel.remove(rcomponent);  
				}
				panel.setLayout(new GridLayout(0,2));
				inputholder.setLayout(new GridBagLayout());
				//Panel to hold starting components
				JPanel northPanel = new JPanel(new GridBagLayout());
				//Panel to hold enter text and text field
				JPanel northPanel1 = new JPanel();
				northPanel1.setLayout(new FlowLayout());
				JLabel label = new JLabel("Please enter your");
				JLabel link = new JLabel("<html><u>Parikh matrix<u><html>");
				link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				link.setForeground(Color.BLUE);
				link.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//JFrame frame = new JFrame("Parikh Matrix Definition");
						JDialog dialog1 = new JDialog(homeFrame);
						JPanel container = new JPanel();
						JLabel parikhMatrixInfo = new JLabel("<html><center>The Parikh matrix of a word has size (k+1)x(k+1),<br>where k is the size of the alphabet. The diagonal<br>of the matrix is populated with 1's and all elements<br>below it are 0. The count of all subwords that consist<br>of consecutive letters in the alphabet and are of<br>length n in the word are found on the n-diagonal.</center><html>");
						container.add(parikhMatrixInfo);
						container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
						Point location = MouseInfo.getPointerInfo().getLocation(); 
						int x = (int) location.getX();
						int y = (int) location.getY();
						dialog1.setLocation(x, y);
						dialog1.setUndecorated(true);
						dialog1.add(container);
						dialog1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						dialog1.setVisible(true);
						dialog1.pack();
						dialog1.addWindowFocusListener(new WindowFocusListener() {
							public void windowGainedFocus(WindowEvent e) {
							}
							public void windowLostFocus(WindowEvent e) {
								if (SwingUtilities.isDescendingFrom(e.getOppositeWindow(), dialog1)) {
									return;
								}
								dialog1.setVisible(false);
							}
						});
					}
				});
				JLabel label2 = new JLabel("size.");
				northPanel1.add(label);
				northPanel1.add(link);
				northPanel1.add(label2);
				sizeField = new JTextField(10);
				BufferedImage wordHelp = null;
				try {
					wordHelp = ImageIO.read(getClass().getResource("/Images/Help.png"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Image pmHelp = wordHelp.getScaledInstance(20,20,Image.SCALE_SMOOTH);
				ImageIcon pmHelpIcon = new ImageIcon(pmHelp);
				JLabel pmHelpIconLabel=new JLabel(pmHelpIcon);
				pmHelpIconLabel.setBorder(BorderFactory.createEmptyBorder(5,10,5,5));
				pmHelpIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				pmHelpIconLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//JFrame frame = new JFrame("Parikh Matrix Definition");
						JDialog dialog1 = new JDialog(homeFrame);
						JPanel container = new JPanel();
						JLabel parikhMatrixInfo = new JLabel("<html><center>Please enter an integer larger than 2.</center><html>");
						container.add(parikhMatrixInfo);
						container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
						Point location = MouseInfo.getPointerInfo().getLocation(); 
						int x = (int) location.getX();
						int y = (int) location.getY();
						dialog1.setLocation(x, y);
						dialog1.setUndecorated(true);
						dialog1.add(container);
						dialog1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						dialog1.setVisible(true);
						dialog1.pack();
						dialog1.addWindowFocusListener(new WindowFocusListener() {
							public void windowGainedFocus(WindowEvent e) {
							}
							public void windowLostFocus(WindowEvent e) {
								if (SwingUtilities.isDescendingFrom(e.getOppositeWindow(), dialog1)) {
									return;
								}
								dialog1.setVisible(false);
							}
						});
					}
				});
				northPanel1.add(sizeField);
				northPanel1.add(pmHelpIconLabel);
				//northPanel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
				GridBagConstraints npc = new GridBagConstraints();
				npc.gridx=0;
				npc.gridy=0;
				northPanel.add(northPanel1, npc);
				//Panel to hold Enter button and error messages
				JPanel northPanel2 = new JPanel(new GridBagLayout());  
				GridBagConstraints np2 = new GridBagConstraints();
				JPanel enter = new JPanel();
				JButton btn = new JButton("Enter");
				btn.setPreferredSize(btnsize);
				enter.add(btn);
				np2.gridx=0;
				np2.gridy=0;
				northPanel2.add(enter,np2);
				JPanel errortext = new JPanel();
				res = new JLabel();
				res.setVisible(false);
				Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
				res.setForeground(Color.RED);
				errortext.add(res);
				np2.gridy=1;
				northPanel2.add(errortext,np2);
				GridBagConstraints np2_finish = new GridBagConstraints();
				np2_finish.gridheight = GridBagConstraints.REMAINDER;
				np2_finish.anchor = GridBagConstraints.SOUTH;
				np2_finish.weighty = 1;
				//np2_finish.gridy=5;
				JPanel panelToAdd = new JPanel();
				panelToAdd.setOpaque(false);
				northPanel2.add(panelToAdd, np2_finish, -1);
				npc.gridy=1;
				northPanel.add(northPanel2, npc);
				GridBagConstraints np_finish = new GridBagConstraints();
				np_finish.gridheight = GridBagConstraints.REMAINDER;
				np_finish.anchor = GridBagConstraints.SOUTH;
				np_finish.weighty = 1;
				np_finish.gridy=5;
				JPanel panelToAdd4 = new JPanel();
				panelToAdd4.setOpaque(false);
				northPanel.add(panelToAdd4, np_finish, -1);
				GridBagConstraints ih = new GridBagConstraints();
				ih.gridx=0;
				ih.gridy=0;
				ih.anchor=GridBagConstraints.PAGE_START;
				inputholder.add(northPanel,ih);
				GridBagConstraints ih_finish = new GridBagConstraints();
				ih_finish.gridheight = GridBagConstraints.REMAINDER;
				ih_finish.anchor = GridBagConstraints.SOUTH;
				ih_finish.weighty = 1;
				ih_finish.gridy=5;
				JPanel panelToAdd2 = new JPanel();
				panelToAdd2.setOpaque(false);
				inputholder.add(panelToAdd2, ih_finish, -1);
				//Panel to hold matrix and submit button - only appears if input is correct
				matError = new JLabel();
				matError.setVisible(false);
				JPanel input = new JPanel();
				input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));
				//input.setLayout( new GridLayout( 0,1 ) );
				save = new JCheckBox("Save output as text file?");
				//save.setBorder(new EmptyBorder(0,50,0,0));
				btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e4){
						String content = sizeField.getText();
						int size = -1;
						try{
							size = Integer.parseInt(content);
							if(isValid(size)) {
								res.setVisible(false);
								Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
								res.setForeground(Color.RED);
								Component[] components = input.getComponents();
								for (Component component : components) {
									input.remove(component);  
								}
								input.setAlignmentX(Component.CENTER_ALIGNMENT); 
								save.setAlignmentX(Component.CENTER_ALIGNMENT); 
								input.setVisible(true);
								//input.setBorder(BorderFactory.createLineBorder( Color.black));
								input.add(save);
								BufferedImage alphHelp = null;
								try {
									alphHelp = ImageIO.read(getClass().getResource("/Images/Help.png"));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								Image matHelp = alphHelp.getScaledInstance(20,20,Image.SCALE_SMOOTH);
								ImageIcon matHelpIcon = new ImageIcon(matHelp);
								JLabel matHelpIconLabel=new JLabel(matHelpIcon);
								matHelpIconLabel.setBorder(BorderFactory.createEmptyBorder(5,10,5,5));
								matHelpIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								matHelpIconLabel.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseClicked(MouseEvent e) {
										//JFrame frame = new JFrame("Parikh Matrix Definition");
										JDialog dialog1 = new JDialog(homeFrame);
										JPanel container = new JPanel();
										JLabel parikhMatrixInfo = new JLabel("<html><center>Please enter a non-negative integer into every box.</center><html>");
										container.add(parikhMatrixInfo);
										container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
										Point location = MouseInfo.getPointerInfo().getLocation(); 
										int x = (int) location.getX();
										int y = (int) location.getY();
										dialog1.setLocation(x, y);
										dialog1.setUndecorated(true);
										dialog1.add(container);
										dialog1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
										dialog1.setVisible(true);
										dialog1.pack();
										dialog1.addWindowFocusListener(new WindowFocusListener() {
											public void windowGainedFocus(WindowEvent e) {
											}
											public void windowLostFocus(WindowEvent e) {
												if (SwingUtilities.isDescendingFrom(e.getOppositeWindow(), dialog1)) {
													return;
												}
												dialog1.setVisible(false);
											}
										});
									}
								});

								JLabel matrixInfo=new JLabel("Enter your Parikh matrix:");//,JLabel.RIGHT);
								//matrixInfo.setBorder(new EmptyBorder(0, 120, 0, 0));
								//matrixInfo.setAlignmentX(Component.RIGHT_ALIGNMENT); 

								JPanel matrixInfoP = new JPanel();
								//matrixInfoP.setLayout(new BoxLayout(matrixInfoP,BoxLayout.X_AXIS));
								matrixInfoP.setAlignmentX(Component.CENTER_ALIGNMENT); 
								matrixInfo.setAlignmentX(Component.CENTER_ALIGNMENT); 
								matHelpIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
								//matrixInfoP.setLayout(new FlowLayout());
								matrixInfoP.add(matrixInfo);
								matrixInfoP.add(matHelpIconLabel);
								input.add(matrixInfoP);
								//input.add(matHelpIconLabel);
								JPanel matrixholder = new JPanel();
								JPanel matrix = new JPanel();
								matrix.setLayout(new GridLayout(size,size,2,2));
								int[][] inpMat = new int [size] [size] ;  //matrix to hold submitted text
								inpText = new JTextField[size][size]; //matrix to hold text fields
								for (int i=0; i<=size-1;i++) {
									for (int j=0; j<=size-1;j++) {
										if (i==j) {
											JLabel space = new JLabel("1");
											space.setBorder(new EmptyBorder(0, 12, 0, 0));
											inpMat[i][j]=1;
											matrix.add(space);
										}
										if (j>i) {
											inpText[i][j] = new JTextField(2);
											matrix.add(inpText[i][j]);
										}
										if (j<i) {
											JLabel space = new JLabel("0");
											space.setBorder(new EmptyBorder(0, 12, 0, 0));
											matrix.add(space);
											inpMat[i][j]=0;
										}
									}
								}
								matrix.validate();
								BufferedImage open = null;
								try {
									open = ImageIO.read(getClass().getResource("/Images/OpenBracketClear.png"));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								Dimension matsize = matrix.getPreferredSize();
								int width = (int) Math.round(matsize.getHeight()/70);
								Image opendimg = open.getScaledInstance(15+width, (int) matsize.getHeight(),Image.SCALE_SMOOTH);
								ImageIcon openIcon = new ImageIcon(opendimg);
								JLabel openLabel = new JLabel(openIcon);
								BufferedImage close = null;
								try {
									close = ImageIO.read(getClass().getResource("/Images/CloseBracketClear.png"));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								Image dclose = close.getScaledInstance(15+width, (int) matsize.getHeight(),Image.SCALE_SMOOTH);
								ImageIcon closeIcon = new ImageIcon(dclose);
								JLabel closeLabel = new JLabel(closeIcon);
								openLabel.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
								closeLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));
								matrixholder.add(openLabel);
								matrixholder.add(matrix);
								matrixholder.add(closeLabel);
								matrixholder.setAlignmentX(Component.CENTER_ALIGNMENT); 
								input.add(matrixholder);

								matError.setAlignmentX(Component.CENTER_ALIGNMENT);
								input.add(matError);
								ih.gridy=1;
								inputholder.remove(panelToAdd2);
								inputholder.add(input, ih);
								inputholder.add(panelToAdd2, ih_finish, -1);
								inputholder.revalidate();
								inputholder.repaint();
								homeFrame.revalidate();
								homeFrame.repaint();}
							else {
								res.setVisible(true);
								Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
								res.setForeground(Color.RED);
								Home.dialog.setVisible(false);
								res.setText("Matrix must be of at least size 3x3.");
								input.setVisible(false);
							}
						} catch(NumberFormatException ex) {
							res.setVisible(true);
							Home.dialog.setVisible(false);
							res.setText("Incorrect input type - please enter an integer larger than 2.");
							Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
							res.setForeground(Color.RED);
							input.setVisible(false);
						} 
						int width = (int) (input.getPreferredSize().getWidth()+northPanel.getPreferredSize().getWidth()+panelToAdd2.getPreferredSize().getWidth());
						int height = (int) (input.getPreferredSize().getHeight()+northPanel.getPreferredSize().getHeight()+panelToAdd2.getPreferredSize().getHeight());
						if (height+30<305) {
							height=305;
						}
						if (width+200<800) {
							width=800;
						}
						Dimension idealSize = new Dimension(width+200,height+30);
						inputholder.setPreferredSize(idealSize);
						inputholder.revalidate();
						inputholder.repaint();
						homeFrame.revalidate();
						homeFrame.repaint();
					}
					private boolean isValid(int size) {
						return size > 2;
					}
				});
				c.gridx=0;
				c.gridy=2;
				centraliser.add(inputholder,c);
				JButton associatedWords = new JButton("<html><center>Associated Words</center><html>");
				JButton isParikh = new JButton("<html><center>Is it Parikh?");
				//JButton space2 = new JButton();
				//space2.setVisible(false);
				associatedWords.setPreferredSize(btnsize);
				isParikh.setPreferredSize(btnsize);
				//space2.setPreferredSize(btnsize);
				//associatedWords.addActionListener(new AssociatedWordsInterface());
				//isParikh.addActionListener(new IsParikhInterface());
				isParikh.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){
					try {
						HomeDialogWait wait = new HomeDialogWait();
						mySwingWorker = new SwingWorker<Void, Void>() {@Override protected Void doInBackground() throws Exception {
							IsParikhInterface.actioned();
							return null;
						}};
						mySwingWorker.execute();
						wait.makeWait(e);
						if (mySwingWorker.isDone()){
							wait.close();
						}
						wait.close();
					} catch (OutOfMemoryError oome) {
						Home.res.setText("Error - out of memory.");
						Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
						res.setForeground(Color.RED);
						Home.res.setVisible(true);
						centraliser.revalidate();
						centraliser.repaint();
						homeFrame.revalidate();
						homeFrame.repaint();
					}
				}}); 
				associatedWords.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){
					try {
						HomeDialogWait wait = new HomeDialogWait();
						mySwingWorker = new SwingWorker<Void, Void>() {@Override protected Void doInBackground() throws Exception {
							AssociatedWordsInterface.actioned();
							return null;
						}};
						mySwingWorker.execute();
						wait.makeWait(e);
						if (mySwingWorker.isDone()){
							wait.close();
						}
						wait.close();
					} catch (OutOfMemoryError oome) {
						Home.res.setText("Error - out of memory.");
						Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
						res.setForeground(Color.RED);
						Home.res.setVisible(true);
						centraliser.revalidate();
						centraliser.repaint();
						homeFrame.revalidate();
						homeFrame.repaint();
					}
				}}); 
				//panel.add(space2);
				panel.add(isParikh);
				panel.add(associatedWords);
				c.gridx=0;
				c.gridy=3;
				centraliser.add(panel,c);
				c.gridx=0;
				c.gridy=4;
				centraliser.add(resultsPanel,c);
				centraliser.revalidate();
				centraliser.repaint();
				homeFrame.revalidate();
				homeFrame.repaint();
			}
		});
		wordBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e3){
				//Panel to hold word input
				Component[] icomponents = inputholder.getComponents();
				for (Component icomponent : icomponents) {
					inputholder.remove(icomponent);  
				}
				Component[] pcomponents = panel.getComponents();
				for (Component pcomponent : pcomponents) {
					panel.remove(pcomponent);  
				}
				Component[] rcomponents = resultsPanel.getComponents();
				for (Component rcomponent : rcomponents) {
					resultsPanel.remove(rcomponent);  
				}
				panel.setLayout(new GridLayout(0,3));
				inputholder.setLayout(new BoxLayout(inputholder, BoxLayout.Y_AXIS));
				inputholder.setPreferredSize(new Dimension(1000,305)); 
				JPanel topHolder = new JPanel(new GridBagLayout());
				JPanel word = new JPanel();
				//JPanel information = new JPanel();
				JPanel alphabet = new JPanel();
				JLabel wordText = new JLabel("Word:");
				JLabel infoText = new JLabel("(with the lexicographically smallest letter in the alphabet being either \"0\" or \"a\")");
				infoText.setFont(new Font(infoText.getFont().getName(), Font.PLAIN, 12));
				wordInput = new JTextArea(1,10);
				wordInput.setLineWrap(true);
				BufferedImage wordHelp = null;
				try {
					wordHelp = ImageIO.read(getClass().getResource("/Images/Help.png"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Image dwordHelp = wordHelp.getScaledInstance(20,20,Image.SCALE_SMOOTH);
				ImageIcon wordHelpIcon = new ImageIcon(dwordHelp);
				JLabel wordHelpIconLabel=new JLabel(wordHelpIcon);
				wordHelpIconLabel.setBorder(BorderFactory.createEmptyBorder(5,10,5,5));
				wordHelpIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				wordHelpIconLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//JFrame frame = new JFrame("Parikh Matrix Definition");
						JDialog dialog1 = new JDialog(homeFrame);
						JPanel container = new JPanel();
						JLabel parikhMatrixInfo = new JLabel("<html><center>Please enter a word that consist of only letters or only<br>non-negative integers. The inclusion of any special<br>characters in the word will not be accepted.</center><html>");
						container.add(parikhMatrixInfo);
						container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
						Point location = MouseInfo.getPointerInfo().getLocation(); 
						int x = (int) location.getX();
						int y = (int) location.getY();
						dialog1.setLocation(x, y);
						dialog1.setUndecorated(true);
						dialog1.add(container);
						dialog1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						dialog1.setVisible(true);
						dialog1.pack();
						dialog1.addWindowFocusListener(new WindowFocusListener() {
							public void windowGainedFocus(WindowEvent e) {
							}
							public void windowLostFocus(WindowEvent e) {
								if (SwingUtilities.isDescendingFrom(e.getOppositeWindow(), dialog1)) {
									return;
								}
								dialog1.setVisible(false);
							}
						});
					}
				});
				word.add(wordText);
				word.add(wordInput);
				word.add(wordHelpIconLabel);
				word.add(infoText);

				//information.add(infoText);
				//JLabel alphabetText = new JLabel("Please enter the size of your");
				JLabel link = new JLabel("<html><u>Alphabet</u>:<html>");
				link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				link.setForeground(Color.BLUE);
				link.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//JFrame frame = new JFrame("Parikh Matrix Definition");
						JDialog dialog1 = new JDialog(homeFrame);
						JPanel container = new JPanel();
						JLabel parikhMatrixInfo = new JLabel("<html><center>The set of all letters used to create a word is called an<br>alphabet. When an order is assigned to these letters, we<br>call this an ordered alphabet. We say the size of an<br>alphabet is the number of letters that alphabet contains.</center><html>");
						container.add(parikhMatrixInfo);
						container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
						Point location = MouseInfo.getPointerInfo().getLocation(); 
						int x = (int) location.getX();
						int y = (int) location.getY();
						dialog1.setLocation(x, y);
						dialog1.setUndecorated(true);
						dialog1.add(container);
						dialog1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						dialog1.setVisible(true);
						dialog1.pack();
						dialog1.addWindowFocusListener(new WindowFocusListener() {
							public void windowGainedFocus(WindowEvent e) {
							}
							public void windowLostFocus(WindowEvent e) {
								if (SwingUtilities.isDescendingFrom(e.getOppositeWindow(), dialog1)) {
									return;
								}
								dialog1.setVisible(false);
							}
						});
					}
				});
				alphabetInput = new JTextArea(1,10);
				alphabetInput.setLineWrap(true);
				BufferedImage alphHelp = null;
				try {
					alphHelp = ImageIO.read(getClass().getResource("/Images/Help.png"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Image dalphHelp = alphHelp.getScaledInstance(20,20,Image.SCALE_SMOOTH);
				ImageIcon alphHelpIcon = new ImageIcon(dalphHelp);
				JLabel alphHelpIconLabel=new JLabel(alphHelpIcon);
				alphHelpIconLabel.setBorder(BorderFactory.createEmptyBorder(5,10,5,5));
				alphHelpIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				alphHelpIconLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//JFrame frame = new JFrame("Parikh Matrix Definition");
						JDialog dialog1 = new JDialog(homeFrame);
						JPanel container = new JPanel();
						JLabel parikhMatrixInfo = new JLabel("<html><center>Please enter a positive integer to represent the size of<br>your alphabet. It is assumed that the alphabet begins<br>with either \"0\" or \"a\" by convention. Your alphabet size<br>may not be smaller than the largest letter in your <br>entered word.</center><html>");
						container.add(parikhMatrixInfo);
						container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
						Point location = MouseInfo.getPointerInfo().getLocation(); 
						int x = (int) location.getX();
						int y = (int) location.getY();
						dialog1.setLocation(x, y);
						dialog1.setUndecorated(true);
						dialog1.add(container);
						dialog1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						dialog1.setVisible(true);
						dialog1.pack();
						dialog1.addWindowFocusListener(new WindowFocusListener() {
							public void windowGainedFocus(WindowEvent e) {
							}
							public void windowLostFocus(WindowEvent e) {
								if (SwingUtilities.isDescendingFrom(e.getOppositeWindow(), dialog1)) {
									return;
								}
								dialog1.setVisible(false);
							}
						});
					}
				});
				//alphabet.add(alphabetText);
				alphabet.add(link);
				alphabet.add(alphabetInput);
				JLabel setText1 = new JLabel("(for P-Parikh Matrices)");
				setText1.setFont(new Font(infoText.getFont().getName(), Font.PLAIN, 12));
				JPanel set = new JPanel();
				//JLabel setText = new JLabel("Please enter the ");
				JLabel setlink = new JLabel("<html><u>Projection Set</u>:<html>");
				//JLabel setText2 = new JLabel(" to keep in your projection: ");
				setlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				setlink.setForeground(Color.BLUE);
				setlink.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//JFrame frame = new JFrame("Parikh Matrix Definition");
						JDialog dialog1 = new JDialog(homeFrame);
						JPanel container = new JPanel();
						JLabel parikhMatrixInfo = new JLabel("<html><center>Let S be a set that contains letters in your given<br>alphabet. We define the P-Parikh matrix of a word with respect<br>to S as the Parikh matrix of the word obtained from the<br>following. For each letter in your word, if that letter<br>is in S, it remains, otherwise replace that letter with<br>the empty word.</center><html>");
						container.add(parikhMatrixInfo);
						container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
						Point location = MouseInfo.getPointerInfo().getLocation(); 
						int x = (int) location.getX();
						int y = (int) location.getY();
						dialog1.setLocation(x, y);
						dialog1.setUndecorated(true);
						dialog1.add(container);
						dialog1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						dialog1.setVisible(true);
						dialog1.pack();
						dialog1.addWindowFocusListener(new WindowFocusListener() {
							public void windowGainedFocus(WindowEvent e) {
							}
							public void windowLostFocus(WindowEvent e) {
								if (SwingUtilities.isDescendingFrom(e.getOppositeWindow(), dialog1)) {
									return;
								}
								dialog1.setVisible(false);
							}
						});
					}
				});
				setInput = new JTextArea(1,10);
				setInput.setLineWrap(true);
				BufferedImage setHelp = null;
				try {
					setHelp = ImageIO.read(getClass().getResource("/Images/Help.png"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Image dsetHelp = setHelp.getScaledInstance(20,20,Image.SCALE_SMOOTH);
				ImageIcon setIcon = new ImageIcon(dsetHelp);
				JLabel setHelpIconLabel=new JLabel(setIcon);
				setHelpIconLabel.setBorder(BorderFactory.createEmptyBorder(5,10,5,5));
				setHelpIconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				setHelpIconLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//JFrame frame = new JFrame("Parikh Matrix Definition");
						JDialog dialog1 = new JDialog(homeFrame);
						JPanel container = new JPanel();
						JLabel parikhMatrixInfo = new JLabel("<html><center>Your set will be accepted if it uses either commas or <br>spaces to separate you set elements. You may include <br>\"{\" and \"}\", or \"[\" and \"]\", at the start and end of your set, respectively, <br>if you wish. Each element in the set may be either a single <br>letter or non-negative integer.</center><html>");
						container.add(parikhMatrixInfo);
						container.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
						Point location = MouseInfo.getPointerInfo().getLocation(); 
						int x = (int) location.getX();
						int y = (int) location.getY();
						dialog1.setLocation(x, y);
						dialog1.setUndecorated(true);
						dialog1.add(container);
						dialog1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						dialog1.setVisible(true);
						dialog1.pack();
						dialog1.addWindowFocusListener(new WindowFocusListener() {
							public void windowGainedFocus(WindowEvent e) {
							}
							public void windowLostFocus(WindowEvent e) {
								if (SwingUtilities.isDescendingFrom(e.getOppositeWindow(), dialog1)) {
									return;
								}
								dialog1.setVisible(false);
							}
						});
					}
				});
				//set.add(setText);
				set.add(setlink);
				//set.add(setText2);
				set.add(setInput);
				set.add(setText1);
				set.add(setHelpIconLabel);
				save = new JCheckBox("Save output as text file?");
				res = new JLabel();
				Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
				res.setForeground(Color.RED);
				res.setVisible(false);
				res.setText(INVALID_SIZE);
				JPanel errorPanel = new JPanel();
				errorPanel.add(res);
				JPanel alignmentPanel = new JPanel(new GridBagLayout());
				GridBagConstraints ca = new GridBagConstraints();
				JPanel alignmentPanelHolder = new JPanel();
				wordText.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
				link.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
				setlink.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
				//infoText.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
				setText1.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
				/*alignmentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
				wordText.setAlignmentX(Component.CENTER_ALIGNMENT);
				link.setAlignmentX(Component.CENTER_ALIGNMENT);
				setlink.setAlignmentX(Component.CENTER_ALIGNMENT);*/
				ca.gridx=0;
				ca.gridy=0;
				alignmentPanel.add(new JLabel ("Please enter your:"),ca);
				ca.gridy=1;
				ca.gridx=1;
				ca.anchor=GridBagConstraints.LINE_END;
				alignmentPanel.add(wordText,ca);
				ca.gridx=2;
				ca.anchor=GridBagConstraints.CENTER;
				alignmentPanel.add(wordInput,ca);
				ca.gridx=3;
				ca.anchor=GridBagConstraints.LINE_START;
				alignmentPanel.add(wordHelpIconLabel,ca);
				/*ca.gridx=2;
				ca.anchor=GridBagConstraints.LINE_START;
				alignmentPanel.add(infoText,ca);*/
				ca.gridy=2;
				ca.gridx=1;
				ca.anchor=GridBagConstraints.LINE_END;
				alignmentPanel.add(link,ca);
				ca.gridx=2;
				ca.anchor=GridBagConstraints.CENTER;
				alignmentPanel.add(alphabetInput,ca);
				ca.gridx=3;
				ca.anchor=GridBagConstraints.LINE_START;
				alignmentPanel.add(alphHelpIconLabel,ca);
				ca.gridy=3;
				ca.gridx=1;
				ca.anchor=GridBagConstraints.LINE_END;
				alignmentPanel.add(setlink,ca);
				ca.gridx=2;
				ca.anchor=GridBagConstraints.CENTER;
				alignmentPanel.add(setInput,ca);
				ca.gridx=4;
				ca.gridy=3;
				ca.anchor=GridBagConstraints.LINE_START;
				alignmentPanel.add(setText1,ca);
				ca.gridx=3;
				ca.anchor=GridBagConstraints.LINE_START;
				alignmentPanel.add(setHelpIconLabel,ca);
				alignmentPanelHolder.add(alignmentPanel);
				GridBagConstraints th = new GridBagConstraints();
				th.gridx=0;
				th.gridy=0;
				topHolder.add(alignmentPanelHolder,th);
				th.gridy=1;
				save.setBorder(new EmptyBorder(10,10,10,10));
				topHolder.add(save,th);
				th.gridy=2;
				topHolder.add(errorPanel,th);
				GridBagConstraints th_finish = new GridBagConstraints();
				th_finish.gridheight = GridBagConstraints.REMAINDER;
				th_finish.anchor = GridBagConstraints.SOUTH;
				th_finish.weighty = 1;
				th_finish.gridy=5;
				JPanel panelToAdd4 = new JPanel();
				panelToAdd4.setOpaque(false);
				topHolder.add(panelToAdd4, th_finish, -1);
				inputholder.add(topHolder);
				inputholder.add(Box.createVerticalGlue());
				c.gridx=0;
				c.gridy=2;
				centraliser.add(inputholder,c);
				/*JButton amiableWords = new JButton("<html><center>Find all words that share<br>a Parikh matrix with your word.</center><html>");
				JButton parikhMatrix = new JButton("<html><center>Find the Parikh<br>matrix of your word.</center><html>");
				JButton lParikhMatrix = new JButton("<html><center>Find the L-Parikh<br>matrix of your word.</center><html>");
				JButton lAmiableWords = new JButton("<html><center>Find the words that share an<br>L-Parikh matrix with your word.</center><html>");
				JButton pParikhMatrix = new JButton("<html><center>Find the P-Parikh matrix of your word.</center><html>");
				JButton pAmiableWords = new JButton("<html><center>Find the words that share a<br>P-Parikh matrix with your word.</center><html>");*/
				JButton amiableWords = new JButton("<html><center>Amiable Words</center><html>");
				JButton parikhMatrix = new JButton("<html><center>Parikh Matrix</center><html>");
				JButton lParikhMatrix = new JButton("<html><center>L-Parikh Matrix</center><html>");
				JButton lAmiableWords = new JButton("<html><center>L-Amiable Words</center><html>");
				JButton pParikhMatrix = new JButton("<html><center>P-Parikh Matrix</center><html>");
				JButton pAmiableWords = new JButton("<html><center>P-Amiable Words</center><html>");
				amiableWords.setPreferredSize(btnsize);
				parikhMatrix.setPreferredSize(btnsize);
				lParikhMatrix.setPreferredSize(btnsize);
				lAmiableWords.setPreferredSize(btnsize);
				pParikhMatrix.setPreferredSize(btnsize);
				pAmiableWords.setPreferredSize(btnsize);
				parikhMatrix.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){
					try {
						HomeDialogWait wait = new HomeDialogWait();
						mySwingWorker = new SwingWorker<Void, Void>() {@Override protected Void doInBackground() throws Exception {
							ParikhMatrixInterface.actioned();
							return null;
						}};
						mySwingWorker.execute();
						wait.makeWait(e);
						if (mySwingWorker.isDone()){
							wait.close();
						}
						wait.close();
					} catch (OutOfMemoryError oome) {
						Home.res.setText("Error - out of memory.");
						Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
						res.setForeground(Color.RED);
						Home.res.setVisible(true);
						centraliser.revalidate();
						centraliser.repaint();
						homeFrame.revalidate();
						homeFrame.repaint();
					}
				}}); 
				amiableWords.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){
					try {
						System.gc();
						HomeDialogWait wait = new HomeDialogWait();
						mySwingWorker = new SwingWorker<Void, Void>() {@Override protected Void doInBackground() throws Exception {
							AmiableWordsInterface.actioned();
							return null;
						}};
						mySwingWorker.execute();
						wait.makeWait(e);
						if (mySwingWorker.isDone()){
							wait.close();
						}
						wait.close();
					} catch (OutOfMemoryError oome) {
						Home.res.setText("Error - out of memory.");
						Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
						res.setForeground(Color.RED);
						Home.res.setVisible(true);
						centraliser.revalidate();
						centraliser.repaint();
						homeFrame.revalidate();
						homeFrame.repaint();
					}
				}}); 
				lParikhMatrix.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){
					try {
						HomeDialogWait wait = new HomeDialogWait();
						mySwingWorker = new SwingWorker<Void, Void>() {@Override protected Void doInBackground() throws Exception {
							LParikhMatrixInterface.actioned();
							return null;
						}};
						mySwingWorker.execute();
						wait.makeWait(e);
						if (mySwingWorker.isDone()){
							wait.close();
						}
						wait.close();
					} catch (OutOfMemoryError oome) {
						Home.res.setText("Error - out of memory.");
						Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
						res.setForeground(Color.RED);
						Home.res.setVisible(true);
						centraliser.revalidate();
						centraliser.repaint();
						homeFrame.revalidate();
						homeFrame.repaint();
					}
				}}); 
				lAmiableWords.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){
					try {
						HomeDialogWait wait = new HomeDialogWait();
						mySwingWorker = new SwingWorker<Void, Void>() {@Override protected Void doInBackground() throws Exception {
							LAmiableWordsInterface.actioned();
							return null;
						}};
						mySwingWorker.execute();
						wait.makeWait(e);
						if (mySwingWorker.isDone()){
							wait.close();
						}
						wait.close();
					} catch (OutOfMemoryError oome) {
						Home.res.setText("Error - out of memory.");
						Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
						res.setForeground(Color.RED);
						Home.res.setVisible(true);
						centraliser.revalidate();
						centraliser.repaint();
						homeFrame.revalidate();
						homeFrame.repaint();
					}
				}}); 
				pParikhMatrix.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){
					try {
						HomeDialogWait wait = new HomeDialogWait();
						mySwingWorker = new SwingWorker<Void, Void>() {@Override protected Void doInBackground() throws Exception {
							PParikhMatrixInterface.actioned();
							return null;
						}};
						mySwingWorker.execute();
						wait.makeWait(e);
						if (mySwingWorker.isDone()){
							wait.close();
						}
						wait.close();
					} catch (OutOfMemoryError oome) {
						Home.res.setText("Error - out of memory.");
						Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
						res.setForeground(Color.RED);
						Home.res.setVisible(true);
						centraliser.revalidate();
						centraliser.repaint();
						homeFrame.revalidate();
						homeFrame.repaint();
					}
				}}); 
				pAmiableWords.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){
					try {
						HomeDialogWait wait = new HomeDialogWait();
						mySwingWorker = new SwingWorker<Void, Void>() {@Override protected Void doInBackground() throws Exception {
							PAmiableWordsInterface.actioned();
							return null;
						}};
						mySwingWorker.execute();
						wait.makeWait(e);
						if (mySwingWorker.isDone()){
							wait.close();
						}
						wait.close();
					} catch (OutOfMemoryError oome) {
						Home.res.setText("Error - out of memory.");
						Home.res.setFont(new Font(Home.res.getFont().getName(), Font.BOLD, 14));
						res.setForeground(Color.RED);
						Home.res.setVisible(true);
						centraliser.revalidate();
						centraliser.repaint();
						homeFrame.revalidate();
						homeFrame.repaint();
					}
				}}); 
				panel.add(parikhMatrix);
				panel.add(lParikhMatrix);
				panel.add(pParikhMatrix);
				panel.add(amiableWords);
				panel.add(lAmiableWords);
				panel.add(pAmiableWords);
				c.gridx=0;
				c.gridy=3;
				centraliser.add(panel,c);
				c.gridx=0;
				c.gridy=4;
				centraliser.add(resultsPanel,c);
				centraliser.revalidate();
				centraliser.repaint();
				homeFrame.revalidate();
				homeFrame.repaint();
			}
		});
		GridBagConstraints gbc_finish = new GridBagConstraints();
		gbc_finish.gridheight = GridBagConstraints.REMAINDER;
		gbc_finish.anchor = GridBagConstraints.SOUTH;
		gbc_finish.weighty = 1;
		gbc_finish.gridy=5;
		JPanel panelToAdd4 = new JPanel();
		panelToAdd4.setOpaque(false);
		centraliser.add(panelToAdd4, gbc_finish, -1);
		centraliser.revalidate();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JScrollPane scrPane = new JScrollPane(centraliser,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//scrPane.setMinimumSize(new Dimension(screenSize.width-100,screenSize.height-100));
		scrPane.setPreferredSize(new Dimension(13*(screenSize.width-100)/16,3*(screenSize.height-100)/4));
		scrPane.getVerticalScrollBar().setUnitIncrement(16);
		scrPane.getHorizontalScrollBar().setUnitIncrement(16);
		scrPane.setBorder(new EmptyBorder(0,0,0,0));
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(20,20,0,20));
		topPanel.setLayout(new GridBagLayout());
		GridBagConstraints tp = new GridBagConstraints();
		tp.gridx=0;
		tp.gridy=0;
		//topPanel.add(stop,tp);
		JButton glossaryB = new JButton("Glossary");
		glossaryB.setPreferredSize(btnsize);
		glossaryB.addActionListener(new Glossary());
		tp.gridx=2;
		topPanel.add(glossaryB,tp);
		JPanel filler = new JPanel();
		tp.weightx=1;
		tp.anchor = GridBagConstraints.CENTER;
		tp.gridx=1;
		topPanel.add(filler,tp);

		homeFrame.add(topPanel,BorderLayout.NORTH);
		homeFrame.add(scrPane,BorderLayout.CENTER);
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.pack();
		homeFrame.setLocationRelativeTo(null);
		homeFrame.setVisible(true);
	}

	static class HomeDialogWait {
		public void makeWait(ActionEvent evt) {
			Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
			Home.dialog = new JDialog(win, Dialog.ModalityType.APPLICATION_MODAL);
			JPanel panel = new JPanel(new GridLayout(0,1));
			JComponent loading = new JPanel(new BorderLayout());
			ImageIcon loadingI = new ImageIcon(getClass().getResource("/Images/ajax-loader.gif"));
			loading.add(new JLabel("Loading...", loadingI, JLabel.CENTER),BorderLayout.CENTER);
			panel.add(loading);
			stop = new JButton("Stop");
			stop.setEnabled(true);
			stop.setPreferredSize(new Dimension(100,50));
			stop.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e){
				stop.setEnabled(false);	
				mySwingWorker.cancel(true);
				System.gc();
			}}); 
			panel.add(stop);
			panel.setAlignmentX(Component.CENTER_ALIGNMENT);
			Home.dialog.setSize(new Dimension(200,100));
			Home.dialog.add(panel);
			Home.dialog.setLocationRelativeTo(win);
			Home.dialog.setVisible(true);
		}
		public void close() {
			Home.dialog.setVisible(false);
			Home.dialog.dispose();
		}
	}
}
