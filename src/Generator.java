import java.awt.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Generator {

	public String[][] plansza = new String[15][15];
	public ArrayList<String> dok = new ArrayList<String>();
	private int[][] uklad = new int[15][15];
	
	protected Generator(String[][] plansza, ArrayList<String> dok)throws Exception{
		this.plansza=plansza.clone();
		this.dok=(ArrayList<String>) dok.clone();
	}
	
	public void generuj() throws Exception{
		
		for(int i=0;i<15;i++) {						//wiersz
			for(int j=0;j<15;j++) {					//kolumny
				for(int k=0;k<dok.size();k++) {		//ile liter
					int l1=0,l2=0;					//l1 - trzyma liczbe liter do postawienie     l2 - trzyma miejsce w ktorym ma byc polozona
					for(int m=0;m<15;m++)
						for(int n=0;n<15;n++)
							uklad[m][n]=0;
					
					while(l1!=k+1) {
						if(j+l2>=15)
							break;
						while(plansza[i][j+l2].charAt(0)!='p') {
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
					
					if(sprawdzPrzystawanie()&&l1>5) {
						przeslij(l1);
					}
					
					if(j+l2>=15)
						break;
					if(Rejestrator.stop==1) {
						Rejestrator.stop=0;
						return;
					}
				}
			}
		}
		for(int i=0;i<15;i++) {						//kolumny
			for(int j=0;j<15;j++) {					//wiersz
				for(int k=0;k<dok.size();k++) {		//ile liter
					int l1=0,l2=0;					//l1 - trzyma liczbe liter do postawienia     l2 - trzyma miejsce w ktorym ma byc polozona
					for(int m=0;m<15;m++)
						for(int n=0;n<15;n++)
							uklad[m][n]=0;
					
					while(l1!=k+1) {
						if(j+l2>=15)
							break;
						while(plansza[j+l2][i].charAt(0)!='p') {
							l2++;
							if(j+l2>=15)
								break;
						}
						if(j+l2>=15)
							break;
						uklad[j+l2][i]=1;
						l1++;
						l2++;
					}
					
					if(sprawdzPrzystawanie()&&l1>5) {
						przeslij(l1);
					}
					
					if(j+l2>=15)
						break;
					if(Rejestrator.stop==1) {
						Rejestrator.stop=0;
						return;
					}
				}
			}
		}
	}
	
	private void przeslij(int l1) throws Exception {
		String[][] nowaPlansza = new String[15][15];
		for(int p=0;p<Rejestrator.wariator.twory.get(l1-1).size();p++) {
		int k=0;
	        for(int i=0;i<15;i++) {
	        	for(int j=0;j<15;j++) {
	        		if (uklad[i][j]==1) {
	        			nowaPlansza[i][j]="a"+Rejestrator.wariator.twory.get(l1-1).get(p)[k];
	        			k++;
	        		}
	        		else
	        			nowaPlansza[i][j]=plansza[i][j];
	        	}
	        }

        Rejestrator.sprawdzator.sprawdz(nowaPlansza,uklad);
		}
	}
	
	private boolean sprawdzPrzystawanie() {
		
		for(int i=0;i<15;i++) {
			for(int j=0;j<15;j++) {
				if(uklad[i][j]==1) {
					if(i>=1)
						if(plansza[i-1][j].charAt(0)!='p')
							return true;
					if(j>=1)
						if(plansza[i][j-1].charAt(0)!='p')
							return true;
					if(i<14)
						if(plansza[i+1][j].charAt(0)!='p')
							return true;
					if(j<14)
						if(plansza[i][j+1].charAt(0)!='p')
							return true;
				}
			}
		}
		return false;
	}
	
	public void dispose() throws Throwable{
		this.finalize();
	}
	
}
