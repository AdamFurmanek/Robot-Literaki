import java.util.ArrayList;

class Wariator
{
	public ArrayList<ArrayList<char[]>> twory;
	
	private int k;
    private int n;
 
    public char[] arr;
    public char[] free;
 

    public Wariator(ArrayList<String> dok) {

    	n=dok.size();

    	k=n;
    	arr	= new char[n];
    	free = new char[n];
    	for(int i=0;i<dok.size();i++) 
    		free[i] = dok.get(i).charAt(1);
    	
    	twory = new ArrayList<ArrayList<char[]>>();
    	for(int i=0;i<7;i++)
    		twory.add(i, new ArrayList<char[]>());
    	while(k>0) {
    		wariuj(0);
    		k--;
    	}
    	
    	for(int i=0;i<twory.size();i++) {
    		for(int j=0;j<twory.get(i).size();j++){
    			for(int k=0;k<=i;k++) {
    				System.out.print(twory.get(i).get(j)[k]);
    			}
    			System.out.print("\n");
    		}
    	}
    }
 
    void wariuj(int index)
    {
        if (index >= k)
        {
        	twory.get(k-1).add(arr.clone());
            PrintVariations();
        }
        else
        {
            for (int i = index; i < n; i++)
            {
                arr[index] = free[i];
                
                char old = free[i];
            	free[i]=free[index];
            	free[index]=old;
            	
            	wariuj(index + 1);
            	
                old = free[i];
            	free[i]=free[index];
            	free[index]=old;
            }
        }
    }
 
    void PrintVariations()
    {
        for(int i=0;i<k;i++) {
        	System.out.print(arr[i]+" ");
        }
        System.out.print("\n");
    }
}