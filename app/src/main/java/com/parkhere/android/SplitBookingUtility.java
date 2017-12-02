package com.parkhere.android;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    Map<String, Integer> TwentyFourHourTimeMap = new HashMap<String, Integer>();
    Map<Integer, String> ReverseTwentyFourHourTimeMap = new HashMap<Integer, String>();

    /**
     *     String[] times = {"12AM", "1AM", "2AM", "3AM", "4AM", "5AM", "6AM", "7AM", "8AM", "9AM", "10AM", "11AM", "12PM", "1PM", "2PM",
     "3PM", "4PM", "5PM", "6PM", "7PM", "8PM", "9PM", "10PM", "11PM" };
     */

    public SplitBookingUtility() {
        TwentyFourHourTimeMap.put("12AM", 0);
        TwentyFourHourTimeMap.put("1AM", 1);
        TwentyFourHourTimeMap.put("2AM", 2);
        TwentyFourHourTimeMap.put("3AM", 3);
        TwentyFourHourTimeMap.put("4AM", 4);
        TwentyFourHourTimeMap.put("5AM", 5);
        TwentyFourHourTimeMap.put("6AM", 6);
        TwentyFourHourTimeMap.put("7AM", 7);
        TwentyFourHourTimeMap.put("8AM", 8);
        TwentyFourHourTimeMap.put("9AM", 9);
        TwentyFourHourTimeMap.put("10AM", 10);
        TwentyFourHourTimeMap.put("11AM", 11);
        TwentyFourHourTimeMap.put("12PM", 12);
        TwentyFourHourTimeMap.put("1PM", 13);
        TwentyFourHourTimeMap.put("2PM", 14);
        TwentyFourHourTimeMap.put("3PM", 15);
        TwentyFourHourTimeMap.put("4PM", 16);
        TwentyFourHourTimeMap.put("5PM", 17);
        TwentyFourHourTimeMap.put("6PM", 18);
        TwentyFourHourTimeMap.put("7PM", 19);
        TwentyFourHourTimeMap.put("8PM", 20);
        TwentyFourHourTimeMap.put("9PM", 21);
        TwentyFourHourTimeMap.put("10PM", 22);
        TwentyFourHourTimeMap.put("11PM", 23);

        ReverseTwentyFourHourTimeMap.put(0, "12AM");
        ReverseTwentyFourHourTimeMap.put(1, "1AM");
        ReverseTwentyFourHourTimeMap.put(2 , "2AM");
        ReverseTwentyFourHourTimeMap.put(3, "3AM");
        ReverseTwentyFourHourTimeMap.put(4, "4AM");
        ReverseTwentyFourHourTimeMap.put(5, "5AM");
        ReverseTwentyFourHourTimeMap.put(6, "6AM");
        ReverseTwentyFourHourTimeMap.put(7, "7AM");
        ReverseTwentyFourHourTimeMap.put(8, "8AM");
        ReverseTwentyFourHourTimeMap.put(9, "9AM");
        ReverseTwentyFourHourTimeMap.put(10,"10AM");
        ReverseTwentyFourHourTimeMap.put(11,"11AM");
        ReverseTwentyFourHourTimeMap.put(12,"12PM");
        ReverseTwentyFourHourTimeMap.put(13, "1PM");
        ReverseTwentyFourHourTimeMap.put(14,"2PM");
        ReverseTwentyFourHourTimeMap.put(15,"3PM");
        ReverseTwentyFourHourTimeMap.put(16,"4PM");
        ReverseTwentyFourHourTimeMap.put(17,"5PM");
        ReverseTwentyFourHourTimeMap.put(18,"6PM");
        ReverseTwentyFourHourTimeMap.put(19,"7PM");
        ReverseTwentyFourHourTimeMap.put(20,"8PM");
        ReverseTwentyFourHourTimeMap.put(21,"9PM");
        ReverseTwentyFourHourTimeMap.put(22,"10PM");
        ReverseTwentyFourHourTimeMap.put(23,"11PM");


    }

    public ArrayList<Listing> noSplitTime(String originalStartTime, String originalEndTime, String chosenStartTime, String chosenEndTime) {
        ArrayList<Listing> splitListings = new ArrayList<>();

        Listing listing = new Listing();
        listing.startTime = chosenStartTime;
        listing.endTime = chosenEndTime;

        splitListings.add(listing);

        return splitListings;
    }

    public ArrayList<Listing> singleSplitTime(String originalStartTime, String originalEndTime, String chosenStartTime, String chosenEndTime) {
        ArrayList<Listing> splitListings = new ArrayList<>();

        if (originalStartTime.equals(chosenStartTime)) {
            System.out.println("Matching Start Times");
            Listing listing1 = new Listing();
            listing1.startTime = chosenStartTime;
            listing1.endTime = chosenEndTime;
            listing1.renterID = "flag";
            splitListings.add(listing1);
            Listing listing2 = new Listing();
            listing2.startTime = chosenEndTime;
            listing2.endTime = originalEndTime;
            listing2.renterID = "";
            splitListings.add(listing2);
        }
        else if (originalEndTime.equals(chosenEndTime)) {
            System.out.println("Matching End Times");
            Listing listing1 = new Listing();
            listing1.startTime = originalStartTime;
            listing1.endTime = chosenStartTime;
            listing1.renterID = "";
            splitListings.add(listing1);
            Listing listing2 = new Listing();
            listing2.startTime = chosenStartTime;
            listing2.endTime = originalEndTime;
            listing2.renterID = "flag";
            splitListings.add(listing2);
        }

        return splitListings;
    }

    public ArrayList<Listing> doubleSplitTime(String originalStartTime, String originalEndTime, String chosenStartTime, String chosenEndTime) {
        ArrayList<Listing> splitListings = new ArrayList<>();

        int chosenStartTimeInt = TwentyFourHourTimeMap.get(chosenStartTime);
        int originalStartTimeInt = TwentyFourHourTimeMap.get(originalStartTime);
        int firstHalf = Math.abs(chosenStartTimeInt - originalStartTimeInt);

        Listing listing1 = new Listing();
        listing1.startTime = originalStartTime;
        originalStartTimeInt = (originalStartTimeInt + firstHalf) % 24;
        listing1.endTime = ReverseTwentyFourHourTimeMap.get(originalStartTimeInt);
        listing1.renterID = "";
        splitListings.add(listing1);

        Listing listing2 = new Listing();
        listing2.startTime = chosenStartTime;
        listing2.endTime = chosenEndTime;
        listing2.renterID = "flag";
        splitListings.add(listing2);

        Listing listing3 = new Listing();
        listing3.startTime = chosenEndTime;
        listing3.endTime = originalEndTime;
        listing3.renterID = "";
        splitListings.add(listing3);

        return splitListings;
    }


    public ArrayList<Listing> noSplitDate(String originalStartDate, String originalEndDate, String chosenStartDate, String chosenEndDate) throws Exception {
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

        calChosenStartDate.add(DATE, -calOriginalStartDate.get(DATE));
        int firstHalf = calChosenStartDate.get(DATE) - 1;
        //to undo what the first two lines did
        calChosenStartDate.add(DATE, calOriginalStartDate.get(DATE));

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

        if (calChosenStartDate.get(DATE) == calOriginalStartDate.get(DATE)) {
            calOriginalEndDate.add(DATE, -calChosenEndDate.get(DATE));
            if (calOriginalEndDate.get(DATE) > 0)
                return true;
        }
        else if (calChosenEndDate.get(DATE) == calOriginalEndDate.get(DATE)) {
            calChosenStartDate.add(DATE, -calOriginalStartDate.get(DATE));
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

        calChosenStartDate.add(DATE, -calOriginalStartDate.get(DATE));

        calOriginalEndDate.add(DATE, -calChosenEndDate.get(DATE));

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

    public boolean checkOneHourTime(String originalStartTime, String originalEndTime) {
        String startTimeString = originalStartTime;
        String endTimeString = originalEndTime;
        int startTimeInt;
        int endTimeInt;
        if (startTimeString.substring(0,2).equals("10") || startTimeString.substring(0,2).equals("11") || startTimeString.substring(0,2).equals("12"))
            startTimeInt = Integer.parseInt(startTimeString.substring(0,2));
        else startTimeInt = Integer.parseInt(startTimeString.substring(0,1));

        if (endTimeString.substring(0,2).equals("10") || endTimeString.substring(0,2).equals("11") || endTimeString.substring(0,2).equals("12"))
            endTimeInt = Integer.parseInt(endTimeString.substring(0,2));
        else endTimeInt = Integer.parseInt(endTimeString.substring(0,1));

        String amPmStart = startTimeString.substring(startTimeString.length() - 2, startTimeString.length());
        String amPmEnd = endTimeString.substring(endTimeString.length() - 2, endTimeString.length());

        String startTime = startTimeInt + amPmStart;
        String endTime = endTimeInt + amPmEnd;

        int originalStartTimeInt = TwentyFourHourTimeMap.get(startTime);
        int originalEndTimeInt = TwentyFourHourTimeMap.get(endTime);
        if (Math.abs(originalEndTimeInt - originalStartTimeInt) == 1 || (originalStartTime.equals("11PM") && originalEndTime.equals("12AM")))
            return true;
        return false;
    }

    public boolean checkNoSplitTime(String originalStartTime, String originalEndTime, String chosenStartTime, String chosenEndTime) {
        if (originalStartTime.equals(chosenStartTime) && originalEndTime.equals(chosenEndTime) || checkOneHourTime(originalStartTime, originalEndTime))
            return true;
        return false;
    }

    public boolean checkSingleSplitTime(String originalStartTime, String originalEndTime, String chosenStartTime, String chosenEndTime) {
        if (originalStartTime.equals(chosenStartTime)) {
            int originalEndTimeInt = TwentyFourHourTimeMap.get(originalEndTime);
            int chosenEndTimeInt = TwentyFourHourTimeMap.get(chosenEndTime);
            if (Math.abs(originalEndTimeInt - chosenEndTimeInt) > 0)
                return true;
        }
        else if (originalEndTime.equals(chosenEndTime)) {
            int originalStartTimeInt = TwentyFourHourTimeMap.get(originalStartTime);
            int chosenStartTimeInt = TwentyFourHourTimeMap.get(chosenStartTime);
            if (Math.abs(chosenStartTimeInt - originalStartTimeInt) > 0)
                return true;
        }
        return false;
    }

    public boolean checkDoubleSplitTime(String originalStartTime, String originalEndTime, String chosenStartTime, String chosenEndTime) {
        int originalEndTimeInt = TwentyFourHourTimeMap.get(originalEndTime);
        int chosenEndTimeInt = TwentyFourHourTimeMap.get(chosenEndTime);
        int originalStartTimeInt = TwentyFourHourTimeMap.get(originalStartTime);
        int chosenStartTimeInt = TwentyFourHourTimeMap.get(chosenStartTime);

        int startTimeDifference = Math.abs(chosenStartTimeInt - originalStartTimeInt);
        int endTimeDifference = Math.abs(originalEndTimeInt - chosenEndTimeInt);

        if (checkNoSplitTime(originalStartTime, originalEndTime, chosenStartTime, chosenEndTime)) {
            return false;
        }
        else if (checkSingleSplitTime(originalStartTime, originalEndTime, chosenStartTime, chosenEndTime)) {
            return false;
        }
        else if (startTimeDifference > 0 && endTimeDifference > 0) {
            return true;
        }
        else return false;

    }

    private String decrementTime(String time) {
        System.out.println("DEC TIME : " + time);
        int timeInt;
        if (time.substring(0,2).equals("10") || time.substring(0,2).equals("11") || time.substring(0,2).equals("12"))
            timeInt = Integer.parseInt(time.substring(0,2));
        else timeInt = Integer.parseInt(time.substring(0,1));
        timeInt = timeInt - 1;
        String endPart = time.substring(time.length() - 2, time.length());
        String timeString = timeInt + endPart;
        return timeString;
    }

    private String incrementTime(String time) {
        System.out.println("INC TIME : " + time);
        int timeInt;
        if (time.substring(0,2).equals("10") || time.substring(0,2).equals("11") || time.substring(0,2).equals("12"))
            timeInt = Integer.parseInt(time.substring(0,2));
        else timeInt = Integer.parseInt(time.substring(0,1));
        timeInt = timeInt + 1;
        String endPart = time.substring(time.length() - 2, time.length());
        String timeString = timeInt + endPart;
        return timeString;
    }
}
