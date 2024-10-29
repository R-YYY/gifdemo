package com.example.gifdemo

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.gifdemo.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding

    private var isClick = false

    private lateinit var layoutManage: GridLayoutManager

    private lateinit var concatAdapter: ConcatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        concatAdapter =
            ConcatAdapter(ConcatAdapter.Config.Builder().setIsolateViewTypes(true).build())
                .apply {
                    addAdapter(TitleAdapter())
                    addAdapter(TextAdapter())
                    addAdapter(TitleAdapter())
                    addAdapter(TextAdapter())
                }
        binding.btnJump.setOnClickListener {
            kotlin.runCatching {
                val intent = Intent()
                intent.setClass(this, MotionActivity::class.java)
                startActivity(intent)
            }.onFailure {
                Log.d(TAG, "${it.message}")
            }
        }
        layoutManage = GridLayoutManager(this, 4)
        layoutManage.spanSizeLookup = CustomLookUpSize(concatAdapter)
        binding.rv.layoutManager = layoutManage
        binding.rv.adapter = concatAdapter
        binding.tv1.text = "textview 1"
        binding.tv2.text = "textview 2"
    }

    class CustomLookUpSize(private val concatAdapter: ConcatAdapter) :
        GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {

            val adapterCount: Int = concatAdapter.adapters.size
            var cumulativePosition = 0
            for (i in 0 until adapterCount) {
                val adapter = concatAdapter.adapters[i]
                val itemCount: Int = adapter.itemCount
                if (position >= cumulativePosition && position < cumulativePosition + itemCount) {
                    // 当 position 处于当前 Adapter 的范围内，返回对应的 span 大小
                    if (adapter is TitleAdapter) {
                        Log.d("ry-debug", "$position:is titleAdapter")
                        return 4
                    }

                    return if (itemCount % 4 != 0 && position == cumulativePosition + itemCount - 1) {
                        val last = itemCount % 4
                        Log.d("ry-debug", "$position:4")
                        5 - last // 如果是当前 Adapter 的最后一个且未填满一行，占满一行
                    } else {
                        Log.d("ry-debug", "$position:1")
                        1
                    }
                    // 否则占 1 个 span
                }
                cumulativePosition += itemCount
            }
            return -1 // 处理异常情况
        }
    }


    class TitleAdapter : RecyclerView.Adapter<TitleAdapter.TitleViewHolder>() {
        inner class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.layout_title, null, false)
            return TitleViewHolder(view)
        }

        override fun getItemCount(): Int {
            return 1
        }

        override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
            holder.itemView.findViewById<TextView>(R.id.tv_title)?.let {
                it.text = "啊哈哈哈哈哈哈哈"
            }
        }
    }

    class TextAdapter : RecyclerView.Adapter<TextAdapter.TextViewHolder>() {

        inner class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.layout_text, null, false)
            return TextViewHolder(view)
        }

        override fun getItemCount(): Int {
            return 7
        }

        override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
            Log.d("ry-debug", "text holder: $position")
            holder.itemView.findViewById<Button>(R.id.btn_text)?.let {
                it.text = "text $position"
            }
        }
    }

    class People(val name: String, val age: Int)

    private fun mockData(): ArrayList<People> {
        val result = ArrayList<People>()
        result.add(People("11111", 1))
        result.add(People("22222", 2))
        result.add(People("33333", 3))
        result.add(People("44444", 4))
        result.add(People("55555", 5))
        return result
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        if (!isClick && ev?.action == MotionEvent.ACTION_DOWN) {
//            isClick = true
//            loadGif()
//            loadStatic()
//        }
        return super.dispatchTouchEvent(ev)
    }

    private fun loadGif() {

        Glide.with(this)
            .asGif()
//            .load("https://cdn.pixabay.com/photo/2023/11/30/08/36/chhathpuja-8421051_1280.jpg")
            .load("https://5b0988e595225.cdn.sohucs.com/images/20170919/1ce5d4c52c24432e9304ef942b764d37.gif")
            .addListener(object : RequestListener<GifDrawable> {
                override fun onResourceReady(
                    resource: GifDrawable,
                    model: Any,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e(TAG, "onLoadFailed error: ${e?.message}")
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
                    binding.tv1.post {
                        resource.callback = object : Drawable.Callback {
                            override fun invalidateDrawable(p0: Drawable) {
                                binding.tv1.invalidate()
                            }

                            override fun scheduleDrawable(p0: Drawable, p1: Runnable, p2: Long) {
                                binding.tv1.scheduleDrawable(p0, p1, p2)
                            }

                            override fun unscheduleDrawable(p0: Drawable, p1: Runnable) {
                                binding.tv1.unscheduleDrawable(p0, p1)
                            }
                        }
                        binding.tv1.text = spannable
                    }
//                    binding.iv1.post { binding.iv1.setImageDrawable(resource) }
                    resource.start()
                }
            })
    }

    private fun loadStatic() {
        Glide.with(this)
//            .load("https://cdn.pixabay.com/photo/2023/11/30/08/36/chhathpuja-8421051_1280.jpg")
            .load("https://5b0988e595225.cdn.sohucs.com/images/20170919/1ce5d4c52c24432e9304ef942b764d37.gif")
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e(TAG, "onLoadFailed error: ${e?.message}")
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
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
