
package inventory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class Delivery extends javax.swing.JFrame {

 
   
    public Delivery() {
        initComponents();
        findVendor();
        showDelivery();
    }

   
    private void findVendor(){
        try{
    dbConnection db = new dbConnection();     
    Connection con=db.getConnection(); 
    String sql="Select * from sessions";
    PreparedStatement pst = con.prepareStatement(sql);
    ResultSet rs=pst.executeQuery();
    while (rs.next()){
     comboSession.addItem(rs.getString("ses_Name"));
     }con.close();
    }
    catch (Exception e){
    JOptionPane.showMessageDialog(null,e);
        }
  }
    
    
    
     private void findProducts(){
    
     String pcode =txtPCode.getText();

        try{
          
	dbConnection db = new dbConnection();
        Connection con=db.getConnection();
          
                   
          PreparedStatement   pst= con.prepareStatement("Select * From products where pro_Code =?");
            pst.setString(1, pcode);
            ResultSet rs= pst.executeQuery();
             
            if(rs.next() == false)
            {
            JOptionPane.showMessageDialog(this, "Product Code Not Fouund");
            }
            else{
            String pname = rs.getString("pro_Name");
            String catid = rs.getString("cat_ID");
            String subcat = rs.getString("sub_ID");
            
            

            txtPName.setText(pname.trim());
            txtCat.setText(catid.trim());
            txtSub.setText(subcat.trim());
           
            }
        }
             catch (SQLException ex) {
                Logger.getLogger(Delivery.class.getName()).log(Level.SEVERE, null, ex);
            } 
    }
    
   
     
     
     
     
    private void addDelivery(){
    String pQty = txtQty.getText();
    
      if (txtPCode.getText().equals("") || txtQty.getText().equals("") || txtDate.getText().equals("")|| txtBill.getText().equals("")){
             JOptionPane.showMessageDialog(null,"Please Enter All Fields");
         }else{
       try{
           
            dbConnection db = new dbConnection();     
            Connection con=db.getConnection();   
            
            
            
   
         String sql = "insert into delivery (dv_PCode, dv_PName, dv_Qty, dv_Cat, dv_Sub, dv_Session, dv_Note,  dv_Ref, dv_Date) values(?,?,?,?,?,?,?,?,?)";
         PreparedStatement pst = con.prepareStatement(sql);
         
          pst.setString(1, txtPCode.getText());
          pst.setString(2,txtPName.getText());
                    
          pst.setString(3, txtQty.getText());
          pst.setString(5, txtCat.getText());
          pst.setString(4, txtSub.getText());
                String  vendors=comboSession.getSelectedItem().toString();
         pst.setString(6,vendors);
         pst.setString(7, txtDesc.getText());
         
         pst.setString(8, txtBill.getText()); 
         pst.setString(9, txtDate.getText());
         
         
         PreparedStatement   pst1= con.prepareStatement("Select * From stock where stk_PCode ='"+txtPCode.getText()+"'");         
            ResultSet rs1= pst1.executeQuery();
            while (rs1.next()){
                String existingQuantity = rs1.getString("stk_Quantity");
                int existqty = Integer.parseInt(existingQuantity); // String to Integer conversion

                String inputqty = pQty;
              
          
            int inputquantity = Integer.parseInt(inputqty); // String to Integer conversion
               if (inputquantity > existqty){
                    JOptionPane.showMessageDialog(null,"Quantity exceed the current stock");
                    txtQty.setText("");
                } else{
                int totaldelivery = existqty - inputquantity;
                String finalquantity = Integer.toString(totaldelivery); //Integer to Istring conversion    
                String totalout = "UPDATE stock SET stk_Quantity="+finalquantity+" where stk_PCode='"+txtPCode.getText()+"'";
                    PreparedStatement pst2 = con.prepareStatement(totalout);
                    pst2.executeUpdate();
                    
             
         
         //Update totaloutTable when a purchase is done
         
            String purQuantity = "Select dpCode, dpQty from totalout where dpCode='"+txtPCode.getText()+"'";
            PreparedStatement pstqty = con.prepareStatement(purQuantity);
            ResultSet rsqty = pstqty.executeQuery();
            
            while (rsqty.next()){
            String existingQuantity2 = rsqty.getString("dpQty");
            int existqty2 = Integer.parseInt(existingQuantity2); // String to Integer conversion
            
            String inputqty2 = txtQty.getText();
            int inputquantity2 = Integer.parseInt(inputqty2); // String to Integer conversion
                    int totalpurchase = existqty2 + inputquantity2;
                    String pqty = Integer.toString(totalpurchase); //Integer to Istring conversion
            
            String totalin = "UPDATE totalout SET dpQty="+pqty+" where dpCode='"+txtPCode.getText()+"'";
           
            PreparedStatement pst3 = con.prepareStatement(totalin);
             pst3.executeUpdate();
            
            }
            
        
            pst.executeUpdate(); 
            JOptionPane.showMessageDialog(null,"New Item Inserted Successfully!");
         txtPCode.setText("");
         txtPName.setText("");    
         txtCat.setText("");
         txtSub.setText("");
         txtQty.setText("");
         txtPCode.setText("");
         txtDesc.setText("");
         txtDate.setText("");
         txtBill.setText("");
               }
            } 

              
         showDelivery();      
         
       }
        catch(Exception e)
            {
            JOptionPane.showMessageDialog(null,e);
            }
      }
  }
  
    
    private void  showDelivery(){
      
      
      try{
            dbConnection db = new dbConnection(); 
            Connection con=db.getConnection(); 
            String sql = "select * from delivery";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel tm = (DefaultTableModel)showPurchase.getModel();
            tm.setRowCount(0);
            while(rs.next()){
            Object o[]={rs.getString("dv_ID"), rs.getString("dv_PCode"), rs.getString("dv_PName"),rs.getString("dv_Qty"), 
            rs.getString("dv_Sub"),rs.getString("dv_Cat"), rs.getString("dv_Note"), rs.getString("dv_Session"), rs.getString("dv_Ref"), 
             rs.getString("dv_Date")};
            tm.addRow(o);
            
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);          
            }     
  }
  
    /**
      // function to move Coursor
        txtQty.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                String value=txtQty.getText();
                if(value.length() > 1){
                    txtPCost.requestFocus();
                }
            }
        });

    */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        showPurchase = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        txtPCode = new javax.swing.JTextField();
        comboSession = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPName = new javax.swing.JTextField();
        txtCat = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDesc = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        txtBill = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtSub = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        lblError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Product Delivery");

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 0, 0));
        jButton4.setText("Close");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(394, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(281, 281, 281)
                .addComponent(jButton4)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jButton4))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(0, 153, 102));
        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        showPurchase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product Code", "Name", "Quantity", "Category", "SubCategory", "Discription", "Supply Area", "Bill No", "Date"
            }
        ));
        jScrollPane2.setViewportView(showPurchase);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addGap(29, 29, 29))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        jPanel8.setBackground(new java.awt.Color(0, 153, 102));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtPCode.setBackground(new java.awt.Color(0, 51, 51));
        txtPCode.setForeground(new java.awt.Color(255, 255, 0));
        txtPCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPCodeKeyPressed(evt);
            }
        });

        jLabel3.setText("Enter Product Code:");

        jLabel6.setText("Select SupplyArea:");

        txtQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQtyKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtQtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtQtyKeyTyped(evt);
            }
        });

        jLabel7.setText("Quantity:");

        txtPName.setBackground(new java.awt.Color(153, 153, 153));
        txtPName.setForeground(new java.awt.Color(255, 255, 0));

        txtCat.setBackground(new java.awt.Color(153, 153, 153));
        txtCat.setForeground(new java.awt.Color(255, 255, 0));

        jLabel8.setText("Product Name:");

        jLabel9.setText("Product Category:");

        txtDesc.setColumns(20);
        txtDesc.setRows(5);
        txtDesc.setBorder(javax.swing.BorderFactory.createTitledBorder("Note/Description"));
        jScrollPane1.setViewportView(txtDesc);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 153, 102));
        jButton1.setText("Delivery Confirm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel12.setText("Date:");

        jLabel23.setText("Bill No:");

        txtSub.setBackground(new java.awt.Color(153, 153, 153));
        txtSub.setForeground(new java.awt.Color(204, 204, 0));

        jLabel24.setText("Sub-Category:");

        lblError.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblError.setForeground(new java.awt.Color(255, 51, 0));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSub, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(comboSession, 0, 170, Short.MAX_VALUE)
                    .addComponent(txtPCode, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(txtPName, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(txtCat, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jLabel7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtBill, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(txtQty, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(txtDate, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(lblError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboSession, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSub, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel24)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblError)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtBill, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel23))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPCodeKeyPressed
        if (evt.getKeyCode() == com.sun.glass.events.KeyEvent.VK_ENTER){
            findProducts();
        }
      
    }//GEN-LAST:event_txtPCodeKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //checkStock();
        addDelivery();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        EmployeePage ep = new EmployeePage();
        ep.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtQtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyReleased
  
        //checkStock(); 
       
    }//GEN-LAST:event_txtQtyKeyReleased

    private void txtQtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyTyped
   

     
      char c = evt.getKeyChar();
                        if(Character.isLetter(c)){
                        txtQty.setEditable(false);
                        lblError.setText("Please enter number only");
                        }else
                        {
                        txtQty.setEditable(true);
                        lblError.setText("");
                        }
                        
     /**                  
                        
    txtQty.addKeyListener(new KeyAdapter() {
   @Override
   public void keyTyped(KeyEvent e) {
      char c = e.getKeyChar();
      if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
         e.consume();  // ignore event
         txtQty.setEditable(false);
         
      }
   }
});
     
      */
    }//GEN-LAST:event_txtQtyKeyTyped

    private void txtQtyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQtyKeyPressed
               
    }//GEN-LAST:event_txtQtyKeyPressed

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
            java.util.logging.Logger.getLogger(Delivery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Delivery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Delivery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Delivery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Delivery().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboSession;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblError;
    private javax.swing.JTable showPurchase;
    private javax.swing.JTextField txtBill;
    private javax.swing.JTextField txtCat;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextArea txtDesc;
    private javax.swing.JTextField txtPCode;
    private javax.swing.JTextField txtPName;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtSub;
    // End of variables declaration//GEN-END:variables
}
