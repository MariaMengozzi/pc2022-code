package pc.examples

export pc.modelling.PetriNet
import pc.utils.MSet

object ReadersAndWriters:
  enum Place:
    case P1, P2, P3, P4, P5, P6, P7

  export Place.*
  export pc.modelling.PetriNet.*
  export pc.modelling.SystemAnalysis.*
  export pc.utils.MSet

  // DSL-like specification of a Petri Net
  def pnRW = PetriNet[Place](
    MSet(P1) ~~> MSet(P2),
    MSet(P2) ~~> MSet(P3),
    MSet(P2) ~~> MSet(P4),
    MSet(P4, P5) ~~> MSet(P7),
    MSet(P7) ~~> MSet(P1, P5),
    MSet(P3, P5) ~~> MSet(P5, P6),
    MSet(P6) ~~> MSet(P1)
  ).toSystem


@main def mainReadersAndWriters =
  import ReadersAndWriters.*
  // example usage
  println(pnRW.paths(MSet(P1, P5), 7).toList.mkString("\n"))

