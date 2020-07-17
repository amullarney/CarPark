package deployment;

// Spring requires a POJ class for each message.
public class OccupancyUpdateMsg {
	private String occupancy;
	private String capacity;
	private String availability;
	public OccupancyUpdateMsg() {
	}
	public OccupancyUpdateMsg( String occupancy, String capacity, String availability ) {
		this.occupancy = occupancy;
		this.capacity = capacity;
		this.availability = availability;
	}
	public String getOccupancy() {
		return occupancy;
	}
	public String getCapacity() {
		return capacity;
	}
	public String getAvailability() {
		return availability;
	}
	public void setOccupancy( String occupancy ) {
		this.occupancy = occupancy;
	}
	public void setCapacity( String capacity ) {
		this.capacity = capacity;
	}
	public void setAvailability( String availability ) {
		this.availability = availability;
	}
}