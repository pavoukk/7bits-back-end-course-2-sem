package it.sevenbits.core.service.validators;

import it.sevenbits.web.model.MetaDataDefault;

public class PageSizeValidator implements IValidator<Integer> {
    private MetaDataDefault metaDataDefault;

    public PageSizeValidator(final MetaDataDefault metaDataDefault) {
        this.metaDataDefault = metaDataDefault;
    }
    @Override
    public boolean check(final Integer value) {
        return value >= metaDataDefault.getMinSize() && value <= metaDataDefault.getMaxSize();
    }
}
