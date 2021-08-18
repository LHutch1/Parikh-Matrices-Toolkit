import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class ParikhMatrixInterface extends JPanel {//implements ActionListener{

	private static final long serialVersionUID = 1L;
	//private static JLabel res;
	private static final String INVALID_SIZE = "Alphabet must be of at least size 2.";
	public SwingWorker<Void, Void> mySwingWorker;
	public JFrame frame;

	public ParikhMatrixInterface() {

		JPanel matrixholder = new JPanel();
		JPanel holder = new JPanel();
		holder.setLayout(new BoxLayout(holder,BoxLayout.Y_AXIS));
		//DialogWait wait = new DialogWait();

		//mySwingWorker = new SwingWorker<Void, Void>() {

		//	@Override
		//	protected Void doInBackground() throws Exception {
		if (!Home.save.isSelected()) {
			try { //checking if alphabet input is valid
				Home.res.setVisible(false);
				matrixholder.setVisible(true);
				String alphIn = Home.alphabetInput.getText();
				int enteredAlphabet;
				if (alphIn.length()==0){
					enteredAlphabet = 0;
				}
				else {
					enteredAlphabet=Integer.parseInt(Home.alphabetInput.getText());
				}
				if(isValid(enteredAlphabet)) {
					Home.res.setVisible(false);
					try { //checking if word input is valid

						//setProgress(1);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						Component[] mcomponents = matrixholder.getComponents();
						for (Component mcomponent : mcomponents) {
							matrixholder.remove(mcomponent);  
						}
						Component[] hcomponents = holder.getComponents();
						for (Component hcomponent : hcomponents) {
							holder.remove(hcomponent);  
						}
						matrixholder.setVisible(true);
						String enteredWord = Home.wordInput.getText();
						ParikhMatrixCalculator pM = new ParikhMatrixCalculator();
						int[] word = new int[enteredWord.length()];
						if (enteredWord.length()==0){
							throw new Exception("No word.");
						}
						if (!isNumber(enteredWord)) {
							for (int i=0;i<enteredWord.length();i++) {
								String character = Character.toString(enteredWord.charAt(i));
								int l = pM.getNumberForChar(character)-1;
								if (l>=0){
									word[i] = l;
								}
								else {
									throw new Exception("Used letters and numbers.");
								}
							}
						}
						else {
							for (int i=0;i<enteredWord.length();i++) {
								int l = Character.getNumericValue(enteredWord.charAt(i));
								word[i] = l;
							}
						}

						//check alphabet size is suitable for word
						int maxLetter = max(word);
						if (alphIn.length()==0) {
							enteredAlphabet = maxLetter+1;
						}
						if (maxLetter>=enteredAlphabet) {
							throw new Exception("Alphabet size not possible.");
						}

						//setProgress(2);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						int[][] parikhMatrix = pM.ParikhMatrix(word,enteredAlphabet+1);

						//setProgress(3);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//add matrix to panel, then add to frame
						JPanel matrix = new JPanel(new GridLayout(parikhMatrix.length,parikhMatrix.length));
						for (int i=0;i<parikhMatrix.length;i++) {
							for (int j=0;j<parikhMatrix.length;j++) {
								JLabel current = new JLabel(Integer.toString(parikhMatrix[i][j]));
								current.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
								matrix.add(current);
							}
						}


						//adding brackets to either side of matrix
						//open bracket
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
						//close bracket
						BufferedImage close = null;
						try {
							close = ImageIO.read(getClass().getResource("/Images/CloseBracketClear.png"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						Image dclose = close.getScaledInstance(15+width, (int) matsize.getHeight(),Image.SCALE_SMOOTH);
						ImageIcon closeIcon = new ImageIcon(dclose);
						JLabel closeLabel = new JLabel(closeIcon);

						//add borders to brackets to give some space
						openLabel.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
						closeLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));
						
						JPanel parikhMatrixlp = new JPanel();
						JLabel parikhMatrixl = new JLabel("<html><u>Parikh Matrix</u><html>:");
						parikhMatrixl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						parikhMatrixl.setForeground(Color.BLUE);
						parikhMatrixl.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								JDialog dialog1 = new JDialog(frame);
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


						Font parikhMatrixFontl = parikhMatrixl.getFont();
						parikhMatrixl.setFont(new Font(parikhMatrixFontl.getName(), Font.PLAIN, 16));
						//parikhMatrixl.setHorizontalAlignment(JLabel.CENTER);
						parikhMatrixlp.add(parikhMatrixl);
						holder.add(parikhMatrixlp);
						//add to panel
						matrixholder.add(openLabel);
						matrixholder.add(matrix);
						matrixholder.add(closeLabel);
						holder.add(matrixholder); 
						holder.setAlignmentX(CENTER_ALIGNMENT);
						add(holder);
						//setProgress(4);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						revalidate();
						repaint();

					}
					catch (Exception e1){
						//setProgress(20);
						Home.dialog.setVisible(false);
						Home.res.setVisible(true);
						Home.res.setText("Please enter a word that consist of only letters or only numbers.");
						String badAlphabet = new String("Alphabet size not possible.");
						String noWord = new String("No word.");
						if (badAlphabet.equals(e1.getMessage())) {
							Home.res.setText("Alphabet size not possible - check entered word.");
						}
						else if (noWord.equals(e1.getMessage())){
							Home.res.setText("");
						}
						else {
							Home.res.setText("Please enter a word that consists of only letters or only numbers.");
						}
						matrixholder.setVisible(false);
					}
				}
				else {
					//setProgress(20);
					Home.dialog.setVisible(false);
					Home.res.setVisible(true);
					matrixholder.setVisible(false);
					Home.res.setText(INVALID_SIZE);
				}
			}
			catch (NumberFormatException ex) {
				//setProgress(20);
				Home.dialog.setVisible(false);
				matrixholder.setVisible(false);
				Home.res.setVisible(true);
				Home.res.setText("Incorrect alphabet input type - please enter an integer.");
			}
		}
		//wait.close();
		//return null;
		//}
		//};

		/*mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e){
				if ("progress".equals(e.getPropertyName())) {
					//progressBar.setIndeterminate(false);
					wait.progressBar.setValue((Integer) e.getNewValue());
					if ((Integer) e.getNewValue() == 1) {
						wait.progressInfo.setText("Step 1 of 4 - Reading Input");
					}
					else if ((Integer) e.getNewValue() == 2) {
						wait.progressInfo.setText("Step 2 of 4 - Calculating Parikh Matrix");
					}
					else if ((Integer) e.getNewValue() == 3) {
						wait.progressInfo.setText("Step 3 of 4 - Compiling Results");
					}
					else if ((Integer) e.getNewValue() == 4) {
						wait.progressInfo.setText("Step 4 of 4 - Complete");
					}
					else if ((Integer) e.getNewValue() == 20) {
						wait.close();
					}
				}
			}
		});
		 */
		//mySwingWorker.execute();
		//wait.makeWait();
		//}

		else {

			JFileChooser chooser = new JFileChooser();
			//JButton inpFile = new JButton("Submit (and save as .txt file)");
			//inpFile.setAlignmentX(Component.CENTER_ALIGNMENT);

			//Calculate associated words and display them when "Submit" is pressed
			//inpFile.addActionListener(new ActionListener() {
			//	public void actionPerformed(ActionEvent e3){
			//		DialogWait wait = new DialogWait();

			//		mySwingWorker = new SwingWorker<Void, Void>() {

			//		    @Override
			//		    protected Void doInBackground() throws Exception {
			try { //checking if alphabet input is valid
				Home.res.setVisible(false);
				matrixholder.setVisible(true);
				String alphIn = Home.alphabetInput.getText();
				int enteredAlphabet;
				if (alphIn.length()==0){
					enteredAlphabet = 0;
				}
				else {
					enteredAlphabet=Integer.parseInt(Home.alphabetInput.getText());
				}
				if(isValid(enteredAlphabet)) {
					Home.res.setVisible(false);
					try { //checking if word input is valid

						//setProgress(1);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						Component[] mcomponents = matrixholder.getComponents();
						for (Component mcomponent : mcomponents) {
							matrixholder.remove(mcomponent);  
						}
						matrixholder.setVisible(true);
						String enteredWord = Home.wordInput.getText();
						ParikhMatrixCalculator pM = new ParikhMatrixCalculator();
						int[] word = new int[enteredWord.length()];
						if (enteredWord.length()==0){
							throw new Exception("No word.");
						}
						if (!isNumber(enteredWord)) {
							for (int i=0;i<enteredWord.length();i++) {
								String character = Character.toString(enteredWord.charAt(i));
								int l = pM.getNumberForChar(character)-1;
								if (l>=0){
									word[i] = l;
								}
								else {
									throw new Exception("Used letters and numbers.");
								}
							}
						}
						else {
							for (int i=0;i<enteredWord.length();i++) {
								int l = Character.getNumericValue(enteredWord.charAt(i));
								word[i] = l;
							}
						}

						//check alphabet size is suitable for word
						int maxLetter = max(word);
						if (alphIn.length()==0) {
							enteredAlphabet = maxLetter+1;
						}
						if (maxLetter>=enteredAlphabet) {
							throw new Exception("Alphabet size not possible.");
						}

						//setProgress(2);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						int[][] parikhMatrix = pM.ParikhMatrix(word,enteredAlphabet+1);

						//setProgress(3);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//add matrix to panel, then add to frame
						JPanel matrix = new JPanel(new GridLayout(parikhMatrix.length,parikhMatrix.length));
						for (int i=0;i<parikhMatrix.length;i++) {
							for (int j=0;j<parikhMatrix.length;j++) {
								JLabel current = new JLabel(Integer.toString(parikhMatrix[i][j]));
								current.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
								matrix.add(current);
							}
						}


						//adding brackets to either side of matrix
						//open bracket
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
						//close bracket
						BufferedImage close = null;
						try {
							close = ImageIO.read(getClass().getResource("/Images/CloseBracketClear.png"));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						Image dclose = close.getScaledInstance(15+width, (int) matsize.getHeight(),Image.SCALE_SMOOTH);
						ImageIcon closeIcon = new ImageIcon(dclose);
						JLabel closeLabel = new JLabel(closeIcon);

						//add borders to brackets to give some space
						openLabel.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
						closeLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));

						//add to panel
						matrixholder.add(openLabel);
						matrixholder.add(matrix);
						matrixholder.add(closeLabel);
						
						JPanel parikhMatrixlp = new JPanel();
						JLabel parikhMatrixl = new JLabel("<html><u>Parikh Matrix</u><html>:");
						parikhMatrixl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						parikhMatrixl.setForeground(Color.BLUE);
						parikhMatrixl.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								JDialog dialog1 = new JDialog(frame);
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


						Font parikhMatrixFontl = parikhMatrixl.getFont();
						parikhMatrixl.setFont(new Font(parikhMatrixFontl.getName(), Font.PLAIN, 16));
						//parikhMatrixl.setHorizontalAlignment(JLabel.CENTER);
						parikhMatrixlp.add(parikhMatrixl);
						holder.add(parikhMatrixlp);
						
						holder.add(matrixholder); 
						add(holder);

						String convertWord = new String("");
						for (int j=0;j<word.length;j++) {
							ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
							convertWord = convertWord + cPM.getCharForNumber(word[j]);
						}	

						//Open save dialog and create file
						chooser.setDialogTitle("Select Folder");
						String defaultTitle = "Parikh Matrix Of " + convertWord + ".txt";
						chooser.setCurrentDirectory(new File(Home.filePath));
						chooser.setSelectedFile(new File(defaultTitle));
						int returnVal = chooser.showSaveDialog(null);

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							Home.filePath = chooser.getSelectedFile().getParent();
							String path = chooser.getCurrentDirectory().getAbsolutePath();
							String filename=chooser.getSelectedFile().getName();
							if (!filename.endsWith(".txt")){
								filename=filename+".txt";
							}
							File output = new File(path,filename);
							output.createNewFile();
							FileWriter write = new FileWriter(output, false);
							FileWriter append = new FileWriter(output, true);
							PrintWriter print_line = new PrintWriter(write);
							PrintWriter add_line = new PrintWriter(append);

							String start = new String("The Parikh matrix of your word ");
							print_line.printf("%s", start);
							print_line.close();
							add_line.printf("%s"+":"+"%n", convertWord);
							for (int i=0;i<parikhMatrix.length;i++) {
								for (int j=0;j<parikhMatrix.length;j++) {
									add_line.printf("%4d", parikhMatrix[i][j]);
								}
								add_line.printf("%n");
							}
							add_line.printf("%n");
							add_line.close();
						}
						//setProgress(4);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						revalidate();
						repaint();

					}
					catch (Exception e1){
						//setProgress(20);
						Home.dialog.setVisible(false);
						Home.res.setVisible(true);
						Home.res.setText("Please enter a word that consist of only letters or only numbers.");
						String noWord = new String("No word.");
						String badAlphabet = new String("Alphabet size not possible.");
						if (badAlphabet.equals(e1.getMessage())) {
							Home.res.setText("Alphabet size not possible - check entered word.");
						}
						else if (noWord.equals(e1.getMessage())){
							Home.res.setText("");
						}
						else {
							Home.res.setText("Please enter a word that consists of only letters or only numbers.");
						}
						matrixholder.setVisible(false);

					}
				}
				else {
					//setProgress(20);
					Home.dialog.setVisible(false);
					Home.res.setVisible(true);
					matrixholder.setVisible(false);
					Home.res.setText(INVALID_SIZE);
				}
			}
			catch (NumberFormatException ex) {
				//setProgress(20);
				Home.dialog.setVisible(false);
				matrixholder.setVisible(false);
				Home.res.setVisible(true);
				Home.res.setText("Incorrect alphabet input type - please enter an integer.");
			}
		}
		//wait.close();
		//return null;
		//}
		//};
		/*
        	mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e){
                    if ("progress".equals(e.getPropertyName())) {
                        //progressBar.setIndeterminate(false);
                        wait.progressBar.setValue((Integer) e.getNewValue());
                        if ((Integer) e.getNewValue() == 1) {
                        	wait.progressInfo.setText("Step 1 of 4 - Reading Input");
                        }
                        else if ((Integer) e.getNewValue() == 2) {
                        	wait.progressInfo.setText("Step 2 of 4 - Calculating Parikh Matrix");
                        }
                        else if ((Integer) e.getNewValue() == 3) {
                        	wait.progressInfo.setText("Step 3 of 4 - Compiling Results");
                        }
                        else if ((Integer) e.getNewValue() == 4) {
                        	wait.progressInfo.setText("Step 4 of 4 - Complete");
                        }
                        else if ((Integer) e.getNewValue() == 20) {
                        	wait.close();
                        }
                    }
                }
            });
        	mySwingWorker.execute();
        	wait.makeWait(e3);
        	}

        	private boolean isValid(int size) {
        		return (size > 1 || size ==0);
            }
        }
        ); */
		Home.dialog.dispose();
	}

	private boolean isValid(int size) {
		return (size > 1 || size ==0);
	}

	public static void actioned() {
		Component[] rcomponents = Home.resultsPanel.getComponents();
		for (Component rcomponent : rcomponents) {
			Home.resultsPanel.remove(rcomponent);  
		}
		Home.res.setVisible(false);
		//make frame scroll-able if matrix is large enough
		JPanel container = new JPanel();
		container.add(new ParikhMatrixInterface());
		/*JScrollPane scrPane = new JScrollPane(container);
		scrPane.getVerticalScrollBar().setUnitIncrement(16);
		scrPane.getHorizontalScrollBar().setUnitIncrement(16);*/
		//Home.stop.setEnabled(false);
		Home.dialog.setVisible(false);
		Home.resultsPanel.add(container);
		Home.resultsPanel.setMinimumSize(new Dimension(500,500));
		Home.resultsPanel.revalidate();
		Home.resultsPanel.repaint();
		Home.centraliser.revalidate();
		Home.centraliser.repaint();
		Home.homeFrame.revalidate();
		Home.homeFrame.repaint();
		/*
		//frame formatting
		frame = new JFrame("The Parikh Matrix Of A Word");
		frame.add(scrPane);
		frame.pack();
		frame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e)
			{
				EnterWordInterface.frame.setVisible(true);
				e.getWindow().dispose();
			}
		});  
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		 */
	}

	private static boolean isNumber(String s) 
	{ 
		for (int i = 0; i < s.length(); i++) 
			if (Character.isDigit(s.charAt(i)) == false) 
				return false; 
		return true; 
	} 

	private static int max(int[] t) {
		int maximum = t[0];   // start with the first value
		for (int i=1; i<t.length; i++) {
			if (t[i] > maximum) {
				maximum = t[i];   // new maximum
			}
		}
		return maximum;
	}

	/*class DialogWait {

		private JDialog dialog;
		public JProgressBar progressBar;
		public JLabel progressInfo;

		public void makeWait(ActionEvent evt) {

			Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
			dialog = new JDialog(win, Dialog.ModalityType.APPLICATION_MODAL);


			JPanel panel = new JPanel(new GridLayout(0,1));
			JLabel loadingLabel = new JLabel("Please wait...");
			loadingLabel.setBorder(new EmptyBorder(15,15,15,15));
			loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

			progressBar = new JProgressBar();
			progressBar.setMinimum(0);
			progressBar.setMaximum(4);
			progressBar.setBorder(new EmptyBorder(0,15,15,15));
			progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

			progressInfo = new JLabel("Step 0 of 4 - Initialising");
			progressInfo.setBorder(new EmptyBorder(0,15,15,15));
			progressInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

			panel.add(loadingLabel);
			panel.add(progressBar);
			panel.add(progressInfo);
			panel.setAlignmentX(Component.CENTER_ALIGNMENT);

			dialog.setSize(new Dimension(300,100));
			dialog.add(panel);
			dialog.setLocationRelativeTo(win);
			dialog.setVisible(true);
		}

		public void close() {
			dialog.dispose();
		}
	}*/
}
