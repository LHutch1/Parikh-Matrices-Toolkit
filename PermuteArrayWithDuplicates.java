import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class PermuteArrayWithDuplicates {

	//public static List<List<Integer>> resultlist;
	
	public List<List<Integer>> permute(int[] arr){
		
		List<List<Integer>> list = new ArrayList<>();
		int max = arr[0]; 
		for(int i=0;i < arr.length;i++){ 
			if(arr[i] > max){ 
				max = arr[i]; 
			} 
		}
		list=algorithm(list, new ArrayList<>(), arr,new boolean[arr.length], max);
		
		
		return list;
	}

	private List<List<Integer>> algorithm(List<List<Integer>> list, List<List<Integer>> resultlist, int [] arr, boolean [] used, int max) {
		
		//resultlist=resultlist1;
		ParikhMatrixCalculator pmc = new ParikhMatrixCalculator();
		int[][] pm = pmc.ParikhMatrix(arr,max+2);
		int[] subgoal;
		int[] substart;
		List<Integer> first = new ArrayList<>();

		substart = new int[max];
		subgoal = new int[max];

		//counting ab, bc, cd... subwords in goal word
		for (int i=0;i<max;i++) {
			subgoal[i]=pm[i][i+2];
		}

		Arrays.sort(arr);

		if (Home.mySwingWorker.isCancelled()) {
			return null;
		}
		
		//counting ab, bc, cd... subwords in start word
		for (int k=0;k<max;k++) {	
			for (int i=0;i<arr.length-1;i++) {
				if (arr[i]==k) {
					for (int j=i+1;j<arr.length;j++) {
						if (arr[j]==k+1) {
							substart[k]=substart[k]+1;
						}
					}
				}
			}
		}
		//add 1 to start of word in list if correct number of subwords, else add 0
		//add subword count to start of first word and add this to final list
		//each element in results list will now have the structure
		//[{1 if subwords match, else 0},{number of word in tree},{number of subwords in word},{word}]
		if (Arrays.equals(subgoal,substart)) {
			first.add(1);
		}
		else {
			first.add(0);
		}
		int count = 1; //count number of words in tree
		first.add(count);
		for (int i=0;i<substart.length;i++) {
			first.add(substart[i]);
		}

		for (int i=0;i<arr.length;i++) {
			first.add(arr[i]);
		}
		resultlist.add(new ArrayList<>(first));
		//empty first to create next word to add to list
		first.clear();

		//newWord will hold the new word that is created by switching two letters in the previous word
		int[] newWord=Arrays.copyOf(arr, arr.length);
		//newSub will hold the new number of each subword after the swap has occurred
		int[] newSub=Arrays.copyOf(substart, substart.length);
		//minWord holds the number of the left most node on current level, and max holds the right most
		int minWord=1;
		int maxWord=1;
		
		if (Home.mySwingWorker.isCancelled()) {
			return null;
		}
		int loop = 0;
		while (maxWord>=minWord && loop==0) {
			if (Home.mySwingWorker.isCancelled()) {
				return null;
			}
			
			Set<String> wordSet = new TreeSet<String>();
			for (int n=minWord;n<=maxWord;n++) {
				if (Home.mySwingWorker.isCancelled()) {
					return null;
				}
				List<Integer> getWord = resultlist.get(n-1);  //contains all information for next word
				List<Integer> wordL = new ArrayList<>(); //contains just the next word
				List<Integer> newSubL = new ArrayList<>();   //contains just the subword counts for the next word
				
				
				//convert to arrays
				for (int i=0;i<arr.length;i++) {
					wordL.add(getWord.get(i+2+substart.length));
				}
				int[] word = wordL.stream().mapToInt(j->j).toArray(); 
				for (int i=0;i<substart.length;i++) {
					newSubL.add(getWord.get(i+2));
				}
				int[] subs = newSubL.stream().mapToInt(j->j).toArray(); 

				//now check first word and search for all 2 length factors where first letter is smaller than next letter, create new word for each where one is switched
				for (int i=0;i<word.length-1;i++) {
					if (Home.mySwingWorker.isCancelled()) {
						return null;
					}
					if (word[i]<word[i+1]) {
						first.clear();
						newWord=Arrays.copyOf(word, word.length);
						newSub=Arrays.copyOf(subs, subs.length);

						//swap letters to make new word
						newWord[i]=word[i+1];
						newWord[i+1]=word[i];

						if (word[i]+1==word[i+1]) {
							newSub[word[i]]=newSub[word[i]]-1;
						}

						//check if any of the subword counts in the current word are less than the goal number of subwords
						int cont = 1; //1 means continue
						for (int a=0;a<subs.length;a++) {
							if (newSub[a]<subgoal[a]) {
								cont=0;
							}
						}
						
						String wordString=Arrays.toString(newWord);
						if (wordSet.contains(wordString)){
							cont=0;
						}
						else {
							cont=1;
							wordSet.add(wordString);
						}
						
						//if current subs are less than goal, skip the word
						if (cont==1) {
							//add 0 or 1 if newWord has correct number of subwords
							if (Arrays.equals(newSub,subgoal)) {
								first.add(1);
							}
							else {
								first.add(0);
							}
							//now add count for which number word this is in the tree
							count=count+1;
							first.add(count);

							//addnewSub to first list
							for (int j=0;j<newSub.length;j++) {
								first.add(newSub[j]);
							}

							//add word to first list
							for (int j=0;j<word.length;j++) {
								first.add(newWord[j]);
							}
							resultlist.add(new ArrayList<>(first));
							
						}
					}

				}
			}
			minWord=maxWord+1;
			maxWord=count;
		}

		//finally add words to list that have correct number of subwords
		List<List<Integer>> duplist = new ArrayList<>();
		for (int i=0;i<count;i++) {
			List<Integer> getWord = resultlist.get(i);  //contains all information of word
			List<Integer> wordL = new ArrayList<>(); //contains just the word
			if (getWord.get(0)==1) {
				for (int j=0;j<arr.length;j++) {
					wordL.add(getWord.get(j+2+substart.length));
				}
				duplist.add(new ArrayList<>(wordL));
			}
		}

		list = new ArrayList<>(new HashSet<>(duplist));
		//return solutions in alphabetical order
		list = list.stream().sorted((o1,o2) -> {
			for (int i = 0; i < Math.min(o1.size(), o2.size()); i++) {
				int c = o1.get(i).compareTo(o2.get(i));
				if (c != 0) {
					return c;
				}
			}
			return Integer.compare(o1.size(), o2.size());
		}).collect(Collectors.toList());
		
		duplist=null;
		resultlist=null;
		first=null;
		return list;
	}
	
	public List<List<Integer>> permuteOld(int[] arr) {
		List<List<Integer>> list = new ArrayList<>();
		Arrays.sort(arr);
		permuteHelper(list, new ArrayList<>(), arr,new boolean[arr.length]);
		return list;
	}
	
	public long factorial(int n) {
	    if (n <= 2) {
	        return n;
	    }
	    return n * factorial(n - 1);
	}
	
	private void permuteHelper(List<List<Integer>> list, List<Integer> resultList, int [] arr, boolean [] used){
		
		// Base case
		if(resultList.size() == arr.length){
	        list.add(new ArrayList<>(resultList));
	    } else{
	        for(int i = 0; i < arr.length; i++){
	        	if (Home.mySwingWorker.isCancelled()) {
					return;
				}
	            if(used[i] || i > 0 && arr[i] == arr[i-1] && !used[i - 1]) 
	            {   
	            	    // If element is already used
	            		continue;
	            }
	            // choose element
	            used[i] = true; 
	            resultList.add(arr[i]);
	            
	            // Explore
	            permuteHelper(list, resultList, arr, used);
	            
	            // Unchoose element
	            used[i] = false; 
	            resultList.remove(resultList.size() - 1);
	        }
	    }
	}
}
