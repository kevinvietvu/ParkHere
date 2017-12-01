package com.parkhere.android;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created by Kevin on 11/27/2017.
 */

public class SplitBookingUtility {

    Date parsedOriginalStartDate;
    Date parsedOriginalEndDate;
    Date parsedChosenStartDate;
    Date parsedChosenEndDate;
    Calendar calOriginalStartDate = Calendar.getInstance();
    Calendar calOriginalEndDate = Calendar.getInstance();
    Calendar calChosenStartDate = Calendar.getInstance();
    Calendar calChosenEndDate = Calendar.getInstance();
    DateFormat df = new SimpleDateFormat("MM-dd-yyyy");

    public SplitBookingUtility() {

    }

    public ArrayList<Listing> NoSplitDate(String originalStartDate, String originalEndDate, String chosenStartDate, String chosenEndDate) throws Exception {
        ArrayList<Listing> splitListings = new ArrayList<>();

        parsedOriginalStartDate = df.parse(originalStartDate);
        parsedChosenStartDate = df.parse(chosenStartDate);
        parsedChosenEndDate = df.parse(chosenEndDate);
        parsedOriginalEndDate = df.parse(originalEndDate);

        calOriginalStartDate.setTime(parsedOriginalStartDate);
        calOriginalEndDate.setTime(parsedOriginalEndDate);
        calChosenStartDate.setTime(parsedChosenStartDate);
        calChosenEndDate.setTime(parsedChosenEndDate);

        Listing listing = new Listing();
        listing.startDate = chosenStartDate;
        listing.endDate = chosenEndDate;

        splitListings.add(listing);

        return splitListings;
    }

    public ArrayList<Listing> singleSplitDate(String originalStartDate, String originalEndDate, String chosenStartDate, String chosenEndDate) throws Exception {
        ArrayList<Listing> splitListings = new ArrayList<>();

        calOriginalStartDate = Calendar.getInstance();
        calOriginalEndDate = Calendar.getInstance();
        calChosenStartDate = Calendar.getInstance();
        calChosenEndDate = Calendar.getInstance();

        parsedOriginalStartDate = df.parse(originalStartDate);
        parsedChosenStartDate = df.parse(chosenStartDate);
        parsedChosenEndDate = df.parse(chosenEndDate);
        parsedOriginalEndDate = df.parse(originalEndDate);

        calOriginalStartDate.setTime(parsedOriginalStartDate);
        calOriginalEndDate.setTime(parsedOriginalEndDate);
        calChosenStartDate.setTime(parsedChosenStartDate);
        calChosenEndDate.setTime(parsedChosenEndDate);

        //calChosenStartDate.get(MONTH) + "-" + calChosenStartDate.get(DATE) + "-" + calChosenStartDate.get(YEAR)


        if (calChosenStartDate.get(DATE) == calOriginalStartDate.get(DATE)) {
            System.out.println("Matching Start Dates");
            Listing listing1 = new Listing();
            listing1.startDate = chosenStartDate;
            listing1.endDate = chosenEndDate;
            listing1.renterID = "flag";
            splitListings.add(listing1);
            calChosenEndDate.add(DATE, 1);
            String month = String.format("%02d", calChosenEndDate.get(MONTH) + 1);
            String day = String.format("%02d", calChosenEndDate.get(DATE));
            Listing listing2 = new Listing();
            System.out.println(calChosenEndDate.getTime().getMonth());
            listing2.startDate = month + "-" + day + "-" + calChosenEndDate.get(YEAR);
            listing2.endDate = originalEndDate;
            listing2.renterID = "";
            splitListings.add(listing2);
        }
        else if (calChosenEndDate.get(DATE) == calOriginalEndDate.get(DATE)) {
            System.out.println("Matching End Dates");
            Listing listing1 = new Listing();
            listing1.startDate = originalStartDate;
            calChosenStartDate.add(DATE, -1);
            String month = String.format("%02d", calChosenStartDate.get(MONTH) + 1);
            String day = String.format("%02d", calChosenStartDate.get(DATE));
            listing1.endDate = month + "-" + day + "-" + calChosenStartDate.get(YEAR);
            listing1.renterID = "";
            splitListings.add(listing1);
            calChosenStartDate.add(DATE, 1);
            String month2 = String.format("%02d", calChosenStartDate.get(MONTH) + 1);
            String day2 = String.format("%02d", calChosenStartDate.get(DATE));
            Listing listing2 = new Listing();
            listing2.startDate = month2 + "-" + day2 + "-" + calChosenStartDate.get(YEAR);
            listing2.endDate = originalEndDate;
            listing2.renterID = "flag";
            splitListings.add(listing2);
        }

        return splitListings;
    }

    public ArrayList<Listing> doubleSplitDate(String originalStartDate, String originalEndDate, String chosenStartDate, String chosenEndDate) throws Exception {
        ArrayList<Listing> splitListings = new ArrayList<>();

        parsedOriginalStartDate = df.parse(originalStartDate);
        parsedChosenStartDate = df.parse(chosenStartDate);
        parsedChosenEndDate = df.parse(chosenEndDate);
        parsedOriginalEndDate = df.parse(originalEndDate);

        calOriginalStartDate.setTime(parsedOriginalStartDate);
        calOriginalEndDate.setTime(parsedOriginalEndDate);
        calChosenStartDate.setTime(parsedChosenStartDate);
        calChosenEndDate.setTime(parsedChosenEndDate);

        /**
         System.out.println("TEST START DATE OG : " + calOriginalStartDate.get(DATE));
         System.out.println("TEST END DATE OG : " + calOriginalEndDate.get(DATE));
         System.out.println("TEST START DATE CHO : " + calChosenStartDate.get(DATE));
         System.out.println("TEST END DATE CHO : " + calChosenEndDate.get(DATE)); */

        /**
         String month = String.format("%02d", m);
         String day = String.format("%02d", d);
         String year = String.format("%02d", y);
         */

        calChosenStartDate.add(DATE, -calOriginalStartDate.get(DATE));
        int firstHalf = calChosenStartDate.get(DATE) - 1;
        //to undo what the first two lines did
        calChosenStartDate.add(DATE, calOriginalStartDate.get(DATE));
        //System.out.println("CHECK LAST PART : " + calOriginalEndDate.get(DATE));

        Listing listing1 = new Listing();
        listing1.startDate = originalStartDate;
        calOriginalStartDate.add(DATE,  firstHalf);
        String month = String.format("%02d", calOriginalStartDate.get(MONTH) + 1);
        String day = String.format("%02d", calOriginalStartDate.get(DATE));
        listing1.endDate = month + "-" + day + "-" + calOriginalStartDate.get(YEAR);
        listing1.renterID = "";
        splitListings.add(listing1);

        Listing listing2 = new Listing();
        String month2 = String.format("%02d", calChosenStartDate.get(MONTH) + 1);
        String day2 = String.format("%02d", calChosenStartDate.get(DATE));
        listing2.startDate = month2 + "-" + day2 + "-" + calChosenStartDate.get(YEAR);
        month2 = String.format("%02d", calChosenEndDate.get(MONTH) + 1);
        day2 = String.format("%02d", calChosenEndDate.get(DATE));
        listing2.endDate = month2 + "-" + day2 + "-" + calChosenEndDate.get(YEAR);
        listing2.renterID = "flag";
        splitListings.add(listing2);

        calChosenEndDate.add(DATE, 1);
        Listing listing3 = new Listing();
        String month3 = String.format("%02d", calChosenEndDate.get(MONTH) + 1);
        String day3 = String.format("%02d", calChosenEndDate.get(DATE));
        listing3.startDate = month3 + "-" + day3 + "-" + calChosenEndDate.get(YEAR);
        listing3.endDate = originalEndDate;
        listing3.renterID = "";
        splitListings.add(listing3);

        return splitListings;
    }

    public boolean checkNoSplitDate(String originalStartDate, String originalEndDate, String chosenStartDate, String chosenEndDate) throws Exception {

        parsedOriginalStartDate = df.parse(originalStartDate);
        parsedChosenStartDate = df.parse(chosenStartDate);
        parsedChosenEndDate = df.parse(chosenEndDate);
        parsedOriginalEndDate = df.parse(originalEndDate);

        calOriginalStartDate.setTime(parsedOriginalStartDate);
        calOriginalEndDate.setTime(parsedOriginalEndDate);
        calChosenStartDate.setTime(parsedChosenStartDate);
        calChosenEndDate.setTime(parsedChosenEndDate);
    /**
        System.out.println("TEST START DATE OG : " + calOriginalStartDate.get(DATE));
        System.out.println("TEST END DATE OG : " + calOriginalEndDate.get(DATE));
        System.out.println("TEST START DATE CHO : " + calChosenStartDate.get(DATE));
        System.out.println("TEST END DATE CHO : " + calChosenEndDate.get(DATE)); */

        //System.out.println("CAL STRING " + calChosenStartDate.get(MONTH) + "-" + calChosenStartDate.get(DATE) + "-" + calChosenStartDate.get(YEAR));

        if (calChosenEndDate.get(DATE) == calOriginalEndDate.get(DATE) && calChosenStartDate.get(DATE) == calOriginalStartDate.get(DATE)) {
            return true;
        }
        return false;
    }

    public boolean checkSingleSplitDate(String originalStartDate, String originalEndDate, String chosenStartDate, String chosenEndDate)throws Exception {

        parsedOriginalStartDate = df.parse(originalStartDate);
        parsedChosenStartDate = df.parse(chosenStartDate);
        parsedChosenEndDate = df.parse(chosenEndDate);
        parsedOriginalEndDate = df.parse(originalEndDate);

        calOriginalStartDate.setTime(parsedOriginalStartDate);
        calOriginalEndDate.setTime(parsedOriginalEndDate);
        calChosenStartDate.setTime(parsedChosenStartDate);
        calChosenEndDate.setTime(parsedChosenEndDate);
    /**
        System.out.println("TEST START DATE OG : " + calOriginalStartDate.get(DATE));
        System.out.println("TEST END DATE OG : " + calOriginalEndDate.get(DATE));
        System.out.println("TEST START DATE CHO : " + calChosenStartDate.get(DATE));
        System.out.println("TEST END DATE CHO : " + calChosenEndDate.get(DATE)); */

        if (calChosenStartDate.get(DATE) == calOriginalStartDate.get(DATE)) {
            calOriginalEndDate.add(DATE, -calChosenEndDate.get(DATE));
            //System.out.println("CHECK FIRST PART : " + calChosenEndDate.get(DATE));
            if (calOriginalEndDate.get(DATE) > 0)
                return true;
        }
        else if (calChosenEndDate.get(DATE) == calOriginalEndDate.get(DATE)) {
            calChosenStartDate.add(DATE, -calOriginalStartDate.get(DATE));
            //System.out.println("CHECK LAST PART : " + calOriginalStartDate.get(DATE));
            if (calChosenStartDate.get(DATE) > 0)
                return true;
        }

        return false;
    }

    public boolean checkDoubleSplitDate(String originalStartDate, String originalEndDate, String chosenStartDate, String chosenEndDate) throws Exception {

        parsedOriginalStartDate = df.parse(originalStartDate);
        parsedChosenStartDate = df.parse(chosenStartDate);
        parsedChosenEndDate = df.parse(chosenEndDate);
        parsedOriginalEndDate = df.parse(originalEndDate);

        calOriginalStartDate.setTime(parsedOriginalStartDate);
        calOriginalEndDate.setTime(parsedOriginalEndDate);
        calChosenStartDate.setTime(parsedChosenStartDate);
        calChosenEndDate.setTime(parsedChosenEndDate);

        /**
        System.out.println("TEST START DATE OG : " + calOriginalStartDate.get(DATE));
        System.out.println("TEST END DATE OG : " + calOriginalEndDate.get(DATE));
        System.out.println("TEST START DATE CHO : " + calChosenStartDate.get(DATE));
        System.out.println("TEST END DATE CHO : " + calChosenEndDate.get(DATE)); */

        calChosenStartDate.add(DATE, -calOriginalStartDate.get(DATE));
        //System.out.println("CHECK FIRST PART : " + calChosenStartDate.get(DATE));

        calOriginalEndDate.add(DATE, -calChosenEndDate.get(DATE));
        //System.out.println("CHECK LAST PART : " + calOriginalEndDate.get(DATE));

        if (checkNoSplitDate(originalStartDate, originalEndDate, chosenStartDate, chosenEndDate)) {
            return false;
        }
        else if (checkSingleSplitDate(originalStartDate, originalEndDate, chosenStartDate, chosenEndDate)) {
            return false;
        }
        else if (calChosenStartDate.get(DATE) > 0 && calOriginalEndDate.get(DATE) > 0) {
            return true;
        }
        return false;
    }
}
