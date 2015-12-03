import javax.swing.JFrame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	private static int 		latticeSize 		= 40;
	private static double 	angerFishPercentage	= 0.001;
	private static double 	foodPercentage 		= 0.4;
	private static int 		preyPopulationSize	= 250;
	private static int		sleepInterval		= 50;
	private static int 		nIterations 		= 500;
	
	private static boolean	enableVisualisation = false;
	
	public static void main(String[] args) {
		if (enableVisualisation)
			openWindow();
		else
			collectData();
		System.out.println("Done!");
	}
	
	private static void collectData() {
		ArrayList<Lattice> latticeList = new ArrayList<Lattice>();
		double[] afPercentage = {0.001, 0.005, 0.01, 0.02, 0.03, 0.033, 0.035, 0.04, 0.05};
		for (int i = 0; i < afPercentage.length; i++) {
	        Lattice lattice = new Lattice(latticeSize, afPercentage[i], foodPercentage, preyPopulationSize, enableVisualisation);
	        try {
				File file = new File("testFileShort" + i + ".txt");
				System.out.println("Writing to file " + i);
	
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}
	
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);			
				bw.write("Lattice size:" + latticeSize + "\nAnglerfish percentage:" + afPercentage[i]);
				for (int j = 0; j < nIterations; j++)
				{
					bw.write(String.valueOf(lattice.getAverageCaution()));
					bw.newLine();
					lattice.update();
				}
				bw.close();
	        } catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	private static void openWindow() {
		
		JFrame f = new JFrame("Angler fish simulation");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Lattice lattice = new Lattice(latticeSize, angerFishPercentage, foodPercentage, preyPopulationSize, enableVisualisation);
        
        
        f.add(lattice);
        f.setSize(800,800);
        f.setVisible(enableVisualisation);	
        try {
			File file = new File("testFile.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);			
			
			int nIterations = 5000;
			while(nIterations-- > 0)
			{
				bw.write(String.valueOf(lattice.getAverageCaution()));
				bw.newLine();
				lattice.update();
				if (enableVisualisation) {
					try {
						Thread.sleep(sleepInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			bw.close();
        } catch (IOException e) {
			e.printStackTrace();
		}
        
	}

}
