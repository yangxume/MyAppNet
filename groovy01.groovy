/**
 * 1 Groovy 变量定义
 * 2 Groovy 方法定义
 * 3 Groovy 可以省略的代码
 *
 */


/**
 * 1 Groovy中变量定义
 *
 *  a.用def关键字来定义变量
 *  b.可以不指定变量的类型
 *  c.默认访问修饰符是public
 *
 */
def a = 1
def int b = 19
def c = "hello world!"

println a
println b
println c

/**
 * 2. Groovy 中方法定义
 *
 * a 方法使用返回类型或def关键字定义
 * b 方法可以接收任意数量的参数
 * c 这些参数可以不申明类型
 * d 如果不提供可见性修饰符，则该方法为public
 * e 如果指定了方法返回类型，可以不需要def关键字来定义方法
 * f 如果不使用return ，方法的返回值为最后一行代码的执行结果
 */
def log(){
    println "haha"
}

Integer add(Integer a , Integer b){
    return a+b
}

Integer minus(a,b){
    return a-b
}

Integer sum(x,y){
    x+y
}

println log()
println add(3,5)
println minus(4,1)
println sum(12,23)

/***
 * 3 Groovy中有很多省略的地方：
 *
 * 语句后面的分号可以省略。
 * 方法的括号可以省略，比如注释1和注释3处。
 * 参数类型可以省略，比如注释2处。
 * return可以省略掉，比如注释4处。
 */
