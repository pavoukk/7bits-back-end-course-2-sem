package it.sevenbits.web.model;

import java.net.URI;

public class MetaData {
    private final int total;

    private final int page;

    private final int size;

    private final URI prev;

    private final URI next;

    private final URI first;

    private final URI last;

    public MetaData(final int total,
                    final int page,
                    final int size,
                    final URI prev,
                    final URI next,
                    final URI first,
                    final URI last) {
        this.total = total;
        this.page = page;
        this.size = size;
        this.prev = prev;
        this.next = next;
        this.first = first;
        this.last = last;
    }

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public URI getPrev() {
        return prev;
    }

    public URI getNext() {
        return next;
    }

    public URI getFirst() {
        return first;
    }
    public URI getLast() {
        return last;
    }
}
