package com.xueqiu.toolbox.ui

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

class DesignUtils {

    companion object {

        @JvmStatic
        fun getAttrDrawable(context: Context, attrId: Int): Drawable? {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(attrId, typedValue, true)
            val drawableRes = typedValue.data
            return getDrawable(context, drawableRes)
        }

        @JvmStatic
        fun getAttrColor(context: Context, attrId: Int): Int {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(attrId, typedValue, true)
            val colorRes = typedValue.data
            return getColor(context, colorRes)
        }

        @JvmStatic
        fun getRipperDrawable(context: Context): Drawable? {
            val attrs = intArrayOf(android.R.attr.selectableItemBackground)
            val typedArray = context.obtainStyledAttributes(attrs)
            val ripperDrawable = typedArray.getDrawable(0)
            typedArray.recycle()
            return ripperDrawable
        }

        @JvmStatic
        fun setRipperEffect(view: View) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.foreground = getRipperDrawable(view.context)
            } else {
                view.background = getRipperDrawable(view.context)
            }
        }

        @JvmStatic
        fun getDrawable(context: Context, @DrawableRes drawableRes: Int): Drawable {
            return context.resources.getDrawable(drawableRes, context.theme)
        }

        @JvmStatic
        fun getColor(context: Context, @ColorRes colorRes: Int): Int {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.resources.getColor(colorRes, context.theme)
            } else {
                context.resources.getColor(colorRes)
            }
        }

        @JvmStatic
        fun setRadius(view: View, radius: Float) {
            view.background?.apply {
                if (this is GradientDrawable) {
                    this.cornerRadius = radius
                    view.background = this
                }
            }
        }

        @JvmStatic
        fun setRadius(drawable: Drawable, radius: Float): Drawable {
            if (drawable is GradientDrawable) {
                drawable.cornerRadius = radius
            }
            return drawable
        }

        @JvmStatic
        fun dpToPx(context: Context, value: Float): Float {
            val scale = context.resources.displayMetrics.density
            return value * scale + 0.5f
        }

        @JvmStatic
        fun spToPx(context: Context, value: Float): Float {
            val fontScale = context.resources.displayMetrics.scaledDensity
            return value * fontScale + 0.5f
        }

        @JvmStatic
        fun pxToDp(context: Context, value: Float): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics)
        }

        @JvmStatic
        fun pxToSp(context: Context, value: Float): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, context.resources.displayMetrics)
        }

        @JvmStatic
        fun setGradient(originalBitmap: Bitmap, @ColorInt startColor: Int, @ColorInt endColor: Int): Bitmap {
            val width = originalBitmap.width
            val height = originalBitmap.height
            val updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            val canvas = Canvas(updatedBitmap)
            canvas.drawBitmap(originalBitmap, 0f, 0f, null)

            val paint = Paint()
            val shader = LinearGradient(0f, 0f, 0f, height.toFloat(), startColor, endColor, Shader.TileMode.CLAMP)
            paint.shader = shader
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
            return updatedBitmap
        }

    }

}