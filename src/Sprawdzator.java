import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Sprawdzator {

	public String[][] plansza = new String[15][15];
	public ArrayList<String> dok = new ArrayList<String>();
	Trie trie;
	static public int dobrych=0;
	static public int jakichkolwiek=0;
	
	public Sprawdzator() throws Exception{
		trie = new Trie();
		Charset ch = Charset.forName("UTF-8");
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("Slownik\\slownik.txt"),ch));
		while(reader.ready()) {
			trie.insert(reader.readLine());
		}
		reader.close();
		
	}
	
	public void sprawdz(String[][] nowaPlansza, int[][] uklad) throws Exception{
		jakichkolwiek++;
		int ileOK=0, wymaganeOK=0;
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				if(uklad[i][j]==1) {
					wymaganeOK++;
					if(sprawdzKomorke(i,j,nowaPlansza)) {
						ileOK++;
					}
				}
				if(wymaganeOK!=ileOK)
					break;
			}
			if(wymaganeOK!=ileOK)
				break;
		}
		if(ileOK==wymaganeOK) {
			dobrych++;
			System.out.println(jakichkolwiek);
			System.out.println(dobrych);
			for (int x = 0; x < 15; x++) {
				for (int y = 0; y < 15; y++) {
					if(nowaPlansza[x][y].charAt(0)=='a')
						System.out.print(nowaPlansza[x][y].charAt(1)+" ");
					else
						System.out.print(". ");
				}
				System.out.println();
			}
			System.out.println("\n");
		}
	}
	
private boolean sprawdzKomorke(int i, int j, String[][] nowaPlansza) throws Exception {
		
		String napis, napis2;
		napis=nowaPlansza[i][j].charAt(1)+"";
		int k=i;
		while(k>0&&nowaPlansza[k-1][j].charAt(0)!='p') {
			k--;
			napis=nowaPlansza[k][j].charAt(1)+napis;
		}
		k=i;
		while(k<14&&nowaPlansza[k+1][j].charAt(0)!='p') {
			k++;
			napis+=nowaPlansza[k][j].charAt(1);
		}
		
		napis2=nowaPlansza[i][j].charAt(1)+"";
		k=j;
		while(k>0&&nowaPlansza[i][k-1].charAt(0)!='p') {
			k--;
			napis2=nowaPlansza[i][k].charAt(1)+napis2;
		}
		k=j;
		while(k<14&&nowaPlansza[i][k+1].charAt(0)!='p') {
			k++;
			napis2+=nowaPlansza[i][k].charAt(1);
		}
		if(sprawdzWyraz(napis)&&sprawdzWyraz(napis2)) {
			return true;
		}
		else
			return false;
	}
	
	private boolean sprawdzWyraz(String string) throws Exception {
		
		
		if(string.length()==1)
			return true;
		
		if(trie.search(string)) {
			return true;
		}
		return false;
	}
}
