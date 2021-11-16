import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Supplier;

import cs2030.simulator.Simulator;
import cs2030.simulator.RandomGenerator;

class Main5 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        List<Double> arrivalTimes = new ArrayList<Double>();
        LinkedList<Double> serveTimes = new LinkedList<Double>();
        LinkedList<Double> restTimes = new LinkedList<Double>();
        List<Boolean> greedyList = new ArrayList<Boolean>();
        int seed = 1;
        int numberOfServers = 1;
        int maxQueueLength = 1;
        int customerCount = 1;
        int selfcheckoutCount = 1;

        double arrivalRate = 0.0;
        double serviceRate = 0.0;
        double restingRate = 0.0;
        double pRest = 0.0;
        double pGreedy = 0.0;

        if (sc.hasNextInt()) {
            seed = sc.nextInt();
        }
        if (sc.hasNextInt()) {
            numberOfServers = sc.nextInt();
        }
        if (sc.hasNextInt()) {
            selfcheckoutCount = sc.nextInt();
        }
        if (sc.hasNextInt()) {
            maxQueueLength = sc.nextInt();
        }
        if (sc.hasNextInt()) {
            customerCount = sc.nextInt();
        }
        if (sc.hasNextDouble()) {
            arrivalRate = sc.nextDouble();
        }
        if (sc.hasNextDouble()) {
            serviceRate = sc.nextDouble();
        }
        if (sc.hasNextDouble()) {
            restingRate = sc.nextDouble();
        }
        if (sc.hasNextDouble()) {
            pRest = sc.nextDouble();
        }
        if (sc.hasNextDouble()) {
            pGreedy = sc.nextDouble();
        }

        // ---------- Random Generation nonsense here ----------
        RandomGenerator rg = Simulator.randGenConstructor(seed, arrivalRate,
                serviceRate, restingRate);

        // Generate arrivalTimes
        arrivalTimes.add(0.0);
        for (int i = 1; i < customerCount; i++) {
            arrivalTimes.add(arrivalTimes.get(i - 1)
                    + Simulator.genInterArrivalTime(rg));
        }

        // Generate restTimes
        for (int i = 0; i < customerCount; i++) {
            restTimes.add(Simulator.genRestPeriod(rg));
        }
        ArrayList<Boolean> takeRest = new ArrayList<Boolean>();
        for (int i = 0; i < customerCount; i++) {
            takeRest.add(Simulator.genRandomRest(rg) < pRest); // take rests if true
        }
        for (int i = 0; i < customerCount; i++) {
            if (!takeRest.get(i)) {
                restTimes.add(i, 0.0);
            }
        }

        // Generate serveTime supplier
        for (int i = 0; i < customerCount; i++) {
            serveTimes.add(Simulator.genServiceTime(rg));
        }
        Supplier<Double> serveTimeSupplier = () -> serveTimes.poll();

        // Generate customer greediness
        for (int i = 0; i < customerCount; i++) {
            greedyList.add(Simulator.genCustomerType(rg) < pGreedy);
        }

        // ---------- End of random generation nonsense ----------

        

        // for (int i = 0; i < customerCount; i++) {
        //     arrivalTimes.add(sc.nextDouble());
        //     serveTimes.add(sc.nextDouble());
        // }

        // for (int i = 0; i < customerCount; i++) {
        //     restTimes.addLast(sc.nextDouble());
        // }
        Simulator s = new Simulator(arrivalTimes, serveTimeSupplier, numberOfServers,
                maxQueueLength, restTimes, greedyList, selfcheckoutCount);
        s.simulate(false);
    }
}