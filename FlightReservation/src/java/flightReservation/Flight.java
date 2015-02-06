/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightReservation;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author norde_000
 */
public class Flight {
    private String destinationCity;
    private String departureCity;
    private long flightLenght;      // Tid flyget tar i minuter
    private LocalTime departureTime;    // LocalTime finns bara i java 8, ska man köra det eller ngt annat?
    private LocalTime estimatedArrival;
    private int price;
    
    public Flight(String destinationCity,String departureCity,long flightLenght,LocalTime departureTime,int price){
        this.destinationCity = destinationCity;
        this.departureCity = departureCity;
        this.flightLenght = flightLenght;
        this.departureTime = departureTime.truncatedTo(ChronoUnit.MINUTES);       // gör så vi bara får med HH:MM
        this.price = price;
        this.estimatedArrival = departureTime.plus(flightLenght,ChronoUnit.MINUTES);    // adderar flightlenght antal minuter till tiden
    }

    /**
     * @return the destinationCity
     */
    public String getDestinationCity() {
        return destinationCity;
    }

    /**
     * @return the departureCity
     */
    public String getDepartureCity() {
        return departureCity;
    }

    /**
     * @return the flightLenght
     */
    public long getFlightLenght() {
        return flightLenght;
    }

    /**
     * @return the departureTime
     */
    public LocalTime getDepartureTime() {
        return departureTime;
    }

    /**
     * @return the estimatedArrival
     */
    public LocalTime getEstimatedArrival() {
        return estimatedArrival;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }
    
    
}
