package comprise.view

import android.graphics.Canvas
import android.view.MotionEvent

abstract class View {
    var desiredWidth: LayoutSize
    var desiredHeight: LayoutSize

    var x = 0
    var y = 0

    var measuredWidth = 0   // what the view wants
    var measuredHeight = 0
    var width = 0 // what was finally mediated
    var height = 0

    constructor(
        width: LayoutSize = LayoutSize.WRAP_CONTENT,
        height: LayoutSize = LayoutSize.WRAP_CONTENT
    ) {
        desiredWidth = width
        desiredHeight = height
    }

    open fun measure() {
        measuredWidth = when (desiredWidth) {
            LayoutSize.WRAP_CONTENT -> 0
            LayoutSize.MATCH_PARENT -> Int.MAX_VALUE
            else -> desiredWidth.size
        }
        measuredHeight = when (desiredHeight) {
            LayoutSize.WRAP_CONTENT -> 0
            LayoutSize.MATCH_PARENT -> Int.MAX_VALUE
            else -> desiredHeight.size
        }
    }

    open fun layout(x: Int, y: Int, width: Int, height: Int) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    open fun draw(canvas: Canvas) {}

    open fun touchEvent(ev: MotionEvent) = false
}

