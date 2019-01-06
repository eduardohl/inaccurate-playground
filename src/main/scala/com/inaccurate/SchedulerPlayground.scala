package com.inaccurate

import java.util.Date
import java.util.concurrent.Executors

import monix.execution.Scheduler

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.language.postfixOps

object SchedulerPlayground extends App {

  val execService = Executors.newFixedThreadPool(4)
  val execContext = ExecutionContext.fromExecutor(execService)
  val scheduler = Scheduler(execContext)

  scheduler.execute(() => { // This method return type is Unit and it can't be cancelled!
    println(s"${new Date()} Will just execute this task asynchronously just once.")
  })

  // This method returns a cancelable that can interrupt the execution!
  val cancelable1 = scheduler.scheduleOnce(3 seconds) {
    println(s"${new Date()} Executing async task after a 3s delay.")
  }

  val cancelable2 = scheduler.scheduleWithFixedDelay(3 seconds, 5 seconds) {
    println(s"${new Date()} Executing async task after a 3s delay, executing it again 5 seconds after each execution.")
  }

  val cancelable3 = scheduler.scheduleAtFixedRate(3 seconds, 5 seconds) {
    println(s"${new Date()} Executing async task after a 3s delay, executing it again every 5 seconds.")
  }
}
