package com.ix.ibrahim7.ps.text.jerusalemapp

import androidx.test.filters.SmallTest
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ParameterizedTest(
    var a: Int,
    var b: Int,
    var c: Int
) {

    @SmallTest
    @Test
    fun sum() {
        val num = arrayOf(a, b, c) // array

        var sum = 0
        for (i in 0..3) {
            sum += num[i]
        }

        Assert.assertEquals(11, sum)
    }

    companion object {

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            println("beforeClass")
        }

        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
            arrayOf(7, 8, 9)

        )
    }
}

@RunWith(Parameterized::class)
class ParameterizedTest2(
    var a: Int
) {

    @SmallTest
    @Test
    fun climb() {

        var dp = IntArray(55, { 0 })
        dp[1] = 1
        dp[2] = 2

        (3..a).forEach { i ->
            dp[i] = dp[i - 1] + dp[i - 2]
        }
        Assert.assertEquals(dp[8], 10)
    }

    companion object {

        @BeforeClass
        @JvmStatic
        fun beforeClass() {
            println("beforeClass")
        }

        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(4),
            arrayOf(5),
            arrayOf(6),
            arrayOf(7),
            arrayOf(8)

        )
    }
}
