package com.example.firstprogram.threedpool

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ThreadPoolTest {

}

/**
 * 当线程池中线程的数目大于5时，便将任务放入任务缓存队列里面，当任务缓存队列满了之后，
 * 便创建新的线程。如果上面程序中，将for循环中改成执行20个任务，就会抛出任务拒绝异常了
 * corePoolSize：线程池的核心大小，也可以理解为最小的线程池大小。
maximumPoolSize：最大线程池大小。
keepAliveTime：空余线程存活时间，指的是超过corePoolSize的空余线程达到多长时间才进行销毁。
unit：销毁时间单位。
workQueue：存储等待执行线程的工作队列。
threadFactory：创建线程的工厂，一般用默认即可。
handler：拒绝策略，当工作队列、线程池全已满时如何拒绝新任务，默认抛出异常。

 *
 * 　workQueue的类型为BlockingQueue<Runnable>，通常可以取下面三种类型：任务缓存队列及排队策略

　　1）ArrayBlockingQueue：基于数组的先进先出队列，此队列创建时必须指定大小；

　　2）LinkedBlockingQueue：基于链表的先进先出队列，如果创建时没有指定此队列大小，则默认为Integer.MAX_VALUE；

　　3）synchronousQueue：这个队列比较特殊，它不会保存提交的任务，而是将直接新建一个线程来执行新来的任务。

线程池的任务缓存队列已满并且线程池中的线程数目达到maximumPoolSize，如果还有任务到来就会采取任务拒绝策略，通常有以下四种策略：
ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务

ThreadPoolExecutor提供了两个方法，用于线程池的关闭，分别是shutdown()和shutdownNow()，其中：
shutdown()：不会立即终止线程池，而是要等所有任务缓存队列中的任务都执行完后才终止，但再也不会接受新的任务
shutdownNow()：立即终止线程池，并尝试打断正在执行的任务，并且清空任务缓存队列，返回尚未执行的任务

线程池任务执行流程：

当线程池小于corePoolSize时，新提交任务将创建一个新线程执行任务，即使此时线程池中存在空闲线程。
当线程池达到corePoolSize时，新提交任务将被放入workQueue中，等待线程池中任务调度执行
当workQueue已满，且maximumPoolSize>corePoolSize时，新提交任务会创建新线程执行任务
当提交任务数超过maximumPoolSize时，新提交任务由RejectedExecutionHandler处理
当线程池中超过corePoolSize线程，空闲时间达到keepAliveTime时，关闭空闲线程
当设置allowCoreThreadTimeOut(true)时，线程池中corePoolSize线程空闲时间达到keepAliveTime也将关闭

一般如果线程池任务队列采用LinkedBlockingQueue队列的话，那么不会拒绝任何任务（因为队列大小没有限制），这种情况下，ThreadPoolExecutor最多仅会按照最小线程数来创建线程，也就是说线程池大小被忽略了。如果线程池任务队列采用

ArrayBlockingQueue队列的话，那么ThreadPoolExecutor将会采取一个非常负责的算法，比如假定线程池的最小线程数为4，最大为8所用的ArrayBlockingQueue最大为10。随着任务到达并被放到队列中，线程池中最多运行4个线程（即最小线程数）。

即使队列完全填满，也就是说有10个处于等待状态的任务，ThreadPoolExecutor也只会利用4个线程。如果队列已满，而又有新任务进来，此时才会启动一个新线程，这里不会因为队列已满而拒接该任务，相反会启动一个新线程。新线程会运行队列中的

第一个任务，为新来的任务腾出空间。这个算法背后的理念是：该池大部分时间仅使用核心线程（4个），即使有适量的任务在队列中等待运行。这时线程池就可以用作节流阀。如果挤压的请求变得非常多，这时该池就会尝试运行更多的线程来清理；

Java通过Executors提供四种线程池，分别为：

1、newSingleThreadExecutor

创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
底层：FinalizableDelegatedExecutorService包装的ThreadPoolExecutor实例，corePoolSize为1；maximumPoolSize为1；keepAliveTime为0L；unit为：TimeUnit.MILLISECONDS；workQueue为：new LinkedBlockingQueue<Runnable>() 无解阻塞队列
通俗：创建只有一个线程的线程池，且线程的存活时间是无限的；当该线程正繁忙时，对于新任务会进入阻塞队列中(无界的阻塞队列)
适用：一个任务一个任务执行的场景

2、newFixedThreadPool

创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
底层：返回ThreadPoolExecutor实例，接收参数为所设定线程数量nThread，corePoolSize为nThread，maximumPoolSize为nThread；keepAliveTime为0L(不限时)；unit为：TimeUnit.MILLISECONDS；WorkQueue为：new LinkedBlockingQueue<Runnable>() 无界阻塞队列
通俗：创建可容纳固定数量线程的池子，每隔线程的存活时间是无限的，当池子满了就不在添加线程了；如果池中的所有线程均在繁忙状态，对于新任务会进入阻塞队列中(无界的阻塞队列)
适用：执行长期的任务，性能好很多

3、newScheduledThreadPool

创建一个可定期或者延时执行任务的定长线程池，支持定时及周期性任务执行。
底层：创建ScheduledThreadPoolExecutor实例，corePoolSize为传递来的参数，maximumPoolSize为Integer.MAX_VALUE；keepAliveTime为0；unit为：TimeUnit.NANOSECONDS；workQueue为：new DelayedWorkQueue() 一个按超时时间升序排序的队列
通俗：创建一个固定大小的线程池，线程池内线程存活时间无限制，线程池可以支持定时及周期性任务执行，如果所有线程均处于繁忙状态，对于新任务会进入DelayedWorkQueue队列中，这是一种按照超时时间排序的队列结构
适用：周期性执行任务的场景

4、newCachedThreadPoo
ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
底层：返回ThreadPoolExecutor实例，corePoolSize为0；maximumPoolSize为Integer.MAX_VALUE；keepAliveTime为60L；unit为TimeUnit.SECONDS；workQueue为SynchronousQueue(同步队列)
通俗：当有新任务到来，则插入到SynchronousQueue中，由于SynchronousQueue是同步队列，因此会在池中寻找可用线程来执行，若有可以线程则执行，若没有可用线程则创建一个线程来执行该任务；若池中线程空闲时间超过指定大小，则该线程会被销毁。
适用：执行很多短期异步的小程序或者负载较轻的服务器
 */
object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        val executor = ThreadPoolExecutor(
            5, 10, 200, TimeUnit.MILLISECONDS,
            ArrayBlockingQueue<Runnable>(5)
        )
        for (i in 0..14) {
            val myTask = MyTask(i)
            executor.execute(myTask)
            System.out.println(
                "线程池中线程数目：" + executor.getPoolSize().toString() + "，队列中等待执行的任务数目：" +
                        executor.getQueue().size
                            .toString() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount()
            )
        }
        executor.shutdown()
    }
}


internal class MyTask(private val taskNum: Int) : Runnable {
    override fun run() {
        println("正在执行task $taskNum")
        try {
            Thread.sleep(4000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        println("task " + taskNum + "执行完毕")
    }
}
