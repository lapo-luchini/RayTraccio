/**
 * Materiale sperimentale che macchietta un materiale dato (è brutto!).
 */
class TextureSpotted extends Texture {
	/** Colore da macchiettare */
  private Texture c;
	/** Prbabilità di macchiettamento */
  private double z;
  TextureSpotted(Texture a, double b) {
	c=a;
	z=b;
  }  
  public Color color(Vector p) {
	if(p.mod()-Math.floor(p.mod())<z)
	  return(Color.BLACK);
	else
	  return(c.color(p));
  }  
  public double reflect(Vector p) {
	return(c.reflect(p));
  }  
  public String toString() {
	return("TextureSpotted["+c+","+z+"]");
  }  
}
