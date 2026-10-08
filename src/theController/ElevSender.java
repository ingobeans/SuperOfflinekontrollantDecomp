/*     */ package theController;
/*     */ 
/*     */ import com.fasterxml.jackson.core.JsonProcessingException;
/*     */ import com.fasterxml.jackson.databind.ObjectMapper;
/*     */ import dtos.StudentDTO;
/*     */ import dtos.TeacherDTO;
/*     */ import gui.StartMenu;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.DatagramSocket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.LinkedList;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import starter.OfflineControlStarter;
/*     */ import utils.CrashFileLogger;
/*     */ import utils.SupersimpleNetworkCheck;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ElevSender
/*     */   extends Thread
/*     */ {
/*     */   public static final int MESSTYPE_CONNECT = 292;
/*     */   public static final int MESSTYPE_REGISTRATION = 109;
/*     */   public static final int MESSTYPE_SUBMISSION_IN_PROGRESS = 234;
/*     */   public static final int MESSTYPE_FINISH_DATA = 355;
/*     */   public static final int MESSTYPE_ALERT_CALL = 591;
/*     */   public static final int MESSTYPE_ERROR = 666;
/*     */   public static final int WRONG_NAME = 1004;
/*     */   public static final int ALREADY_REGISTERED = 937;
/*     */   public static final int BROKEN_DTO = 495;
/*     */   public static final int NO_ANSWER = 817;
/*     */   public static final int UNKNOWN_ERROR = 434;
/*     */   public static final int EXTERNAL_IP = 1000;
/*     */   private String name;
/*     */   private DatagramSocket socket;
/*  50 */   private int errorStatusCode = 0; private InetAddress teacherAddress; private static final int teacherPort = 6789; private final String twoOctetsOfMyIP; private final ObjectMapper mapper; private final int secretCode; private static final long longSleep = 30000L; private static final long shortSleep = 10000L; private long sleepTime; private boolean firstSubmission = true; private boolean firstTimeOnline; private boolean firstTimeOffline; private boolean beginning; public static final String OK = "OK"; public static final String FAIL_MESS = "Ej godkänd offlinekontroll"; private volatile boolean isSubmitting = false;
/*  51 */   private final LinkedList<String> usedIPaddresses = new LinkedList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ElevSender() {
/*  64 */     this.secretCode = (int)(1000000.0D + Math.random() * 9000000.0D);
/*  65 */     this.mapper = new ObjectMapper();
/*  66 */     this.sleepTime = 10000L;
/*  67 */     this.firstTimeOnline = false;
/*  68 */     this.firstTimeOffline = true;
/*     */     
/*  70 */     String myIP = getMyIP();
/*  71 */     if (myIP.startsWith("127.") || !hasInternetWithPingTest()) {
/*  72 */       JOptionPane.showMessageDialog(null, "Du verkar vara offline. Sätt på wifi och börja om");
/*  73 */       OfflineControlStarter.closeLock();
/*     */     } 
/*     */     
/*     */     try {
/*  77 */       this.socket = new DatagramSocket();
/*  78 */       this.socket.setSoTimeout(3000);
/*  79 */     } catch (SocketException e) {
/*  80 */       JOptionPane.showMessageDialog(null, "Oväntat socketfel. Kontakta admin.");
/*  81 */       OfflineControlStarter.closeLock();
/*     */     } 
/*     */     
/*  84 */     int secondDot = myIP.indexOf('.', myIP.indexOf('.') + 1);
/*  85 */     this.twoOctetsOfMyIP = myIP.substring(0, secondDot + 1);
/*  86 */     this.usedIPaddresses.add(myIP);
/*  87 */     CrashFileLogger.log("Startkod: " + this.secretCode % 1000);
/*  88 */     CrashFileLogger.log("Min ip: " + myIP);
/*  89 */     new StartMenu(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*  94 */     int counter = 0;
/*  95 */     String previousIP = "";
/*  96 */     CrashFileLogger.log("##### Offlineövervakning startade #####");
/*  97 */     this.beginning = true;
/*  98 */     this.firstTimeOnline = true;
/*  99 */     this.firstTimeOffline = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     while (!Thread.currentThread().isInterrupted()) {
/* 107 */       counter++;
/*     */ 
/*     */       
/*     */       try {
/* 111 */         Thread.sleep(this.sleepTime);
/* 112 */       } catch (InterruptedException e) {
/* 113 */         Thread.currentThread().interrupt();
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 118 */       String currentIP = getMyIP();

                // fusk: blockera ip koll
/* 119 */       if (false && !currentIP.equals(previousIP) && !this.usedIPaddresses.contains(currentIP)) {
/* 120 */         this.usedIPaddresses.add(currentIP);
/* 121 */         CrashFileLogger.log("" + counter + " Ny IP: " + counter);
/*     */         
/* 123 */         if (!currentIP.startsWith(this.twoOctetsOfMyIP) && !currentIP.startsWith("127.")) {
/* 124 */           CrashFileLogger.log("" + counter + " Oväntad ip-adress: " + counter);
/* 125 */           this.errorStatusCode += 1000;
/* 126 */           showWarning(currentIP);
/*     */         } 
/*     */       } 
/* 129 */       previousIP = currentIP;
/*     */ 
/*     */       
/* 132 */       int type = this.isSubmitting ? 234 : 591;

                // fusk: skicka endast ping om vid submit
                if (this.isSubmitting) {
/* 133 */           sendMessage(new StudentDTO(this.name, type), false);
                }
/* 134 */       if (this.isSubmitting && this.firstSubmission) {
/* 135 */         CrashFileLogger.log("" + counter + " ONLINE och inlämning påbörjas! ");
/* 136 */         this.firstSubmission = false;
/*     */       } 
/*     */       
/* 139 */       if (this.isSubmitting) {
/*     */         continue;
/*     */       }

                // fusk: blockera internet koll
/* 142 */       if (false && hasInternetWithPingTest()) {
/* 143 */         this.sleepTime = 10000L;
/* 144 */         this.firstTimeOffline = true;
/* 145 */         if (!this.beginning) {
/* 146 */           this.errorStatusCode++;
/* 147 */           if (this.firstTimeOnline) {
/* 148 */             CrashFileLogger.log("" + counter + " HAR OTILLÅTEN ÅTKOMST TILL INTERNET, ip: " + counter);
/* 149 */             this.firstTimeOnline = false;
/*     */           } 
/*     */         }  continue;
/*     */       }
/* 153 */       this.beginning = false;
/* 154 */       this.firstTimeOnline = true;
/* 155 */       if (this.firstTimeOffline) {
/* 156 */         CrashFileLogger.log("" + counter + " OFFLINE <<<---");
/* 157 */         this.firstTimeOffline = false; continue;
/*     */       } 
/* 159 */       this.sleepTime = 30000L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void showWarning(String badIP) {
/* 165 */     JFrame warningFrame = new JFrame();
/* 166 */     warningFrame.setLayout(new FlowLayout());
/* 167 */     JLabel label = new JLabel("Konstig IP-adress registrerad: " + badIP);
/* 168 */     label.setFont(new Font("ARIAL NARROW", 1, 30));
/* 169 */     label.setForeground(Color.RED);
/* 170 */     label.setOpaque(true);
/* 171 */     label.setBackground(Color.BLACK);
/* 172 */     warningFrame.add(label);
/* 173 */     warningFrame.pack();
/* 174 */     warningFrame.setLocationRelativeTo((Component)null);
/* 175 */     warningFrame.setAlwaysOnTop(true);
/* 176 */     warningFrame.setDefaultCloseOperation(2);
/* 177 */     warningFrame.setVisible(true);
/*     */   }
/*     */   
/*     */   private synchronized void sendMessage(StudentDTO dto, boolean doLogging) {
/* 181 */     if (dto == null) {
/* 182 */       CrashFileLogger.log("dton var tom. Konstigt");
/*     */       return;
/*     */     } 
/*     */     try {
/* 186 */       String json = this.mapper.writeValueAsString(dto);
/* 187 */       byte[] array = json.getBytes();
/* 188 */       DatagramPacket packet = new DatagramPacket(array, array.length, this.teacherAddress, 6789);
/* 189 */       if (doLogging) CrashFileLogger.log("Skickade följande: " + json); 
/* 190 */       this.socket.send(packet);
/* 191 */     } catch (JsonProcessingException e) {
/* 192 */       CrashFileLogger.log("JSON-fel till string! Message: " + e.getMessage());
/* 193 */     } catch (Exception e) {
/* 194 */       JOptionPane.showMessageDialog(null, "Sändfel 87");
/* 195 */       CrashFileLogger.log("Gick inte att skicka. Message: " + e.getMessage());
/* 196 */       CrashFileLogger.logStackTrace(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TeacherDTO receiveMessage() {
/* 210 */     byte[] buf = new byte[512];
/* 211 */     DatagramPacket dp = new DatagramPacket(buf, buf.length);
/*     */     try {
/* 213 */       CrashFileLogger.log("Väntar på svar från läraren...");
/* 214 */       this.socket.receive(dp);
/* 215 */       String recievedString = new String(buf, 0, dp.getLength());
/* 216 */       CrashFileLogger.log("Mottagen string: " + recievedString);
/*     */       
/*     */       try {
/* 219 */         TeacherDTO incomingDTO = (TeacherDTO)this.mapper.readValue(recievedString, TeacherDTO.class);
/* 220 */         return incomingDTO;
/* 221 */       } catch (JsonProcessingException e) {
/* 222 */         CrashFileLogger.log("JSON-fel: " + e.getMessage());
/* 223 */         return new TeacherDTO(666, false, 495);
/*     */       } 
/* 225 */     } catch (SocketTimeoutException timeoutException) {
/* 226 */       CrashFileLogger.log("Timeout på mottagningen! Inget kom...");
/* 227 */       return new TeacherDTO(666, false, 817);
/* 228 */     } catch (Exception e) {
/* 229 */       CrashFileLogger.log("Oväntat fel vid mottagning: " + e.getMessage());
/* 230 */       return new TeacherDTO(666, false, 434);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String handleFinishData(long examseconds, String submitStartTime) {
/* 235 */     interrupt();
/* 236 */     String finishingIP = getMyIP();
/* 237 */     CrashFileLogger.log("Skickar data för avslutning: ");
/* 238 */     CrashFileLogger.log("-> Skickar med IP-adress: " + finishingIP);
/* 239 */     CrashFileLogger.log("-> Kod: " + this.secretCode % 1000);
/* 240 */     CrashFileLogger.log("-> Provskrivningstid: " + examseconds + " s");
/* 241 */     CrashFileLogger.log("-> Starttid inlämning: " + submitStartTime);
/* 242 */     CrashFileLogger.log("-> Felkod: " + this.errorStatusCode);
/* 243 */     CrashFileLogger.log("-> Ip-adresser: " + this.usedIPaddresses.toString());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     StudentDTO sendDTO = new StudentDTO(this.name, this.secretCode, 355, examseconds, submitStartTime, this.usedIPaddresses.toString(), this.errorStatusCode);
/*     */     
/* 252 */     sendMessage(sendDTO, true);
/*     */     
/* 254 */     TeacherDTO received = receiveMessage();
/* 255 */     if (received.getMessageType() == 666) {
/* 256 */       CrashFileLogger.log("Fel vid mottagning vid finishresponse: " + received.getErrorType());
/* 257 */       if (received.getErrorType() == 817) return "Inget svar. Försök igen"; 
/* 258 */       if (received.getErrorType() == 495) return "Fel på nätverkskommunikationen. Försök igen"; 
/* 259 */       return "Oväntat fel, försök igen";
/*     */     } 
/* 261 */     if (received.getMessageType() != 355) {
/* 262 */       CrashFileLogger.log("Mottog fel typ av meddelande");
/* 263 */       return "Oväntat konstigt svar, försök igen";
/*     */     } 
/* 265 */     return received.isResponseOK() ? "OK" : "Ej godkänd offlinekontroll";
/*     */   }
/*     */   
/*     */   public void setSubmitting() {
/* 269 */     this.isSubmitting = true;
/*     */   }
/*     */   
/*     */   public boolean hasInternetWithPingTest() {
/* 273 */     return SupersimpleNetworkCheck.hasInternet();
/*     */   }
/*     */ 
/*     */   
/*     */   public String tryConnect(String ipAddress) {
/* 278 */     if (ipAddress.trim().isEmpty()) return "Du måste ange ip"; 
/* 279 */     if (!ipAddress.contains(".")) return "Du måste skriva punkter";
/*     */     
/* 281 */     String teacherIP = ipAddress.trim();
/*     */     try {
/* 283 */       this.teacherAddress = InetAddress.getByName(teacherIP);
/* 284 */       if (InetAddress.getLocalHost().getHostAddress().startsWith("127.")) return "Du är offline. Sätt på wifi!"; 
/* 285 */     } catch (UnknownHostException e) {
/* 286 */       CrashFileLogger.log("Angiven lärar-IP " + ipAddress + " funkade inte");
/* 287 */       return "IP-adressen var ogiltig";
/*     */     } 
/* 289 */     CrashFileLogger.log("Skickar kontaktförsök till " + teacherIP);
/*     */     
/* 291 */     StudentDTO dto = new StudentDTO("", 292);
/* 292 */     sendMessage(dto, true);
/* 293 */     TeacherDTO received = receiveMessage();
/* 294 */     if (received.getMessageType() == 666) {
/* 295 */       CrashFileLogger.log("Fel vid mottagning vid kontaktförsök: " + received.getErrorType());
/* 296 */       if (received.getErrorType() == 817) return "Inget svar. Försök igen"; 
/* 297 */       if (received.getErrorType() == 495) return "Fel på nätverkskommunikationen. Försök igen"; 
/* 298 */       return "Oväntat fel, försök igen";
/*     */     } 
/* 300 */     if (received.isResponseOK() && received.getMessageType() == 292) {
/* 301 */       CrashFileLogger.log("Kontakt lyckades");
/* 302 */       return "OK";
/*     */     } 
/*     */     try {
/* 305 */       CrashFileLogger.log("Annat oväntat fel: " + this.mapper.writeValueAsString(received));
/* 306 */     } catch (JsonProcessingException e) {
/* 307 */       CrashFileLogger.log("JSON-fel!");
/*     */     } 
/* 309 */     return "Ett oväntat fel, prova igen";
/*     */   }
/*     */ 
/*     */   
/*     */   public String tryRegister(String name) {
/* 314 */     if (name.trim().isEmpty()) return "Du måste ange namn";
/*     */     
/* 316 */     this.name = name.trim();
/* 317 */     CrashFileLogger.log("Skickar fråga om att registrera " + name);
/*     */     
/* 319 */     StudentDTO dto = new StudentDTO(name, 109);
/* 320 */     dto.setCode(this.secretCode);
/* 321 */     sendMessage(dto, true);
/* 322 */     TeacherDTO received = receiveMessage();
/*     */     
/* 324 */     if (received.getMessageType() == 666) {
/* 325 */       CrashFileLogger.log("Fel vid mottagning vid registerförsök: " + received.getErrorType());
/* 326 */       if (received.getErrorType() == 817) return "Inget svar. Försök igen"; 
/* 327 */       if (received.getErrorType() == 495) return "Fel på nätverkskommunikationen. Försök igen"; 
/* 328 */       return "Oväntat fel, försök igen";
/*     */     } 
/* 330 */     if (received.getMessageType() != 109) {
/* 331 */       CrashFileLogger.log("Mottog fel typ av meddelande");
/* 332 */       return "Oväntat konstigt svar, försök igen";
/*     */     } 
/* 334 */     if (received.isResponseOK()) {
/* 335 */       start();
/* 336 */       CrashFileLogger.log("Registrering lyckades");
/* 337 */       return "OK";
/*     */     } 
/* 339 */     if (received.getErrorType() == 937) {
/* 340 */       CrashFileLogger.log("Redan registrerad");
/* 341 */       return "Redan registrerad, säg till läraren.";
/*     */     } 
/* 343 */     if (received.getErrorType() == 1004) {
/* 344 */       CrashFileLogger.log("Namnet hittades inte");
/* 345 */       return "Ditt namn hittades inte, försök igen.";
/*     */     } 
/*     */     try {
/* 348 */       CrashFileLogger.log("Annat oväntat fel: " + this.mapper.writeValueAsString(received));
/* 349 */     } catch (JsonProcessingException e) {
/* 350 */       CrashFileLogger.log("JSON-fel!");
/*     */     } 
/* 352 */     return "Ett oväntat fel, prova igen";
/*     */   }
/*     */   
/*     */   private String getMyIP() {
/*     */     try {
/* 357 */       return InetAddress.getLocalHost().getHostAddress();
/* 358 */     } catch (UnknownHostException e) {
/* 359 */       return "unknown";
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasSchoolIP() {
/* 364 */     String current = getMyIP();
/* 365 */     return current.startsWith(this.twoOctetsOfMyIP);
/*     */   }
/*     */ }


/* Location:              C:\Users\ingemar\Downloads\Offlinekontrollant_1_02\SuperOfflinekontrollant.jar!\theController\ElevSender.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */