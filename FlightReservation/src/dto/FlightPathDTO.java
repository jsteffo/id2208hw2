package dto;





public class FlightPathDTO {

	private String destination;
	private String departure;

	public FlightPathDTO(String destination, String departure) {

		this.destination = destination;
		this.departure = departure;
	}

	public String getDestination() {
		return destination;
	}
	public String getDeparture() {
		return departure;
	}

    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @param departure the departure to set
     */
    public void setDeparture(String departure) {
        this.departure = departure;
    }
	
}
