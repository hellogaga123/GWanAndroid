package com.gaga.gwanandroid

import org.koin.core.module.Module

/**
 * @Author Gaga
 * @Date 2020/8/17 11:01
 * @Description
 */
typealias ModuleDeclaration = Module.() -> Unit

//fun main(args: Array<String>) {
//    val post=object:Function0<Unit> {
//        override fun invoke() {
//            return print("word")
//        }
//    }
//
//}
//
//inline fun hello(noinline postAction: () -> Unit): () -> Unit {
//    print("hello")
//    return postAction()
//}

