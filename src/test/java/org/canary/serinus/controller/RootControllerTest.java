package org.canary.serinus.controller;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class RootControllerTest {

    @InjectMocks
    private RootController controller;

    public RootControllerTest() {
        super();
    }

    @Test
    public void rootShouldNotReturnBlankString() {

        final String response = this.controller.root();

        Assert.assertTrue(StringUtils.isNotBlank(response));
    }
}