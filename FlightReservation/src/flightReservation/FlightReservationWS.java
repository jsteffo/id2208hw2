/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightReservation;

import java.util.ArrayList;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import java.time.LocalTime;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import dto.Route;
import dto.TicketAndPrice;

/**
 *
 * @author norde_000
 */
@WebService(serviceName = "FlightReservationWS")
public class FlightReservationWS {

    ArrayList<Flight> listOfFlights = new ArrayList<>();
    ArrayList<Route> possibleRoutes = new ArrayList<>();
    LocalTime tid1 = LocalTime.of(6,00);
    LocalTime tid2 = LocalTime.of(8,00);
    LocalTime tid3 = LocalTime.of(10,00);
    LocalTime tid4 = LocalTime.of(12,00);
    LocalTime tid5 = LocalTime.of(15,00);
    Flight flight1;
    Flight flight2;
    Flight flight3;
    Flight flight4;
    Flight flight5;
    
   
    public FlightReservationWS() {
        // instansierar flightsen, sätter alla med 10 ticktes till o börja med
        // enda sättet jag kommer på för tillfället för att kunna boka flights på olika datum är att ha en nestlad lista typ list<Datum<Flight>> där varje element i den första listan
        //motsvarar ett datum, och varje datum innehåller en lista med flights som går den dagen. känns jävla krångligt men kommer inte på så mkt mer just nu
        this.flight5 = new Flight("Stockholm", "Bankok", 600, tid5, 8000,10);
        this.flight4 = new Flight("London", "Berlin", 80, tid4, 400,10);
        this.flight3 = new Flight("Stockholm", "London", 65, tid3, 700,10);
        this.flight1 = new Flight("Stockholm", "Malmö", 45, tid2, 500,10);
        this.flight2 = new Flight("Malmö", "London", 45, tid1, 500,10);

    }

    @PostConstruct
    private void initVariables() {
        listOfFlights.add(flight1);
        listOfFlights.add(flight2);
        listOfFlights.add(flight3);
        listOfFlights.add(flight4);
        listOfFlights.add(flight5);
        

    }
        // Returnar en ArrayList<Route>, om det finns direktflgiht så är det bara en route i listan, annars alla möjliga kombinationer, går det inte att flyga dit/därifrån så är den null;
    @WebMethod(operationName = "FindFlight")
    public ArrayList<Route> FindFlight(@WebParam(name = "DepartureCity") String departureCity, @WebParam(name = "DestinationCity") String destinationCity) {
        ArrayList<Flight> listOfRelevantFlights = new ArrayList<>(); 
        for (int i = 0; i < listOfFlights.size(); i++) {
                // finns det direktflyg så läggs den flighten in i en route som läggs ensam i possibleRoutes
            if (departureCity.equalsIgnoreCase(listOfFlights.get(i).getDepartureCity())&&destinationCity.equalsIgnoreCase(listOfFlights.get(i).getDestinationCity())) {
                
                Flight relevantFlight = listOfFlights.get(i);
                listOfRelevantFlights.add(relevantFlight);
                Route directRoute = new Route(listOfRelevantFlights);
                possibleRoutes.add(directRoute);
                return possibleRoutes;
            }
            // sen får man kolla om det går att hitta en kombination av flights som tar en till sin destination, fastnat på det dock så börjar på task 3 ist
            
            // om inte returna listan som null
            else{
                possibleRoutes.clear();
                return possibleRoutes;
            }
        }
        return possibleRoutes;  // borde aldrig komma hit

    }

    /**
     * Web service operation
     * 
     */
    //OBS! gör den utan att kolla datum nu eftersom vi inte bestämt oss för hur det ska lösas än
    @WebMethod(operationName = "CheckAvailableTickets")
    public TicketAndPrice CheckAvailableTickets(@WebParam(name = "route") Route route) { // för varje flight i route kollar antalet tickets och pris, svarar med antalet tickets i den 
                                                                                         //flight som har minst antal tickets. plussar även ihop priset för alla och svarar med summan.
        int routePrice=0;                                                                                
        TicketAndPrice answer = new TicketAndPrice(100,0); // börjar med 100 tickets eftersom det är fler än någon flight har.
        for(int i=0;i<route.getFlights().size();i++){
            if(route.getFlights().get(i).getNumberOfTickets()<answer.getNumberOfTickets()){
                answer.setNumberOfTickets(route.getFlights().get(i).getNumberOfTickets());
            }
            
            routePrice +=routePrice+route.getFlights().get(i).getPrice();
            
        }
        answer.setPriceOfTicket(routePrice);
        
        return answer;
    }


}
