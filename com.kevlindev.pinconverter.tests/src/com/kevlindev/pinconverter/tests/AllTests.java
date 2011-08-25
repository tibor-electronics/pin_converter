package com.kevlindev.pinconverter.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	com.kevlindev.utils.AllTests.class,
	com.kevlindev.pinconverter.AllTests.class,
	com.kevlindev.pinconverter.parsing.AllTests.class
})
public class AllTests {

}
