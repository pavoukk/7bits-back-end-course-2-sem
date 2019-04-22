package it.sevenbits.core.service.validators;

import it.sevenbits.web.model.metadata.MetaDataDefault;

/**
 * A validator of page size value.
 */
public class PageSizeValidator implements IValidator<Integer> {
    private MetaDataDefault metaDataDefault;

    /**
     * The constructor.
     *
     * @param metaDataDefault an object containing allowed values.
     */
    public PageSizeValidator(final MetaDataDefault metaDataDefault) {
        this.metaDataDefault = metaDataDefault;
    }

    @Override
    public boolean check(final Integer value) {
        return value >= metaDataDefault.getMinSize() && value <= metaDataDefault.getMaxSize();
    }
}
