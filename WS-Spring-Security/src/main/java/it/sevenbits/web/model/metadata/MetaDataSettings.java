package it.sevenbits.web.model.metadata;

import it.sevenbits.web.model.metadata.MetaDataDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * The class is use to set default values for meta data from a specified file.
 */
@Configuration
@PropertySource("classpath:application.yml")
public class MetaDataSettings {

    @Autowired
    private Environment environment;

    /**
     * Creates a MetaDataDefault object with default values.
     *
     * @return an object full of default values.
     */
    @Bean
    public MetaDataDefault getDefaultMetaData() {
        String status = environment.getProperty("default.status");
        String order = environment.getProperty("default.order");
        String page = environment.getProperty("default.page");
        String size = environment.getProperty("default.size");
        String minSize = environment.getProperty("default.min-size");
        String maxSize = environment.getProperty("default.max-size");
        String minPage = environment.getProperty("default.min-page");

        String queryStatus = environment.getProperty("query.status");
        String queryOrder = environment.getProperty("query.order");
        String queryPage = environment.getProperty("query.page");
        String querySize = environment.getProperty("query.size");
        String queryMainPath = environment.getProperty("query.main-path");

        return new MetaDataDefault(status, order,
                Integer.valueOf(page),
                Integer.valueOf(size),
                Integer.valueOf(minSize),
                Integer.valueOf(maxSize),
                Integer.valueOf(minPage),
                queryStatus,
                queryOrder,
                queryPage,
                querySize,
                queryMainPath);
    }
}
