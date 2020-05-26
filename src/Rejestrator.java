import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Rejestrator {
	
	public static int stop=0;
	
	public static Rejestrator rejestrator;
	public static Wariator wariator;
	public static Generator generator;
	public static Sprawdzator sprawdzator;
	
	protected String[][] plansza = new String[15][15];
	protected ArrayList<String> dok = new ArrayList<String>();
	
	private char[] literyAlfabetu = { 'a', '¹', 'b', 'c', 'æ', 'd', 'e', 'ê', 'f', 'g', 'h', 'i', 'j', 'k', 'l', '³', 'm', 'n', 'ñ', 'o', 'ó', 'p', 'r', 's', 'œ', 't', 'u', 'w', 'y', 'z', 'Ÿ', '¿' };
	private char[] literyPol = { 'b', 'c', 'n', 'z', 'y', '2', '3' };

	private BufferedImage[] alfabet = new BufferedImage[32];
	private BufferedImage[] alfabetBialy = new BufferedImage[32];
	private BufferedImage[] alfabetDoku = new BufferedImage[33];
	private BufferedImage[] pola = new BufferedImage[7];
	
	public static void main(String[] args) throws Exception, Throwable{
		new Okno();
		
		sprawdzator=new Sprawdzator();
		rejestrator=new Rejestrator();
	}
	
	public Rejestrator() throws Exception{
		inicjuj();
		BufferedImage screenShot = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				BufferedImage pole = screenShot.getSubimage(527 + (i * 37), 161 + (j * 37), 37, 37);
				identyfikuj(pole, i, j);
			}
		}
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				System.out.print(plansza[i][j] + " ");
			}
			System.out.println();
		}

		for (int i = 0; i < 7; i++) {
			BufferedImage pole = screenShot.getSubimage(1192 + (i * 37), 305, 37, 37);
			identyfikujDok(pole, i);
		}
		
		wartosciDomyslne();
		
		wariator=new Wariator(dok);
		sprawdzator.plansza=plansza.clone();
		sprawdzator.dok=(ArrayList<String>) dok.clone();
		generator = new Generator(plansza, dok);
		generator.generuj();
		
	}
	
	public void dalej() {
		
	}

	private void wartosciDomyslne() {
		if(dok.size()==0) {
			dok.add("al");
			dok.add("ai");
			dok.add("at");
			dok.add("ae");
			dok.add("ar");
			dok.add("ak");
			dok.add("ai");
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				plansza[i][j]="pb";
		plansza[3][7]="at";
		plansza[4][7]="ae";
		plansza[5][7]="as";
		plansza[6][7]="at";
		plansza[7][7]="ao";
		plansza[8][7]="aw";
		plansza[9][7]="aa";
		plansza[10][7]="aæ";
		}
	}
	
	private void inicjuj() throws Exception {
		for (int i = 0; i < 32; i++) {
			alfabet[i] = ImageIO.read(new File("Alfabet/" + literyAlfabetu[i] + ".png"));
			alfabetBialy[i] = ImageIO.read(new File("AlfabetBialy/" + literyAlfabetu[i] + ".png"));
			alfabetDoku[i] = ImageIO.read(new File("AlfabetDoku/" + literyAlfabetu[i] + ".png"));
		}
		alfabetDoku[32] = ImageIO.read(new File("AlfabetDoku/0.png"));
		
		// 0 - biale 1 - czerwone 2 - niebieskie 3 - zielone 4 - yellow 5 - 2x 6 - 3x
		pola[0] = ImageIO.read(new File("Pola/b.png"));
		pola[1] = ImageIO.read(new File("Pola/c.png"));
		pola[2] = ImageIO.read(new File("Pola/n.png"));
		pola[3] = ImageIO.read(new File("Pola/z.png"));
		pola[4] = ImageIO.read(new File("Pola/y.png"));
		pola[5] = ImageIO.read(new File("Pola/2.png"));
		pola[6] = ImageIO.read(new File("Pola/3.png"));
	}

	private void identyfikuj(BufferedImage pole, int j, int i) {
		for (int k = 0; k < 7; k++) {
			if (porownaj(pole, pola[k])) {
				plansza[i][j] = "p" + literyPol[k];
				return;
			} else {
				plansza[i][j] = "xx";
			}
		}
		for (int k = 0; k < 32; k++) {
			if (porownaj(pole, alfabet[k])) {
				plansza[i][j] = "a" + literyAlfabetu[k];
				return;
			} else {
				plansza[i][j] = "xx";
			}
		}
		for (int k = 0; k < 32; k++) {
			if (porownaj(pole, alfabetBialy[k])) {
				plansza[i][j] = "b" + literyAlfabetu[k];
				return;
			} else {
				plansza[i][j] = "xx";
			}
		}
	}

	private void identyfikujDok(BufferedImage pole, int i) {
		for (int k = 0; k < 32; k++) {
			if (porownaj(pole, alfabetDoku[k])) {
				dok.add("d" + literyAlfabetu[k]);
				return;
			}
		}
		if (porownaj(pole, alfabetDoku[32])) {
			dok.add("d0");
			return;
		}

	}

	private boolean porownaj(BufferedImage obraz1, BufferedImage obraz2) {
		int zlePiksele = 0;
		if (obraz1.getWidth() == obraz2.getWidth() && obraz1.getHeight() == obraz2.getHeight()) {
			for (int x = 0; x < obraz1.getWidth(); x++) {
				for (int y = 0; y < obraz1.getHeight(); y++) {
					if (obraz1.getRGB(x, y) != obraz2.getRGB(x, y)) {
						// System.out.println("z³y piksel: "+x+" "+y);
						zlePiksele++;
					}
					if (zlePiksele > 15)
						return false;
				}
			}
		} else {
			return false;
		}
		// System.out.println("Zle piksele: "+zlePiksele);
		return true;
	}

}
