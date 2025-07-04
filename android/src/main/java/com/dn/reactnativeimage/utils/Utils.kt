package com.dn.reactnativeimage.utils

import java.security.MessageDigest

object Utils {
  fun hashString(value: String): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(value.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
  }
}
