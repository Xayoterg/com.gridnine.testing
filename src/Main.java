import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        List<Flight> flights= FlightBuilder.createFlights();
        Scanner scanner = new Scanner(System.in);
        String filter;
        while (true){
            filter = scanner.nextLine();
            if (filter.equals("exit")) break;
            HashSet<Flight> resultFlights = filter.equals("1")?
                    Filter.departureEarlier(flights):filter.equals("2")?
                    Filter.arrivalBeforeDeparture(flights):filter.equals("3")?
                    Filter.intervalFilter(flights):null;
            System.out.println(resultFlights);
        }
    }
}