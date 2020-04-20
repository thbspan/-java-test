package com.test.junit;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.TemporaryFolder;

public class ExternalResourceTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Rule
    public ExternalResource resource = new ExternalResource() {

        @Override
        protected void before() throws Throwable {
            folder.create();
        }

        @Override
        protected void after() {
            folder.delete();
        }
    };

    @Test
    public void testFolder() {
        Assert.assertTrue(folder.getRoot().isDirectory());
    }
}
