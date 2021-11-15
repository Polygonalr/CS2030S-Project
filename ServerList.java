package cs2030.simulator;

import java.util.Optional;
import java.util.ArrayList;

class ServerList extends ArrayList<Server> {
    private static final double BIG_DOUBLE = 99999.9;
    private static final int BIG_INT = 99999;

    public Optional<Server> getAvailableServer() {
        // Check for any non-serving Servers first
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).isServing()) {
                return Optional.<Server>of(this.get(i));
            }
        }
        return Optional.empty();
    }

    public Optional<Server> getServerWithQueueSlot(boolean greedy, boolean dumbCustomers) {
        if (dumbCustomers) {
            // first server with queueslot
            Optional<Server> chosenServer = Optional.empty();
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).hasQueueSlot()) {
                    chosenServer = Optional.<Server>of(this.get(i));
                    break;
                }
            }
            return chosenServer;
        }
        
        if (greedy) {
            // server with shortest queue
            int shortestQueue = BIG_INT;
            Optional<Server> chosenServer = Optional.empty();
            // Check for any Servers first
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).hasQueueSlot()
                        && this.get(i).getQueueLength() < shortestQueue) {
                    chosenServer = Optional.<Server>of(this.get(i));
                    shortestQueue = this.get(i).getQueueLength();
                }
            }
            return chosenServer;
        }
        
        // server with shortest wait time
        double fastestAvailTime = BIG_DOUBLE;
        Optional<Server> chosenServer = Optional.empty();
        // Check for any Servers first
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).hasQueueSlot()
                    && this.get(i).getNextAvailableTime() < fastestAvailTime) {
                chosenServer = Optional.<Server>of(this.get(i));
                fastestAvailTime = this.get(i).getNextAvailableTime();
            }
        }
        return chosenServer;
    }

    public boolean setUnavailable(Server s) throws ArrayIndexOutOfBoundsException {
        int index = this.indexOf(s);
        if (index == -1) {
            throw new ArrayIndexOutOfBoundsException("Can't find server to set availability!");
        }
        this.set(index, s.setUnavailable());
        return true;
    }

    public boolean isAvailable(Server s) throws ArrayIndexOutOfBoundsException {
        int index = this.indexOf(s);
        if (index == -1) {
            throw new ArrayIndexOutOfBoundsException("Can't find server to set availability!");
        }
        return this.get(index).isAvailable();
    }

    public boolean setAvailable(Server s) throws ArrayIndexOutOfBoundsException {
        int index = this.indexOf(s);
        if (index == -1) {
            throw new ArrayIndexOutOfBoundsException("Can't find server to set availability!");
        }
        this.set(index, this.get(index).setAvailable());
        return true;
    }

    public double getNextAvailableTime(Server s) throws ArrayIndexOutOfBoundsException {
        int index = this.indexOf(s);
        if (index == -1) {
            throw new ArrayIndexOutOfBoundsException("Can't find server to set availability!");
        }
        return this.get(index).getNextAvailableTime();
    }

    public boolean setNextAvailableTime(Server s, double t) throws ArrayIndexOutOfBoundsException {
        int index = this.indexOf(s);
        if (index == -1) {
            throw new ArrayIndexOutOfBoundsException("Can't find server to set availability!");
        }
        this.set(index, this.get(index).setNextAvailableTime(t));
        return true;
    }
}