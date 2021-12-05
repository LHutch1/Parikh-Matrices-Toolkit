import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

public class PParikhMatrixInterface extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static JLabel res;
	private static final String INVALID_SIZE = "Alphabet must be of at least size 2.";
	public SwingWorker<Void, Void> mySwingWorker;
	public JFrame frame;
	
	public PParikhMatrixInterface() {
		setLayout(new BorderLayout());
        
        //Panel to hold starting components
        JPanel holder = new JPanel();
        holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));
        JPanel topHolder = new JPanel(new GridLayout(0,1));
        JPanel word = new JPanel();
        JPanel information = new JPanel();
        JPanel alphabet = new JPanel();
        JPanel set = new JPanel();
        JPanel enter = new JPanel();
        JLabel wordText = new JLabel("Please enter your word: ");
        JLabel infoText = new JLabel("(with the lexicographically smallest letter in the alphabet being either \"0\" or \"a\")");
        infoText.setFont(new Font(infoText.getFont().getName(), Font.PLAIN, 12));
        JTextField wordInput = new JTextField(15);
        word.add(wordText);
        word.add(wordInput);
        information.add(infoText);
        JLabel alphabetText = new JLabel("Please enter the size of your");
        JLabel link = new JLabel("<html><u>alphabet</u>:<html>");
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        link.setForeground(Color.BLUE);
        link.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	//JFrame frame = new JFrame("Parikh Matrix Definition");
            	JDialog dialog1 = new JDialog(frame);
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
        JTextField alphabetInput = new JTextField(3);
        alphabet.add(alphabetText);
        alphabet.add(link);
        alphabet.add(alphabetInput);
        JLabel setText = new JLabel("Please enter the ");
        JLabel setlink = new JLabel("<html><u>set of letters</u><html>");
        JLabel setText2 = new JLabel(" to keep in your projection: ");
        setlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setlink.setForeground(Color.BLUE);
        setlink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	//JFrame frame = new JFrame("Parikh Matrix Definition");
            	JDialog dialog1 = new JDialog(frame);
        		JPanel container = new JPanel();
        		JLabel parikhMatrixInfo = new JLabel("<html><center>Let S be a set that contains letters in your given<br>alphabet. We define the P-Parikh matrix of a word with respect<br>to S as the Parikh matrix of the word obtained from the<br>following. For each letter in your word, if that letter<br>is in S, it remains, otherwise replace that letter with<br>the empty word. Now map your letters to a new alphabet that<br>maintains their order and results in all letters being<br>consecutive. (Example - for the set S={a,c,e} and the word<br>'cabbcced', we first remove letters not in S to get 'cacce'.<br>Now map to the new alphabet to get 'babbc'.)\"</center><html>");
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
        JTextField setInput = new JTextField(15);
        set.add(setText);
        set.add(setlink);
        set.add(setText2);
        set.add(setInput);
        JButton enterButton = new JButton("Enter");
        res = new JLabel();
        res.setVisible(false);
    	res.setText(INVALID_SIZE);
    	JPanel matrixholder = new JPanel();
        enterButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e3){
        		DialogWait wait = new DialogWait();
        		mySwingWorker = new SwingWorker<Void, Void>() {
					@Override
        		    protected Void doInBackground() throws Exception {
                    try { //checking if alphabet input is valid
                    	res.setVisible(false);
                    	matrixholder.setVisible(true);
                    	String alphIn = alphabetInput.getText();
                    	int enteredAlphabet;
                    	if (alphIn.length()==0){
                    		enteredAlphabet = 0;
                    	}
                    	else {
                    		enteredAlphabet=Integer.parseInt(alphabetInput.getText());
                    	}
                    	if(isValid(enteredAlphabet)) {
                            res.setVisible(false);
                            try { //checking if word input is valid
                            	
                            	setProgress(1);
                            	
                            	Component[] mcomponents = matrixholder.getComponents();
                                for (Component mcomponent : mcomponents) {
                                    matrixholder.remove(mcomponent);  
                                }
                                matrixholder.setVisible(true);
                            	String enteredWord = wordInput.getText();
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
                            	
                            	String set = setInput.getText();
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
                            	
                            	setProgress(2);
                            	
                            	int[][] parikhMatrix = pM.ParikhMatrix(word,enteredAlphabet+1);
                            	
                            	setProgress(3);
                            	
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
                                parikh.setLayout(new BoxLayout(parikh, BoxLayout.Y_AXIS));
                                minimatrix.add(openLabel);
                                minimatrix.add(matrix);
                                minimatrix.add(closeLabel);
                                minimatrix.setAlignmentX(Component.CENTER_ALIGNMENT);
                                parikh.add(minimatrix);
                                String convertWord = new String("");
            					for (int j=0;j<finalWord.length;j++) {
            						ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
            						convertWord = convertWord + cPM.getCharForNumber(finalWord[j]);
            					}
                                JLabel wordLabel = new JLabel(convertWord);
            					Font font = wordLabel.getFont();
            					wordLabel.setFont(new Font(font.getName(), Font.PLAIN, 18));
            					wordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            					wordLabel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
                                parikh.add(wordLabel);
                                parikh.setAlignmentX(Component.CENTER_ALIGNMENT);
                                parikh.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
                                matrixholder.add(parikh);
                                holder.add(matrixholder); 
                                
                                setProgress(4);
                                
                                Projection p = new Projection();
                                int[] pWord = p.ProjectionCalculator(finalWord,setP,enteredAlphabet);
                                
                                
                                setProgress(5);
                                
                                int[][] pparikhMatrix = pM.ParikhMatrix(pWord,setP.length+1);
                            	
                                setProgress(6);
                                
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
                                pparikh.setLayout(new BoxLayout(pparikh, BoxLayout.Y_AXIS));
                                pminimatrix.add(popenLabel);
                                pminimatrix.add(pmatrix);
                                pminimatrix.add(pcloseLabel);
                                pminimatrix.setAlignmentX(Component.CENTER_ALIGNMENT);
                                pparikh.add(pminimatrix);
                                String pconvertWord = new String("");
            					for (int j=0;j<pWord.length;j++) {
            						ParikhMatrixCalculator lcPM = new ParikhMatrixCalculator();
            						pconvertWord = pconvertWord + lcPM.getCharForNumber(pWord[j]);
            					}
                                JLabel pwordLabel = new JLabel(pconvertWord);
            					Font pfont = pwordLabel.getFont();
            					pwordLabel.setFont(new Font(pfont.getName(), Font.PLAIN, 18));
            					pwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            					pwordLabel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
                                pparikh.add(pwordLabel);
                                pparikh.setAlignmentX(Component.CENTER_ALIGNMENT);
                                pparikh.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
                                matrixholder.add(pparikh);
                                holder.add(matrixholder); 
                                
                                setProgress(7);
                                
                                revalidate();
                                repaint();
                            	
                            }
                            catch (Exception e1){
                            	setProgress(20);
                            	res.setVisible(true);
                            	res.setText("Please enter a word that consist of only letters or only numbers.");
                            	String badAlphabet = new String("Alphabet size not possible.");
                            	String badProjection1 = new String("Entered projection not possible - alphabet size.");
                            	String badProjection2 = new String("Entered projection not possible - projection not in word.");
                            	String badProjection3 = new String("No projection entered.");
                    			if (badAlphabet.equals(e1.getMessage())) {
                    				res.setText("Alphabet size not possible - check entered word.");
                    			}
                    			else if (badProjection1.equals(e1.getMessage())) {
                    				res.setText("Entered projection not possible. Set contains letter not in alphabet.");
                    			}
                    			else if (badProjection2.equals(e1.getMessage())) {
                    				res.setText("Entered projection not possible. Set does not contain any letter in entered word.");
                    			}
                    			else if (badProjection3.equals(e1.getMessage())) {
                    				res.setText("Please enter a projection set.");
                    			}
                    			else {
                    				res.setText("Please enter a word that consists of only letters or only numbers.");
                    			}
                    			matrixholder.setVisible(false);
                    			
                            }
                    	}
                    	else {
                    		setProgress(20);
                    		res.setVisible(true);
                    		matrixholder.setVisible(false);
                        	res.setText(INVALID_SIZE);
                    	}
                    }
                    catch (NumberFormatException ex) {
                    	setProgress(20);
                    	matrixholder.setVisible(false);
                    	res.setVisible(true);
                    	res.setText("Incorrect alphabet input type - please enter an integer.");
                    }
                    
                    

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
        
        JButton enterFile = new JButton("Enter (and save as .txt file)");
        JFileChooser chooser = new JFileChooser();
        enterFile.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e3){
            		DialogWait wait = new DialogWait();
            		mySwingWorker = new SwingWorker<Void, Void>() {
    					@Override
            		    protected Void doInBackground() throws Exception {
                        try { //checking if alphabet input is valid
                        	res.setVisible(false);
                        	matrixholder.setVisible(true);
                        	String alphIn = alphabetInput.getText();
                        	int enteredAlphabet;
                        	if (alphIn.length()==0){
                        		enteredAlphabet = 0;
                        	}
                        	else {
                        		enteredAlphabet=Integer.parseInt(alphabetInput.getText());
                        	}
                        	if(isValid(enteredAlphabet)) {
                                res.setVisible(false);
                                try { //checking if word input is valid
                                	
                                	setProgress(1);
                                	
                                	Component[] mcomponents = matrixholder.getComponents();
                                    for (Component mcomponent : mcomponents) {
                                        matrixholder.remove(mcomponent);  
                                    }
                                    matrixholder.setVisible(true);
                                	String enteredWord = wordInput.getText();
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
                                	
                                	String set = setInput.getText();
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
                                	
                                	setProgress(2);
                                	
                                	String convertinputWord = new String("");
                                	for (int j=0;j<word.length;j++) {
                						ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
                						convertinputWord = convertinputWord + cPM.getCharForNumber(finalWord[j]);
                					}
                                	
                                	//Open save dialog and create file
                        			
                        			chooser.setDialogTitle("Select Folder");
                        			String defaultTitle = "P-Parikh Matrix Of " + convertinputWord + ".txt";
                        			chooser.setSelectedFile(new File(defaultTitle));
                        			int returnVal = chooser.showSaveDialog(enterFile);
                        			
                        			if (returnVal == JFileChooser.APPROVE_OPTION) {
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
                                	
                        			setProgress(3);
                        			
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
                                    parikh.setLayout(new BoxLayout(parikh, BoxLayout.Y_AXIS));
                                    minimatrix.add(openLabel);
                                    minimatrix.add(matrix);
                                    minimatrix.add(closeLabel);
                                    minimatrix.setAlignmentX(Component.CENTER_ALIGNMENT);
                                    parikh.add(minimatrix);
                                    String convertWord = new String("");
                					for (int j=0;j<finalWord.length;j++) {
                						ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
                						convertWord = convertWord + cPM.getCharForNumber(finalWord[j]);
                					}
                                    JLabel wordLabel = new JLabel(convertWord);
                					Font font = wordLabel.getFont();
                					wordLabel.setFont(new Font(font.getName(), Font.PLAIN, 18));
                					wordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                					wordLabel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
                                    parikh.add(wordLabel);
                                    parikh.setAlignmentX(Component.CENTER_ALIGNMENT);
                                    parikh.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
                                    matrixholder.add(parikh);
                                    holder.add(matrixholder); 
                                    
                                    setProgress(4);
                                    
                                    Projection p = new Projection();
                                    int[] pWord = p.ProjectionCalculator(finalWord,setP,enteredAlphabet);
                                    
                                    
                                    setProgress(5);
                                    
                                    int[][] pparikhMatrix = pM.ParikhMatrix(pWord,setP.length+1);
                                	
                                    setProgress(6);
                                    
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
                                    pparikh.setLayout(new BoxLayout(pparikh, BoxLayout.Y_AXIS));
                                    pminimatrix.add(popenLabel);
                                    pminimatrix.add(pmatrix);
                                    pminimatrix.add(pcloseLabel);
                                    pminimatrix.setAlignmentX(Component.CENTER_ALIGNMENT);
                                    pparikh.add(pminimatrix);
                                    String pconvertWord = new String("");
                					for (int j=0;j<pWord.length;j++) {
                						ParikhMatrixCalculator lcPM = new ParikhMatrixCalculator();
                						pconvertWord = pconvertWord + lcPM.getCharForNumber(pWord[j]);
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
                					pwordLabel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
                                    pparikh.add(pwordLabel);
                                    pparikh.setAlignmentX(Component.CENTER_ALIGNMENT);
                                    pparikh.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
                                    matrixholder.add(pparikh);
                                    holder.add(matrixholder); 
                                    
                                    setProgress(7);
                                    
                                    revalidate();
                                    repaint();
                        			}
                                }
                                catch (Exception e1){
                                	setProgress(20);
                                	res.setVisible(true);
                                	res.setText("Please enter a word that consist of only letters or only numbers.");
                                	String badAlphabet = new String("Alphabet size not possible.");
                                	String badProjection1 = new String("Entered projection not possible - alphabet size.");
                                	String badProjection2 = new String("Entered projection not possible - projection not in word.");
                                	String badProjection3 = new String("No projection entered.");
                        			if (badAlphabet.equals(e1.getMessage())) {
                        				res.setText("Alphabet size not possible - check entered word.");
                        			}
                        			else if (badProjection1.equals(e1.getMessage())) {
                        				res.setText("Entered projection not possible. Set contains letter not in alphabet.");
                        			}
                        			else if (badProjection2.equals(e1.getMessage())) {
                        				res.setText("Entered projection not possible. Set does not contain any letter in entered word.");
                        			}
                        			else if (badProjection3.equals(e1.getMessage())) {
                        				res.setText("Please enter a projection set.");
                        			}
                        			else {
                        				res.setText("Please enter a word that consists of only letters or only numbers.");
                        			}
                        			matrixholder.setVisible(false);
                        			
                                }
                        	}
                        	else {
                        		setProgress(20);
                        		res.setVisible(true);
                        		matrixholder.setVisible(false);
                            	res.setText(INVALID_SIZE);
                        	}
                        }
                        catch (NumberFormatException ex) {
                        	setProgress(20);
                        	matrixholder.setVisible(false);
                        	res.setVisible(true);
                        	res.setText("Incorrect alphabet input type - please enter an integer.");
                        }
                        
                        

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
        
        enter.add(enterButton);
        enter.add(enterFile);
        JPanel errorPanel = new JPanel();
        errorPanel.add(res);
        topHolder.add(word);
        topHolder.add(information);
        topHolder.add(alphabet);
        topHolder.add(set);
        topHolder.add(enter);
        topHolder.add(errorPanel);
        holder.add(topHolder);
        add(holder,BorderLayout.NORTH);
    }

	
	public void actionPerformed(ActionEvent e2) {
		
		//make frame scroll-able if matrix is large enough
    	JPanel container = new JPanel();
    	container.add(new PParikhMatrixInterface());
    	JScrollPane scrPane = new JScrollPane(container);
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
        frame.setVisible(true);
		
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
