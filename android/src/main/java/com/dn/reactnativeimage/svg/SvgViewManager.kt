package com.dn.reactnativeimage.svg

import com.dn.reactnativeimage.enums.ResizeMode
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.SvgViewManagerInterface
import com.facebook.react.viewmanagers.SvgViewManagerDelegate

@ReactModule(name = SvgViewManager.NAME)
class SvgViewManager : SimpleViewManager<SvgView>(),
  SvgViewManagerInterface<SvgView> {
  private val mDelegate: ViewManagerDelegate<SvgView> = SvgViewManagerDelegate(this)

  override fun getDelegate(): ViewManagerDelegate<SvgView> {
    return mDelegate
  }

  override fun getName(): String {
    return NAME
  }

  public override fun createViewInstance(context: ThemedReactContext): SvgView {
    return SvgView(context)
  }

  @ReactProp(name = "xml")
  override fun setXml(view: SvgView?, value: String?) {
    view?.setXml(value)
  }

  @ReactProp(name = "url")
  override fun setUrl(view: SvgView?, value: String?) {
    view?.setUrl(value)
  }

  @ReactProp(name = "resizeMode")
  override fun setResizeMode(view: SvgView?, value: String?) {
    val resizeMode = ResizeMode.entries.find { it.name.lowercase() == value }
    view?.setResizeMode(resizeMode)
  }

  companion object {
    const val NAME = "SvgView"
  }
}
