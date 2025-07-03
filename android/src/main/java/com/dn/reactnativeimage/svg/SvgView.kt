package com.dn.reactnativeimage.svg

import android.content.Context
import android.graphics.Canvas
import android.graphics.Picture
import android.util.AttributeSet
import android.view.View
import com.caverock.androidsvg.SVG
import com.dn.reactnativeimage.enums.ResizeMode
import com.dn.reactnativeimage.models.Dimension
import androidx.core.graphics.withSave

class SvgView : View {
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
    if (xml == null) {
      mSvgPicture = null
    } else {
      val svg = SVG.getFromString(xml)
      mSvgPicture = svg.renderToPicture()
    }
    invalidate()
  }

  fun setUrl(url: String?) {}

  fun setResizeMode(mode: ResizeMode?) {
    mResizeMode = mode
    invalidate()
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
