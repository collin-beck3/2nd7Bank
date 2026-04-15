import java.io.File;             // lets you open files
import java.io.FileNotFoundException;   //handles missing files
import java.util.Scanner;         // reads file line by line
import java.util.ArrayList;      //stores a list of timecards 

//time card class to hold the data for each employee
class Timecard {
    String Name;
    double HoursWorked;
    double HourlyRate;

    // timecard constructor (filling out the card) 
    public Timecard(String name, double hoursWorked, double hourlyRate) {
        this.Name = name;
        this.HoursWorked = hoursWorked;
        this.HourlyRate = hourlyRate;
    }
 
    //calculates the paycheck/fields on timecard and produces a string for output
    public String payrollString() {
        // // do the math
        double tax_rate = 0.2;    // 20% tax rate
        double gross_pay = this.HourlyRate * this.HoursWorked;    //total earned
        double taxes = gross_pay * tax_rate;     //taxes taken out
        double net_pay = gross_pay - taxes;     //take home pay
        // produce the output string
        return String.format("%s, %.2f, %.2f, %.2f", this.Name, gross_pay, taxes, net_pay);
        //return "";
    }
     // quick summary of the card - just name, hours, and rate...NO MATH!!
   public String toString() {
        return String.format("%s, %.1f, %.1f", this.Name,
        this.HoursWorked, this.HourlyRate);
    }
}

// class to read the data from the file and produce a list of timecards
public class Payroll {
    public static ArrayList<Timecard> readData(String fileName) {
        Timecard t = null;
        try {
            File file = new File(fileName);    // open the file
            Scanner scanner = new Scanner(file);    //read it line by line
            ArrayList<Timecard> data = new ArrayList<>();   //create empty list

            // Skip header line/first line when reading the data (just labels not real data) IGNORE
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();    //read one line
                String[] parts = line.split(",");    //split by comma ", " into 3 parts ex. "John Doe, 40, 15.50" -> ["John Doe", " 40", " 15.50"]
                String name = parts[0].trim();            //first part = name
                double hoursWorked = Double.parseDouble(parts[1].trim());   //second = hours  double.parseDouble() converts the string to a number, trim() removes extra spaces
                double hourlyRate = Double.parseDouble(parts[2].trim());    //third = rate
                t = new Timecard(name, hoursWorked, hourlyRate);   //create a timecard with the data
                data.add(t);                                 //add the timecard to the list
            }
            scanner.close();
            return data;

            //error handling 
        } catch (FileNotFoundException e) {
            System.out.println("File not found"); //file missing
            return null;
        } catch (Exception e) {
            System.out.print("ERRORERROR ");    //something else broke
            System.out.println(e);
            return null;
        }
    }
}

// RUN THE PROGRAM Start with the reading of data into a list
ArrayList<Timecard> input_data = Payroll.readData("input.data");
System.out.println("Name, Gross Pay, Taxes, Net Pay");
String outp;
// print out the data in the list. See `toString()` method above.
for (Timecard t : input_data) {
    outp = t.payrollString();
    System.out.println(outp);

}

// The flow of the program is as follows:
// input.data file
//       ↓
// readData() opens and reads it
//       ↓
// Creates a Timecard for each person
//       ↓
// payrollString() calculates their pay
//       ↓
// Prints everyone's paycheck! 