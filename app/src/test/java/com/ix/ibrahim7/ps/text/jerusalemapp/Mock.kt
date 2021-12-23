package com.ix.ibrahim7.ps.text.jerusalemapp

import com.ix.ibrahim7.ps.text.jerusalemapp.model.dataGetter
import org.junit.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.IOException

class Mock {

    @Mock
    val data = dataGetter()

    @Before
    @Throws(IOException::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        println("beforeTest")
    }

    @Test
    fun getData(){
        val text = data.getText("Hello")
        Mockito.`when`(text).thenReturn("Hello")
        Assert.assertEquals("Hello", text)



    }




    @After
    fun afterTest() {
        println("afterTest")
    }

}
