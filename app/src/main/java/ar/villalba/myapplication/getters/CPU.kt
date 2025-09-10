package ar.villalba.myapplication.getters

import android.os.Build
import java.io.File
import java.io.FileFilter
import java.io.RandomAccessFile
import java.util.regex.Pattern

class CPU{

    val numberOfCores = obtainNumberOfCores()
    val currentCoreFreq = obtainCurrentCoreFreq()
    val minCoreFreq = obtainMinCoreFreq()
    val maxCoreFreq = obtainMaxCoreFreq()
    val cpuName = fetchCpuName()

    fun obtainNumberOfCores(): Long{
        return (if (Build.VERSION.SDK_INT >= 17){
            Runtime.getRuntime().availableProcessors().toLong()
        }else{
            getNumCoresLegacy()
        }) as Long
    }

    private fun getNumCoresLegacy(): Int {
        class CpuFilter : FileFilter {
            override fun accept(pathname: File): Boolean {
                // Check if filename is "cpu", followed by a single digit number
                return Pattern.matches("cpu[0-9]+", pathname.name)
            }
        }
        return try {
            File("/sys/devices/system/cpu/").listFiles(CpuFilter())!!.size
        } catch (e: Exception) {
            1
        }
    }
    fun obtainCurrentCoreFreq(): List<Long>{
        val totalCores = obtainNumberOfCores() - 1

        val coreFreqList = mutableListOf<Long>()

        for (i in 0..totalCores){
            val currentFreqPath = "/sys/devices/system/cpu/cpu${i}/cpufreq/scaling_cur_freq"
            coreFreqList.add(RandomAccessFile(currentFreqPath, "r").use {it.readLine().toLong()/1000})
        }
        return coreFreqList
    }

    fun obtainMinCoreFreq(): List<Long>{
        val totalCores = obtainNumberOfCores() - 1

        val coreMinFreqList = mutableListOf<Long>()

        for (i in 0..totalCores){
            val minPath = "/sys/devices/system/cpu/cpu${i}/cpufreq/cpuinfo_min_freq"
            coreMinFreqList.add(RandomAccessFile(minPath, "r").use { it.readLine().toLong()/1000 })
        }
        return coreMinFreqList
    }
    fun obtainMaxCoreFreq(): List<Long>{
        val totalCores = obtainNumberOfCores() - 1

        val coreMinFreqList = mutableListOf<Long>()

        for (i in 0..totalCores){
            val minPath = "/sys/devices/system/cpu/cpu${i}/cpufreq/cpuinfo_max_freq"
            coreMinFreqList.add(RandomAccessFile(minPath, "r").use { it.readLine().toLong()/1000 })
        }
        return coreMinFreqList
    }
    fun fetchCpuName(): String {
        return File("/proc/cpuinfo").readLines().last()
    }

}