package com.example.firstprogram.service

import android.app.Service
import android.content.Intent

import android.os.IBinder
import android.util.Log
import com.example.firstprogram.IBinderPool
import com.example.firstprogram.ICallback
import com.example.firstprogram.ipc.Book
import com.example.firstprogram.ipc.IBookManagerAidlInterface
import com.example.firstprogram.ipc.StudentBean
import com.example.firstprogram.ipc.StudentBeanAidlInterface
import java.util.concurrent.CopyOnWriteArrayList


class BinderPoolService  : Service()  {


    class BookManagerImpl : IBookManagerAidlInterface.Stub() {

        var bs: MutableList<Book> = CopyOnWriteArrayList()

        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {

        }

        override fun getBooks(): MutableList<Book> {
            return bs
        }


        init {
            val book = Book()
            book.id = 10
            book.name = "Harray porllit"
            book.price = 105
            bs.add(book)
        }
    }

    class StudentManagerImpl : StudentBeanAidlInterface.Stub() {

        var ss: MutableList<StudentBean> = CopyOnWriteArrayList()

        val students: List<Any>
            get() = ss

        init {
            ss.add(StudentBean())
        }

        override fun getStudentBeanList(): MutableList<StudentBean> {
            return ss
        }

        override fun addStudentBeanInOut(bean: StudentBean?) {

        }

        override fun addStudentBeanIn(bean: StudentBean?) {

        }

        override fun addStudentBeanOut(bean: StudentBean?) {

        }

        override fun addCallback(callback: ICallback?) {

        }
    }

    var bookManager = BookManagerImpl()
    var studentManager = StudentManagerImpl()

    private var binderPool = object : IBinderPool.Stub() {

        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {}

        override fun queryBinder(binderCode: Int): IBinder? {
            Log.d("BinderPool", "---queryBinderService--$binderCode--")
            when (binderCode) {
                1 -> return bookManager.asBinder()
                2 -> return studentManager.asBinder()
            }
            return null
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        val info = intent!!.getStringExtra("extra")
        Log.d("BinderPool", "info=$info")
        return binderPool.asBinder()
    }
}