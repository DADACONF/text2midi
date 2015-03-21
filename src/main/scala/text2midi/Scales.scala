package text2midi

/**
 *
 * @author Kevin Kyyro <kkyyro@linkedin.com>
 */
object Scales {
  import MidiNote._

  /**
   * @param degrees 0-indexed notes of the scale, relative to the C
   */
  sealed class Scale(val degrees: Int*) {
    val notes = degrees map { noteValue => noteValue.toNote() }

    val doubled = repeat(degrees.max / 12)

    def repeat(octaves: Int) = {
      val all = for {
        octave <- 0 until octaves
        note <- degrees
      } yield note + octave * 12

      all.toSeq.sorted.distinct
    }

    /** @return closest note in scale to the given midi note */
    def closest(note: MidiNote): MidiNote = {
      val relative = doubled minBy { scaleNote => Math.abs(scaleNote - note.relativeDegree) }
      val interval = relative - note.relativeDegree

      note transpose interval
    }

    /** @return closest note in scale above or equal to given midi note */
    def above(note: MidiNote): MidiNote = {
      val relative = doubled filter (_ >= note.relativeDegree) minBy { _ - note.relativeDegree }
      val interval = relative - note.relativeDegree

      note transpose interval
    }

    /** @return closest note in scale below or equal to given midi note */
    def below(note: MidiNote): MidiNote = {
      val relative = doubled filter (_ <= note.relativeDegree) minBy { _ - note.relativeDegree }
      val interval = relative - note.relativeDegree

      note transpose interval
    }

    /** @return for note n, the nth note in this scale */
    def spread(note: MidiNote): MidiNote = {
      val octavesCovered = degrees.max / 12
      val index = degrees.size % note.note
      val wraps = (note.note - 1) / degrees.size

      note.copy(note = degrees(index) + (octavesCovered * wraps))
    }
  }

  val Chromatic = new Scale(1 to 12: _*)

  /* C, D, E, F, G, A, B, C */
  val Major = new Scale(0, 2, 4, 5, 7, 9, 11, 12)

  /* C, D, D#, F, G, A, A#, C */
  val Minor = new Scale(0, 2, 3, 5, 7, 9, 10, 12)
}

trait ScaleComponent {
  def scale: Scales.Scale
}