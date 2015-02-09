package flightReservation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.jws.WebService;

import dto.FlightPathDTO;
import dto.PriceDTO;
import dto.TicketsForRouteDTO;

@WebService(targetNamespace="http://id2208hw2BottomUp/")
public class FlightManager {

	public static void main(String args []) {
		new FlightManager();
	}

//	private List <Flight> flightList;
	private List <City> cityList;


	public FlightManager(){

		cityList = new ArrayList<>();
	//	flightList = new ArrayList<Flight>();

		initiateCityFlights();
		//Keeps track of the suggested flights involved in route
		//Initially empty but populated by getPossibleRouting
		//				List <Flight> currentFlightList = new ArrayList<Flight>();
		//				getPossibleRoutingLocal("Stockholm", "Göteborg", currentFlightList);
		//				for(Flight f : currentFlightList) {
		//					System.out.println("from: " + f.getDepartureCity() + " to " + f.getDestinationCity());
		//				}
		//		List<Flight> fff = getPossibleRoutingLocal("Stockholm", "Visby", LocalDate.MIN);
		//		for(Flight f : fff) {
		//		
		//			System.out.println("from: " + f.getDepartureCity() + " to " + f.getDestinationCity());
		//		}		
		//initiate stuff....

	}


	private void initiateCityFlights(){
		LinkedList <Flight> flightList = new LinkedList<Flight>();
		flightList.add(new Flight("Stockholm", "Göteborg", LocalDate.of(2015, 3, 1), 55, 4, 1));
		flightList.add(new Flight("Stockholm", "Malmö", LocalDate.of(2015, 2, 1), 55, 4, 2));
		flightList.add(new Flight("Stockholm", "Abisko", LocalDate.of(2015, 5, 1), 55, 4, 3));
		cityList.add(new City("Stockholm", flightList));

		flightList = new LinkedList<Flight>();
		flightList.add(new Flight("Göteborg", "Halmstad", LocalDate.of(2015, 1, 1), 55, 4, 4));
		flightList.add(new Flight("Göteborg", "Visby", LocalDate.of(2015, 4, 1), 55, 4, 5));
		cityList.add(new City("Göteborg", flightList));

		flightList = new LinkedList<Flight>();
		flightList.add(new Flight("Malmö", "Halmstad", LocalDate.of(2015, 1, 1), 55, 4, 6));
		flightList.add(new Flight("Malmö", "Stockholm", LocalDate.of(2015, 1, 1), 55, 4, 7));
		cityList.add(new City("Malmö", flightList));

		flightList = new LinkedList<Flight>();
		cityList.add(new City("Abisko", flightList));
		flightList = new LinkedList<Flight>();
		cityList.add(new City("Visby", flightList));
		flightList = new LinkedList<Flight>();
		cityList.add(new City("Halmstad", flightList));
	}

	//Stefan skall fixa med denna
	public List<FlightPathDTO> getPossibleRouting(String departureCity, String arrivalCity){
		List <FlightPathDTO> returnList = new ArrayList<FlightPathDTO>();
		List <Flight> currentFlightList = getPossibleRoutingLocal(departureCity, arrivalCity, LocalDate.MIN);
		for(Flight f : currentFlightList) {
			returnList.add(new FlightPathDTO(f.getDestinationCity(), f.getDepartureCity()));
		}
		return returnList;
	}


	//Stefan skall fixa med denna
	public TicketsForRouteDTO getTicketsForRoute(String departureCity, String destinationCity, String date){
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		int day = Integer.parseInt(date.split("-")[2]);
		List <Flight> flightList = getPossibleRoutingLocal(departureCity, destinationCity, LocalDate.of(year, month, day));
		int minAvailableTickets = Integer.MAX_VALUE;
		int price = 0;
		for(Flight f : flightList) {
			if(f.getNumberOfTickets() < minAvailableTickets){
				minAvailableTickets = f.getNumberOfTickets();
			}
			price += price;
		}
		if(flightList.size() != 0){
			return new TicketsForRouteDTO(price, minAvailableTickets);	
		}
		return null;
	}

	//Motherfuck nytt
	private List<Flight> getPossibleRoutingLocal(String departureCity, String destinationCity, LocalDate date){
		int [] previousArray = new int [1000];
		Comparator<Flight> comp = new Comparator<Flight>() {
			@Override
			public int compare(Flight f1, Flight f2) {
				return f1.getDepartureTime().compareTo(f2.getDepartureTime());
			}
		};
		Set<String> citySet = new HashSet<String>(); //Use this set to keep track of nodes NOT visisted.
		LinkedList <Flight> queue = new LinkedList<Flight>();
		for(City c : cityList){
			citySet.add(c.getName());
			if(c.getName().equalsIgnoreCase(departureCity)){
				for(Flight flight : c.getFlightList()){
					if(flight.getDepartureTime().compareTo(date) >= 0){
						queue.add(flight);	
					}		
				}
			}
		}

		if(queue.isEmpty()) {

			return null;
		}
		while(!citySet.isEmpty()  && !queue.isEmpty()){

			//sort här så vi köra på tid!
			queue.sort(comp); //ej så effektivt
			Flight oldFlight = queue.poll();
			citySet.remove(oldFlight.getDepartureCity());

			City currentCity = null;

			for(City c : cityList){
				if(c.getName().equalsIgnoreCase(oldFlight.getDestinationCity())){
					currentCity = c;
				}
			}
			if(currentCity.getName().equalsIgnoreCase(destinationCity)){
				//We are done
				List<Flight> localFlightList = new ArrayList<Flight>();
				int id = oldFlight.getId();
				while(id != 0){				
					for(City c : cityList){				
						for(Flight f : c.getFlightList()){				
							if(f.getId() == id){
								localFlightList.add(f);	
								id = previousArray[id];
							}
						}
					}
				}
				return localFlightList;
			}
			//For every flight in city
			for(Flight newFlight : currentCity.getFlightList()){
				//Only queue up flight assuming flight TO city occur before flight FROM city
				//Flights are assumed to be instantanious. And we don't want to travel back. Cyclic might still be 
				//a problem

				if(!newFlight.getDestinationCity().equalsIgnoreCase(oldFlight.getDepartureCity()) 
						&& oldFlight.getDepartureTime().compareTo(newFlight.getDepartureTime()) <= 0){
					//If city not already visisted

					if(citySet.contains(newFlight.getDestinationCity())){
						previousArray[newFlight.getId()] = oldFlight.getId();

						queue.add(newFlight);	
					}

				}
			}


		}

		return null;
	}

	public List<PriceDTO> outputPrice(){
		List<PriceDTO> priceList = new ArrayList<>();
		for(City c : cityList){
			for(Flight f : c.getFlightList()){
				PriceDTO dto = new PriceDTO(f.getDepartureCity(), f.getDestinationCity(), f.getPrice());
				priceList.add(dto);
			}
		}
		return priceList;
	}


	public String bookTicket(String creditCardnbr, String departureCity, String destinationCity) {
		
		List<Flight> flightsToBook = getPossibleRoutingLocal(departureCity, destinationCity, LocalDate.MIN); 
		boolean isTicketsLeft=true;
		String returnMessage="";
		Flight tempFlight;
		if(flightsToBook.isEmpty()){
			returnMessage = "There is no route between "+departureCity+" and "+destinationCity;
			return returnMessage;
		}

		// loopa igenom alla som ska bokas och kolla om ngn av de har slut på biljetter
		for (int i = 0; i < flightsToBook.size(); i++) {
			int tempTickets = flightsToBook.get(i).getNumberOfTickets();

			if(tempTickets < 1){
				isTicketsLeft = false;
				tempFlight = flightsToBook.get(i);
				returnMessage = "Vi kunde tyvärr inte boka dina biljetter "
						+ "eftersom att flighten mellan "+tempFlight.getDepartureCity()+" och "
								+ ""+tempFlight.getDestinationCity()+ " har slut på biljetter";
			}
		}
		if (isTicketsLeft) {
			// här loopar vi igenom igen och faktiskt bokar alla
			for (int j = 0; j < flightsToBook.size(); j++) {
				int tempTickets = flightsToBook.get(j).getNumberOfTickets();
				flightsToBook.get(j).setNumberOfTickets(tempTickets - 1);
			}
			returnMessage = "biljett mellan "+departureCity+" och "+destinationCity+" är bokad";
		}

		return returnMessage;
	}










}
