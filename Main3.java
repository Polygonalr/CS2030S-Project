import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import cs2030.simulator.Simulator;

class Main3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Double> arrivalTimes = new ArrayList<Double>();
        List<Double> serveTimes = new ArrayList<Double>();
        List<Double> restTimes = new ArrayList<Double>();
        int numberOfServers = 1;
        int maxQueueLength = 1;
        int customerCount = 1;

        if (sc.hasNextInt()) {
            numberOfServers = sc.nextInt();
        }

        if (sc.hasNextInt()) {
            maxQueueLength = sc.nextInt();
        }

        if (sc.hasNextInt()) {
            customerCount = sc.nextInt();
        }

        for (int i = 0; i < customerCount; i++) {
            arrivalTimes.add(sc.nextDouble());
            serveTimes.add(sc.nextDouble());
        }

        for (int i = 0; i < customerCount; i++) {
            restTimes.add(sc.nextDouble());
        }
        Simulator s = new Simulator(arrivalTimes, serveTimes, numberOfServers,
                maxQueueLength, false);
        s.simulate(false);
    }
}