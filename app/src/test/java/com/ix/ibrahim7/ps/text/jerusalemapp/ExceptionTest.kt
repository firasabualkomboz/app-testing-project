package com.ix.ibrahim7.ps.text.jerusalemapp

import org.junit.Assert
import org.junit.Test

class ExceptionTest {

    @Test
    fun nullPointerExecptionTesting() {
        try {
            val num = arrayOf(1, 2, 3, 4) // implicit type declaration

            var sum = 0
            for (i in 0..3) {
                sum += num[i]
            }

            Assert.fail("Null Pointer Exception")
        } catch (e: NullPointerException) {
        }
    }

    @Test
    fun outOfBoundExceptionTesting() {
        try {
            val strs = arrayOf("dog", "racecar", "car")

            var len = 0
            var res = ""
            for (j in 0 until strs[0].length) {
                val c = strs[0][j]

                res += c
            }

            Assert.fail("Index Out Of Bounds Exception")
        } catch (e: IndexOutOfBoundsException) {
        }
    }
}
