package pc.rl

import org.scalatest.FlatSpec
import org.scalatest.Matchers._
import TwoWaysMDP._
import TwoWaysMDP.State
import TwoWaysMDP.Action._

class QFunctionSpec extends FlatSpec{

  "QF" should "properly get" in {
    assertResult( 1.0)( qfTW()(State(0),left))
    assertResult( 0.0)( qfTW()(State(20),left))
  }

  "QF" should "properly update" in {
    assertResult( 100)( qfTW().update(State(0),left,100)(State(0),left))
    assertResult( 0.0)( qfTW().update(State(10),left,100)(State(10),left))
  }
}

class MDPSpec extends FlatSpec{

  "MDP" should "properly take" in {
    assertResult((-0.2,State(5)))( mdpTW().take(State(4),right))
    assertResult((10.0,State(-5)))( mdpTW().take(State(-4),left))
  }
}

class RLSpec extends FlatSpec{

  val rl = rlTW()

  "QRL" should "properly updated QF" in {
    var qf = qfTW()
    qf = rl.updateQ(rl.initial,qf)._2
    assertResult(0.85)(qf(State(0),left))
  }

  "QRL" should "properly run an episode" in {
    var qf = qfTW()
    qf = rl.runEpisodes(100,qf)
    assertResult(0.0)(qf(State(10),left))
    assertResult(10.0)(qf(State(-4),left))
    assert( (-5 to 10).forall(i => qf.detTake(State(i))==left))
  }

  "QRL" should "properly execute a run after learning" in {
    var qf = qfTW()
    qf = rl.runEpisodes(100,qf)
    assertResult( List((left,State(-1)), (left,State(-2)), (left,State(-3)), (left,State(-4)), (left,State(-5))) )(rl.run(qf).toList)
  }
}
