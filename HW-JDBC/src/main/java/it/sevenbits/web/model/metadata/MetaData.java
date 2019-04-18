package it.sevenbits.web.model.metadata;

import java.net.URI;

/**
 * The class is used to store meta data bounded up with tasks list.
 */

public class MetaData {
    private final int total;

    private final int page;

    private final int size;

    private final URI prev;

    private final URI next;

    private final URI first;

    private final URI last;

    /**
     * The constructor.
     *
     * @param total total amount of tasks.
     * @param page  a value of the current page.
     * @param size  a size of the page.
     * @param prev  a link to the previous page.
     * @param next  a link to the next page.
     * @param first a link to the first page.
     * @param last  a link to the last page.
     */

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
