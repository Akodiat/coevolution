import javax.swing.JFrame;

import org.math.plot.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	private static int latticeSize = 40;
	private static double angerFishPercentage = 0.06;
	private static double foodPercentage = 0.4;
	private static int preyPopulationSize = 250;
	private static int sleepInterval = 50;
	private static int nIterations = 4000;

	private static boolean enableVisualisation = true;
	private static boolean saveAllData = false;

	public static void main(String[] args) {
		if (enableVisualisation)
			openWindow();
		else{
			if (saveAllData)
				collectData();
			else
				collectDataEnd();
		}
			
		System.out.println("Done!");
	}

	private static void collectData() {
		Plot2DPanel avgCautionPlot = new Plot2DPanel();
		Plot2DPanel popSizePlot = new Plot2DPanel();
		double[] afPercentage = { 0.001, 0.005, 0.01, 0.02, 0.03, 0.033, 0.035, 0.04, 0.05 };
		
		
		for (int i = 0; i < afPercentage.length; i++) {
			Lattice lattice = new Lattice(latticeSize, afPercentage[i], foodPercentage, preyPopulationSize,
					enableVisualisation);
			
			double[] iterationValues = new double[nIterations];
			double[] avgCautionValues = new double[nIterations];
			double[] popSizeValues = new double[nIterations];
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
				for (int j = 0; j < nIterations; j++) {
					double averageCaution = lattice.getAverageCaution();
					double popSizeValue = lattice.getPreyPopulationSize();
					iterationValues[j] = j;
					avgCautionValues[j] = Double.isNaN(averageCaution)? 0 : averageCaution;
					popSizeValues[j] = Double.isNaN(popSizeValue)? 0 : popSizeValue;
					
					bw.write(String.valueOf(averageCaution));
					bw.newLine();
					lattice.update();
				}
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			avgCautionPlot.addLinePlot(afPercentage[i]*100 + "% Angler fishes", iterationValues, avgCautionValues);
			popSizePlot.addLinePlot(afPercentage[i]*100 + "% Angler fishes", iterationValues, popSizeValues);
		}
		newPlotWindow(avgCautionPlot,"Average caution plot");
		newPlotWindow(popSizePlot,"Population size plot");
	}
	
	private static void collectDataEnd() {
			//double[] afPercentage = {0.001, 0.005, 0.01, 0.0125, 0.015, 0.0175, 0.02, 0.0225, 0.025, 0.0275, 0.03, 0.033, 0.035, 0.04, 0.05};
			double[] afPercentage = { 0.0275, 0.03, 0.033, 0.035 };
			int iterations = 10;
			try {
				File file = new File("testFileEnd.txt");
				
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				
				for (int i = 0; i < afPercentage.length; i++) {
					double meanAverageCaution = 0;
					
					
					for (int iter = 0; iter < iterations; iter++){
						System.out.println("Running index " + i + " out of " + afPercentage.length + "(" + iter + ")");
						Lattice lattice = new Lattice(latticeSize, afPercentage[i], foodPercentage, preyPopulationSize,
								enableVisualisation);
						double averageCaution = lattice.getAverageCaution();
						for (int j = 0; j < nIterations; j++) {
							lattice.update();
							if(lattice.getAverageCaution() >0){
								averageCaution = lattice.getAverageCaution();
							}
						}
						meanAverageCaution += averageCaution;
					}
					System.out.println(meanAverageCaution + " : " + meanAverageCaution/iterations);
					bw.write(String.valueOf(afPercentage[i]) + " " + String.valueOf(meanAverageCaution/iterations));
					bw.newLine();
				}
				bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void newPlotWindow(Plot2DPanel plot, String desc) {
		JFrame frame = new JFrame(desc);
		frame.setContentPane(plot);
		frame.setVisible(true);

	}

	private static void openWindow() {

		JFrame f = new JFrame("Angler fish simulation");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Lattice lattice = new Lattice(latticeSize, angerFishPercentage, foodPercentage, preyPopulationSize,
				enableVisualisation);

		f.add(lattice);
		f.setSize(800, 800);
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
			while (nIterations-- > 0) {
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
