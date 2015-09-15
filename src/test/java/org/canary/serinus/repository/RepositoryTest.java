package org.canary.serinus.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class RepositoryTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Session session;

    @Mock
    private Criteria criteria;

    @InjectMocks
    private Repository repository;

    private static final Class<?> MODEL_CLASS = Object.class;
    private static final int MODEL_ID = 1;

    private static final Logger LOGGER = Logger.getLogger(RepositoryTest.class);

    @Before
    public void before() {

        Mockito.when(this.entityManager
            .unwrap(Session.class))
            .thenReturn(this.session);

        Mockito.when(this.session
            .createCriteria(MODEL_CLASS))
            .thenReturn(this.criteria);

        Mockito.when(this.session
            .save(Matchers.any(MODEL_CLASS)))
            .thenReturn(MODEL_ID);
    }

    @Test
    public void searchShouldThrowIllegalArgument() {

        final Map<String, Object> searchParameters = this.getSearchParameters();

        try {

            this.repository.search(null, searchParameters);
            Assert.fail();

        } catch(final IllegalArgumentException e) {
            LOGGER.trace("Expected Illegal argument thrown during testing.");
        }

        try {

            this.repository.search(MODEL_CLASS, null);
            Assert.fail();

        } catch(final IllegalArgumentException e) {
            LOGGER.trace("Expected Illegal argument thrown during testing.");
        }
    }

    @Test
    public void searchShouldCallCriteriaList() {

        final Map<String, Object> searchParameters = this.getSearchParameters();

        this.repository.search(MODEL_CLASS, searchParameters);

        Mockito.verify(this.criteria, Mockito.times(1))
            .list();
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveShouldThrowIllegalArgument() {
        this.repository.save(null);
    }

    @Test
    public void saveShouldCallSessionSave() {

        final Object model = this.getModel();

        this.repository.save(model);

        Mockito.verify(this.session, Mockito.times(1))
            .save(model);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getShouldThrowIllegalArgument() {
        this.repository.get(null, MODEL_ID);
    }

    @Test
    public void getShouldCallSessionGet() {

        this.repository.get(MODEL_CLASS, MODEL_ID);

        Mockito.verify(this.session, Mockito.times(1))
            .get(MODEL_CLASS, MODEL_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getAllShouldThrowIllegalArgument() {
        this.repository.getAll(null);
    }

    @Test
    public void getAllShouldCallCriteriaList() {

        this.repository.getAll(MODEL_CLASS);

        Mockito.verify(this.criteria, Mockito.times(1))
            .list();
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateShouldThrowIllegalArgument() {
        this.repository.update(null);
    }

    @Test
    public void updateShouldCallSessionUpdate() {

        final Object model = this.getModel();

        this.repository.update(model);

        Mockito.verify(this.session, Mockito.times(1))
            .update(model);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteShouldThrowIllegalArgument() {
        this.repository.delete(null);
    }

    @Test
    public void deleteShouldCallSessionDelete() {

        final Object model = this.getModel();

        this.repository.delete(model);

        Mockito.verify(this.session, Mockito.times(1))
            .delete(model);
    }

    private Map<String, Object> getSearchParameters() {

        final Map<String, Object> searchParameters = new HashMap<>();

        return searchParameters;
    }

    private Object getModel() {

        final Object model = new Object();

        return model;
    }

}