
package inventory;

import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class Products extends javax.swing.JFrame {

   
    public Products() {
        initComponents();
        combLoadCategory();
        showProducts();
    }

    private void combLoadCategory(){
    
    dbConnection db = new dbConnection();     
    Connection con=db.getConnection();  

        ResultSet rs;
        PreparedStatement stmt;
        String sqlquery = " Select DISTINCT cat_ID from subcategory";

        try{
            stmt = con.prepareStatement (sqlquery);
            rs = stmt.executeQuery();

        while (rs.next()){
            
            catCombo.addItem(rs.getString("cat_ID"));
             }
            con.close();
        }catch(Exception ex) {ex.printStackTrace();}
        
        }
    
    
    String subcat;
    private void combLoadSubCategory(){
    
    dbConnection db = new dbConnection();     
    Connection con=db.getConnection();  

        ResultSet rs;
        PreparedStatement stmt;
        String sqlquery = " Select DISTINCT sub_Name from subcategory where cat_ID = '"+subcat+"'";

        try{
            stmt = con.prepareStatement (sqlquery);
            rs = stmt.executeQuery();

        while (rs.next()){
            String abc =rs.getString(1);
            subCatCombo.addItem(abc);
        }
            con.close();
        }catch(Exception ex) {ex.printStackTrace();}
        
        }
    
    
    private void addProducts(){
         try{
            dbConnection db = new dbConnection();
            Connection con=db.getConnection();
            String sql = "insert into products(pro_Name, pro_Code, pro_Desc, cat_ID, sub_ID) values(?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, txtProductName.getText());
            pst.setString(2, txtProductCode.getText());
            pst.setString(3, txtProductDetails.getText());
            
            String cat=catCombo.getSelectedItem().toString();
            String subcat=subCatCombo.getSelectedItem().toString();
            pst.setString(4,cat);
            pst.setString(5,subcat);
            pst.executeUpdate();

            
            //Create new product in totalpurchase table
   
            String sql2 = " Insert into totalin(ppCode, ppName, ppQty) values (?,?,'0')";
            PreparedStatement pst2 = con.prepareStatement(sql2);
            pst2.setString(1, txtProductCode.getText());
            pst2.setString(2,txtProductName.getText());       
            pst2.executeUpdate();
      
            //Create new product in totaldelivery table
            String sql3 = " Insert into totalout (dpCode, dpName, dpQty) values (?,?,'0')";
            PreparedStatement pst3 = con.prepareStatement(sql3);
            pst3.setString(1, txtProductCode.getText());
            pst3.setString(2,txtProductName.getText());       
            pst3.executeUpdate();
      
            //Create new product in Stock table
            String sql4 = " Insert into stock (stk_PCode, stk_PName, stk_Quantity) values (?,?,'0')";
            PreparedStatement pst4 = con.prepareStatement(sql4);
            pst4.setString(1, txtProductCode.getText());
            pst4.setString(2,txtProductName.getText());       
            pst4.executeUpdate();
            
            JOptionPane.showMessageDialog(null,"New Products Created Successfully!");
            txtProductName.setText("");
            txtProductCode.setText("");
            txtProductDetails.setText("");
            
            showProducts();
            con.close();

        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
        showProducts();

    }
    
    
    private void showProducts(){
       try{
          dbConnection db = new dbConnection(); 
          Connection con=db.getConnection(); 
          String sql = "select * from products";
          PreparedStatement pst = con.prepareStatement(sql);
          ResultSet rs = pst.executeQuery();
          DefaultTableModel tm = (DefaultTableModel)tblShowProducts.getModel();
          tm.setRowCount(0);
         while(rs.next()){
            Object o[]={rs.getInt("pro_ID"), rs.getString("pro_Code"),rs.getString("pro_Name"), rs.getString("sub_ID"),  
                rs.getString("cat_ID"), rs.getString("pro_Desc")};
            tm.addRow(o);
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);          
            }    
        
        }
    
    private void updateProducts(){
      int opt = JOptionPane.showConfirmDialog(null, "Are You Sure To Update?", "Update", JOptionPane.YES_NO_OPTION);
    if (opt==0){       
    try{
          //Database Connection   
       dbConnection db = new dbConnection();     
       Connection con=db.getConnection();        
            //Update spacific row
        int row =tblShowProducts.getSelectedRow();
        String value = (tblShowProducts.getModel().getValueAt(row,0).toString());
        String query = "Update products SET pro_Name=?, pro_Code=?, pro_Desc=?, sub_ID=?, cat_ID=? where pro_ID="+value;
        PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, txtProductName.getText());
            pst.setString(2, txtProductCode.getText());
            pst.setString(3, txtProductDetails.getText()); 
            String cat=catCombo.getSelectedItem().toString();
            String subcat=subCatCombo.getSelectedItem().toString();
            pst.setString(4,cat);
            pst.setString(5,subcat);
            pst.executeUpdate();
            
            
            //Update in totalin, totalout, stock tables
           String updateTotalIn = "Update totalin SET ppCode=?, ppName=? where ID="+value;
            PreparedStatement pst2 = con.prepareStatement(updateTotalIn );
            pst2.setString(1, txtProductCode.getText());
            pst2.setString(2, txtProductName.getText());
            
            pst2.executeUpdate();  
            
            String updateTotalOut = "Update totalout SET dpCode=?,dpName=? where ID="+value;
            PreparedStatement pst3 = con.prepareStatement(updateTotalOut );
            pst3.setString(1, txtProductCode.getText());
            pst3.setString(2, txtProductName.getText());
            pst3.executeUpdate();  
           
            String updateStock = "Update stock SET stk_PCode=?,stk_PName=? where ID="+value;
            PreparedStatement pst4 = con.prepareStatement(updateStock );
            
            pst4.setString(1, txtProductCode.getText());
            pst4.setString(2, txtProductName.getText());
            pst4.executeUpdate();
       
         DefaultTableModel model = (DefaultTableModel)tblShowProducts.getModel();
         model.setRowCount(0);
         
         
         
         
        JOptionPane.showMessageDialog(null,"Data Updated Successfully!");
        //To clear text fields after updates
            txtProductName.setText("");
            txtProductCode.setText("");
            txtProductDetails.setText("");
            
        showProducts();
        con.close();
         
       }
 
        catch(Exception e)
            {
            JOptionPane.showMessageDialog(null,e);
            } 
        }   
    }
    
   private void removeProducts(){
       int opt = JOptionPane.showConfirmDialog(null, "Are You Sure To Delete?", "Delete", JOptionPane.YES_NO_OPTION);
      if (opt==0){ 
      try{
          dbConnection db = new dbConnection();     
          Connection con=db.getConnection();          
            //Update spacific row
        int row =tblShowProducts.getSelectedRow();
        String value = (tblShowProducts.getModel().getValueAt(row,0).toString());
        
        String query = "DELETE From products where pro_ID="+value;
        PreparedStatement pst = con.prepareStatement(query);
        pst.executeUpdate();
     
        DefaultTableModel model = (DefaultTableModel)tblShowProducts.getModel();
        model.setRowCount(0);
        JOptionPane.showMessageDialog(null,"Data Has Been Deleted Successfully!");
        
        //To clear text fields after data has been deleted
            txtProductName.setText("");
            txtProductCode.setText("");
            txtProductDetails.setText("");
        
        showProducts();
        con.close();
       }
       catch(Exception ex){
           JOptionPane.showMessageDialog(null,ex);
        }    
      }  
   }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        txtProductName = new javax.swing.JTextField();
        txtProductCode = new javax.swing.JTextField();
        catCombo = new javax.swing.JComboBox<>();
        subCatCombo = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtProductDetails = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblShowProducts = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Products");

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
                .addContainerGap(424, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(301, 301, 301)
                .addComponent(jButton4)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(0, 153, 102));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Create New Products", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(0, 102, 102))); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 102, 51));
        jButton1.setText("Create Products");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        catCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                catComboItemStateChanged(evt);
            }
        });

        txtProductDetails.setColumns(20);
        txtProductDetails.setRows(5);
        jScrollPane2.setViewportView(txtProductDetails);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Product Details");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Enter New Product Name:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Enter New Product Code:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Select Category:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Select Sub-Category:");

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 153, 102));
        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 0, 0));
        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(subCatCombo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(catCombo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtProductCode, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtProductName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jLabel1))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(41, 41, 41))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(jLabel2))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(txtProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtProductCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(catCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4))))
                                .addGap(12, 12, 12)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(subCatCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))))))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(0, 153, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Showing Products"));

        tblShowProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product Code", "Product Name", "Sub-Category ", "Category ", "Product Details"
            }
        ));
        tblShowProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblShowProductsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblShowProducts);
        if (tblShowProducts.getColumnModel().getColumnCount() > 0) {
            tblShowProducts.getColumnModel().getColumn(0).setPreferredWidth(8);
            tblShowProducts.getColumnModel().getColumn(1).setPreferredWidth(35);
            tblShowProducts.getColumnModel().getColumn(3).setPreferredWidth(40);
            tblShowProducts.getColumnModel().getColumn(4).setPreferredWidth(30);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       addProducts();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void catComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_catComboItemStateChanged
        if(evt.getStateChange() == ItemEvent.SELECTED)
        {
            subcat = catCombo.getSelectedItem().toString();
            subCatCombo.removeAllItems();
            combLoadSubCategory();
            
        }
    }//GEN-LAST:event_catComboItemStateChanged

    private void tblShowProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblShowProductsMouseClicked

        int i = tblShowProducts.getSelectedRow();
        TableModel model = tblShowProducts.getModel(); 
        txtProductCode.setText(model.getValueAt(i,1).toString());
        txtProductName.setText(model.getValueAt(i,2).toString());
       catCombo.setSelectedItem(model.getValueAt(i,4).toString());      
        // when catCombo change the Category type, the change of sub-cat will be done automatically
      // subCatCombo.setSelectedItem(model.getValueAt(i,3).toString()); 
        txtProductDetails.setText(model.getValueAt(i,5).toString());
        
    }//GEN-LAST:event_tblShowProductsMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       updateProducts();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       removeProducts();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        EmployeePage ep = new EmployeePage();
        ep.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Products().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> catCombo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> subCatCombo;
    private javax.swing.JTable tblShowProducts;
    private javax.swing.JTextField txtProductCode;
    private javax.swing.JTextArea txtProductDetails;
    private javax.swing.JTextField txtProductName;
    // End of variables declaration//GEN-END:variables
}
