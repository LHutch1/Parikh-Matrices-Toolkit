import java.awt.*;  
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class IsParikhInterface extends JPanel {

	private static final long serialVersionUID = 1L;
	//private static final String INVALID_SIZE = "Parikh matrices must be of at least size 3x3.";
	private static final String INVALID_TYPE = "All elements must be of type integer.";
	private static final String INCOMPLETE_MAT = "Please enter a value for every element of the matrix.";
	private static final String INVALID_INTEGER = "All elements must be greater than or equal to zero.";

	//private static JTextField sizeField;
	//private static JLabel res;
	private static JLabel matError;

	//public SwingWorker<Void, Void> mySwingWorker;

	public JFrame frame;

	public IsParikhInterface() {

		matError = new JLabel();
		matError.setVisible(false);
		JPanel input = new JPanel();
		input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));
		if (!Home.save.isSelected()) {
			if (Home.mySwingWorker.isCancelled()) {
				return;
			}
			/*	btn.addActionListener(new ActionListener() {
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
										protected Void doInBackground() throws Exception { */
			try {
				JPanel words = new JPanel();
				String content = Home.sizeField.getText();
				if (content.length()==0){
					throw new Exception("No size.");
				}
				int size = -1;
				size = Integer.parseInt(content);
				final Integer innerSize = new Integer(size);
				int[][] inpMat = new int [size] [size] ;
				for (int i=0; i<=size-1;i++) {
					for (int j=0; j<=size-1;j++) {
						if (i==j) {
							inpMat[i][j]=1;
						}
						if (j<i) {
							inpMat[i][j]=0;
						}
					}
				}
				if (Home.mySwingWorker.isCancelled()) {
					return;
				}
				try {
					matError.setVisible(false);
					words.setVisible(true);

					Component[] wcomponents = words.getComponents();
					for (Component wcomponent : wcomponents) {
						words.remove(wcomponent);  
					}
					if (Home.mySwingWorker.isCancelled()) {
						return;
					}
					words.setLayout(new BoxLayout(words, BoxLayout.Y_AXIS));

					//setProgress(1);

					int sum = 0;
					int count = 0;
					//getting values entered by user and adding them to matrix for handling
					for (int i=0; i<=innerSize-1;i++) {
						for (int j=0; j<=innerSize-1;j++) {
							if (j>i) {
								if(isElementValid(Integer.parseInt(Home.inpText[i][j].getText()))) {
									String intcontent = Home.inpText[i][j].getText();
									inpMat[i][j]=Integer.parseInt(intcontent);
								}
								else {
									matError.setVisible(true);
									words.setVisible(false);
									matError.setText(INVALID_INTEGER);
									//setProgress(20);
								}
							}
						}
					}

					//setProgress(2);
					if (Home.mySwingWorker.isCancelled()) {
						return;
					}
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

					//setProgress(3);
					if (Home.mySwingWorker.isCancelled()) {
						return;
					}
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
					if (Home.mySwingWorker.isCancelled()) {
						return;
					}
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

						//setProgress(4);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//firstWord is lexicographically smallest word with correct number of letters

						//now find all permutations of firstWord
						PermuteArrayWithDuplicates perm = new PermuteArrayWithDuplicates();
						List<List<Integer>> permute = perm.permuteOld(firstWord);

						//setProgress(5);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
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

						//setProgress(6);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
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

					//setProgress(7);
					if (Home.mySwingWorker.isCancelled()) {
						return;
					}
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
					//setProgress(20);
					Home.dialog.setVisible(false);
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
			}catch (Exception e1){
				Home.dialog.setVisible(false);
				String noWord = new String("No size.");
				if (noWord.equals(e1.getMessage())){
					Home.res.setText("");
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
							});*/
		}
		else {
			JFileChooser chooser = new JFileChooser();
			//JButton inpFile = new JButton("Submit (and save as .txt file)");
			//inpFile.setAlignmentX(Component.CENTER_ALIGNMENT);
			/*
							//Calculate associated words and display them when "Submit" is pressed
							inpFile.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e){
									DialogWait wait = new DialogWait();

									mySwingWorker = new SwingWorker<Void, Void>() {

										@Override
										protected Void doInBackground() throws Exception {*/
			try {
				JPanel words = new JPanel();
				String content = Home.sizeField.getText();
				if (content.length()==0){
					throw new Exception("No size.");
				}
				if (Home.mySwingWorker.isCancelled()) {
					return;
				}
				int size = -1;
				size = Integer.parseInt(content);
				final Integer innerSize = new Integer(size);
				int[][] inpMat = new int [size] [size] ;
				for (int i=0; i<=size-1;i++) {
					for (int j=0; j<=size-1;j++) {
						if (i==j) {
							inpMat[i][j]=1;
						}
						if (j<i) {
							inpMat[i][j]=0;
						}
					}
				}
				try {
					matError.setVisible(false);
					words.setVisible(true);

					Component[] wcomponents = words.getComponents();
					for (Component wcomponent : wcomponents) {
						words.remove(wcomponent);  
					}

					words.setLayout(new BoxLayout(words, BoxLayout.Y_AXIS));

					//setProgress(1);
					if (Home.mySwingWorker.isCancelled()) {
						return;
					}
					int sum = 0;
					int count = 0;
					//getting values entered by user and adding them to matrix for handling
					for (int i=0; i<=innerSize-1;i++) {
						for (int j=0; j<=innerSize-1;j++) {
							if (j>i) {
								if(isElementValid(Integer.parseInt(Home.inpText[i][j].getText()))) {
									String intcontent = Home.inpText[i][j].getText();
									inpMat[i][j]=Integer.parseInt(intcontent);
								}
								else {
									matError.setVisible(true);
									words.setVisible(false);
									matError.setText(INVALID_INTEGER);
									//setProgress(20);
								}
							}
						}
					}

					//setProgress(2);
					if (Home.mySwingWorker.isCancelled()) {
						return;
					}
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

					//setProgress(3);
					if (Home.mySwingWorker.isCancelled()) {
						return;
					}
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

						//setProgress(4);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						//firstWord is lexicographically smallest word with correct number of letters

						//now find all permutations of firstWord
						PermuteArrayWithDuplicates perm = new PermuteArrayWithDuplicates();
						List<List<Integer>> permute = perm.permuteOld(firstWord);

						//setProgress(5);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
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

						//setProgress(6);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}

						//Open save dialog and create file

						chooser.setDialogTitle("Select Folder");
						String defaultTitle = "Decide If Matrix Is Parikh.txt";
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
							if (Home.mySwingWorker.isCancelled()) {
								return;
							}
						}
					}
					else {
						JLabel noSol = new JLabel("This matrix is not a Parikh matrix.");
						noSol.setFont(new Font(noSol.getFont().getName(), Font.PLAIN, 18));
						resultsLabel.add(noSol);
						if (Home.mySwingWorker.isCancelled()) {
							return;
						}
						chooser.setDialogTitle("Select Folder");
						String defaultTitle = "Decide If Matrix Is Parikh.txt";
						chooser.setSelectedFile(new File(defaultTitle));
						int returnVal = chooser.showSaveDialog(null);

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

					//setProgress(7);
					if (Home.mySwingWorker.isCancelled()) {
						return;
					}
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
					//setProgress(20);
					Home.dialog.setVisible(false);
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
			}catch (Exception e1){
				Home.dialog.setVisible(false);
				String noWord = new String("No size.");
				if (noWord.equals(e1.getMessage())){
					Home.res.setText("");
				}
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
		 */

		//}
		//);
		matError.setAlignmentX(Component.CENTER_ALIGNMENT);
		input.add(matError);
		add(input, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

	private boolean isElementValid(int elementSize) {
		return elementSize >= 0;
	}

	//make frame for user interface
	public static void actioned() {
		Component[] rcomponents = Home.resultsPanel.getComponents();
		for (Component rcomponent : rcomponents) {
			Home.resultsPanel.remove(rcomponent);  
		}
		Home.res.setVisible(false);
		//make frame scroll-able if matrix is large enough
		JPanel container = new JPanel();
		container.add(new IsParikhInterface());
		Home.resultsPanel.add(container);
		Home.resultsPanel.setMinimumSize(new Dimension(500,500));
		Home.resultsPanel.revalidate();
		Home.resultsPanel.repaint();
		Home.centraliser.revalidate();
		Home.centraliser.repaint();
		Home.homeFrame.revalidate();
		Home.homeFrame.repaint();
		Home.dialog.setVisible(false);
		/*JScrollPane scrPane = new JScrollPane(container);
    	scrPane.getVerticalScrollBar().setUnitIncrement(16);
    	scrPane.getHorizontalScrollBar().setUnitIncrement(16);

    	//frame formatting
        frame = new JFrame("Decide If The Matrix Is A Parikh Matrix");
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
