import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PParikhMatrixUploadInterface extends JPanel {

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	public static JFileChooser chooser;
	public static JFileChooser chooserSave;
	public static int iteration;
	public static int[] pset;
	public static int[] word;
	public static int enteredAlphabet;
	public static Scanner scanner;

	public PParikhMatrixUploadInterface(int[] word, int alphabet, String savePath, int it, int[] pset) {
		try { 
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

			String defaultTitle = "P-Parikh Matrix Of " + convertinputWord + ".txt";
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

				String convertWord = new String("");
				for (int j=0;j<word.length;j++) {
					convertWord = convertWord + pM.getCharForNumber(word[j]);
				}

				Projection p = new Projection();
				int[] pWord = p.ProjectionCalculator(word,setP,alphabet);
				int[][] pparikhMatrix = pM.ParikhMatrix(pWord,setP.length+1);

				String pconvertWord = new String("");
				int[] mappedWord = p.InverseProjection(pWord,setP); //map back to original set
				for (int j=0;j<pWord.length;j++) {
					pconvertWord = pconvertWord + pM.getCharForNumber(mappedWord[j]);
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
			}
			if (!scanner.hasNext()) {
				Home.dialog.dispose();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			Home.dialog.dispose();
		}
	}

	public static void actioned() {
		try {
			File uploadedFile = Home.uploadedFile;
			Home.dialog.setVisible(false);
			chooserSave = new JFileChooser();
			//Create file chooser for user to select a location to save the output files to (default to same location as uploaded file)
			chooserSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			disableTextField(chooserSave);  //prevent user from altering file name as these are auto generated
			chooserSave.setDialogTitle("Select Location To Save Output Files");
			chooserSave.setSelectedFile(new File("P-Parikh Matrix Of...")); 
			chooserSave.setCurrentDirectory(uploadedFile);
			int returnVal = chooserSave.showSaveDialog(frame);
			String path = chooserSave.getCurrentDirectory().getAbsolutePath();

			//if user selects a location
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				iteration=0;
				scanner = new Scanner(uploadedFile);
				scanner.useDelimiter("\\n");
				while (scanner.hasNext()){
					if (Home.mySwingWorker.isCancelled()) {
						return;
					}
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
						new PParikhMatrixUploadInterface(word,enteredAlphabet, path, 0, pset);
					}
					if (iteration%2==1) {
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
				scanner.close();
				Home.dialog.dispose();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			Home.dialog.dispose();
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

	public static boolean disableTextField(Container container) {
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
