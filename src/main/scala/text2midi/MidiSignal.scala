package text2midi

import ddf.minim.ugens.Frequency

import scala.concurrent.duration._

/**
 * @author Kevin Kyyro <kkyyro@linkedin.com>
 */
sealed trait MidiSignal

object MidiNote {
  type Note = Int

  def of(noteValue: Int, velocity: Float = 1.0f, duration: FiniteDuration = 250.millis) =
    MidiNote(noteValue, velocity, duration)

  implicit class IntToNote(noteValue: Int) {
    def toNote(velocity: Float = 1.0f, duration: FiniteDuration = 250.millis) =
      MidiNote(noteValue, velocity, duration)
  }

  implicit class NoteToMidiValue(note: String) {
    def toNote(velocity: Float = 1.0f, duration: FiniteDuration = 250.millis) = MidiNote(value, velocity, duration)
    def ^(octaves: Int) = (octaves * 12 + value).toNote()
    def value = noteToMidiValue(note)
  }

  implicit def noteToMidiValue(note: String) = note.toLowerCase match {
    case "c" | "b#" => 0
    case "c#" => 1
    case "d" => 2
    case "d#" => 3
    case "e" => 4
    case "f" | "e#" => 5
    case "f#" => 6
    case "g" => 7
    case "g#" => 8
    case "a" => 9
    case "a#" => 10
    case "b" => 11
  }
}

case class MidiNote(note: MidiNote.Note, velocity: Float, duration: FiniteDuration) extends MidiSignal {
  import MidiNote._

  def frequency = Frequency.ofMidiNote(note)
  def transpose(halfSteps: Note) = copy(note = Math.min(note + halfSteps, 127))
  def octaveUp = transpose(12)
  def octaveDown = transpose(-12)

  def octave = note / 12
  def relativeDegree = note % 12
}

case class MidiNotes(notes: Seq[MidiNote]) extends MidiSignal


