package com.dn.reactnativeimage.svg

import com.dn.reactnativeimage.cache.DiskCacheManager
import com.dn.reactnativeimage.utils.Utils
import java.io.File

object SvgCacheManager : DiskCacheManager() {
  override fun getItem(key: String): File {
    val hashedKey = Utils.hashString(key)
    return File(mCacheDir, hashedKey)
  }

  override fun removeItem(key: String) {
    val file = getItem(key)
    if (file.exists()) {
      file.delete()
    }
  }

  override fun setItem(key: String, item: Any) {
    val file = getItem(key)
    file.writeText(item as String)
  }
}
