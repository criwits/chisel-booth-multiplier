package BoothMultiplier

import chisel3._
import chisel3.util._

/** 32 bit * 32 bit -> 64 bit Reference:
  * https://foxsen.github.io/archbase/%E8%BF%90%E7%AE%97%E5%99%A8%E8%AE%BE%E8%AE%A1.html#%E5%AE%9A%E7%82%B9%E8%A1%A5%E7%A0%81%E4%B9%98%E6%B3%95%E5%99%A8
  */

class BoothMultiplier extends Module {
  val io = IO(new ArithBundle)

  val multiplicandReg = RegInit(0.U(64.W))
  val multiplierReg = RegInit(0.U(33.W)) // One more bit
  val resultReg = RegInit(0.U(64.W))

  val shiftCounter = RegInit(0.U(8.W)) // Shift counter
  val busy = (multiplierReg =/= 0.U(33.W) && shiftCounter < 16.U(8.W))

  when(io.in.start && ~busy) {
    resultReg := 0.U(64.W)
    shiftCounter := 0.U(8.W)
    multiplicandReg := io.in.num_1.asTypeOf(SInt(64.W)).asUInt // Signed extend to 64 bit
    multiplierReg := Cat(io.in.num_2.asUInt, 0.U(1.W)) // Add one more 0 bit right next to it
  }.otherwise {
    when(busy) {
      resultReg := resultReg + MuxLookup(multiplierReg(2, 0), 0.U(64.W), Array(
          "b000".U -> 0.U(64.W),
          "b001".U -> multiplicandReg,
          "b010".U -> multiplicandReg,
          "b011".U -> (multiplicandReg << 1.U),
          "b100".U -> (-(multiplicandReg << 1.U)),
          "b101".U -> (-multiplicandReg),
          "b110".U -> (-multiplicandReg),
          "b111".U -> 0.U(64.W)
      ))
      multiplicandReg := multiplicandReg << 2.U
      multiplierReg := multiplierReg >> 2.U
      shiftCounter := shiftCounter + 1.U(8.W)
    }.otherwise {
      resultReg := resultReg
      multiplicandReg := multiplicandReg
      multiplierReg := multiplierReg
      shiftCounter := shiftCounter
    }
  }

  io.out.result := resultReg.asSInt
  io.out.busy := busy
}
