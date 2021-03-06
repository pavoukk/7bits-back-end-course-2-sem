package it.sevenbits.web.model.metadata;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * The class is used to store default values.
 */
@Validated
public class MetaDataDefault {
    @NotBlank
    private final String status;

    @NotBlank
    private final String order;

    @Size(min = 1)
    private final int page;

    @Size(min = 10, max = 50)
    private final int size;

    @NotBlank
    private final int minSize;

    @NotBlank
    private final int maxSize;

    @NotBlank
    private final int minPage;

    @NotBlank
    private final String queryStatus;

    @NotBlank
    private final String queryOrder;

    @NotBlank
    private final String queryPage;

    @NotBlank
    private final String querySize;

    @NotBlank
    private final String queryMainPath;

    /**
     * The constructor.
     *
     * @param status        a status of the needed tasks.
     * @param order         an order in which the tasks will be shown
     * @param page          a current page number
     * @param size          a size of the page
     * @param minSize       a minimum allowed size of the page
     * @param maxSize       a maximum allowed size of the page
     * @param minPage       a minimum value of the page
     * @param queryStatus   value of status query
     * @param queryOrder    value of order query
     * @param queryPage     value of page query
     * @param querySize     value of size query
     * @param queryMainPath value of main path query
     */
    public MetaDataDefault(final String status,
                           final String order,
                           final int page,
                           final int size,
                           final int minSize,
                           final int maxSize,
                           final int minPage,
                           final String queryStatus,
                           final String queryOrder,
                           final String queryPage,
                           final String querySize,
                           final String queryMainPath) {
        this.status = status;
        this.order = order;
        this.page = page;
        this.size = size;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.minPage = minPage;
        this.queryStatus = queryStatus;
        this.queryOrder = queryOrder;
        this.queryPage = queryPage;
        this.querySize = querySize;
        this.queryMainPath = queryMainPath;
    }

    public String getStatus() {
        return status;
    }

    public String getOrder() {
        return order;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public int getMinSize() {
        return minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getMinPage() {
        return minPage;
    }

    public String getQueryStatus() {
        return queryStatus;
    }

    public String getQueryOrder() {
        return queryOrder;
    }

    public String getQueryPage() {
        return queryPage;
    }

    public String getQuerySize() {
        return querySize;
    }

    public String getQueryMainPath() {
        return queryMainPath;
    }
}
