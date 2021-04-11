package com.meet.cradletaskapplication

import android.content.Context
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import kotlin.math.abs

class Task {

    private val weatherIndices = arrayOf(
        intArrayOf(0, 4),
        intArrayOf(4, 10),
        intArrayOf(10, 16),
        intArrayOf(16, 22),
        intArrayOf(22, 30),
        intArrayOf(30, 35),
        intArrayOf(35, 40),
        intArrayOf(40, 46),
        intArrayOf(46, 53),
        intArrayOf(53, 58),
        intArrayOf(58, 63),
        intArrayOf(63, 67),
        intArrayOf(67, 72),
        intArrayOf(72, 76),
        intArrayOf(76, 80),
        intArrayOf(80, 83),
        intArrayOf(83)
    )


    private fun readFile(context: Context, file: String?): List<List<String>> {
        val document: MutableList<List<String>> = ArrayList()
        try {
            var stringList: MutableList<String> = ArrayList()
            val inputStream: InputStream = context.assets.open(file!!)

            inputStream.bufferedReader().useLines { lines ->
                lines.forEach { line ->
                    if (line != "") {
                        line.trim()

                        if (line.substring(0, 4).trim() != "mo")
                            stringList.addAll(weatherDataValuesFromLine(line))

                    }
                    document.add(stringList)
                    stringList = ArrayList()
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return document
    }

    private fun weatherDataValuesFromLine(line: String): List<String> {

        val stringList: MutableList<String> = ArrayList()
        for (i in weatherIndices.indices) {
            if (weatherIndices[i].size == 2)
                stringList.add(line.substring(weatherIndices[i][0], weatherIndices[i][1]).trim())
            else
                stringList.add(line.substring(weatherIndices[i][0]).trim())
        }
        return stringList
    }

    private fun smallestPointDifference(
        document: List<List<String>>, index1: Int,
        index2: Int, indexReturn: Int
    ): String {
        var smallestTemp = -1f
        var tempSpread = 0f
        var returnValue = ""

        //go through the document
        for (i in 1 until document.size) {

            if (document[i].isEmpty()) {
                continue
            }

            var value1: Float?
            var value2: Float?

            try {
                value1 = if (document[i][index1].contains("*"))
                    document[i][index1].split("*").toTypedArray()[0].toFloatOrNull()
                else
                    document[i][index1].toFloatOrNull()

                value2 = if (document[i][index2].contains("*"))
                    document[i][index2].split("*").toTypedArray()[0].toFloatOrNull()
                else
                    document[i][index2].toFloatOrNull()

            } catch (e: java.lang.NumberFormatException) {
                return "System cannot compare value other than number"
            } catch (e: IndexOutOfBoundsException) {
                return "Column not found to calculate difference"
            } catch (e: Exception) {
                e.printStackTrace()
                return e.message.toString()
            }

            if (value1 == null || value2 == null) {
                return "System cannot compare value other than number"
            }

            tempSpread = try {
                abs(document[i][index1].toFloat() - document[i][index2].toFloat())
            } catch (e: NumberFormatException) {
                val temp1: Array<String> = document[i][index1].split("*").toTypedArray()
                val temp2: Array<String> = document[i][index2].split("*").toTypedArray()
                abs(temp1[0].toFloat() - temp2[0].toFloat())
            } catch (e: IndexOutOfBoundsException) {
                return "Column not found for calculate difference"
            } catch (e: Exception) {
                return e.message.toString()
            }
            try {
                if (smallestTemp == tempSpread) {
                    returnValue += ", " + document[i][indexReturn].trim()
                }
                if (smallestTemp > tempSpread || smallestTemp == -1f) {
                    smallestTemp = tempSpread
                    returnValue = document[i][indexReturn].trim()
                }
            } catch (e: IndexOutOfBoundsException) {
                return "Column not found for return value index"
            } catch (e: Exception) {
                return e.message.toString()
            }


        }
        return returnValue
    }

    fun performTask(
        context: Context, file: String?, index1: Int,
        index2: Int, indexReturn: Int
    ): String {

        val document = readFile(context, file)
        if (document.isEmpty()) {
            return "Data or File not found"
        }

        if (index1 < 0 || index2 < 0 || indexReturn < 0) {
            return "Please provide positive index value"
        }
        return smallestPointDifference(document, index1, index2, indexReturn)
    }
}