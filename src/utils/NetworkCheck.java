/*    */ package utils;
/*    */ 
/*    */ import java.net.InetSocketAddress;
/*    */ import java.net.Socket;
/*    */ import java.util.Arrays;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.Executors;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ 
/*    */ public class NetworkCheck
/*    */ {
/* 15 */   private static final List<InetSocketAddress> CHECKS = Arrays.asList(new InetSocketAddress[] { new InetSocketAddress("8.8.8.8", 53), new InetSocketAddress("1.1.1.1", 53), new InetSocketAddress("example.com", 80) });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static CompletableFuture<Boolean> isInternetAvailable(int timeoutMs) {
/* 28 */     List<CompletableFuture<Boolean>> futures = CHECKS.stream().map(addr -> checkAddressAsync(addr, timeoutMs)).toList();
/*    */     
/* 30 */     CompletableFuture<Boolean> result = new CompletableFuture<>();
/* 31 */     AtomicInteger remaining = new AtomicInteger(futures.size());
/*    */     
/* 33 */     for (Iterator<CompletableFuture<Boolean>> iterator = futures.iterator(); iterator.hasNext(); ) { CompletableFuture<Boolean> f = iterator.next();
/* 34 */       f.whenComplete((valuea, throwable) -> {
/*    */             if (result.isDone()) {
/*    */               return;
/*    */             }
/*    */ 
/*    */ 
/*    */             
/*    */             if (throwable == null && Boolean.TRUE.equals(valuea)) {
/*    */               result.complete(Boolean.valueOf(true));
/*    */ 
/*    */ 
/*    */               
/*    */               //futures.forEach(() -> {});
/*    */             } else if (remaining.decrementAndGet() == 0) {
/*    */               result.complete(Boolean.valueOf(false));
/*    */             } 
/*    */           }); }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 55 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   private static CompletableFuture<Boolean> checkAddressAsync(InetSocketAddress addr, int timeoutMs) {
/* 60 */     return CompletableFuture.supplyAsync(() -> { try { Socket socket = new Socket(); try { socket.connect(addr, timeoutMs); Boolean bool = Boolean.valueOf(true); socket.close(); return bool; }
/* 61 */             catch (Throwable throwable) { try { socket.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */                throw throwable; }
/*    */              }
/* 64 */           catch (Exception e)
/*    */           { return Boolean.FALSE; }
/*    */         
/*    */         }, EXECUTOR);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void main(String[] args) {
/* 72 */     isInternetAvailable(2000).thenAccept(online -> {
/*    */           if (online) {
/*    */             System.out.println("Internet tillgängligt ✅");
/*    */           } else {
/*    */             System.out.println("Ingen internetanslutning ❌");
/*    */           } 
/*    */         });
/*    */ 
/*    */     
/*    */     try {
/* 82 */       Thread.sleep(3000L);
/* 83 */     } catch (InterruptedException _a) {}
/*    */     
/* 85 */     EXECUTOR.shutdown();
/*    */   }
/*    */ }


/* Location:              C:\Users\ingemar\Downloads\Offlinekontrollant_1_02\SuperOfflinekontrollant.jar\\utils\NetworkCheck.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */