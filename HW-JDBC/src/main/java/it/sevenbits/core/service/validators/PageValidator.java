package it.sevenbits.core.service.validators;

import it.sevenbits.web.model.MetaDataDefault;

public class PageValidator implements IValidator<Integer> {
    private MetaDataDefault metaDataDefault;

    public PageValidator(MetaDataDefault metaDataDefault) {
        this.metaDataDefault = metaDataDefault;
    }

    @Override
    public boolean check(final Integer value) {
        return value >= metaDataDefault.getMinPage();
    }
}
