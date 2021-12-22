package BoothMultiplier

import chisel3._
import chisel3.util._
import chiseltest._
import chisel3.stage.ChiselStage
import org.scalatest.{Matchers, FlatSpec}
import chiseltest.experimental.TestOptionBuilder._
import chiseltest.internal.WriteVcdAnnotation

import BoothMultiplier._

class BoothTest extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior of ""
  it should "just work" in {
    test(new BoothMultiplier).withAnnotations(Seq(WriteVcdAnnotation)) { c =>
        // Initialize
        c.reset.poke(true.B)
        c.clock.step()
        c.reset.poke(false.B)
        c.clock.step()

        // 18 * 12 = 216
        // This test passed
        c.io.in.start.poke(true.B)
        c.io.in.num_1.poke(18.S)
        c.io.in.num_2.poke(12.S)
        c.clock.step()
        c.io.in.start.poke(false.B)

        while (c.io.out.busy.peek().litValue != 0) {
          c.clock.step()
        }

        c.io.out.result.expect(216.S)
        c.clock.step()

        // -6 * -7 = -42
        // This test failed.
        c.io.in.start.poke(true.B)
        c.io.in.num_1.poke((-6).S)
        c.io.in.num_2.poke((-7).S)
        c.clock.step()
        c.io.in.start.poke(false.B)

        while (c.io.out.busy.peek().litValue != 0) {
          c.clock.step()
        }

        c.io.out.result.expect((-42).S)
        c.clock.step()

        // // Random generator
        // val r = scala.util.Random
        // // Test for 1000 times
        // for (i <- 1 until 1000) {
        //   val num_1 = r.nextInt()
        //   val num_2 = r.nextInt()
        //   var answer: Long = (num_1.toLong) * (num_2.toLong)

        //   print(s"Test round $i: $num_1 * $num_2 \n")
        //   c.io.in.start.poke(true.B)
        //   c.io.in.num_1.poke(num_1.S)
        //   c.io.in.num_2.poke(num_2.S)
        //   c.clock.step()
        //   c.io.in.start.poke(false.B)

        //   while (c.io.out.busy.peek().litValue != 0) {
        //     c.clock.step()
        //   }

        //   c.io.out.result.expect(answer.S)
        //   c.clock.step()

        // }



    }
  }
}
