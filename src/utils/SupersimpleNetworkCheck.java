/*    */ package utils;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.Socket;
/*    */ 
/*    */ public class SupersimpleNetworkCheck {
/*    */   public static boolean hasInternet() {
/*  9 */     return (testHost("8.8.8.8", 53) || 
/* 10 */       testHost("1.1.1.1", 53) || 
/* 11 */       testHost("www.example.com", 80));
/*    */   }
/*    */   private static boolean testHost(String host, int port) {
/*    */     
/* 15 */     try { Socket socket = new Socket(); 
/* 16 */       try { socket.connect(new InetSocketAddress(host, port), 2000);
/* 17 */         boolean bool = true;
/* 18 */         socket.close(); return bool; } catch (Throwable throwable) { try { socket.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/* 19 */     { return false; }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\ingemar\Downloads\Offlinekontrollant_1_02\SuperOfflinekontrollant.jar\\utils\SupersimpleNetworkCheck.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */