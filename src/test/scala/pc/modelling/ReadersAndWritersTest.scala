package pc.modelling

import org.scalatest.funsuite.AnyFunSuite
import pc.examples.ReadersAndWriters.*
import pc.utils.MSet
import org.scalatest.matchers.should.Matchers.*

class ReadersAndWritersTest extends AnyFunSuite:

  test(" in no path long at most 100 states mutual exclusion fails (no readers and writers together)"){
    val expected = MSet(P6, P7)
    pnRW.paths(MSet(P1,P1,P5),20).toSet should not contain expected
  }

  test(" in no path long at most 100 states mutual exclusion fails (no more than 1 writer)"){
    val expected = MSet(P7, P7)
    pnRW.paths(MSet(P1,P1,P5),20).toSet should not contain expected
  }

