package de.charlex.compose.htmltext.core

import androidx.compose.ui.text.TextLayoutResult

data class RectPx(val left: Float, val top: Float, val right: Float, val bottom: Float) {
    val width: Float get() = right - left
    val height: Float get() = bottom - top
}

fun computeLinkRects(
    layout: TextLayoutResult,
    start: Int,
    end: Int
): List<RectPx> {
    if (start >= end) return emptyList()
    val result = mutableListOf<RectPx>()
    val startLine = layout.getLineForOffset(start)
    val endLine = layout.getLineForOffset(end - 1)
    for (line in startLine..endLine) {
        val lineStart = maxOf(start, layout.getLineStart(line))
        val lineEndExclusive = minOf(end, layout.getLineEnd(line, true))
        if (lineStart >= lineEndExclusive) continue
        val firstBox = layout.getBoundingBox(lineStart)
        val lastBox = layout.getBoundingBox(lineEndExclusive - 1)
        result.add(
            RectPx(
                left = firstBox.left,
                top = firstBox.top,
                right = lastBox.right,
                bottom = lastBox.bottom
            )
        )
    }
    return result
}
