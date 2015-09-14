package org.canary.serinus.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.canary.serinus.service.AbstractService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

@RunWith(MockitoJUnitRunner.class)
public final class ControllerTest {

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private Controller.Service service;

    @InjectMocks
    private Controller controller;

    private static final String MODEL_TYPE_KEY = "Test Model Type Key";
    private static final int MODEL_ID = 1;

    @Before
    public void before() {

        Mockito.when(this.applicationContext
            .getBean(StringUtils.capitalize(MODEL_TYPE_KEY.toLowerCase())
                + "Service"))
            .thenReturn(this.service);
    }

    @Test
    public void postSearchShouldCallServiceSearchModels() {

        final Map<String, Object> searchParameters = this.getSearchParameters();

        this.controller.postSearch(MODEL_TYPE_KEY, searchParameters);

        Mockito.verify(this.service, Mockito.times(1))
            .searchModels(searchParameters);
    }

    @Test
    public void postShouldCallServiceCreateModel() {

        final Map<String, Object> modelMap = this.getModelMap();

        this.controller.post(MODEL_TYPE_KEY, modelMap);

        Mockito.verify(this.service, Mockito.times(1))
            .createModel(modelMap);
    }

    @Test
    public void getStringIntShouldCallServiceGetModel() {

        this.controller.get(MODEL_TYPE_KEY, MODEL_ID);

        Mockito.verify(this.service, Mockito.times(1))
            .getModel(MODEL_ID);
    }

    @Test
    public void getStringShouldCallServiceGetModels() {

        this.controller.get(MODEL_TYPE_KEY);

        Mockito.verify(this.service, Mockito.times(1))
            .getModels();
    }

    @Test
    public void getHeadStringIntShouldCallServiceGetLightweightModel() {

        this.controller.getHead(MODEL_TYPE_KEY, MODEL_ID);

        Mockito.verify(this.service, Mockito.times(1))
            .getLightweightModel(MODEL_ID);
    }

    @Test
    public void getHeadStringShouldCallServiceGetLightweightModels() {

        this.controller.getHead(MODEL_TYPE_KEY);

        Mockito.verify(this.service, Mockito.times(1))
            .getLightweightModels();
    }

    @Test
    public void putSouldCallServiceOverrideModel() {

        final Map<String, Object> modelMap = this.getModelMap();

        this.controller.put(MODEL_TYPE_KEY, MODEL_ID, modelMap);

        Mockito.verify(this.service, Mockito.times(1))
            .overrideModel(MODEL_ID, modelMap);
    }

    @Test
    public void patchShouldCallServiceUpdateModel() {

        final Map<String, Object> modelMap = this.getModelMap();

        this.controller.patch(MODEL_TYPE_KEY, MODEL_ID, modelMap);

        Mockito.verify(this.service, Mockito.times(1))
            .updateModel(MODEL_ID, modelMap);
    }

    @Test
    public void deleteShouldCallServiceDeleteModel() {

        this.controller.delete(MODEL_TYPE_KEY, MODEL_ID);

        Mockito.verify(this.service, Mockito.times(1))
            .deleteModel(MODEL_ID);
    }

    private Map<String, Object> getSearchParameters() {

        final Map<String, Object> searchParameters = new HashMap<>();

        return searchParameters;
    }

    private Map<String, Object> getModelMap() {

        final Map<String, Object> modelMap = new HashMap<>();

        return modelMap;
    }

}