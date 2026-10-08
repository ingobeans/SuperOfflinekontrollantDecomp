/*    */ package gui;
/*    */ import java.awt.*;
/*    */
/*    */
/*    */ import javax.swing.*;
import javax.swing.border.LineBorder;

/*    */
/*    */ public class PanelTemplate extends JPanel {
/*  8 */   public static final Font font = new Font("Monospaced", 0, 14);
/*  9 */   protected static Dimension buttonSize = new Dimension(50, 25);
/* 10 */   protected static Dimension textfieldSize = new Dimension(150, 25); protected JPanel left; protected JPanel center; protected JPanel right;
/*    */   protected JPanel top;
/*    */   private boolean first = true;
/*    */   
/*    */   public PanelTemplate() {
/* 15 */     setLayout(new BorderLayout());
/* 16 */     setBackground(new Color(16575431));
/*    */     
/* 18 */     setBorder(new LineBorder(Color.BLUE, 2));
/* 19 */     this.right = new JPanel(new FlowLayout(2, 10, 5));
/* 20 */     this.center = new JPanel(new FlowLayout(1, 10, 5));
/* 21 */     this.left = new JPanel(new FlowLayout(0, 10, 5));
/* 22 */     add(this.left, "West");
/* 23 */     add(this.center, "Center");
/* 24 */     add(this.right, "East");
/*    */   }
/*    */   public void addLeft(Component component, boolean fixedSize) {
/* 27 */     if (this.first) {
/* 28 */       if (component == null) { add(Box.createHorizontalStrut(100)); }
/*    */       else
/* 30 */       { if (fixedSize) component.setPreferredSize(new Dimension(100, 35)); 
/* 31 */         this.left.add(component); }
/*    */       
/* 33 */       this.first = false;
/*    */     } else {
/* 35 */       this.left.add(component);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void addCenter(Component component) {
/* 40 */     this.center.add(component);
/*    */   }
/*    */   
/*    */   public void addRight(Component component) {
/* 44 */     this.right.add(component);
/*    */   }
/*    */   
/*    */   public void onlyCenter(Component c) {
/* 48 */     remove(this.left);
/* 49 */     remove(this.right);
/* 50 */     addCenter(c);
/*    */   }
/*    */ }


/* Location:              C:\Users\ingemar\Downloads\Offlinekontrollant_1_02\SuperOfflinekontrollant.jar!\gui\PanelTemplate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */