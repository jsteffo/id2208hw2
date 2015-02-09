package flightReservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import dto.FlightPathDTO;

@WebService(targetNamespace="http://id2208hw2BottomUp/")
public class FlightManager {

	public static void main(String args []) {
		new FlightManager();
	}

	private List <Flight> flightList;

	public FlightManager(){
		//Initiate flights so we can play
		flightList = new ArrayList<Flight>();
		initiateFlights();
		//Keeps track of the suggested flights involved in route
		//Initially empty but populated by getPossibleRouting
//		List <Flight> currentFlightList = new ArrayList<Flight>();
//		getPossibleRouting("Stockholm", "rrr", currentFlightList);
//		for(Flight f : currentFlightList) {
//			System.out.println("from: " + f.getDepartureCity() + " to " + f.getArrivalCity());
//		}
		//initiate stuff....

	}

	private void initiateFlights(){
		//The following four routes makes the route stockholm to göteborg have 2 possible alternatives.
		flightList.add(new Flight(99, "Stockholm", "Abisko", LocalDate.of(2015, 1, 1) , 5,5));
		flightList.add(new Flight(99, "Abisko", "Kiruna", LocalDate.of(2015, 1, 1) , 5,6));
		flightList.add(new Flight(99, "Stockholm", "Malmö", LocalDate.of(2015, 1, 1) , 5,1));

		flightList.add(new Flight(33, "Halmstad", "Göteborg", LocalDate.of(2015, 1, 1) , 2,2));
		flightList.add(new Flight(88, "Malmö", "Göteborg", LocalDate.of(2015, 1, 1) , 7,3));

		flightList.add(new Flight(77, "Gotland", "Stockholm", LocalDate.of(2015, 1, 1) , 9,4));
	}

	public List<Flight> getPossibleRoutingRemote(String departureCity, String arrivalCity){
		List <Flight> currentFlightList = new ArrayList<Flight>();
		getPossibleRouting(departureCity, arrivalCity, currentFlightList);
		List<FlightPathDTO> 
		for(Flight f : currentFlightList) {
			
		}
		return currentFlightList;
	}
	
	/**
	 * Used by getPossibleRoutingRemote to determine what flights allow one to travel from departure city to arrival city
	 * @param departureCity
	 * @param arrivalCity
	 * @param currentFlights
	 */
	private void getPossibleRouting(String departureCity, String arrivalCity, List<Flight> currentFlights){		
		for(Flight f : flightList) {			
			if(f.getDepartureCity().equalsIgnoreCase(departureCity)) {
				currentFlights.add(f);
				//Below basecase of some sort...
				if(f.getArrivalCity().equalsIgnoreCase(arrivalCity)){
					currentFlights.add(f);
					return;
				}
				getPossibleRouting(f.getArrivalCity(), arrivalCity, currentFlights);			
			}		
		}
		if(currentFlights.size() != 0){
			currentFlights.remove(currentFlights.size() - 1);	
		}
		return;
	}

}
