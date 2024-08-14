public class Road {

	String roadID, intersection1ID, intersection2ID;
	Double distance;

	Road(String id, String inter1ID, String inter2ID) {
		roadID = id;
		intersection1ID = inter1ID;
		intersection2ID = inter2ID;
	}

	public String toString() {
		return roadID;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
