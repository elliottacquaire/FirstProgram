package com.example.firstprogram.pdf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.firstprogram.R
import com.github.chrisbanes.photoview.PhotoView
import kotlinx.android.synthetic.main.activity_pdf_reader.*
import java.io.File

/**
* pdf 读取展示
 * 利用PdfRenderer类实现
* */

class PdfReaderActivity : AppCompatActivity() {

    private var mDescriptor: ParcelFileDescriptor? = null
    private var mRenderer: PdfRenderer? = null
    private var pathStr: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_reader)

        val outMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(outMetrics)
        val widthPixels: Int = outMetrics.widthPixels
        val heightPixels: Int = outMetrics.heightPixels
        pathStr = intent.getStringExtra("pathFile") ?: ""

        openRender(widthPixels,heightPixels)
    }

    private fun openRender( widthPixels : Int, heightPixels:Int){
        //初始化PdfRender
        try {
            val file = File(pathStr)
            mDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            mDescriptor?.let {
                mRenderer = PdfRenderer(it)
            }
            view_pager.adapter = MyAdapter(mRenderer!!, this,widthPixels,heightPixels)
        }catch (e: Exception){}


    }

    override fun onDestroy() {
        super.onDestroy()
        mRenderer?.close()
        mDescriptor?.close()
    }


}

    /**
    * 使用 ViewPager，切换回上一个 Fragment 页面时（已经初始化完毕），不会回调任何生命周期方法以及onHiddenChanged()，只有 setUserVisibleHint(boolean isVisibleToUser) 会被回调。
    setUserVisibleHint(boolean isVisibleToUser) 方法总是会优先于 Fragment 生命周期函数的调用。
    * */
/**
 * 是否调用了setUserVisibleHint方法。处理show+add+hide模式下，默认可见 Fragment 不调用
 * onHiddenChanged 方法，进而不执行懒加载方法的问题。
 */
/**
 * 当使用ViewPager+Fragment形式会调用该方法时，setUserVisibleHint会优先Fragment生命周期函数调用，
 * 所以这个时候就,会导致在setUserVisibleHint方法执行时就执行了懒加载，
 * 而不是在onResume方法实际调用的时候执行懒加载。所以需要这个变量
 */
/**
 * 如果 behavior 的值为 BEHAVIOR_SET_USER_VISIBLE_HINT，那么当 Fragment 对用户的可见状态发生改变时，setUserVisibleHint 方法会被调用。
如果 behavior 的值为 BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT ，那么当前选中的 Fragment 在 Lifecycle.State#RESUMED 状态 ，其他不可见的 Fragment 会被限制在 Lifecycle.State#STARTED 状态。

 * */
class MyAdapter(private val mRendererPdf: PdfRenderer, val context: Context,
                private val widthPixels : Int, private val heightPixels:Int) : PagerAdapter() {

    override fun getCount(): Int {
        return mRendererPdf.pageCount
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pdf, null)
        val pvPdf = view.findViewById(R.id.iv_pdf) as PhotoView
        pvPdf.isZoomable = true
        if (count <= position) {
            return view
        }
        val currentPage: PdfRenderer.Page = mRendererPdf.openPage(position)
        val bitmap: Bitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888)
        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        pvPdf.setImageBitmap(bitmap)
        //关闭当前Page对象
        currentPage.close()
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //销毁需要销毁的视图
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}