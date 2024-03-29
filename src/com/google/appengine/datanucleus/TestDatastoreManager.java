/**********************************************************************
Copyright (c) 2012 Andy Jefferson and others. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Contributors:
    ...
**********************************************************************/
package com.google.appengine.datanucleus;

import java.util.Map;

import org.datanucleus.ClassLoaderResolver;
import org.datanucleus.NucleusContext;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * StoreManager for extending AppEngine DataNucleus store to be testable.
 * Creates a HRD local datastore for usage by the tests.
 */
public class TestDatastoreManager extends DatastoreManager
{
    LocalServiceTestHelper testHelper;

    /**
     * Construct a DatastoreManager.
     * @param clr The ClassLoaderResolver
     * @param nucCtx The NucleusContext
     * @param props Properties to store on this StoreManager
     */
    public TestDatastoreManager(ClassLoaderResolver clr, NucleusContext nucCtx, Map<String, Object> props)
    throws NoSuchFieldException, IllegalAccessException
    {
        super(clr, nucCtx, props);

        // Create High-Replication-Datastore simulation
        LocalDatastoreServiceTestConfig config = new LocalDatastoreServiceTestConfig();
        config.setDefaultHighRepJobPolicyUnappliedJobPercentage(1); // Can't be <1 and still have multi-XG txns
        config.setStoreDelayMs(0);
        testHelper = new LocalServiceTestHelper(config).setEnvAppId("DNTest");
        testHelper.setUp();
    }

    /* (non-Javadoc)
     * @see com.google.appengine.datanucleus.DatastoreManager#close()
     */
    @Override
    public void close()
    {
        testHelper.tearDown();
        super.close();
    }
}