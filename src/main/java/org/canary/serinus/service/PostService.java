package org.canary.serinus.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.canary.serinus.model.Post;
import org.canary.serinus.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("PostService")
public class PostService extends AbstractService<Post> {

    private static final Logger LOGGER = Logger.getLogger(PostService.class);

    private PostService() {

        super();

        LOGGER.trace("constructed");
    }

    // search
    @Transactional(readOnly = true)
    public List<Post> search(final Map<String, Object> parameters) {

        LOGGER.info("search[parameters=" + parameters + "]");

        final Repository repository = super.getRepository();
        final List<Post> posts = repository.search(Post.class, parameters);

        LOGGER.debug("search[parameters=" + parameters + ", returns=" + posts
            + "]");

        return posts;
    }

    // create
    @Transactional
    public int create(final Post post) {

        LOGGER.info("create[post=" + post + "]");

        final Repository repository = super.getRepository();
        final int postId = repository.save(post);

        LOGGER.debug("create[post=" + post + ", returns=" + postId + "]");
        return postId;
    }

    // read
    @Transactional(readOnly = true)
    public Post get(final int id) {

        LOGGER.info("get[id=" + id + "]");

        final Repository repository = super.getRepository();
        final Post post = repository.get(Post.class, id);

        LOGGER.debug("get[id=" + id + ", returns=" + post + "]");
        return post;
    }

    @Transactional(readOnly = true)
    public List<Post> getAll() {

        LOGGER.info("getAll");

        final Repository repository = super.getRepository();
        final List<Post> posts = repository.getAll(Post.class);

        LOGGER.debug("getAll[returns=" + posts + "]");

        return posts;
    }

    // update
    @Transactional
    public void override(final Post post) {

        LOGGER.info("override[post=" + post + "]");

        final Repository repository = super.getRepository();

        repository.update(post);
    }

    @Transactional
    public void update(final Post post) {

        LOGGER.info("update[post=" + post + "]");

        final Post persisted = this.get(post.getId());

        if(StringUtils.isNotBlank(post.getTitle())) {
            persisted.setTitle(post.getTitle());
        }

        if(StringUtils.isNotBlank(post.getBody())) {
            persisted.setTitle(post.getBody());
        }

        this.override(post);
    }

    // delete
    @Transactional
    public void delete(final Post post) {

        LOGGER.info("delete[post=" + post + "]");

        final Repository repository = super.getRepository();

        repository.delete(post);
    }

    protected Class<Post> getModelClass() {

        LOGGER.debug("getModelClass");

        final Class<Post> clazz = Post.class;

        LOGGER.trace("getModelClass[returns=" + clazz + "]");
        return clazz;
    }

}