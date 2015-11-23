import javax.swing.JFrame;

public class Main {

	private static int latticeSize 			= 50;
	private static double angerFishPercentage 	= 0.02;
	private static double foodPercentage 		= 0.6;
	private static int preyPopulationSize		= 30;
	
	public static void main(String[] args) {
		openWindow();
	}
	
	private static void openWindow() {
		JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        f.add(new Lattice(latticeSize));
        f.setSize(250,250);
        f.setVisible(true);	
	}

}
