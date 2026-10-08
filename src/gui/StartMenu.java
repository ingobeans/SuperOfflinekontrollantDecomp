/*     */ package gui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.FlowLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.time.Duration;
/*     */ import java.time.LocalTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.border.LineBorder;
/*     */ import starter.OfflineControlStarter;
/*     */ import theController.ElevSender;
/*     */ import utils.CrashFileLogger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StartMenu
/*     */   extends Thread
/*     */ {
/*  32 */   private final int psize = 10;
/*  33 */   private final Color activeColor = new Color(11987187);
/*  34 */   private final Color failColor = new Color(15972036);
/*  35 */   private final Color failColorDark = new Color(10625313);
/*  36 */   private final Color okColor = new Color(11923874);
/*  37 */   private final Color okColorDark = new Color(5154353);
/*  38 */   private final Color beige = new Color(16575431);
/*     */   private JLabel clock;
/*  40 */   private final Dimension textDimension = new Dimension(80, 35);
/*  41 */   private final Font font = new Font("Consolas", 1, 14);
/*  42 */   private final Dimension buttonSize = new Dimension(150, 25);
/*  43 */   private final Dimension textfieldSize = new Dimension(150, 25);
/*     */   private final ElevSender sender;
/*  45 */   private final JPanel[] panels = new JPanel[10];
/*  46 */   private final JButton[] buttons = new JButton[10];
/*  47 */   private final JTextField[] textFields = new JTextField[10];
/*  48 */   private final JLabel[] infoLabels = new JLabel[10];
/*  49 */   private final JLabel[] statusLabels = new JLabel[10];
/*  50 */   private final JLabel[] checkLabels = new JLabel[10];
/*     */   private volatile Duration duration;
/*     */   private String startSubmitTime;
/*     */   private JPanel clockPanel;
/*  54 */   private final int HEADER = 0;
/*  55 */   private final int CONFIRM = 1;
/*  56 */   private final int IP = 2;
/*  57 */   private final int NAME = 3;
/*  58 */   private final int WIFI_OFF = 4;
/*  59 */   private final int READY = 5;
/*  60 */   private final int SUBMISSION = 6;
/*  61 */   private final int FINISH = 7;
/*  62 */   private final int ABORT = 8;
/*  63 */   private final int CLOCK = 9;
/*     */ 
/*     */ 
/*     */   
/*     */   public StartMenu(ElevSender sender) {
/*  68 */     UIManager.put("AuditoryCues.enabled", Boolean.FALSE);
/*  69 */     this.sender = sender;
/*     */     
/*  71 */     for (int i = 0; i < this.panels.length; i++) {
/*  72 */       JPanel panel = new JPanel(new BorderLayout(8, 8));
/*  73 */       panel.setBorder(new LineBorder(Color.DARK_GRAY, 2));
/*  74 */       panel.setPreferredSize(new Dimension(1000, 40));
/*  75 */       panel.setBackground(this.beige);
/*  76 */       this.panels[i] = panel;
/*  77 */       JButton button = new JButton();
/*  78 */       button.setPreferredSize(this.buttonSize);
/*  79 */       button.setEnabled(false);
/*  80 */       button.setFont(this.font);
/*  81 */       this.buttons[i] = button;
/*  82 */       JTextField t = new JTextField();
/*  83 */       t.setPreferredSize(this.textfieldSize);
/*  84 */       t.setFont(this.font);
/*  85 */       t.setEnabled(false);
/*  86 */       this.textFields[i] = t;
/*     */       
/*  88 */       JLabel inf = new JLabel();
/*  89 */       inf.setFont(this.font);
/*  90 */       this.infoLabels[i] = inf;
/*  91 */       inf.setForeground(Color.LIGHT_GRAY);
/*     */       
/*  93 */       JLabel stat = new JLabel();
/*  94 */       stat.setFont(this.font);
/*  95 */       this.statusLabels[i] = stat;
/*  96 */       stat.setForeground(Color.RED);
/*     */       
/*  98 */       JLabel check = new JLabel("");
/*  99 */       this.checkLabels[i] = check;
/* 100 */       check.setPreferredSize(new Dimension(20, 20));
/*     */ 
/*     */       
/* 103 */       JPanel left = new JPanel(new FlowLayout(0, 10, 5));
/* 104 */       JPanel right = new JPanel(new FlowLayout(2, 10, 5));
/* 105 */       left.setOpaque(false);
/* 106 */       right.setOpaque(false);
/* 107 */       panel.add(left, "West");
/* 108 */       panel.add(right, "East");
/*     */       
/* 110 */       left.add(inf);
/* 111 */       left.add(t);
/* 112 */       left.add(stat);
/*     */       
/* 114 */       right.add(button);
/* 115 */       right.add(check);
/* 116 */       if (i == 9) this.clockPanel = right;
/*     */     
/*     */     } 
/*     */     
/* 120 */     JFrame frame = new JFrame();
/* 121 */     frame.setUndecorated(true);
/* 122 */     frame.setLayout(new FlowLayout());
/* 123 */     frame.getContentPane().setBackground(Color.BLUE);
/* 124 */     JPanel mainPanel = new JPanel();
/* 125 */     mainPanel.setBackground(Color.BLUE);
/* 126 */     frame.add(mainPanel);
/* 127 */     mainPanel.setLayout(new BoxLayout(mainPanel, 1));
/*     */     
/* 129 */     prepareHeader();
/* 130 */     prepareConfirm();
/* 131 */     prepareIP();
/* 132 */     prepareName();
/* 133 */     prepareWifiOff();
/* 134 */     prepareReady();
/* 135 */     prepareSubmission();
/* 136 */     prepareFinish();
/* 137 */     prepareAbort();
/* 138 */     prepareClock();
/* 139 */     for (int j = 0; j < 10; ) { mainPanel.add(this.panels[j]); j++; }
/* 140 */      frame.pack();
/* 141 */     frame.setDefaultCloseOperation(0);
/* 142 */     frame.setLocationRelativeTo((Component)null);
/* 143 */     activateNext(0);
/* 144 */     frame.setVisible(true);
/*     */   }
/*     */   
/*     */   private void prepareHeader() {
/* 148 */     JPanel pW = new JPanel(new FlowLayout());
/* 149 */     JPanel pC = new JPanel(new FlowLayout());
/* 150 */     JPanel pE = new JPanel(new FlowLayout(1, 10, 10));
/* 151 */     pW.setOpaque(false);
/* 152 */     pC.setOpaque(false);
/* 153 */     pE.setOpaque(false);
/* 154 */     JLabel labelW = new JLabel("");
/* 155 */     JLabel labelC = new JLabel("OFFLINEKONTROLL");
/* 156 */     JLabel labelE = new JLabel("Version: 1.02");
/* 157 */     labelC.setFont(new Font("Consolas", 1, 30));
/* 158 */     labelE.setFont(new Font("Consolas", 0, 10));
/* 159 */     labelC.setForeground(Color.WHITE);
/* 160 */     labelE.setForeground(Color.WHITE);
/* 161 */     labelW.setPreferredSize(labelE.getPreferredSize());
/* 162 */     pW.add(labelW);
/* 163 */     pC.add(labelC);
/* 164 */     pE.add(labelE);
/* 165 */     this.panels[0].removeAll();
/* 166 */     this.panels[0].setBackground(Color.BLACK);
/* 167 */     this.panels[0].add(pC, "Center");
/* 168 */     this.panels[0].add(pW, "West");
/* 169 */     this.panels[0].add(pE, "East");
/*     */   }
/*     */   
/*     */   private void prepareConfirm() {
/* 173 */     ((JPanel)this.panels[1].getComponent(0)).setLayout(new FlowLayout(0, 10, 10));
/* 174 */     this.textFields[1].setVisible(false);
/* 175 */     this.infoLabels[1].setText("Kolla att du vet var inlämning sker så att inlämning går snabbt.");
/* 176 */     this.buttons[1].setText("Ja jag vet!");
/* 177 */     this.buttons[1].addActionListener(e -> activateNext(1));
/*     */   }
/*     */   
/*     */   private void prepareIP() {
/* 181 */     this.infoLabels[2].setText("Ange IP-adress:");
/* 182 */     this.buttons[2].setText("Koppla");
/* 183 */     this.buttons[2].addActionListener(e -> {
/*     */           this.statusLabels[2].setText("");
/*     */           String ipInput = this.textFields[2].getText().trim();
/*     */           String response = this.sender.tryConnect(ipInput);
/*     */           if (response.equals("OK")) {
/*     */             activateNext(2);
/*     */           } else {
/*     */             this.statusLabels[2].setText(response);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   private void prepareName() {
/* 196 */     this.infoLabels[3].setText("Ange namn:");
/* 197 */     this.buttons[3].setText("Registrera");
/* 198 */     this.buttons[3].addActionListener(e -> {
/*     */           String response = this.sender.tryRegister(this.textFields[3].getText());
/*     */           if (response.equals("OK")) {
/*     */             activateNext(3);
/*     */             this.duration = Duration.ZERO;
/*     */             start();
/*     */             this.clock.setForeground(Color.GREEN);
/*     */             this.infoLabels[9].setForeground(Color.BLACK);
/*     */           } else {
/*     */             this.statusLabels[3].setText(response);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   private void prepareWifiOff() {
/* 213 */     ((JPanel)this.panels[4].getComponent(0)).setLayout(new FlowLayout(0, 10, 10));
/* 214 */     this.infoLabels[4].setText("Stäng av wifi");
/* 215 */     this.textFields[4].setVisible(false);
/* 216 */     this.buttons[4].setText("Fortsätt");
/* 217 */     this.buttons[4].addActionListener(e -> {
/*     */           if (this.sender.hasInternetWithPingTest()) {
/*     */             this.statusLabels[4].setForeground(Color.RED);
/*     */             this.statusLabels[4].setText("Som sagt, stäng av internetåtkomsten!");
/*     */           } else {
/*     */             this.statusLabels[4].setForeground(Color.BLACK);
/*     */             this.statusLabels[4].setText("Skriv provet, skriv sedan \"JA\"");
/*     */             activateNext(4);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   private void prepareReady() {
/* 230 */     this.infoLabels[5].setText("SKRIV PROVET NU! Skriv sedan KLAR.");
/* 231 */     this.buttons[5].setText("Fortsätt");
/* 232 */     this.buttons[5].addActionListener(e -> {
/*     */           if (this.textFields[5].getText().trim().equals("KLAR")) {
/*     */             String question = "Är du säker på att du är färdig med alla lösningar och är redo att lämna in och avsluta?";
/*     */             int choice = JOptionPane.showConfirmDialog(null, question, "Bekräfta", 0);
/*     */             if (choice != 0)
/*     */               return; 
/*     */             this.startSubmitTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
/*     */             this.sender.setSubmitting();
/*     */             activateNext(5);
/*     */           } else {
/*     */             this.statusLabels[5].setText("<- FEL");
/*     */           } 
/*     */         });
/*     */   }
/*     */   private void prepareSubmission() {
/* 247 */     this.infoLabels[6].setText("Sätt på wifi, ladda upp snabbt, skriv INSKICKAT.");
/* 248 */     this.buttons[6].setText("Bekräfta");
/* 249 */     this.buttons[6].addActionListener(e -> {
/*     */           if (!this.sender.hasSchoolIP()) {
/*     */             JOptionPane.showMessageDialog(null, "Du måste in på skolans wifi!!");
/*     */             return;
/*     */           } 
/*     */           if (this.textFields[6].getText().trim().equals("INSKICKAT")) {
/*     */             String response = this.sender.handleFinishData(this.duration.toSeconds(), this.startSubmitTime);
/*     */             if (!response.equals("Ej godkänd offlinekontroll") && !response.equals("OK")) {
/*     */               this.statusLabels[6].setForeground(Color.RED);
/*     */               this.statusLabels[6].setText(response);
/*     */             } else {
/*     */               Color finalColor = response.equals("OK") ? this.okColorDark : this.failColorDark;
/*     */               this.statusLabels[7].setForeground(finalColor);
/*     */               this.statusLabels[7].setBackground(Color.WHITE);
/*     */               this.statusLabels[7].setOpaque(true);
/*     */               this.statusLabels[7].setText(response);
/*     */               activateNext(6);
/*     */             } 
/*     */           } else {
/*     */             this.statusLabels[6].setForeground(Color.RED);
/*     */             this.statusLabels[6].setText("<- FEL");
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void prepareFinish() {
/* 277 */     ((JPanel)this.panels[7].getComponent(0)).setLayout(new FlowLayout(0, 10, 10));
/* 278 */     this.infoLabels[7].setText("Offlinekontrollresultat:");
/* 279 */     this.buttons[7].setText("Stäng");
/* 280 */     this.textFields[7].setVisible(false);
/* 281 */     this.buttons[7].addActionListener(e -> {
/*     */           CrashFileLogger.closeFile("Normalt avslut. Programmet stängs");
/*     */           OfflineControlStarter.closeLock();
/*     */         });
/*     */   }
/*     */   
/*     */   private void prepareAbort() {
/* 288 */     this.textFields[8].setVisible(false);
/* 289 */     this.buttons[8].setText("AVBRYT PROVET!");
/* 290 */     this.buttons[8].setBackground(Color.RED);
/* 291 */     this.buttons[8].setForeground(Color.WHITE);
/* 292 */     this.buttons[8].setEnabled(true);
/* 293 */     this.buttons[8].addActionListener(e -> {
/*     */           String choice = JOptionPane.showInputDialog(null, "Är du säker?\nSka du verkligen avbryta provet\noch inte lämna in något??\nSkriv JA isåfall.", "Nej");
/*     */           if (choice != null && choice.equalsIgnoreCase("JA")) {
/*     */             CrashFileLogger.closeFile("Programmet avbröts av eleven");
/*     */             OfflineControlStarter.closeLock();
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void prepareClock() {
/* 307 */     ((JPanel)this.panels[9].getComponent(0)).setLayout(new FlowLayout(0, 10, 10));
/* 308 */     this.infoLabels[9].setText("Skrivtid");
/* 309 */     this.textFields[9].setVisible(false);
/* 310 */     JPanel panel = new JPanel(new FlowLayout(1, 0, -1));
/* 311 */     panel.setPreferredSize(this.buttonSize);
/* 312 */     panel.setBackground(Color.BLACK);
/*     */     
/* 314 */     this.clock = new JLabel("0:00:00");
/* 315 */     this.clock.setFont(new Font("OCR A Extended", 0, 25));
/* 316 */     this.clock.setForeground(Color.WHITE);
/* 317 */     panel.add(this.clock);
/* 318 */     this.clockPanel.remove(0);
/* 319 */     this.clockPanel.add(panel, 0);
/* 320 */     this.panels[9].setBackground(this.beige);
/*     */   }
/*     */   
/*     */   private void activateNext(int index) {
/* 324 */     if (index > 0) {
/* 325 */       this.textFields[index].setEnabled(false);
/* 326 */       this.buttons[index].setEnabled(false);
/* 327 */       this.statusLabels[index].setText("");
/* 328 */       this.checkLabels[index].setText("✔");
/* 329 */       this.infoLabels[index].setForeground(Color.LIGHT_GRAY);
/* 330 */       this.panels[index].setBackground(this.okColor);
/*     */     } 
/* 332 */     this.textFields[index + 1].setEnabled(true);
/* 333 */     this.buttons[index + 1].setEnabled(true);
/* 334 */     this.panels[index + 1].setBackground(this.activeColor);
/* 335 */     this.infoLabels[index + 1].setForeground(Color.BLACK);
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/*     */       while (true) {
/* 342 */         Thread.sleep(10000L);
/* 343 */         this.duration = this.duration.plusSeconds(10L);
/* 344 */         int mins = this.duration.toMinutesPart();
/* 345 */         int secs = this.duration.toSecondsPart();
/* 346 */         long hrs = this.duration.toHours();
/* 347 */         this.clock.setText("" + hrs + hrs + ((mins < 10) ? ":0" : ":") + mins + ((secs < 10) ? ":0" : ":"));
/*     */       } 
/* 349 */     } catch (InterruptedException e) {
/* 350 */       System.out.println("Klockan avbröts");
/*     */       return;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ingemar\Downloads\Offlinekontrollant_1_02\SuperOfflinekontrollant.jar!\gui\StartMenu.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */