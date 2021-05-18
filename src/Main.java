import java.io.*;
import java.util.Timer;

/*
double getTemperature(); skal returnere temperatur fra en temperaturmåler


Lese temp fra en fil
Konvertere temperaturen på filen til celsius
    - temperaturen varierer fra -50 til +50 grader
    - 2048 er ca 0C
    - 3000 er ca 23C

Skal sende det til HTTP REST endpoint
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

        String temperaturTxt = "temperature.txt";
        readFile(new File(temperaturTxt));


    }

    // Metoden leser innholdet i filen linje for linje
    // Thread.sleep(100); gjør at programmet venter 100 ms før det kjører videre
    public static void readFile(File filnavn){

            try {
                File temperature = new File("temperature.txt");
                FileReader readTemp = new FileReader(temperature);
                BufferedReader br = new BufferedReader(readTemp);

                String line;

                while ((line = br.readLine()) != null){
                    System.out.println(line);
                    Thread.sleep(100);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        // Lest ferdig
    }



}




