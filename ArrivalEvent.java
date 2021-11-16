package cs2030.simulator;

import java.util.Optional;
import java.util.Map;
import java.util.LinkedList;

class ArrivalEvent extends Event {
    private final Statistics statistics;
    private final boolean dumbCustomers;
    private static final int ARRIVAL_PRIORITY = 1;

    ArrivalEvent(Customer customer, ServerList serverList, Statistics statistics,
            boolean dumbCustomers) {
        super(customer, serverList, ARRIVAL_PRIORITY);
        this.statistics = statistics;
        this.dumbCustomers = dumbCustomers;
    }

    public Optional<Event> execute() {
        // Find whether there is a non-serving server.
        // Add customer to the queue of the sevrer and transform to ServeEvent if so.
        Optional<Event> transformedEvent = super.getServerList().getAvailableServer().<Event>map(
            server -> {
                statistics.countServedCustomer();
                super.getServerList().addCustomerToQueue(server, Pair.of(
                    super.getCustomer(),
                    super.getTime() + super.getCustomer().getServeTime()
                ));
                return new ServeEvent(
                    super.getCustomer().getArrivalTime(),
                    super.getCustomer(),
                    super.getServerList(),
                    server
                );
            }
        );
        // If not, find whether there's a server with a queue slot.
        // Add customer to the queue of the server and transform to WaitEvent if so.
        transformedEvent = transformedEvent.or(() -> {
            return super.getServerList().getServerWithQueueSlot(false, dumbCustomers).<Event>map(
                server -> {
                    this.statistics.countServedCustomer();
                    // this.statistics.addWaitTime(server.getNextAvailableTime() - super.getTime());
                    super.getServerList().addCustomerToQueue(server, Pair.of(
                        super.getCustomer(),
                        0.0
                    ));
                    return new WaitEvent(
                        super.getCustomer().getArrivalTime(), super.getCustomer(),
                        super.getServerList(),
                        server,
                        this.statistics
                    );
                }
            );
        });
        // Or else, transform to LeaveEvent
        Event newEvent = transformedEvent.orElseGet(() -> {
            statistics.countLeftCustomer();
            return new LeaveEvent(super.getCustomer(), super.getServerList());
        });
        return Optional.<Event>of(newEvent);
    }

    @Override
    public String toString() {
        return super.descriptivePrint("arrives");
    }
}