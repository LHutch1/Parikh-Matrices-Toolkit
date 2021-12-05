import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
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
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

public class LAmiableWordsInterface extends JPanel{// implements ActionListener{

	private static final long serialVersionUID = 1L;
	//private static JLabel res;
	private static final String INVALID_SIZE = "Alphabet must be of at least size 2.";
	//private static final float CENTER_ALIGNMENT = 0;
	public SwingWorker<Void, Void> mySwingWorker;
	public JFrame frame;

	@SuppressWarnings("resource")
	public LAmiableWordsInterface() {
		JPanel holder = new JPanel();
		holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));
		JPanel matrixholder = new JPanel();
		matrixholder.setLayout(new BoxLayout(matrixholder, BoxLayout.X_AXIS));
		matrixholder.setAlignmentY(Component.TOP_ALIGNMENT);
		JPanel outputholder = new JPanel();
		outputholder.setLayout(new BoxLayout(outputholder, BoxLayout.Y_AXIS));
		if (!Home.save.isSelected()) {
			/*enterButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e3){
        		DialogWait wait = new DialogWait();

        		mySwingWorker = new SwingWorker<Void, Void>() {

        		    @Override
        		    protected Void doInBackground() throws Exception { */
			try { //checking if alphabet input is valid
				Home.res.setVisible(false);
				JPanel info1 = new JPanel();
				JLabel infoText = new JLabel("The red words are your entered word and its");
				JLabel link = new JLabel("<html><u>Lyndon conjugate.</u><html>");
				link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				link.setForeground(Color.BLUE);
				link.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						JDialog dialog1 = new JDialog(frame);
						JPanel container = new JPanel();
						JLabel parikhMatrixInfo = new JLabel("<html><center>The Lyndon conjugate of a word is the conjugate that is<br>lexicographically smallest based on the order of the alphabet.</center><html>");
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
				info1.add(infoText);
				info1.add(link);
				outputholder.add(info1);
				JPanel info2 = new JPanel();
				//info2.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
				JLabel infoText2 = new JLabel("The orange words are the words that are");
				JLabel link2 = new JLabel("<html><u>L-amiable</u><html>");
				JLabel infoText3 = new JLabel("with your word, and their Lyndon conjugates.");
				link2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				link2.setForeground(Color.BLUE);
				link2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						JDialog dialog1 = new JDialog(frame);
						JPanel container = new JPanel();
						JLabel parikhMatrixInfo = new JLabel("<html><center>Two words are L-amiable if they share<br>the same Parikh and L-Parikh matrices.</center><html>");
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
				info2.add(infoText2);
				info2.add(link2);
				info2.add(infoText3);
				outputholder.add(info2);

				JLabel infoText4 = new JLabel("When a word's Lyndon conjugate is of interest, we display both words together as follows: Original Word (Lyndon Conjugate)");
				JPanel info3 = new JPanel();
				info3.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
				info3.add(infoText4);
				outputholder.add(info3);

				outputholder.setVisible(true);
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
						Component[] ocomponents = outputholder.getComponents();
						for (Component ocomponent : ocomponents) {
							outputholder.remove(ocomponent);  
						}
						Component[] mcomponents = matrixholder.getComponents();
						for (Component mcomponent : mcomponents) {
							matrixholder.remove(mcomponent);  
						}
						outputholder.setVisible(true);

						link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						link.setForeground(Color.BLUE);
						link.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								JDialog dialog1 = new JDialog(frame);
								JPanel container = new JPanel();
								JLabel parikhMatrixInfo = new JLabel("<html><center>The Lyndon conjugate of a word is the conjugate that is<br>lexicographically smallest based on the order of the alphabet.</center><html>");
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
						info1.add(infoText);
						info1.add(link);
						outputholder.add(info1);
						//info2.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
						link2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						link2.setForeground(Color.BLUE);
						link2.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								JDialog dialog1 = new JDialog(frame);
								JPanel container = new JPanel();
								JLabel parikhMatrixInfo = new JLabel("<html><center>Two words are L-amiable if they share<br>the same Parikh and L-Parikh matrices.</center><html>");
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
						info2.add(infoText2);
						info2.add(link2);
						info2.add(infoText3);
						outputholder.add(info2);

						info3.add(infoText4);
						outputholder.add(info3);

						String enteredWord = Home.wordInput.getText();
						if (enteredWord.length()==0){
							throw new Exception("No word.");
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
									finalWord[i]=l;
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
						int[][] parikhMatrix = pM.ParikhMatrix(word,enteredAlphabet+1);

						//add matrix to panel, then add to frame
						JPanel matrix = new JPanel(new GridLayout(parikhMatrix.length,parikhMatrix.length));
						for (int i=0;i<parikhMatrix.length;i++) {
							for (int j=0;j<parikhMatrix.length;j++) {
								JLabel current = new JLabel(Integer.toString(parikhMatrix[i][j]));
								current.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
								current.setAlignmentX(Component.CENTER_ALIGNMENT);
								matrix.add(current);
							}
						}

						//setProgress(3);
						if (Home.mySwingWorker.isCancelled()) {
							return;
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
						JPanel minimatrix = new JPanel();
						JPanel parikh = new JPanel();
						parikh.setLayout(new GridBagLayout());
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

						parikh.add(parikhMatrixl,c);
						minimatrix.setMaximumSize(new Dimension(500,20+(int)matsize.getHeight()));
						minimatrix.add(openLabel);
						minimatrix.add(matrix);
						minimatrix.add(closeLabel);
						minimatrix.setAlignmentX(Component.CENTER_ALIGNMENT);
						c.gridx=0;
						c.gridy=1;
						c.fill=GridBagConstraints.HORIZONTAL;
						c.anchor=GridBagConstraints.NORTH;
						parikh.add(minimatrix,c);

						//setProgress(4);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//now find all permutations of firstWord
						PermuteArrayWithDuplicates perm = new PermuteArrayWithDuplicates();
						List<List<Integer>> permute = perm.permute(word);

						//setProgress(5);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//remove any words with incorrect matrix
						int numWords = permute.size();
						int[][] results = new int[numWords][word.length];
						int counter = 0;
						int[][] matchIndex = new int [numWords][2];
						for (int i=0;i<numWords;i++) {
							List<Integer> cW = permute.get(i);
							int[] currentWord = cW.stream().mapToInt(j->j).toArray(); 
							ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
							int[][] currentParikhMatrix = cPM.ParikhMatrix(currentWord,enteredAlphabet+1);
							if (Arrays.deepEquals(parikhMatrix,currentParikhMatrix)) {
								results[counter] = currentWord;
								counter = counter+1;
								matchIndex[i][0]=1;
							}
							else {
								matchIndex[i][0]=0;
							}
						}

						//setProgress(6);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						LyndonConjugate lC = new LyndonConjugate();
						int[] lword = lC.lyndonConj(finalWord);
						int[][] lparikhMatrix = pM.ParikhMatrix(lword,enteredAlphabet+1);

						//setProgress(7);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//remove any words with incorrect matrix
						int lnumWords = permute.size();
						int[][] lresults = new int[lnumWords][lword.length];
						int lcounter = 0;
						ParikhMatrixCalculator lcPM = new ParikhMatrixCalculator();
						for (int i=0;i<lnumWords;i++) {
							List<Integer> lcW = permute.get(i);
							int[] currentWord = lcW.stream().mapToInt(j->j).toArray(); 
							int[] lcurrentWord = lC.lyndonConj(currentWord);
							int[][] lcurrentParikhMatrix = lcPM.ParikhMatrix(lcurrentWord,enteredAlphabet+1);
							if (Arrays.deepEquals(lparikhMatrix,lcurrentParikhMatrix)) {
								lresults[lcounter] = lcurrentWord;
								lcounter = lcounter+1;
								matchIndex[i][1]=1;
							}
							else {
								matchIndex[i][1]=0;
							}
						}

						//setProgress(8);
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
						int amiablecounter = 0;
						if (counter>0) {
							for (int i=0;i<numWords;i++) {
								if (matchIndex[i][0]==1) {
									String convertWord = new String("");
									List<Integer> cW = permute.get(i);
									int[] currentWord = cW.stream().mapToInt(j->j).toArray(); 
									for (int j=0;j<word.length;j++) {
										ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
										convertWord = convertWord + cPM.getCharForNumber(currentWord[j]);
									}
									JLabel words = new JLabel(convertWord);
									font = words.getFont();
									words.setFont(new Font(font.getName(), Font.PLAIN, 18));
									words.setHorizontalAlignment(JLabel.CENTER);
									if (Arrays.equals(finalWord,currentWord)) {
										words.setForeground(Color.red);
									}
									else if (matchIndex[i][1]==1 && matchIndex[i][0]==1) {
										Color darkOrange = new Color(230,130,0);
										words.setForeground(darkOrange);
									}
									words.setAlignmentX(Component.CENTER_ALIGNMENT);
									resultsLabel.add(words);
									amiablecounter = amiablecounter + 1;
								}
							}
						}
						else {
							JLabel noSol = new JLabel("No words associated to this matrix.");
							noSol.setFont(new Font(noSol.getFont().getName(), Font.PLAIN, 18));
							resultsLabel.add(noSol);

						}


						//output final list and make it look clear
						JPanel wordLabel = new JPanel();
						resultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
						//wordLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
						wordLabel.setLayout(new BoxLayout(wordLabel, BoxLayout.Y_AXIS));
						wordLabel.add(resultsLabel);
						c.gridx=0;
						c.gridy=2;
						c.fill=GridBagConstraints.HORIZONTAL;
						c.anchor=GridBagConstraints.NORTH;
						parikh.add(wordLabel,c);
						parikh.setBorder(BorderFactory.createEmptyBorder(0,0,0,15));
						matrixholder.add(parikh);
						outputholder.add(matrixholder); 

						//now do the same but for the Lyndon conjugate of word
						//first find Lyndon conjugate


						//add matrix to panel, then add to frame
						JPanel lmatrix = new JPanel(new GridLayout(lparikhMatrix.length,lparikhMatrix.length));
						for (int i=0;i<lparikhMatrix.length;i++) {
							for (int j=0;j<lparikhMatrix.length;j++) {
								JLabel lcurrent = new JLabel(Integer.toString(lparikhMatrix[i][j]));
								lcurrent.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
								lcurrent.setAlignmentX(Component.CENTER_ALIGNMENT);
								lmatrix.add(lcurrent);
							}
						}


						//adding brackets to either side of matrix
						//open bracket
						BufferedImage lopen = null;
						try {
							lopen = ImageIO.read(getClass().getResource("/Images/OpenBracketClear.png"));
						} catch (IOException le1) {
							le1.printStackTrace();
						}
						Dimension lmatsize = lmatrix.getPreferredSize();
						int lwidth = (int) Math.round(lmatsize.getHeight()/70);
						Image lopendimg = lopen.getScaledInstance(15+lwidth, (int) lmatsize.getHeight(),Image.SCALE_SMOOTH);
						ImageIcon lopenIcon = new ImageIcon(lopendimg);
						JLabel lopenLabel = new JLabel(lopenIcon);
						//close bracket
						BufferedImage lclose = null;
						try {
							lclose = ImageIO.read(getClass().getResource("/Images/CloseBracketClear.png"));
						} catch (IOException le1) {
							le1.printStackTrace();
						}
						Image ldclose = lclose.getScaledInstance(15+lwidth, (int) lmatsize.getHeight(),Image.SCALE_SMOOTH);
						ImageIcon lcloseIcon = new ImageIcon(ldclose);
						JLabel lcloseLabel = new JLabel(lcloseIcon);

						//add borders to brackets to give some space
						lopenLabel.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
						lcloseLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));

						//add to panel
						JPanel lminimatrix = new JPanel();
						JPanel lparikh = new JPanel();
						lparikh.setLayout(new GridBagLayout());
						JLabel lParikhMatrix= new JLabel("<html><u>L-Parikh Matrix</u>:<html>");
						lParikhMatrix.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						lParikhMatrix.setForeground(Color.BLUE);
						lParikhMatrix.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								JDialog dialog1 = new JDialog(frame);
								JPanel container = new JPanel();
								JLabel parikhMatrixInfo = new JLabel("<html><center>The L-Parikh matrix of a word is the Parikh matrix<br>of that word's Lyndon conjugate. To reduce a<br>word's ambiguity, we use both it's Parikh<br>matrix and L-Parikh matrix.</center><html>");
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
						Font lParikhMatrixFont = lParikhMatrix.getFont();
						lParikhMatrix.setFont(new Font(lParikhMatrixFont.getName(), Font.PLAIN, 16));
						lParikhMatrix.setHorizontalAlignment(JLabel.CENTER);
						//lParikhMatrix.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
						c.gridx=0;
						c.gridy=0;
						c.fill=GridBagConstraints.HORIZONTAL;
						c.anchor=GridBagConstraints.NORTH;
						lparikh.add(lParikhMatrix,c);
						lminimatrix.setMaximumSize(new Dimension(500,20+(int)matsize.getHeight()));
						lminimatrix.add(lopenLabel);
						lminimatrix.add(lmatrix);
						lminimatrix.add(lcloseLabel);
						lminimatrix.setAlignmentX(Component.CENTER_ALIGNMENT);
						c.gridx=0;
						c.gridy=1;
						c.fill=GridBagConstraints.HORIZONTAL;
						c.anchor=GridBagConstraints.NORTH;
						lparikh.add(lminimatrix,c);

						int lamiablecounter = 0;
						for (int i=0;i<numWords;i++) {
							if (matchIndex[i][0]==1 && matchIndex[i][1]==1) {
								lamiablecounter = lamiablecounter+1;
							}
						}

						//convert list of arrays into words that use letters
						JPanel lresultsLabel = new JPanel();
						lresultsLabel.setLayout(new BoxLayout(lresultsLabel, BoxLayout.Y_AXIS));
						lresultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
						JLabel numWords4;
						JLabel numWords5;
						JLabel numWords6;
						if (lamiablecounter==1) {
							numWords4 = new JLabel("There is");
							numWords5 = new JLabel(Integer.toString(lamiablecounter));
							numWords6 = new JLabel("word L-amiable with your word:");
						}
						else {
							numWords4 = new JLabel("There are");
							numWords5 = new JLabel(Integer.toString(lamiablecounter));
							numWords6 = new JLabel("words L-amiable with your word:");
						}
						numWords4.setFont(new Font(font.getName(), Font.PLAIN, 18));
						numWords5.setFont(new Font(font.getName(), Font.PLAIN, 18));
						numWords6.setFont(new Font(font.getName(), Font.PLAIN, 18));
						JPanel numWordsP2 = new JPanel();
						numWordsP2.add(numWords4);
						numWordsP2.add(numWords5);
						numWordsP2.add(numWords6);
						lresultsLabel.add(numWordsP2);

						for (int i=0;i<numWords;i++) {
							if (matchIndex[i][0]==1 && matchIndex[i][1]==1) {
								String convertWord = new String("");
								String lconvertWord = new String("");
								List<Integer> cW = permute.get(i);
								int[] currentWord = cW.stream().mapToInt(j->j).toArray(); 
								int[] lcurrentWord = lC.lyndonConj(currentWord);
								for (int j=0;j<word.length;j++) {
									convertWord = convertWord + lcPM.getCharForNumber(currentWord[j]);
								}
								for (int j=0;j<word.length;j++) {
									lconvertWord = lconvertWord + lcPM.getCharForNumber(lcurrentWord[j]);
								}
								JLabel lwords = new JLabel();
								lwords.setText(convertWord + " (" + lconvertWord + ")");
								Font lfont = lwords.getFont();
								lwords.setFont(new Font(lfont.getName(), Font.PLAIN, 18));
								if (Arrays.equals(finalWord,currentWord)) {
									lwords.setForeground(Color.red);
								}
								else if (matchIndex[i][1]==1 && matchIndex[i][0]==1) {
									Color darkOrange = new Color(230,130,0);
									lwords.setForeground(darkOrange);
								}
								//lwords.setHorizontalAlignment(JLabel.CENTER);
								lwords.setAlignmentX(Component.CENTER_ALIGNMENT);
								lresultsLabel.add(lwords);
								//lamiablecounter = lamiablecounter+1;
							}
						}
						//lresultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
						JPanel lwordLabel = new JPanel();
						//lwordLabel.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
						lwordLabel.setLayout(new BoxLayout(lwordLabel, BoxLayout.Y_AXIS));
						lwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
						lwordLabel.add(lresultsLabel);
						c.gridx=0;
						c.gridy=2;
						c.fill=GridBagConstraints.HORIZONTAL;
						c.anchor=GridBagConstraints.NORTH;
						lparikh.add(lwordLabel,c);
						lparikh.setBorder(BorderFactory.createEmptyBorder(0,15,0,0));
						GridBagConstraints gbcFiller = new GridBagConstraints();
						gbcFiller.weighty = 1.0;
						gbcFiller.fill = GridBagConstraints.BOTH;
						gbcFiller.gridheight=GridBagConstraints.REMAINDER;
						lparikh.add(Box.createGlue(), gbcFiller);
						matrixholder.add(lparikh);
						outputholder.add(matrixholder); 



						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
						int numNotWords = counter-lamiablecounter;
						double wordSize = (finalWord.length * 55);
						int numColmaybe = (int) Math.floor(screenSize.getWidth()/wordSize);
						int numCol=0;
						if (numColmaybe<numNotWords || numNotWords==0) {
							numCol = numColmaybe;
						}
						else {
							numCol = numNotWords;
						}
						JPanel lnotresultsLabel = new JPanel(new GridLayout(0,numCol));
						lnotresultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
						if (!(amiablecounter==lamiablecounter)) {
							for (int i=0;i<numWords;i++) {
								if (matchIndex[i][0]==1 && matchIndex[i][1]==0) {
									String notconvertWord = new String("");
									String notlconvertWord = new String("");
									List<Integer> cW = permute.get(i);
									int[] notcurrentWord = cW.stream().mapToInt(j->j).toArray(); 
									int[] notlcurrentWord = lC.lyndonConj(notcurrentWord);
									for (int j=0;j<word.length;j++) {
										notconvertWord = notconvertWord + lcPM.getCharForNumber(notcurrentWord[j]);
									}
									notconvertWord = notconvertWord + " (";
									for (int j=0;j<word.length;j++) {
										notlconvertWord = notlconvertWord + lcPM.getCharForNumber(notlcurrentWord[j]);
									}
									notconvertWord = notconvertWord + notlconvertWord + ")";
									JLabel notlwords = new JLabel(notconvertWord);
									Font notlfont = notlwords.getFont();
									notlwords.setFont(new Font(notlfont.getName(), Font.PLAIN, 16));
									notlwords.setHorizontalAlignment(JLabel.CENTER);
									lnotresultsLabel.add(notlwords);
								}
							}

							//output final list and make it look clear
							JPanel notlwordLabel = new JPanel();
							notlwordLabel.setLayout(new BoxLayout(notlwordLabel, BoxLayout.Y_AXIS));
							JPanel notInfo = new JPanel();
							JLabel notInfoBefore = new JLabel();
							int distinctCount = counter - lamiablecounter;
							if (distinctCount==1) {
								notInfoBefore.setText("There is " + distinctCount + " word that is");
							}
							else {
								notInfoBefore.setText("There are " + distinctCount + " words that are");
							}
							JLabel notInfoLink = new JLabel("<html><u>L-distinct</u><html>");
							JLabel notInfoAfter = new JLabel("with your entered word:");
							notInfoLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							notInfoLink.setForeground(Color.BLUE);
							notInfoLink.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent e) {
									JDialog dialog1 = new JDialog(frame);
									JPanel container = new JPanel();
									JLabel parikhMatrixInfo = new JLabel("<html><center>Two words are L-distinct if they share the same<br>Parikh matrix, but have different L-Parikh matrices.</center><html>");
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
							Font notInfoFont = notInfoBefore.getFont();
							notInfoBefore.setFont(new Font(notInfoFont.getName(), Font.PLAIN, 16));
							notInfoLink.setFont(new Font(notInfoFont.getName(), Font.PLAIN, 16));
							notInfoAfter.setFont(new Font(notInfoFont.getName(), Font.PLAIN, 16));
							notInfo.add(notInfoBefore);
							notInfo.add(notInfoLink);
							notInfo.add(notInfoAfter);
							//JLabel notInfo2 = new JLabel("Original Word (Lyndon Conjugate)");
							notInfo.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
							//notInfo2.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
							notInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
							//notInfo2.setAlignmentX(Component.CENTER_ALIGNMENT);
							//notInfo2.setHorizontalAlignment(JLabel.CENTER);
							lnotresultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
							notlwordLabel.add(notInfo);
							//notlwordLabel.add(notInfo2);
							notlwordLabel.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
							notlwordLabel.add(lnotresultsLabel);
							notlwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
							outputholder.add(notlwordLabel);
							holder.add(outputholder);
						}
						else {
							JPanel notlwordLabel = new JPanel();
							notlwordLabel.setLayout(new BoxLayout(notlwordLabel, BoxLayout.Y_AXIS));
							notlwordLabel.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
							JLabel notInfo = new JLabel();
							if (counter==1) {
								notInfo.setText("Your word is described uniquely by its Parikh matrix.");
								notInfo.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
								Font notInfoFont = notInfo.getFont();
								notInfo.setFont(new Font(notInfoFont.getName(), Font.PLAIN, 16));
								notInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
								notlwordLabel.add(notInfo);
							}
							else {
								JPanel notInfoP = new JPanel();
								notInfo.setText("The Parikh matrix of your entered word is not");
								JLabel notInfo2 = new JLabel("<html><u>L-distinguishable</u>.<html>");
								notInfo2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								notInfo2.setForeground(Color.BLUE);
								notInfo2.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseClicked(MouseEvent e) {
										JDialog dialog1 = new JDialog(frame);
										JPanel container = new JPanel();
										JLabel parikhMatrixInfo = new JLabel("<html><center>A Parikh matrix is L-distinguishable if it<br>describes at least two distinct words<br>whose respective L-Parikh matrices are<br>different.</center><html>");
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
								notInfoP.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
								Font notInfoFont = notInfo.getFont();
								notInfo.setFont(new Font(notInfoFont.getName(), Font.PLAIN, 16));
								notInfo2.setFont(new Font(notInfoFont.getName(), Font.PLAIN, 16));
								notInfoP.add(notInfo);
								notInfoP.add(notInfo2);
								notInfoP.setAlignmentX(Component.CENTER_ALIGNMENT);
								notlwordLabel.add(notInfoP);
							}
							notlwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
							outputholder.add(notlwordLabel);
							holder.add(outputholder);
						}

						//setProgress(9);
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
						outputholder.setVisible(false);

					}
				}
				else {
					//setProgress(20);
					Home.dialog.setVisible(false);
					Home.res.setVisible(true);
					outputholder.setVisible(false);
					Home.res.setText(INVALID_SIZE);
				}
			}
			catch (NumberFormatException ex) {
				//setProgress(20);
				Home.dialog.setVisible(false);
				outputholder.setVisible(false);
				Home.res.setVisible(true);
				Home.res.setText("Incorrect alphabet input type - please enter an integer.");
			}
			/*
        	        wait.close();
        	        return null;
        	    }
        	};

        	mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e){
                    if ("progress".equals(e.getPropertyName())) {
                        //progressBar.setIndeterminate(false);
                        wait.progressBar.setValue((Integer) e.getNewValue());
                        if ((Integer) e.getNewValue() == 1) {
                        	wait.progressInfo.setText("Step 1 of 9 - Reading Input");
                        }
                        else if ((Integer) e.getNewValue() == 2) {
                        	wait.progressInfo.setText("Step 2 of 9 - Calculating Parikh Matrix");
                        }
                        else if ((Integer) e.getNewValue() == 3) {
                        	wait.progressInfo.setText("Step 3 of 9 - Painting Parikh Matrix");
                        }
                        else if ((Integer) e.getNewValue() == 4) {
                        	wait.progressInfo.setText("Step 4 of 9 - Calculating Permutations");
                        }
                        else if ((Integer) e.getNewValue() == 5) {
                        	wait.progressInfo.setText("Step 5 of 9 - Finding Parikh Matrices");
                        }
                        else if ((Integer) e.getNewValue() == 6) {
                        	wait.progressInfo.setText("Step 6 of 9 - Calculating Lyndon Conjugate");
                        }
                        else if ((Integer) e.getNewValue() == 7) {
                        	wait.progressInfo.setText("Step 7 of 9 - Calculating L-Parikh Matrices");
                        }
                        else if ((Integer) e.getNewValue() == 8) {
                        	wait.progressInfo.setText("Step 8 of 9 - Compiling Results");
                        }
                        else if ((Integer) e.getNewValue() == 9) {
                        	wait.progressInfo.setText("Step 9 of 9 - Complete");
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



        });*/
		}
		else {
			//JButton enterFile = new JButton("Enter (and save as .txt file)");
			JFileChooser chooser = new JFileChooser();
			/*enterFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e3){
        		DialogWait wait = new DialogWait();

        		mySwingWorker = new SwingWorker<Void, Void>() {

        		    @Override
        		    protected Void doInBackground() throws Exception { */
			try { //checking if alphabet input is valid
				Home.res.setVisible(false);
				JPanel info1 = new JPanel();
				JLabel infoText = new JLabel("The red words are your entered word and its");
				JLabel link = new JLabel("<html><u>Lyndon conjugate.</u><html>");
				link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				link.setForeground(Color.BLUE);
				link.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						JDialog dialog1 = new JDialog(frame);
						JPanel container = new JPanel();
						JLabel parikhMatrixInfo = new JLabel("<html><center>The Lyndon conjugate of a word is the conjugate that is<br>lexicographically smallest based on the order of the alphabet.</center><html>");
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
				info1.add(infoText);
				info1.add(link);
				outputholder.add(info1);
				JPanel info2 = new JPanel();
				//info2.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
				JLabel infoText2 = new JLabel("The orange words are the words that are");
				JLabel link2 = new JLabel("<html><u>L-amiable</u><html>");
				JLabel infoText3 = new JLabel("with your word, and their Lyndon conjugates.");
				link2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				link2.setForeground(Color.BLUE);
				link2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						JDialog dialog1 = new JDialog(frame);
						JPanel container = new JPanel();
						JLabel parikhMatrixInfo = new JLabel("<html><center>Two words are L-amiable if they share<br>the same Parikh and L-Parikh matrices.</center><html>");
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
				info2.add(infoText2);
				info2.add(link2);
				info2.add(infoText3);
				outputholder.add(info2);

				JLabel infoText4 = new JLabel("When a word's Lyndon conjugate is of interest, we display both words together as follows: Original Word (Lyndon Conjugate)");
				JPanel info3 = new JPanel();
				info3.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
				info3.add(infoText4);
				outputholder.add(info3);

				outputholder.setVisible(true);
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
						Component[] ocomponents = outputholder.getComponents();
						for (Component ocomponent : ocomponents) {
							outputholder.remove(ocomponent);  
						}
						Component[] mcomponents = matrixholder.getComponents();
						for (Component mcomponent : mcomponents) {
							matrixholder.remove(mcomponent);  
						}
						outputholder.setVisible(true);

						link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						link.setForeground(Color.BLUE);
						link.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								JDialog dialog1 = new JDialog(frame);
								JPanel container = new JPanel();
								JLabel parikhMatrixInfo = new JLabel("<html><center>The Lyndon conjugate of a word is the conjugate that is<br>lexicographically smallest based on the order of the alphabet.</center><html>");
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
						info1.add(infoText);
						info1.add(link);
						outputholder.add(info1);
						//info2.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
						link2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						link2.setForeground(Color.BLUE);
						link2.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								JDialog dialog1 = new JDialog(frame);
								JPanel container = new JPanel();
								JLabel parikhMatrixInfo = new JLabel("<html><center>Two words are L-amiable if they share<br>the same Parikh and L-Parikh matrices.</center><html>");
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
						info2.add(infoText2);
						info2.add(link2);
						info2.add(infoText3);
						outputholder.add(info2);

						//JLabel infoText4 = new JLabel("When a word's Lyndon conjugate is of interest, we display both words together as follows: Original Word (Lyndon Conjugate)");
						//JPanel info3 = new JPanel();
						info3.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
						info3.add(infoText4);
						outputholder.add(info3);

						String enteredWord = Home.wordInput.getText();
						if (enteredWord.length()==0){
							throw new Exception("No word.");
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
									finalWord[i]=l;
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
						String convertinputWord = new String("");
						for (int j=0;j<word.length;j++) {
							ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
							convertinputWord = convertinputWord + cPM.getCharForNumber(finalWord[j]);
						}

						//Open save dialog and create file

						chooser.setDialogTitle("Select Folder");
						String defaultTitle = "Words L-Amiable With " + convertinputWord + ".txt";
						chooser.setCurrentDirectory(new File(Home.filePath));
						chooser.setSelectedFile(new File(defaultTitle));
						int returnVal = chooser.showSaveDialog(null);
						JPanel resultsLabel = new JPanel();

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							Home.filePath = chooser.getSelectedFile().getParent();
							String path = chooser.getCurrentDirectory().getAbsolutePath();
							String filename=chooser.getSelectedFile().getName();
							if (!filename.endsWith(".txt")){
								filename=filename+".txt";
							}
							File outputfile = new File(path,filename);
							outputfile.createNewFile();
							FileWriter write = new FileWriter(outputfile, false);
							FileWriter append = new FileWriter(outputfile, true);
							PrintWriter print_line = new PrintWriter(write);
							PrintWriter add_line = new PrintWriter(append);

							int[][] parikhMatrix = pM.ParikhMatrix(word,enteredAlphabet+1);

							String start = new String("The Parikh matrix of your word ");
							print_line.printf("%s", start);
							print_line.printf("%s"+":"+"%n", convertinputWord);
							print_line.close();
							for (int i=0;i<parikhMatrix.length;i++) {
								for (int j=0;j<parikhMatrix.length;j++) {
									add_line.printf("%4d", parikhMatrix[i][j]);
								}
								add_line.printf("%n");
							}
							add_line.printf("%n");

							//add matrix to panel, then add to frame
							JPanel matrix = new JPanel(new GridLayout(parikhMatrix.length,parikhMatrix.length));
							for (int i=0;i<parikhMatrix.length;i++) {
								for (int j=0;j<parikhMatrix.length;j++) {
									JLabel current = new JLabel(Integer.toString(parikhMatrix[i][j]));
									current.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
									current.setAlignmentX(Component.CENTER_ALIGNMENT);
									matrix.add(current);
								}
							}

							//setProgress(3);
							if (Home.mySwingWorker.isCancelled()) {
								return;
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
							JPanel minimatrix = new JPanel();
							JPanel parikh = new JPanel();
							parikh.setLayout(new GridBagLayout());
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

							parikh.add(parikhMatrixl,c);
							minimatrix.setMaximumSize(new Dimension(500,20+(int)matsize.getHeight()));
							minimatrix.add(openLabel);
							minimatrix.add(matrix);
							minimatrix.add(closeLabel);
							minimatrix.setAlignmentX(Component.CENTER_ALIGNMENT);
							c.gridx=0;
							c.gridy=1;
							c.fill=GridBagConstraints.HORIZONTAL;
							c.anchor=GridBagConstraints.NORTH;
							parikh.add(minimatrix,c);

							//setProgress(4);
							if (Home.mySwingWorker.isCancelled()) {
								return;
							}
							//now find all permutations of firstWord
							PermuteArrayWithDuplicates perm = new PermuteArrayWithDuplicates();
							List<List<Integer>> permute = perm.permute(word);

							//setProgress(5);
							if (Home.mySwingWorker.isCancelled()) {
								return;
							}
							//remove any words with incorrect matrix
							int numWords = permute.size();
							int[][] results = new int[numWords][word.length];
							int counter = 0;
							int[][] matchIndex = new int [numWords][2];
							for (int i=0;i<numWords;i++) {
								List<Integer> cW = permute.get(i);
								int[] currentWord = cW.stream().mapToInt(j->j).toArray(); 
								ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
								int[][] currentParikhMatrix = cPM.ParikhMatrix(currentWord,enteredAlphabet+1);
								if (Arrays.deepEquals(parikhMatrix,currentParikhMatrix)) {
									results[counter] = currentWord;
									counter = counter+1;
									matchIndex[i][0]=1;
								}
								else {
									matchIndex[i][0]=0;
								}
							}

							//setProgress(6);
							if (Home.mySwingWorker.isCancelled()) {
								return;
							}
							String start2 = new String("The Lyndon conjugate of your word ");
							add_line.printf("%s", start2);
							add_line.printf("%s"+":"+"%n", convertinputWord);
							//add_line.close();

							LyndonConjugate lC = new LyndonConjugate();
							int[] lword = lC.lyndonConj(finalWord);
							int[][] lparikhMatrix = pM.ParikhMatrix(lword,enteredAlphabet+1);

							String convertlinputWord = new String("");
							for (int j=0;j<word.length;j++) {
								ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
								convertlinputWord = convertlinputWord + cPM.getCharForNumber(lword[j]);
							}
							add_line.printf("%s", convertlinputWord);
							add_line.printf("%n");
							add_line.printf("%n");

							String start3 = new String("The L-Parikh matrix of your word ");
							add_line.printf("%s", start3);
							add_line.printf("%s"+":"+"%n", convertinputWord);
							//add_line.close();
							for (int i=0;i<lparikhMatrix.length;i++) {
								for (int j=0;j<lparikhMatrix.length;j++) {
									add_line.printf("%4d", lparikhMatrix[i][j]);
								}
								add_line.printf("%n");
							}
							add_line.printf("%n");


							//add_line.close();

							//setProgress(7);
							if (Home.mySwingWorker.isCancelled()) {
								return;
							}
							//remove any words with incorrect matrix
							int lnumWords = permute.size();
							int[][] lresults = new int[lnumWords][lword.length];
							int lcounter = 0;
							ParikhMatrixCalculator lcPM = new ParikhMatrixCalculator();
							for (int i=0;i<lnumWords;i++) {
								List<Integer> lcW = permute.get(i);
								int[] currentWord = lcW.stream().mapToInt(j->j).toArray(); 
								int[] lcurrentWord = lC.lyndonConj(currentWord);
								int[][] lcurrentParikhMatrix = lcPM.ParikhMatrix(lcurrentWord,enteredAlphabet+1);
								if (Arrays.deepEquals(lparikhMatrix,lcurrentParikhMatrix)) {
									lresults[lcounter] = lcurrentWord;
									lcounter = lcounter+1;
									matchIndex[i][1]=1;
								}
								else {
									matchIndex[i][1]=0;
								}
							}

							int lamiablecounter = 0;
							for (int i=0;i<numWords;i++) {
								if (matchIndex[i][0]==1 && matchIndex[i][1]==1) {
									lamiablecounter = lamiablecounter+1;
								}
							}

							//String start4 = new String("There are %d words that share a Parikh matrix and L-Parikh matrix with your word %s: %n");
							//add_line.printf("%s", start4);
							if (lamiablecounter==1) {
								add_line.printf("There is %d word that shares a Parikh matrix and L-Parikh matrix with your word %s: %n", lamiablecounter,convertinputWord);
							} else {
								add_line.printf("There are %d words that share a Parikh matrix and L-Parikh matrix with your word %s: %n", lamiablecounter,convertinputWord);
							}

							//setProgress(8);
							if (Home.mySwingWorker.isCancelled()) {
								return;
							}
							//convert list of arrays into words that use letters
							//JPanel resultsLabel = new JPanel();
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
							int amiablecounter = 0;
							if (counter>0) {
								for (int i=0;i<numWords;i++) {
									if (matchIndex[i][0]==1) {
										String convertWord = new String("");
										List<Integer> cW = permute.get(i);
										int[] currentWord = cW.stream().mapToInt(j->j).toArray(); 
										for (int j=0;j<word.length;j++) {
											ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
											convertWord = convertWord + cPM.getCharForNumber(currentWord[j]);
										}
										JLabel words = new JLabel(convertWord);
										font = words.getFont();
										words.setFont(new Font(font.getName(), Font.PLAIN, 18));
										words.setHorizontalAlignment(JLabel.CENTER);
										if (Arrays.equals(finalWord,currentWord)) {
											words.setForeground(Color.red);
										}
										else if (matchIndex[i][1]==1 && matchIndex[i][0]==1) {
											Color darkOrange = new Color(230,130,0);
											words.setForeground(darkOrange);
										}
										words.setAlignmentX(Component.CENTER_ALIGNMENT);
										resultsLabel.add(words);
										amiablecounter = amiablecounter + 1;
									}
								}
							}
							else {
								JLabel noSol = new JLabel("No words associated to this matrix.");
								noSol.setFont(new Font(noSol.getFont().getName(), Font.PLAIN, 18));
								resultsLabel.add(noSol);

							}


							//output final list and make it look clear
							JPanel wordLabel = new JPanel();
							resultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
							//wordLabel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
							wordLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,15));
							wordLabel.setLayout(new BoxLayout(wordLabel, BoxLayout.Y_AXIS));
							wordLabel.add(resultsLabel);
							c.gridx=0;
							c.gridy=2;
							c.fill=GridBagConstraints.HORIZONTAL;
							c.anchor=GridBagConstraints.NORTH;
							parikh.add(wordLabel,c);
							//parikh.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
							matrixholder.add(parikh);
							outputholder.add(matrixholder); 

							//now do the same but for the Lyndon conjugate of word
							//first find Lyndon conjugate


							//add matrix to panel, then add to frame
							JPanel lmatrix = new JPanel(new GridLayout(lparikhMatrix.length,lparikhMatrix.length));
							for (int i=0;i<lparikhMatrix.length;i++) {
								for (int j=0;j<lparikhMatrix.length;j++) {
									JLabel lcurrent = new JLabel(Integer.toString(lparikhMatrix[i][j]));
									lcurrent.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
									lcurrent.setAlignmentX(Component.CENTER_ALIGNMENT);
									lmatrix.add(lcurrent);
								}
							}


							//adding brackets to either side of matrix
							//open bracket
							BufferedImage lopen = null;
							try {
								lopen = ImageIO.read(getClass().getResource("/Images/OpenBracketClear.png"));
							} catch (IOException le1) {
								le1.printStackTrace();
							}
							Dimension lmatsize = lmatrix.getPreferredSize();
							int lwidth = (int) Math.round(lmatsize.getHeight()/70);
							Image lopendimg = lopen.getScaledInstance(15+lwidth, (int) lmatsize.getHeight(),Image.SCALE_SMOOTH);
							ImageIcon lopenIcon = new ImageIcon(lopendimg);
							JLabel lopenLabel = new JLabel(lopenIcon);
							//close bracket
							BufferedImage lclose = null;
							try {
								lclose = ImageIO.read(getClass().getResource("/Images/CloseBracketClear.png"));
							} catch (IOException le1) {
								le1.printStackTrace();
							}
							Image ldclose = lclose.getScaledInstance(15+lwidth, (int) lmatsize.getHeight(),Image.SCALE_SMOOTH);
							ImageIcon lcloseIcon = new ImageIcon(ldclose);
							JLabel lcloseLabel = new JLabel(lcloseIcon);

							//add borders to brackets to give some space
							lopenLabel.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
							lcloseLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));

							//add to panel
							JPanel lminimatrix = new JPanel();
							JPanel lparikh = new JPanel();
							lparikh.setLayout(new GridBagLayout());
							JLabel lParikhMatrix= new JLabel("<html><u>L-Parikh Matrix</u>:<html>");
							lParikhMatrix.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							lParikhMatrix.setForeground(Color.BLUE);
							lParikhMatrix.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent e) {
									JDialog dialog1 = new JDialog(frame);
									JPanel container = new JPanel();
									JLabel parikhMatrixInfo = new JLabel("<html><center>The L-Parikh matrix of a word is the Parikh matrix<br>of that word's Lyndon conjugate. To reduce a<br>word's ambiguity, we use both it's Parikh<br>matrix and L-Parikh matrix.</center><html>");
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
							Font lParikhMatrixFont = lParikhMatrix.getFont();
							lParikhMatrix.setFont(new Font(lParikhMatrixFont.getName(), Font.PLAIN, 16));
							lParikhMatrix.setHorizontalAlignment(JLabel.CENTER);
							//lParikhMatrix.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
							c.gridx=0;
							c.gridy=0;
							c.fill=GridBagConstraints.HORIZONTAL;
							c.anchor=GridBagConstraints.NORTH;
							lparikh.add(lParikhMatrix,c);
							lminimatrix.setMaximumSize(new Dimension(500,20+(int)matsize.getHeight()));
							lminimatrix.add(lopenLabel);
							lminimatrix.add(lmatrix);
							lminimatrix.add(lcloseLabel);
							lminimatrix.setAlignmentX(Component.CENTER_ALIGNMENT);
							c.gridx=0;
							c.gridy=1;
							c.fill=GridBagConstraints.HORIZONTAL;
							c.anchor=GridBagConstraints.NORTH;
							lparikh.add(lminimatrix,c);



							//convert list of arrays into words that use letters
							JPanel lresultsLabel = new JPanel();
							lresultsLabel.setLayout(new BoxLayout(lresultsLabel, BoxLayout.Y_AXIS));
							lresultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
							JLabel numWords4;
							JLabel numWords5;
							JLabel numWords6;
							if (lamiablecounter==1) {
								numWords4 = new JLabel("There is");
								numWords5 = new JLabel(Integer.toString(lamiablecounter));
								numWords6 = new JLabel("word L-amiable with your word:");
							}
							else {
								numWords4 = new JLabel("There are");
								numWords5 = new JLabel(Integer.toString(lamiablecounter));
								numWords6 = new JLabel("words L-amiable with your word:");
							}
							numWords4.setFont(new Font(font.getName(), Font.PLAIN, 18));
							numWords5.setFont(new Font(font.getName(), Font.PLAIN, 18));
							numWords6.setFont(new Font(font.getName(), Font.PLAIN, 18));
							JPanel numWordsP2 = new JPanel();
							numWordsP2.add(numWords4);
							numWordsP2.add(numWords5);
							numWordsP2.add(numWords6);
							lresultsLabel.add(numWordsP2);

							for (int i=0;i<numWords;i++) {
								if (matchIndex[i][0]==1 && matchIndex[i][1]==1) {
									String convertWord2 = new String("");
									String lconvertWord = new String("");
									List<Integer> cW = permute.get(i);
									int[] currentWord2 = cW.stream().mapToInt(j->j).toArray(); 
									int[] lcurrentWord = lC.lyndonConj(currentWord2);
									for (int j=0;j<word.length;j++) {
										convertWord2 = convertWord2 + lcPM.getCharForNumber(currentWord2[j]);
									}
									for (int j=0;j<word.length;j++) {
										lconvertWord = lconvertWord + lcPM.getCharForNumber(lcurrentWord[j]);
									}
									JLabel lwords = new JLabel();
									lwords.setText(convertWord2 + " (" + lconvertWord + ")");
									add_line.printf("%s" + "%n", convertWord2);
									//JLabel lwords = new JLabel(lconvertWord);
									Font lfont = lwords.getFont();
									lwords.setFont(new Font(lfont.getName(), Font.PLAIN, 18));
									if (Arrays.equals(finalWord,currentWord2)) {
										lwords.setForeground(Color.red);
									}
									else if (matchIndex[i][1]==1 && matchIndex[i][0]==1) {
										Color darkOrange = new Color(230,130,0);
										lwords.setForeground(darkOrange);
									}
									//lwords.setHorizontalAlignment(JLabel.CENTER);
									lwords.setAlignmentX(Component.CENTER_ALIGNMENT);
									lresultsLabel.add(lwords);
									//lamiablecounter = lamiablecounter+1;
								}
							}
							JPanel lwordLabel = new JPanel();
							lwordLabel.setBorder(BorderFactory.createEmptyBorder(0,15,0,0));
							lwordLabel.setLayout(new BoxLayout(lwordLabel, BoxLayout.Y_AXIS));
							lwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
							lwordLabel.add(lresultsLabel);
							c.gridx=0;
							c.gridy=2;
							c.fill=GridBagConstraints.HORIZONTAL;
							c.anchor=GridBagConstraints.NORTH;
							lparikh.add(lwordLabel,c);
							GridBagConstraints gbcFiller = new GridBagConstraints();
							gbcFiller.weighty = 1.0;
							gbcFiller.fill = GridBagConstraints.BOTH;
							gbcFiller.gridheight=GridBagConstraints.REMAINDER;
							lparikh.add(Box.createGlue(), gbcFiller);
							matrixholder.add(lparikh);
							outputholder.add(matrixholder); 

							add_line.printf("%n");
							//String start5 = new String("There are %d words that share a Parikh matrix with your word ");
							//add_line.printf("%s", start5);
							int distinctCount = counter - lamiablecounter;
							if (distinctCount==1){
								add_line.printf("There is %d word that shares a Parikh matrix with your word %s but does not share an L-Parikh matrix with it:"+"%n", distinctCount,convertinputWord);
							}
							else {
								add_line.printf("There are %d words that share a Parikh matrix with your word %s but do not share an L-Parikh matrix with it:"+"%n", distinctCount,convertinputWord);
							}
							Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
							int numNotWords = counter-lamiablecounter;
							double wordSize = (finalWord.length * 55);
							int numColmaybe = (int) Math.floor(screenSize.getWidth()/wordSize);
							int numCol=0;
							if (numColmaybe<numNotWords || numNotWords==0) {
								numCol = numColmaybe;
							}
							else {
								numCol = numNotWords;
							}
							JPanel lnotresultsLabel = new JPanel(new GridLayout(0,numCol));
							lnotresultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
							if (!(amiablecounter==lamiablecounter)) {
								for (int i=0;i<numWords;i++) {
									if (matchIndex[i][0]==1 && matchIndex[i][1]==0) {
										String notconvertWord = new String("");
										String notlconvertWord = new String("");
										List<Integer> cW = permute.get(i);
										int[] notcurrentWord = cW.stream().mapToInt(j->j).toArray(); 
										int[] notlcurrentWord = lC.lyndonConj(notcurrentWord);
										for (int j=0;j<word.length;j++) {
											notconvertWord = notconvertWord + lcPM.getCharForNumber(notcurrentWord[j]);
										}
										add_line.printf("%s" + "%n", notconvertWord);
										notconvertWord = notconvertWord + " (";
										for (int j=0;j<word.length;j++) {
											notlconvertWord = notlconvertWord + lcPM.getCharForNumber(notlcurrentWord[j]);
										}
										notconvertWord = notconvertWord + notlconvertWord + ")";
										JLabel notlwords = new JLabel(notconvertWord);
										Font notlfont = notlwords.getFont();
										notlwords.setFont(new Font(notlfont.getName(), Font.PLAIN, 16));
										notlwords.setHorizontalAlignment(JLabel.CENTER);
										lnotresultsLabel.add(notlwords);
									}
								}

								//output final list and make it look clear
								JPanel notlwordLabel = new JPanel();
								notlwordLabel.setLayout(new BoxLayout(notlwordLabel, BoxLayout.Y_AXIS));
								JPanel notInfo = new JPanel();
								JLabel notInfoBefore = new JLabel();
								if (distinctCount==1) {
									notInfoBefore.setText("There is " + distinctCount + " word that is");
								}
								else {
									notInfoBefore.setText("There are " + distinctCount + " words that are");
								}
								JLabel notInfoLink = new JLabel("<html><u>L-distinct</u><html>");
								JLabel notInfoAfter = new JLabel("with your entered word:");
								notInfoLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								notInfoLink.setForeground(Color.BLUE);
								notInfoLink.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseClicked(MouseEvent e) {
										JDialog dialog1 = new JDialog(frame);
										JPanel container = new JPanel();
										JLabel parikhMatrixInfo = new JLabel("<html><center>Two words are L-distinct if they share the same<br>Parikh matrix, but have different L-Parikh matrices.</center><html>");
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
								Font notInfoFont = notInfoBefore.getFont();
								notInfoBefore.setFont(new Font(notInfoFont.getName(), Font.PLAIN, 16));
								notInfoLink.setFont(new Font(notInfoFont.getName(), Font.PLAIN, 16));
								notInfoAfter.setFont(new Font(notInfoFont.getName(), Font.PLAIN, 16));
								notInfo.add(notInfoBefore);
								notInfo.add(notInfoLink);
								notInfo.add(notInfoAfter);
								//JLabel notInfo2 = new JLabel("Original Word (Lyndon Conjugate)");
								notInfo.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
								//notInfo2.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
								notInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
								//notInfo2.setAlignmentX(Component.CENTER_ALIGNMENT);
								//notInfo2.setHorizontalAlignment(JLabel.CENTER);
								lnotresultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
								notlwordLabel.add(notInfo);
								//notlwordLabel.add(notInfo2);
								notlwordLabel.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
								notlwordLabel.add(lnotresultsLabel);
								notlwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
								outputholder.add(notlwordLabel);
								holder.add(outputholder);
							}
							else {
								JPanel notlwordLabel = new JPanel();
								notlwordLabel.setLayout(new BoxLayout(notlwordLabel, BoxLayout.Y_AXIS));
								notlwordLabel.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
								JLabel notInfo = new JLabel();
								if (counter==1) {
									add_line.printf("Your word is described uniquely by its Parikh matrix.");
									notInfo.setText("Your word is described uniquely by its Parikh matrix.");
									notInfo.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
									Font notInfoFont = notInfo.getFont();
									notInfo.setFont(new Font(notInfoFont.getName(), Font.PLAIN, 16));
									notInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
									notlwordLabel.add(notInfo);
								}
								else {
									JPanel notInfoP = new JPanel();
									notInfo.setText("The Parikh matrix of your entered word is not");
									add_line.printf("The Parikh matrix of your entered word is not L-distinguishable.");
									JLabel notInfo2 = new JLabel("<html><u>L-distinguishable</u>.<html>");
									notInfo2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
									notInfo2.setForeground(Color.BLUE);
									notInfo2.addMouseListener(new MouseAdapter() {
										@Override
										public void mouseClicked(MouseEvent e) {
											JDialog dialog1 = new JDialog(frame);
											JPanel container = new JPanel();
											JLabel parikhMatrixInfo = new JLabel("<html><center>A Parikh matrix is L-distinguishable if it<br>describes at least two distinct words<br>whose respective L-Parikh matrices are<br>different.</center><html>");
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
									notInfoP.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
									Font notInfoFont = notInfo.getFont();
									notInfo.setFont(new Font(notInfoFont.getName(), Font.PLAIN, 16));
									notInfo2.setFont(new Font(notInfoFont.getName(), Font.PLAIN, 16));
									notInfoP.add(notInfo);
									notInfoP.add(notInfo2);
									notInfoP.setAlignmentX(Component.CENTER_ALIGNMENT);
									notlwordLabel.add(notInfoP);
								}
								notlwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
								outputholder.add(notlwordLabel);
								holder.add(outputholder);
							}
							add_line.close();
							//setProgress(9);
							if (Home.mySwingWorker.isCancelled()) {
								return;
							}
							revalidate();
							repaint();

						}
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
						outputholder.setVisible(false);
					}

				}
				else {
					//setProgress(20);
					Home.dialog.setVisible(false);
					Home.res.setVisible(true);
					outputholder.setVisible(false);
					Home.res.setText(INVALID_SIZE);
				}

			}
			catch (NumberFormatException ex) {
				//setProgress(20);
				Home.dialog.setVisible(false);
				outputholder.setVisible(false);
				Home.res.setVisible(true);
				Home.res.setText("Incorrect alphabet input type - please enter an integer.");
			}
		}
		/*
        	        wait.close();
        	        return null;
        	    }
        	};

        	mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e){
                    if ("progress".equals(e.getPropertyName())) {
                        //progressBar.setIndeterminate(false);
                        wait.progressBar.setValue((Integer) e.getNewValue());
                        if ((Integer) e.getNewValue() == 1) {
                        	wait.progressInfo.setText("Step 1 of 9 - Reading Input");
                        }
                        else if ((Integer) e.getNewValue() == 2) {
                        	wait.progressInfo.setText("Step 2 of 9 - Calculating Parikh Matrix");
                        }
                        else if ((Integer) e.getNewValue() == 3) {
                        	wait.progressInfo.setText("Step 3 of 9 - Painting Parikh Matrix");
                        }
                        else if ((Integer) e.getNewValue() == 4) {
                        	wait.progressInfo.setText("Step 4 of 9 - Calculating Permutations");
                        }
                        else if ((Integer) e.getNewValue() == 5) {
                        	wait.progressInfo.setText("Step 5 of 9 - Finding Parikh Matrices");
                        }
                        else if ((Integer) e.getNewValue() == 6) {
                        	wait.progressInfo.setText("Step 6 of 9 - Calculating Lyndon Conjugate");
                        }
                        else if ((Integer) e.getNewValue() == 7) {
                        	wait.progressInfo.setText("Step 7 of 9 - Calculating L-Parikh Matrices");
                        }
                        else if ((Integer) e.getNewValue() == 8) {
                        	wait.progressInfo.setText("Step 8 of 9 - Compiling Results");
                        }
                        else if ((Integer) e.getNewValue() == 9) {
                        	wait.progressInfo.setText("Step 9 of 9 - Complete");
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


        });*/
		add(holder,BorderLayout.NORTH);
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
		container.add(new LAmiableWordsInterface());
		Home.dialog.setVisible(false);
		//Home.stop.setEnabled(false);
		Home.resultsPanel.add(container);
		Home.resultsPanel.setMinimumSize(new Dimension(500,500));
		Home.resultsPanel.revalidate();
		Home.resultsPanel.repaint();
		Home.centraliser.revalidate();
		Home.centraliser.repaint();
		Home.homeFrame.revalidate();
		Home.homeFrame.repaint();
		/*JScrollPane scrPane = new JScrollPane(container);
    	scrPane.getVerticalScrollBar().setUnitIncrement(16);
    	scrPane.getHorizontalScrollBar().setUnitIncrement(16);

    	//frame formatting
        frame = new JFrame("Amiable Words And L-Amiable Words");
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
        frame.setVisible(true);*/

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

	class DialogWait {

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
			progressBar.setMaximum(9);
			progressBar.setBorder(new EmptyBorder(0,15,15,15));
			progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

			progressInfo = new JLabel("Step 0 of 9 - Initialising");
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
	}

}
