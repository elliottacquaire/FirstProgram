package com.example.firstprogram.annotation

import android.util.Log

class AnimationTest {


    /**
     * sombody() 被 @MyAnnotation(value={"girl","boy"}) 所标注，
     * @MyAnnotation(value={"girl","boy"}), 意味着MyAnnotation的value值是{"girl","boy"}
     *
     * @Retention用来约束注解的生命周期，分别有三个值，源码级别（source），类文件级别（class）或者运行时级别（runtime），其含有如下：

    SOURCE：注解将被编译器丢弃（该类型的注解信息只会保留在源码里，源码经过编译后，注解信息会被丢弃，不会保留在编译好的class文件里）

    CLASS：注解在class文件中可用，但会被VM丢弃（该类型的注解信息会保留在源码里和class文件里，在执行的时候，不会加载到虚拟机中），请注意，当注解未定义Retention值时，默认值是CLASS，如Java内置注解，@Override、@Deprecated、@SuppressWarnning等

    RUNTIME：注解信息将在运行期(JVM)也保留，因此可以通过反射机制读取注解的信息（源码、class文件和执行的时候都有注解的信息），如SpringMvc中的@Controller、@Autowired、@RequestMapping等。



    /**一个人喜欢玩游戏，他喜欢玩英雄联盟，绝地求生，极品飞车，尘埃4等，则我们需要定义一个人的注解，他属性代表喜欢玩游戏集合，一个游戏注解，游戏属性代表游戏名称*/
    /**玩家注解*/
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface People {
    Game[] value() ;
    }
    /**游戏注解*/
    @Repeatable(People.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Game {
    String value() default "";
    }
    /**玩游戏类*/
    @Game(value = "LOL")
    @Game(value = "PUBG")
    @Game(value = "NFS")
    @Game(value = "Dirt4")
    public class PlayGame {
    }


     */
    @MyAnnotation(value = ["girl", "boy"])
    fun somebody(name: String, age: Int) {
        Log.i("aaa","somebody-----$age--")
    }

    @MyAnnotation1(value = ["girls","boys"],getAge = 20)
    fun testAge(age: Int) {
        Log.i("aaa","testAge-----$age--")
    }
}