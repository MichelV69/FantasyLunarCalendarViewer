package com.pe902_gaming.CLILunarCalendar;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class App 
{
  public static void main( String[] args )
  {
    ReadCalendarProperties CalPropFile = new ReadCalendarProperties();
    if (args.length > 0 ) 
    {
       CalPropFile.setPropertiesFileName(args[0]);
       System.out.println("\n----\nLooking to load Calendar configuration from [" + CalPropFile.getPropertiesFileName() + "]" );
       if (CalPropFile.FileExists()) 
       {
          CalPropFile.LoadFromFile();
          System.out.println("... should be loaded now ... ");
       }
    }

    // --- initial vars and objects
    String UserTextInput;
    Scanner ReadUserInput = new Scanner (System.in);
    LunarCalendar OurCalendar = new LunarCalendar();

    // --- Start getting data for the Calendar
    //CalPropFile.CalProp.getProperty("CalendarYearInDays")

    if ((CalPropFile.getFileDidLoadOK()) && (Double.parseDouble(CalPropFile.CalProp.getProperty("CalendarYearInDays")) > 0)) 
    {
      // we got calendar config from file load it up
      OurCalendar.setCalendarYearInDays(Double.parseDouble(CalPropFile.CalProp.getProperty("CalendarYearInDays")));
      OurCalendar.setYearZero(Integer.parseInt(CalPropFile.CalProp.getProperty("YearZero")));
      OurCalendar.setDayZero(Integer.parseInt(CalPropFile.CalProp.getProperty("DayZero")));

      OurCalendar.setObject1Name(CalPropFile.CalProp.getProperty("Object1Name"));
      OurCalendar.setObject1PeriodInDays(Double.parseDouble(CalPropFile.CalProp.getProperty("Object1PeriodInDays")));
      OurCalendar.setObject2Name(CalPropFile.CalProp.getProperty("Object2Name"));
      OurCalendar.setObject2PeriodInDays(Double.parseDouble(CalPropFile.CalProp.getProperty("Object2PeriodInDays")));
      OurCalendar.setObject3Name(CalPropFile.CalProp.getProperty("Object3Name"));
      OurCalendar.setObject3PeriodInDays(Double.parseDouble(CalPropFile.CalProp.getProperty("Object3PeriodInDays")));

    }
    else
    {
      // get the info interactive from the user

      System.out.println("\n----\nCalendar Geometry");
      System.out.println("CalendarYearInDays?");
      UserTextInput = ReadUserInput.nextLine();
      OurCalendar.setCalendarYearInDays(Double.parseDouble(UserTextInput));

      System.out.println("YearZero?");
      UserTextInput = ReadUserInput.nextLine();
      OurCalendar.setYearZero(Integer.parseInt(UserTextInput));

      System.out.println("DayZero?");
      UserTextInput = ReadUserInput.nextLine();
      OurCalendar.setDayZero(Integer.parseInt(UserTextInput));

      System.out.println("\n----\nOrbital Periods");
      System.out.println("Object1Name?");
      UserTextInput = ReadUserInput.nextLine();
      if (UserTextInput.isEmpty()) 
      {
        UserTextInput = OurCalendar.ORBIT_IS_EMPTY;
      }
      OurCalendar.setObject1Name(UserTextInput);

      if (OurCalendar.getObject1Name() != OurCalendar.ORBIT_IS_EMPTY) 
      {
        System.out.println("Object1PeriodInDays?");
        UserTextInput = ReadUserInput.nextLine();
        if (UserTextInput.isEmpty()) 
        {
          UserTextInput = "0.0";
        }        
        OurCalendar.setObject1PeriodInDays(Double.parseDouble(UserTextInput));      
      }

      System.out.println("Object2Name?");
      UserTextInput = ReadUserInput.nextLine();
      if (UserTextInput.isEmpty()) 
      {
        UserTextInput = OurCalendar.ORBIT_IS_EMPTY;
      }
      OurCalendar.setObject2Name(UserTextInput);

      if (OurCalendar.getObject2Name() != OurCalendar.ORBIT_IS_EMPTY) 
      {
        System.out.println("Object2PeriodInDays?");
        UserTextInput = ReadUserInput.nextLine();
        if (UserTextInput.isEmpty()) 
        {
          UserTextInput = "0.0";
        }   
        OurCalendar.setObject2PeriodInDays(Double.parseDouble(UserTextInput));      
      }

      System.out.println("Object3Name?");
      UserTextInput = ReadUserInput.nextLine();
      if (UserTextInput.isEmpty()) 
      {
        UserTextInput = OurCalendar.ORBIT_IS_EMPTY;
      }
      OurCalendar.setObject3Name(UserTextInput);

      if (OurCalendar.getObject3Name() != OurCalendar.ORBIT_IS_EMPTY) 
      {
        System.out.println("Object3PeriodInDays?");
        UserTextInput = ReadUserInput.nextLine();
        if (UserTextInput.isEmpty()) 
        {
          UserTextInput = "0.0";
        }
        OurCalendar.setObject3PeriodInDays(Double.parseDouble(UserTextInput));
      }
    }

    // now interactive-get initial observation data
    Boolean DoAgain = true;
    Boolean CalendarSlide = false;
    int CalendarSlideStepDays = 0;
    do
    {  
      if (CalendarSlide) 
      {
        OurCalendar.setDateObservationDayOfYear(OurCalendar.getDateObservationDayOfYear() + CalendarSlideStepDays);
      } 
      else 
      {
        System.out.println("\n----\nDate of Observation");
        System.out.println("DateObservationYear?");
        UserTextInput = ReadUserInput.nextLine();
        OurCalendar.setDateObservationYear(Integer.parseInt(UserTextInput));

        System.out.println("DateObservationDayOfYear?");
        UserTextInput = ReadUserInput.nextLine();
        OurCalendar.setDateObservationDayOfYear(Integer.parseInt(UserTextInput));
      }

      // ---
      // --- show output
      System.out.println("\n----\nOutput");
      System.out.println(OurCalendar.toString());
      System.out.println(OurCalendar.AstroCalc());

      // --- wait for user to be done
      System.out.println("Press 'Q' to Quit\n");
      System.out.println("      'R' to Reverse the ObservationDayOfYear to " + ( OurCalendar.getDateObservationDayOfYear() - 1 ));
      System.out.println("      'A' to Advance the ObservationDayOfYear to " + ( OurCalendar.getDateObservationDayOfYear() + 1 ));
      System.out.println("          ... or any other key to pick another Observation Year/Day");
      UserTextInput = ReadUserInput.nextLine();

      CalendarSlide = false;
      if (UserTextInput.length() > 0) 
      {
        switch (UserTextInput.toUpperCase().charAt(0)) 
        {
          case 'Q':
            DoAgain = false;
          break;
          case 'R':
          case '-':
            CalendarSlideStepDays = -1;
            CalendarSlide = true;
          break;
          case 'A':
          case '+':
            CalendarSlideStepDays = 1;
            CalendarSlide = true;
          break;
        } // end switch
      }  // end if

    } while(DoAgain); 
  } // end method main
} // end class App
