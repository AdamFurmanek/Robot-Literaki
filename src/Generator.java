import java.awt.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Generator {

	private Rejestrator rejestrator;
	private int[][] uklad = new int[15][15];
	private String literyDok = new String("");
	
	protected Generator(Rejestrator rejestrator)throws Exception {
		this.rejestrator=rejestrator;
		
		for(int i=0;i<rejestrator.ileDok;i++)
			literyDok+=rejestrator.dok[i].charAt(1);
		
		for(int i=0;i<15;i++) {
			//lecimy po kolumnach/czy tam wierszach
			for(int j=0;j<15;j++) {
				//lecimy po tym drugim
				for(int k=0;k<rejestrator.ileDok;k++) {
					//zwiekszamy ilosc liter
					int l1=0,l2=0;                 //l1 - trzyma liczbe liter do postawienie     l2 - trzyma miejsce w ktorym ma byc polozona
					for(int m=0;m<15;m++)
						for(int n=0;n<15;n++)
							uklad[m][n]=0;
					
					while(l1!=k+1) {
						if(j+l2>=15)
							break;
						while(rejestrator.plansza[i][j+l2].charAt(0)!='p') {
							l2++;
							if(j+l2>=15)
								break;
						}
						if(j+l2>=15)
							break;
						uklad[i][j+l2]=1;
						l1++;
						l2++;
					}
					
					if(sprawdzPrzystawanie()) {

					generujWyrazy(l1);
					}
					
					if(j+l2>=15)
						break;
					
				}
			}
		}
		//System.out.println(sprawdzKomorke(7, 7));
		
	}
	
	private void generujWyrazy(int ileLiter) throws Exception{
    	Permutator p = new Permutator();
        p.permutation(literyDok,ileLiter, uklad, rejestrator);

	}
	
	private boolean sprawdzPrzystawanie() {
		for(int i=0;i<15;i++)
			for(int j=0;j<15;j++) {
				if(uklad[i][j]==1) {
					if(i>=1)
						if(rejestrator.plansza[i-1][j].charAt(0)!='p')
							return true;
					if(j>=1)
						if(rejestrator.plansza[i][j-1].charAt(0)!='p')
							return true;
					if(i<14)
						if(rejestrator.plansza[i+1][j].charAt(0)!='p')
							return true;
					if(j<14)
						if(rejestrator.plansza[i][j+1].charAt(0)!='p')
							return true;
				}
			}
		return false;
	}
	
}
