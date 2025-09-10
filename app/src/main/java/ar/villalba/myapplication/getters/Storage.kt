package ar.villalba.myapplication.getters

import android.os.Environment
import android.os.StatFs

class Storage {


    fun getTotalStorageInfo(): Long{
        val internalPath = Environment.getDataDirectory()
        val stat = StatFs(internalPath.path)

        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val availableBlocks = stat.availableBlocksLong

        val totalSpaceBytes = totalBlocks * blockSize / 1024 / 1024
        val availableSpaceBytes = availableBlocks * blockSize

        return totalSpaceBytes
    }
    fun getRestStorageInfo(): Long{
        val internalPath = Environment.getDataDirectory()
        val stat = StatFs(internalPath.path)

        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val availableBlocks = stat.availableBlocksLong

        val totalSpaceBytes = totalBlocks * blockSize
        val availableSpaceBytes = availableBlocks * blockSize / 1024 / 1024

        return availableSpaceBytes
    }

}