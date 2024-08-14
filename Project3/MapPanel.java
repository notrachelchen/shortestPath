import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MapPanel extends JPanel {

	public double xScale, yScale;
	public double minLat, minLong, maxLat, maxLong;
	public List<Road> roads;
	public Map<String, Intersection> intersectionMap;
	public List<Intersection> pathList;

	public MapPanel() {
		setPreferredSize(new Dimension(900, 900));
		pathList = null;
	}

	public void setParams(Region region) {
		minLat = region.minLat;
		minLong = region.minLong;
		maxLat = region.maxLat;
		maxLong = region.maxLong;
		roads = region.roads;
		intersectionMap = region.intersectionMap;

	}

	public void paintComponent(Graphics page) {

		// use 2D graphics to display lines with double values for coordinates
		Graphics2D page2d = (Graphics2D) page;
		super.paintComponent(page2d);

		page2d.setColor(Color.BLACK);

		// set the scales
		// adapted from Lab TAs
		xScale = this.getWidth() / (maxLong - minLong);
		yScale = this.getHeight() / (maxLat - minLat);

		Intersection int1, int2;

		double x1, y1, x2, y2;

		scale();
		// display all roads
		for (Road road : roads) {

			int1 = intersectionMap.get(road.intersection1ID);
			int2 = intersectionMap.get(road.intersection2ID);

			x1 = int1.longitude;
			y1 = int1.latitude;
			x2 = int2.longitude;
			y2 = int2.latitude;

			// System.out.println("x1:" + x1 + ", y1:" + y1 + ", x2:" + x2 + ", y2:" + y2);
			page2d.draw(new Line2D.Double((x1 - minLong) * xScale, getHeight() - ((y1 - minLat) * yScale),
					(x2 - minLong) * xScale, getHeight() - ((y2 - minLat) * yScale)));

		}

		Node node1 = null;
		Node node2 = null;
		// Highlight paths found by DIJKSTRA
		/// *
		if (pathList != null) {
			page2d.setColor(Color.RED);
			page2d.setStroke(new BasicStroke(3));

			for (int i = 0; i < pathList.size() - 1; i++) {
				x1 = pathList.get(i).longitude;
				y1 = pathList.get(i).latitude;
				x2 = pathList.get(i + 1).longitude;
				y2 = pathList.get(i + 1).latitude;

				// System.out.println("x1:" + x1 + ", y1:" + y1 + ", x2:" + x2 + ", y2:" + y2);
				page2d.draw(new Line2D.Double((x1 - minLong) * xScale, getHeight() - ((y1 - minLat) * yScale),
						(x2 - minLong) * xScale, getHeight() - ((y2 - minLat) * yScale)));

			}

		}
		// */

	}

	public void scale() {

		xScale = this.getWidth() / (maxLong - minLong);
		yScale = this.getHeight() / (maxLat - minLat);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
