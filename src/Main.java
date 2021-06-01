import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/*

Skal sende info til HTTP REST endpoint
    - getTemperature skal lese fra filen, men bare en linje hver 100ms
    - Applikasjonen skal kunne kalkulerere min, max, og average temperatur på perioder på 2 min
    - Hvert 2 minutt skal applikasjonen sende de lagrede verdiene til HTTP REST API:
        http://localhost:5000/api/temperature
    - Verdiene skal bli sendt i JSON format med følgende format
            // TemperatureMeasurement
        {
            "time": {
                "start": string, // Start date and time in ISO8601 format for the measurement
                "end": string // End date and time in ISO8601 format for the measurement
            },
            "min": number, // Minimum observed temperature
            "max": number, // Maximum observed temperature
            "average": number // Average temperature
        }
 */
public class Main {
    public static void main(String[] args) {

        File temperatures = new File("temperature.txt");
        double values = getTemperature(new File(String.valueOf(temperatures)));

    }

    public static double getTemperature(File fileName) {
        // Metoden leser innholdet i filen linje for linje
        // Thread.sleep(100); gjør at programmet venter 100 ms før det kjører videre

        double valueRead = 0;
        double bitRes = 4096;
        double Vref = 3300;
        double stepsize = Vref/bitRes;
        double stegMinMax = 100;
        double mVprsteg = Vref / stegMinMax;
        double voltIn = 0;
        double temp = 0;
        int count = 0;
        ArrayList<Double> values = new ArrayList<Double>();

        try {
            FileReader readTemp = new FileReader(fileName);
            Scanner line = new Scanner(readTemp);

            // Mens det finnes en linje, og den inneholder noe, så skal linjen bli konvertert
            while (line.hasNextDouble() && line.hasNext() ) {
                // legger veriden i linjen i en variabel
                valueRead = line.nextDouble();
                // Finner LSB i stepsize (3300 / 4096), ganger denne med verdien som er lest for å finne
                // volten tilhørende verdien.
                voltIn = valueRead * (stepsize);
                // Finner temperaturen ved å bruke voltIn og dele denne på Vref/stegMinMax
                temp = (voltIn/ mVprsteg)-50;

                // System.out.println(temp);
                Thread.sleep(100);
                count++;
                values.add(temp);
                //System.out.println(values);

                if (count == 1200){
                    double min = Collections.min(values);
                    double max = Collections.max(values);

                    System.out.println(
                            "----------" +
                            "\nMinimumverdi: " + min +
                            "\nMaksimumverdi: " + max +
                            "\nGjennomsnitt: " + average(values) +
                            "\n----------");
                    count = 0;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return valueRead;
    }

    public static double average(ArrayList<Double> getTempValues) {
        double total = 0;
        if (!getTempValues.isEmpty()) {
            for (double i : getTempValues) {
                total += i;
            }
            return total / getTempValues.size();
        }
        return total;
    }
}









