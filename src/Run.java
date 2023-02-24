import javax.swing.JFrame;

public class Run {

	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setUndecorated(true);
		window.setTitle("The first night of Christmas");
		
		window.add(new Game());
		window.pack();
		window.setVisible(true);
	}

}
