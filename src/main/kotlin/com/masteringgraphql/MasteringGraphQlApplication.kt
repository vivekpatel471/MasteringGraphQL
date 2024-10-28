package com.masteringgraphql

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MasteringGraphQlApplication

fun main(args : Array<String>) {
    System.out.println("Hello World!")
    runApplication<MasteringGraphQlApplication>(*args)
}

