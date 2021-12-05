import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
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
import java.util.Arrays;
import java.util.List;
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

public class AmiableWordsInterface extends JPanel{// implements ActionListener{

	private static final long serialVersionUID = 1L;
	//private static JLabel res;
	private static final String INVALID_SIZE = "Alphabet must be of at least size 2.";
	public SwingWorker<Void, Void> mySwingWorker;
	public JFrame frame;

	public AmiableWordsInterface() {
		Home.mySwingWorker.isCancelled();
		JPanel holder = new JPanel(new GridLayout(0,1));
		JPanel output = new JPanel();
		output.setLayout(new BoxLayout(output, BoxLayout.Y_AXIS));
		JPanel matrixholder = new JPanel();
		JPanel wordsP = new JPanel();
		//enterButton.addActionListener(new ActionListener() {
		//	public void actionPerformed(ActionEvent e5){
		//		DialogWait wait = new DialogWait();

		//		mySwingWorker = new SwingWorker<Void, Void>() {

		//		    @Override
		//		    protected Void doInBackground() throws Exception {
		if (!Home.save.isSelected()) {
			try { //checking if alphabet input is valid
				Home.res.setVisible(false);
				output.setVisible(true);
				//setProgress(1);
				String alphIn = Home.alphabetInput.getText();
				int enteredAlphabet;
				if (alphIn.length()==0){
					enteredAlphabet = 0;
				}
				else {
					enteredAlphabet=Integer.parseInt(Home.alphabetInput.getText());
				}
				if (Home.mySwingWorker.isCancelled()) {
					return;
				}
				if(isValid(enteredAlphabet)) {
					Home.res.setVisible(false);
					try { //checking if word input is valid
						Component[] ocomponents = output.getComponents();
						for (Component ocomponent : ocomponents) {
							output.remove(ocomponent);  
						}
						Component[] mcomponents = matrixholder.getComponents();
						for (Component mcomponent : mcomponents) {
							matrixholder.remove(mcomponent);  
						}
						Component[] wcomponents = wordsP.getComponents();
						for (Component wcomponent : wcomponents) {
							wordsP.remove(wcomponent);  
						}
						output.setVisible(true);
						JPanel info1 = new JPanel();
						JLabel infoText = new JLabel("The red word is your entered word.");
						info1.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
						info1.add(infoText);
						output.add(info1);
						String enteredWord = Home.wordInput.getText();
						if (enteredWord.length()==0){
							throw new Exception("No word.");
						}
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						ParikhMatrixCalculator pM = new ParikhMatrixCalculator();
						int[] word = new int[enteredWord.length()];
						int[] finalWord = new int[enteredWord.length()];
						if (!isNumber(enteredWord)) {
							for (int i=0;i<enteredWord.length();i++) {
								String character = Character.toString(enteredWord.charAt(i));
								int l = pM.getNumberForChar(character)-1;
								if (l>=0){
									word[i] = l;
									finalWord[i] = l;
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
								finalWord[i] = l;
							}
						}
						if (Home.mySwingWorker.isCancelled()) {
							return;
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

						//find Parikh matrix of given word
						int[][] parikhMatrix = pM.ParikhMatrix(word,enteredAlphabet+1);

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
						//JPanel minimatrixholder = new JPanel();
						matrixholder.add(openLabel);
						matrixholder.add(matrix);
						matrixholder.add(closeLabel);
						
						JPanel minimatrix = new JPanel();
						//minimatrix.setLayout(new BoxLayout);
						matrixholder.setLayout(new GridBagLayout());
						GridBagConstraints c = new GridBagConstraints();
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
						parikhMatrixl.setHorizontalAlignment(JLabel.CENTER);
						c.gridx=0;
						c.gridy=0;
						c.fill=GridBagConstraints.HORIZONTAL;
						c.anchor=GridBagConstraints.NORTH;
						c.insets = new Insets(3,0,3,0);

						matrixholder.add(parikhMatrixl,c);
						minimatrix.setMaximumSize(new Dimension(500,20+(int)matsize.getHeight()));
						minimatrix.add(openLabel);
						minimatrix.add(matrix);
						minimatrix.add(closeLabel);
						minimatrix.setAlignmentX(Component.CENTER_ALIGNMENT);
						c.gridx=0;
						c.gridy=1;
						c.fill=GridBagConstraints.HORIZONTAL;
						c.anchor=GridBagConstraints.NORTH;
						matrixholder.add(minimatrix,c);
						
						//minimatrix.add(parikhMatrixl);
						//minimatrix.add(matrixholder);
						output.add(matrixholder); 

						//setProgress(3);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//now find all permutations of firstWord
						PermuteArrayWithDuplicates perm = new PermuteArrayWithDuplicates();
						List<List<Integer>> permute = perm.permute(word);
						//System.out.println(permute);
						//setProgress(4);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//make it an output-able form for error checking
						String perms =Arrays.toString(permute.toArray());
						JLabel permsLabel = new JLabel(perms);
						permsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

						//remove any words with incorrect matrix
						int numWords = permute.size();
						int[][] results = new int[numWords][word.length];
						int counter = 0;
						int currentValue=0;
						for (int i=0;i<numWords;i++) {
							if (Home.mySwingWorker.isCancelled()) {
								return;
							}
							currentValue =+ currentValue;
							List<Integer> cW = permute.get(i);
							int[] currentWord = cW.stream().mapToInt(j->j).toArray(); 
							ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
							int[][] currentParikhMatrix = cPM.ParikhMatrix(currentWord,enteredAlphabet+1);
							if (Arrays.deepEquals(parikhMatrix,currentParikhMatrix)) {
								results[counter] = currentWord;
								counter = counter+1;
							}
						}

						//setProgress(5);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//convert list of arrays into words that use letters
						JPanel resultsLabel = new JPanel();
						resultsLabel.setLayout(new BoxLayout(resultsLabel, BoxLayout.Y_AXIS));
						JLabel numWords1;
						JLabel numWords2;
						JLabel numWords3;
						if (counter==1) {
							numWords1 = new JLabel("There is");
							numWords2 = new JLabel(Integer.toString(counter));
							numWords3 = new JLabel("word amiable with your word:");
						}
						else {
							numWords1 = new JLabel("There are");
							numWords2 = new JLabel(Integer.toString(counter));
							numWords3 = new JLabel("words amiable with your word:");
						}
						Font font = numWords1.getFont();
						numWords1.setFont(new Font(font.getName(), Font.PLAIN, 18));
						numWords2.setFont(new Font(font.getName(), Font.PLAIN, 18));
						numWords3.setFont(new Font(font.getName(), Font.PLAIN, 18));
						JPanel numWordsP = new JPanel();
						numWordsP.add(numWords1);
						numWordsP.add(numWords2);
						numWordsP.add(numWords3);
						resultsLabel.add(numWordsP);
						if (counter>0) {
							for (int i=0;i<counter;i++) {
								String convertWord = new String("");
								for (int j=0;j<word.length;j++) {
									ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
									convertWord = convertWord + cPM.getCharForNumber(results[i][j]);
								}
								JLabel words = new JLabel(convertWord);
								font = words.getFont();
								words.setFont(new Font(font.getName(), Font.PLAIN, 18));
								if (Arrays.equals(finalWord,results[i])) {
									words.setForeground(Color.red);
								}
								words.setAlignmentX(Component.CENTER_ALIGNMENT);
								resultsLabel.add(words);
							}
						}
						else {
							JLabel noSol = new JLabel("No words associated to this matrix.");
							noSol.setFont(new Font(noSol.getFont().getName(), Font.PLAIN, 18));
							resultsLabel.add(noSol);
						}
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//setProgress(6);
						//output final list and make it look clear
						resultsLabel.setBorder(BorderFactory.createEmptyBorder(0,30,30,30));
						resultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

						wordsP.add(resultsLabel);
						wordsP.setAlignmentX(Component.CENTER_ALIGNMENT);

						output.add(wordsP);
						add(output,BorderLayout.CENTER);
						revalidate();
						repaint();
						System.gc();

					}
					catch (OutOfMemoryError E) {
						Home.dialog.setVisible(false);
						output.setVisible(false);
						Home.res.setVisible(true);
						Home.res.setText("Out of memory.");
						System.out.println(E.getMessage());
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
					}
				}
				else {
					//setProgress(20);
					Home.dialog.setVisible(false);
					Home.res.setVisible(true);
					output.setVisible(false);
					Home.res.setText(INVALID_SIZE);
				}
			}
			catch (NumberFormatException ex) {
				//setProgress(20);
				Home.dialog.setVisible(false);
				output.setVisible(false);
				Home.res.setVisible(true);
				Home.res.setText("Incorrect alphabet input type - please enter an integer.");
			}
			catch (OutOfMemoryError E) {
				Home.dialog.setVisible(false);
				output.setVisible(false);
				Home.res.setVisible(true);
				Home.res.setText("Out of memory.");
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
                        	wait.progressInfo.setText("Step 1 of 6 - Reading Input");
                        }
                        else if ((Integer) e.getNewValue() == 2) {
                        	wait.progressInfo.setText("Step 2 of 6 - Drawing Matrix");
                        }
                        else if ((Integer) e.getNewValue() == 3) {
                        	wait.progressInfo.setText("Step 3 of 6 - Calculating Permutations");
                        }
                        else if ((Integer) e.getNewValue() == 4) {
                        	wait.progressInfo.setText("Step 4 of 6 - Finding Parikh Matrices");
                        }
                        else if ((Integer) e.getNewValue() == 5) {
                        	wait.progressInfo.setText("Step 5 of 6 - Painting Results");
                        }
                        else if ((Integer) e.getNewValue() == 6) {
                        	wait.progressInfo.setText("Step 6 of 6 - Complete");
                        }
                        else if ((Integer) e.getNewValue() == 20) {
                        	wait.close();
                        }
                    }
                }
        		});
        		mySwingWorker.execute();
        		wait.makeWait(e5); 
        		wait.close();
        	}
			 */

			//}
			//);
		}
		else {
			//enter.add(enterButton);

			//JButton enterFile = new JButton("Enter (and save as .txt file)");
			JFileChooser chooser = new JFileChooser();
			//enterFile.addActionListener(new ActionListener() {
			//public void actionPerformed(ActionEvent e5){
			//	DialogWait wait = new DialogWait();

			//	mySwingWorker = new SwingWorker<Void, Void>() {

			//    @Override
			//    protected Void doInBackground() throws Exception {
			try { //checking if alphabet input is valid
				Home.res.setVisible(false);
				output.setVisible(true);
				//setProgress(1);
				JPanel info1 = new JPanel();
				JLabel infoText = new JLabel("The red word is your entered word.");
				info1.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
				info1.add(infoText);
				output.add(info1);
				if (Home.mySwingWorker.isCancelled()) {
					return;
				}
				String alphIn = Home.alphabetInput.getText();
				int enteredAlphabet;
				if (alphIn.length()==0){
					enteredAlphabet = 0;
				}
				else {
					enteredAlphabet=Integer.parseInt(Home.alphabetInput.getText());
				}
				if(isValid(enteredAlphabet)) {
					if (Home.mySwingWorker.isCancelled()) {
						return;
					}
					Home.res.setVisible(false);
					try { //checking if word input is valid
						Component[] ocomponents = output.getComponents();
						for (Component ocomponent : ocomponents) {
							output.remove(ocomponent);  
						}
						Component[] mcomponents = matrixholder.getComponents();
						for (Component mcomponent : mcomponents) {
							matrixholder.remove(mcomponent);  
						}
						Component[] wcomponents = wordsP.getComponents();
						for (Component wcomponent : wcomponents) {
							wordsP.remove(wcomponent);  
						}
						output.setVisible(true);
						info1.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
						info1.add(infoText);
						output.add(info1);
						String enteredWord = Home.wordInput.getText();
						if (enteredWord.length()==0){
							throw new Exception("No word.");
						}
						ParikhMatrixCalculator pM = new ParikhMatrixCalculator();
						int[] word = new int[enteredWord.length()];
						int[] finalWord = new int[enteredWord.length()];
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						if (!isNumber(enteredWord)) {
							for (int i=0;i<enteredWord.length();i++) {
								String character = Character.toString(enteredWord.charAt(i));
								int l = pM.getNumberForChar(character)-1;
								if (l>=0){
									word[i] = l;
									finalWord[i] = l;
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
								finalWord[i] = l;
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
						//find Parikh matrix of given word
						int[][] parikhMatrix = pM.ParikhMatrix(word,enteredAlphabet+1);


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

						JPanel minimatrix = new JPanel();
						//minimatrix.setLayout(new BoxLayout);
						matrixholder.setLayout(new GridBagLayout());
						GridBagConstraints c = new GridBagConstraints();
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
						parikhMatrixl.setHorizontalAlignment(JLabel.CENTER);
						c.gridx=0;
						c.gridy=0;
						c.fill=GridBagConstraints.HORIZONTAL;
						c.anchor=GridBagConstraints.NORTH;
						c.insets = new Insets(3,0,3,0);

						matrixholder.add(parikhMatrixl,c);
						minimatrix.setMaximumSize(new Dimension(500,20+(int)matsize.getHeight()));
						minimatrix.add(openLabel);
						minimatrix.add(matrix);
						minimatrix.add(closeLabel);
						minimatrix.setAlignmentX(Component.CENTER_ALIGNMENT);
						c.gridx=0;
						c.gridy=1;
						c.fill=GridBagConstraints.HORIZONTAL;
						c.anchor=GridBagConstraints.NORTH;
						matrixholder.add(minimatrix,c);
						output.add(matrixholder); 
						//setProgress(3);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//now find all permutations of firstWord
						PermuteArrayWithDuplicates perm = new PermuteArrayWithDuplicates();
						List<List<Integer>> permute = perm.permute(word);

						//setProgress(4);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//make it an output-able form for error checking
						String perms =Arrays.toString(permute.toArray());
						JLabel permsLabel = new JLabel(perms);
						permsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

						//remove any words with incorrect matrix
						int numWords = permute.size();
						int[][] results = new int[numWords][word.length];
						int counter = 0;
						int currentValue=0;
						for (int i=0;i<numWords;i++) {
							if (Home.mySwingWorker.isCancelled()) {
								return;
							}
							currentValue =+ currentValue;
							List<Integer> cW = permute.get(i);
							int[] currentWord = cW.stream().mapToInt(j->j).toArray(); 
							ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
							int[][] currentParikhMatrix = cPM.ParikhMatrix(currentWord,enteredAlphabet+1);
							if (Arrays.deepEquals(parikhMatrix,currentParikhMatrix)) {
								results[counter] = currentWord;
								counter = counter+1;
							}
						}

						//setProgress(5);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						String convertinputWord = new String("");
						for (int j=0;j<word.length;j++) {
							if (Home.mySwingWorker.isCancelled()) {
								return;
							}
							ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
							convertinputWord = convertinputWord + cPM.getCharForNumber(finalWord[j]);
						}

						//Open save dialog and create file

						chooser.setDialogTitle("Select Folder");
						String defaultTitle = "Words Amiable With " + convertinputWord + ".txt";
						chooser.setCurrentDirectory(new File(Home.filePath));
						chooser.setSelectedFile(new File(defaultTitle));
						int returnVal = chooser.showSaveDialog(null);
						JPanel resultsLabel = new JPanel();

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							String path = chooser.getCurrentDirectory().getAbsolutePath();
							String filename=chooser.getSelectedFile().getName();
							if (!filename.endsWith(".txt")){
								filename=filename+".txt";
							}
							File outputfile = new File(path,filename);
							Home.filePath = chooser.getSelectedFile().getParent();
							outputfile.createNewFile();
							FileWriter write = new FileWriter(outputfile, false);
							FileWriter append = new FileWriter(outputfile, true);
							PrintWriter print_line = new PrintWriter(write);
							PrintWriter add_line = new PrintWriter(append);

							//convert list of arrays into words that use letters

							resultsLabel.setLayout(new BoxLayout(resultsLabel, BoxLayout.Y_AXIS));
							JLabel numWords1;
							JLabel numWords2;
							JLabel numWords3;
							if (counter==1) {
								numWords1 = new JLabel("There is");
								numWords2 = new JLabel(Integer.toString(counter));
								numWords3 = new JLabel("word amiable with your word:");
							}
							else {
								numWords1 = new JLabel("There are");
								numWords2 = new JLabel(Integer.toString(counter));
								numWords3 = new JLabel("words amiable with your word:");
							}
							Font font = numWords1.getFont();
							numWords1.setFont(new Font(font.getName(), Font.PLAIN, 18));
							numWords2.setFont(new Font(font.getName(), Font.PLAIN, 18));
							numWords3.setFont(new Font(font.getName(), Font.PLAIN, 18));
							JPanel numWordsP = new JPanel();
							numWordsP.add(numWords1);
							numWordsP.add(numWords2);
							numWordsP.add(numWords3);
							resultsLabel.add(numWordsP);
							if (counter>0) {
								String start = new String("The Parikh matrix of your word ");
								print_line.printf("%s", start);
								print_line.close();
								add_line.printf("%s"+":"+"%n", convertinputWord);

								for (int i=0;i<parikhMatrix.length;i++) {
									for (int j=0;j<parikhMatrix.length;j++) {
										add_line.printf("%4d", parikhMatrix[i][j]);
									}
									add_line.printf("%n");
								}
								add_line.printf("%n");
								if (counter==1){
									add_line.printf("There is %d word amiable with your word:",counter);
								}
								else {
									add_line.printf("There are %d words amiable with your word:",counter);
								}
								add_line.printf("%n");
								for (int i=0;i<counter;i++) {
									String convertWord = new String("");
									for (int j=0;j<word.length;j++) {
										ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
										convertWord = convertWord + cPM.getCharForNumber(results[i][j]);
									}
									JLabel words = new JLabel(convertWord);
									font = words.getFont();
									words.setFont(new Font(font.getName(), Font.PLAIN, 18));
									if (Arrays.equals(finalWord,results[i])) {
										words.setForeground(Color.red);
									}
									words.setAlignmentX(Component.CENTER_ALIGNMENT);
									resultsLabel.add(words);
									add_line.printf("%s" + "%n", convertWord);
								}
								add_line.close();
							}
							else {
								JLabel noSol = new JLabel("No words associated to this matrix.");
								noSol.setFont(new Font(noSol.getFont().getName(), Font.PLAIN, 18));
								resultsLabel.add(noSol);
							}
						}
						//setProgress(6);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//output final list and make it look clear
						resultsLabel.setBorder(BorderFactory.createEmptyBorder(0,30,30,30));
						resultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

						wordsP.add(resultsLabel);
						wordsP.setAlignmentX(Component.CENTER_ALIGNMENT);

						output.add(wordsP);
						add(output,BorderLayout.CENTER);
						revalidate();
						repaint();

					}
					catch (Exception e1){
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
					}
				}
				else {
					Home.dialog.setVisible(false);
					Home.res.setVisible(true);
					output.setVisible(false);
					Home.res.setText(INVALID_SIZE);
				}
			}
			catch (NumberFormatException ex) {
				Home.dialog.setVisible(false);
				output.setVisible(false);
				Home.res.setVisible(true);
				Home.res.setText("Incorrect alphabet input type - please enter an integer.");
			}
			catch (OutOfMemoryError E) {
				Home.dialog.setVisible(false);
				output.setVisible(false);
				Home.res.setVisible(true);
				Home.res.setText("Out of memory.");
			}
			catch (Exception e) {
				Home.dialog.setVisible(false);
			}

		}
		add(holder,BorderLayout.NORTH);
		Home.dialog.dispose();
	}

	private boolean isValid(Integer size) {
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
		container.add(new AmiableWordsInterface());
		System.gc();
		Home.dialog.setVisible(false);
		Home.resultsPanel.add(container);
		Home.resultsPanel.setMinimumSize(new Dimension(500,500));
		Home.resultsPanel.revalidate();
		Home.resultsPanel.repaint();
		Home.homeFrame.revalidate();
		Home.homeFrame.repaint();
		Home.centraliser.revalidate();
		Home.centraliser.repaint();
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
}