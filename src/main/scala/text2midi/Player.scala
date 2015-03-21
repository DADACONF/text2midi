package text2midi

import processing.core.PApplet
import text2midi.minim.OscillatorComponent

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

/**
 * @author Kevin Kyyro <kkyyro@linkedin.com>
 */
trait Player {
  def play(text: String): Unit
  def stop: Unit
}

trait PlayerComponent {
  def player: Player
}

trait DefaultPlayerComponent extends PlayerComponent with SetupAware with DrawAware {
  this: PApplet
    with MidifierComponent
    with OscillatorComponent =>

  afterSetup {
    println("Starting player loop")
    player.loop // kick off the loop
  }

  val player = new Player {
    var midi: Seq[MidiSignal] = Nil
    val transpose = -12

    lazy val loop = Future {
      def playNote(index: Int): Unit = Try {
        val signal: Option[MidiSignal] = midi.lift(index)
        println(s"Attempting to play $signal")

        signal foreach {
          case notes: MidiNotes => ???

          case note: MidiNote =>
            println(s"Setting note to: $note")

            // set osc
            oscillator.setFrequency(note.transpose(transpose).frequency.asHz())
            oscillator.setAmplitude(note.velocity)

            // play for its duration
            Thread.sleep(note.duration.toMillis)
        }
      } recover {
        case t: Throwable => println(s"uh oh! ${t.getMessage}")
      }

      var i = 0
      while (true) {
        if (playing) {
          playNote(i)

          // update state
          i = (i + 1) % midi.length

        } else {
          println("no longer playing")
          i = 0
          Thread.sleep(250)
        }
      }
    }

    def play(text: String) = {
      midi = midifier midify text

      println(s"Current midi for $text:\n$midi")
    }

    def stop = {
      midi = Nil
    }

    def playing = midi.nonEmpty

  }
}
