package com.example.firstprogram.generic;


import java.util.ArrayList;
import java.util.List;

/**
 * 泛型接口、泛型类和泛型方法
 *
 * 类型通配符上限通过形如Box<? extends Number>形式定义，相对应的，
 * 类型通配符下限为Box<? super Number>形式，其含义与类型通配符上限正好相反
 *
 *
 * 无论何时，如果你能做到，你就该尽量使用泛型方法。也就是说，如果使用泛型方法将整个类泛型化，
 * 那么就应该使用泛型方法。另外对于一个static的方法而已，无法访问泛型类型的参数。
 * 所以如果static方法要使用泛型能力，就必须使其成为泛型方法。
 *
 */
public class GenericTest {

    public static void main(String[] args) {
        Box<String> name = new Box<String>("corn");

        try {
            Object obj = genericMethod(Class.forName("com.test.test"));
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //不能创建一个确切的泛型类型的数组
//        List<String>[] ls = new ArrayList<String>[10];
        //使用通配符创建泛型数组是可以的
        List<?>[] ls1 = new ArrayList<?>[10];
        List<String>[] ls11 = new ArrayList[10];
    }

    /**
     * 泛型方法的基本介绍
     * @param tClass 传入的泛型实参
     * @return T 返回值为T类型
     * 说明：
     *     1）public 与 返回值中间<T>非常重要，可以理解为声明此方法为泛型方法。
     *     2）只有声明了<T>的方法才是泛型方法，泛型类中的使用了泛型的成员方法并不是泛型方法。
     *     3）<T>表明该方法将使用泛型类型T，此时才可以在方法中使用泛型类型T。
     *     4）与泛型类的定义一样，此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型。
     */
    public static <T> T genericMethod(Class<T> tClass)throws InstantiationException ,
            IllegalAccessException{
        T instance = tClass.newInstance();
        return instance;
    }

    //静态方法有一种情况需要注意一下，那就是在类中的静态方法使用泛型：
    // 静态方法无法访问类上定义的泛型；如果静态方法操作的引用数据类型不确定的时候，必须要将泛型定义在方法上。
    //即：如果静态方法要使用泛型的话，必须将静态方法也定义成泛型方法 。
    /**
     * 如果在类中定义使用泛型的静态方法，需要添加额外的泛型声明（将这个方法定义成泛型方法）
     * 即使静态方法要使用泛型类中已经声明过的泛型也不可以。
     * 如：public static void show(T t){..},此时编译器会提示错误信息：
     "StaticGenerator cannot be refrenced from static context"
     */
    public static <T> void show(T t){

    }

    //在泛型方法中添加上下边界限制的时候，必须在权限声明与返回值之间的<T>上添加上下边界，即在泛型声明的时候添加
    //public <T> T showKeyName(Generic<T extends Number> container)，编译器会报错："Unexpected bound"
    public <T extends Number> T showKeyName(Box<T> container){
        System.out.println("container key :" + container.getData());
        T test = container.getData();
        return test;
    }

}

//泛型类
class Box<T> {
    private T data;

    public Box() {
    }

    public Box(T data) {
        this.data = data;
    }

    //我想说的其实是这个，虽然在方法中使用了泛型，但是这并不是一个泛型方法。
    //这只是类中一个普通的成员方法，只不过他的返回值是在声明泛型类已经声明过的泛型。
    //所以在这个方法中才可以继续使用 T 这个泛型。
    public T getData() {
        return data;
    }
}