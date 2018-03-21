/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isikukoodid;

import java.text.*;
import java.util.*;

/**
 *
 * @author diana
 */
public class IsikukoodidGen {

    /**
     * String tüüpi muutujad sugu, soo nr, päev, kuu, sajand, aasta
     * teine kuni seitsmes nr isikukoodis, haigla nr, kontrollnr, isikukood
     */
    private String gender, gendernr, day, month, century, year,
            twotoseven, hospitalnr, controlnr, personalcode;
    
    /**
     * Int tüüpi muutuja täisaasta
     */
    private static int fullYear, maxYear, minYear;

    /**
     * Klassi konstruktor
     * @param gender sugu
     * @param date kuupäev
     * @param maxYear
     * @param minYear
     */
    public IsikukoodidGen(String gender, String date, int maxYear, int minYear) {
        this.maxYear = maxYear;
        this.minYear = minYear;
        this.gender = gender;
        day = date.substring(0, 2);
        month = date.substring(3, 5);
        century = date.substring(6, 8);
        year = date.substring(8);
        fullYear = Integer.parseInt(century + year);
        if (genderNr() && twoToSeven()) {
            hospitalNr();
            controlNr();
            personalcode = gendernr + twotoseven + hospitalnr + controlnr;
        }
    }

    /**
     * Isikukood
     * @return Tagastab isikukoodi
     */
    public String getPersonalcode() {
        return personalcode;
    }

    /**
     * Soo nr
     * @return Genereerib soo numbri, õnnestumisel tagastab true
     */
    private boolean genderNr() {
        if (gender.equalsIgnoreCase("Naine") || gender.equalsIgnoreCase("N")) {
            switch (century) {
                case "18":
                    gendernr = "2";
                    break;
                case "19":
                    gendernr = "4";
                    break;
                case "20":
                    gendernr = "6";
                    break;
                default:
                    break;
            }
            return true;
        } else if (gender.equalsIgnoreCase("Mees") || gender.equalsIgnoreCase("M")) {
            switch (century) {
                case "18":
                    gendernr = "1";
                    break;
                case "19":
                    gendernr = "3";
                    break;
                case "20":
                    gendernr = "5";
                    break;
                default:
                    break;
            }
            return true;
        }
        personalcode = "Sugu on vigane!";
        return false;
    }
    
    /**
     * Isikukoodi 2.-7. nrid
     * @return Genereerib 2.-7. nrid, õnnestumisel tagastab true
     */
    private boolean twoToSeven() {
        if (isDateValid(fullYear + "-" + month + "-" + day) && fullYear >= minYear && fullYear <= maxYear) {
            twotoseven = year + month + day;
            return true;
        }
        personalcode = "Kuupäev on vigane!";
        return false;
    }

    /**
     * Haigla number
     */
    private void hospitalNr() {
        int randNr = randInt(1, 999);
        hospitalnr = String.format("%03d", randNr);
    }

    /**
     * Kontrollnumber
     */
    private void controlNr() {
        String firstTen = gendernr + year + month + day + hospitalnr;
        String[] parts = firstTen.split("(?!^)");
        int[] firstTierNrs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
        int[] secondTierNrs = {3, 4, 5, 6, 7, 8, 9, 1, 2, 3};
        int total = 0;
        for (int i = 0; i < firstTierNrs.length; i++) {
            total = total + (firstTierNrs[i] * Integer.parseInt(parts[i]));
        }
        int modular = total % 11;
        if (modular == 10) {
            for (int i = 0; i < secondTierNrs.length; i++) {
                total = total + (secondTierNrs[i] * Integer.parseInt(parts[i]));
            }
            modular = total % 11;
        }
        controlnr = modular + "";
    }

    /**
     * Kuupäeva kontroll
     * @param date String kuupäev yyyy-MM-dd
     * @return 
     */
    private boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Genereerib suvalise nri kaasaantud vahemikus
     * @param min Miinimum nr
     * @param max Maksimum nr
     * @return 
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
