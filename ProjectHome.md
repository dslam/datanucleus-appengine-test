To use "datanucleus-appengine" with JUnit tests you have to start a LocalServiceTestHelper, and call setUp()/tearDown(). This project provides a wrapper XXXStoreManager that handles the creation of the LocalServiceTestHelper and calling of setUp/tearDown.

This means that "datanucleus-appengine" will be directly testable using third party JDO/JPA test suites such as those for DataNucleus and the JDO TCK. Note that the LocalServiceTestHelper is for HRD with 1% unapplied jobs, which seems to be as close as possible to a reliable testable datastore as we can get.


To make use of "datanucleus-appengine" in a testsuite all that you need to do is
**add "datanucleus-appengine", "datanucleus-appengine-test" and "appengine-testing" to the CLASSPATH** set the "datanucleus.connectionURL" to "appenginetest"
This will then start the TestDatastoreManager in this project which handles the LocalServiceTestHelper.