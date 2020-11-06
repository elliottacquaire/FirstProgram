package com.example.firstprogram.autosize

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firstprogram.R
import me.jessyan.autosize.internal.CancelAdapt
import me.jessyan.autosize.internal.CustomAdapt

//当某个 Activity 想放弃适配，请实现 CancelAdapt 接口
//当某个 Activity 的设计图尺寸与在 AndroidManifest 中填写的全局设计图尺寸不同时，可以实现 CustomAdapt 接口扩展适配参数
//首先开启支持 Fragment 自定义参数的功能
//AutoSizeConfig.getInstance().setCustomFragment(true);
class AutoSize1Activity : AppCompatActivity(), CancelAdapt {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_size1)
    }

//    override fun isBaseOnWidth(): Boolean {
//        return true
//    }

    /**
     * 这里使用 iPhone 的设计图, iPhone 的设计图尺寸为 750px * 1334px, 因为这个页面使用副单位进行布局
     * 所以可以直接以像素作为单位返回设计图的尺寸
     * <p>
     * 返回设计图上的设计尺寸
     * {@link #getSizeInDp} 须配合 {@link #isBaseOnWidth()} 使用, 规则如下:
     * 如果 {@link #isBaseOnWidth()} 返回 {@code true}, {@link #getSizeInDp} 则应该返回设计图的总宽度
     * 如果 {@link #isBaseOnWidth()} 返回 {@code false}, {@link #getSizeInDp} 则应该返回设计图的总高度
     * 如果您不需要自定义设计图上的设计尺寸, 想继续使用在 AndroidManifest 中填写的设计图尺寸, {@link #getSizeInDp} 则返回 {@code 0}
     *
     * @return 设计图上的设计尺寸
     */
//    override fun getSizeInDp(): Float {
//       return 1080f
//    }
}