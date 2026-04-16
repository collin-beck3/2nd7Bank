
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

class Timecard {
    String Name;
    double HoursWorked;
    double HourlyRate;

    public Timecard(String name, double hoursWorked, double hourlyRate) {
        this.Name = name;
        this.HoursWorked = hoursWorked;
        this.HourlyRate = hourlyRate;
    }
 
    public String payrollString() {
        // // do the math
        double tax_rate = 0.2;
        double gross_pay = this.HourlyRate * this.HoursWorked;
        double taxes = gross_pay * tax_rate;
        double net_pay = gross_pay - taxes;
        // produce the output string
        return String.format("%s, %.2f, %.2f, %.2f", this.Name, gross_pay, taxes, net_pay);
        //return "";
    }

    public Paycheck payrollPaycheck() {
        double tax_rate = 0.2;
        double gross_pay = this.HourlyRate * this.HoursWorked;
        double taxes = gross_pay * tax_rate;
        double net_pay = gross_pay - taxes;
        return new Paycheck(this.Name, gross_pay, taxes, net_pay);
    }

   public String toString() {
        return String.format("%s, %.1f, %.1f", this.Name,
        this.HoursWorked, this.HourlyRate);
    }
}

class Paycheck {
    String Name;
    double GrossPay;
    double Taxes;
    double NetPay;

    public Paycheck(String name, double grossPay, double taxes, double netPay) {
        Name = name;
        GrossPay = grossPay;
        Taxes = taxes;
        NetPay = netPay;
    }

    public String getName() {
        return Name;
    }
    public double getGrossPay() {
        return GrossPay;
    }
    public double getTaxes() {
        return Taxes;
    }
    public double getNetPay() {
        return NetPay;
    }
}

public class Payroll {
    public static ArrayList<Timecard> readData(String fileName) {
        Timecard t = null;
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            ArrayList<Timecard> data = new ArrayList<>();

            // Skip header line
            if (scanner.hasNextLine()) {
                //System.out.println("-");
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                String name = parts[0].trim();
                //System.out.printf("* %s\n", name);
                double hoursWorked = Double.parseDouble(parts[1].trim());
                //System.out.printf("* %f\n", hoursWorked);
                double hourlyRate = Double.parseDouble(parts[2].trim());
                //System.out.printf("* %f\n", hourlyRate);
                t = new Timecard(name, hoursWorked, hourlyRate);
                data.add(t);
            }
            scanner.close();
            return data;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return null;
        } catch (Exception e) {
            System.out.print("ERRORERROR ");
            System.out.println(e);
            return null;
        }
    }
}

// Start with the reading of data into a list
ArrayList<Timecard> input_data = Payroll.readData("halfmilemployees.csv");

String outp;
// print out the data in the list. See `toString()` method above.
double gpay;
double epay;
double npay;
Paycheck pc; //

for (Timecard t : input_data) {
    outp = t.payrollString();
    pc = t.payrollPaycheck();
    gpay += pc.getGrossPay();
    epay += pc.getTaxes();
    npay += pc.getNetPay();

    //System.out.println(outp);
}

System.out.printf("%,.2f, %,.2f %,.2f\n\n", gpay, epay, npay);