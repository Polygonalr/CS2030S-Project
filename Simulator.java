package cs2030.simulator;

import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;

public class Simulator {
    private final PriorityQueue<Event> eventList;
    private final PriorityQueue<Event> finalEventList;
    private final ServerList serverList;
    private final Statistics statistics;

    private static final int STATS_MAX_SIZE = 3;
    private static final int I_TOTAL_WAIT = 0;
    private static final int I_CUSTOMERS_SERVED = 1;
    private static final int I_CUSTOMERS_LEFT = 2;

    /**
     * Dummy JavaDoc.
     * This is for Main1-4.
     */
    public Simulator(List<Double> arrivalTimes, List<Double> serveTimes, int numberOfServers,
            int maxQueueLength, LinkedList<Double> restTimes, boolean dumbCustomers,
            int selfcheckoutCount)
            throws Exception {
        this.eventList = new PriorityQueue<Event>(new EventComparator());
        this.finalEventList = new PriorityQueue<Event>(new EventComparator());
        this.serverList = new ServerList(restTimes);
        this.statistics = new Statistics();
        for (int i = 0; i < numberOfServers; i++) {
            this.serverList.add(new Server(i + 1, maxQueueLength));
        }
        LinkedList<Map.Entry<Customer, Double>> commonQueue =
                new LinkedList<Map.Entry<Customer, Double>>();
        for (int i = numberOfServers; i < numberOfServers + selfcheckoutCount; i++) {
            this.serverList.add(new SelfCheckout(i + 1, maxQueueLength, commonQueue));
        }
        for (int i = 0; i < arrivalTimes.size(); i++) {
            Customer newCustomer = new Customer(i + 1, arrivalTimes.get(i), serveTimes.get(i));
            Event arrivalEvent = new ArrivalEvent(newCustomer, this.serverList,
                    this.statistics, !newCustomer.isGreedy());
            eventList.add(arrivalEvent);
            finalEventList.add(arrivalEvent);
        }
    }

    // Main5 initialization with Supplier<Double> for serveTimes and greedyList
    public Simulator(List<Double> arrivalTimes, Supplier<Double> serveTimes, int numberOfServers,
            int maxQueueLength, LinkedList<Double> restTimes, List<Boolean> greedyList,
            int selfcheckoutCount) throws Exception {
        this.eventList = new PriorityQueue<Event>(new EventComparator());
        this.finalEventList = new PriorityQueue<Event>(new EventComparator());
        this.serverList = new ServerList(restTimes);
        this.statistics = new Statistics();
        for (int i = 0; i < numberOfServers; i++) {
            this.serverList.add(new Server(i + 1, maxQueueLength));
        }
        LinkedList<Map.Entry<Customer, Double>> commonQueue =
                new LinkedList<Map.Entry<Customer, Double>>();
        for (int i = numberOfServers; i < numberOfServers + selfcheckoutCount; i++) {
            this.serverList.add(new SelfCheckout(i + 1, maxQueueLength, commonQueue));
        }
        for (int i = 0; i < arrivalTimes.size(); i++) {
            Customer newCustomer = new Customer(i + 1, arrivalTimes.get(i), serveTimes,
                    greedyList.get(i));
            Event arrivalEvent = new ArrivalEvent(newCustomer, this.serverList,
                    this.statistics, !newCustomer.isGreedy());
            eventList.add(arrivalEvent);
            finalEventList.add(arrivalEvent);
        }
    }

    // I do not wish to pollute my Simulator anymore so this has to be done.
    // ------------------ Methods to expose RandomGenerator to Main5 -----------------------

    public static RandomGenerator randGenConstructor(int seed, double arrivalRate,
            double serviceRate, double restingRate) {
        return new RandomGenerator(seed, arrivalRate, serviceRate, restingRate);
    }

    public static double genCustomerType(RandomGenerator rg) {
        return rg.genCustomerType();
    }

    public static double genInterArrivalTime(RandomGenerator rg) {
        return rg.genInterArrivalTime();
    }

    public static double genRandomRest(RandomGenerator rg) {
        return rg.genRandomRest();
    }

    public static double genRestPeriod(RandomGenerator rg) {
        return rg.genRestPeriod();
    }

    public static double genServiceTime(RandomGenerator rg) {
        return rg.genServiceTime();
    }
    // -------------------------------------------------------------------------------------

    // For debugging!
    private String getCurrentEventString() {
        PriorityQueue<Event> eventListCopy = new PriorityQueue<Event>(eventList);
        String toReturn = "";
        while (!eventListCopy.isEmpty()) {
            toReturn += eventListCopy.poll().toString() + "\n";
        }
        return toReturn;
    }
    
    // For debugging!
    private String getCurrentServerQueueString() {
        String toReturn = "";
        for (int i = 0; i < this.serverList.size(); i++) {
            toReturn += this.serverList.get(i).debugPrint();
        }
        return toReturn;
    }

    private void calculateStatistics() {
        // Copying finalEventList into ArrayList<String>
        PriorityQueue<Event> finalEventListCopy = new PriorityQueue<Event>(this.finalEventList);
        ArrayList<Event> finalEventListCopyCopy = new ArrayList<Event>();
        ArrayList<String> finalEventListStrings = new ArrayList<String>();
        while (!finalEventListCopy.isEmpty()) {
            Event nextEvent = finalEventListCopy.poll();
            if (nextEvent.toAddToFinal()) {
                finalEventListCopyCopy.add(nextEvent);
                finalEventListStrings.add(nextEvent.toString());
            }
        }

        // Only adding waiting time to statistics as other statistics are calculated within
        // respective Event classes
        finalEventListCopyCopy.forEach(e -> {
            if (e.toString().contains("arrives")) {
                int customerId = e.getCustomer().getId();
                double arrivalTime = e.getTime();
                // Extracts the ServeEvent string from the finalEventList that matches
                // the customerNumber, then extracts the timing from that ServeEvent
                // If ServeEvent does not exist, defaults to arrivalTime.
                double servesTime = finalEventListCopyCopy.stream()
                        .filter(x -> x.getCustomer().getId() == customerId
                                && x.toString().contains("serves by"))
                        .findFirst()
                        .map(x -> x.getTime())
                        .orElse(arrivalTime);
                this.statistics.addWaitTime(servesTime - arrivalTime);
            }
        });
    }

    private String getFullEventString() {
        this.calculateStatistics();

        // Final event string builder
        PriorityQueue<Event> finalEventListCopy = new PriorityQueue<Event>(this.finalEventList);
        String toReturn = "";
        while (!finalEventListCopy.isEmpty()) {
            Event nextEvent = finalEventListCopy.poll();
            if (nextEvent.toAddToFinal()) {
                toReturn += nextEvent.toString() + "\n";
            }
        }
        return toReturn;
    }

    /**
     * Dummy JavaDoc.
     */
    public void simulate(boolean debug) throws Exception {
        while (!this.eventList.isEmpty()) {
            Event currentEvent = this.eventList.poll();

            // ------- UGLY CODE INCOMING -------
            if (currentEvent.isRestEvent()) {
                // create and push ServeEvent here
                Server s = currentEvent.freedServer()
                        .orElseThrow(() -> new Exception("illegal freedServer called"));
                currentEvent.dequeue().ifPresent(x -> {
                    Event newServeEvent = new ServeEvent(
                        currentEvent.getTime(),
                        x.getKey(),
                        this.serverList,
                        s
                    );
                    this.eventList.add(newServeEvent);
                    this.finalEventList.add(newServeEvent);
                });
            }
            // ----------------------------------

            Optional<Event> newEvent = currentEvent.execute();
            newEvent.ifPresent(event -> {
                this.eventList.add(event);
                this.finalEventList.add(event);
            });
            if (debug) {
                System.out.println("\n" + this.getCurrentEventString() + "\n"
                        + this.getCurrentServerQueueString());
            }
        }
        System.out.print(this.getFullEventString());
        System.out.println(this.statistics);
    }
}