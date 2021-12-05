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

public class LAmiableWordsUploadInterface implements ActionListener{

	public JFrame frame;
	public JFileChooser chooser;
	public JFileChooser chooserSave;
	public int iteration;

	public LAmiableWordsUploadInterface(int[] word, int alphabet, String savePath, int it) {
		try { //checking if alphabet input is valid
			ParikhMatrixCalculator pM = new ParikhMatrixCalculator();
			String convertinputWord = new String("");
			for (int j=0;j<word.length;j++) {
				convertinputWord = convertinputWord + pM.getCharForNumber(word[j]);
			}

			//Open save dialog and create file
			String defaultTitle = "Words L-Amiable With " + convertinputWord + ".txt";
			chooserSave = new JFileChooser();
			if (it==1) {
				chooserSave.setSelectedFile(new File("Amiable Words.txt"));
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

				String start2 = new String("The Lyndon conjugate of your word ");
				add_line.printf("%s", start2);
				add_line.printf("%s"+":"+"%n", convertinputWord);

				LyndonConjugate lC = new LyndonConjugate();
				int[] lword = lC.lyndonConj(finalword);
				int[][] lparikhMatrix = pM.ParikhMatrix(lword,alphabet+1);

				String convertlinputWord = new String("");
				for (int j=0;j<finalword.length;j++) {
					convertlinputWord = convertlinputWord + pM.getCharForNumber(lword[j]);
				}
				add_line.printf("%s", convertlinputWord);
				add_line.printf("%n");
				add_line.printf("%n");

				String start3 = new String("The L-Parikh matrix of your word ");
				add_line.printf("%s", start3);
				add_line.printf("%s"+":"+"%n", convertinputWord);
				for (int i=0;i<lparikhMatrix.length;i++) {
					for (int j=0;j<lparikhMatrix.length;j++) {
						add_line.printf("%4d", lparikhMatrix[i][j]);
					}
					add_line.printf("%n");
				}
				add_line.printf("%n");

				int lnumWords = permute.size();
				int[][] lresults = new int[lnumWords][lword.length];
				int lcounter = 0;
				ParikhMatrixCalculator lcPM = new ParikhMatrixCalculator();
				for (int i=0;i<lnumWords;i++) {
					List<Integer> lcW = permute.get(i);
					int[] currentWord = lcW.stream().mapToInt(j->j).toArray(); 
					int[] lcurrentWord = lC.lyndonConj(currentWord);
					int[][] lcurrentParikhMatrix = lcPM.ParikhMatrix(lcurrentWord,alphabet+1);
					if (Arrays.deepEquals(lparikhMatrix,lcurrentParikhMatrix)) {
						lresults[lcounter] = lcurrentWord;
						lcounter = lcounter+1;
						matchIndex[i][1]=1;
					}
					else {
						matchIndex[i][1]=0;
					}
				}	

				int amiablecounter = 0;
				int lamiablecounter = 0;
				if (counter>0) {
					for (int i=0;i<numWords;i++) {
						if (matchIndex[i][0]==1) {
							amiablecounter = amiablecounter + 1;
						}
						if (matchIndex[i][0]==1 && matchIndex[i][1]==1) {
							lamiablecounter = lamiablecounter+1;
						}
					}
				}
				if (amiablecounter>0 && lamiablecounter>0 && counter>0) {
					String start4 = new String("Words that share a Parikh matrix and L-Parikh matrix with your word ");
					add_line.printf("%s", start4);
					add_line.printf("%s"+":"+"%n", convertinputWord);
					//remove any words with incorrect matrix
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
							String lconvertWord = new String("");
							List<Integer> cW = permute.get(i);
							int[] currentWord2 = cW.stream().mapToInt(j->j).toArray(); 
							int[] lcurrentWord = lC.lyndonConj(currentWord2);
							for (int j=0;j<finalword.length;j++) {
								convertWord2 = convertWord2 + lcPM.getCharForNumber(currentWord2[j]);
							}
							for (int j=0;j<finalword.length;j++) {
								lconvertWord = lconvertWord + lcPM.getCharForNumber(lcurrentWord[j]);
							}
							add_line.printf("%s" + "%n", convertWord2);
						}
					}
				}
				else {
					add_line.printf("Your word %s is uniquely described by its L-Parikh matrix. %n", convertinputWord);
				}
				add_line.printf("%n");
				if (!(amiablecounter==lamiablecounter)) {
					String start5 = new String("Words that share a Parikh matrix with your word ");
					add_line.printf("%s", start5);
					add_line.printf("%s"+" but do not share an L-Parikh matrix with it:"+"%n", convertinputWord);
					for (int i=0;i<numWords;i++) {
						if (matchIndex[i][0]==1 && matchIndex[i][1]==0) {
							String notconvertWord = new String("");
							List<Integer> cW = permute.get(i);
							int[] notcurrentWord = cW.stream().mapToInt(j->j).toArray(); 
							for (int j=0;j<finalword.length;j++) {
								notconvertWord = notconvertWord + pM.getCharForNumber(notcurrentWord[j]);
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
						add_line.printf("The Parikh matrix of your entered word is not L-distinguishable.");
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
				chooserSave.setSelectedFile(new File("Words L-Amiable With...")); 
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
							scanner.useDelimiter(",|\\n");  //can use either comma or new line as delimiter

							//count how many words are present in file for progress bar
							int count = 0;
							while(scanner.hasNext()) {
								count++;
								scanner.next();
							}
							scanner.close();
							//re-read file to start at beginning again
							scanner = new Scanner(uploadedFile);
							scanner.useDelimiter(",|\\n");
							while (scanner.hasNext()){
								iteration++;
								setProgress((int) Math.floor(100*((double)iteration/(double)count)));
								String enteredWord = scanner.next();
								int[] word = new int[enteredWord.length()];
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
								int enteredAlphabet = maxLetter+1;

								//calculate Parikh matrix of word and create file that contains it
								new LAmiableWordsUploadInterface(word,enteredAlphabet, path, 0);

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
