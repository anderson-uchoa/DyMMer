package br.ufc.lps.jg;


import java.awt.Color;
import java.awt.geom.Rectangle2D;

import org.jgraph.JGraph;
import org.jgraph.event.*;

import javax.swing.*;

public class hello extends JFrame{
		  public hello (String title)
		  {
		   super (title);

		   setDefaultCloseOperation (EXIT_ON_CLOSE);

		   JGraph graph = new JGraph ();

		   GraphSelectionListener gsl;
		   gsl = new GraphSelectionListener ()
		      {
		        public void valueChanged (GraphSelectionEvent gse)
		        {
		          Object [] cells = gse.getCells ();
		          for (int i = 0; i < cells.length; i++)
		            System.out.println (cells [i] + ": " +
		                      (gse.isAddedCell (cells [i]) ?
		                               "added" :
		                               "removed"));
		        }
		      };
		   graph.addGraphSelectionListener (gsl);

		   getContentPane ().add (new JScrollPane (graph));

		   pack ();

		   setVisible (true);
		  }

		  public static void main (String [] args)
		  {
		   new hello ("Sample Graph 3");
		  }
}
