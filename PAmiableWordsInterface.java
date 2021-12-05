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
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
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

public class PAmiableWordsInterface extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static JLabel res;
	private static final String INVALID_SIZE = "Alphabet must be of at least size 2.";
	//private static final String INFO_TEXT = "<html><center>The red words are your entered word and its projection.<br><br>The orange words are the words that are P-amiable with your word, and their projections.</center><html>";
	public SwingWorker<Void, Void> mySwingWorker;
	public JFrame frame;
	
	public PAmiableWordsInterface() {
		setLayout(new BorderLayout());
        
        //Panel to hold starting components
        JPanel holder = new JPanel();
        holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));
        JPanel topHolder = new JPanel();
        topHolder.setLayout(new BoxLayout(topHolder, BoxLayout.Y_AXIS));
        topHolder.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        JPanel word = new JPanel();
        JPanel information = new JPanel();
        JPanel alphabet = new JPanel();
        JPanel enter = new JPanel();
        JPanel set = new JPanel();
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
    	matrixholder.setLayout(new BoxLayout(matrixholder, BoxLayout.X_AXIS));
    	matrixholder.setAlignmentY(Component.TOP_ALIGNMENT);
    	JPanel outputholder = new JPanel();
    	outputholder.setLayout(new BoxLayout(outputholder, BoxLayout.Y_AXIS));
        enterButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e3){
        		DialogWait wait = new DialogWait();

        		mySwingWorker = new SwingWorker<Void, Void>() {

        		    @Override
        		    protected Void doInBackground() throws Exception {
                    try { //checking if alphabet input is valid
                    	res.setVisible(false);
                    	JPanel info1 = new JPanel();
                    	JLabel infoText = new JLabel("The red words are your entered word and its projection.");
                        info1.add(infoText);
                        outputholder.add(info1);
                        JPanel info2 = new JPanel();
                        info2.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
                        JLabel infoText2 = new JLabel("The orange words are the words that are");
                        JLabel link2 = new JLabel("<html><u>P-amiable</u><html>");
                        JLabel infoText3 = new JLabel("with your word, and their projections.");
                        link2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        link2.setForeground(Color.BLUE);
                        link2.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                            	JDialog dialog1 = new JDialog(frame);
                        		JPanel container = new JPanel();
                        		JLabel parikhMatrixInfo = new JLabel("<html><center>Two words are P-amiable if they share<br>the same Parikh matrix and there does<br>not exist a set S such that they have<br>different P-Parikh matrices.</center><html>");
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
                    	outputholder.setVisible(true);
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
                    		outputholder.setVisible(true);
                            try { //checking if word input is valid
                            	
                            	setProgress(1);
                            	
                            	Component[] ocomponents = outputholder.getComponents();
                                for (Component ocomponent : ocomponents) {
                                    outputholder.remove(ocomponent);  
                                }
                                Component[] mcomponents = matrixholder.getComponents();
                                for (Component mcomponent : mcomponents) {
                                    matrixholder.remove(mcomponent);  
                                }info1.add(infoText);
                                outputholder.add(info1);
                                info2.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
                                link2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                link2.setForeground(Color.BLUE);
                                link2.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                    	JDialog dialog1 = new JDialog(frame);
                                		JPanel container = new JPanel();
                                		JLabel parikhMatrixInfo = new JLabel("<html><center>Two words are P-amiable if they share<br>the same Parikh matrix and there does<br>not exist a set S such that they have<br>different P-Parikh matrices.</center><html>");
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
                            	outputholder.setVisible(true);
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
                            	
                            	setProgress(3);
                            	
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
    							//parikhMatrixl.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
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
                                
                                setProgress(4);
                                
            					//now find all permutations of firstWord
                    			PermuteArrayWithDuplicates perm = new PermuteArrayWithDuplicates();
                    			List<List<Integer>> permute = perm.permute(word);
                    			
                    			setProgress(5);
                    			
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
                    			
                    			setProgress(6);
                                
                                Projection p = new Projection();
                                int[] pword = p.ProjectionCalculator(finalWord,setP,enteredAlphabet);
                                int[][] pparikhMatrix = pM.ParikhMatrix(pword,setP.length+1);
                                
                                setProgress(7);
                                
                    			//remove any words with incorrect matrix
                    			int pnumWords = permute.size();
                    			int[][] presults = new int[pnumWords][pword.length];
                    			int pcounter = 0;
                    			ParikhMatrixCalculator pcPM = new ParikhMatrixCalculator();
                    			for (int i=0;i<pnumWords;i++) {
                    				List<Integer> pcW = permute.get(i);
                    				int[] currentWord = pcW.stream().mapToInt(j->j).toArray(); 
                    				int[] pcurrentWord = p.ProjectionCalculator(currentWord,setP,enteredAlphabet);
                    				int[][] pcurrentParikhMatrix = pcPM.ParikhMatrix(pcurrentWord,setP.length+1);
                    				if (Arrays.deepEquals(pparikhMatrix,pcurrentParikhMatrix)) {
                    					presults[pcounter] = pcurrentWord;
                    					pcounter = pcounter+1;
                    					matchIndex[i][1]=1;
                    				}
                    				else {
                    					matchIndex[i][1]=0;
                    				}
                    			}
                    			
                    			setProgress(8);
                    			
                    			//convert list of arrays into words that use letters
                    			JPanel resultsLabel = new JPanel();
                    			resultsLabel.setLayout(new BoxLayout(resultsLabel, BoxLayout.Y_AXIS));
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
	                    					Font font = words.getFont();
	                    					words.setFont(new Font(font.getName(), Font.PLAIN, 18));
	                    					words.setHorizontalAlignment(JLabel.CENTER);
	                    					if (Arrays.equals(finalWord,currentWord)) {
	                    						words.setForeground(Color.red);
	                    					}
	                    					else if (matchIndex[i][1]==1 && matchIndex[i][0]==1) {
	                    						Color darkOrange = new Color(230,130,0);
	                    						words.setForeground(darkOrange);
	                    					}
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
                                
                                //JLabel lParikhMatrix= new JLabel("<html><u>L-Parikh Matrix</u>:<html>");
                                pParikhMatrix.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                pParikhMatrix.setForeground(Color.BLUE);
                                pParikhMatrix.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
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

                    			
                    			//convert list of arrays into words that use letters
                    			JPanel presultsLabel = new JPanel();
                    			presultsLabel.setLayout(new BoxLayout(presultsLabel, BoxLayout.Y_AXIS));
                    			presultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    			int pamiablecounter = 0;
                    				for (int i=0;i<numWords;i++) {
                    					if (matchIndex[i][0]==1 && matchIndex[i][1]==1) {
                    							String pconvertWord = new String("");
                    							List<Integer> cW = permute.get(i);
                                				int[] currentWord = cW.stream().mapToInt(j->j).toArray(); 
                                				int[] pcurrentWord = p.ProjectionCalculator(currentWord,setP,enteredAlphabet);
                    							for (int j=0;j<pword.length;j++) {
                    								pconvertWord = pconvertWord + pcPM.getCharForNumber(pcurrentWord[j]);
                    							}
                    							JLabel pwords = new JLabel(pconvertWord);
                    							Font pfont = pwords.getFont();
                    							pwords.setFont(new Font(pfont.getName(), Font.PLAIN, 18));
                    							if (Arrays.equals(finalWord,currentWord)) {
    	                    						pwords.setForeground(Color.red);
    	                    					}
    	                    					else if (matchIndex[i][1]==1 && matchIndex[i][0]==1) {
    	                    						Color darkOrange = new Color(230,130,0);
    	                    						pwords.setForeground(darkOrange);
    	                    					}
                    							pwords.setHorizontalAlignment(JLabel.CENTER);
                    							presultsLabel.add(pwords);
                    							pamiablecounter = pamiablecounter+1;
                    					}
                    				}
                                JPanel pwordLabel = new JPanel();
                                //lwordLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
                                pwordLabel.setLayout(new BoxLayout(pwordLabel, BoxLayout.Y_AXIS));
                    			pwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    			pwordLabel.add(presultsLabel);
                    			c.gridx=0;
    							c.gridy=2;
    							c.fill=GridBagConstraints.HORIZONTAL;
    							c.anchor=GridBagConstraints.NORTH;
                                pparikh.add(pwordLabel,c);
                                GridBagConstraints gbcFiller = new GridBagConstraints();
                                gbcFiller.weighty = 1.0;
                                gbcFiller.fill = GridBagConstraints.BOTH;
                                gbcFiller.gridheight=GridBagConstraints.REMAINDER;
                                pparikh.add(Box.createGlue(), gbcFiller);
                                matrixholder.add(pparikh);
                                outputholder.add(matrixholder); 
                                
                    			
                                
                    			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    			int numNotWords = counter-pamiablecounter;
                    			double wordSize = (finalWord.length * 55);
                    			int numColmaybe = (int) Math.floor(screenSize.getWidth()/wordSize);
                    			int numCol=0;
                    			if (numColmaybe<numNotWords || numNotWords==0) {
                    				numCol = numColmaybe;
                    			}
                    			else {
                    				numCol = numNotWords;
                    			}
                    			JPanel pnotresultsLabel = new JPanel(new GridLayout(0,numCol));
                    			pnotresultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    			if (!(amiablecounter==pamiablecounter)) {
                    				for (int i=0;i<numWords;i++) {
                    					if (matchIndex[i][0]==1 && matchIndex[i][1]==0) {
                    							String notconvertWord = new String("");
                    							String notpconvertWord = new String("");
                    							List<Integer> cW = permute.get(i);
                                				int[] notcurrentWord = cW.stream().mapToInt(j->j).toArray(); 
                                				int[] notpcurrentWord = p.ProjectionCalculator(notcurrentWord,setP,enteredAlphabet);
                    							for (int j=0;j<word.length;j++) {
                    								notconvertWord = notconvertWord + pcPM.getCharForNumber(notcurrentWord[j]);
                    							}
                    							notconvertWord = notconvertWord + " (";
                    							for (int j=0;j<pword.length;j++) {
                    								notpconvertWord = notpconvertWord + pcPM.getCharForNumber(notpcurrentWord[j]);
                    							}
                    							notconvertWord = notconvertWord + notpconvertWord + ")";
                    							JLabel notpwords = new JLabel(notconvertWord);
                    							Font notpfont = notpwords.getFont();
                    							notpwords.setFont(new Font(notpfont.getName(), Font.PLAIN, 16));
                    							notpwords.setHorizontalAlignment(JLabel.CENTER);
                    							pnotresultsLabel.add(notpwords);
                    					}
                    				}
                    			
	                    			//output final list and make it look clear
	                                JPanel notpwordLabel = new JPanel();
	                                notpwordLabel.setLayout(new BoxLayout(notpwordLabel, BoxLayout.Y_AXIS));
	                                
	                                JPanel notInfo = new JPanel();
	                                JLabel notInfoBefore = new JLabel("Words that are");
	                                JLabel notInfoLink = new JLabel("<html><u>P-distinct</u><html>");
	                                JLabel notInfoAfter = new JLabel("with your entered word:");
	                                notInfoLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	                                notInfoLink.setForeground(Color.BLUE);
	                                notInfoLink.addMouseListener(new MouseAdapter() {
	                                    @Override
	                                    public void mouseClicked(MouseEvent e) {
	                                    	JDialog dialog1 = new JDialog(frame);
	                                		JPanel container = new JPanel();
	                                		JLabel parikhMatrixInfo = new JLabel("<html><center>Two words are P-distinct if they share the same<br>Parikh matrix, but there exists a set S such that they<br>have different P-Parikh matrices.\"</center><html>");
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
	                                
	                                JLabel notInfo2 = new JLabel("Original Word (Projected Word)");
	                                notInfo.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
	                                notInfo2.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
	                                //Font notInfoFont = notInfo.getFont();
	    							notInfo.setFont(new Font(notInfoFont.getName(), Font.PLAIN, 16));
	    							notInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
	    							//notInfo.setHorizontalAlignment(JLabel.CENTER);
	    							notInfo2.setAlignmentX(Component.CENTER_ALIGNMENT);
	    							notInfo2.setHorizontalAlignment(JLabel.CENTER);
	    							pnotresultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    							notpwordLabel.add(notInfo);
	    							notpwordLabel.add(notInfo2);
	                                notpwordLabel.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
	                    			notpwordLabel.add(pnotresultsLabel);
	                    			notpwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	                    			outputholder.add(notpwordLabel);
	                    			holder.add(outputholder);
                    			}
                    			else {
                    				JPanel notpwordLabel = new JPanel();
	                                notpwordLabel.setLayout(new BoxLayout(notpwordLabel, BoxLayout.Y_AXIS));
	                                notpwordLabel.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
                    				JLabel notInfo = new JLabel();
                    				if (counter==1) {
                    					notInfo.setText("Your word is described uniquely by its Parikh matrix.");
                    				}
                    				else {
                    					JPanel notInfoP = new JPanel();
                    					notInfo.setText("The Parikh matrix of your entered word is not");
                    					JLabel notInfo2 = new JLabel("<html><u>P-distinguishable</u>.<html>");
                    					notInfo2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        notInfo2.setForeground(Color.BLUE);
                                        notInfo2.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseClicked(MouseEvent e) {
                                            	JDialog dialog1 = new JDialog(frame);
                                        		JPanel container = new JPanel();
                                        		JLabel parikhMatrixInfo = new JLabel("<html><center>A Parikh matrix is P-distinguishable if it<br>describes at least two distinct words whose<br>respective P-Parikh matrices are different<br>for some set S.</center><html>");
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
    	    							notpwordLabel.add(notInfoP);
                    				}
	    							notpwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    							outputholder.add(notpwordLabel);
	                    			holder.add(outputholder);
                    			}
                    			
                    			setProgress(9);
                    			
                                revalidate();
                                repaint();
                            	
                            }
                            catch (Exception e1){
                            	setProgress(20);
                            	res.setVisible(true);
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
                    			outputholder.setVisible(false);
                    			
                            }
                    	}
                    	else {
                    		setProgress(20);
                    		res.setVisible(true);
                    		outputholder.setVisible(false);
                        	res.setText(INVALID_SIZE);
                    	}
                    }
                    catch (NumberFormatException ex) {
                    	setProgress(20);
                    	outputholder.setVisible(false);
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
                        	wait.progressInfo.setText("Step 6 of 9 - Calculating Projected Words");
                        }
                        else if ((Integer) e.getNewValue() == 7) {
                        	wait.progressInfo.setText("Step 7 of 9 - Calculating P-Parikh Matrices");
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

	
        	private boolean isValid(int size) {
        		return (size > 1 || size ==0);
            }
        });
        
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
                    	JPanel info1 = new JPanel();
                    	JLabel infoText = new JLabel("The red words are your entered word and its projection.");
                        info1.add(infoText);
                        outputholder.add(info1);
                        JPanel info2 = new JPanel();
                        info2.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
                        JLabel infoText2 = new JLabel("The orange words are the words that are");
                        JLabel link2 = new JLabel("<html><u>P-amiable</u><html>");
                        JLabel infoText3 = new JLabel("with your word, and their projections.");
                        link2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        link2.setForeground(Color.BLUE);
                        link2.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                            	JDialog dialog1 = new JDialog(frame);
                        		JPanel container = new JPanel();
                        		JLabel parikhMatrixInfo = new JLabel("<html><center>Two words are P-amiable if they share<br>the same Parikh matrix and there does<br>not exist a set S such that they have<br>different P-Parikh matrices.</center><html>");
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
                    	outputholder.setVisible(true);
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
                    		outputholder.setVisible(true);
                            try { //checking if word input is valid
                            	
                            	setProgress(1);
                            	
                            	Component[] ocomponents = outputholder.getComponents();
                                for (Component ocomponent : ocomponents) {
                                    outputholder.remove(ocomponent);  
                                }
                                Component[] mcomponents = matrixholder.getComponents();
                                for (Component mcomponent : mcomponents) {
                                    matrixholder.remove(mcomponent);  
                                }info1.add(infoText);
                                outputholder.add(info1);
                                info2.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
                                link2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                link2.setForeground(Color.BLUE);
                                link2.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                    	JDialog dialog1 = new JDialog(frame);
                                		JPanel container = new JPanel();
                                		JLabel parikhMatrixInfo = new JLabel("<html><center>Two words are P-amiable if they share<br>the same Parikh matrix and there does<br>not exist a set S such that they have<br>different P-Parikh matrices.</center><html>");
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
                            	outputholder.setVisible(true);
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
                    			String defaultTitle = "Words P-Amiable With " + convertinputWord + ".txt";
                    			chooser.setSelectedFile(new File(defaultTitle));
                    			int returnVal = chooser.showSaveDialog(enterFile);
                    			//JPanel resultsLabel = new JPanel();
                    			
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
                            	
                            	setProgress(3);
                            	
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
    							//parikhMatrixl.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
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
                                
                                setProgress(4);
                                
            					//now find all permutations of firstWord
                    			PermuteArrayWithDuplicates perm = new PermuteArrayWithDuplicates();
                    			List<List<Integer>> permute = perm.permute(word);
                    			
                    			setProgress(5);
                    			
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
                    			
                    			setProgress(6);
                                
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
            					
            					
                                Projection p = new Projection();
                                int[] pword = p.ProjectionCalculator(finalWord,setP,enteredAlphabet);
                                int[][] pparikhMatrix = pM.ParikhMatrix(pword,setP.length+1);
                                
                                String convertpinputWord = new String("");
                            	for (int j=0;j<pword.length;j++) {
            						ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
            						convertpinputWord = convertpinputWord + cPM.getCharForNumber(pword[j]);
            					}
                            	add_line.printf("%s", convertpinputWord);
                    			add_line.printf("%n");
                    			add_line.printf("%n");
                    			
                    			String start3 = new String("The P-Parikh matrix of your word ");
                    			add_line.printf("%s", start3);
            					add_line.printf("%s"+":"+"%n", convertinputWord);
                    			//add_line.close();
                    			for (int i=0;i<pparikhMatrix.length;i++) {
                    				for (int j=0;j<pparikhMatrix.length;j++) {
                    					add_line.printf("%4d", pparikhMatrix[i][j]);
                    				}
                    				add_line.printf("%n");
                    			}
                    			add_line.printf("%n");
                    			
                    			String start4 = new String("Words that share a Parikh matrix and P-Parikh matrix with your word ");
                    			add_line.printf("%s", start4);
            					add_line.printf("%s"+":"+"%n", convertinputWord);
                                
                                setProgress(7);
                                
                    			//remove any words with incorrect matrix
                    			int pnumWords = permute.size();
                    			int[][] presults = new int[pnumWords][pword.length];
                    			int pcounter = 0;
                    			ParikhMatrixCalculator pcPM = new ParikhMatrixCalculator();
                    			for (int i=0;i<pnumWords;i++) {
                    				List<Integer> pcW = permute.get(i);
                    				int[] currentWord = pcW.stream().mapToInt(j->j).toArray(); 
                    				int[] pcurrentWord = p.ProjectionCalculator(currentWord,setP,enteredAlphabet);
                    				int[][] pcurrentParikhMatrix = pcPM.ParikhMatrix(pcurrentWord,setP.length+1);
                    				if (Arrays.deepEquals(pparikhMatrix,pcurrentParikhMatrix)) {
                    					presults[pcounter] = pcurrentWord;
                    					pcounter = pcounter+1;
                    					matchIndex[i][1]=1;
                    				}
                    				else {
                    					matchIndex[i][1]=0;
                    				}
                    			}
                    			
                    			setProgress(8);
                    			
                    			//convert list of arrays into words that use letters
                    			JPanel resultsLabel = new JPanel();
                    			resultsLabel.setLayout(new BoxLayout(resultsLabel, BoxLayout.Y_AXIS));
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
	                    					Font font = words.getFont();
	                    					words.setFont(new Font(font.getName(), Font.PLAIN, 18));
	                    					words.setHorizontalAlignment(JLabel.CENTER);
	                    					if (Arrays.equals(finalWord,currentWord)) {
	                    						words.setForeground(Color.red);
	                    					}
	                    					else if (matchIndex[i][1]==1 && matchIndex[i][0]==1) {
	                    						Color darkOrange = new Color(230,130,0);
	                    						words.setForeground(darkOrange);
	                    					}
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
                                
                                //JLabel lParikhMatrix= new JLabel("<html><u>L-Parikh Matrix</u>:<html>");
                                pParikhMatrix.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                pParikhMatrix.setForeground(Color.BLUE);
                                pParikhMatrix.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
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

                    			
                    			//convert list of arrays into words that use letters
                    			JPanel presultsLabel = new JPanel();
                    			presultsLabel.setLayout(new BoxLayout(presultsLabel, BoxLayout.Y_AXIS));
                    			presultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    			int pamiablecounter = 0;
                    				for (int i=0;i<numWords;i++) {
                    					if (matchIndex[i][0]==1 && matchIndex[i][1]==1) {
                    							String convertWord2 = new String("");
                    							String pconvertWord = new String("");
                    							List<Integer> cW = permute.get(i);
                                				int[] currentWord = cW.stream().mapToInt(j->j).toArray(); 
                                				int[] pcurrentWord = p.ProjectionCalculator(currentWord,setP,enteredAlphabet);
                                				for (int j=0;j<word.length;j++) {
                    								convertWord2 = convertWord2 + pcPM.getCharForNumber(currentWord[j]);
                    							}
                                				for (int j=0;j<pword.length;j++) {
                    								pconvertWord = pconvertWord + pcPM.getCharForNumber(pcurrentWord[j]);
                    							}
                                				add_line.printf("%s" + "%n", convertWord2);
                    							JLabel pwords = new JLabel(pconvertWord);
                    							Font pfont = pwords.getFont();
                    							pwords.setFont(new Font(pfont.getName(), Font.PLAIN, 18));
                    							if (Arrays.equals(finalWord,currentWord)) {
    	                    						pwords.setForeground(Color.red);
    	                    					}
    	                    					else if (matchIndex[i][1]==1 && matchIndex[i][0]==1) {
    	                    						Color darkOrange = new Color(230,130,0);
    	                    						pwords.setForeground(darkOrange);
    	                    					}
                    							pwords.setHorizontalAlignment(JLabel.CENTER);
                    							presultsLabel.add(pwords);
                    							pamiablecounter = pamiablecounter+1;
                    					}
                    				}
                                JPanel pwordLabel = new JPanel();
                                //lwordLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
                                pwordLabel.setLayout(new BoxLayout(pwordLabel, BoxLayout.Y_AXIS));
                    			pwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    			pwordLabel.add(presultsLabel);
                    			c.gridx=0;
    							c.gridy=2;
    							c.fill=GridBagConstraints.HORIZONTAL;
    							c.anchor=GridBagConstraints.NORTH;
                                pparikh.add(pwordLabel,c);
                                GridBagConstraints gbcFiller = new GridBagConstraints();
                                gbcFiller.weighty = 1.0;
                                gbcFiller.fill = GridBagConstraints.BOTH;
                                gbcFiller.gridheight=GridBagConstraints.REMAINDER;
                                pparikh.add(Box.createGlue(), gbcFiller);
                                matrixholder.add(pparikh);
                                outputholder.add(matrixholder); 
                                
                                add_line.printf("%n");
                                String start5 = new String("Words that share a Parikh matrix with your word ");
                    			add_line.printf("%s", start5);
            					add_line.printf("%s"+" but do not share a P-Parikh matrix with it:"+"%n", convertinputWord);
                    			
                                
                    			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    			int numNotWords = counter-pamiablecounter;
                    			double wordSize = (finalWord.length * 55);
                    			int numColmaybe = (int) Math.floor(screenSize.getWidth()/wordSize);
                    			int numCol=0;
                    			if (numColmaybe<numNotWords || numNotWords==0) {
                    				numCol = numColmaybe;
                    			}
                    			else {
                    				numCol = numNotWords;
                    			}
                    			JPanel pnotresultsLabel = new JPanel(new GridLayout(0,numCol));
                    			pnotresultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    			if (!(amiablecounter==pamiablecounter)) {
                    				for (int i=0;i<numWords;i++) {
                    					if (matchIndex[i][0]==1 && matchIndex[i][1]==0) {
                    							String notconvertWord = new String("");
                    							String notpconvertWord = new String("");
                    							List<Integer> cW = permute.get(i);
                                				int[] notcurrentWord = cW.stream().mapToInt(j->j).toArray(); 
                                				int[] notpcurrentWord = p.ProjectionCalculator(notcurrentWord,setP,enteredAlphabet);
                    							for (int j=0;j<word.length;j++) {
                    								notconvertWord = notconvertWord + pcPM.getCharForNumber(notcurrentWord[j]);
                    							}
                    							add_line.printf("%s" + "%n", notconvertWord);
                    							notconvertWord = notconvertWord + " (";
                    							for (int j=0;j<pword.length;j++) {
                    								notpconvertWord = notpconvertWord + pcPM.getCharForNumber(notpcurrentWord[j]);
                    							}
                    							notconvertWord = notconvertWord + notpconvertWord + ")";
                    							JLabel notpwords = new JLabel(notconvertWord);
                    							Font notpfont = notpwords.getFont();
                    							notpwords.setFont(new Font(notpfont.getName(), Font.PLAIN, 16));
                    							notpwords.setHorizontalAlignment(JLabel.CENTER);
                    							pnotresultsLabel.add(notpwords);
                    					}
                    				}
                    			
	                    			//output final list and make it look clear
	                                JPanel notpwordLabel = new JPanel();
	                                notpwordLabel.setLayout(new BoxLayout(notpwordLabel, BoxLayout.Y_AXIS));
	                                
	                                JPanel notInfo = new JPanel();
	                                JLabel notInfoBefore = new JLabel("Words that are");
	                                JLabel notInfoLink = new JLabel("<html><u>P-distinct</u><html>");
	                                JLabel notInfoAfter = new JLabel("with your entered word:");
	                                notInfoLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	                                notInfoLink.setForeground(Color.BLUE);
	                                notInfoLink.addMouseListener(new MouseAdapter() {
	                                    @Override
	                                    public void mouseClicked(MouseEvent e) {
	                                    	JDialog dialog1 = new JDialog(frame);
	                                		JPanel container = new JPanel();
	                                		JLabel parikhMatrixInfo = new JLabel("<html><center>Two words are P-distinct if they share the same<br>Parikh matrix, but there exists a set S such that they<br>have different P-Parikh matrices.\"</center><html>");
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
	                                
	                                JLabel notInfo2 = new JLabel("Original Word (Projected Word)");
	                                notInfo.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
	                                notInfo2.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
	                                //Font notInfoFont = notInfo.getFont();
	    							notInfo.setFont(new Font(notInfoFont.getName(), Font.PLAIN, 16));
	    							notInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
	    							//notInfo.setHorizontalAlignment(JLabel.CENTER);
	    							notInfo2.setAlignmentX(Component.CENTER_ALIGNMENT);
	    							notInfo2.setHorizontalAlignment(JLabel.CENTER);
	    							pnotresultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    							notpwordLabel.add(notInfo);
	    							notpwordLabel.add(notInfo2);
	                                notpwordLabel.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
	                    			notpwordLabel.add(pnotresultsLabel);
	                    			notpwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	                    			outputholder.add(notpwordLabel);
	                    			holder.add(outputholder);
                    			}
                    			else {
                    				JPanel notpwordLabel = new JPanel();
	                                notpwordLabel.setLayout(new BoxLayout(notpwordLabel, BoxLayout.Y_AXIS));
	                                notpwordLabel.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
                    				JLabel notInfo = new JLabel();
                    				if (counter==1) {
                    					add_line.printf("Your word is described uniquely by its Parikh matrix.");
                    					notInfo.setText("Your word is described uniquely by its Parikh matrix.");
                    				}
                    				else {
                    					JPanel notInfoP = new JPanel();
                    					notInfo.setText("The Parikh matrix of your entered word is not");
                    					add_line.printf("The Parikh matrix of your entered word is not P-distinguishable.");
                    					JLabel notInfo2 = new JLabel("<html><u>P-distinguishable</u>.<html>");
                    					notInfo2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                        notInfo2.setForeground(Color.BLUE);
                                        notInfo2.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseClicked(MouseEvent e) {
                                            	JDialog dialog1 = new JDialog(frame);
                                        		JPanel container = new JPanel();
                                        		JLabel parikhMatrixInfo = new JLabel("<html><center>A Parikh matrix is P-distinguishable if it<br>describes at least two distinct words whose<br>respective P-Parikh matrices are different<br>for some set S.</center><html>");
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
    	    							notpwordLabel.add(notInfoP);
                    				}
	    							notpwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    							outputholder.add(notpwordLabel);
	                    			holder.add(outputholder);
                    			}
                    			add_line.close();
                    			setProgress(9);
                    			
                                revalidate();
                                repaint();
                    			}
                            }
                            catch (Exception e1){
                            	setProgress(20);
                            	res.setVisible(true);
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
                    			outputholder.setVisible(false);
                    			
                            }
                    	}
                    	else {
                    		setProgress(20);
                    		res.setVisible(true);
                    		outputholder.setVisible(false);
                        	res.setText(INVALID_SIZE);
                    	}
                    }
                    catch (NumberFormatException ex) {
                    	setProgress(20);
                    	outputholder.setVisible(false);
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
                        	wait.progressInfo.setText("Step 6 of 9 - Calculating Projected Words");
                        }
                        else if ((Integer) e.getNewValue() == 7) {
                        	wait.progressInfo.setText("Step 7 of 9 - Calculating P-Parikh Matrices");
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

	
        	private boolean isValid(int size) {
        		return (size > 1 || size ==0);
            }
        });
        
        
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
    	container.add(new PAmiableWordsInterface());
    	JScrollPane scrPane = new JScrollPane(container);
    	scrPane.getVerticalScrollBar().setUnitIncrement(16);
    	scrPane.getHorizontalScrollBar().setUnitIncrement(16);
    	
    	//frame formatting
        JFrame frame = new JFrame("Amiable Words And P-Amiable Words");
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
