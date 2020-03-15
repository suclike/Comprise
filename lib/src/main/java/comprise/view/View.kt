package comprise.view

import android.graphics.Canvas
import android.view.MotionEvent
import kotlin.math.max

abstract class View {
    var desiredWidth: LayoutSize
    var desiredHeight: LayoutSize

    var x = 0
    var y = 0

    var minWidth = 0
    var minHeight = 0
    var measuredWidth = 0   // what the view wants
    var measuredHeight = 0
    var width = 0 // what was finally mediated
    var height = 0

    var parent: View? = null

    constructor(
        width: LayoutSize = LayoutSize.WRAP_CONTENT,
        height: LayoutSize = LayoutSize.WRAP_CONTENT,
        minWidth: Int = 0,
        minHeight: Int = 0
    ) {
        desiredWidth = width
        desiredHeight = height
        this.minWidth = minWidth
        this.minHeight = minHeight
    }

    open fun measure() {
        measuredWidth = when (desiredWidth) {
            LayoutSize.WRAP_CONTENT -> minWidth
            LayoutSize.MATCH_PARENT -> Int.MAX_VALUE
            else -> desiredWidth.size
        }
        measuredHeight = when (desiredHeight) {
            LayoutSize.WRAP_CONTENT -> minHeight
            LayoutSize.MATCH_PARENT -> Int.MAX_VALUE
            else -> desiredHeight.size
        }
    }

    open fun layout(x: Int, y: Int, width: Int, height: Int) {
        this.x = x
        this.y = y
        this.width = max(0, width)
        this.height = max(0, height)
    }

    open fun draw(canvas: Canvas) {}

    open fun touchEvent(ev: MotionEvent) = false

    open fun requestLayout() {
        parent?.requestLayout()
    }

    open fun requestDraw() {
        parent?.requestDraw()
    }
}

