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

public class LAmiableWordsUploadInterface {

	public static JFrame frame;
	public static JFileChooser chooser;
	public static JFileChooser chooserSave;
	public static int iteration;
	public static Scanner scanner;

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
					if (lamiablecounter==1) {
						add_line.printf("There is %d word that shares a Parikh matrix and L-Parikh matrix with your word %s: %n", lamiablecounter,convertinputWord);
					} else {
						add_line.printf("There are %d words that share a Parikh matrix and L-Parikh matrix with your word %s: %n", lamiablecounter,convertinputWord);
					}
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
					int distinctCount = counter - lamiablecounter;
					if (distinctCount==1){
						add_line.printf("There is %d word that shares a Parikh matrix with your word %s but does not share an L-Parikh matrix with it:"+"%n", distinctCount,convertinputWord);
					}
					else {
						add_line.printf("There are %d words that share a Parikh matrix with your word %s but do not share an L-Parikh matrix with it:"+"%n", distinctCount,convertinputWord);
					}
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
			if (!scanner.hasNext()) {
				Home.dialog.dispose();
			}
		}
		catch (Exception e1){
			e1.printStackTrace();
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
			chooserSave.setSelectedFile(new File("Words L-Amiable With...")); 
			chooserSave.setCurrentDirectory(uploadedFile);
			int returnVal = chooserSave.showSaveDialog(frame);
			String path = chooserSave.getCurrentDirectory().getAbsolutePath();

			//if user selects a location
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				scanner = new Scanner(uploadedFile);
				scanner.useDelimiter(",|\\n");
				while (scanner.hasNext()){
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
					new LAmiableWordsUploadInterface(word,enteredAlphabet, path, 0);
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
