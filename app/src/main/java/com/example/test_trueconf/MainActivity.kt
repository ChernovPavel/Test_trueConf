package com.example.test_trueconf

import android.animation.*
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

const val RUSSIAN_LANGUAGE = "ru"
const val ENGLISH_LANGUAGE = "en"

class MainActivity : AppCompatActivity() {

    private var animator: AnimatorSet? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text)
        val container = findViewById<FrameLayout>(R.id.container)

        container.setOnTouchListener { _, event ->

            animator?.cancel()

            val bottom = container.bottom.toFloat() - textView.height.toFloat()
            val top = container.top.toFloat()

            textView.x = event.x
            textView.y = event.y
            val yCoordinate = textView.y

            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    when (Locale.getDefault().language) {
                        ENGLISH_LANGUAGE -> {
                            textView.setTextColor(Color.RED)
                        }
                        RUSSIAN_LANGUAGE -> {
                            textView.setTextColor(Color.BLUE)
                        }
                    }
                }
            }

            val firstTimeDownAnimation = ObjectAnimator.ofFloat(
                textView,
                View.TRANSLATION_Y,
                textView.y,
                bottom
            ).apply {
                duration =
                    2000L / (container.bottom.toFloat() / (container.bottom.toFloat() - yCoordinate)).toLong()
            }

            val upDownAnimation = ObjectAnimator.ofFloat(
                textView,
                View.TRANSLATION_Y,
                bottom,
                top
            ).apply {
                repeatMode = ObjectAnimator.REVERSE
                repeatCount = ObjectAnimator.INFINITE
                duration = 2000L
            }

            animator = AnimatorSet().apply {
                startDelay = 5000L
                interpolator = LinearInterpolator()
                playSequentially(firstTimeDownAnimation, upDownAnimation)
                start()

            }
            true
        }

        textView.setOnClickListener {
            animator?.cancel()
        }
    }

    override fun onDestroy() {
        animator?.cancel()
        super.onDestroy()
    }
}