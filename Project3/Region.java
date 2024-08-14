import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Region {

	Map<String, Intersection> intersectionMap;
	Map<String, Road> roadMap;
	List<Road> roads;

	Map<Key, String> intersectionsToRoadMap; // key <intersection1ID, intersection2ID, roadID>

	public static double minLat, maxLat, minLong, maxLong;

	Region() {
		intersectionMap = new HashMap<String, Intersection>();
		roadMap = new HashMap<String, Road>();

		minLat = minLong = Integer.MAX_VALUE;
		maxLat = maxLong = Integer.MIN_VALUE;
		roads = new ArrayList<Road>();

		// HashMap<String, String> temp = new HashMap<String, String>
		intersectionsToRoadMap = new HashMap<Key, String>();
	}

	// public void insertToRoadMap(String roadID, Road road) {
	// roadMap.put(roadID, road);
	// setDistance(roadID);
	// roads.add(road);
	// }
	/*
	 * public void printMap() {
	 * int counter =0;
	 * for (Key key1:intersectionsToRoadMap.keySet()) {
	 * counter++;
	 * String value = intersectionsToRoadMap.get(key1);
	 * System.out.println(counter + ":<" + key1 + ">:" + value);
	 * 
	 * }
	 * }
	 */
	public void insertToRoadMap(String roadID, String intersection1ID, String intersection2ID) {
		Key key = new Key(intersection1ID, intersection2ID);
		intersectionsToRoadMap.put(key, roadID);

		Road road = new Road(roadID, intersection1ID, intersection2ID);
		roadMap.put(roadID, road);
		setDistance(roadID);
		roads.add(road);
	}

	private void printRoads() {
		for (Road road : roads) {
			System.out.println("road:" + road);
		}
	}

	public void insert(Intersection intersection) {
		// updates the min and max latitude and longitude
		if (intersection.latitude < minLat) {
			minLat = intersection.latitude;
		}

		if (intersection.latitude > maxLat) {
			maxLat = intersection.latitude;
		}

		if (intersection.longitude < minLong) {
			minLong = intersection.longitude;
		}

		if (intersection.longitude > maxLong) {
			maxLong = intersection.longitude;
		}

		intersectionMap.put(intersection.intersectionID, intersection);
	}

	public void setDistance(String roadID) {

		Road road = roadMap.get(roadID);
		assert (road != null);

		Intersection inter1 = intersectionMap.get(road.intersection1ID);
		Intersection inter2 = intersectionMap.get(road.intersection2ID);

		double dist = calcDist(inter1.latitude, inter1.longitude, inter2.latitude, inter2.longitude);
		road.distance = dist;

	}

	public static double calcDist(double lat1, double long1, double lat2, double long2) {

		int earthRadius = 6371000;

		lat1 = Math.toRadians(lat1);
		long1 = Math.toRadians(long1);
		lat2 = Math.toRadians(lat2);
		long2 = Math.toRadians(long2);

		double changeLat = lat2 - lat1;
		double changeLong = long2 - long1;

		double a = (Math.sin(changeLat / 2) * Math.sin(changeLat / 2))
				+ (Math.cos(lat1) * Math.cos(lat2) * Math.sin(changeLong / 2) * Math.sin(changeLong / 2));

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return earthRadius * c;

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
