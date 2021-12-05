import java.util.Arrays;

public class Projection {
	
	public int[] InverseProjection(int[] word, int[] set) {
		int[] mapped = new int[word.length];
		int[][] map = new int[2][set.length];
		Arrays.sort(set); //sort the set to ensure accurate mapping
		//create the mapping from a normal alphabet to the entered set
		for (int i=0;i<set.length;i++) {
			map[0][i]=i;  //fill first line with conventional alphabet
			map[1][i]=set[i];   //fill second line with projection set
		}
		for (int i=0;i<word.length;i++) {
			for (int j=0;j<set.length;j++) {
				if (word[i]==map[0][j]) {
					mapped[i]=map[1][j];
				}
			}
		}
		return mapped;
	}
	
	public int[] ProjectionCalculator(int[] word, int[] set, int bigalphabet) {
		int[] mid = new int[word.length];	
		Arrays.fill(mid, -1);
		int count=0;
		
		//remove letters not kept in projection
		for (int i=0;i<set.length;i++) {
			for (int j=0;j<word.length;j++) {
				if (set[i]==word[j]) {
					mid[j]=word[j];
					count = count+1;
				}
			}
		}
		
		//remove -1 from original array
		int keep = count;
		int count2=0;
		int[] mid2 = new int[keep];
		for (int i=0;i<word.length;i++) {
			if (!(mid[i]==-1)) {
				mid2[count2]=mid[i];
				count2=count2+1;
			}
		}
		
		//convert letters to new alphabet
		int[] fin = new int[mid2.length];
		int[][] map = new int[2][set.length]; //map is a map from the input set to the new alphabet
		for (int i=0;i<set.length;i++) {
			map[0][i]=set[i];
			map[1][i]=i;
		}

		for (int i=0;i<mid2.length;i++) {
			for (int j=0;j<set.length;j++) {
				if (mid2[i]==set[j]) {
					fin[i]=map[1][j];
				}
			}
		}
		
		return fin;
	}
}
