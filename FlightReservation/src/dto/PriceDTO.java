/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author norde_000
 */
public class PriceDTO {
    private String departureCity;
    private String destinationCity;
    private int price;

    public PriceDTO(String departureCity, String destinationCity,int price){
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.price = price;
        
    }
    /**
     * @return the departureCity
     */
    public String getDepartureCity() {
        return departureCity;
    }

    /**
     * @return the destinationCity
     */
    public String getDestinationCity() {
        return destinationCity;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }
}