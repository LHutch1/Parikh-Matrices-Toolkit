import java.util.Arrays;

public class LyndonConjugate {
		
	
		public int[] lyndonConj(int[] w) {
			int size = w.length;
			//array to hold all conjugates
			int[][] conjugates = new int[size][size];
			//first conjugate is word itself
			conjugates[0] = w;
			
			//find all conjugates and store them
			for (int i=1;i<size;i++) {
				if (Home.mySwingWorker.isCancelled()) {
					return null;
				}
				conjugates[i] = leftRotatebyOne(conjugates[i-1],size);
			}
			
			//for each conjugate, test all conjugates to see which is lexicographically smaller
			//save the smaller conjugate of the two as the next element in that row
			int[][][] smallest = new int[size][size][size];
			for (int i=0;i<size;i++) {
				for (int j=0; j<size; j++) {
					smallest[i][j]=lexicoSmaller(conjugates[i],conjugates[j]);
				}
			}
			
			if (Home.mySwingWorker.isCancelled()) {
				return null;
			}
			
			//make a matrix of 1s and 0s. Enter a 1 if the lexicographically smaller word is the original word on that row
			int[][] equal = new int[size][size];
			for (int i=0;i<size;i++) {
				for (int j=0; j<size; j++) {
					if (Arrays.equals(smallest[i][j],conjugates[i])) {
						equal[i][j]=1;
					}
					else {
						equal[i][j]=0;
					}
				}
			}

			if (Home.mySwingWorker.isCancelled()) {
				return null;
			}
			
			//the row where all elements in "equal" are 1 is the smallest, as no other conjugate was found to be smaller
			//return that index
			int index = 0;
			for (int i=0;i<size;i++) {
				int allequal=0;
				for (int j=0; j<size; j++) {
					allequal = allequal+equal[i][j];
				}
				if (allequal==size) {
					index=i;
				}
			}
			return conjugates[index];
		}
		
	    public int[] leftRotatebyOne(int[] arr, int n) 
	    { 
	    	if (Home.mySwingWorker.isCancelled()) {
				return null;
			}
	        int i; 
	        int[] j = new int[arr.length];
	        j[n-1]=arr[0];
	        for (i = 0; i < n - 1; i++) {
	            j[i] = arr[i + 1]; 
	            
	        }
	        return j;
	    } 
	  
	    public int[] lexicoSmaller(int[] w1, int[]w2)
	    {
	    	if (Home.mySwingWorker.isCancelled()) {
				return null;
			}
	        int n = w1.length;
	        int m = w2.length;
	        int l = Math.min(n, m);
	        int count = 0;
	        for(int i = 0; i < l; i++)
	        {
	            if(w1[i] < w2[i]) 
	            {
	                return w1;
	            }
	            else if(w1[i] == w2[i]) {
	            	count = count+1;
	            }
	            else if(w1[i] == w2[i] && count == l && n<m){
	            	return w1;
	            }
	            else if(w1[i]>w2[i]) {
	            	return w2;
	            }
	        }
	        return w2; //if words are equal you can return either
	    }
	    
	    
	 
}
