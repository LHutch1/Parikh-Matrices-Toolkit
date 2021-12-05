
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
public class PermuteArrayWithDuplicates {
	
	public List<List<Integer>> permute(int[] arr) {
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