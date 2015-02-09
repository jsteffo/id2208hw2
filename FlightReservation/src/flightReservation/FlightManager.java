package flightReservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import dto.FlightPathDTO;
import dto.PriceDTO;

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
//		getPossibleRoutingLocal("Stockholm", "Göteborg", currentFlightList);
//		for(Flight f : currentFlightList) {
//			System.out.println("from: " + f.getDepartureCity() + " to " + f.getDestinationCity());
//		}
		//initiate stuff....

	}

	private void initiateFlights(){
		//The following four routes makes the route stockholm to göteborg have 2 possible alternatives.
		flightList.add(new Flight("Stockholm", "Abisko", LocalDate.of(2015, 1, 1),5, 7));

		flightList.add(new Flight("Abisko", "Kiruna", LocalDate.of(2015, 1, 1) , 5, 6));
		flightList.add(new Flight("Stockholm", "Malmö", LocalDate.of(2015, 1, 1) , 5, 1));

		flightList.add(new Flight("Halmstad", "Göteborg", LocalDate.of(2015, 1, 1) , 2, 2));
		flightList.add(new Flight("Malmö", "Göteborg", LocalDate.of(2015, 1, 1) , 7, 3));

		flightList.add(new Flight("Gotland", "Stockholm", LocalDate.of(2015, 1, 1) , 9, 4));
	}

	public List<FlightPathDTO> getPossibleRouting(String departureCity, String arrivalCity){
		List <Flight> currentFlightList = new ArrayList<Flight>();
		getPossibleRoutingLocal(departureCity, arrivalCity, currentFlightList);
		List<FlightPathDTO> returnFlightList = new ArrayList<FlightPathDTO>();
		for(Flight f : currentFlightList) {
			returnFlightList.add(new FlightPathDTO(f.getDestinationCity(), f.getDepartureCity()));
		}
		return returnFlightList;
	}
	
	/**
	 * Used by getPossibleRoutingRemote to determine what flights allow one to travel from departure city to arrival city
	 * @param departureCity
	 * @param arrivalCity
	 * @param currentFlights
	 */
	private void getPossibleRoutingLocal(String departureCity, String arrivalCity, List<Flight> currentFlights){		
		for(Flight f : flightList) {			
			if(f.getDepartureCity().equalsIgnoreCase(departureCity)) {
				currentFlights.add(f);
				//Below basecase of some sort...
				if(f.getDestinationCity().equalsIgnoreCase(arrivalCity)){
					currentFlights.add(f);
					return;
				}
				getPossibleRoutingLocal(f.getDestinationCity(), arrivalCity, currentFlights);			
			}		
		}
		if(currentFlights.size() != 0){
			currentFlights.remove(currentFlights.size() - 1);	
		}
		return;
	}
        public List<PriceDTO> outputPrice(){
            List<PriceDTO> priceList = new ArrayList();
            for(int i=0;i<flightList.size();i++){
                
                PriceDTO price = new PriceDTO(flightList.get(i).getDepartureCity(), flightList.get(i).getDestinationCity(),flightList.get(i).getPrice());
                priceList.add(price);
            }
            
            
            return priceList;
        }
        String bookTicket(String creditCardnbr,String departureCity, String destinationCity){
            List<FlightPathDTO> flightsToBook =  getPossibleRouting();
            
            return "grattis du har bokat ";
        }

}
