/*    */ package timers;
/*    */ 
/*    */ public class MyClock extends Thread {
/*    */   private int minutes;
/*    */   
/*    */   public MyClock() {
/*  7 */     this.minutes = 0;
/*  8 */     start();
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 13 */     while (!Thread.currentThread().isInterrupted()) {
/*    */       try {
/* 15 */         Thread.sleep(6000L);
/*    */       }
/* 17 */       catch (InterruptedException e) {
/* 18 */         Thread.currentThread().interrupt();
/*    */       } 
/* 20 */       this.minutes++;
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getMinutes() {
/* 25 */     return this.minutes;
/*    */   }
/*    */ }


/* Location:              C:\Users\ingemar\Downloads\Offlinekontrollant_1_02\SuperOfflinekontrollant.jar!\timers\MyClock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */