import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;

public class Filter {
    public static HashSet<Flight> resultFlights = new HashSet<>();
    public static HashSet<Flight> departureEarlier(List<Flight> flights){
        LocalDateTime localDateTime = LocalDateTime.now();
        for (Flight flight: flights) {
            String[] segments = getSegments(flight);
            for (String segment:segments) {
                LocalDateTime departure =  getDates(segment)[0]; // Дата вылета
                if(departure.compareTo(localDateTime) < 0){ // показываем правильные варианты
                    continue; // Прерывание текущей иттерации цикла
                }
                resultFlights.add(flight);
            }
        }
        return resultFlights;
    }
    public static HashSet<Flight> arrivalBeforeDeparture(List<Flight> flights){
        for (Flight flight: flights) {
            String[] segments = getSegments(flight);
            for (String segment:segments) {
                LocalDateTime departure =  getDates(segment)[0]; // Дата вылета
                LocalDateTime arrival = getDates(segment)[1]; // Дата прилёта
                if(arrival.compareTo(departure) < 0){ // показываем неправильный вариант
                    continue; // Прерывание текущей иттерации цикла
                }
                resultFlights.add(flight);
            }
        }
        return resultFlights;
    }
    public static  HashSet<Flight> intervalFilter(List<Flight> flights){
        outer:for (Flight flight: flights) {
            String[] segments = getSegments(flight);
            for (int i=0; i<segments.length; i++) {
                if(segments.length>1 && (segments.length-1)!=i) {
                    long prevMin = getDates(segments[i])[1].atZone(ZoneId.systemDefault()).toEpochSecond()/60;
                    long nextMin = getDates(segments[i + 1])[0].atZone(ZoneId.systemDefault()).toEpochSecond()/60;
                    if(nextMin-prevMin>120) continue outer;
                }
            }
        }
        return resultFlights;
    }
    public static String[] getSegments(Flight flight){
        String flightSting = flight.toString();
        return flightSting.split(" ");
    }
    public static LocalDateTime[] getDates(String str){
        StringBuffer sb = new StringBuffer(str);
        // Чистим от мусора даты вылета и прилёта от скобочек
        String[] dates = sb.delete(sb.length()-1, sb.length()).delete(0,1).toString().split("\\|");
        LocalDateTime departure =  LocalDateTime.parse(dates[0]); // Дата вылета
        LocalDateTime arrival = LocalDateTime.parse(dates[1]); // Дата прилёта
        return new LocalDateTime[]{departure, arrival};
    }
}
