package com.example.firstprogram.customview

import android.content.Context
import android.text.Layout
import android.text.StaticLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.firstprogram.R
import com.orhanobut.logger.Logger

class MultTextView : RelativeLayout, View.OnClickListener {

    private var textView: TextView? = null
    private var expandView: TextView? = null
    private var backView: TextView? = null

        private var valueString: String? = null
    private var isExpand: Boolean = false

    constructor(mContext: Context) : this(mContext,null)

    constructor(mContext: Context, attrs: AttributeSet?) : this(mContext, attrs!!,0)

    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr:Int) : super(mContext, attrs,defStyleAttr) {
        initView()
    }

    init {
        Logger.i("init-------")
        initView()
    }

    private fun initView() {
        val view = LayoutInflater.from(context).inflate(R.layout.mult_textview, this, true);
        textView = view.findViewById(R.id.textView)
        expandView = view.findViewById(R.id.expandView)
        backView = view.findViewById(R.id.backView)

        expandView?.setOnClickListener(this)
        backView?.setOnClickListener(this)

//        valueString.let {
//            textView?.setText(it)
//        }

        expandView?.visibility = View.GONE
        backView?.visibility = View.GONE


    }


    /**
     * 判断是否有多行 文字处理
     */
    private fun isLastIndexForLimit(): Boolean {
//        return textView?.lineCount?:0 > 1
        Logger.d("----${getLines()}-----")
        return getLines() > 1
    }

    private fun getLines(): Int {
        //实例化StaticLayout 传入相应参数
        val staticLayout = StaticLayout(
            textView?.text, textView?.paint,
            measuredWidth - paddingLeft - paddingRight,
            Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false
        )
        return staticLayout.lineCount
    }

    fun setTextView(value: String?) {
        this.valueString = value?.trim()
        Logger.d("set-------")
        valueString.let {
            textView?.text = it
//            Logger.d("set-------${getLines()}")
            if (isLastIndexForLimit()) {
                if (isExpand) {
                    backView?.visibility = View.VISIBLE
                    expandView?.visibility = View.GONE
                    textView?.maxLines = getLines()
                } else {
                    backView?.visibility = View.GONE
                    expandView?.visibility = View.VISIBLE
                    textView?.maxLines = 1
                }

            } else {
                backView?.visibility = View.GONE
                expandView?.visibility = View.GONE
            }
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.expandView -> {
                isExpand = true
//                textView?.maxLines = textView?.lineCount?:1
//                backView?.visibility = View.VISIBLE
//                expandView?.visibility = View.GONE

                setTextView(valueString)
            }
            R.id.backView -> {
                isExpand = false
//                textView?.maxLines = 1
//                backView?.visibility = View.GONE
//                expandView?.visibility = View.VISIBLE
                setTextView(valueString)
            }
        }
    }
}