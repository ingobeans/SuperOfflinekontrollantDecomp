/*    */ package dtos;
/*    */ 
/*    */ 
/*    */ public class StudentDTO
/*    */ {
/*    */   private String name;
/*    */   private int code;
/*    */   private int type;
/*    */   private long seconds;
/*    */   private String startSubmitTime;
/*    */   private String ipAddresses;
/*    */   private int statusCode;
/*    */   
/*    */   public StudentDTO() {}
/*    */   
/*    */   public StudentDTO(String name, int code, int type, long seconds, String startSubmitTime, String ipAddresses, int statusCode) {
/* 17 */     this.name = name;
/* 18 */     this.code = code;
/* 19 */     this.type = type;
/* 20 */     this.seconds = seconds;
/* 21 */     this.startSubmitTime = startSubmitTime;
/* 22 */     this.ipAddresses = ipAddresses;
/* 23 */     this.statusCode = statusCode;
/*    */   }
/*    */   
/*    */   public StudentDTO(String name, int type) {
/* 27 */     this.name = name;
/* 28 */     this.type = type;
/* 29 */     this.code = 0;
/* 30 */     this.seconds = 0L;
/* 31 */     this.startSubmitTime = "";
/* 32 */     this.ipAddresses = "";
/* 33 */     this.statusCode = 0;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 37 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 41 */     this.name = name;
/*    */   }
/*    */   
/*    */   public int getCode() {
/* 45 */     return this.code;
/*    */   }
/*    */   
/*    */   public void setCode(int code) {
/* 49 */     this.code = code;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 53 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(int type) {
/* 57 */     this.type = type;
/*    */   }
/*    */   
/*    */   public long getSeconds() {
/* 61 */     return this.seconds;
/*    */   }
/*    */   
/*    */   public void setSeconds(long seconds) {
/* 65 */     this.seconds = seconds;
/*    */   }
/*    */   
/*    */   public String getStartSubmitTime() {
/* 69 */     return this.startSubmitTime;
/*    */   }
/*    */   
/*    */   public void setStartSubmitTime(String startSubmitTime) {
/* 73 */     this.startSubmitTime = startSubmitTime;
/*    */   }
/*    */   
/*    */   public String getIpAddresses() {
/* 77 */     return this.ipAddresses;
/*    */   }
/*    */   
/*    */   public void setIpAddresses(String ipAddresses) {
/* 81 */     this.ipAddresses = ipAddresses;
/*    */   }
/*    */   
/*    */   public int getStatusCode() {
/* 85 */     return this.statusCode;
/*    */   }
/*    */   
/*    */   public void setStatusCode(int statusCode) {
/* 89 */     this.statusCode = statusCode;
/*    */   }
/*    */ }


/* Location:              C:\Users\ingemar\Downloads\Offlinekontrollant_1_02\SuperOfflinekontrollant.jar!\dtos\StudentDTO.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */