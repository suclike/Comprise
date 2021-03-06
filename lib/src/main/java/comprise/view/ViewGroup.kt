package comprise.view

import android.graphics.Canvas
import android.view.MotionEvent
import comprise.letElse

abstract class ViewGroup(
    width: LayoutSize = LayoutSize.WRAP_CONTENT,
    height: LayoutSize = LayoutSize.WRAP_CONTENT,
    minWidth: Int = 0,
    minHeight: Int = 0,
    name: String? = null,
    children: List<View> = emptyList()
) : View(width, height, minWidth, minHeight, name) {

    val children = ViewList(this)

    private var touchReceiver: View? = null

    init {
        this.children.addAll(children)
    }

    override fun draw(canvas: Canvas, editMode: Boolean, debugMode: Boolean) {
        children.forEach {
            val saveCount = canvas.save()
            canvas.translate(it.x.toFloat(), it.y.toFloat())
            it.draw(canvas, editMode, debugMode)
            canvas.restoreToCount(saveCount)
        }
    }

    override fun touchEvent(ev: MotionEvent): Boolean {
        touchReceiver.letElse({
            val result: Boolean
            if (ev.action == MotionEvent.ACTION_DOWN) {
                val event = MotionEvent.obtain(ev)
                event.action = MotionEvent.ACTION_CANCEL
                result = it.touchEvent(event)
                event.recycle()
                touchReceiver = null
            } else if (ev.action == MotionEvent.ACTION_UP) {
                val event = MotionEvent.obtain(ev)
                event.setLocation(event.x - it.x.toFloat(), event.y - it.y.toFloat())
                result = it.touchEvent(event)
                touchReceiver = null
                event.recycle()
            } else {
                val event = MotionEvent.obtain(ev)
                event.setLocation(event.x - it.x.toFloat(), event.y - it.y.toFloat())
                result = it.touchEvent(event)
                if (!result)
                    touchReceiver = null
                event.recycle()
            }
            return result
        }, {
            children.forEach {
                val event = MotionEvent.obtain(ev)
                event.setLocation(event.x - it.x.toFloat(), event.y - it.y.toFloat())
                if (event.x >= 0 && event.y >= 0 && event.x <= it.width && event.y <= it.height) {
                    if (it.touchEvent(event)) {
                        touchReceiver = it
                        event.recycle()
                        return true
                    }
                }
                event.recycle()
            }
        })
        return super.touchEvent(ev)
    }
}
