/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightReservation;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author norde_000
 */
public class Route {
    String departureCity;
    String destinationCity;
    LocalTime departureTime;
    int routeTime;
    ArrayList<Flight> flights; 
    
}
