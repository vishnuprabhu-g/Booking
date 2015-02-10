/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author vishnu-pt517
 */
public class FareCalculater {

    public static double CalculateFare(double distance, double costPerKm) {
        double actual = distance * costPerKm;
        return Math.ceil(actual);
    }

    public static double calculateReservationFare(double distance) {
        if (distance < 100) {
            return 15;
        } else {
            return 30;
        }
    }

    public static double roundOff(double fare) {
        double val = Math.ceil(fare);
        if (val % 5 != 0) {
            val = ((int) (val / 5)) * 5 + 5;
        }
        return val;
    }
}
