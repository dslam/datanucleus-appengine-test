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
import org.datanucleus.util.NucleusLogger;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * StoreManager for extending AppEngine DataNucleus store to be testable.
 */
public class TestDatastoreManager extends DatastoreManager
{
    LocalServiceTestHelper testHelper;

    /**
     * Construct a DatastoreManager.
     * @param clr The ClassLoaderResolver
     * @param nucContext The NucleusContext
     * @param props Properties to store on this StoreManager
     */
    public TestDatastoreManager(ClassLoaderResolver clr, NucleusContext nucContext, Map<String, Object> props)
    throws NoSuchFieldException, IllegalAccessException
    {
        super(clr, nucContext, props);
        NucleusLogger.GENERAL.info(">> TestDatastoreManager.ctr storeManagerKey=" + storeManagerKey);
        testHelper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()).setEnvAppId("DNTest");
        NucleusLogger.GENERAL.info(">> TestDatastoreManager.close calling setUp");
        testHelper.setUp();
        NucleusLogger.GENERAL.info(">> TestDatastoreManager.ctr test environment initialised");
    }

    /* (non-Javadoc)
     * @see com.google.appengine.datanucleus.DatastoreManager#close()
     */
    @Override
    public void close()
    {
        NucleusLogger.GENERAL.info(">> TestDatastoreManager.close calling tearDown");
        testHelper.tearDown();
        super.close();
    }
}