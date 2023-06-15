package pl.edu.pg.gateway.trip;

import java.util.List;

public class DeletionAfterTimeout implements Runnable {
    private final List<Long> list;
    private final Long element;
    private final int timeout;

    public DeletionAfterTimeout(List<Long> list, Long element, int timeout) {
        this.list = list;
        this.element = element;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        list.remove(element);
    }
}
