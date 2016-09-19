package br.ufc.lps.gui.tree.testeVisualizacao;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.LinkedList;
import java.util.logging.Logger;
import prefuse.Display;

public class FeatureShape
  extends RoundRectangle2D.Double
{
  public static enum Area
  {
    CONFIGSYMBOL,  EXPANDER,  CONFIGSTATE,  LABEL,  BACKGROUND,  NONE;
    
    private Area() {}
  }
  
  private Dimension textDimension = null;
  private double verticalPadding = 2.0D;
  private double horizontalPadding = 2.0D;
  private double configBoxWidth = 20.0D;
  private double expanderBubbleWidth = 10.0D;
  private double configStateBubbleWidth = 7.0D;
  private boolean hasExpanderBubble = false;
  private boolean hasConfigstateBubble = false;
  private boolean configStateBubbleFilled = false;
  private boolean showDetails = false;
  private Point2D.Double labelTextPosition = new Point2D.Double();
  private Rectangle2D.Double labelTextRect = new Rectangle2D.Double();
  private Rectangle2D.Double configSymbolRect = new Rectangle2D.Double();
  private Rectangle2D.Double expanderBubbleRect = new Rectangle2D.Double();
  private Rectangle2D.Double configStateBubbleRect = new Rectangle2D.Double();
  private Rectangle2D.Double backgroundBox = new Rectangle2D.Double();
  private static final Logger log = Logger.getLogger(FeatureShape.class.getName());
  private LinkedList<SlotIcon> slotIcons = new LinkedList();
  
  protected double center(double max, double objectSize)
  {
    return max - objectSize / 2.0D;
  }
  
  protected void centerRectVertically(double topY, double max, Rectangle2D.Double rect)
  {
    rect.y = (topY + (this.height / 2.0D - rect.height / 2.0D));
  }
  
  public void calculate(double size)
  {
    double verticalPadding = this.verticalPadding * size;
    double horizontalPadding = this.horizontalPadding * size;
    
    this.height = (this.textDimension.getHeight() + 2.0D * verticalPadding);
    
    double offsetX = 0.0D;
    double yPos = getY();
    double maxHeight = getHeight();
    
    this.backgroundBox.y = getY();
    this.backgroundBox.height = maxHeight;
    
    this.configStateBubbleRect.x = (this.x + offsetX);
    this.configStateBubbleRect.width = this.configStateBubbleWidth;
    this.configStateBubbleRect.height = this.configStateBubbleWidth;
    this.configStateBubbleRect.y = getY();
    
    centerRectVertically(this.y, maxHeight, this.configStateBubbleRect);
    
    offsetX += this.configStateBubbleWidth;
    
    this.backgroundBox.x = (this.x + offsetX);
    double backgroundBoxStartOffset = offsetX;
    
    this.configSymbolRect.x = (this.x + offsetX + horizontalPadding);
    this.configSymbolRect.y = (this.y + verticalPadding);
    this.configSymbolRect.height = (maxHeight - 2.0D * verticalPadding);
    this.configSymbolRect.width = this.configBoxWidth;
    
    offsetX += this.configSymbolRect.width + 2.0D * horizontalPadding;
    
    this.labelTextPosition.x = (this.x + offsetX + horizontalPadding);
    this.labelTextPosition.y = (this.y + verticalPadding + center(maxHeight, this.textDimension.getHeight()));
    
    this.labelTextRect.x = this.labelTextPosition.x;
    this.labelTextRect.y = this.labelTextPosition.y;
    this.labelTextRect.width = this.textDimension.width;
    this.labelTextRect.height = this.textDimension.height;
    
    offsetX += this.textDimension.width + 2.0D * horizontalPadding;
    if ((this.slotIcons != null) && (!this.slotIcons.isEmpty())) {
      for (SlotIcon slotIcon : this.slotIcons)
      {
        Rectangle2D.Double iconRect = new Rectangle2D.Double();
        iconRect.width = slotIcon.getDimension().getWidth(size);
        iconRect.height = slotIcon.getDimension().getWidth(size);
        
        centerRectVertically(this.y, maxHeight, iconRect);
        
        iconRect.x = (this.x + offsetX);
        
        offsetX += iconRect.width + horizontalPadding;
        
        slotIcon.setPositionRectangle(iconRect);
      }
    }
    offsetX += horizontalPadding;
    if (this.hasExpanderBubble)
    {
      offsetX += this.expanderBubbleWidth / 2.0D;
      
      this.backgroundBox.width = (offsetX - backgroundBoxStartOffset);
      
      this.expanderBubbleRect.width = this.expanderBubbleWidth;
      this.expanderBubbleRect.height = this.expanderBubbleWidth;
      this.expanderBubbleRect.x = (this.x + offsetX - this.expanderBubbleWidth / 2.0D);
      centerRectVertically(this.y, maxHeight, this.expanderBubbleRect);
      
      offsetX += this.expanderBubbleWidth / 2.0D;
    }
    else
    {
      this.backgroundBox.width = (offsetX - backgroundBoxStartOffset);
    }
    this.width = offsetX;
  }
  
  public Dimension getTextDimension()
  {
    return this.textDimension;
  }
  
  public void setTextDimension(Dimension textDimension)
  {
    this.textDimension = textDimension;
  }
  
  public double getVerticalPadding()
  {
    return this.verticalPadding;
  }
  
  public void setVerticalPadding(double verticalPadding)
  {
    this.verticalPadding = verticalPadding;
  }
  
  public double getHorizontalPadding()
  {
    return this.horizontalPadding;
  }
  
  public void setHorizontalPadding(double horizontalPadding)
  {
    this.horizontalPadding = horizontalPadding;
  }
  
  public double getConfigBoxWidth()
  {
    return this.configBoxWidth;
  }
  
  public void setConfigBoxWidth(double configBoxWidth)
  {
    this.configBoxWidth = configBoxWidth;
  }
  
  public double getExpanderBubbleWidth()
  {
    return this.expanderBubbleWidth;
  }
  
  public void setExpanderBubbleWidth(double expanderBubbleWidth)
  {
    this.expanderBubbleWidth = expanderBubbleWidth;
  }
  
  public double getConfigStateBubbleWidth()
  {
    return this.configStateBubbleWidth;
  }
  
  public void setConfigStateBubbleWidth(double configStateBubbleWidth)
  {
    this.configStateBubbleWidth = configStateBubbleWidth;
  }
  
  public boolean isHasExpanderBubble()
  {
    return this.hasExpanderBubble;
  }
  
  public void setHasExpanderBubble(boolean hasExpanderBubble)
  {
    this.hasExpanderBubble = hasExpanderBubble;
  }
  
  public boolean isHasConfigstateBubble()
  {
    return this.hasConfigstateBubble;
  }
  
  public void setHasConfigstateBubble(boolean hasConfigstateBubble)
  {
    this.hasConfigstateBubble = hasConfigstateBubble;
  }
  
  public Point2D.Double getLabelTextPosition()
  {
    return this.labelTextPosition;
  }
  
  public void setLabelTextPosition(Point2D.Double labelTextPosition)
  {
    this.labelTextPosition = labelTextPosition;
  }
  
  public Rectangle2D.Double getConfigSymbolRect()
  {
    return this.configSymbolRect;
  }
  
  public void setConfigSymbolRect(Rectangle2D.Double configSymbolRect)
  {
    this.configSymbolRect = configSymbolRect;
  }
  
  public Rectangle2D.Double getExpanderBubbleRect()
  {
    return this.expanderBubbleRect;
  }
  
  public void setExpanderBubbleRect(Rectangle2D.Double expanderBubbleRect)
  {
    this.expanderBubbleRect = expanderBubbleRect;
  }
  
  public Rectangle2D.Double getConfigStateBubbleRect()
  {
    return this.configStateBubbleRect;
  }
  
  public void setConfigStateBubbleRect(Rectangle2D.Double configStateBubbleRect)
  {
    this.configStateBubbleRect = configStateBubbleRect;
  }
  
  public Rectangle2D.Double getBackgroundBox()
  {
    return this.backgroundBox;
  }
  
  public void setBackgroundBox(Rectangle2D.Double backgroundBox)
  {
    this.backgroundBox = backgroundBox;
  }
  
  public boolean isConfigStateBubbleFilled()
  {
    return this.configStateBubbleFilled;
  }
  
  public void setConfigStateBubbleFilled(boolean configStateBubbleFilled)
  {
    this.configStateBubbleFilled = configStateBubbleFilled;
  }
  
  public Area evaluateClick(MouseEvent e, Display display)
  {
    AffineTransform transformation = display.getTransform();
    
    Point2D transformedClickpoint = null;
    try
    {
      transformedClickpoint = transformation.inverseTransform(new Point2D.Double(e.getX(), e.getY()), null);
    }
    catch (NoninvertibleTransformException e1)
    {
      log.warning("NoninvertibleTransformException: Could not invert display transformation");
      return Area.NONE;
    }
    if (this.configSymbolRect.contains(transformedClickpoint)) {
      return Area.CONFIGSYMBOL;
    }
    if (this.labelTextRect.contains(transformedClickpoint)) {
      return Area.LABEL;
    }
    if ((this.hasConfigstateBubble) && (this.configStateBubbleRect.contains(transformedClickpoint))) {
      return Area.CONFIGSTATE;
    }
    if ((this.hasExpanderBubble) && (this.expanderBubbleRect.contains(transformedClickpoint))) {
      return Area.EXPANDER;
    }
    if (this.backgroundBox.contains(transformedClickpoint)) {
      return Area.BACKGROUND;
    }
    return Area.NONE;
  }
  
  public LinkedList<SlotIcon> getSlotIcons()
  {
    return this.slotIcons;
  }
}
