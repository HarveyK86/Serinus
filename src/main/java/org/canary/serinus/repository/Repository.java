package org.canary.serinus.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Repository
public class Repository {

    @Autowired
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(Repository.class);

    public Repository() {

        super();

        LOGGER.info("constructed");
    }

    public <Model> List<Model> search(final Class<Model> clazz,
        final Map<String, Object> parameters) {

        LOGGER.info("search[clazz=" + clazz + ", parameters="
            + parameters + "]");

        final Session session = this.getSession();
        final Criteria criteria = session.createCriteria(clazz);
        final List<Model> models;

        for(final Entry parameter : parameters.entrySet()) {

            criteria.add(Restrictions.eq((String) parameter.getKey(),
                parameter.getValue()));
        }

        models = criteria.list();

        LOGGER.debug("search[clazz=" + clazz + ", parameters=" + parameters
            + ", returns=" + models + "]");

        return models;
    }

    public int save(final Object model) {

        LOGGER.info("save[model=" + model + "]");

        final Session session = this.getSession();
        final int modelId = (int) session.save(model);

        LOGGER.debug("save[model=" + model + ", returns=" + modelId + "]");
        return modelId;
    }

    public <Model> Model get(final Class<Model> clazz, final int id) {

        LOGGER.info("get[clazz=" + clazz + ", id=" + id + "]");

        final Session session = this.getSession();
        final Model model = (Model) session.get(clazz, id);

        LOGGER.debug("get[clazz=" + clazz + ", id=" + id + ", returns="
            + model + "]");

        return model;
    }

    public <Model> List<Model> getAll(final Class<Model> clazz) {

        LOGGER.info("getAll[clazz=" + clazz + "]");

        final List<Model> models = this.search(clazz,
            new HashMap<String, Object>());

        LOGGER.debug("getAll[clazz=" + clazz + ", returns=" + models + "]");
        return models;
    }

    public void update(final Object model) {

        LOGGER.info("update[model=" + model + "]");

        final Session session = this.getSession();

        session.update(model);
    }

    public void delete(final Object model) {

        LOGGER.info("update[model=" + model + "]");

        final Session session = this.getSession();

        session.delete(model);
    }

    private Session getSession() {

        LOGGER.debug("getSession");

        final Session session = this.entityManager.unwrap(Session.class);

        LOGGER.trace("getSession[returns=" + session + "]");
        return session;
    }

}