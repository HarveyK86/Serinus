package org.canary.serinus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public final class Controller {

    @Autowired
    private ApplicationContext applicationContext;

    private final Map<String, Service> services;

    private static final String SERVICE_NAME_SUFFIX = "Service";

    private static final Logger LOGGER = Logger.getLogger(Controller.class);

    private Controller() {

        super();

        LOGGER.trace("constructor");

        this.services = new HashMap<>();
    }

    // search
    @RequestMapping(method = RequestMethod.POST,
                    value = "/api/{model}/search")
    public List<Object> postSearch(

        @PathVariable("model")
        final String modelTypeKey,

        @RequestBody
        final Map<String, Object> searchParameters
    ) {

        LOGGER.info("postSearch[modelTypeKey=" + modelTypeKey
            + ", searchParameters=" + searchParameters);

        final Service service = this.getService(modelTypeKey);
        final List<Object> modelObjects =
            service.searchModels(searchParameters);

        LOGGER.debug("postSearch[modelTypeKey=" + modelTypeKey
            + ", searchParameters=" + searchParameters + ", returns="
            + modelObjects + "]");

        return modelObjects;
    }

    // create
    @RequestMapping(method = RequestMethod.POST,
                    value = "/api/{model}")
    public Object post(

        @PathVariable("model")
        final String modelTypeKey,

        @RequestBody
        final Map<String, Object> modelMap
    ) {

        LOGGER.info("post[modelTypeKey=" + modelTypeKey + ", modelMap="
            + modelMap + "]");

        final Service service = this.getService(modelTypeKey);
        final Object modelObject = service.createModel(modelMap);

        LOGGER.debug("post[modelTypeKey=" + modelTypeKey + ", modelMap="
            + modelMap + ", returns=" + modelObject + "]");

        return modelObject;
    }

    // read
    @RequestMapping(method = RequestMethod.GET,
                    value = "/api/{model}/{id}")
    public Object get(

        @PathVariable("model")
        final String modelTypeKey,

        @PathVariable("id")
        final int id
    ) {

        LOGGER.info("get[modelTypeKey=" + modelTypeKey + ", id=" + id + "]");

        final Service service = this.getService(modelTypeKey);
        final Object modelObject = service.getModel(id);

        LOGGER.debug("get[modelTypeKey=" + modelTypeKey + ", id=" + id
            + ", returns=" + modelObject + "]");

        return modelObject;
    }

    @RequestMapping(method = RequestMethod.GET,
                    value = "/api/{model}")
    public List<Object> get(

        @PathVariable("model")
        final String modelTypeKey
    ) {

        LOGGER.info("get[modelTypeKey=" + modelTypeKey + "]");

        final Service service = this.getService(modelTypeKey);
        final List<Object> modelObjects = service.getModels();

        LOGGER.debug("get[modelTypeKey=" + modelTypeKey + ", returns="
            + modelObjects + "]");

        return modelObjects;
    }

    @RequestMapping(method = RequestMethod.GET,
                    value = "/api/{model}/head/{id}")
    public Object getHead(

        @PathVariable("model")
        final String modelTypeKey,

        @PathVariable("id")
        final int id
    ) {

        LOGGER.info("getHead[modelTypeKey=" + modelTypeKey + ", id=" + id + "]");

        final Service service = this.getService(modelTypeKey);
        final Object lightweightModelObject = service.getLightweightModel(id);

        LOGGER.debug("getHead[modelTypeKey=" + modelTypeKey + ", id=" + id
            + ", returns=" + lightweightModelObject + "]");

        return lightweightModelObject;
    }

    @RequestMapping(method = RequestMethod.GET,
                    value = "/api/{model}/head")
    public Object getHead(

        @PathVariable("model")
        final String modelTypeKey
    ) {

        LOGGER.info("getHead[modelTypeKey=" + modelTypeKey + "]");

        final Service service = this.getService(modelTypeKey);
        final List<Object> lightweightModelObjects =
            service.getLightweightModels();

        LOGGER.debug("getHead[modelTypeKey=" + modelTypeKey + ", returns="
            + lightweightModelObjects + "]");

        return lightweightModelObjects;
    }

    // update
    @RequestMapping(method = RequestMethod.PUT,
                    value = "/api/{model}/{id}")
    public Object put(

        @PathVariable("model")
        final String modelTypeKey,

        @PathVariable("id")
        final int id,

        @RequestBody
        final Map<String, Object> modelMap
    ) {

        LOGGER.info("put[modelTypeKey=" + modelTypeKey + ", id=" + id + "]");

        final Service service = this.getService(modelTypeKey);
        final Object modelObject = service.overrideModel(id, modelMap);

        LOGGER.debug("put[modelTypeKey=" + modelTypeKey + ", id=" + id
            + ", modelMap=" + ", returns=" + modelObject + "]");

        return modelObject;
    }

    @RequestMapping(method = RequestMethod.PATCH,
                    value = "/api/{model}/{id}")
    public Object patch(

        @PathVariable("model")
        final String modelTypeKey,

        @PathVariable("id")
        final int id,

        @RequestBody
        final Map<String, Object> modelMap
    ) {

        LOGGER.info("patch[modelTypeKey=" + modelTypeKey + ", id=" + id
            + ", modelMap=" + modelMap + "]");

        final Service service = this.getService(modelTypeKey);
        final Object modelObject = service.updateModel(id, modelMap);

        LOGGER.debug("patch[modelTypeKey=" + modelTypeKey + ", id=" + id
            + ", modelMap=" + modelMap + ", returns=" + modelObject + "]");

        return modelObject;
    }

    // delete
    @RequestMapping(method = RequestMethod.DELETE,
                    value = "/api/{model}/{id}")
    public void delete(

        @PathVariable("model")
        final String modelTypeKey,

        @PathVariable("id")
        final int id
    ) {

        LOGGER.info("delete[modelTypeKey=" + modelTypeKey + ", id=" + id + "]");

        final Service service = this.getService(modelTypeKey);

        service.deleteModel(id);
    }

    private Service getService(final String modelTypeKey) {

        LOGGER.debug("getService[modelTypeKey=" + modelTypeKey + "]");

        final String serviceName;

        Service service = this.services.get(modelTypeKey);

        if(service == null) {

            serviceName = StringUtils.capitalize(modelTypeKey.toLowerCase())
                + SERVICE_NAME_SUFFIX;

            service = (Service) this.applicationContext.getBean(serviceName);
            this.services.put(modelTypeKey, service);
        }

        LOGGER.trace("getService[modelTypeKey=" + modelTypeKey + ", returns="
            + service + "]");

        return service;
    }

    public interface Service {

        // search
        List<Object> searchModels(final Map<String, Object> searchParameters);

        // create
        Object createModel(final Map<String, Object> modelMap);

        // read
        Object getModel(final int id);

        List<Object> getModels();

        Object getLightweightModel(final int id);

        List<Object> getLightweightModels();

        // update
        Object overrideModel(final int id, final Map<String, Object> modelMap);

        Object updateModel(final int id, final Map<String, Object> modelMap);

        // delete
        void deleteModel(final int id);

    }

}