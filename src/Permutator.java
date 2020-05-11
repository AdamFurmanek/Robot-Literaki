import java.util.Arrays;

public class Permutator {
	
	private Rejestrator rejestrator;
	private String[][] ukladLiter = new String[15][15];
	private int[][] uklad;
	private Sprawdzator sprawdzator = new Sprawdzator();
	
    public void permutation(String s, int k, int[][] uklad, Rejestrator rejestrator) throws Exception {
    	this.rejestrator=rejestrator;
    	this.uklad=uklad.clone();
        char[] original = s.toCharArray();
        Arrays.sort(original);
        char[] clone = new char[k];
        boolean[] mark = new boolean[k];
        Arrays.fill(mark, false);
        permute(original, clone, mark, 0, k);
    }

    private void permute(char[] original, char[] clone, boolean[] mark, int length, int n) throws Exception {
        if (length == n) {
            System.out.println(clone);
			for (int x = 0; x < 15; x++)
				for (int y = 0; y < 15; y++)
					ukladLiter[x][y]=rejestrator.plansza[x][y];
            
            int k=0;
            for(int i=0;i<15;i++) {
            	for(int j=0;j<15;j++) {
            		if (uklad[i][j]==1) {
            			ukladLiter[i][j]="a"+clone[k];
            			k++;
            		}
            	}
            }

            sprawdzator.sprawdz(ukladLiter,uklad);
            
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