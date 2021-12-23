package com.ix.ibrahim7.ps.text.jerusalemapp

import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@LargeTest
@Suite.SuiteClasses(
    ExampleUnitTest::class,
    ParameterizedTest::class,
    ParameterizedTest2::class,
    Mock::class,
    ExceptionTest::class
)
class TestSuite
