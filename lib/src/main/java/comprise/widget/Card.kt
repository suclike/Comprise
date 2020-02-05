package comprise.widget

import android.graphics.Path
import android.graphics.RectF
import comprise.view.Clip
import comprise.view.ContentView
import comprise.view.LayoutSize
import comprise.view.View

class Card(
    width: LayoutSize = LayoutSize.WRAP_CONTENT,
    height: LayoutSize = LayoutSize.WRAP_CONTENT,
    content: View
) : ContentView(width, height) {

    private val rect = RectF()
    private val path = Path()

    init {
        val clip = Clip(width, height, content, path)
        val shadow = Shadow(width, height, clip, path)
        this.content = shadow
    }

    override fun layout(x: Int, y: Int, width: Int, height: Int) {
        super.layout(x, y, width, height)
        path.reset()
        rect.set(0.0f, 0.0f, width.toFloat(), height.toFloat())
        path.addRoundRect(rect, (2 * 3).toFloat(), (2 * 3).toFloat(), Path.Direction.CCW)
    }
}