import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class AmiableWordsUploadInterface{

	public static JFrame frame;
	public JFileChooser chooser;
	public static JFileChooser chooserSave;
	public static int iteration;
	public static Scanner scanner;

	public AmiableWordsUploadInterface(int[] word, int alphabet, String savePath, int it) {
		Home.mySwingWorker.isCancelled();
		try {
			ParikhMatrixCalculator pM = new ParikhMatrixCalculator();

			String convertinputWord = new String("");
			for (int j=0;j<word.length;j++) {
				convertinputWord = convertinputWord + pM.getCharForNumber(word[j]);
			}

			//find Parikh matrix of given word
			int[][] parikhMatrix = pM.ParikhMatrix(word,alphabet+1);

			if (Home.mySwingWorker.isCancelled()) {
				return;
			}
			//now find all permutations of firstWord
			PermuteArrayWithDuplicates perm = new PermuteArrayWithDuplicates();
			List<List<Integer>> permute = perm.permute(word);

			if (Home.mySwingWorker.isCancelled()) {
				return;
			}
			//remove any words with incorrect matrix
			int numWords = permute.size();
			int[][] results = new int[numWords][word.length];
			int counter = 0;
			int currentValue=0;
			for (int i=0;i<numWords;i++) {
				if (Home.mySwingWorker.isCancelled()) {
					return;
				}
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
			//chooserSave = new JFileChooser();
			//if (it==1) {
			//chooserSave.setSelectedFile(new File("Amiable Words.txt"));
			//}
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
				if (counter==1){
					add_line.printf("There is %d word amiable with your word:",counter);
				}
				else {
					add_line.printf("There are %d words amiable with your word:",counter);
				}
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
			if (!scanner.hasNext()) {
				Home.dialog.dispose();
			}
		}
		catch (Exception e){
			e.printStackTrace();
			Home.dialog.dispose();
		}
	}

	public static void actioned() {
		try {
			File uploadedFile = Home.uploadedFile;
			chooserSave = new JFileChooser();
			chooserSave.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			disableTextField(chooserSave);  //prevent user from altering file name as these are auto generated
			chooserSave.setDialogTitle("Select Location To Save Output Files");
			chooserSave.setSelectedFile(new File("Words Amiable With...")); 
			chooserSave.setCurrentDirectory(uploadedFile);
			int returnVal = chooserSave.showSaveDialog(frame);
			String path = chooserSave.getCurrentDirectory().getAbsolutePath();
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				//@SuppressWarnings("resource")
				scanner = new Scanner(uploadedFile);
				scanner.useDelimiter(",|\\n");
				while (scanner.hasNext()){
					iteration++;
					if (Home.mySwingWorker.isCancelled()) {
						return;
					}
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