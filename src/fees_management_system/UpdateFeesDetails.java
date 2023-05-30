/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fees_management_system;

import java.awt.Color;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.SQLException;




/**
 *
 * @author Dell 11
 */
public final class UpdateFeesDetails extends  JFrame {

    /**
     * Creates new form AddFees
     */
    
    
    
    public UpdateFeesDetails() {
        initComponents();
        displayCashFirst();
        fillComboBox();
        
        int receiptNo = getReceiptNo();
        txt_receiptNo.setText(Integer.toString(receiptNo));
        
        
        setRecords();
        
    }

    public void displayCashFirst(){
        lbl_DDNo.setVisible(false);
        lbl_ChequeNo.setVisible(false);
        lbl_bankName.setVisible(false);
        
        txt_DDNo.setVisible(false);
        txt_ChequeNo.setVisible(false);
        txt_bankName.setVisible(false);
    }
    
    
    
    public boolean validation(){
        
       
        
        if(combo_PaymentMode.getSelectedItem().toString().equalsIgnoreCase("cheque")){
            if(txt_ChequeNo.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please enter cheque number");
                return false;
            }
            if(txt_bankName.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please enter bank name");
                return false;
            }
        }
        if(combo_PaymentMode.getSelectedItem().toString().equalsIgnoreCase("dd")){
            if(txt_DDNo.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please enter dd no.");
                return false;
            }
            if(txt_bankName.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please enter bank name");
                return false;
            }
        }
        if(combo_PaymentMode.getSelectedItem().toString().equalsIgnoreCase("card")){
            if(txt_bankName.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please enter bank name");
                return false;
            }
        }
        if(txt_ReceivedFrom.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please enter user name");
            return false;
        }
        if(dateChooser.getDate()==null){
            JOptionPane.showMessageDialog(this,"Please select a date");
            return false;
        }
        if(txt_amount.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please enter amount[In Number]");
            return false;
        }
        
        
        
        return true;
    }
    
    
    public void fillComboBox(){
        try{
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection con=DriverManager.getConnection("jdbc:derby://localhost:1527/fees_management","root","sweta@12R");
            PreparedStatement pst=con.prepareStatement("select cname from course");
            ResultSet rs=pst.executeQuery();
            while(rs.next()){
                comboCourse.addItem(rs.getString("cname"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
            
        }
    }
    
    public int getReceiptNo() {
        int receiptNo=0;
        try{
            Connection con=DBConnection.getConnection();
            PreparedStatement pst=con.prepareStatement("select max(reciept_no) from fees_Details");
            ResultSet rs=pst.executeQuery();
            
            if(rs.next()==true){
                receiptNo=rs.getInt(1);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return receiptNo+1;
    }
    
   

   
  



    
public String updateData(int recieptNo, String studentName, String rollNo, String paymentMode,
        String chequeNo, String bankName, String ddNo, String courseName, String gstin, float totalAmount,
        String date, float amount, float cgst, float sgst, String totalInWords, String remark, int year1, int year2) {

    String status = "";

try (Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/fees_management", "root", "sweta@12R")) {

    PreparedStatement pst = con.prepareStatement("UPDATE fees_details SET student_name=?, roll_no=?, payment_mode=?, cheque_no=?, bank_name=?, dd_no=?, course_name=?, gstin=?, total_amount=?, date=?, amount=?, cgst=?, sgst=?, total_in_words=?, remark=?, year1=?, year2=? WHERE reciept_no=?");

    pst.setString(1, studentName);
    pst.setString(2, rollNo);
    pst.setString(3, paymentMode);
    pst.setString(4, chequeNo);
    pst.setString(5, bankName);
    pst.setString(6, ddNo);
    pst.setString(7, courseName);
    pst.setString(8, gstin);
    pst.setFloat(9, totalAmount);
    pst.setString(10, date);
    pst.setFloat(11, amount);
    pst.setFloat(12, cgst);
    pst.setFloat(13, sgst);
    pst.setString(14, totalInWords);
    pst.setString(15, remark);
    pst.setInt(16, year1);
    pst.setInt(17, year2);
    pst.setInt(18, recieptNo);

    int rowCount = pst.executeUpdate();

    if (rowCount == 1) {
        status = "success";
    } else {
        status = "failed";
    }

} catch (SQLException e) {
    e.printStackTrace();
    status = "failed";
}
return status;

}


public void setRecords(){
    
    try {
        
       Connection con=DBConnection.getConnection();
       PreparedStatement pst=con.prepareStatement("select * from fees_details order by reciept_no desc fetch first 1 rows only");
       ResultSet rs=pst.executeQuery();
       rs.next();
        
       txt_receiptNo.setText(rs.getString("reciept_no"));
       combo_PaymentMode.setSelectedItem(rs.getString("payment_mode"));
       txt_ChequeNo.setText(rs.getString("cheque_no"));
       txt_DDNo.setText(rs.getString("dd_no"));
       txt_bankName.setText(rs.getString("bank_name"));
       txt_ReceivedFrom.setText((rs.getString("student_name")));
       dateChooser.setDate(rs.getDate("date"));
       txt_Year1.setText(rs.getString("year1"));
       txt_Year2.setText(rs.getString("year2"));
       txt_rollNo.setText(rs.getString("roll_no"));
       comboCourse.setSelectedItem(rs.getString("course_name"));
       txt_amount.setText(rs.getString("amount"));
       txt_cgst.setText(rs.getString("cgst"));
       txt_sgst.setText(rs.getString("sgst"));
       txt_total.setText(rs.getString("total_amount"));
       txt_total_word.setText(rs.getString("total_in_words"));
       txt_remark.setText(rs.getString("remark"));
        
        
        
        
    } 
    catch (Exception e) {
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
        panelParent = new javax.swing.JPanel();
        lbl_receiptno = new javax.swing.JLabel();
        lbl_PaymentMode = new javax.swing.JLabel();
        lbl_DDNo = new javax.swing.JLabel();
        lbl_ChequeNo = new javax.swing.JLabel();
        txt_GSTNo = new javax.swing.JLabel();
        txt_DDNo = new javax.swing.JTextField();
        panelChild = new javax.swing.JPanel();
        lbl_paymentYear = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_rollNo = new javax.swing.JTextField();
        lbl_ReceivedForm = new javax.swing.JLabel();
        txt_total_word = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        comboCourse = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txt_Year1 = new javax.swing.JTextField();
        txt_courseName = new javax.swing.JTextField();
        txt_sgst = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        txt_total = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        btn_print = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        txt_ReceivedFrom = new javax.swing.JTextField();
        txt_Year2 = new javax.swing.JTextField();
        lbl_ReceivedForm1 = new javax.swing.JLabel();
        lbl_ReceivedForm2 = new javax.swing.JLabel();
        txt_amount = new javax.swing.JTextField();
        txt_cgst = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_remark = new javax.swing.JTextArea();
        lbl_bankName = new javax.swing.JLabel();
        combo_PaymentMode = new javax.swing.JComboBox<>();
        txt_bankName = new javax.swing.JTextField();
        txt_ChequeNo = new javax.swing.JTextField();
        txt_receiptNo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        dateChooser = new com.toedter.calendar.JDateChooser();

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

        panelParent.setBackground(new java.awt.Color(153, 0, 153));
        panelParent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_receiptno.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_receiptno.setForeground(new java.awt.Color(255, 255, 255));
        lbl_receiptno.setText("Receipt no : IT-");
        panelParent.add(lbl_receiptno, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        lbl_PaymentMode.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_PaymentMode.setForeground(new java.awt.Color(255, 255, 255));
        lbl_PaymentMode.setText("Mode of Payment :");
        panelParent.add(lbl_PaymentMode, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        lbl_DDNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_DDNo.setForeground(new java.awt.Color(255, 255, 255));
        lbl_DDNo.setText("DD no :");
        panelParent.add(lbl_DDNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, -1));

        lbl_ChequeNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_ChequeNo.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ChequeNo.setText("Cheque no :");
        panelParent.add(lbl_ChequeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, -1));

        txt_GSTNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_GSTNo.setForeground(new java.awt.Color(255, 255, 255));
        txt_GSTNo.setText("22HVSJH55 ");
        panelParent.add(txt_GSTNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 90, -1, 30));

        txt_DDNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_DDNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DDNoActionPerformed(evt);
            }
        });
        panelParent.add(txt_DDNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 240, -1));

        panelChild.setBackground(new java.awt.Color(153, 0, 153));
        panelChild.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_paymentYear.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_paymentYear.setForeground(new java.awt.Color(255, 255, 255));
        lbl_paymentYear.setText("The following payment in the college office for the year");
        panelChild.add(lbl_paymentYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 440, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("To");
        panelChild.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, -1, 30));

        txt_rollNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_rollNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_rollNoActionPerformed(evt);
            }
        });
        panelChild.add(txt_rollNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 140, 80, -1));

        lbl_ReceivedForm.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_ReceivedForm.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ReceivedForm.setText("SGST 9%");
        panelChild.add(lbl_ReceivedForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 390, -1, -1));

        txt_total_word.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_total_word.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_total_wordActionPerformed(evt);
            }
        });
        panelChild.add(txt_total_word, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 540, 460, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Roll No :");
        panelChild.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 140, -1, -1));

        comboCourse.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        comboCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCourseActionPerformed(evt);
            }
        });
        panelChild.add(comboCourse, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, 240, 30));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Amount");
        panelChild.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 220, -1, -1));
        panelChild.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 460, 420, 40));
        panelChild.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 248, 1360, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Sr No");
        panelChild.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Sr No");
        panelChild.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Sr No");
        panelChild.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Sr No");
        panelChild.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Sr No");
        panelChild.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Head");
        panelChild.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 220, -1, -1));

        txt_Year1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_Year1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Year1ActionPerformed(evt);
            }
        });
        panelChild.add(txt_Year1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 80, -1));

        txt_courseName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_courseName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_courseNameActionPerformed(evt);
            }
        });
        panelChild.add(txt_courseName, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, 410, -1));

        txt_sgst.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_sgst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sgstActionPerformed(evt);
            }
        });
        panelChild.add(txt_sgst, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 400, 280, -1));
        panelChild.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 1360, 40));

        txt_total.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });
        panelChild.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 480, 280, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Course ");
        panelChild.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, -1, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Remark :");
        panelChild.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 610, -1, 30));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Receiver Signature");
        panelChild.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 630, -1, 30));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Total in words :");
        panelChild.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 540, -1, 30));
        panelChild.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 600, 420, 30));

        btn_print.setBackground(new java.awt.Color(0, 0, 0));
        btn_print.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btn_print.setForeground(new java.awt.Color(255, 255, 255));
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });
        panelChild.add(btn_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 680, -1, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Course ");
        panelChild.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 130, -1, -1));

        txt_ReceivedFrom.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_ReceivedFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ReceivedFromActionPerformed(evt);
            }
        });
        panelChild.add(txt_ReceivedFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 370, -1));

        txt_Year2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_Year2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Year2ActionPerformed(evt);
            }
        });
        panelChild.add(txt_Year2, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 80, 80, -1));

        lbl_ReceivedForm1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_ReceivedForm1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ReceivedForm1.setText("Received From :");
        panelChild.add(lbl_ReceivedForm1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, -1));

        lbl_ReceivedForm2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_ReceivedForm2.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ReceivedForm2.setText("CGST 9%");
        panelChild.add(lbl_ReceivedForm2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 340, -1, -1));

        txt_amount.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_amountActionPerformed(evt);
            }
        });
        panelChild.add(txt_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 290, 280, -1));

        txt_cgst.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_cgst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cgstActionPerformed(evt);
            }
        });
        panelChild.add(txt_cgst, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 350, 280, -1));

        txt_remark.setColumns(20);
        txt_remark.setRows(5);
        jScrollPane2.setViewportView(txt_remark);

        panelChild.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 610, 460, -1));

        panelParent.add(panelChild, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 1310, 800));

        lbl_bankName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbl_bankName.setForeground(new java.awt.Color(255, 255, 255));
        lbl_bankName.setText("Bank Name :");
        panelParent.add(lbl_bankName, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, -1, -1));

        combo_PaymentMode.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        combo_PaymentMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DD", "Cheque", "Cash", "Card" }));
        combo_PaymentMode.setSelectedIndex(2);
        combo_PaymentMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_PaymentModeActionPerformed(evt);
            }
        });
        panelParent.add(combo_PaymentMode, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 240, 30));

        txt_bankName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_bankName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bankNameActionPerformed(evt);
            }
        });
        panelParent.add(txt_bankName, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 240, -1));

        txt_ChequeNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        panelParent.add(txt_ChequeNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 240, -1));

        txt_receiptNo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txt_receiptNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_receiptNoActionPerformed(evt);
            }
        });
        panelParent.add(txt_receiptNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 240, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Date :");
        panelParent.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 30, -1, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("GSTIN :");
        panelParent.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 90, -1, 30));
        panelParent.add(dateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 32, 160, 30));

        getContentPane().add(panelParent, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 0, 1360, 1040));

        setSize(new java.awt.Dimension(1918, 1123));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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

    private void txt_rollNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_rollNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_rollNoActionPerformed

    private void txt_ReceivedFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ReceivedFromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ReceivedFromActionPerformed

    private void txt_total_wordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_total_wordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total_wordActionPerformed

    private void txt_Year1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Year1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_Year1ActionPerformed

    private void txt_courseNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_courseNameActionPerformed
        
    }//GEN-LAST:event_txt_courseNameActionPerformed

    private void txt_sgstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sgstActionPerformed
      
      
    }//GEN-LAST:event_txt_sgstActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void txt_bankNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bankNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bankNameActionPerformed

    private void txt_DDNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_DDNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_DDNoActionPerformed

    private void txt_receiptNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_receiptNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_receiptNoActionPerformed

    private void combo_PaymentModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_PaymentModeActionPerformed
        
        if(combo_PaymentMode.getSelectedIndex()==0){
            lbl_DDNo.setVisible(true);
            txt_DDNo.setVisible(true);
            
            lbl_ChequeNo.setVisible(false);
            txt_ChequeNo.setVisible(false);
            
            lbl_bankName.setVisible(true);
            txt_bankName.setVisible(true);
        }
        if(combo_PaymentMode.getSelectedIndex()==1){
            lbl_DDNo.setVisible(false);
            txt_DDNo.setVisible(false);
            
            lbl_ChequeNo.setVisible(true);
            txt_ChequeNo.setVisible(true);
            
            lbl_bankName.setVisible(true);
            txt_bankName.setVisible(true);
        }
        if(combo_PaymentMode.getSelectedIndex()==2){
            lbl_DDNo.setVisible(false);
            txt_DDNo.setVisible(false);
            
            lbl_ChequeNo.setVisible(false);
            txt_ChequeNo.setVisible(false);
            
            lbl_bankName.setVisible(false);
            txt_bankName.setVisible(false);
        }
        if(combo_PaymentMode.getSelectedItem().equals("Card")){
            lbl_DDNo.setVisible(false);
            txt_DDNo.setVisible(false);
            
            lbl_ChequeNo.setVisible(false);
            txt_ChequeNo.setVisible(false);
            
            lbl_bankName.setVisible(true);
            txt_bankName.setVisible(true);
        }
    }//GEN-LAST:event_combo_PaymentModeActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
       if (validation() == true) {
        int recieptNo = Integer.parseInt(txt_receiptNo.getText());
        String studentName = txt_ReceivedFrom.getText();
        String rollNo = txt_rollNo.getText();
        String paymentMode = combo_PaymentMode.getSelectedItem().toString();
        String chequeNo = txt_ChequeNo.getText();
        String bankName = txt_bankName.getText();
        String ddNo = txt_DDNo.getText();
        String courseName = txt_courseName.getText();
        String gstin = txt_GSTNo.getText();
        float totalAmount = Float.parseFloat(txt_total.getText());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(dateChooser.getDate());
        float initialAmount = Float.parseFloat(txt_amount.getText());
        float cgst = Float.parseFloat(txt_cgst.getText());
        float sgst = Float.parseFloat(txt_sgst.getText());
        String totalInWords = txt_total_word.getText();
        String remark = txt_remark.getText();
        int year1 = Integer.parseInt(txt_Year1.getText());
        int year2 = Integer.parseInt(txt_Year2.getText());
        
        String result = updateData(recieptNo, studentName, rollNo, paymentMode, chequeNo, bankName, ddNo, courseName, gstin, totalAmount, date, initialAmount, cgst, sgst, totalInWords, remark, year1, year2);

        if (result.equals("success")) {
            JOptionPane.showMessageDialog(this, "Record updated successfully");
            
            PrintReciept p=new PrintReciept();
            p.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update data");
        }
    }
    }//GEN-LAST:event_btn_printActionPerformed

    private void comboCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCourseActionPerformed
        txt_courseName.setText(comboCourse.getSelectedItem().toString());
    }//GEN-LAST:event_comboCourseActionPerformed

    private void txt_Year2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Year2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_Year2ActionPerformed

    private void txt_amountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_amountActionPerformed
        Float amnt=Float.parseFloat(txt_amount.getText());
        Float cgst=(float)(amnt*0.09);
        Float sgst=(float)(amnt*0.09);
        
        txt_cgst.setText(cgst.toString());
        txt_sgst.setText(sgst.toString());
        
        float total=amnt+cgst+sgst;
        
        txt_total.setText(Float.toString(total));
        
        txt_total_word.setText(NumberToWordsConverter.convert((int)total)+ " Only ");
    }//GEN-LAST:event_txt_amountActionPerformed

    private void txt_cgstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cgstActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cgstActionPerformed

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
           home Home=new home();
           Home.setVisible(true);
           this.dispose();
        
        
        
       
    }//GEN-LAST:event_btnHomeMouseClicked

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        
        Login lg=new Login();
        lg.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btnBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseClicked
        
        home Home =new home();
        Home.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackMouseClicked

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
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                    new UpdateFeesDetails().setVisible(true);
                
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
    private javax.swing.JButton btn_print;
    private javax.swing.JComboBox<String> comboCourse;
    private javax.swing.JComboBox<String> combo_PaymentMode;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl_ChequeNo;
    private javax.swing.JLabel lbl_DDNo;
    private javax.swing.JLabel lbl_PaymentMode;
    private javax.swing.JLabel lbl_ReceivedForm;
    private javax.swing.JLabel lbl_ReceivedForm1;
    private javax.swing.JLabel lbl_ReceivedForm2;
    private javax.swing.JLabel lbl_bankName;
    private javax.swing.JLabel lbl_paymentYear;
    private javax.swing.JLabel lbl_receiptno;
    private javax.swing.JPanel panelBack;
    private javax.swing.JPanel panelChild;
    private javax.swing.JPanel panelCourseList;
    private javax.swing.JPanel panelEdit;
    private javax.swing.JPanel panelHome;
    private javax.swing.JPanel panelLogout;
    private javax.swing.JPanel panelParent;
    private javax.swing.JPanel panelSearch;
    private javax.swing.JPanel panelViewAllRecord;
    private javax.swing.JPanel panelsideBar;
    private javax.swing.JTextField txt_ChequeNo;
    private javax.swing.JTextField txt_DDNo;
    private javax.swing.JLabel txt_GSTNo;
    private javax.swing.JTextField txt_ReceivedFrom;
    private javax.swing.JTextField txt_Year1;
    private javax.swing.JTextField txt_Year2;
    private javax.swing.JTextField txt_amount;
    private javax.swing.JTextField txt_bankName;
    private javax.swing.JTextField txt_cgst;
    private javax.swing.JTextField txt_courseName;
    private javax.swing.JTextField txt_receiptNo;
    private javax.swing.JTextArea txt_remark;
    private javax.swing.JTextField txt_rollNo;
    private javax.swing.JTextField txt_sgst;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_total_word;
    // End of variables declaration//GEN-END:variables

    private String insertData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
