package it.sevenbits.core.service.validators;

import it.sevenbits.web.model.metadata.MetaDataDefault;

/**
 * A page validator.
 */
public class PageValidator implements IValidator<Integer> {
    private MetaDataDefault metaDataDefault;

    /**
     * The constructor.
     *
     * @param metaDataDefault an object containing allowed values.
     */
    public PageValidator(final MetaDataDefault metaDataDefault) {
        this.metaDataDefault = metaDataDefault;
    }

    @Override
    public boolean check(final Integer value) {
        return value >= metaDataDefault.getMinPage();
    }
}
