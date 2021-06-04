import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException {

    /*
        // https://www.baeldung.com/httpurlconnection-post

            // URL
        URL url = new URL ("http://localhost:5000/api/temperature");
            // Åpner en connection
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            //  Sier at requesten er av typen POST
        connection.setRequestMethod("POST");
            // Sier at det er json som skal postes
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
            // Sier at det skal aksepteres json
        connection.setRequestProperty("Accept", "application/json");
            // Gjør at man kan poste
        connection.setDoOutput(true);

        String jsonInputString = "{\"name\": \"Upendra\", \"job\": \"Programmer\"}";
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    */
            // Lagrer tekst-filen i en variabel
        File temperatures = new File("temperature.txt");
            // Kjører getTemperature metoden med filen som argument
        getTemperature(temperatures);

    }

    public static double getTemperature(File fileName) {
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
                // Leser innholdet i filen linje for linje
            FileReader readTemp = new FileReader(fileName);
            Scanner line = new Scanner(readTemp);

                // Mens det finnes en linje, og den inneholder noe, så skal linjen
                // bli konvertert til riktig temperatur
            while (line.hasNextDouble() && line.hasNext() ) {
                    // legger verdien i linjen i en variabel
                valueRead = line.nextDouble();
                    // Finner LSB i stepsize (3300 / 4096), ganger denne med verdien som er lest for å finne
                    // volten tilhørende verdien.
                voltIn = valueRead * (stepsize);
                    // Finner temperaturen ved å bruke voltIn og dele denne på Vref/stegMinMax
                temp = (voltIn / mVprsteg) - 50;

                Thread.sleep(100);
                    // la til en count. Når denne er 1200 har det gått to minutter.
                    // Burde kanskje heller bruke en slags timer?
                count++;
                values.add(temp);
                //System.out.println(values);

                // 1200 er to minutter, bruker 100 under testing for å sjekke at det stemmer. 100 er 10 sek.
                if (count == 100){
                        // Finner min og max i Arraylisten
                    double min = Collections.min(values);
                    double max = Collections.max(values);
                    System.out.println(
                            "----------" +
                            "\nMinimumverdi: " + min +
                            "\nMaksimumverdi: " + max +
                                    // Henter gjennomsnitt fra metoden lenger ned
                            "\nGjennomsnitt: " + average(values) +
                            "\n----------");
                        // Setter count til 0, og tømmer listen for verdier
                    count = 0;
                    values.clear();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return valueRead;
    }

        // Metoden finner gjennomsnittet av verdiene i Arraylisten
    public static double average(ArrayList<Double> getTempValues) {
        double total = 0;
        if (!getTempValues.isEmpty()) {
                // Legger sammen verdiene i arraylisten
            for (double i : getTempValues) {
                total += i;
            }
                // Regner ut gjennomsnittet
            return total / getTempValues.size();
        }
            // Returnerer gjennomsnittet
        return total;
    }
}









