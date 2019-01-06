package com.inaccurate

import monix.eval.Task
import monix.reactive._


// Needed below
import scala.concurrent.duration._

object Play extends App {
  import monix.execution.Scheduler.Implicits.global
  val task = Task { 1 + 1 }


  // Nothing happens here, as observable is lazily
  // evaluated only when the subscription happens!
  val tick = {
    Observable
      .interval(1.second)
      .filter(a => a % 2 == 0)
      .map(_ * 2)
      .flatMap(x => Observable.fromIterable(Seq(x,x)))
      .take(5)
      .dump("Out")
  }
  val cancelable = tick.subscribe()
  Thread.sleep(10000)
}
