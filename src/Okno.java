import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Okno extends JFrame implements ActionListener{

	public Okno() {
		
		super("Literaki");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setSize(800, 823);
		JButton przycisk = new JButton("klik");
		add(przycisk);
		przycisk.addActionListener(this);

	}
	public void actionPerformed(ActionEvent e) {

		Rejestrator.stop=1;
		Sprawdzator.dobrych=0;
		Sprawdzator.jakichkolwiek=0;
		try {
			Rejestrator.rejestrator=new Rejestrator();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
