package com.ix.ibrahim7.ps.text.jerusalemapp

import org.junit.*
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    companion object {

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            println("beforeClass")
        }

        @AfterClass
        @JvmStatic
        fun afterClass() {
            println("afterClass")
        }
    }

    @Before
    fun beforeTest() {
        println("beforeTest")
    }

    @After
    fun afterTest() {
        println("afterTest")
    }

    @Test
    fun sum() {
        val num = arrayOf(1, 2, 3, 4)

        var sum = 0
        for (i in 0..3) {
            sum += num[i]
        }

        assertEquals(11, sum)
    }

    @Test
    fun longestCommonPrefix() {

        val strs = arrayOf("dog", "racecar", "car")

        var len = 0
        var res = ""
        for (j in 0 until strs[0].length) {
            val c = strs[0][j]

            res += c
            println(res)
            assertEquals("d", res)
        }
    }

    @Test
    fun climb() {

        var dp = IntArray(55, { 0 })
        dp[1] = 1
        dp[2] = 2

        (3..8).forEach { i ->
            dp[i] = dp[i - 1] + dp[i - 2]
        }
        assertEquals(dp[8], 10)
    }

    @Test
    fun countAndSay() {
        var last = "1"
        (1 until 2).forEach {
            var nextStr = ""
            var cnt = 0
            for (i in 0 until last.length) {
                if (i == 0) {
                    cnt++
                } else if (last[i] == last[i - 1]) {
                    cnt++
                } else {
                    nextStr += "${cnt}${last[i - 1]}"
                    cnt = 1
                }
                if (i == last.length - 1)
                    nextStr += "${cnt}${last[i]}"
            }
            last = nextStr

            assertTrue("12" == last)
        }
    }

    @Test
    fun reverseInt() {

        var res = 0
        var n = 10
        while (n != 0) {
            if (Math.abs(res) > Int.MAX_VALUE / 10)

                res = res * 10 + n % 10
            n /= 10
            assertTrue("5" > n.toString())
        }
    }
}
