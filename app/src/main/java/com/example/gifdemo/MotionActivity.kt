package com.example.gifdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.gifdemo.databinding.ActivityMotionBinding

class MotionActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MotionActivity"
    }

    private lateinit var binding: ActivityMotionBinding

    private val adapter = MotionAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMotionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRV()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRV() {
        binding.testRv.adapter = adapter
        binding.testRv.layoutManager = LinearLayoutManager(this)
        binding.testRv.addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.i(TAG, "-----------onScrollStateChanged-----------");
                Log.i(TAG, "newState: $newState");
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.i(TAG, "-----------onScrolled-----------");
                Log.i(TAG, "dy: $dy");
                Log.i(TAG, "CHECK_SCROLL_UP: " + recyclerView.canScrollVertically(1));
                Log.i(TAG, "CHECK_SCROLL_DOWN: " + recyclerView.canScrollVertically(-1));
            }
        })
    }

    class MotionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView = itemView.findViewById<TextView>(R.id.btn_text)
        fun onBindData(data: String) {
            textView?.text = data
        }
    }

    class MotionAdapter : RecyclerView.Adapter<MotionViewHolder>() {

        private val mData = mutableListOf<String>().apply {
            for (i in 0..20) {
                add(i, "这是第${i}个")
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MotionViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.layout_text, parent, false)
            return MotionViewHolder(view);
        }

        override fun onBindViewHolder(holder: MotionViewHolder, position: Int) {
            if (position >= itemCount) {
                return
            }
            holder.onBindData(mData[position])
        }

        override fun getItemCount(): Int {
            return mData.size
        }
    }

}