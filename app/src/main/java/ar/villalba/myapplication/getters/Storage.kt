package ar.villalba.myapplication.getters

import android.os.Environment
import android.os.StatFs

class Storage {
    val total = getTotalStorageInfo()
    val rest = getRestStorageInfo()
    val used = total - rest

    fun getTotalStorageInfo(): Long{
        val internalPath = Environment.getDataDirectory()
        val stat = StatFs(internalPath.path)

        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong

        val totalSpaceBytes = totalBlocks * blockSize / 1024 / 1024

        return totalSpaceBytes
    }

    fun getRestStorageInfo(): Long{
        val internalPath = Environment.getDataDirectory()
        val stat = StatFs(internalPath.path)

        val blockSize = stat.blockSizeLong
        val availableBlocks = stat.availableBlocksLong

        val availableSpaceBytes = availableBlocks * blockSize / 1024 / 1024

        return availableSpaceBytes
    }

}