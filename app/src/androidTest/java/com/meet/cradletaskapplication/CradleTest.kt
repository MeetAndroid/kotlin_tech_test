package com.meet.cradletaskapplication

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CradleTest {
    @Test
    fun testWeatherTaskSuccess() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val mTask = Task()

        val result = mTask.performTask(context, "weather.dat", 1, 2, 0)
        //then
        Assert.assertEquals(result, "14")
    }

    @Test
    fun testWeatherTaskFileNotFound() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val mTask = Task()

        val result = mTask.performTask(context, "wether.dat", 1, 2, 0)
        //then
        Assert.assertEquals(result, "Data or File not found")

    }


    @Test
    fun testWeatherTaskNegativeIndex() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val mTask = Task()

        val result = mTask.performTask(context, "weather.dat", -1, 2, 0)
        val result2 = mTask.performTask(context, "weather.dat", 1, -2, 0)
        val result3 = mTask.performTask(context, "weather.dat", 1, 2, -1)
        //then
        Assert.assertEquals(result, "Please provide positive index value")
        Assert.assertEquals(result2, "Please provide positive index value")
        Assert.assertEquals(result3, "Please provide positive index value")

    }


    @Test
    fun testWeatherTaskIndexOutOfRange() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val mTask = Task()

        val result = mTask.performTask(context, "weather.dat", 25, 2, 0)
        val result2 = mTask.performTask(context, "weather.dat", 1, 26, 0)
        val result3 = mTask.performTask(context, "weather.dat", 1, 2, 19)
        //then
        Assert.assertEquals(result, "Column not found to calculate difference")
        Assert.assertEquals(result2, "Column not found to calculate difference")
        Assert.assertEquals(result3, "Column not found for return value index")

    }

    @Test
    fun testWeatherTaskNonDigitColumnValue() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val mTask = Task()

        val result = mTask.performTask(context, "weather.dat", 8, 2, 0)
        val result2 = mTask.performTask(context, "weather.dat", 1, 8, 0)
        //then
        Assert.assertEquals(result, "System cannot compare value other than number")
        Assert.assertEquals(result2, "System cannot compare value other than number")

    }
}
