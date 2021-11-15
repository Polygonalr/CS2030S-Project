package cs2030.simulator;

import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

class SelfCheckout extends Server {
    SelfCheckout(int id, LinkedList<Map.Entry<Customer, Double>> queue) {
        super(id, queue);
    }
}