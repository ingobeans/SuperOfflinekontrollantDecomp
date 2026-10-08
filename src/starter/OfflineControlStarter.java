/*    */ package starter;
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.Color;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.FlowLayout;
/*    */ import java.io.IOException;
/*    */ import java.net.ServerSocket;
/*    */ import javax.swing.*;
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ import theController.ElevSender;
/*    */ import utils.CrashFileLogger;
/*    */ 
/*    */ public class OfflineControlStarter {
/*    */   private static ServerSocket lockSocket;
/*    */   
/*    */  public static void main(String[] args) {
/*    */     try {
/* 23 */       SwingUtilities.invokeAndWait(() -> {
/*    */             int spinnerWidth = 300;
/*    */             
/*    */             spinner = new JFrame("Laddar...");
/*    */             spinner.setLayout(new BorderLayout());
/*    */             spinner.setUndecorated(true);
/*    */             JLabel label = new JLabel("starting offlinecontrol...");
/*    */             JPanel panel = new JPanel(new FlowLayout());
/*    */             panel.add(label);
/*    */             spinner.add(panel, "North");
/*    */             JProgressBar progress = new JProgressBar();
/*    */             progress.setPreferredSize(new Dimension(spinnerWidth, 20));
/*    */             progress.setIndeterminate(true);
/*    */             progress.setBackground(new Color(16578749));
/*    */             progress.setForeground(Color.RED);
/*    */             spinner.add(progress, "South");
/*    */             spinner.pack();
/*    */             spinner.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
/*    */             spinner.setLocationRelativeTo(null);
/*    */             spinner.setVisible(true);
/*    */           });
/* 44 */     } catch (Exception e) {
/* 45 */       System.out.println(e.getMessage());
/*    */     } 
/*    */     
/*    */     try {
/* 49 */       lockSocket = new ServerSocket(53989);
/* 50 */     } catch (IOException e) {
/* 51 */       JOptionPane.showMessageDialog(null, "Programmet körs redan. Detta startförsök avslutas.");
/* 52 */       System.exit(0);
/*    */     } 
/*    */     
/* 55 */     Thread.setDefaultUncaughtExceptionHandler(new CrashFileLogger());
/* 56 */     CrashFileLogger.log("Program startar.");
/*    */     
/* 58 */     new ElevSender();
/* 59 */     spinner.dispose();
/*    */   }
/*    */   private static JFrame spinner; public static final String version = "1.02";
/*    */   public static void closeLock() {
/*    */     try {
/* 64 */       if (lockSocket != null) lockSocket.close(); 
/* 65 */     } catch (IOException e) {
/* 66 */       System.out.println("Problem");
/*    */     } 
/* 68 */     System.exit(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\ingemar\Downloads\Offlinekontrollant_1_02\SuperOfflinekontrollant.jar!\starter\OfflineControlStarter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */