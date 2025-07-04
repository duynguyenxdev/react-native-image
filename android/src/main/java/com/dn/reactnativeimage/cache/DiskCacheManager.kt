package com.dn.reactnativeimage.cache

import com.dn.reactnativeimage.utils.Utils
import java.io.File

abstract class DiskCacheManager : ICacheManager {
  protected lateinit var mCacheDir: File

  fun setCacheDir(cacheDir: File) {
    mCacheDir = cacheDir
    if (!mCacheDir.exists()) {
      mCacheDir.mkdirs()
    }
  }

  fun getCacheDir(): File = mCacheDir
}
