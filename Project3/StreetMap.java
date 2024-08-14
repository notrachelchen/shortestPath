import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class StreetMap {
	String startIntersection, endIntersection;
	Region region;
	MapPanel mapPanel;
	JFrame frame;

	StreetMap() {
		region = new Region();
		mapPanel = new MapPanel();

		frame = new JFrame("Map");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void readFile(String fileName) {
		String line, intersectionID, intersection1ID, intersection2ID, roadID;
		String[] tokens;
		Double latitude, longitude;

		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));

			while ((line = br.readLine()) != null) {
				tokens = new String[4];
				tokens = line.split("\\s+");

				if (tokens[0].startsWith("i")) {
					intersectionID = tokens[1];
					latitude = Double.parseDouble(tokens[2]);
					longitude = Double.parseDouble(tokens[3]);

					Intersection intersection = new Intersection(intersectionID, latitude, longitude);

					region.insert(intersection);

				}

				if (tokens[1].startsWith("r")) {
					roadID = tokens[1];
					intersection1ID = tokens[2];
					intersection2ID = tokens[3];

					// Road road = new Road(roadID, intersection1ID, intersection2ID);
					// region.insertToRoadMap(roadID, road);
					region.insertToRoadMap(roadID, intersection1ID, intersection2ID);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doShow() {
		System.out.println("Drawing is processing... Please wait");
		// frame = new JFrame("Map");
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mapPanel.setParams(region);

		frame.getContentPane().add(mapPanel);
		frame.pack();
		frame.setVisible(true);

	}

	public void doDirections(String startIn, String endIn) {
		startIntersection = startIn;
		endIntersection = endIn;

	}

	public static int containString(String[] array, String target) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].contains(target)) {
				return i;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// args = new String[5];

		StreetMap streetMap = new StreetMap();
		String fileName = args[0]; // args[0];
		// args[0] = fileName;
		// args[1] = "--show";
		// args[2] = "--directions";
		// args[3] = "i106835"; //"MOREY";
		// args[4] = "i300339"; //"HOYT";
		// args[3] = "GILBERT-LONG";
		// args[4] = "OBRIEN";

		streetMap.readFile(fileName);
		String startIntersectionID, endIntersectionID;

		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) {
				continue;
			}

			if (args[i].contains("directions")) {
				startIntersectionID = args[i + 1];
				endIntersectionID = args[i + 2];
				// streetMap.doDirections(startIntersectionID,endIntersectionID);

				Graph g = new Graph(streetMap.region);
				g.buildGraph();
				Node startNode = g.getNode(startIntersectionID);
				Node endNode = g.getNode(endIntersectionID);
				// g.dijkstra(startNode);
				g.dijkstra(startNode, endNode);
				streetMap.mapPanel.pathList = g.getShortedPath(startNode, endNode);

				streetMap.frame.invalidate();
				streetMap.frame.validate();
				streetMap.frame.repaint();

			}

			if (args[i].contains("show")) {
				streetMap.doShow();
			}

		}

	}

}
