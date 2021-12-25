package BoothMultiplier

import chisel3._
import chisel3.util._
import chisel3.stage._

object BoothGen extends App {
    val c = new ChiselStage
    println(c.emitVerilog(new BoothMultiplier, Array("--target-dir", "build/")))
}