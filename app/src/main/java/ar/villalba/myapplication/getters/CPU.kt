package ar.villalba.myapplication.getters

import android.os.Build
import java.io.File
import java.io.FileFilter
import java.io.RandomAccessFile
import java.util.regex.Pattern

/**
 * The CPU class provides access to various hardware-level details
 * of the device's central processing unit (CPU).
 *
 * It retrieves information such as the number of cores, the current,
 * minimum, and maximum frequencies per core, and the CPU model name.
 * This class uses both system APIs and direct file reads from Linux
 * system paths (e.g., /proc and /sys) to gather data.
 *
 * @property numberOfCores The total number of available processor cores.
 * @property currentCoreFreq A list of current frequencies (in MHz) for each core.
 * @property minCoreFreq A list of minimum supported frequencies (in MHz) for each core.
 * @property maxCoreFreq A list of maximum supported frequencies (in MHz) for each core.
 * @property cpuName The CPU model name, obtained from `/proc/cpuinfo`.
 */
class CPU{

    val numberOfCores = obtainNumberOfCores()
    val currentCoreFreq = obtainCurrentCoreFreq()
    val minCoreFreq = obtainMinCoreFreq()
    val maxCoreFreq = obtainMaxCoreFreq()
    val cpuName = fetchCpuName()

    /**
     * Determines the number of processor cores available on the device.
     *
     * On API level 17 and above, it uses the standard runtime method.
     * For older devices, it falls back to a legacy file-based check.
     *
     * @return The number of CPU cores as a Long value.
     */
    fun obtainNumberOfCores(): Long{
        return (if (Build.VERSION.SDK_INT >= 17){
            Runtime.getRuntime().availableProcessors().toLong()
        }else{
            getNumCoresLegacy()
        }) as Long
    }

    /**
     * Legacy implementation to count CPU cores by checking
     * the `/sys/devices/system/cpu/` directory.
     *
     * This is used when the runtime API is not available
     * (API level < 17).
     *
     * @return The number of CPU cores as an Int.
     */
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

    /**
     * Retrieves the current operating frequency of each CPU core.
     *
     * The values are read from the `scaling_cur_freq` files located
     * under `/sys/devices/system/cpu/`. Frequencies are converted
     * to MHz before being returned.
     *
     * @return A list of current frequencies in MHz, one entry per core.
     */
    fun obtainCurrentCoreFreq(): List<Long>{
        val totalCores = obtainNumberOfCores() - 1

        val coreFreqList = mutableListOf<Long>()

        for (i in 0..totalCores){
            val currentFreqPath = "/sys/devices/system/cpu/cpu${i}/cpufreq/scaling_cur_freq"
            coreFreqList.add(RandomAccessFile(currentFreqPath, "r").use {it.readLine().toLong()/1000})
        }
        return coreFreqList
    }


    /**
     * Retrieves the minimum supported frequency of each CPU core.
     *
     * The values are read from the `cpuinfo_min_freq` files located
     * under `/sys/devices/system/cpu/`. Frequencies are converted
     * to MHz before being returned.
     *
     * @return A list of minimum frequencies in MHz, one entry per core.
     */
    fun obtainMinCoreFreq(): List<Long>{
        val totalCores = obtainNumberOfCores() - 1

        val coreMinFreqList = mutableListOf<Long>()

        for (i in 0..totalCores){
            val minPath = "/sys/devices/system/cpu/cpu${i}/cpufreq/cpuinfo_min_freq"
            coreMinFreqList.add(RandomAccessFile(minPath, "r").use { it.readLine().toLong()/1000 })
        }
        return coreMinFreqList
    }

    /**
     * Retrieves the maximum supported frequency of each CPU core.
     *
     * The values are read from the `cpuinfo_max_freq` files located
     * under `/sys/devices/system/cpu/`. Frequencies are converted
     * to MHz before being returned.
     *
     * @return A list of maximum frequencies in MHz, one entry per core.
     */
    fun obtainMaxCoreFreq(): List<Long>{
        val totalCores = obtainNumberOfCores() - 1

        val coreMinFreqList = mutableListOf<Long>()

        for (i in 0..totalCores){
            val minPath = "/sys/devices/system/cpu/cpu${i}/cpufreq/cpuinfo_max_freq"
            coreMinFreqList.add(RandomAccessFile(minPath, "r").use { it.readLine().toLong()/1000 })
        }
        return coreMinFreqList
    }

    /**
     * Fetches the CPU model name.
     *
     * The name is extracted from the `/proc/cpuinfo` file.
     * Typically, this includes the processor model, architecture,
     * and additional identifiers depending on the device.
     *
     * @return A string containing the CPU name.
     */
    fun fetchCpuName(): String {
        return File("/proc/cpuinfo").readLines().last()
    }

}