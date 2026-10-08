/*    */ package utils;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintWriter;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.time.LocalDate;
/*    */ import java.time.LocalTime;
/*    */ import java.time.format.DateTimeFormatter;
/*    */ import java.util.Arrays;
/*    */ import java.util.Comparator;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class CrashFileLogger implements Thread.UncaughtExceptionHandler {
/* 16 */   private static final String LOG_FILE = "log_" + (new SimpleDateFormat("yyyyMMdd_HHmmss"))
/* 17 */     .format(new Date()) + ".log"; private static final int MAX_FILES = 3;
/*    */   private static final String LOG_DIR = "logs";
/*    */   private static PrintWriter writer;
/*    */   
/*    */   static {
/* 22 */     cleanupOldLogs(3);
/*    */     try {
/* 24 */       File dir = new File("logs");
/* 25 */       if (!dir.exists()) dir.mkdirs(); 
/* 26 */       writer = new PrintWriter(new FileWriter(new File(dir, LOG_FILE), true), true);
/* 27 */       writer.println("==== Rapport ====");
/* 28 */       writer.println("Datum: " + String.valueOf(LocalDate.now()));
/* 29 */       writer.println("Tidpunkt: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
/* 30 */       writer.println();
/*    */       
/* 32 */       writer.println("==== Systeminformation ====");
/* 33 */       writer.println("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
/* 34 */       writer.println("Java version: " + System.getProperty("java.version"));
/* 35 */       writer.println("Användare: " + System.getProperty("user.name"));
/* 36 */       writer.println("Arbetskatalog: " + System.getProperty("user.dir"));
/* 37 */       writer.println();
/* 38 */       writer.println("Meddelandetyper: 292 kopplar, 109 registrering, 234 lämnar in, 355 klar, 591 online, 613 oväntad online ");
/* 39 */       writer.flush();
/*    */     }
/* 41 */     catch (IOException e) {
/* 42 */       System.out.println("Fel vid start av logging: " + e.getMessage());
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void log(String message) {
/* 47 */     writer.println(timestamp() + " - " + timestamp());
/*    */   }
/*    */   
/*    */   private static String timestamp() {
/* 51 */     return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
/*    */   }
/*    */ 
/*    */   
/*    */   public void uncaughtException(Thread t, Throwable e) {
/* 56 */     writer.println("### FEL i tråden \"" + t.getName() + "\" ###");
/* 57 */     writer.println("Tid: " + timestamp());
/* 58 */     e.printStackTrace(writer);
/* 59 */     writer.println("### --- SLUT PÅ FELRAPPORT --- ###");
/*    */   }
/*    */   
/*    */   public static void logStackTrace(Throwable throwable) {
/* 63 */     synchronized (writer) {
/* 64 */       writer.println("### ---- FEL! ---- ###");
/* 65 */       writer.println("Tid: " + timestamp());
/* 66 */       throwable.printStackTrace(writer);
/* 67 */       writer.println("### --- SLUT PÅ FELRAPPORT --- ###");
/* 68 */       writer.flush();
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void cleanupOldLogs(int maxFiles) {
/* 73 */     File dir = new File("logs");
/* 74 */     File[] logFiles = dir.listFiles();
/* 75 */     if (logFiles != null && logFiles.length > maxFiles) {
/* 76 */       Arrays.sort(logFiles, Comparator.comparingLong(File::lastModified));
/* 77 */       for (int i = 0; i < logFiles.length - maxFiles; i++) {
/* 78 */         logFiles[i].delete();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void closeFile(String message) {
/* 84 */     log(message);
/* 85 */     writer.flush();
/* 86 */     writer.close();
/*    */   }
/*    */ }


/* Location:              C:\Users\ingemar\Downloads\Offlinekontrollant_1_02\SuperOfflinekontrollant.jar\\utils\CrashFileLogger.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */