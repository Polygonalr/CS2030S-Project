package cs2030.simulator;

import java.util.Optional;
import java.util.LinkedList;

class LeaveEvent extends Event {
    private static final int LEAVE_PRIORITY = 5;
    
    LeaveEvent(Customer customer, ServerList serverList) {
        super(customer, serverList, LEAVE_PRIORITY);
    }

    public Optional<Event> execute() {
        return Optional.empty();
    }

    @Override
    public String toString() {
        return super.descriptivePrint("leaves");
    }
}