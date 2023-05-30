/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fees_management_system;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Dell 11
 */
public class GenerateReport extends javax.swing.JFrame {

    /**
     * Creates new form GenerateReport
     */
    DefaultTableModel model;
    public GenerateReport() {
        initComponents();
        fillComboBox();
        
    }
    
    public void fillComboBox(){
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/fees_management","root","sweta@12R");
            PreparedStatement pst=con.prepareStatement("select cname from course");
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                combo_courseDetails.addItem(rs.getString("cname"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
            
        }
    }
    
    public void setRecordsToTable(){
        
        String cname=combo_courseDetails.getSelectedItem().toString();
        
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String fromDate= dateFormat.format(dateChooser_from.getDate());
        String toDate=dateFormat.format(dateChooser_to.getDate());
        
        Float amountTotal=0.0f;
        
        try {
            Connection con=DBConnection.getConnection();
            PreparedStatement pst=con.prepareStatement("SELECT * FROM fees_details WHERE CAST(date AS VARCHAR(255)) BETWEEN ? AND ? AND course_name=?");
            pst.setString(1,fromDate);
            pst.setString(2,toDate);
            pst.setString(3,cname);
            ResultSet rs=pst.executeQuery();
            
            while(rs.next()){
                String recieptNo=rs.getString("reciept_no");
                String rollNo=rs.getString("roll_no");
                String studentName=rs.getString("student_name");
                String courseName=rs.getString("course_name");
                float amount=rs.getFloat("total_amount");
                String remark=rs.getString("remark");
                
                amountTotal=amountTotal+amount;
               
                
                Object[] obj = {recieptNo,rollNo,studentName,courseName,amount,remark};
                
                model = (DefaultTableModel)tbl_feesDetails.getModel();
                model.addRow(obj);
                
            }
            lbl_course.setText(cname);
            lbl_totalAmount.setText(amountTotal.toString());
            lbl_totalInWords.setText(NumberToWordsConverter.convert(amountTotal.intValue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void clearTable(){
        DefaultTableModel model = (DefaultTableModel)tbl_feesDetails.getModel();
        model.setRowCount(1);
    }
    
  public void exportToExcel(){
      XSSFWorkbook wb = new XSSFWorkbook();
      XSSFSheet ws = wb.createSheet();
      DefaultTableModel model=(DefaultTableModel)tbl_feesDetails.getModel();
      
      TreeMap<String,Object[]> map = new TreeMap<>();
      
      map.put("0", new Object[]{model.getColumnName(0),model.getColumnName(1),model.getColumnName(2),model.getColumnName(3),model.getColumnName(4),model.getColumnName(5)});
      
      for (int i = 1; i < model.getRowCount(); i++) {
          
          map.put(Integer.toString(i), new Object[]{model.getValueAt(i,0),model.getValueAt(i,1),model.getValueAt(i,2),model.getValueAt(i,3),model.getValueAt(i,4),model.getValueAt(i,5),});
          
      }
      
      Set<String> id = map.keySet();
      
      XSSFRow fRow;
      
      int rowId=0;
      
      for(String key : id){
          
          fRow =  ws.createRow(rowId++);
          
          Object[] value = map.get(key);
          
          int cellId = 0;
          
          for (Object object : value) {
              
              XSSFCell cell = fRow.createCell(cellId++);
              cell.setCellValue(object.toString());
          }
      }
      try {
           FileOutputStream fos = new FileOutputStream(new File(txt_filePath.getText()));
           wb.write(fos);
           fos.close();
           JOptionPane.showMessageDialog(this,"File exported successfully : " +txt_filePath.getText());
      } catch (Exception e) {
          e.printStackTrace();
      }
      
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        panelsideBar = new javax.swing.JPanel();
        panelHome = new javax.swing.JPanel();
        btnHome = new javax.swing.JLabel();
        panelSearch = new javax.swing.JPanel();
        btnSearch = new javax.swing.JLabel();
        panelEdit = new javax.swing.JPanel();
        btnEdit = new javax.swing.JLabel();
        panelCourseList = new javax.swing.JPanel();
        btnCourseList = new javax.swing.JLabel();
        panelViewAllRecord = new javax.swing.JPanel();
        btnViewAllRecord = new javax.swing.JLabel();
        panelBack = new javax.swing.JPanel();
        btnBack = new javax.swing.JLabel();
        panelLogout = new javax.swing.JPanel();
        btnLogout = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        combo_courseDetails = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        dateChooser_from = new com.toedter.calendar.JDateChooser();
        dateChooser_to = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txt_filePath = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_feesDetails = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        lbl_totalInWords = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbl_course = new javax.swing.JLabel();
        lbl_totalAmount = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelsideBar.setBackground(new java.awt.Color(102, 0, 102));
        panelsideBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelHome.setBackground(new java.awt.Color(102, 0, 102));
        panelHome.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelHome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnHome.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        btnHome.setForeground(new java.awt.Color(255, 255, 255));
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/home.png"))); // NOI18N
        btnHome.setText("   Home");
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHomeMouseExited(evt);
            }
        });
        panelHome.add(btnHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 0, 280, -1));

        panelsideBar.add(panelHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 330, 70));

        panelSearch.setBackground(new java.awt.Color(102, 0, 102));
        panelSearch.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelSearch.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSearch.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/search2.png"))); // NOI18N
        btnSearch.setText("Search Record");
        btnSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSearchMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSearchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSearchMouseExited(evt);
            }
        });
        panelSearch.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 0, 280, -1));

        panelsideBar.add(panelSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 330, 70));

        panelEdit.setBackground(new java.awt.Color(102, 0, 102));
        panelEdit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelEdit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEdit.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/edit2.png"))); // NOI18N
        btnEdit.setText("Edit Courses");
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEditMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEditMouseExited(evt);
            }
        });
        panelEdit.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 0, 280, -1));

        panelsideBar.add(panelEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 330, 330, 70));

        panelCourseList.setBackground(new java.awt.Color(102, 0, 102));
        panelCourseList.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelCourseList.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCourseList.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        btnCourseList.setForeground(new java.awt.Color(255, 255, 255));
        btnCourseList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/list_1.png"))); // NOI18N
        btnCourseList.setText("Course List");
        btnCourseList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCourseListMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCourseListMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCourseListMouseExited(evt);
            }
        });
        panelCourseList.add(btnCourseList, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 0, 280, -1));

        panelsideBar.add(panelCourseList, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 460, 330, 70));

        panelViewAllRecord.setBackground(new java.awt.Color(102, 0, 102));
        panelViewAllRecord.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelViewAllRecord.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnViewAllRecord.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        btnViewAllRecord.setForeground(new java.awt.Color(255, 255, 255));
        btnViewAllRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/view all record.png"))); // NOI18N
        btnViewAllRecord.setText("View All Record");
        btnViewAllRecord.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnViewAllRecordMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnViewAllRecordMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnViewAllRecordMouseExited(evt);
            }
        });
        panelViewAllRecord.add(btnViewAllRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 0, 280, -1));

        panelsideBar.add(panelViewAllRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 600, 330, 70));

        panelBack.setBackground(new java.awt.Color(102, 0, 102));
        panelBack.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelBack.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBack.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/left-arrow.png"))); // NOI18N
        btnBack.setText("Back");
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBackMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBackMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBackMouseExited(evt);
            }
        });
        panelBack.add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 0, 280, -1));

        panelsideBar.add(panelBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 740, 330, 70));

        panelLogout.setBackground(new java.awt.Color(102, 0, 102));
        panelLogout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelLogout.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnLogout.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        btnLogout.setForeground(new java.awt.Color(255, 255, 255));
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fees_management_system/images/logout.png"))); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogoutMouseExited(evt);
            }
        });
        panelLogout.add(btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 0, 280, -1));

        panelsideBar.add(panelLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 880, 330, 70));

        getContentPane().add(panelsideBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 1040));

        jPanel1.setBackground(new java.awt.Color(153, 0, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        combo_courseDetails.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_courseDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_courseDetailsActionPerformed(evt);
            }
        });
        jPanel1.add(combo_courseDetails, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 510, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Select Course :");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Select Date :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 120, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("From Date :");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, -1, -1));
        jPanel1.add(dateChooser_from, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, 140, -1));
        jPanel1.add(dateChooser_to, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 120, 150, -1));

        jButton1.setBackground(new java.awt.Color(102, 0, 102));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 190, -1, -1));

        jButton2.setBackground(new java.awt.Color(102, 0, 102));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Export To Excel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 260, -1, -1));

        txt_filePath.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jPanel1.add(txt_filePath, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 262, 400, 30));

        jButton3.setBackground(new java.awt.Color(102, 0, 102));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Submit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, -1, -1));

        jButton4.setBackground(new java.awt.Color(102, 0, 102));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Browse");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 260, -1, -1));

        tbl_feesDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Receipt No", "Roll No", "Student Name", "Course", "Amount", "Remark"
            }
        ));
        jScrollPane1.setViewportView(tbl_feesDetails);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 340, 1270, 610));

        jPanel2.setBackground(new java.awt.Color(102, 0, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_totalInWords.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_totalInWords.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lbl_totalInWords, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 460, 50));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Course Selected :");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Total Amount Collected :");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Total Amount In Word :");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        lbl_course.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_course.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lbl_course, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 260, 30));

        lbl_totalAmount.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_totalAmount.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lbl_totalAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 100, 250, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 20, 490, 300));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("To Date :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 120, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 0, 1360, 1040));

        setSize(new java.awt.Dimension(1918, 1117));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        home Home=new home();
        Home.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_btnHomeMouseClicked

    private void btnHomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseEntered
        Color clr=new Color(153,0,153);
        panelHome.setBackground(clr);
    }//GEN-LAST:event_btnHomeMouseEntered

    private void btnHomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseExited
        Color clr=new Color(102,0,102);
        panelHome.setBackground(clr);
    }//GEN-LAST:event_btnHomeMouseExited

    private void btnSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseEntered
        Color clr=new Color(153,0,153);
        panelSearch.setBackground(clr);
    }//GEN-LAST:event_btnSearchMouseEntered

    private void btnSearchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseExited
        Color clr=new Color(102,0,102);
        panelSearch.setBackground(clr);
    }//GEN-LAST:event_btnSearchMouseExited

    private void btnEditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseEntered
        Color clr=new Color(153,0,153);
        panelEdit.setBackground(clr);
    }//GEN-LAST:event_btnEditMouseEntered

    private void btnEditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseExited
        Color clr=new Color(102,0,102);
        panelEdit.setBackground(clr);
    }//GEN-LAST:event_btnEditMouseExited

    private void btnCourseListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCourseListMouseEntered
        Color clr=new Color(153,0,153);
        panelCourseList.setBackground(clr);
    }//GEN-LAST:event_btnCourseListMouseEntered

    private void btnCourseListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCourseListMouseExited
        Color clr=new Color(102,0,102);
        panelCourseList.setBackground(clr);
    }//GEN-LAST:event_btnCourseListMouseExited

    private void btnViewAllRecordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewAllRecordMouseEntered
        Color clr=new Color(153,0,153);
        panelViewAllRecord.setBackground(clr);
    }//GEN-LAST:event_btnViewAllRecordMouseEntered

    private void btnViewAllRecordMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewAllRecordMouseExited
        Color clr=new Color(102,0,102);
        panelViewAllRecord.setBackground(clr);
    }//GEN-LAST:event_btnViewAllRecordMouseExited

    private void btnBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseEntered
        Color clr=new Color(153,0,153);
        panelBack.setBackground(clr);
    }//GEN-LAST:event_btnBackMouseEntered

    private void btnBackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseExited
        Color clr=new Color(102,0,102);
        panelBack.setBackground(clr);
    }//GEN-LAST:event_btnBackMouseExited

    private void btnLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseEntered
        Color clr=new Color(153,0,153);
        panelLogout.setBackground(clr);
    }//GEN-LAST:event_btnLogoutMouseEntered

    private void btnLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseExited
        Color clr=new Color(102,0,102);
        panelLogout.setBackground(clr);
    }//GEN-LAST:event_btnLogoutMouseExited

    private void combo_courseDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_courseDetailsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_courseDetailsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         SimpleDateFormat Date_Format = new SimpleDateFormat("YYYY-MM-dd"); 
        String datefrom=  Date_Format.format(dateChooser_from.getDate());
      String dateto=  Date_Format.format(dateChooser_to.getDate());
       
        MessageFormat header=new MessageFormat("Report From "+datefrom+" To " +dateto);
        MessageFormat footer=new MessageFormat("page{0,number,integer}");
        try {
            tbl_feesDetails.print(JTable.PrintMode.FIT_WIDTH, header, footer);
            
        } catch (Exception e) {
            e.getMessage();
        } 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        exportToExcel();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       
        clearTable();
        setRecordsToTable();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.showOpenDialog(this);
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String date=dateFormat.format(new Date());
        
        try {
            File f=fileChooser.getSelectedFile();
            String path=f.getAbsolutePath();
            
            path=path+ "_"+date+".xlsx";
            txt_filePath.setText(path);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
       
        Login lg=new Login();
        lg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btnBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseClicked
        
        home Home = new home();
        Home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackMouseClicked

    private void btnSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSearchMouseClicked
        
        SearchRecord search=new SearchRecord();
        search.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnSearchMouseClicked

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        
        EditCourse edit= new EditCourse();
        edit.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnEditMouseClicked

    private void btnCourseListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCourseListMouseClicked
        
        
    }//GEN-LAST:event_btnCourseListMouseClicked

    private void btnViewAllRecordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewAllRecordMouseClicked
        
        ViewAllRecords view=new ViewAllRecords();
        view.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnViewAllRecordMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GenerateReport().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnBack;
    private javax.swing.JLabel btnCourseList;
    private javax.swing.JLabel btnEdit;
    private javax.swing.JLabel btnHome;
    private javax.swing.JLabel btnLogout;
    private javax.swing.JLabel btnSearch;
    private javax.swing.JLabel btnViewAllRecord;
    private javax.swing.JComboBox<String> combo_courseDetails;
    private com.toedter.calendar.JDateChooser dateChooser_from;
    private com.toedter.calendar.JDateChooser dateChooser_to;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_course;
    private javax.swing.JLabel lbl_totalAmount;
    private javax.swing.JLabel lbl_totalInWords;
    private javax.swing.JPanel panelBack;
    private javax.swing.JPanel panelCourseList;
    private javax.swing.JPanel panelEdit;
    private javax.swing.JPanel panelHome;
    private javax.swing.JPanel panelLogout;
    private javax.swing.JPanel panelSearch;
    private javax.swing.JPanel panelViewAllRecord;
    private javax.swing.JPanel panelsideBar;
    private javax.swing.JTable tbl_feesDetails;
    private javax.swing.JTextField txt_filePath;
    // End of variables declaration//GEN-END:variables
}
