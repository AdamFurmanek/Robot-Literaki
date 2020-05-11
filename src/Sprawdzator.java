import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Sprawdzator {

	private String[][] ukladLiter = new String[15][15];
	private int[][] uklad = new int[15][15];
	
	public void sprawdz(String[][] ukladLiter, int[][] uklad) throws Exception {
		this.uklad=uklad.clone();
		this.ukladLiter=ukladLiter.clone();
		for(int i=0;i<15;i++)
			for(int j=0;j<15;j++) {
				if(uklad[i][j]==1) {
					if(sprawdzKomorke(i,j)) {
						for (int x = 0; x < 15; x++) {
						for (int y = 0; y < 15; y++) {
								System.out.print(ukladLiter[x][y].charAt(1)+" ");
						}
						System.out.println();
					}
					System.out.println("\n\n");
					}
				}
			}
	}
	
private boolean sprawdzKomorke(int i, int j) throws Exception {
		
		String napis, napis2;
		napis=ukladLiter[i][j].charAt(1)+"";
		int k=i;
		while(k>0&&ukladLiter[k-1][j].charAt(0)!='p') {
			k--;
			napis=ukladLiter[k][j].charAt(1)+napis;
		}
		k=i;
		while(k<14&&ukladLiter[k+1][j].charAt(0)!='p') {
			k++;
			napis+=ukladLiter[k][j].charAt(1);
		}
		
		napis2=ukladLiter[i][j].charAt(1)+"";
		k=j;
		while(k>0&&ukladLiter[i][k-1].charAt(0)!='p') {
			k--;
			napis2=ukladLiter[i][k].charAt(1)+napis2;
		}
		k=j;
		while(k<14&&ukladLiter[i][k+1].charAt(0)!='p') {
			k++;
			napis2+=ukladLiter[i][k].charAt(1);
		}
		System.out.println("\n\n"+napis2);
		System.out.println(napis);
		if(sprawdzWyraz(napis)&&sprawdzWyraz(napis2))
			return true;
		else
			return false;
	}
	
	private boolean sprawdzWyraz(String string) throws Exception {
		
		if(string.length()==1)
			return true;
		
		Charset ch = Charset.forName("UTF-8");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("Slownik\\slownik.txt"),ch));
		while(reader.ready()) {
			if(string.compareTo(reader.readLine())==0) {
				reader.close();
				return true;
			}
		}
		reader.close();
		return false;
		
	}
}
