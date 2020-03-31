package brickbreak;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame j = new JFrame();
		Gameplay g = new Gameplay();
		j.setBounds(10, 10, 700, 600);
		j.setTitle("Breakout Ball");
		j.setResizable(false);
		j.setVisible(true);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.add(g);
	}

}
