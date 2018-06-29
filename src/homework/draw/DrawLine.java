package homework.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import homework.algorithm.ACOsolution;
import homework.algorithm.AntForTSP;
import homework.bean.City;

public class DrawLine extends JPanel {

	static AntForTSP tsp = null;

	List<City> from = null;
	List<City> dest = null;

	boolean first = true;

	/**
	 * draw
	 * @throws IOException
	 * @throws CloneNotSupportedException
	 */
	public void draw(AntForTSP  explore)  throws IOException, CloneNotSupportedException {
		
		tsp = explore;
		tsp.main();

		JFrame j = new JFrame();
		j.setBackground(Color.blue);
		j.setSize(1000, 600);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setVisible(true);

		DrawLine drawLine = new DrawLine();
		j.getContentPane().add(drawLine);

		// 回起點
		tsp.bestRouteList.add(tsp.bestRouteList.get(0));
		
		System.out.println("畫圖");
		System.out.println(tsp.recordBest.size());

		for (int i = 0; i < tsp.bestRouteList.size() - 1; i++) {

			drawLine.from = new ArrayList<>();
			drawLine.dest = new ArrayList<>();

			for (int k = 0; k <tsp.recordBest.size(); k += 5) {
				
				drawLine.from.add(tsp.allCity.get(tsp.recordBest.get(k).getVisit().get(i)));
				drawLine.dest.add(tsp.allCity.get(tsp.recordBest.get(k).getVisit().get(i + 1)));
				
				
				if(drawLine.first){
					System.out.println("iter:"+(k+5));
				}
			}
			
			//此為比較用，tsp 最佳解
			drawLine.from.add(tsp.allCity.get(tsp.bestRouteList.get(i)));
			drawLine.dest.add(tsp.allCity.get(tsp.bestRouteList.get(i + 1)));

			drawLine.repaint();
			// wait 500 millisecond
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
			}
		}
	
	}
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {

		if (first) {

			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth() + 100, this.getHeight());
			first = false;
		}
		int addX = 0;
		int addY = 0;
		
		for (int i = 0; i < from.size(); i++) {
			
			//五個為一行
			if (i % 5 == 0 && i != 0) {
				addX = 0;
				addY += 100;
			} else {

				if (i != 0) {
					addX += 100;
				}
			}
			
			//tsp 最要解
			if (i == from.size() - 1) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.RED);
			}

			g.drawLine((int) from.get(i).getX() + addX, (int) from.get(i).getY() + addY,
					(int) dest.get(i).getX() + addX, (int) dest.get(i).getY() + addY);

		}
	}
}
