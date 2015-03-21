package text2midi.minim

import ddf.minim.ugens.{Oscil, Waves}
import processing.core.PApplet
import text2midi.SetupAware

/**
 * @author Kevin Kyyro <kkyyro@linkedin.com>
 */
trait OscillatorComponent {
  def oscillator: Oscil
}

trait SingleOscillatorComponent extends OscillatorComponent with SetupAware {
  this: OutputComponent
    with PApplet =>

  override val oscillator = new Oscil(440, 0.0f, Waves.SINE)

  afterSetup {
    oscillator patch out
  }
}