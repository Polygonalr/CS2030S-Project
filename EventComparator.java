package cs2030.simulator;

import java.util.Comparator;

class EventComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        if (e1.getTime() < e2.getTime()) {
            return -1;
        } else if (e1.getTime() > e2.getTime()) {
            return 1;
        }

        if (e1.getCustomer().getId() < e2.getCustomer().getId()) {
            return -1;
        } else if (e1.getCustomer().getId() > e2.getCustomer().getId()) {
            return 1;
        }

        if (e1.getPriority() < e2.getPriority()) {
            return -1;
        } else if (e1.getPriority() > e2.getPriority()) {
            return 1;
        }
        return 0;
    }
}