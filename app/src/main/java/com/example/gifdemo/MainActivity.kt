package com.example.gifdemo

import android.R.attr
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.util.Log
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.gifdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    private var isClick = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tv1.text = "123456"
        binding.tv2.text = "789000"
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (!isClick && ev?.action == MotionEvent.ACTION_DOWN) {
            isClick = true
//            loadThirdGif()
            loadGif()
            loadStatic()
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun loadThirdGif() {
        val gifDrawable = pl.droidsonroids.gif.GifDrawable(assets, "smile.gif")

        val spannable = SpannableString("smile")
        val imageSpan = CustomImageSpan(gifDrawable)
        spannable.setSpan(imageSpan, 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tv1.post { binding.tv1.text = spannable }
        gifDrawable.start()
    }

    private fun loadGif() {

        Glide.with(this)
            .asGif()
            .load("https://5b0988e595225.cdn.sohucs.com/images/20170919/1ce5d4c52c24432e9304ef942b764d37.gif")
            .addListener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e(TAG, "onLoadFailed error: ${e?.message}")
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable?,
                    model: Any?,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            .into(object : SimpleTarget<GifDrawable>() {
                override fun onResourceReady(
                    resource: GifDrawable,
                    transition: Transition<in GifDrawable>?
                ) {
                    resource.setLoopCount(GifDrawable.LOOP_FOREVER)
                    val imageSpan = CustomImageSpan(resource)
                    val spannable = SpannableString("smile")
                    spannable.setSpan(
                        imageSpan,
                        0,
                        5,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    binding.tv1.post { binding.tv1.text = spannable }
                    binding.iv1.post { binding.iv1.setImageDrawable(resource) }
                    resource.start()
                }
            })
    }

    private fun loadStatic() {
        Glide.with(this)
            .load("https://5b0988e595225.cdn.sohucs.com/images/20170919/1ce5d4c52c24432e9304ef942b764d37.gif")
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e(TAG, "onLoadFailed error: ${e?.message}")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

            })
            .into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    val imageSpan = CustomImageSpan(resource)
                    val spannable = SpannableString("smile")
                    spannable.setSpan(
                        imageSpan,
                        0,
                        5,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    binding.tv2.post { binding.tv2.text = spannable }
                    binding.iv2.post { binding.iv2.setImageDrawable(resource) }
                }
            })
    }
}
