package br.ufc.lps.gui.tree.testeVisualizacao;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import prefuse.render.Renderer;
import prefuse.visual.VisualItem;

public class ConfigSourceSlotIcon
  extends SlotIcon
{
  Font iconFont = new Font("Courier New", 0, 10);
  private static FontMetrics fontMetrics = Renderer.DEFAULT_GRAPHICS.getFontMetrics(new Font("Courier New", 1, 8));
  private static LineMetrics lineMetrics = fontMetrics.getLineMetrics("MC", Renderer.DEFAULT_GRAPHICS);
  
  public ConfigSourceSlotIcon(double width, double height)
  {
    super(width, height);
  }
  
  public ConfigSourceSlotIcon()
  {
    super(20.0D, 14.0D);
  }
  
  public void draw(Graphics2D g2d, VisualItem item)//, Feature feature)
  {
	  /*
    if (feature == null) {
      return;
    }
    ConfigurationSource configSource = null;
    if (!feature.getSelectedFeature().isEmpty()) {
      configSource = ((SelectedFeature)feature.getSelectedFeature().get(0)).getConfigurationSource();
    }
    if (!feature.getEliminatedFeature().isEmpty()) {
      configSource = ((EliminatedFeature)feature.getEliminatedFeature().get(0)).getConfigurationSource();
    }
    String label = "";
    if (configSource != null)
    {
      switch (configSource)
      {
      case MODEL: 
        label = "(M)";
        
        break;
      case MODELCONSEQUENCE: 
        label = "(MC)";
        
        break;
      case USER: 
        label = "(U)";
        
        break;
      case USERCONSEQUENCE: 
        label = "(UC)";
      }*/
	  String label = "(UC)";
      Rectangle2D rect = getPositionRectangle();
      
      g2d.setFont(this.iconFont);
      
      int yPos = (int)(rect.getCenterY() + lineMetrics.getAscent() / 2.0F);
      int xPos = (int)rect.getX();
      
      g2d.drawString(label, xPos, yPos);
    }
  }

