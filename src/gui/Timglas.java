/*    */ package gui;
/*    */ import javax.swing.*;
/*    */
/*    */
/*    */
import java.awt.*;
import java.util.Objects;

/*    */
/*    */ public class Timglas {
/*    */   public static void main(String[] args) {
/*  9 */     SwingUtilities.invokeLater(() -> {
/*    */           JFrame frame = new JFrame("Exempel");
/*    */           frame.setDefaultCloseOperation(3);
/*    */           frame.setSize(300, 200);
/*    */           frame.setLocationRelativeTo((Component)null);
/*    */           frame.setVisible(true);
/*    */           showWaitDialog(frame, ()->{});
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void showWaitDialog(JFrame parent, Runnable job) {
/* 32 */     JDialog dialog = new JDialog(parent, "Arbetar...", true);
/* 33 */     dialog.setLayout(new BorderLayout());
/* 34 */     dialog.add(new JLabel("Vänligen vänta..."), "North");
/*    */ 
/*    */     
/* 37 */     JProgressBar bar = new JProgressBar();
/* 38 */     bar.setIndeterminate(true);
/* 39 */     dialog.add(bar, "Center");
/*    */     
/* 41 */     dialog.setSize(200, 100);
/* 42 */     dialog.setLocationRelativeTo(parent);
/*    */ 
/*    */     
/* 45 */     (new Thread(() -> {
/*    */           job.run(); Objects.requireNonNull(dialog);
/*    */           SwingUtilities.invokeLater(dialog::dispose);
/* 48 */         })).start();
/*    */ 
/*    */     
/* 51 */     dialog.setVisible(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\ingemar\Downloads\Offlinekontrollant_1_02\SuperOfflinekontrollant.jar!\gui\Timglas.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */