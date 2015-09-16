package org.canary.serinus;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

@SpringBootApplication
public class Serinus {

    private static final Logger LOGGER = Logger.getLogger(Serinus.class);

    protected Serinus() {

        super();

        LOGGER.trace("constructed");
    }

    public static final void main(final String[] args) {

        LOGGER.info("main[args=" + args + "]");

        SpringApplication.run(Serinus.class, args);
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        LOGGER.info("filterRegistrationBean");

        final FilterRegistrationBean filterRegistrationBean =
            new FilterRegistrationBean();

        final UrlRewriteFilter urlRewriteFilter = new UrlRewriteFilter();

        filterRegistrationBean.setFilter(urlRewriteFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("confPath", "urlrewrite.xml");


        LOGGER.debug("filterRegistrationBean[returns=" + filterRegistrationBean
            + "]");

        return filterRegistrationBean;
    }

}