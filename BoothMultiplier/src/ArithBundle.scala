package BoothMultiplier

import chisel3._
import chisel3.util._

class ArithBundle extends Bundle {
  val in = Input(new ArithBundle_in)
  val out = Output(new ArithBundle_out)
}

// Input Bundle
class ArithBundle_in extends Bundle {
  val start = Bool() // Start signal for 1 cycle
  val num_1 = SInt(32.W) // Operation number 1
  val num_2 = SInt(32.W) // Operation number 2
}

// Output Bundle
class ArithBundle_out extends Bundle {
  val busy = Bool() // Busy status signal
  val result = SInt(64.W) // Result
}
