import java.awt.*;  
import java.awt.event.*;
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
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class IsParikhUploadInterface extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;

	private static final String INVALID_SIZE = "Parikh matrices must be of at least size 3x3.";
	private static final String INVALID_TYPE = "All elements must be of type integer.";
	private static final String INCOMPLETE_MAT = "Please enter a value for every element of the matrix.";
	private static final String INVALID_INTEGER = "All elements must be greater than or equal to zero.";
	
    private static JTextField sizeField;
    private static JLabel res;
    private static JLabel matError;
   
    public SwingWorker<Void, Void> mySwingWorker;
    
    public JFrame frame;
    
    public IsParikhUploadInterface() {
        setLayout(new BorderLayout());
        
        //Panel to hold starting components
        JPanel northPanel = new JPanel(new BorderLayout());
        
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
        JLabel label2 = new JLabel("size.");
        northPanel1.add(label);
        northPanel1.add(link);
        northPanel1.add(label2);
        sizeField = new JTextField(10);
        northPanel1.add(sizeField);
        northPanel1.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        northPanel.add(northPanel1, BorderLayout.NORTH);

        //Panel to hold Enter button and error messages
        JPanel northPanel2 = new JPanel(new GridLayout(0,1));  
        JPanel enter = new JPanel();
        JButton btn = new JButton("Enter");
        enter.add(btn);
        northPanel2.add(enter);
        JPanel errortext = new JPanel();
        res = new JLabel();
        res.setVisible(false);
        errortext.add(res);
        northPanel2.add(errortext);
        northPanel.add(northPanel2, BorderLayout.CENTER);
        
        add(northPanel,BorderLayout.NORTH);
        
        //Panel to hold matrix and submit button - only appears if input is correct
        matError = new JLabel();
        matError.setVisible(false);
        JPanel input = new JPanel();
        input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));
        btn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e4){
                    String content = sizeField.getText();
                    int size = -1;
                    try{
                        size = Integer.parseInt(content);
                        if(isValid(size)) {
                            res.setVisible(false);
                            Component[] components = input.getComponents();
                            for (Component component : components) {
                                input.remove(component);  
                            }
                            input.setVisible(true);
                            JPanel matrixholder = new JPanel();
                            JPanel matrix = new JPanel();
                            matrix.setLayout(new GridLayout(size,size,2,2));
                            int[][] inpMat = new int [size] [size] ;  //matrix to hold submitted text
                            JTextField[][] inpText = new JTextField[size][size]; //matrix to hold text fields
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
                            
                            //adding brackets to either side of input
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
                            
                            input.add(matrixholder);
                            JButton inp = new JButton("Submit");
                            inp.setAlignmentX(Component.CENTER_ALIGNMENT);
                            
                            //creating variables for use in button click action
                            final Integer innerSize = new Integer(size); //workaround to use size in inner class
                            JPanel words = new JPanel();
                            
                            //Calculate associated words and display them when "Submit" is pressed
                            inp.addActionListener(new ActionListener() {
                            	@Override
                            	public void actionPerformed(ActionEvent e){
                            		DialogWait wait = new DialogWait();

                            		mySwingWorker = new SwingWorker<Void, Void>() {

                            		@Override
                            		protected Void doInBackground() throws Exception {
                            		try {
                            			matError.setVisible(false);
                            			words.setVisible(true);
                            			
                            			Component[] wcomponents = words.getComponents();
                                        for (Component wcomponent : wcomponents) {
                                            words.remove(wcomponent);  
                                        }
                                        
                            			words.setLayout(new BoxLayout(words, BoxLayout.Y_AXIS));
                            		
                            			setProgress(1);
                            			
                            			int sum = 0;
                            			int count = 0;
                            			//getting values entered by user and adding them to matrix for handling
                            			for (int i=0; i<=innerSize-1;i++) {
                            				for (int j=0; j<=innerSize-1;j++) {
                            					if (j>i) {
                            						if(isElementValid(Integer.parseInt(inpText[i][j].getText()))) {
                            							String intcontent = inpText[i][j].getText();
                            							inpMat[i][j]=Integer.parseInt(intcontent);
                            						}
                            						else {
                            							matError.setVisible(true);
                                            			words.setVisible(false);
                                            			matError.setText(INVALID_INTEGER);
                                            			setProgress(20);
                            						}
                            					}
                            				}
                            			}
                            			
                            			setProgress(2);
                            			
                            			//basic checks to speed up process by checking maximum values and zeros
                            			
                            			//get length of word
                            			int len = 0;
                            			int[] numLet = new int[innerSize-1];
                            			for (int i=0; i<=innerSize-1;i++) {
                            				for (int j=0; j<=innerSize-1;j++) {
                            					if (i+1==j) {
                            						len = len + inpMat[i][j];
                            						numLet[i]=inpMat[i][j];
                            					}
                            				}
                            			} 
                            			
                            			setProgress(3);
                            			
                            			for (int i=0; i<innerSize;i++) {
                            				for (int j=0; j<innerSize;j++) {
                            					if (j>i) {
                            						count = count+1;
                            						int diff = j-i;
                            						int max = 1;
                            						for (int k=0;k<diff;k++) {
                            							max = max * numLet[i+k];
                            						}
                            						if (inpMat[i][j]<=max) {
                            							sum = sum+1;
                            						}
                            					}
                            				}
                            			}
                            			
                            			int sum0 = 0;
                            			for (int i=0; i<innerSize;i++) {
                            				for (int j=0; j<innerSize;j++) {
                            					if (j>i) {
                            						if (inpMat[i+1][j]==0 && inpMat[i][j]>0) {
                            							sum0 = 1;
                            						}
                            						else if (inpMat[i][j-1]==0 && inpMat[i][j]>0) {
                            							sum0 = 1;
                            						}
                            					}
                            				}
                            			}
                            			
                            			JPanel resultsLabel = new JPanel();
                            			resultsLabel.setLayout(new BoxLayout(resultsLabel, BoxLayout.Y_AXIS));
                            			
                            			if (sum==count && sum0==0){
	                            			
	                            			//find all words with correct number of each letter
	                            			int numChars = numLet[0];
	                            			int prevNumChars = 100*(numLet[0]+100);
	                            			int[] firstWord = new int[len];
	                            			for (int i=0;i<=innerSize-2;i++) { 
	                            				for (int j=0;j<=len-1;j++) {
	                            					if (j<numChars && i==0) {
	                            						firstWord[j]=i;
	                            					}
	                            					else if (j<numChars && j>=prevNumChars) {
	                            						firstWord[j]=i;
	                            					}
	                            				}
	                            				prevNumChars=numChars;
	                            				if (i<innerSize-2) {
	                            					numChars = numChars+numLet[i+1];
	                            				}
	                            			}
	                            			
	                            			setProgress(4);
	                            			
	                            			//firstWord is lexicographically smallest word with correct number of letters
	                            			
	                            			//now find all permutations of firstWord
	                            			PermuteArrayWithDuplicates perm = new PermuteArrayWithDuplicates();
	                            			List<List<Integer>> permute = perm.permute(firstWord);
	                            			
	                            			setProgress(5);
	                            			
	                            			//make it an output-able form for error checking
	                            			String perms =Arrays.toString(permute.toArray());
	                            			JLabel permsLabel = new JLabel(perms);
	                            			permsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	                            			
	                            			//remove any words with incorrect matrix
	                            			int numWords = permute.size();
	                            			int[][] results = new int[numWords][len];
	                            			int counter = 0;
	                            			for (int i=0;i<numWords;i++) {
	                            				List<Integer> cW = permute.get(i);
	                            				int[] currentWord = cW.stream().mapToInt(j->j).toArray(); 
	                            				ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
	                            				int[][] currentParikhMatrix = cPM.ParikhMatrix(currentWord,innerSize);
	                            				if (Arrays.deepEquals(inpMat,currentParikhMatrix)) {
	                            					results[counter] = currentWord;
	                            					counter = counter+1;
	                            				}
	                            			}
	                            			
	                            			setProgress(6);
	                            			
	                            			//convert list of arrays into words that use letters
	                            			
	                            			if (counter>0) {
	                            				JLabel noSol = new JLabel("This matrix is a Parikh matrix.");
	                            				noSol.setFont(new Font(noSol.getFont().getName(), Font.PLAIN, 18));
	                            				resultsLabel.add(noSol);
	                            			}
	                            			else {
	                            				JLabel noSol = new JLabel("This matrix is not a Parikh matrix.");
	                            				noSol.setFont(new Font(noSol.getFont().getName(), Font.PLAIN, 18));
	                            				resultsLabel.add(noSol);
	                            			}
                            			}
                            			else {
                            				JLabel noSol = new JLabel("This matrix is not a Parikh matrix.");
                            				noSol.setFont(new Font(noSol.getFont().getName(), Font.PLAIN, 18));
                            				resultsLabel.add(noSol);
                            			}
                            			
                            			setProgress(7);
                            			
                            			//output final list and make it look clear
                            			resultsLabel.setBorder(BorderFactory.createEmptyBorder(60,30,30,30));
                            			resultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                            			words.add(resultsLabel);
                            			words.setAlignmentX(Component.CENTER_ALIGNMENT);
                            			input.add(words);
                            			revalidate();
                            			repaint();
                            		}
                            		catch(NumberFormatException ex) {
                            			setProgress(20);
                            			matError.setVisible(true);
                            			String empty = new String("For input string: \"\"");
                            			if (empty.equals(ex.getMessage())) {
                            				matError.setText(INCOMPLETE_MAT);
                            			}
                            			else {
                            				matError.setText(INVALID_TYPE);
                            			}
                            			words.setVisible(false);
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
                                        	wait.progressInfo.setText("Step 2 of 7 - Creating Matrix");
                                        }
                                        else if ((Integer) e.getNewValue() == 3) {
                                        	wait.progressInfo.setText("Step 3 of 7 - Finding Example Word");
                                        }
                                        else if ((Integer) e.getNewValue() == 4) {
                                        	wait.progressInfo.setText("Step 4 of 7 - Calculating Permutations");
                                        }
                                        else if ((Integer) e.getNewValue() == 5) {
                                        	wait.progressInfo.setText("Step 5 of 7 - Finding Parikh Matrices");
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
                    		wait.makeWait(e);
                            	}
                            });
                            
                            JFileChooser chooser = new JFileChooser();
                            JButton inpFile = new JButton("Submit (and save as .txt file)");
                            inpFile.setAlignmentX(Component.CENTER_ALIGNMENT);
                            
                            //Calculate associated words and display them when "Submit" is pressed
                            inpFile.addActionListener(new ActionListener() {
                            	@Override
                            	public void actionPerformed(ActionEvent e){
                            		DialogWait wait = new DialogWait();

                            		mySwingWorker = new SwingWorker<Void, Void>() {

                            		@Override
                            		protected Void doInBackground() throws Exception {
                            		try {
                            			matError.setVisible(false);
                            			words.setVisible(true);
                            			
                            			Component[] wcomponents = words.getComponents();
                                        for (Component wcomponent : wcomponents) {
                                            words.remove(wcomponent);  
                                        }
                                        
                            			words.setLayout(new BoxLayout(words, BoxLayout.Y_AXIS));
                            		
                            			setProgress(1);
                            			
                            			int sum = 0;
                            			int count = 0;
                            			//getting values entered by user and adding them to matrix for handling
                            			for (int i=0; i<=innerSize-1;i++) {
                            				for (int j=0; j<=innerSize-1;j++) {
                            					if (j>i) {
                            						if(isElementValid(Integer.parseInt(inpText[i][j].getText()))) {
                            							String intcontent = inpText[i][j].getText();
                            							inpMat[i][j]=Integer.parseInt(intcontent);
                            						}
                            						else {
                            							matError.setVisible(true);
                                            			words.setVisible(false);
                                            			matError.setText(INVALID_INTEGER);
                                            			setProgress(20);
                            						}
                            					}
                            				}
                            			}
                            			
                            			setProgress(2);
                            			
                            			//basic checks to speed up process by checking maximum values and zeros
                            			
                            			//get length of word
                            			int len = 0;
                            			int[] numLet = new int[innerSize-1];
                            			for (int i=0; i<=innerSize-1;i++) {
                            				for (int j=0; j<=innerSize-1;j++) {
                            					if (i+1==j) {
                            						len = len + inpMat[i][j];
                            						numLet[i]=inpMat[i][j];
                            					}
                            				}
                            			} 
                            			
                            			setProgress(3);
                            			
                            			for (int i=0; i<innerSize;i++) {
                            				for (int j=0; j<innerSize;j++) {
                            					if (j>i) {
                            						count = count+1;
                            						int diff = j-i;
                            						int max = 1;
                            						for (int k=0;k<diff;k++) {
                            							max = max * numLet[i+k];
                            						}
                            						if (inpMat[i][j]<=max) {
                            							sum = sum+1;
                            						}
                            					}
                            				}
                            			}
                            			
                            			int sum0 = 0;
                            			for (int i=0; i<innerSize;i++) {
                            				for (int j=0; j<innerSize;j++) {
                            					if (j>i) {
                            						if (inpMat[i+1][j]==0 && inpMat[i][j]>0) {
                            							sum0 = 1;
                            						}
                            						else if (inpMat[i][j-1]==0 && inpMat[i][j]>0) {
                            							sum0 = 1;
                            						}
                            					}
                            				}
                            			}
                            			
                            			JPanel resultsLabel = new JPanel();
                            			resultsLabel.setLayout(new BoxLayout(resultsLabel, BoxLayout.Y_AXIS));
                            			
                            			if (sum==count && sum0==0){
	                            			
	                            			//find all words with correct number of each letter
	                            			int numChars = numLet[0];
	                            			int prevNumChars = 100*(numLet[0]+100);
	                            			int[] firstWord = new int[len];
	                            			for (int i=0;i<=innerSize-2;i++) { 
	                            				for (int j=0;j<=len-1;j++) {
	                            					if (j<numChars && i==0) {
	                            						firstWord[j]=i;
	                            					}
	                            					else if (j<numChars && j>=prevNumChars) {
	                            						firstWord[j]=i;
	                            					}
	                            				}
	                            				prevNumChars=numChars;
	                            				if (i<innerSize-2) {
	                            					numChars = numChars+numLet[i+1];
	                            				}
	                            			}
	                            			
	                            			setProgress(4);
	                            			
	                            			//firstWord is lexicographically smallest word with correct number of letters
	                            			
	                            			//now find all permutations of firstWord
	                            			PermuteArrayWithDuplicates perm = new PermuteArrayWithDuplicates();
	                            			List<List<Integer>> permute = perm.permute(firstWord);
	                            			
	                            			setProgress(5);
	                            			
	                            			//make it an output-able form for error checking
	                            			String perms =Arrays.toString(permute.toArray());
	                            			JLabel permsLabel = new JLabel(perms);
	                            			permsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	                            			
	                            			//remove any words with incorrect matrix
	                            			int numWords = permute.size();
	                            			int[][] results = new int[numWords][len];
	                            			int counter = 0;
	                            			for (int i=0;i<numWords;i++) {
	                            				List<Integer> cW = permute.get(i);
	                            				int[] currentWord = cW.stream().mapToInt(j->j).toArray(); 
	                            				ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
	                            				int[][] currentParikhMatrix = cPM.ParikhMatrix(currentWord,innerSize);
	                            				if (Arrays.deepEquals(inpMat,currentParikhMatrix)) {
	                            					results[counter] = currentWord;
	                            					counter = counter+1;
	                            				}
	                            			}
	                            			
	                            			setProgress(6);
	                            			
	                    					
	                            			//Open save dialog and create file
	                            			
	                            			chooser.setDialogTitle("Select Folder");
	                            			String defaultTitle = "Decide If Matrix Is Parikh.txt";
	                            			chooser.setSelectedFile(new File(defaultTitle));
	                            			int returnVal = chooser.showSaveDialog(inpFile);
	                            			
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
	        	                    			
	                            			//convert list of arrays into words that use letters
	                            			
	                            			if (counter>0) {
	                            				JLabel noSol = new JLabel("This matrix is a Parikh matrix.");
	                            				noSol.setFont(new Font(noSol.getFont().getName(), Font.PLAIN, 18));
	                            				resultsLabel.add(noSol);
	                            				String start = new String("Your matrix (below) IS a Parikh matrix.");
	                                			print_line.printf("%s %n", start);
	                                			print_line.close();
	                                			for (int i=0;i<inpMat.length;i++) {
	                                				for (int j=0;j<inpMat.length;j++) {
	                                					add_line.printf("%4d", inpMat[i][j]);
	                                				}
	                                				add_line.printf("%n");
	                                			}
	                                			add_line.printf("%n");
	                            			}
	                            			else {
	                            				JLabel noSol = new JLabel("This matrix is not a Parikh matrix.");
	                            				noSol.setFont(new Font(noSol.getFont().getName(), Font.PLAIN, 18));
	                            				resultsLabel.add(noSol);
	                            				noSol.setFont(new Font(noSol.getFont().getName(), Font.PLAIN, 18));
	                            				resultsLabel.add(noSol);
	                            				String start = new String("Your matrix (below) IS NOT a Parikh matrix.");
	                                			print_line.printf("%s %n", start);
	                                			print_line.close();
	                                			for (int i=0;i<inpMat.length;i++) {
	                                				for (int j=0;j<inpMat.length;j++) {
	                                					add_line.printf("%4d", inpMat[i][j]);
	                                				}
	                                				add_line.printf("%n");
	                                			}
	                                			add_line.printf("%n");
	                            			}
	                            			add_line.close();
	                            			}
                            			}
                            			else {
                            				JLabel noSol = new JLabel("This matrix is not a Parikh matrix.");
                            				noSol.setFont(new Font(noSol.getFont().getName(), Font.PLAIN, 18));
                            				resultsLabel.add(noSol);
                            				
                            				chooser.setDialogTitle("Select Folder");
	                            			String defaultTitle = "Decide If Matrix Is Parikh.txt";
	                            			chooser.setSelectedFile(new File(defaultTitle));
	                            			int returnVal = chooser.showSaveDialog(inpFile);
	                            			
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
	                            				String start = new String("Your matrix (below) IS NOT a Parikh matrix.");
	                                			print_line.printf("%s %n", start);
	                                			print_line.close();
	                                			for (int i=0;i<inpMat.length;i++) {
	                                				for (int j=0;j<inpMat.length;j++) {
	                                					add_line.printf("%4d", inpMat[i][j]);
	                                				}
	                                				add_line.printf("%n");
	                                			}
	                                			add_line.printf("%n");
	                                			add_line.close();
	                            			}
                            			}
                            			
                            			setProgress(7);
                            			
                            			//output final list and make it look clear
                            			resultsLabel.setBorder(BorderFactory.createEmptyBorder(60,30,30,30));
                            			resultsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                            			words.add(resultsLabel);
                            			words.setAlignmentX(Component.CENTER_ALIGNMENT);
                            			input.add(words);
                            			revalidate();
                            			repaint();
                            		}
                            		catch(NumberFormatException ex) {
                            			setProgress(20);
                            			matError.setVisible(true);
                            			String empty = new String("For input string: \"\"");
                            			if (empty.equals(ex.getMessage())) {
                            				matError.setText(INCOMPLETE_MAT);
                            			}
                            			else {
                            				matError.setText(INVALID_TYPE);
                            			}
                            			words.setVisible(false);
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
                                        	wait.progressInfo.setText("Step 2 of 7 - Creating Matrix");
                                        }
                                        else if ((Integer) e.getNewValue() == 3) {
                                        	wait.progressInfo.setText("Step 3 of 7 - Finding Example Word");
                                        }
                                        else if ((Integer) e.getNewValue() == 4) {
                                        	wait.progressInfo.setText("Step 4 of 7 - Calculating Permutations");
                                        }
                                        else if ((Integer) e.getNewValue() == 5) {
                                        	wait.progressInfo.setText("Step 5 of 7 - Finding Parikh Matrices");
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
                    		wait.makeWait(e);
                            	}
                            });
                            
                            inp.setAlignmentX(Component.CENTER_ALIGNMENT);
                            input.add(inp);
                            input.add(inpFile);
                            matError.setAlignmentX(Component.CENTER_ALIGNMENT);
                            input.add(matError);
                            add(input, BorderLayout.CENTER);
                            revalidate();
                            repaint();
                        } else {
                            res.setVisible(true);
                        	res.setText(INVALID_SIZE);
                        	input.setVisible(false);
                        }
                    } catch(NumberFormatException ex) {
                        res.setVisible(true);
                    	res.setText("Incorrect input type - please enter an integer larger than 2.");
                    	input.setVisible(false);
                    }   
                    revalidate();
                    repaint();
                }
        		private boolean isValid(int size) {
                    return size > 2;
                }
        		private boolean isElementValid(int elementSize) {
                    return elementSize >= 0;
                }
        	}
        );
    }

    
    //make frame for user interface
	public void actionPerformed(ActionEvent e) {
		
		//make frame scroll-able if matrix is large enough
    	JPanel container = new JPanel();
    	container.add(new IsParikhUploadInterface());
    	JScrollPane scrPane = new JScrollPane(container);
    	scrPane.getVerticalScrollBar().setUnitIncrement(16);
    	scrPane.getHorizontalScrollBar().setUnitIncrement(16);
    	
    	//frame formatting
        frame = new JFrame("Decide If The Matrix Is A Parikh Matrix");
        frame.add(scrPane);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
		
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
