
package inventory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class updateDelivery extends javax.swing.JFrame {

 
   
    public updateDelivery() {
        initComponents();
        loadClasses();
        showDelivery();
    }

   
    private void loadClasses(){
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
    
    
    
    
    private void updatePurchase(){
    
         if (txtPCode.getText().equals("") || txtCategory.getText().equals("")||  txtDate.getText().equals("")|| txtBill.getText().equals("")){
             JOptionPane.showMessageDialog(null,"Please Enter All Fields");
         }else{
             int opt = JOptionPane.showConfirmDialog(null, "Are You Sure To Update?", "Delete", JOptionPane.YES_NO_OPTION);
             if (opt==0){ 
            try{
           
             dbConnection db = new dbConnection();     
             Connection con=db.getConnection();        
            //Update spacific row
        int row =showDelivery.getSelectedRow();
        String value = (showDelivery.getModel().getValueAt(row,0).toString());      
   
         String sql = "Update delivery Set  dv_PCode=?, dv_PName=?, dv_Qty=?, dv_Cat=?, dv_Sub=?, dv_Note=?, dv_Session=?, dv_Ref=?, dv_Date=? Where dv_ID="+value;
         PreparedStatement pst = con.prepareStatement(sql);
         
       
            pst.setString(1, txtPCode.getText());
             pst.setString(2, txtPName.getText());
              int qtyex= Integer.parseInt(txtQty.getText());
              int qty = Integer.parseInt(spnQty.getValue().toString());
              int updateqty = qtyex+ qty;
              String Quantity = Integer.toString(updateqty); //conversion Int to String
            pst.setString(3, Quantity); 
            pst.setString(4, txtCategory.getText());             
                
            pst.setString(5, txtSub.getText());
            
            pst.setString(6, txtDesc.getText());
            String  session=comboSession.getSelectedItem().toString();
            pst.setString(7,session);
            pst.setString(8, txtBill.getText()); 
            pst.setString(9, txtDate.getText());
  
            pst.executeUpdate();
             showDelivery();
 
            
           //Updating Stock
         
            String stkQuantity = "Select stk_PCode, stk_Quantity  from stock  where stk_PCode='"+txtPCode.getText()+"'";
            PreparedStatement pstQty = con.prepareStatement(stkQuantity );
            ResultSet rsQty = pstQty.executeQuery();

              while (rsQty.next()){

            String existingStkQuantity = rsQty.getString("stk_Quantity");
            int existStkQty = Integer.parseInt(existingStkQuantity); // String to Integer conversion
            
                    int totalpurchase = existStkQty - qty;
                            
                    String stockQuantity = Integer.toString(totalpurchase); //Integer to Istring conversion
                    //jTextField1.setText(stockQuantity);
                    String totalin1 = "UPDATE stock SET stk_Quantity=? where stk_PCode='"+txtPCode.getText()+"'";
                    PreparedStatement pstmore = con.prepareStatement(totalin1);
                    pstmore.setString(1, stockQuantity);
                    pstmore.executeUpdate();   
            
                 }
            
            
           
            con.close();
 
         JOptionPane.showMessageDialog(null,"Delivery Updated Successfully!");
         
         txtPCode.setText("");
         txtPName.setText("");        
         txtQty.setText("");
         spnQty.setValue(0);
         txtCategory.setText("");
         txtSub.setText("");
         txtDesc.setText("");
         txtDate.setText("");
         txtBill.setText("");      
     
         
       }
       
        catch(Exception e)
            {
            JOptionPane.showMessageDialog(null,e);
            }
      }
         }
  }
  
    
     private void removeDeliveryItem(){
       if (txtPCode.getText().equals("") || txtPName.getText().equals("")){
             JOptionPane.showMessageDialog(null,"Some fields are empty");
         }else{    
            int opt = JOptionPane.showConfirmDialog(null, "Are You Sure To Delete?", "Delete", JOptionPane.YES_NO_OPTION);
            if (opt==0){ 
            try{
                dbConnection db = new dbConnection();     
                Connection con=db.getConnection();          
                  //Update spacific row
              int row =showDelivery.getSelectedRow();
              String value = (showDelivery.getModel().getValueAt(row,0).toString());

              String query = "DELETE From delivery where dv_ID="+value;
              PreparedStatement pst = con.prepareStatement(query);
              pst.executeUpdate();
              DefaultTableModel model = (DefaultTableModel)showDelivery.getModel();
              model.setRowCount(0);
        
        
        
        //Substrac the quantity from Stock
        String stkQuantity = "Select stk_PCode, stk_Quantity  from stock  where stk_PCode='"+txtPCode.getText()+"'";
            PreparedStatement pstQty = con.prepareStatement(stkQuantity );
            ResultSet rsQty = pstQty.executeQuery();

              while (rsQty.next()){

            String existingStkQuantity = rsQty.getString("stk_Quantity");
            int existStkQty = Integer.parseInt(existingStkQuantity); // String to Integer conversion
            int qtyex= Integer.parseInt(txtQty.getText());
                    int totalpurchase = existStkQty + qtyex;  //Adding the quantity to Stock after removing a delivery
                            
                    String stockQuantity = Integer.toString(totalpurchase); //Integer to Istring conversion
                    //jTextField1.setText(stockQuantity);
                    String totalin1 = "UPDATE stock SET stk_Quantity=? where stk_PCode='"+txtPCode.getText()+"'";
                    PreparedStatement pstmore = con.prepareStatement(totalin1);
                    pstmore.setString(1, stockQuantity);
                    pstmore.executeUpdate();   
            
                 }
        
        
        
        
        JOptionPane.showMessageDialog(null,"Requested Data Has Been Deleted Successfully!");
        
        
        
        
        
        
        //To clear text fields after data has been deleted
        txtPCode.setText("");
         txtPName.setText("");        
         txtQty.setText("");
         spnQty.setValue(0);
         txtCategory.setText("");
         txtPCode.setText("");
         txtDesc.setText("");
         txtDate.setText("");
         txtBill.setText("");   
        
        showDelivery();
        con.close();
       }
       catch(Exception ex){
           JOptionPane.showMessageDialog(null,ex);
        }    
      }  
           
   }
     }
     
     
    private void showDelivery(){
      
      
      try{
            dbConnection db = new dbConnection(); 
            Connection con=db.getConnection(); 
            String sql = "select * from delivery";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel tm = (DefaultTableModel)showDelivery.getModel();
            tm.setRowCount(0);
            txtQty.setEditable(false);
            while(rs.next()){
            Object o[]={rs.getString("dv_ID"), rs.getString("dv_PCode"), rs.getString("dv_PName"),rs.getString("dv_Qty"), 
            rs.getString("dv_Cat"),rs.getString("dv_Sub"),  rs.getString("dv_Note"), rs.getString("dv_Session"), rs.getString("dv_Date"), rs.getString("dv_ref") };
            tm.addRow(o);
            
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);          
            }     
  }
  
   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        showDelivery = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        txtPCode = new javax.swing.JTextField();
        comboSession = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDesc = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtCategory = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtBill = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        spnQty = new javax.swing.JSpinner();
        txtSub = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Update Delivery ");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        showDelivery.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product Code", "Name", "Quantity", "Category", "Sub-Category", "Note", "Session", "Date", "Ref No"
            }
        ));
        showDelivery.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showDeliveryMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(showDelivery);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
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

        txtPCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPCodeKeyPressed(evt);
            }
        });

        jLabel3.setText("Enter Product Code:");

        jLabel6.setText("Supply Area:");

        jLabel7.setText("Quantity:");

        jLabel8.setText("Product Name:");

        txtDesc.setColumns(20);
        txtDesc.setRows(5);
        txtDesc.setBorder(javax.swing.BorderFactory.createTitledBorder("Note/Description"));
        jScrollPane1.setViewportView(txtDesc);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 153, 102));
        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 0, 0));
        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txtCategory.setToolTipText("");

        jLabel10.setText("Category:");

        jLabel12.setText("Date:");

        jLabel23.setText("Bill No:");

        spnQty.setBorder(javax.swing.BorderFactory.createTitledBorder("Update Quantity"));
        spnQty.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnQtyStateChanged(evt);
            }
        });

        jLabel11.setText("Sub-Category:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10))
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtQty)
                    .addComponent(comboSession, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPCode, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                    .addComponent(txtCategory, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                    .addComponent(txtSub, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(spnQty, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBill, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel23))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(txtBill, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtSub, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addGap(7, 7, 7)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(spnQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel6)
                .addGap(22, 22, 22)
                .addComponent(jLabel3)
                .addGap(14, 14, 14)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboSession, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(txtPCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(txtPName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(0, 0, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            
        }
      
    }//GEN-LAST:event_txtPCodeKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        updatePurchase();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
         AdminPage ap = new AdminPage();
        ap.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    removeDeliveryItem();      
    }//GEN-LAST:event_jButton2ActionPerformed

    private void showDeliveryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showDeliveryMouseClicked
        int i = showDelivery.getSelectedRow();
        TableModel model = showDelivery.getModel();
        txtPCode.setText(model.getValueAt(i,1).toString());
        txtPName.setText(model.getValueAt(i,2).toString());
        txtQty.setText(model.getValueAt(i,3).toString());
        txtCategory.setText(model.getValueAt(i,4).toString());
        txtSub.setText(model.getValueAt(i,5).toString());
        txtDesc.setText(model.getValueAt(i,6).toString());
        txtDate.setText(model.getValueAt(i,8).toString());
        comboSession.setSelectedItem(model.getValueAt(i,7).toString());
        txtBill.setText(model.getValueAt(i,9).toString());
    }//GEN-LAST:event_showDeliveryMouseClicked

    private void spnQtyStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnQtyStateChanged
      
    }//GEN-LAST:event_spnQtyStateChanged

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
            java.util.logging.Logger.getLogger(updateDelivery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(updateDelivery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(updateDelivery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(updateDelivery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new updateDelivery().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboSession;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable showDelivery;
    private javax.swing.JSpinner spnQty;
    private javax.swing.JTextField txtBill;
    private javax.swing.JTextField txtCategory;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextArea txtDesc;
    private javax.swing.JTextField txtPCode;
    private javax.swing.JTextField txtPName;
    private javax.swing.JTextField txtQty;
    private javax.swing.JTextField txtSub;
    // End of variables declaration//GEN-END:variables
}
