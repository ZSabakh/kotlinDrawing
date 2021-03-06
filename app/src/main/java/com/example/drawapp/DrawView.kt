package com.example.drawapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class DrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var drawPath: CustomPath? = null
    private var brushSize: Float = 0.toFloat()
    private var drawPaint: Paint? = null
    private var canvasPaint: Paint? = null
    private var color = Color.BLACK
    private var canvasBitmap: Bitmap? = null
    private var canvas: Canvas? = null
    private val drawedPaths = ArrayList<CustomPath>()

    init {
        setUpDrawing()
    }

    private fun setUpDrawing() {
        drawPath = CustomPath(color, brushSize)
        drawPaint = Paint()
        drawPaint!!.color = color
        drawPaint!!.style = Paint.Style.STROKE
        drawPaint!!.strokeJoin = Paint.Join.ROUND
        drawPaint!!.strokeCap = Paint.Cap.ROUND
        canvasPaint = Paint(Paint.DITHER_FLAG)

        brushSize = 20.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, wprev: Int, hprev: Int) {
        super.onSizeChanged(w, h, wprev, hprev)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(canvasBitmap!!)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(canvasBitmap!!, 0f, 0f, canvasPaint)

        for(lines in drawedPaths){
            drawPaint!!.strokeWidth =  lines.brushThickness
            drawPaint!!.color = lines.color
            canvas.drawPath(lines, drawPaint!!)

        }

        if (!drawPath!!.isEmpty) {
            drawPaint!!.strokeWidth = drawPath!!.brushThickness
            drawPaint!!.color = drawPath!!.color
            canvas.drawPath(drawPath!!, drawPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x // Touch event of X coordinate
        val touchY = event.y // touch event of Y coordinate

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                drawPath!!.color = color
                drawPath!!.brushThickness = brushSize

                drawPath!!.reset()
                drawPath!!.moveTo(
                    touchX,
                    touchY
                )
            }

            MotionEvent.ACTION_MOVE -> {
                drawPath!!.lineTo(
                    touchX,
                    touchY
                )
            }

            MotionEvent.ACTION_UP -> {
                drawPath = CustomPath(color, brushSize)
                drawedPaths.add(drawPath!!)
            }
            else -> return false
        }

        invalidate()
        return true
    }

    internal inner class CustomPath(var color: Int, var brushThickness: Float) : Path()
}
