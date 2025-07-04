package com.dn.reactnativeimage.svg

import android.content.Context
import android.graphics.Canvas
import android.graphics.Picture
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.caverock.androidsvg.SVG
import com.dn.reactnativeimage.enums.ResizeMode
import com.dn.reactnativeimage.models.Dimension
import androidx.core.graphics.withSave
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class SvgView : View {
  private val TAG = "SvgView"

  private val client = OkHttpClient()

  private var mSvgPicture: Picture? = null
  private var mResizeMode: ResizeMode? = null

  constructor(context: Context?) : super(context)
  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  )

  private fun getViewDimension(): Dimension {
    if (mSvgPicture == null) {
      return Dimension(width, height)
    }
    val viewWidth = if (width == 0) mSvgPicture!!.width else width
    val viewHeight = if (height == 0) mSvgPicture!!.height else height
    return Dimension(viewWidth, viewHeight)
  }

  fun setXml(xml: String?) {
    if (xml.isNullOrEmpty()) {
      mSvgPicture = null
    } else {
      val svg = SVG.getFromString(xml)
      mSvgPicture = svg.renderToPicture()
    }
    invalidate()
  }

  fun setUrl(url: String?) {
    if (url.isNullOrEmpty()) {
      mSvgPicture = null
      invalidate()
    } else {
      val cachedSvgFile = SvgCacheManager.getItem(url)
      if (cachedSvgFile.exists()) {
        CoroutineScope(Dispatchers.IO).launch {
          cachedSvgFile.inputStream().use {
            val svg = SVG.getFromInputStream(it)
            val picture = svg.renderToPicture()

            withContext(Dispatchers.Main){
              mSvgPicture = picture
              invalidate()
            }
          }
        }
      }
      else {
        fetchSvg(url, onSuccess = { result ->
          SvgCacheManager.setItem(url, result)
          setXml(result)
        }, onError = { error ->
          Log.e(TAG, "Failed to fetch svg from $url, error: $error")
        })
      }
    }
  }

  fun setResizeMode(mode: ResizeMode?) {
    mResizeMode = mode
    invalidate()
  }

  private fun fetchSvg(
    url: String,
    onSuccess: (result: String) -> Unit,
    onError: (error: Exception) -> Unit
  ) {
    val request = Request.Builder().url(url).build()
    client.newCall(request).enqueue(object : Callback {
      override fun onFailure(call: Call, e: IOException) {
        post {
          onError(e)
        }
      }

      override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
          post {
            onError(Exception("Failed to fetch svg: Response = $response"))
          }
          return
        }

        val svgXml = response.body?.string()

        if (svgXml.isNullOrEmpty()) {
          post {
            onError(Exception("Failed to fetch svg: SVG is empty"))
          }
          return
        }

        post {
          onSuccess(svgXml)
        }
      }
    })
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    mSvgPicture?.let {
      val dimension = getViewDimension()
      val viewWidth = dimension.width.toFloat()
      val viewHeight = dimension.height.toFloat()
      val pictureWidth = it.width
      val pictureHeight = it.height
      val resizeMode = mResizeMode ?: ResizeMode.CONTAIN

      canvas.withSave {
        when (resizeMode) {
          ResizeMode.CONTAIN -> {
            val scale = minOf(viewWidth / pictureWidth, viewHeight / pictureHeight)
            canvas.scale(scale, scale)
          }

          ResizeMode.COVER -> {
            val scale = maxOf(viewWidth / pictureWidth, viewHeight / pictureHeight)
            canvas.scale(scale, scale)
          }

          ResizeMode.STRETCH -> {
            canvas.scale(viewWidth / pictureWidth, viewHeight / pictureHeight)
          }
        }

        drawPicture(it)
      }
    }
  }
}
