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
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
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

public class PParikhMatrixInterface extends JPanel{

	private static final long serialVersionUID = 1L;
	//private static JLabel res;
	private static final String INVALID_SIZE = "Alphabet must be of at least size 2.";
	public SwingWorker<Void, Void> mySwingWorker;
	public JFrame frame;

	@SuppressWarnings("resource")
	public PParikhMatrixInterface() {
		JPanel holder = new JPanel();
		holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));

		JPanel matrixholder = new JPanel();
		if (!Home.save.isSelected()) {
			/*enterButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e3){
        		DialogWait wait = new DialogWait();
        		mySwingWorker = new SwingWorker<Void, Void>() {
					@Override
        		    protected Void doInBackground() throws Exception { */
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

						//convert input set to array of unique integers

						String set = Home.setInput.getText();
						if (set.length()==0) {
							throw new Exception("No projection entered.");
						}
						set = set.replaceAll("[^a-zA-Z0-9]", "");  //remove special characters
						set = set.toLowerCase(); 
						int[] pset = new int[set.length()];
						int countNum = 0;
						int countLet = 0;
						for (int i=0;i<set.length();i++) {
							if (Character.isLetter(set.charAt(i))){
								String character = Character.toString(set.charAt(i));
								pset[i] = pM.getNumberForChar(character)-1;
								countLet = countLet+1;
							}
							else if (Character.isDigit(set.charAt(i))) {
								pset[i] = Character.getNumericValue(set.charAt(i));
								countNum= countNum+1;
							}

						}
						Arrays.sort(pset);
						int prev = pset[0];
						int[] uniqueset = new int[pset.length];
						uniqueset[0] = prev;
						int setcount = 1;
						for (int i=0;i<pset.length;i++) {
							if (!(prev==pset[i])) {
								uniqueset[setcount]=pset[i];
								setcount = setcount+1;
							}
							prev = pset[i];
						}
						int[] setP = Arrays.copyOfRange(uniqueset, 0, setcount);

						int maxProjLetter = max(setP);
						int projInWord = 0;
						for (int i=0;i<word.length;i++) {
							for (int j=0;j<setP.length;j++) {
								if (word[i]==setP[j]) {
									projInWord=projInWord+1;
									break;
								}
							}
						}
						if ((isNumber(set) && !isNumber(enteredWord)) || (!isNumber(set) && isNumber(enteredWord))) {
							throw new Exception("Opposing alphanumeric word and set.");
						}
						else if (countNum!=0 && countLet!=0) {
							throw new Exception ("Mixed set characters.");
						}
						else if (maxProjLetter>=enteredAlphabet) {
							throw new Exception("Entered projection not possible - alphabet size.");
						}
						else if (projInWord==0) {
							throw new Exception("Entered projection not possible - projection not in word.");
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
								current.setAlignmentX(Component.CENTER_ALIGNMENT);
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
						String convertWord = new String("");
						for (int j=0;j<finalWord.length;j++) {
							ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
							convertWord = convertWord + cPM.getCharForNumber(finalWord[j]);
						}
						JLabel wordLabel = new JLabel(convertWord);
						JPanel wordLabelP = new JPanel();
						Font font = wordLabel.getFont();
						wordLabel.setFont(new Font(font.getName(), Font.PLAIN, 18));
						wordLabelP.setAlignmentX(Component.CENTER_ALIGNMENT);
						wordLabelP.add(wordLabel);
						c.gridx=0;
						c.gridy=2;
						c.fill=GridBagConstraints.HORIZONTAL;
						c.anchor=GridBagConstraints.NORTH;
						parikh.add(wordLabelP,c);
						parikh.setAlignmentX(Component.CENTER_ALIGNMENT);
						matrixholder.add(parikh);
						holder.add(matrixholder); 

						//setProgress(4);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						Projection p = new Projection();
						int[] pWord = p.ProjectionCalculator(finalWord,setP,enteredAlphabet);
						int[] mappedWord = p.InverseProjection(pWord,setP); //map back to original set

						//setProgress(5);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						int[][] pparikhMatrix = pM.ParikhMatrix(pWord,setP.length+1);

						//setProgress(6);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//add matrix to panel, then add to frame
						JPanel pmatrix = new JPanel(new GridLayout(pparikhMatrix.length,pparikhMatrix.length));
						for (int i=0;i<pparikhMatrix.length;i++) {
							for (int j=0;j<pparikhMatrix.length;j++) {
								JLabel pcurrent = new JLabel(Integer.toString(pparikhMatrix[i][j]));
								pcurrent.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
								pcurrent.setAlignmentX(Component.CENTER_ALIGNMENT);
								pmatrix.add(pcurrent);
							}
						}

						//adding brackets to either side of matrix
						//open bracket
						BufferedImage popen = null;
						try {
							popen = ImageIO.read(getClass().getResource("/Images/OpenBracketClear.png"));
						} catch (IOException pe1) {
							pe1.printStackTrace();
						}
						Dimension pmatsize = pmatrix.getPreferredSize();
						int pwidth = (int) Math.round(pmatsize.getHeight()/70);
						Image popendimg = popen.getScaledInstance(15+pwidth, (int) pmatsize.getHeight(),Image.SCALE_SMOOTH);
						ImageIcon popenIcon = new ImageIcon(popendimg);
						JLabel popenLabel = new JLabel(popenIcon);
						//close bracket
						BufferedImage pclose = null;
						try {
							pclose = ImageIO.read(getClass().getResource("/Images/CloseBracketClear.png"));
						} catch (IOException pe1) {
							pe1.printStackTrace();
						}
						Image pdclose = pclose.getScaledInstance(15+pwidth, (int) pmatsize.getHeight(),Image.SCALE_SMOOTH);
						ImageIcon pcloseIcon = new ImageIcon(pdclose);
						JLabel pcloseLabel = new JLabel(pcloseIcon);

						//add borders to brackets to give some space
						popenLabel.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
						pcloseLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));

						//add to panel
						JPanel pminimatrix = new JPanel();
						JPanel pparikh = new JPanel();
						pparikh.setLayout(new GridBagLayout());
						JLabel pParikhMatrix= new JLabel("<html><u>P-Parikh Matrix</u>:<html>");
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//JLabel lParikhMatrix= new JLabel("<html><u>L-Parikh Matrix</u>:<html>");
						pParikhMatrix.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						pParikhMatrix.setForeground(Color.BLUE);
						pParikhMatrix.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								JDialog dialog1 = new JDialog(frame);
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
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						Font pParikhMatrixFont = pParikhMatrix.getFont();
						pParikhMatrix.setFont(new Font(pParikhMatrixFont.getName(), Font.PLAIN, 16));
						pParikhMatrix.setHorizontalAlignment(JLabel.CENTER);
						c.gridx=0;
						c.gridy=0;
						c.fill=GridBagConstraints.HORIZONTAL;
						c.anchor=GridBagConstraints.NORTH;
						pparikh.add(pParikhMatrix,c);
						pminimatrix.setMaximumSize(new Dimension(500,20+(int)matsize.getHeight()));
						pminimatrix.add(popenLabel);
						pminimatrix.add(pmatrix);
						pminimatrix.add(pcloseLabel);
						pminimatrix.setAlignmentX(Component.CENTER_ALIGNMENT);
						c.gridx=0;
						c.gridy=1;
						c.fill=GridBagConstraints.HORIZONTAL;
						c.anchor=GridBagConstraints.NORTH;
						pparikh.add(pminimatrix,c);
						String pconvertWord = new String("");
						for (int j=0;j<mappedWord.length;j++) {
							ParikhMatrixCalculator lcPM = new ParikhMatrixCalculator();
							pconvertWord = pconvertWord + lcPM.getCharForNumber(mappedWord[j]);
						}
						JLabel pwordLabel = new JLabel(pconvertWord);
						Font pfont = pwordLabel.getFont();
						pwordLabel.setFont(new Font(pfont.getName(), Font.PLAIN, 18));
						pwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
						//pwordLabel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
						c.gridx=0;
						c.gridy=2;
						c.fill=GridBagConstraints.HORIZONTAL;
						c.anchor=GridBagConstraints.NORTH;
						JPanel pwordLabelP = new JPanel();
						pwordLabelP.add(pwordLabel);
						pparikh.add(pwordLabelP,c);
						pparikh.setAlignmentX(Component.CENTER_ALIGNMENT);
						//pparikh.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
						matrixholder.add(pparikh);
						holder.add(matrixholder); 

						//setProgress(7);
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
						String badProjection1 = new String("Entered projection not possible - alphabet size.");
						String badProjection2 = new String("Entered projection not possible - projection not in word.");
						String badProjection3 = new String("No projection entered.");
						String badProjection4 = new String("Opposing alphanumeric word and set.");
						String badProjection5 = new String("Mixed set characters.");
						if (badAlphabet.equals(e1.getMessage())) {
							Home.res.setText("Alphabet size not possible - check entered word.");
						}
						else if (badProjection1.equals(e1.getMessage())) {
							Home.res.setText("Entered projection not possible. Set contains letter not in alphabet.");
						}
						else if (badProjection2.equals(e1.getMessage())) {
							Home.res.setText("Entered projection not possible. Set does not contain any letter in entered word.");
						}
						else if (badProjection3.equals(e1.getMessage())) {
							Home.res.setText("Please enter a projection set.");
						}
						else if (badProjection4.equals(e1.getMessage())) {
							Home.res.setText("Please ensure your projection set uses the same characters as your word.");
						}
						else if (badProjection5.contentEquals(e1.getMessage())) {
							Home.res.setText("Please enter a projection set that consists of only letters or only numbers.");
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
                        	wait.progressInfo.setText("Step 1 of 7 - Reading Input");
                        }
                        else if ((Integer) e.getNewValue() == 2) {
                        	wait.progressInfo.setText("Step 2 of 7 - Calculating Parikh Matrix");
                        }
                        else if ((Integer) e.getNewValue() == 3) {
                        	wait.progressInfo.setText("Step 3 of 7 - Painting Parikh Matrix");
                        }
                        else if ((Integer) e.getNewValue() == 4) {
                        	wait.progressInfo.setText("Step 4 of 7 - Calculating Projected Word");
                        }
                        else if ((Integer) e.getNewValue() == 5) {
                        	wait.progressInfo.setText("Step 5 of 7 - Calculating P-Parikh Matrix");
                        }
                        else if ((Integer) e.getNewValue() == 6) {
                        	wait.progressInfo.setText("Step 6 of 7 - Compiling Results");
                        }
                        else if ((Integer) e.getNewValue() == 7) {
                        	wait.progressInfo.setText("Step 7 of 7 - Complete");
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
        );
			 */
			//JButton enterFile = new JButton("Enter (and save as .txt file)");
		}
		else {
			JFileChooser chooser = new JFileChooser();
			/*enterFile.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e3){
            		DialogWait wait = new DialogWait();
            		mySwingWorker = new SwingWorker<Void, Void>() {
    					@Override
            		    protected Void doInBackground() throws Exception {*/
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

						//convert input set to array of unique integers

						String set = Home.setInput.getText();
						if (set.length()==0) {
							throw new Exception("No projection entered.");
						}
						set = set.replaceAll("[^a-zA-Z0-9]", "");  //remove special characters
						set = set.toLowerCase(); 
						int[] pset = new int[set.length()];
						for (int i=0;i<set.length();i++) {
							if (Character.isLetter(set.charAt(i))){
								String character = Character.toString(set.charAt(i));
								pset[i] = pM.getNumberForChar(character)-1;
							}
							else if (Character.isDigit(set.charAt(i))) {
								pset[i] = Character.getNumericValue(set.charAt(i));
							}

						}
						Arrays.sort(pset);
						int prev = pset[0];
						int[] uniqueset = new int[pset.length];
						uniqueset[0] = prev;
						int setcount = 1;
						for (int i=0;i<pset.length;i++) {
							if (!(prev==pset[i])) {
								uniqueset[setcount]=pset[i];
								setcount = setcount+1;
							}
							prev = pset[i];
						}
						int[] setP = Arrays.copyOfRange(uniqueset, 0, setcount);

						int maxProjLetter = max(setP);
						int projInWord = 0;
						for (int i=0;i<word.length;i++) {
							for (int j=0;j<setP.length;j++) {
								if (word[i]==setP[j]) {
									projInWord=projInWord+1;
									break;
								}
							}
						}
						if (maxProjLetter>=enteredAlphabet) {
							throw new Exception("Entered projection not possible - alphabet size.");
						}
						else if (projInWord==0) {
							throw new Exception("Entered projection not possible - projection not in word.");
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
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						chooser.setDialogTitle("Select Folder");
						String defaultTitle = "P-Parikh Matrix Of " + convertinputWord + ".txt";
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
							File outputfile = new File(path,filename);
							outputfile.createNewFile();
							FileWriter write = new FileWriter(outputfile, false);
							FileWriter append = new FileWriter(outputfile, true);
							PrintWriter print_line = new PrintWriter(write);
							PrintWriter add_line = new PrintWriter(append);

							int[][] parikhMatrix = pM.ParikhMatrix(word,enteredAlphabet+1);

							if (Home.mySwingWorker.isCancelled()) {
								return;
							}

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
									current.setAlignmentX(Component.CENTER_ALIGNMENT);
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
							String convertWord = new String("");
							for (int j=0;j<finalWord.length;j++) {
								ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
								convertWord = convertWord + cPM.getCharForNumber(finalWord[j]);
							}
							JLabel wordLabel = new JLabel(convertWord);
							JPanel wordLabelP = new JPanel();
							Font font = wordLabel.getFont();
							wordLabel.setFont(new Font(font.getName(), Font.PLAIN, 18));
							wordLabelP.setAlignmentX(Component.CENTER_ALIGNMENT);
							wordLabelP.add(wordLabel);
							c.gridx=0;
							c.gridy=2;
							c.fill=GridBagConstraints.HORIZONTAL;
							c.anchor=GridBagConstraints.NORTH;
							parikh.add(wordLabelP,c);
							parikh.setAlignmentX(Component.CENTER_ALIGNMENT);
							matrixholder.add(parikh);
							holder.add(matrixholder); 

							//setProgress(4);
							if (Home.mySwingWorker.isCancelled()) {
								return;
							}
							Projection p = new Projection();
							int[] pWord = p.ProjectionCalculator(finalWord,setP,enteredAlphabet);
							int[] mappedWord = p.InverseProjection(pWord,setP); //map back to original set

							//setProgress(5);
							if (Home.mySwingWorker.isCancelled()) {
								return;
							}
							int[][] pparikhMatrix = pM.ParikhMatrix(pWord,setP.length+1);

							//setProgress(6);
							if (Home.mySwingWorker.isCancelled()) {
								return;
							}
							//add matrix to panel, then add to frame
							JPanel pmatrix = new JPanel(new GridLayout(pparikhMatrix.length,pparikhMatrix.length));
							for (int i=0;i<pparikhMatrix.length;i++) {
								for (int j=0;j<pparikhMatrix.length;j++) {
									JLabel pcurrent = new JLabel(Integer.toString(pparikhMatrix[i][j]));
									pcurrent.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
									pcurrent.setAlignmentX(Component.CENTER_ALIGNMENT);
									pmatrix.add(pcurrent);
								}
							}

							//adding brackets to either side of matrix
							//open bracket
							BufferedImage popen = null;
							try {
								popen = ImageIO.read(getClass().getResource("/Images/OpenBracketClear.png"));
							} catch (IOException pe1) {
								pe1.printStackTrace();
							}
							Dimension pmatsize = pmatrix.getPreferredSize();
							int pwidth = (int) Math.round(pmatsize.getHeight()/70);
							Image popendimg = popen.getScaledInstance(15+pwidth, (int) pmatsize.getHeight(),Image.SCALE_SMOOTH);
							ImageIcon popenIcon = new ImageIcon(popendimg);
							JLabel popenLabel = new JLabel(popenIcon);
							//close bracket
							BufferedImage pclose = null;
							try {
								pclose = ImageIO.read(getClass().getResource("/Images/CloseBracketClear.png"));
							} catch (IOException pe1) {
								pe1.printStackTrace();
							}
							Image pdclose = pclose.getScaledInstance(15+pwidth, (int) pmatsize.getHeight(),Image.SCALE_SMOOTH);
							ImageIcon pcloseIcon = new ImageIcon(pdclose);
							JLabel pcloseLabel = new JLabel(pcloseIcon);

							//add borders to brackets to give some space
							popenLabel.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));
							pcloseLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,20));

							//add to panel
							JPanel pminimatrix = new JPanel();
							JPanel pparikh = new JPanel();
							pparikh.setLayout(new GridBagLayout());
							JLabel pParikhMatrix= new JLabel("<html><u>P-Parikh Matrix</u>:<html>");
							if (Home.mySwingWorker.isCancelled()) {
								return;
							}
							//JLabel lParikhMatrix= new JLabel("<html><u>L-Parikh Matrix</u>:<html>");
							pParikhMatrix.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							pParikhMatrix.setForeground(Color.BLUE);
							pParikhMatrix.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent e) {
									JDialog dialog1 = new JDialog(frame);
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
							if (Home.mySwingWorker.isCancelled()) {
								return;
							}
							Font pParikhMatrixFont = pParikhMatrix.getFont();
							pParikhMatrix.setFont(new Font(pParikhMatrixFont.getName(), Font.PLAIN, 16));
							pParikhMatrix.setHorizontalAlignment(JLabel.CENTER);
							c.gridx=0;
							c.gridy=0;
							c.fill=GridBagConstraints.HORIZONTAL;
							c.anchor=GridBagConstraints.NORTH;
							pparikh.add(pParikhMatrix,c);
							pminimatrix.setMaximumSize(new Dimension(500,20+(int)matsize.getHeight()));
							pminimatrix.add(popenLabel);
							pminimatrix.add(pmatrix);
							pminimatrix.add(pcloseLabel);
							pminimatrix.setAlignmentX(Component.CENTER_ALIGNMENT);
							c.gridx=0;
							c.gridy=1;
							c.fill=GridBagConstraints.HORIZONTAL;
							c.anchor=GridBagConstraints.NORTH;
							pparikh.add(pminimatrix,c);
							String pconvertWord = new String("");
							for (int j=0;j<mappedWord.length;j++) {
								ParikhMatrixCalculator lcPM = new ParikhMatrixCalculator();
								pconvertWord = pconvertWord + lcPM.getCharForNumber(mappedWord[j]);
							}
							String start2 = new String("The projection of your word ");
							add_line.printf("%s", start2);
							add_line.printf("%s"+" with your projection set {", convertinputWord);
							for (int i=0;i<pset.length;i++) {
								ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
								if (i==pset.length-1) {
									add_line.printf("%s}: %n", cPM.getCharForNumber(pset[i]));
								}
								else {
									add_line.printf("%s,", cPM.getCharForNumber(pset[i]));
								}
							}
							add_line.printf("%s"+"%n", pconvertWord);
							add_line.printf("%n");

							String start3 = new String("The P-Parikh matrix of your word ");
							add_line.printf("%s", start3);
							add_line.printf("%s"+":"+"%n", convertinputWord);
							for (int i=0;i<pparikhMatrix.length;i++) {
								for (int j=0;j<pparikhMatrix.length;j++) {
									add_line.printf("%4d", pparikhMatrix[i][j]);
								}
								add_line.printf("%n");
							}
							add_line.printf("%n");
							add_line.close();
							JLabel pwordLabel = new JLabel(pconvertWord);
							Font pfont = pwordLabel.getFont();
							pwordLabel.setFont(new Font(pfont.getName(), Font.PLAIN, 18));
							pwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
							//pwordLabel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
							c.gridx=0;
							c.gridy=2;
							c.fill=GridBagConstraints.HORIZONTAL;
							c.anchor=GridBagConstraints.NORTH;
							JPanel pwordLabelP = new JPanel();
							pwordLabelP.add(pwordLabel);
							pparikh.add(pwordLabelP,c);
							pparikh.setAlignmentX(Component.CENTER_ALIGNMENT);
							//pparikh.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
							matrixholder.add(pparikh);
							holder.add(matrixholder); 

							//setProgress(7);
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
						String badAlphabet = new String("Alphabet size not possible.");
						String badProjection1 = new String("Entered projection not possible - alphabet size.");
						String badProjection2 = new String("Entered projection not possible - projection not in word.");
						String badProjection3 = new String("No projection entered.");
						String noWord = new String("No word.");
						if (badAlphabet.equals(e1.getMessage())) {
							Home.res.setText("Alphabet size not possible - check entered word.");
						}
						else if (noWord.equals(e1.getMessage())){
							Home.res.setText("");
						}
						else if (badProjection1.equals(e1.getMessage())) {
							Home.res.setText("Entered projection not possible. Set contains letter not in alphabet.");
						}
						else if (badProjection2.equals(e1.getMessage())) {
							Home.res.setText("Entered projection not possible. Set does not contain any letter in entered word.");
						}
						else if (badProjection3.equals(e1.getMessage())) {
							Home.res.setText("Please enter a projection set.");
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
                            	wait.progressInfo.setText("Step 1 of 7 - Reading Input");
                            }
                            else if ((Integer) e.getNewValue() == 2) {
                            	wait.progressInfo.setText("Step 2 of 7 - Calculating Parikh Matrix");
                            }
                            else if ((Integer) e.getNewValue() == 3) {
                            	wait.progressInfo.setText("Step 3 of 7 - Painting Parikh Matrix");
                            }
                            else if ((Integer) e.getNewValue() == 4) {
                            	wait.progressInfo.setText("Step 4 of 7 - Calculating Projected Word");
                            }
                            else if ((Integer) e.getNewValue() == 5) {
                            	wait.progressInfo.setText("Step 5 of 7 - Calculating P-Parikh Matrix");
                            }
                            else if ((Integer) e.getNewValue() == 6) {
                            	wait.progressInfo.setText("Step 6 of 7 - Compiling Results");
                            }
                            else if ((Integer) e.getNewValue() == 7) {
                            	wait.progressInfo.setText("Step 7 of 7 - Complete");
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

            }
            );
			 */
		}
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
		container.add(new PParikhMatrixInterface());
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
        JFrame frame = new JFrame("The P-Parikh Matrix Of A Word");
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
			progressBar.setMaximum(7);
			progressBar.setBorder(new EmptyBorder(0,15,15,15));
			progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

			progressInfo = new JLabel("Step 0 of 7 - Initialising");
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
