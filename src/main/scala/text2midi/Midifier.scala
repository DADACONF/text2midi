package text2midi

import scala.concurrent.duration._

/**
 *
 * @author Kevin Kyyro <kkyyro@linkedin.com>
 */
trait Midifier {
  def midify(text: String): Seq[MidiSignal]
}

trait MidifierComponent {
  def midifier: Midifier
}

trait MonophonicMidifierComponent extends MidifierComponent { this: ScaleComponent =>

  val midifier = new Midifier {
    val length = 250.millis
    val rest = MidiNote(0, 0.0f, length)
    val fullStop = MidiNote(0, 0.0f, length * 2)

    override def midify(text: String): Seq[MidiSignal] = text split "\\s" flatMap midifyWord

    def midifyWord(word: String): Seq[MidiSignal] = word map {
      case ' ' | '\n' | '\t' => rest
      case ',' | ';' | '.' => fullStop
      case c => scale closest MidiNote(c, velocity(c, word), noteLength(c, word))
    }

    def noteLength(c: Char, word: String) = if (word.length > 1) 1.second / word.length else 250.millis

    def velocity(c: Char, word: String) = ((c.toInt % 13).toFloat / 23) + 0.25f
  }
}
