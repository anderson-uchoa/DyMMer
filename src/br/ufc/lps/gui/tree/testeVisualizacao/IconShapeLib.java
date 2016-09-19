package br.ufc.lps.gui.tree.testeVisualizacao;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

public class IconShapeLib
{
  public static Shape checkSymbol(Rectangle2D.Double targetRect, double scale)
  {
    double dimension = targetRect.width * scale;
    
    GeneralPath gp = new GeneralPath();
    gp.moveTo(targetRect.x + 0.25D * dimension, targetRect.y + 0.44999998807907104D * dimension);
    gp.quadTo(targetRect.x + 0.3499999940395355D * dimension, targetRect.y + 0.5199999809265137D * dimension, targetRect.x + 0.44999998807907104D * dimension, targetRect.y + 0.6499999761581421D * dimension);
    gp.quadTo(targetRect.x + 0.6499999761581421D * dimension, targetRect.y + 0.30000001192092896D * dimension, targetRect.x + 0.8500000238418579D * dimension, targetRect.y + 0.11999999731779099D * dimension);
    
    return gp;
  }
  
  public static Shape crossSymbol(Rectangle2D.Double targetRect, double scale)
  {
    double dimension = targetRect.width * scale;
    
    GeneralPath gp = new GeneralPath();
    gp.moveTo(targetRect.x + 0.25D * dimension, targetRect.y + 0.25D * dimension);
    gp.quadTo(targetRect.x + 0.699999988079071D * dimension, targetRect.y + 0.699999988079071D * dimension, targetRect.x + 0.75D * dimension, targetRect.y + 0.75D * dimension);
    
    gp.moveTo(targetRect.x + 0.75D * dimension, targetRect.y + 0.25D * dimension);
    gp.quadTo(targetRect.x + 0.25D * dimension, targetRect.y + 0.75D * dimension, targetRect.x + 0.25D * dimension, targetRect.y + 0.75D * dimension);
    
    return gp;
  }
  
  public static Shape plusSymbol(Rectangle2D.Double targetRect, double scale)
  {
    double dimension = targetRect.width * scale;
    
    GeneralPath gp = new GeneralPath();
    gp.moveTo(targetRect.x + 0.25D * dimension, targetRect.y + 0.5D * dimension);
    gp.lineTo(targetRect.x + 0.75D * dimension, targetRect.y + 0.5D * dimension);
    
    gp.moveTo(targetRect.x + 0.5D * dimension, targetRect.y + 0.25D * dimension);
    gp.lineTo(targetRect.x + 0.5D * dimension, targetRect.y + 0.75D * dimension);
    
    return gp;
  }
  
  public static Shape minusSymbol(Rectangle2D.Double targetRect, double scale)
  {
    double dimension = targetRect.width * scale;
    
    GeneralPath gp = new GeneralPath();
    gp.moveTo(targetRect.x + 0.25D * dimension, targetRect.y + 0.5D * dimension);
    gp.lineTo(targetRect.x + 0.75D * dimension, targetRect.y + 0.5D * dimension);
    
    return gp;
  }
  
  public static Shape andGroupSymbol(Rectangle2D.Double targetRect, double scale)
  {
    double dimension = targetRect.width * scale;
    
    GeneralPath gp = new GeneralPath();
    
    gp.moveTo(targetRect.x + 0.699999988079071D * dimension, targetRect.y + 0.10000000149011612D * dimension);
    gp.lineTo(targetRect.x + 0.20000000298023224D * dimension, targetRect.y + 0.5D * dimension);
    gp.lineTo(targetRect.x + 0.699999988079071D * dimension, targetRect.y + 0.8999999761581421D * dimension);
    
    gp.moveTo(targetRect.x + 0.20000000298023224D * dimension, targetRect.y + 0.5D * dimension);
    gp.lineTo(targetRect.x + 0.699999988079071D * dimension, targetRect.y + 0.5D * dimension);
    
    return gp;
  }
  
  public static Shape alternateGroupGroupSymbol(Rectangle2D.Double targetRect, double scale)
  {
    double dimension = targetRect.width * scale;
    
    GeneralPath gp = new GeneralPath();
    
    gp.moveTo(targetRect.x + 0.699999988079071D * dimension, targetRect.y + 0.10000000149011612D * dimension);
    gp.lineTo(targetRect.x + 0.20000000298023224D * dimension, targetRect.y + 0.5D * dimension);
    gp.lineTo(targetRect.x + 0.699999988079071D * dimension, targetRect.y + 0.8999999761581421D * dimension);
    
    gp.moveTo(targetRect.x + 0.20000000298023224D * dimension, targetRect.y + 0.5D * dimension);
    gp.lineTo(targetRect.x + 0.699999988079071D * dimension, targetRect.y + 0.5D * dimension);
    
    gp.moveTo(targetRect.x + 0.4000000059604645D * dimension, targetRect.y + 0.20000000298023224D * dimension);
    gp.curveTo(targetRect.x + 0.6499999761581421D * dimension, targetRect.y + 0.20000000298023224D * dimension, targetRect.x + 0.6499999761581421D * dimension, targetRect.y + 0.800000011920929D * dimension, targetRect.x + 0.4000000059604645D * dimension, targetRect.y + 0.800000011920929D * dimension);
    
    return gp;
  }
  
  public static Shape orGroupSymbolPart(Rectangle2D.Double targetRect, double scale)
  {
    double dimension = targetRect.width * scale;
    
    GeneralPath gp = new GeneralPath();
    
    gp.moveTo(targetRect.x + 0.44999998807907104D * dimension, targetRect.y + 0.3199999928474426D * dimension);
    gp.curveTo(targetRect.x + 0.550000011920929D * dimension, targetRect.y + 0.3199999928474426D * dimension, targetRect.x + 0.550000011920929D * dimension, targetRect.y + 0.6800000071525574D * dimension, targetRect.x + 0.44999998807907104D * dimension, targetRect.y + 0.6800000071525574D * dimension);
    
    gp.lineTo(targetRect.x + 0.20000000298023224D * dimension, targetRect.y + 0.5D * dimension);
    gp.closePath();
    
    return gp;
  }
  
  public static void drawIcon(Graphics2D g, Shape shape, double scale, Color fillColor, Color borderColor)
  {
    float dimOuter = (float)(0.5D * Math.pow(scale, 0.75D));
    float dimInner = (float)(0.2800000011920929D * Math.pow(scale, 0.75D));
    
    Stroke oldStroke = g.getStroke();
    
    g.setStroke(new BasicStroke(dimOuter, 1, 1));
    g.setColor(borderColor);
    g.draw(shape);
    
    g.setStroke(new BasicStroke(dimInner, 1, 1));
    g.setColor(fillColor);
    g.draw(shape);
    
    g.setStroke(oldStroke);
  }
  
  public static void drawFilledIcon(Graphics2D g, Shape shape, double scale, Color fillColor, Color borderColor)
  {
    float dimOuter = (float)(0.5D * Math.pow(scale, 0.75D));
    float dimInner = (float)(0.2800000011920929D * Math.pow(scale, 0.75D));
    
    Stroke oldStroke = g.getStroke();
    
    g.setStroke(new BasicStroke(dimOuter, 1, 1));
    g.setColor(borderColor);
    g.draw(shape);
    
    g.setStroke(new BasicStroke(dimInner, 1, 1));
    g.setColor(fillColor);
    g.fill(shape);
    
    g.setStroke(oldStroke);
  }
}
