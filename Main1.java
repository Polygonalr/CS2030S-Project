import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import cs2030.simulator.Simulator;

class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Double> arrivalTimes = new ArrayList<Double>();
        List<Double> serveTimes = new ArrayList<Double>();
        List<Double> restTimes = new ArrayList<Double>();
        int numberOfServers = 1;
        int maxQueueLength = 1;

        if (sc.hasNextInt()) {
            numberOfServers = sc.nextInt();
        }

        while (sc.hasNextDouble()) {
            arrivalTimes.add(sc.nextDouble());
            serveTimes.add(1.0);
            restTimes.add(0.0);
        }
        Simulator s = new Simulator(arrivalTimes, serveTimes, numberOfServers,
                maxQueueLength, true);
        s.simulate(false);
    }
}