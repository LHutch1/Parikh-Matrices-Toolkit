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

public class AmiableWordsUploadInterface implements ActionListener{

	public JFrame frame;
	public JFileChooser chooser;
	public JFileChooser chooserSave;
	public int iteration;

	public AmiableWordsUploadInterface(int[] word, int alphabet, String savePath, int it) {
		try {
			ParikhMatrixCalculator pM = new ParikhMatrixCalculator();

			String convertinputWord = new String("");
			for (int j=0;j<word.length;j++) {
				convertinputWord = convertinputWord + pM.getCharForNumber(word[j]);
			}

			//find Parikh matrix of given word
			int[][] parikhMatrix = pM.ParikhMatrix(word,alphabet+1);

			//now find all permutations of firstWord
			PermuteArrayWithDuplicates perm = new PermuteArrayWithDuplicates();
			List<List<Integer>> permute = perm.permute(word);

			//remove any words with incorrect matrix
			int numWords = permute.size();
			int[][] results = new int[numWords][word.length];
			int counter = 0;
			int currentValue=0;
			for (int i=0;i<numWords;i++) {
				currentValue =+ currentValue;
				List<Integer> cW = permute.get(i);
				int[] currentWord = cW.stream().mapToInt(j->j).toArray(); 
				int[][] currentParikhMatrix = pM.ParikhMatrix(currentWord,alphabet+1);
				if (Arrays.deepEquals(parikhMatrix,currentParikhMatrix)) {
					results[counter] = currentWord;
					counter = counter+1;
				}
			}

			//Open save dialog and create file
			String defaultTitle = "Words Amiable With " + convertinputWord + ".txt";
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

				//convert list of arrays into words that use letters
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
				add_line.printf("Words described by this Parikh matrix: %n");
				add_line.printf("%n");
				for (int i=0;i<counter;i++) {
					String convertWord = new String("");
					for (int j=0;j<word.length;j++) {
						convertWord = convertWord + pM.getCharForNumber(results[i][j]);
					}
					add_line.printf("%s" + "%n", convertWord);
				}
				add_line.close();
			}
		}
		catch (Exception e){
			e.printStackTrace();
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
				chooserSave.setSelectedFile(new File("Words Amiable With...")); 
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
								new AmiableWordsUploadInterface(word,enteredAlphabet, path, 0);

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