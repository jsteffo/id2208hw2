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
	
}
