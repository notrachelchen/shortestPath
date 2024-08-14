public class Intersection {

	public String intersectionID;
	public Double latitude, longitude;
	public int nodeID;

	Intersection(String id, Double lat, Double lon) {
		intersectionID = id;
		latitude = lat;
		longitude = lon;
		nodeID = -1;
	}

	public String toString() {
		return intersectionID;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
