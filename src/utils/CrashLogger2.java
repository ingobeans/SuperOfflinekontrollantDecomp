/*     */ package utils;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.time.LocalDate;
import java.time.LocalDateTime;
/*     */ import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/*     */
/*     */ public class CrashLogger2 implements Thread.UncaughtExceptionHandler {
/*  12 */   private static final List<String> history = new LinkedList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  17 */   private static final int maxHistoryLines = 50;
/*  18 */   private static final int maxLogFiles = 10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void enable() {
/*  29 */     Thread.setDefaultUncaughtExceptionHandler(new CrashLogger2());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void addHistory(String event) {
/*  36 */     String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
/*  37 */     history.add("[" + time + "] " + event);
/*  38 */     if (history.size() > maxHistoryLines) history.removeFirst();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uncaughtException(Thread t, Throwable e) {
/*  46 */     logError(e, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void logError(Throwable throwable, Thread thread) {
/*  53 */     String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
/*  54 */     String filename = "logs/error_" + timestamp + ".log";
/*     */     
/*  56 */     try { FileWriter fw = new FileWriter(filename); 
/*  57 */       try { PrintWriter pw = new PrintWriter(fw);
/*     */         
/*  59 */         try { pw.println("==== Kraschrapport ====");
/*  60 */           pw.println("Datum: " + String.valueOf(LocalDate.now()));
/*  61 */           pw.println("Tidpunkt: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
/*  62 */           pw.println("Tråd: " + thread.getName());
/*  63 */           pw.println();
/*     */           
/*  65 */           pw.println("==== Systeminformation ====");
/*  66 */           pw.println("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
/*  67 */           pw.println("Java version: " + System.getProperty("java.version"));
/*  68 */           pw.println("Användare: " + System.getProperty("user.name"));
/*  69 */           pw.println("Arbetskatalog: " + System.getProperty("user.dir"));
/*  70 */           pw.println();
/*     */           
/*  72 */           pw.println("==== Stacktrace ====");
/*  73 */           throwable.printStackTrace(pw);
/*  74 */           pw.println();
/*     */           
/*  76 */           pw.println("==== Användarhistorik ====");
/*  77 */           if (history.isEmpty()) {
/*  78 */             pw.println("(Ingen historik sparad)");
/*     */           } else {
/*  80 */             for (String entry : history) {
/*  81 */               pw.println(entry);
/*     */             }
/*     */           } 
/*     */           
/*  85 */           pw.println("\n==== Slut på rapport ====");
/*     */           
/*  87 */           System.out.println("Ett oväntat fel inträffade. Rapport sparad i: " + filename);
/*     */           
/*  89 */           pw.close(); } catch (Throwable throwable1) { try { pw.close(); } catch (Throwable throwable2) { throwable1.addSuppressed(throwable2); }  throw throwable1; }  fw.close(); } catch (Throwable throwable1) { try { fw.close(); } catch (Throwable throwable2) { throwable1.addSuppressed(throwable2); }  throw throwable1; }  } catch (IOException e)
/*  90 */     { e.printStackTrace(); }
/*     */ 
/*     */     
/*  93 */     cleanupOldLogs(maxLogFiles);
/*     */   }
/*     */   
/*     */   private void cleanupOldLogs(int maxFiles) {
/*  97 */     File dir = new File("logs");
/*  98 */     File[] logFiles = dir.listFiles((d, name) -> (name.startsWith("error_") && name.endsWith(".log")));
/*  99 */     if (logFiles != null && logFiles.length > maxFiles) {
/* 100 */       Arrays.sort(logFiles, Comparator.comparingLong(File::lastModified));
/* 101 */       for (int i = 0; i < logFiles.length - maxFiles; i++) {
/* 102 */         logFiles[i].delete();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void logStackTrace(Throwable throwable) {
/* 110 */     String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
/* 111 */     String filename = "manual_error_" + timestamp + ".log";
/*     */     
/* 113 */     try { FileWriter fw = new FileWriter(filename); 
/* 114 */       try { PrintWriter pw = new PrintWriter(fw);
/*     */         
/* 116 */         try { pw.println("==== Manuell felrapport ====");
/* 117 */           pw.println("Tidpunkt: " + String.valueOf(LocalDateTime.now()));
/* 118 */           pw.println();
/*     */           
/* 120 */           pw.println("==== Stacktrace ====");
/* 121 */           throwable.printStackTrace(pw);
/* 122 */           pw.println();
/*     */           
/* 124 */           pw.println("==== Användarhistorik ====");
/* 125 */           synchronized (history) {
/* 126 */             if (history.isEmpty()) {
/* 127 */               pw.println("(Ingen historik sparad)");
/*     */             } else {
/* 129 */               for (String entry : history) {
/* 130 */                 pw.println(entry);
/*     */               }
/*     */             } 
/*     */           } 
/*     */           
/* 135 */           pw.println("\n==== Slut på rapport ====");
/*     */           
/* 137 */           pw.close(); } catch (Throwable throwable1) { try { pw.close(); } catch (Throwable throwable2) { throwable1.addSuppressed(throwable2); }  throw throwable1; }  fw.close(); } catch (Throwable throwable1) { try { fw.close(); } catch (Throwable throwable2) { throwable1.addSuppressed(throwable2); }  throw throwable1; }  } catch (IOException e)
/* 138 */     { e.printStackTrace(); }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\ingemar\Downloads\Offlinekontrollant_1_02\SuperOfflinekontrollant.jar\\utils\CrashLogger2.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */