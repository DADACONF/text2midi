package text2midi.minim

import ddf.minim.Minim
import processing.core.PApplet
import text2midi.SetupAware

/**
 *
 * @author Kevin Kyyro <kkyyro@linkedin.com>
 */
trait MinimComponent {
  def minim: Minim
}

trait DefaultMinimComponent extends MinimComponent with SetupAware { self: PApplet =>
  var minim: Minim = _

  beforeSetup {
    minim = new Minim(this)
  }
}
