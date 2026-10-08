/*    */ package dtos;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TeacherDTO
/*    */ {
/*    */   private int messageType;
/*    */   private boolean responseOK;
/*    */   private int errorType;
/*    */   
/*    */   public TeacherDTO() {}
/*    */   
/*    */   public TeacherDTO(int messageType, boolean responseOK, int errorType) {
/* 14 */     this.messageType = messageType;
/* 15 */     this.responseOK = responseOK;
/* 16 */     this.errorType = errorType;
/*    */   }
/*    */   
/*    */   public int getMessageType() {
/* 20 */     return this.messageType;
/*    */   }
/*    */   
/*    */   public void setMessageType(int type) {
/* 24 */     this.messageType = type;
/*    */   }
/*    */   
/*    */   public boolean isResponseOK() {
/* 28 */     return this.responseOK;
/*    */   }
/*    */   
/*    */   public void setResponseOK(boolean responseOK) {
/* 32 */     this.responseOK = responseOK;
/*    */   }
/*    */   
/*    */   public int getErrorType() {
/* 36 */     return this.errorType;
/*    */   }
/*    */   
/*    */   public void setErrorType(int errorType) {
/* 40 */     this.errorType = errorType;
/*    */   }
/*    */ }


/* Location:              C:\Users\ingemar\Downloads\Offlinekontrollant_1_02\SuperOfflinekontrollant.jar!\dtos\TeacherDTO.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */