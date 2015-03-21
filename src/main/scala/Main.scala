import ddf.minim.ugens.{Oscil, Waves}
import processing.core._
import text2midi._
import text2midi.minim._
import math._
import scala.util.Random
import ddf.minim._

object Main extends PApplet {

  def main(args: Array[String]) = {
    val app = new Main
//      with DefaultMinimComponent
      with LineOutComponent
      with SingleOscillatorComponent
      with DefaultPlayerComponent
      with MonophonicMidifierComponent
      with ScaleComponent
    {
      val scale = Scales.Minor
    }

    val frame = new javax.swing.JFrame("Test")
    frame.getContentPane add app

    app.init
    frame.setBounds(0, 0, Window.Width, Window.Height)
    frame.setVisible(true)
  }
}

object Window {
  val Height = 720
  val Width = 1280
}

abstract class Main extends PApplet
  with SetupAware
  with DrawAware
  with MinimComponent
{
  this: PlayerComponent 
    with OscillatorComponent =>

  var angle = 0
  var lines = Array.empty[String]
  var currentLine = 0
  var minim: Minim = _

  override def applicationSetup() = {
    size(Window.Width, Window.Height)
    background(102)
    smooth()
    noStroke()
    fill(0, 102)
    frameRate(10)

    minim = new Minim(this)

    lines = loadStrings("don-quixote.txt") filter (_.nonEmpty)

    println(s"Loaded ${lines.size} lines of text from don-quixote.txt")
  }

  override def mainDraw() = {
    angle += 10
    val value = cos(toRadians(angle)) * 6.0
    for (a <- 0 to 360 by 75) {
      val xoff = cos(toRadians(a)) * value
      val yoff = sin(toRadians(a)) * value
      fill(Random.nextInt(255))

      ellipse(
        (mouseX + xoff).toFloat,
        (mouseY + yoff).toFloat,
        value.toFloat,
        value.toFloat)
    }
    fill(255)
    ellipse(mouseX, mouseY, 2, 2)
  }

  override def keyPressed() = {
    key match {
      case '1' => oscillator.setWaveform( Waves.SINE );
      case '2' => oscillator.setWaveform( Waves.TRIANGLE );
      case '3' => oscillator.setWaveform( Waves.SAW );
      case '4' => oscillator.setWaveform( Waves.SQUARE );
      case '5' => oscillator.setWaveform( Waves.QUARTERPULSE );
      case _ =>
        val line = lines(currentLine)
        println(s"Playing line: $line")
        player play line
        currentLine = (currentLine + 1) % lines.size
    }
  }
}