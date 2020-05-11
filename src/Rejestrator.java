import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Rejestrator {

	private char[] literyAlfabetu = { 'a', '¹', 'b', 'c', 'æ', 'd', 'e', 'ê', 'f', 'g', 'h', 'i', 'j', 'k', 'l', '³',
			'm', 'n', 'ñ', 'o', 'ó', 'p', 'r', 's', 'œ', 't', 'u', 'w', 'y', 'z', 'Ÿ', '¿' };
	private char[] literyPol = { 'b', 'c', 'n', 'z', 'y', '2', '3' };

	private BufferedImage[] alfabet = new BufferedImage[32];
	private BufferedImage[] alfabetBialy = new BufferedImage[32];
	private BufferedImage[] alfabetDoku = new BufferedImage[33];
	private BufferedImage[] pola = new BufferedImage[7];

	protected String[][] plansza = new String[15][15];
	protected String[] dok = new String[7];
	protected int ileDok;
	
	public static void main(String[] args) throws Exception {
		new Rejestrator();
	}
	
	private Rejestrator() throws Exception {
		inicjuj();
		BufferedImage screenShot = new Robot()
				.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
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
		//////////////CHWILOWO//////////////
		dok[0]="ap";
		dok[1]="ai";
		dok[2]="ae";
		dok[3]="as";
		dok[4]="aa";
		//////////////^^^^^^^^//////////////
		for (int i = 0; i < 7; i++) {
			if(dok[i].compareTo("xx")!=0)
				ileDok++;
			System.out.print(dok[i] + " ");
		}

		
		//////////////CHWILOWO//////////////
		for (int i = 0; i < 15; i++)
			for (int j = 0; j < 15; j++)
				plansza[i][j]="pb";
		plansza[7][7]="ad";
		plansza[0][0]="ad";
		plansza[14][14]="ad";
		plansza[14][13]="ad";
		System.out.println(ileDok);
		//////////////^^^^^^^^//////////////
		
		
		new Generator(this);
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
				dok[i] = "d" + literyAlfabetu[k];
				return;
			} else {
				dok[i] = "xx";
			}
		}
		if (porownaj(pole, alfabetDoku[32])) {
			dok[i] = "b0";
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
