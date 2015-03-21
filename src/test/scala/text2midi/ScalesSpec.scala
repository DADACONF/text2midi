package text2midi

import org.scalatest._
import text2midi.MidiNote
import text2midi.Scales._

/**
 * @author Kevin Kyyro <kkyyro@linkedin.com>
 */
class ScalesSpec extends FlatSpec with Matchers {
  import MidiNote._
  import Scales._

  "Scale.repeat(n)" should "be empty for n=0" in {
    Major repeat 0 should be (empty)
  }

  it should "be the scale for n=1" in {
    Major repeat 1 should be (Scales.Major.degrees)
  }

  it should "repeat twice for n=2" in {
    Major repeat 2 should contain.inOrderOnly(0, 2, 4, 5, 7, 9, 11, 12, 14, 16, 17, 19, 21, 23, 24)
  }

  "Scale.closest(n)" should "be n if n is in the scale" in  {
    (Major closest Major.notes(2)) should be (Major.notes(2))
  }

  it should "be n if n is in the scale and octaved" in {
    Major closest Major.notes(2).octaveUp should be (Major.notes(2).octaveUp)
  }

  it should "be C or D for C# in Major" in {
    Seq("C".toNote(), "D".toNote()) should contain (Major closest "C#".toNote())
  }
}
