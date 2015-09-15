package org.canary.serinus.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.canary.serinus.controller.Controller;
import org.canary.serinus.model.Lightweight;
import org.canary.serinus.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractService<Model> implements Controller.Service {

    @Autowired
    private Repository repository;

    private static final Logger LOGGER =
        Logger.getLogger(AbstractService.class);

    protected AbstractService() {

        super();

        LOGGER.trace("constructed");
    }

    // search
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<Object> searchModels(
        final Map<String, Object> searchParameters) {

        LOGGER.info("searchModels[searchParameters=" + searchParameters + "]");

        if(searchParameters == null) {

            throw new IllegalArgumentException("Illegal argument; "
                + "searchParameters cannot be null.");
        }

        final List<Model> models = this.search(searchParameters);

        LOGGER.debug("searchModels[searchParameters=" + searchParameters
            + ", returns=" + models + "]");

        return (List<Object>) models;
    }

    // create
    @Transactional
    public Object createModel(final Map<String, Object> modelMap) {

        LOGGER.info("createModel[modelMap=" + modelMap + "]");

        if(modelMap == null) {

            throw new IllegalArgumentException("Illegal argument; modelMap "
                + "cannot be null.");
        }

        final Model model = this.getModel(modelMap);
        final int modelId = this.create(model);
        final Model createdModel = this.get(modelId);

        LOGGER.debug("createModel[modelMap=" + modelMap + ", returns="
            + createdModel + "]");

        return createdModel;
    }

    // read
    @Transactional(readOnly = true)
    public Object getModel(final int id) {

        LOGGER.info("getModel[id=" + id + "]");

        final Model model = this.get(id);

        LOGGER.debug("getModel[id=" + id + ", returns=" + model + "]");
        return model;
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public List<Object> getModels() {

       LOGGER.info("getModels");

       final List<Model> models = this.getAll();

       LOGGER.debug("getModels[returns=" + models + "]");
       return (List<Object>) models;
   }

    @Transactional(readOnly = true)
    public Object getLightweightModel(final int id) {

        LOGGER.info("getLightweightModel[id=" + id + "]");

        final Model model = this.get(id);
        final Object lightweightModelObject = this.getLightweightModel(model);

        LOGGER.debug("getLightweightModel[id=" + id + ", returns="
            + lightweightModelObject + "]");

        return lightweightModelObject;
   }

    @Transactional(readOnly = true)
    public List<Object> getLightweightModels() {

        LOGGER.info("getLightweightModels");

        final List<Model> models = this.getAll();
        final List<Object> lightweightModelObjects = new ArrayList<>();

        Object lightweightModelObject;

        for(Model model : models) {

            lightweightModelObject = this.getLightweightModel(model);
            lightweightModelObjects.add(lightweightModelObject);
        }

        LOGGER.debug("getLightweightModels[returns=" + lightweightModelObjects
            + "]");

        return lightweightModelObjects;
    }

    // update
    @Transactional
    public Object overrideModel(final int id,
        final Map<String, Object> modelMap) {

        LOGGER.info("overrideModel[id=" + id + ", modelMap=" + modelMap + "]");

        if(modelMap == null || id != (int) modelMap.get("id")) {

            throw new IllegalArgumentException("Illegal argument; modelMap "
                + "cannot be null and the ID must match the ID in the "
                + "modelMap.");
        }

        final Model model = this.getModel(modelMap);
        final Model overriddenModel;

        this.override(model);
        overriddenModel = this.get((int) modelMap.get("id"));

        LOGGER.debug("overrideModel[id=" + id + ", modelMap=" + modelMap
            + ", returns=" + overriddenModel + "]");

        return overriddenModel;
    }

    @Transactional
    public Object updateModel(final int id, final Map<String, Object> modelMap) {

        LOGGER.info("updateModel[id=" + id + ", modelMap=" + modelMap + "]");

        if(modelMap == null || id != (int) modelMap.get("id")) {

            throw new IllegalArgumentException("Illegal argument; modelMap "
                + "cannot be null and the ID must match the ID in the "
                + "modelMap.");
        }

        final Model model = this.getModel(modelMap);
        final Model updatedModel;

        this.update(model);
        updatedModel = this.get((int) modelMap.get("id"));

        LOGGER.debug("updateModel[id=" + id + ", modelMap=" + modelMap
            + ", returns=" + updatedModel + "]");

        return updatedModel;
    }

    // delete
    @Transactional
    public void deleteModel(final int id) {

        LOGGER.info("deleteModel[id=" + id + "]");

        final Model model = this.get(id);

        this.delete(model);
    }

    protected Repository getRepository() {

        LOGGER.debug("getRepository[returns=" + this.repository + "]");
        return this.repository;
    }

    protected Model getModel(final Map<String, Object> modelMap) {

        LOGGER.debug("getModel[modelMap=" + modelMap + "]");

        final Class<Model> clazz = this.getModelClass();
        final Constructor<Model> constructor;

        Model model = null;
        Field field;

        try {

            constructor = clazz.getConstructor();
            model = constructor.newInstance();

            for(final Entry entry : modelMap.entrySet()) {

                field = clazz.getDeclaredField((String) entry.getKey());
                field.setAccessible(true);
                field.set(model, entry.getValue());
            }

        } catch(final NoSuchMethodException | InstantiationException
            | IllegalAccessException | InvocationTargetException
            | NoSuchFieldException e) {

            LOGGER.error("Exception caught while instantiating " + clazz +
                " [modelMap=" + modelMap + "]", e);
        }

        LOGGER.trace("getModel[modelMap=" + modelMap + ", returns=" + model
            + "]");

        return model;
    }

    protected Object getLightweightModel(final Model model) {

        LOGGER.debug("getLightweightModel[model=" + model + "]");

        @SuppressWarnings("unchecked")
        final Class<Model> clazz = (Class<Model>) model.getClass();
        final Field[] fields = clazz.getDeclaredFields();
        final Map<String, Object> lightweightModelMap =
            new HashMap<String, Object>();

        Annotation annotation;

        for(final Field field : fields) {

            field.setAccessible(true);
            annotation = field.getAnnotation(Lightweight.class);

            if(annotation != null) {

                try {
                    lightweightModelMap.put(field.getName(), field.get(model));

                } catch(final IllegalAccessException e) {

                    LOGGER.error("Exception caught while retrieving a "
                        + "lightweight object [model=" + model + "]", e);
                }
            }
        }

        LOGGER.trace("getLightweightModel[model=" + model + ", returns="
            + lightweightModelMap + "]");

        return lightweightModelMap;
    }

    // search
    public abstract List<Model> search(final Map<String, Object> parameters);

    // create
    public abstract int create(final Model model);

    // read
    public abstract Model get(final int id);

    public abstract List<Model> getAll();

    // update
    public abstract void override(final Model model);

    public abstract void update(final Model model);

    // delete
    public abstract void delete(final Model model);

    protected abstract Class<Model> getModelClass();

}