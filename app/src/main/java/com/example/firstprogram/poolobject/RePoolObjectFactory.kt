package com.example.firstprogram.poolobject

import androidx.core.util.Pools
import com.example.firstprogram.ipc.Book

object RePoolObjectFactory  {
    var sp = Pools.SynchronizedPool<Book>(1000)

}