import javax.swing.JFrame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

	private static int 		latticeSize 		= 40;
	private static double 	angerFishPercentage	= 0.03;
	private static double 	foodPercentage 		= 0.4;
	private static int 		preyPopulationSize	= 100;
	private static int		sleepInterval		= 50;
	
	public static void main(String[] args) {
		openWindow();
		System.out.println("Done!");
	}
	
	private static void openWindow() {
		JFrame f = new JFrame("Angler fish simulation");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Lattice lattice = new Lattice(latticeSize, angerFishPercentage, foodPercentage, preyPopulationSize);
        f.add(lattice);
        f.setSize(800,800);
        f.setVisible(true);	
        try {
			File file = new File("testFile.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);			
			
			int nIterations = 2000;
			while(nIterations-- > 0)
			{
				bw.write(String.valueOf(lattice.getAverageCaution()));
				bw.newLine();
				lattice.update();
				try {
					Thread.sleep(sleepInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			bw.close();
        } catch (IOException e) {
			e.printStackTrace();
		}
        
	}

}
