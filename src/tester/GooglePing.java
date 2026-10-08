/*    */ package tester;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.Socket;
/*    */ 
/*    */ public class GooglePing {
/*    */   public static void main(String[] args) {
/*  9 */     int count = 0;
/*    */     while (true) {
/* 11 */       count++; 
/* 12 */       try { Socket socket = new Socket(); 
/* 13 */         try { Thread.sleep(5000L);
/* 14 */           socket.connect(new InetSocketAddress("8.8.8.8", 53), 2000);
/* 15 */           System.out.println("" + count + ": JA SVAR!!!");
/* 16 */           socket.close(); } catch (Throwable throwable) { try { socket.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/* 17 */       { System.out.print("" + count + ": Nej inget svar: " + count);
/* 18 */         System.out.println(e.getMessage()); }
/* 19 */       catch (InterruptedException e)
/* 20 */       { System.out.println("" + count + ": Interrupt");
/* 21 */         System.out.println(e.getMessage()); }
/*    */     
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ingemar\Downloads\Offlinekontrollant_1_02\SuperOfflinekontrollant.jar!\tester\GooglePing.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */