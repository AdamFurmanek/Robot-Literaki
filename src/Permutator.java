import java.util.ArrayList;
import java.util.Arrays;

public class Permutator {
	
	protected String[][] plansza = new String[15][15];
	protected ArrayList<String> dok = new ArrayList<String>();
	private String[][] nowaPlansza = new String[15][15];

	private int[][] uklad;
	
	public Permutator(String[][] plansza, ArrayList<String> dok) throws Exception{
		this.plansza=plansza.clone();
		this.dok=(ArrayList<String>) dok.clone();
	}
	
    public void permutation(String s, int k, int[][] uklad) throws Exception {
    	this.uklad=uklad.clone();
        char[] original = s.toCharArray();
        Arrays.sort(original);
        char[] clone = new char[s.length()];
        boolean[] mark = new boolean[s.length()];
        Arrays.fill(mark, false);
        permute(original, clone, mark, 0, s.length());
    }


    
    private void permute(char[] original, char[] clone, boolean[] mark, int length, int n) throws Exception {
        if (length == n) {
			for (int x = 0; x < 15; x++)
				for (int y = 0; y < 15; y++)
					nowaPlansza[x][y]=plansza[x][y];
			
            int k=0;
            for(int i=0;i<15;i++) {
            	for(int j=0;j<15;j++) {
            		if (uklad[i][j]==1) {
            			nowaPlansza[i][j]="a"+clone[k];
            			k++;
            		}
            	}
            }

            Rejestrator.sprawdzator.sprawdz(plansza,dok,nowaPlansza,uklad);
            
            return;
        }

        for (int i = 0; i < n; i++) {
            if (mark[i] == true) continue;
            // dont use this state. to keep order of duplicate character
            if (i > 0 && original[i] == original[i-1] && mark[i-1] == false) continue;
            mark[i] = true;
            clone[length] = original[i];
            permute(original, clone, mark, length+1, n);
            mark[i] = false;
        }

    }

}