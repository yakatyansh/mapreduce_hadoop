import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BloodRequestDataFaker {

    private static final String[] CITIES = {"City A", "City B", "City C", "City D", "City E"};
    private static final String[] BLOOD_TYPES = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    private static final Random random = new Random();

    public static void main(String[] args) throws IOException {
        int numRequests = 1000; // Adjust as needed
        int numYears = 10;

        FileWriter writer = new FileWriter("blood_requests.csv");
        writer.write("request_key,city,blood_type,rh_factor,amount_ml,year\n");

        for (int i = 0; i < numRequests; i++) {
            String requestKey = "key" + (i + 1);
            String city = CITIES[random.nextInt(CITIES.length)];
            String bloodType = BLOOD_TYPES[random.nextInt(BLOOD_TYPES.length)];
            String rhFactor = random.nextBoolean() ? "+" : "-";
            int amountMl = random.nextInt(500) + 100; // Amount between 100 and 600 ml
            int year = random.nextInt(numYears) + 2014; // Years between 2014 and 2023

            writer.write(requestKey + "," + city + "," + bloodType + "," + rhFactor + "," + amountMl + "," + year + "\n");
        }

        writer.close();
        System.out.println("Generated " + numRequests + " blood request records in blood_requests.csv");
    }
}
