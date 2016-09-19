package br.ufc.lps.gui.tree.testeVisualizacao;


import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.text.AttributedString;
import java.util.LinkedList;

import prefuse.Visualization;
import prefuse.render.AbstractShapeRenderer;
import prefuse.render.Renderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualItem;

public class RendererMy
  extends AbstractShapeRenderer
{
  private Font itemFontCache = null;
  private Font cachedFont = null;
  private Dimension cachedTextDimension = new Dimension();
  private int cachedMaxTextWidth = 0;
  protected String delimeter = "\n";
  private String m_text;
  private String m_labelName = "label";
  private static Composite alpha50 = AlphaComposite.getInstance(3, 0.5F);
  private RoundRectangle2D boundBox;
  
  private AlphaComposite makeComposite(float alpha)
  {
    int type = 3;
    return AlphaComposite.getInstance(type, alpha);
  }
  
  protected void computeTextDimensions(VisualItem item, String text, double size)
  {
    this.cachedFont = item.getFont();
    if (size != 1.0D) {
      this.cachedFont = FontLib.getFont(this.cachedFont.getName(), this.cachedFont.getStyle(), size * this.cachedFont.getSize());
    }
    FontMetrics fm = Renderer.DEFAULT_GRAPHICS.getFontMetrics(this.cachedFont);
    LineMetrics lm = fm.getLineMetrics(text, DEFAULT_GRAPHICS);
    float centerBaseLine = lm.getBaselineOffsets()[1];
    
    this.cachedTextDimension.width = ((int)(fm.getStringBounds(text, DEFAULT_GRAPHICS).getWidth() * 1.2D));
    this.cachedTextDimension.height = ((int)fm.getStringBounds(text, DEFAULT_GRAPHICS).getHeight());
  }
  
  protected String getText(VisualItem item)
  {
    String s = null;
    if (item.canGetString(this.m_labelName)) {
      return item.getString(this.m_labelName);
    }
    return s;
  }
  
  public FeatureShape getRawFeatureShape(VisualItem item)
  {
    if (!(item instanceof NodeItem)) {
      return null;
    }
    NodeItem nodeItem = (NodeItem)item;
    
    double size = item.getSize();
    
    boolean isFeatureGroup = true;//nodeItem.getInt("NODETYPE") == 200;
    if (isFeatureGroup)
    {
      //FeatureGroup group = (FeatureGroup)nodeItem.get("FEATUREGROUP_OBJECT");
      this.m_text = "teste";//MappingUtils.getCardinalityString(group);
    }
    else
    {
      this.m_text = getText(item);
    }
    int textWidth = 0;
    int textHeight = 0;
    if (this.m_text != null)
    {
      computeTextDimensions(item, this.m_text, size);
      textWidth = this.cachedTextDimension.width;
      textHeight = this.cachedTextDimension.height;
    }
    FeatureShape featureShape = null;
    if (isFeatureGroup)
    {
      //featureShape = new FeatureGroupShape();
    	 featureShape = new FeatureShape();
         
         featureShape.getSlotIcons().add(new ConfigSourceSlotIcon());
    }
    else
    {
      featureShape = new FeatureShape();
      
      featureShape.getSlotIcons().add(new ConfigSourceSlotIcon());
    }
    featureShape.x = ((int)item.getX());
    featureShape.y = ((int)item.getY());
    featureShape.archeight = 5.0D;
    featureShape.arcwidth = 5.0D;
    
    featureShape.setTextDimension(new Dimension(textWidth, textHeight));
    featureShape.setHasConfigstateBubble(false);
    
    int configState = 100;//nodeItem.getInt("FEATURE_CONFIGSTATE");
    if (configState != 100)
    {
      featureShape.setHasConfigstateBubble(true);
      switch (configState)
      {
      case 200: 
        featureShape.setConfigStateBubbleFilled(false); break;
      case 300: 
        featureShape.setConfigStateBubbleFilled(true);
      }
    }
    if (nodeItem.getChildCount() > 0) {
      featureShape.setHasExpanderBubble(true);
    }
    featureShape.calculate(size);
    
    return featureShape;
  }
  
  protected Shape getRawShape(VisualItem item)
  {
    return getRawFeatureShape(item);
  }
  
  public void render(Graphics2D g, VisualItem item)
  {
    FeatureShape featureShape = (FeatureShape)getShape(item);
    if (featureShape == null) {
      return;
    }
    
  }
  
  private boolean isConsequence()//ConfigurationSource configSource)
  {
    return false;//(configSource == ConfigurationSource.MODELCONSEQUENCE) || (configSource == ConfigurationSource.USERCONSEQUENCE);
  }
}
