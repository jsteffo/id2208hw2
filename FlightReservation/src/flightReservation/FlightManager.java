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
		List <FlightPathDTO> returnList = new ArrayList<>();
		for(Flight f : currentFlightList) {
			returnList.add(new FlightPathDTO(f.getDestinationCity(), f.getDepartureCity()));
		}
		return returnList;
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
        String bookTicket(String creditCardnbr, String departureCity, String destinationCity) {
        List<Flight> flightsToBook = new ArrayList<Flight>();
        getPossibleRoutingLocal(departureCity, destinationCity, flightsToBook);   // hittar rutten som man ska boka
        boolean isTicketsLeft=true;
        String returnMessage="";
        Flight tempFlight;
        // loopa igenom alla som ska bokas och kolla om ngn av de har slut på biljetter
        for (int i = 0; i < flightsToBook.size(); i++) {
            int tempTickets = flightsToBook.get(i).getNumberOfTickets();
            
            if(tempTickets < 1){
                isTicketsLeft = false;
                tempFlight = flightsToBook.get(i);
                returnMessage = "Vi kunde tyvärr inte boka dina biljetter eftersom att flighten mellan "+tempFlight.getDepartureCity()+" och "+tempFlight.getDestinationCity()+ "har slut på biljetter";
            }
        }
            if (isTicketsLeft) {
                // här loopar vi igenom igen och faktiskt bokar alla
                for (int j = 0; j < flightsToBook.size(); j++) {
                    int tempTickets = flightsToBook.get(j).getNumberOfTickets();
                    flightsToBook.get(j).setNumberOfTickets(tempTickets - 1);
                }
                returnMessage = "biljett mellan "+departureCity+" och "+destinationCity+"är bokad";
            }
        
        return returnMessage;
        }
        

                    
            
            
            
           
        

}
