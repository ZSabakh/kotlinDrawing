package com.example.drawapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.nio.file.Path
import java.util.*
import java.util.jar.Attributes

class DrawView(context: Context, attributes: AttributeSet) : View(context, attributes) {

    private var drawPath: CustomPath? = null
    private var canvasBitmap: Bitmap? = null
    private var drawPaint: Paint? = null
    private var canvasPaint: Paint? = null
    private var brushSize: Float = 0.toFloat()
    private var color = Color.RED
    private var canvas: Canvas? = null

    init {
        initDrawing()
    }

    private fun initDrawing(){
        drawPath = CustomPath(color, brushSize)
        drawPaint = Paint()
        drawPaint!!.color = color
        drawPaint!!.style = Paint.Style.STROKE
        drawPaint!!.strokeJoin = Paint.Join.ROUND
        drawPaint!!.strokeCap = Paint.Cap.ROUND
        canvasPaint = Paint(Paint.DITHER_FLAG)
        brushSize = 20.toFloat()
    }

    internal inner class CustomPath(var color: Int,
                                             var thickness: Float) : Path() {

    }

}