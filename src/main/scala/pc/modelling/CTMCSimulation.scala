package pc.modelling

import java.util.Random
import pc.utils.Stochastics

object CTMCSimulation:

  case class Event[A](time: Double, state: A)
  type Trace[A] = LazyList[Event[A]]

  export CTMC.*

  extension [S](self: CTMC[S])
    def newSimulationTrace(s0: S, rnd: Random): Trace[S] =
      LazyList.iterate(Event(0.0, s0)) { case Event(t, s) =>
        if self.transitions(s).isEmpty
        then
          Event(t, s)
        else
          val choices = self.transitions(s) map (t => (t.rate, t.state))
          //are the states reachable from the state in which the system is, so if I'm in SEEND the choices are:
          // choices= HashSet((100000.0,FAIL), (100000.0,SEND), (200000.0,DONE))
          val next = Stochastics.cumulative(choices.toList)
          val sumR = next.last._1
          val choice = Stochastics.draw(next)(using rnd)
          Event(t + Math.log(1 / rnd.nextDouble()) / sumR, choice)
      }
