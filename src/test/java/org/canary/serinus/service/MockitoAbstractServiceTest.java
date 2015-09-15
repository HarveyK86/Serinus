package org.canary.serinus.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.canary.serinus.controller.Controller;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class MockitoAbstractServiceTest {

    private Controller.Service service;

    private static final int MODEL_ID = 1;

    private static final Logger LOGGER =
        Logger.getLogger(MockitoAbstractServiceTest.class);

    @Before
    @SuppressWarnings("unchecked")
    public void before() {

        final Object model = this.getModel();

        this.service = Mockito.mock(AbstractService.class);

        Mockito.doCallRealMethod().when(this.service)
            .searchModels(Matchers.anyMap());
        Mockito.doCallRealMethod().when(this.service)
            .createModel(Matchers.anyMap());
        Mockito.doCallRealMethod().when(this.service)
            .getModel(MODEL_ID);
        Mockito.doCallRealMethod().when(this.service)
            .getModels();
        Mockito.doCallRealMethod().when(this.service)
            .getLightweightModel(MODEL_ID);
        Mockito.doCallRealMethod().when(this.service)
            .getLightweightModels();
        Mockito.doCallRealMethod().when(this.service)
            .overrideModel(Matchers.eq(MODEL_ID), Matchers.anyMap());
        Mockito.doCallRealMethod().when(this.service)
            .updateModel(Matchers.eq(MODEL_ID), Matchers.anyMap());
        Mockito.doCallRealMethod().when(this.service)
            .deleteModel(MODEL_ID);

        Mockito.when(((AbstractService) this.service)
            .create(model))
            .thenReturn(MODEL_ID);

        Mockito.when(((AbstractService) this.service)
            .getModel(Matchers.anyMap()))
            .thenReturn(model);
    }

    @Test(expected = IllegalArgumentException.class)
    public void searchModelsShouldThrowIllegalArgument() {
        this.service.searchModels(null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void searchModelsShouldCallSearch() {

        final Map<String, Object> searchParameters = this.getSearchParameters();

        this.service.searchModels(searchParameters);

        Mockito.verify((AbstractService) this.service, Mockito.times(1))
            .search(searchParameters);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createModelShouldThrowIllegalArgument() {
        this.service.createModel(null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void createModelShouldCallCreate() {

        final Map<String, Object> modelMap = this.getModelMap();

        this.service.createModel(modelMap);

        Mockito.verify((AbstractService) this.service, Mockito.times(1))
            .create(Matchers.any(Object.class));
    }

    @Test
    public void getModelShouldCallGet() {

        this.service.getModel(MODEL_ID);

        Mockito.verify((AbstractService) this.service, Mockito.times(1))
            .get(MODEL_ID);
    }

    @Test
    public void getModelsShouldCallGetAll() {

        this.service.getModels();

        Mockito.verify((AbstractService) this.service, Mockito.times(1))
            .getAll();
    }

    @Test
    public void getLightweightModelShouldCallGet() {

        this.service.getLightweightModel(MODEL_ID);

        Mockito.verify((AbstractService) this.service, Mockito.times(1))
            .get(MODEL_ID);
    }

    @Test
    public void getLightweightModelsShouldCallGetAll() {

        this.service.getLightweightModels();

        Mockito.verify((AbstractService) this.service, Mockito.times(1))
            .getAll();
    }

    @Test
    public void overrideModelShouldThrowIllegalArgument() {

        final Map<String, Object> modelMap = this.getModelMap(-1);

        try {

            this.service.overrideModel(MODEL_ID, null);
            Assert.fail();

        } catch(final IllegalArgumentException e) {
            LOGGER.trace("Expected Illegal argument thrown during testing.");
        }

        try {

            this.service.overrideModel(MODEL_ID, modelMap);
            Assert.fail();

        } catch(final IllegalArgumentException e) {
            LOGGER.trace("Expected Illegal argument thrown during testing.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void overrideModelShouldCallOverride() {

        final Map<String, Object> modelMap = this.getModelMap();

        this.service.overrideModel(MODEL_ID, modelMap);

        Mockito.verify((AbstractService) this.service, Mockito.times(1))
            .override(Matchers.any(Object.class));
    }

    @Test
    public void updateModelShouldThrowIllegalArgument() {

        final Map<String, Object> modelMap = this.getModelMap(-1);

        try {

            this.service.updateModel(MODEL_ID, null);
            Assert.fail();

        } catch(final IllegalArgumentException e) {
            LOGGER.trace("Expected Illegal argument thrown during testing.");
        }

        try {

            this.service.updateModel(MODEL_ID, modelMap);
            Assert.fail();

        } catch(final IllegalArgumentException e) {
            LOGGER.trace("Expected Illegal argument throw during testing.");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void updateModelShouldCallUpdate() {

        final Map<String, Object> modelMap = this.getModelMap();

        this.service.updateModel(MODEL_ID, modelMap);

        Mockito.verify((AbstractService) this.service, Mockito.times(1))
            .update(Matchers.any(Object.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void deleteModelShouldCallDelete() {

        this.service.deleteModel(MODEL_ID);

        Mockito.verify((AbstractService) this.service, Mockito.times(1))
            .delete(Matchers.any(Object.class));
    }

    private Map<String, Object> getSearchParameters() {

        final Map<String, Object> searchParameters = new HashMap<>();

        return searchParameters;
    }

    private Map<String, Object> getModelMap() {
        return this.getModelMap(MODEL_ID);
    }

    private Map<String, Object> getModelMap(final int id) {

        final Map<String, Object> modelMap = new HashMap<>();

        modelMap.put("id", id);

        return modelMap;
    }

    private Object getModel() {

        final Object model = new Object();

        return model;
    }

}