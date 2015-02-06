/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightReservation;

import java.time.LocalTime;

/**
 *
 * @author norde_000
 */
public class Flight {
    String destinationCity;
    String departureCity;
    int flightLenght;
    LocalTime departureTime;    // LocalTime finns bara i java 8, ska man köra det eller ngt annat?
    LocalTime estimatedArrival;
    int price;
    
    public Flight(String destinationCity,String departureCity,int flightLenght,LocalTime departureTime,int price){
        this.destinationCity = destinationCity;
        this.departureCity = departureCity;
        this.flightLenght = flightLenght;
        this.departureTime = departureTime;
        this.price = price;
        this.estimatedArrival = departureTime.plus(flightLenght, null);
    }
    
    
}
