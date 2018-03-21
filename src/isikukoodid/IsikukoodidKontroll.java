/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isikukoodid;

import java.text.*;
import java.time.*;

/**
 * Isikukoodi klass
 *
 * @author diana
 */
public class IsikukoodidKontroll {

    /**
     * Isikukood
     */
    private String personalcode;

    /**
     * Kuude nimetused
     */
    private final String[] months = new String[]{"Jaanuar", "Veebruar",
        "Märts", "Aprill", "Mai", "Juuni", "Juuli", "August", "September",
        "Oktoober", "November", "Detsember"};

    /**
     * Tulevik ja isikukoodi korrektsus
     */
    private boolean future, pcValid;

    /**
     * Kuupäeva vorming
     */
    final static String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Klassi konstruktor
     *
     * @param personalcode
     * @param future
     */
    public IsikukoodidKontroll(String personalcode, boolean future) {
        if (personalcode.length() == 11 && isNumeric(personalcode) && isPersonalCodeValid(personalcode)) {
            this.personalcode = personalcode;
            this.future = future;
            String birthDate = (getBirthYearFull() + "-" + getBirthMonthNr() + "-" + getBirthDayNr());
            this.pcValid = isDateValid(birthDate);
        } else {
            this.pcValid = false;
        }
    }

    /**
     * Sünniaja kontroll (tulevik/minevik)
     *
     * @return Tagastab boolean tüüpi väärtuse - true tulevik, false minevik
     */
    public boolean checkDate() {
        LocalDate birthDate = getLocalDate();
        LocalDate currentDate = LocalDate.now();
        int difference = birthDate.compareTo(currentDate); // sünniaeg - tänane
        return difference > 0;
    }

    /**
     * Isikukoodi korrektsus ja tuleviku lubatuse võrdlus
     *
     * @return Tagastab boolean tüüpi väärtuse
     */
    public boolean getPcValid() {
        if (pcValid) {
            if ((future && checkDate()) || (!future && !checkDate()) || (future && !checkDate())) {
                return true;
            } else if (!future && checkDate()) {
                return false;
            }
        }
        return false;
    }

    /**
     * Soo number
     *
     * @return Tagastab soo numbri
     */
    public int getGenderInt() {
        return Integer.parseInt(personalcode.substring(0, 1));
    }

    /**
     * Soo täht
     *
     * @return Tagastab soo tähe
     */
    public String getGenderLetter() {
        String genderLetter = "";
        if (getGenderInt() == 1 || getGenderInt() == 3 || getGenderInt() == 5) {
            genderLetter = "M";
        } else if (getGenderInt() == 2 || getGenderInt() == 4 || getGenderInt() == 6) {
            genderLetter = "N";
        }
        return genderLetter;
    }

    /**
     * Soo sõna
     *
     * @return Tagastab soo sõna
     */
    public String getGenderWord() {
        String genderWord = "";
        if (getGenderInt() == 1 || getGenderInt() == 3 || getGenderInt() == 5) {
            genderWord = "Mees";
        } else if (getGenderInt() == 2 || getGenderInt() == 4 || getGenderInt() == 6) {
            genderWord = "Naine";
        }
        return genderWord;
    }

    /**
     * Kahekohaline aasta
     *
     * @return Tagastab kahekohalise aasta
     */
    public String getBirthYearShortNr() {
        return personalcode.substring(1, 3);
    }

    public int getBirthYearFull2() {
        return getBirthYearFull();
    }

    /**
     * Neljakohaline aasta
     *
     * @return Tagastab neljakohalise aasta
     */
    private int getBirthYearFull() {
        String firstHalf = "";
        switch (getGenderInt()) {
            case 1:
            case 2:
                firstHalf = "18";
                break;
            case 3:
            case 4:
                firstHalf = "19";
                break;
            case 5:
            case 6:
                firstHalf = "20";
                break;
            default:
                break;
        }
        int birthYearFull = Integer.parseInt(firstHalf + getBirthYearShortNr());
        return birthYearFull;
    }

    /**
     * Sünnikuu int
     *
     * @return Tagastab sünnikuu int tüüpi
     */
    public int getBirthMonthInt() {
        return Integer.parseInt(getBirthMonthNr());
    }

    /**
     * Sünnikuu number string
     *
     * @return Tagastab sünnikuu numbri string tüüpi
     */
    private String getBirthMonthNr() {
        return personalcode.substring(3, 5);
    }

    /**
     * Sünnikuu sõna
     *
     * @return Tagastab sünnikuu sõnana
     */
    public String getBirthMonthWord() {
        return months[getBirthMonthInt() - 1];
    }

    /**
     * Sünnipäev int
     *
     * @return Tagastab sünnipäeva int tüüpi
     */
    public int getBirthDayInt() {
        return Integer.parseInt(getBirthDayNr());
    }

    /**
     * Sünnipäev string
     *
     * @return Tagastab sünnipäeva string tüüpi
     */
    private String getBirthDayNr() {
        return personalcode.substring(5, 7);
    }

    /**
     * Sünnikuupäev
     *
     * @return tagastab sünnikuupäeva vormingus mm.dd.yyyy string tüüpi
     */
    public String getBirthDate() {
        String birthDate = getBirthDayNr() + "." + getBirthMonthNr() + "." + getBirthYearFull();
        return birthDate;
    }

    /**
     * Haigla number int
     *
     * @return Tagastab haigla numbri int tüüpi
     */
    public int getHospitalInt() {
        return Integer.parseInt(getHospitalNr());
    }

    /**
     * Haigla nimi
     *
     * @return Tagastab haigla nime
     */
    public String getHospitalName() {
        String hospitalName = "";
        if (getHospitalInt() >= 1 && getHospitalInt() <= 10) {
            hospitalName = "Kuressaare Haigla";
        } else if (getHospitalInt() >= 11 && getHospitalInt() <= 19) {
            hospitalName = "Tartu Ülikooli Naistekliinik, Tartumaa, Tartu";
        } else if (getHospitalInt() >= 21 && getHospitalInt() <= 220) {
            hospitalName = "Ida-Tallinna Keskhaigla, Pelgulinna sünnitusmaja, Hiiumaa, Keila, Rapla haigla, Loksa haigla";
        } else if (getHospitalInt() >= 221 && getHospitalInt() <= 270) {
            hospitalName = "Ida-Viru Keskhaigla (Kohtla-Järve, endine Jõhvi)";
        } else if (getHospitalInt() >= 271 && getHospitalInt() <= 370) {
            hospitalName = "Maarjamõisa Kliinikum (Tartu), Jõgeva Haigla";
        } else if (getHospitalInt() >= 371 && getHospitalInt() <= 420) {
            hospitalName = "Narva Haigla";
        } else if (getHospitalInt() >= 421 && getHospitalInt() <= 470) {
            hospitalName = "Pärnu Haigla";
        } else if (getHospitalInt() >= 471 && getHospitalInt() <= 490) {
            hospitalName = "Pelgulinna Sünnitusmaja (Tallinn), Haapsalu haigla";
        } else if (getHospitalInt() >= 491 && getHospitalInt() <= 520) {
            hospitalName = "Järvamaa Haigla (Paide)";
        } else if (getHospitalInt() >= 521 && getHospitalInt() <= 570) {
            hospitalName = "Rakvere, Tapa haigla";
        } else if (getHospitalInt() >= 571 && getHospitalInt() <= 600) {
            hospitalName = "Valga Haigla";
        } else if (getHospitalInt() >= 601 && getHospitalInt() <= 650) {
            hospitalName = "Viljandi Haigla";
        } else if (getHospitalInt() >= 651 && getHospitalInt() <= 710) {
            hospitalName = "Lõuna-Eesti Haigla (Võru), Põlva Haigla";
        } else if (getHospitalInt() >= 711) {
            hospitalName = "Välismaalane";
        }
        return hospitalName;
    }

    /**
     * Haigla number string
     *
     * @return Tagastab haigla numbri string tüüpi
     */
    public String getHospitalNr() {
        return personalcode.substring(7, 10);
    }

    /**
     * Kontrollnumber
     *
     * @return Tagastab kontrollnumbri
     */
    public int getControlInt() {
        return Integer.parseInt(personalcode.substring(10, 11));
    }

    /**
     * Vanus tänasel kuupäeval, täisaastates
     *
     * @return Tagastab vanuse
     */
    public int getAge() {
        LocalDate ld = getLocalDate();
        LocalDate currentDate = LocalDate.now();
        int age = 0;
        if ((ld != null) && (currentDate != null)) {
            age = Period.between(ld, currentDate).getYears();
            if (age < 0) {
                age = -1;
            }
        }
        return age;
    }

    /**
     * Sünnikuupäev vormingus yyyy-mm-dd
     *
     * @return Tagastab LocalDate kuupäeva
     */
    public LocalDate getLocalDate() {
        LocalDate ld = LocalDate.parse((getBirthYearFull() + "-" + getBirthMonthNr() + "-" + getBirthDayNr()));
        return ld;
    }

    private boolean isDateValid(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Kontrollnumbri kontroll
     *
     * @return Tagastab boolean tüüpi väärtuse
     */
    private boolean isPersonalCodeValid(String personalcode) {
        String[] parts = personalcode.split("(?!^)");
        boolean result = false;
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
        if (Integer.parseInt(parts[10]) == modular) {
            result = true;
        }
        return result;
    }

    /**
     * String tüüpi numbri kontroll
     *
     * @param str string tüüpi number
     * @return Tagastab boolean tüüpi väärtuse
     */
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
