package br.ufc.lps.gui.tree.testeVisualizacao;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import prefuse.visual.VisualItem;

public class SlotIcon
{
  private ScalableDimension2D dimension = new ScalableDimension2D();
  private Rectangle2D.Double positionRectangle = null;
  
  public SlotIcon(double width, double height)
  {
    this.dimension.setSize(width, height);
  }
  
  public ScalableDimension2D getDimension()
  {
    return this.dimension;
  }
  
  public Dimension2D getDimension(double size)
  {
    return this.dimension.getScaled(size);
  }
  
  public void draw(Graphics2D g2d, VisualItem item)//, Feature feature)
  {
    g2d.setColor(new Color(item.getStrokeColor()));
    g2d.drawRect((int)this.positionRectangle.getX(), (int)this.positionRectangle.getY(), (int)this.positionRectangle.getWidth(), (int)this.positionRectangle.getHeight());
  }
  
  public Rectangle2D.Double getPositionRectangle()
  {
    return this.positionRectangle;
  }
  
  public void setPositionRectangle(Rectangle2D.Double positionRectangle)
  {
    this.positionRectangle = positionRectangle;
  }
}
