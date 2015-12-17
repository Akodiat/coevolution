import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSplitPane;
import javax.swing.SwingWorker;

import org.math.plot.Plot2DPanel;

public class Window extends JFrame {
	private static final long serialVersionUID = -7609923067531544108L;
	private int latticeSize = 32;
	private double angerFishPercentage = 0.015;
	private double foodPercentage = 0.04;
	private int preyPopulationSize = 15;
	private int sleepInterval = 50;
	private int nIterations = 1200;
	
	private double maxMetabolism = 0.012;
	private double minMetabolism = 0.001;

	private Dimension latticeDimension = new Dimension(400, 400);
	private Dimension plotDimension = new Dimension(400, 150);;

	private Lattice lattice;
	private Plot2DPanel plotA;
	private Plot2DPanel plotB;

	private boolean enableVisualisation;
	private boolean saveAllData = false;

	public Window(boolean enableVisualisation) {
		this.enableVisualisation = enableVisualisation;
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		//setUndecorated(true);
		setTitle("Angler fish simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupMenu();

		lattice = new Lattice(
				latticeSize, angerFishPercentage,
				foodPercentage, preyPopulationSize,
				enableVisualisation, 
				maxMetabolism, minMetabolism);
		lattice.setPreferredSize(latticeDimension);

		plotA = new Plot2DPanel();
		plotA.setPreferredSize(plotDimension);
		plotB = new Plot2DPanel();
		plotB.setPreferredSize(plotDimension);
		
		JSplitPane plotSplitPane = new JSplitPane(
				JSplitPane.VERTICAL_SPLIT,
                plotA, plotB);

		plotSplitPane.setResizeWeight(0.5);
		
		JSplitPane splitPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT,
                lattice, plotSplitPane);
		
		add(splitPane);
		
		splitPane.setResizeWeight(0.5);
		setVisible(enableVisualisation);
		
		//ex1();
		ex2();
	}

	public void ex1()
	{
		plotA.setAxisLabel(0, "Timestep t");
		plotA.setAxisLabel(1, "Population size");

		plotB.setAxisLabel(0, "Timestep t");
		plotB.setAxisLabel(1, "Average intelligence");
		double[] preySizeValues = new double[nIterations];
		double[] anglSizeValues = new double[nIterations];
		double[] foodSizeValues = new double[nIterations];
		double[] brainSizeValues = new double[nIterations];
		double[] iterationValues = new double[nIterations];
		
		for (int i = 0; i < nIterations; i++) {
			iterationValues[i] = nIterations;
		}

		plotA.addLinePlot("Prey", iterationValues, preySizeValues);
		plotA.addLinePlot("Angler fish", iterationValues, anglSizeValues);
		plotA.addLinePlot("Food fishes", iterationValues, foodSizeValues);
		
		plotB.addLinePlot("Average brain size", iterationValues, brainSizeValues);
		
		for (int i = 0; i < nIterations; i++) {
			lattice.update();
			preySizeValues[i] = lattice.getPreyPopulationSize();
			anglSizeValues[i] = lattice.getAnglerFishPopulationSize();
			foodSizeValues[i] = lattice.getFoodSize();
			brainSizeValues[i] = lattice.getAverageBrainSize();
			iterationValues[i] = i;
			
			plotA.removeAllPlots();
			plotA.addLinePlot("Prey", iterationValues, preySizeValues);
			plotA.addLinePlot("Angler fish", iterationValues, anglSizeValues);
			plotA.addLinePlot("Food fishes", iterationValues, foodSizeValues);
			
			plotB.removeAllPlots();
			plotB.addLinePlot("Average brain size", iterationValues, brainSizeValues);
			
			plotA.invalidate();
			plotA.revalidate();
			plotA.repaint();
			
			plotB.invalidate();
			plotB.revalidate();
			plotB.repaint();
			
			//System.out.println(lattice.getAverageBrainSize());
			try {
				Thread.sleep(sleepInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void ex2(){
		double[] afPercentage = { 0.0005, 0.001, 0.002, 0.003, 0.004, 0.005, 0.006, 0.007, 0.008, 0.009, 0.01, 0.011, 0.012, 0.013, 0.014, 0.015, 0.016, 0.017, 0.018, 0.019, 0.020, 0.021};//0.005, 0.01, 0.015, 0.02, 0.025, 0.030, 0.035, 0.040 };
		//double[] maxMet = {0.026, 0.027, 0.028, 0.029, 0.030};
		double[] maxMet = {0.002, 0.003, 0.004, 0.005, 0.006, 0.007, 0.008, 0.009, 0.010, 0.011, 0.012, 0.013, 0.014, 0.015, 0.016, 0.017, 0.018, 0.019, 0.020, 0.021, 0.022, 0.023, 0.024, 0.025, 0.026, 0.027, 0.028, 0.029, 0.030};
		int nIter = 10000;
		int repetitions = 5;
		try {
			File file = new File("largeBashSmallGrid.txt");

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (int k = 0; k < maxMet.length; k++){
				for (int j = 0; j < afPercentage.length; j++) {
					for (int n = 0; n < repetitions; n++) {
						Lattice lattice = new Lattice(latticeSize, afPercentage[j], foodPercentage, preyPopulationSize,
								enableVisualisation,maxMet[k],minMetabolism);
						for (int i = 0; i < nIter; i++) {
							lattice.update();
						}
						bw.write(String.valueOf(maxMet[k]) + " " + String.valueOf(afPercentage[j]) + " " + String.valueOf(lattice.getAverageBrainSize()));
						bw.newLine();
						System.out.println("[" + (double)((double)(k+1)/(double)maxMet.length) + "] : maxMet: " + maxMet[k] + " | afPer: " + afPercentage[j] + " | aver. bs: " + lattice.getAverageBrainSize());
					}
					
				}
			}
			
			
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void collectSomething()
	{
		try {
			File file = new File("testFile.txt");

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			int nIterations = 5000;
			while (nIterations-- > 0) {
				bw.write(String.valueOf(lattice.getAverageBrainSize()));
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

	public void collectData() {
		Plot2DPanel avgCautionPlot = new Plot2DPanel();
		Plot2DPanel popSizePlot = new Plot2DPanel();
		double[] afPercentage = { 0.001, 0.005, 0.01, 0.02, 0.03, 0.033, 0.035, 0.04, 0.05 };

		for (int i = 0; i < afPercentage.length; i++) {
			Lattice lattice = new Lattice(latticeSize, afPercentage[i], foodPercentage, preyPopulationSize,
					enableVisualisation,maxMetabolism,minMetabolism);

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
					double averageCaution = lattice.getAverageBrainSize();
					double popSizeValue = lattice.getPreyPopulationSize();
					iterationValues[j] = j;
					avgCautionValues[j] = Double.isNaN(averageCaution) ? 0 : averageCaution;
					popSizeValues[j] = Double.isNaN(popSizeValue) ? 0 : popSizeValue;

					bw.write(String.valueOf(averageCaution));
					bw.newLine();
					lattice.update();
				}
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			avgCautionPlot.addLinePlot(afPercentage[i] * 100 + "% Angler fishes", iterationValues, avgCautionValues);
			popSizePlot.addLinePlot(afPercentage[i] * 100 + "% Angler fishes", iterationValues, popSizeValues);
		}
		newPlotWindow(avgCautionPlot, "Average caution plot");
		newPlotWindow(popSizePlot, "Population size plot");
	}

	private void newPlotWindow(Plot2DPanel plot, String desc) {
		JFrame frame = new JFrame(desc);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}

	public void collectDataEnd() {
		// double[] afPercentage = {0.001, 0.005, 0.01, 0.0125, 0.015, 0.0175,
		// 0.02, 0.0225, 0.025, 0.0275, 0.03, 0.033, 0.035, 0.04, 0.05};
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

				for (int iter = 0; iter < iterations; iter++) {
					System.out.println("Running index " + i + " out of " + afPercentage.length + "(" + iter + ")");
					Lattice lattice = new Lattice(latticeSize, afPercentage[i], foodPercentage, preyPopulationSize,
							enableVisualisation, maxMetabolism, minMetabolism);
					double averageCaution = lattice.getAverageBrainSize();
					for (int j = 0; j < nIterations; j++) {
						lattice.update();
						if (lattice.getAverageBrainSize() > 0) {
							averageCaution = lattice.getAverageBrainSize();
						}
					}
					meanAverageCaution += averageCaution;
				}
				System.out.println(meanAverageCaution + " : " + meanAverageCaution / iterations);
				bw.write(String.valueOf(afPercentage[i]) + " " + String.valueOf(meanAverageCaution / iterations));
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setupMenu() {
		// Creates a menubar for a JFrame
		JMenuBar menuBar = new JMenuBar();

		// Add the menubar to the frame
		setJMenuBar(menuBar);

		// Define and add two drop down menu to the menubar
		JMenu simulationMenu = new JMenu("Simulation");
		JMenu examplesMenu = new JMenu("Example");
		menuBar.add(simulationMenu);
		menuBar.add(examplesMenu);

		// Create and add simple menu item to one of the drop down menu
		JMenuItem stepAction = new JMenuItem("Step simulation");
		JMenuItem runAction = new JMenuItem("Run simulation");

		// Create and add CheckButton as a menu item to one of the drop down
		// menu
		JCheckBoxMenuItem checkAction = new JCheckBoxMenuItem("Check Action");
		
		JMenuItem ex1Action = new JMenuItem("Example 1");
		// Create and add Radio Buttons as simple menu items to one of the drop
		// down menu
		JRadioButtonMenuItem radioAction1 = new JRadioButtonMenuItem("Radio Button1");
		JRadioButtonMenuItem radioAction2 = new JRadioButtonMenuItem("Radio Button2");
		// Create a ButtonGroup and add both radio Button to it. Only one radio
		// button in a ButtonGroup can be selected at a time.
		ButtonGroup bg = new ButtonGroup();
		bg.add(radioAction1);
		bg.add(radioAction2);
		simulationMenu.add(stepAction);
		simulationMenu.add(runAction);
		simulationMenu.add(checkAction);
		simulationMenu.addSeparator();
		examplesMenu.addSeparator();
		examplesMenu.add(radioAction1);
		examplesMenu.add(radioAction2);
		examplesMenu.add(ex1Action);
		// Add a listener to the New menu item. actionPerformed() method will
		// invoked, if user triggered this menu item
		runAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("You have clicked on the new action");
				SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>() {
				    @Override
				    public Integer doInBackground() {
						ex1();
						return 1;
				    }
				};
				worker.execute();
			}
		});
		ex1Action.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("You have clicked on the new action");
				SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>() {
				    @Override
				    public Integer doInBackground() {
						ex1();
						return 1;
				    }
				};
				worker.execute();
			}
		});
	}
}
