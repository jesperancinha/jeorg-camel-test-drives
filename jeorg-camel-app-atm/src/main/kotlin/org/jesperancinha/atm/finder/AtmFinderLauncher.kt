package org.jesperancinha.atm.finder

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class AtmFinderLauncher

fun main(args: Array<String>) {
    SpringApplication.run(AtmFinderLauncher::class.java, *args)
}