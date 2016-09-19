package br.ufc.lps.gui.tree.testeVisualizacao;

import java.awt.geom.Dimension2D;

public class ScalableDimension2D
  extends Dimension2D
{
  private double width;
  private double height;
  
  public ScalableDimension2D() {}
  
  public ScalableDimension2D(double width, double height)
  {
    this.width = width;
    this.height = height;
  }
  
  public double getHeight()
  {
    return this.height;
  }
  
  public double getWidth()
  {
    return this.width;
  }
  
  public void setSize(double width, double height)
  {
    this.width = width;
    this.height = height;
  }
  
  public double getHeight(double size)
  {
    return this.height * size;
  }
  
  public double getWidth(double size)
  {
    return this.width * size;
  }
  
  public Dimension2D getScaled(double size)
  {
    return new ScalableDimension2D(this.width * size, this.height * size);
  }
  
  public void scale(double size)
  {
    this.width *= size;
    this.height *= size;
  }
}
