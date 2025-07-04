package com.dn.reactnativeimage.cache

interface ICacheManager {
  fun getItem(key: String): Any?

  fun setItem(key: String, item: Any)

  fun removeItem(key: String)
}
