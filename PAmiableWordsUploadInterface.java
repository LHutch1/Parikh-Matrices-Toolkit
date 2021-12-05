import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PAmiableWordsUploadInterface extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	public JFrame frame;
	public JFileChooser chooser;
	public JFileChooser chooserSave;
	public int iteration;
	public int[] pset;
	public int[] word;
	public int enteredAlphabet;
	public PAmiableWordsUploadInterface(int[] word, int alphabet, String savePath, int it, int[] pset) {
		try { //checking if word input is valid
			ParikhMatrixCalculator pM = new ParikhMatrixCalculator();
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
			if (maxProjLetter>=alphabet) {
				throw new Exception("Entered projection not possible - alphabet size.");
			}
			else if (projInWord==0) {
				throw new Exception("Entered projection not possible - projection not in word.");
			}

			String convertinputWord = new String("");
			for (int j=0;j<word.length;j++) {
				ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
				convertinputWord = convertinputWord + cPM.getCharForNumber(word[j]);
			}

			//Open save dialog and create file

			String defaultTitle = "Words P-amiable With " + convertinputWord + ".txt";
			chooserSave = new JFileChooser();
			if (it==1) {
				chooserSave.setSelectedFile(new File("P-Parikh Matrix.txt"));
			}
			if (it==0) {
				File outputfile = new File(savePath,defaultTitle);
				outputfile.createNewFile();
				FileWriter write = new FileWriter(outputfile, false);
				FileWriter append = new FileWriter(outputfile, true);
				PrintWriter print_line = new PrintWriter(write);
				PrintWriter add_line = new PrintWriter(append);


				int[][] parikhMatrix = pM.ParikhMatrix(word,alphabet+1);

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
				int[] finalword = word;


				//now find all permutations of firstWord
				PermuteArrayWithDuplicates perm = new PermuteArrayWithDuplicates();
				List<List<Integer>> permute = perm.permute(Arrays.copyOf(word, word.length));


				//remove any words with incorrect matrix
				int numWords = permute.size();
				int[][] results = new int[numWords][finalword.length];
				int counter = 0;
				int[][] matchIndex = new int [numWords][2];
				for (int i=0;i<numWords;i++) {
					List<Integer> cW = permute.get(i);
					int[] currentWord = cW.stream().mapToInt(j->j).toArray(); 
					ParikhMatrixCalculator cPM = new ParikhMatrixCalculator();
					int[][] currentParikhMatrix = cPM.ParikhMatrix(currentWord,alphabet+1);
					if (Arrays.deepEquals(parikhMatrix,currentParikhMatrix)) {
						results[counter] = currentWord;
						counter = counter+1;
						matchIndex[i][0]=1;
					}
					else {
						matchIndex[i][0]=0;
					}
				}


				String start2 = new String("The projection of your word ");
				add_line.printf("%s", start2);
				add_line.printf("%s"+" with your projection set {", convertinputWord);
				for (int i=0;i<pset.length;i++) {
					if (i==pset.length-1) {
						add_line.printf("%s}: %n", pM.getCharForNumber(pset[i]));
					}
					else {
						add_line.printf("%s,", pM.getCharForNumber(pset[i]));
					}
				}


				Projection p = new Projection();
				int[] pword = p.ProjectionCalculator(word,setP,alphabet);
				int[][] pparikhMatrix = pM.ParikhMatrix(pword,setP.length+1);

				String convertpinputWord = new String("");
				for (int j=0;j<pword.length;j++) {
					convertpinputWord = convertpinputWord + pM.getCharForNumber(pword[j]);
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

				//remove any words with incorrect matrix
				int pnumWords = permute.size();
				int[][] presults = new int[pnumWords][pword.length];
				int pcounter = 0;
				ParikhMatrixCalculator pcPM = new ParikhMatrixCalculator();
				for (int i=0;i<pnumWords;i++) {
					List<Integer> pcW = permute.get(i);
					int[] currentWord = pcW.stream().mapToInt(j->j).toArray(); 
					int[] pcurrentWord = p.ProjectionCalculator(currentWord,setP,alphabet);
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

				int amiablecounter = 0;
				int pamiablecounter = 0;
				if (counter>0) {
					for (int i=0;i<numWords;i++) {
						if (matchIndex[i][0]==1) {
							amiablecounter = amiablecounter + 1;
						}
						if (matchIndex[i][0]==1 && matchIndex[i][1]==1) {
							pamiablecounter = pamiablecounter+1;
						}
					}
				}
				if (amiablecounter>0 && pamiablecounter>0 && counter>0) {
					String start4 = new String("Words that share a Parikh matrix and P-Parikh matrix with your word ");
					add_line.printf("%s", start4);
					add_line.printf("%s"+":"+"%n", convertinputWord);
					if (counter>0) {
						for (int i=0;i<numWords;i++) {
							if (matchIndex[i][0]==1) {
								String convertWord = new String("");
								List<Integer> cW = permute.get(i);
								int[] currentWord = cW.stream().mapToInt(j->j).toArray(); 
								for (int j=0;j<finalword.length;j++) {
									convertWord = convertWord + pM.getCharForNumber(currentWord[j]);
								}
							}
						}
					}
					for (int i=0;i<numWords;i++) {
						if (matchIndex[i][0]==1 && matchIndex[i][1]==1) {
							String convertWord2 = new String("");
							String pconvertWord = new String("");
							List<Integer> cW = permute.get(i);
							int[] currentWord = cW.stream().mapToInt(j->j).toArray(); 
							int[] pcurrentWord = p.ProjectionCalculator(currentWord,setP,alphabet);
							for (int j=0;j<finalword.length;j++) {
								convertWord2 = convertWord2 + pcPM.getCharForNumber(currentWord[j]);
							}
							for (int j=0;j<pword.length;j++) {
								pconvertWord = pconvertWord + pcPM.getCharForNumber(pcurrentWord[j]);
							}
							add_line.printf("%s" + "%n", convertWord2);
						}
					}
				}
				else {
					add_line.printf("Your word %s is uniquely described by its P-Parikh matrix. %n", convertinputWord);
				}


				add_line.printf("%n");

				if (!(amiablecounter==pamiablecounter)) {
					String start5 = new String("Words that share a Parikh matrix with your word ");
					add_line.printf("%s", start5);
					add_line.printf("%s"+" but do not share a P-Parikh matrix with it:"+"%n", convertinputWord);

					for (int i=0;i<numWords;i++) {
						if (matchIndex[i][0]==1 && matchIndex[i][1]==0) {
							String notconvertWord = new String("");
							List<Integer> cW = permute.get(i);
							int[] notcurrentWord = cW.stream().mapToInt(j->j).toArray(); 
							for (int j=0;j<finalword.length;j++) {
								notconvertWord = notconvertWord + pcPM.getCharForNumber(notcurrentWord[j]);
							}
							add_line.printf("%s" + "%n", notconvertWord);
						}
					}
				}
				else {
					if (counter==1) {
						add_line.printf("Your word is described uniquely by its Parikh matrix.");
					}
					else {
						add_line.printf("The Parikh matrix of your entered word is not P-distinguishable with your given projection set.");
					}
				}
				add_line.close();
			}
		}
		catch (Exception e1){
			e1.printStackTrace();

		}
	}



	public void actionPerformed(ActionEvent e2) {
		try {

			//Create file chooser for user to upload file of words
			chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
			chooser. setAcceptAllFileFilterUsed(false);
			chooser.setFileFilter(filter);
			chooser.setDialogTitle("Select Input File");
			int result = chooser.showOpenDialog(null);

			//if user uploads a file
			if (result==JFileChooser.APPROVE_OPTION) {
				File uploadedFile = chooser.getSelectedFile();

				//Create file chooser for user to select a location to save the output files to (default to same location as uploaded file)
				chooserSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				disableTextField(chooserSave);  //prevent user from altering file name as these are auto generated
				chooserSave.setDialogTitle("Select Location To Save Output Files");
				chooserSave.setSelectedFile(new File("Words P-Amiable With...")); 
				chooserSave.setCurrentDirectory(chooser.getCurrentDirectory());
				int returnVal = chooserSave.showSaveDialog(frame);
				String path = chooserSave.getCurrentDirectory().getAbsolutePath();

				//if user selects a location
				if (returnVal == JFileChooser.APPROVE_OPTION) {

					//create loading screen and done screen elements
					JFrame loading = new JFrame();
					JPanel loadingP = new JPanel();
					loadingP.setLayout(new BoxLayout(loadingP, BoxLayout.Y_AXIS));
					JLabel loadingL = new JLabel("Loading...");
					JPanel finishedP = new JPanel();
					finishedP.setLayout(new BoxLayout(finishedP, BoxLayout.Y_AXIS));
					JLabel finishedL = new JLabel("Done");
					JButton finishedB = new JButton("OK");
					JProgressBar loadingPB = new JProgressBar();
					loadingP.setAlignmentX(Component.CENTER_ALIGNMENT);
					loadingL.setAlignmentX(Component.CENTER_ALIGNMENT);
					finishedP.setAlignmentX(Component.CENTER_ALIGNMENT);
					finishedL.setAlignmentX(Component.CENTER_ALIGNMENT);
					finishedB.setAlignmentX(Component.CENTER_ALIGNMENT);
					loadingPB.setAlignmentX(Component.CENTER_ALIGNMENT);
					loadingL.setBorder(new EmptyBorder(15,15,15,15));
					finishedL.setBorder(new EmptyBorder(15,15,15,15));
					loadingPB.setMinimum(0);
					loadingPB.setMaximum(100);
					loadingPB.setStringPainted(true);
					loadingPB.setBorder(new EmptyBorder(0,15,15,15));
					loadingPB.setAlignmentX(Component.CENTER_ALIGNMENT);
					loadingPB.setValue(0);
					loadingP.add(loadingL);
					loadingP.add(loadingPB);
					loading.add(loadingP);
					loading.pack();
					loading.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					loading.setLocationRelativeTo(null);
					loading.revalidate();
					loading.repaint();
					loading.setVisible(true);
					finishedP.add(finishedL);
					finishedP.add(finishedB);

					iteration=0;

					//run calculations in swing worker to prevent blocking the GUI thread, therefore allowing progress bar to update as needed
					SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {

						@Override
						protected Void doInBackground() throws Exception {
							Scanner scanner = new Scanner(uploadedFile);
							scanner.useDelimiter("\\n");  //can use either comma or new line as delimiter

							//count how many words are present in file for progress bar
							int count = 0;
							int maxSize=0;
							while(scanner.hasNext()) {
								count++;
								String currentWord = scanner.next();
								if (currentWord.length()>maxSize) {
									maxSize=currentWord.length();
								}
								//scanner.next();
							}
							scanner.close();
							//re-read file to start at beginning again
							scanner = new Scanner(uploadedFile);
							scanner.useDelimiter("\\n");

							while (scanner.hasNext()){
								iteration++;
								if (iteration%2==0) { 
									String set = scanner.next();
									set = set.replaceAll("[^a-zA-Z0-9]", "");  //remove special characters
									set = set.toLowerCase(); 
									pset = new int[set.length()];
									ParikhMatrixCalculator pM = new ParikhMatrixCalculator();
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
									new PAmiableWordsUploadInterface(word,enteredAlphabet, path, 0, pset);
								}
								if (iteration%2==1) {
									setProgress((int) Math.floor(100*((double)iteration/(double)count)));
									String enteredWord = scanner.next();
									word = new int[enteredWord.length()];
									ParikhMatrixCalculator pM = new ParikhMatrixCalculator();
									//convert next word in file into int[] for handling
									if (!isNumber(enteredWord)) {
										for (int i=0;i<enteredWord.length();i++) {
											String character = Character.toString(enteredWord.charAt(i));
											int l = pM.getNumberForChar(character)-1;
											if (l>=0){
												word[i] = l;
											}
										}
									}
									else {
										for (int i=0;i<enteredWord.length();i++) {
											int l = Character.getNumericValue(enteredWord.charAt(i));
											word[i] = l;
										}
									}
									//calculate these every iteration to allow different alphabet sizes
									int maxLetter = max(word);
									enteredAlphabet = maxLetter+1;

									//calculate Parikh matrix of word and create file that contains it

								}
							}
							setProgress(100);
							scanner.close();
							return null;
						}
					};

					//update progress bar - when 100% is reached (only happens at end of while loop due to "floor", change display to "Done" display
					mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {
						@Override
						public void propertyChange(PropertyChangeEvent e){
							if ("progress".equals(e.getPropertyName())) {
								loadingPB.setValue((Integer) e.getNewValue());
								if ((Integer)e.getNewValue()==100) {
									loading.remove(loadingP);
									loading.add(finishedP);
									loading.revalidate();
									loading.repaint();
								}
							}
						}
					});

					mySwingWorker.execute();

					//set "OK" button to dispose JFrame when clicked
					finishedB.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							loading.dispose();
						}
					});
				}

			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

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

	public boolean disableTextField(Container container) {
		Component[] comps = container.getComponents();
		for (Component comp : comps) {
			if (comp instanceof JTextField) {
				((JTextField) comp).setEnabled(false);
				return true;
			}
			if (comp instanceof Container) {
				if (disableTextField((Container) comp)) return true;
			}
		}
		return false;
	}

}
