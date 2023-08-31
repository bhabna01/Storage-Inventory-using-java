
package inventory;

import java.awt.Color;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;


public class adminStock extends javax.swing.JFrame {
@Override
    public Color getBackground() {
        return super.getBackground();
    }
   
    public adminStock() {
        initComponents();
        comboSubcaegoryLoad();
       showSearchStock();
       
    }

   
    // Load the Category Combo
     private void comboSubcaegoryLoad(){
        
    try{
        dbConnection db = new dbConnection();     
        Connection con=db.getConnection(); 
        String sql=("SELECT DISTINCT products.sub_ID as Subcategory FROM stock INNER JOIN products ON products.pro_Code=stock.stk_PCode");
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs=pst.executeQuery();
     while (rs.next()){
        comboSub.addItem(rs.getString("Subcategory")); 
        }
        }
         catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
     
     //Combine Stock Object Data with an arry list
     private ArrayList<objStock> getStock(String Sub){
        
        dbConnection db = new dbConnection();     
     
        ArrayList<objStock> list = new ArrayList<objStock>();
        Connection con=db.getConnection();
        Statement st;
        ResultSet rs;

   try {
        st = con.createStatement();
        rs = st.executeQuery("SELECT stock.ID, stock.stk_PCode,  stock.stk_Quantity, products.pro_Name as productName, products.sub_ID as subCategory, products.cat_ID as Category FROM stock INNER JOIN products ON stock.stk_PCode = products.pro_Code Where products.sub_ID='"+Sub+"'");

        objStock os;
        while(rs.next()){
        os = new objStock(
        rs.getInt("stock.ID"),
        rs.getString("stock.stk_PCode"),
        rs.getString("productName"),
        rs.getString("stock.stk_Quantity"),
        rs.getString("subCategory"),
        rs.getString("Category")
   
             );
            list.add(os);
          }
   
        } catch (SQLException ex) {
        Logger.getLogger(adminStock.class.getName()).log(Level.SEVERE, null, ex);
            }
        return list;
    }
    
    //Show Data an in a Table based on Subcategory Combo box 
  private void showStockSub(){
        
        ArrayList<objStock> list = getStock((String)comboSub.getSelectedItem());
        DefaultTableModel model = new DefaultTableModel();       
        model.setColumnIdentifiers(new Object[]{"ID","Code", "Product Name","Qte","Sub-Category","Category"});
        Object[] row = new Object[6];
        for(int i = 0; i < list.size(); i++){
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getCode();
            row[2] = list.get(i).getName();
            row[3] = list.get(i).getQte();
            row[4] = list.get(i).getSub();
            row[5] = list.get(i).getCat();
            model.addRow(row);
        }
        
        tblshowStock.setModel(model);
        
    }  
     
   
    
  private ArrayList<objStock> getSearchStock(String CODE){
        
        dbConnection db = new dbConnection();     
     
        ArrayList<objStock> list = new ArrayList<objStock>();
        Connection con=db.getConnection();
        Statement st;
        ResultSet rs;

   try {
        st = con.createStatement();
        rs = st.executeQuery("SELECT stock.ID, stock.stk_PCode,  stock.stk_Quantity, products.pro_Name as productName, products.sub_ID as subCategory, products.cat_ID as Category FROM stock INNER JOIN products ON stock.stk_PCode = products.pro_Code Where stock.stk_PCode='"+CODE+"'");

        objStock os;
        while(rs.next()){
        os = new objStock(
        rs.getInt("stock.ID"),
        rs.getString("stock.stk_PCode"),
        rs.getString("productName"),
        rs.getString("stock.stk_Quantity"),
        rs.getString("subCategory"),
        rs.getString("Category")
   
             );
            list.add(os);
          }
   
        } catch (SQLException ex) {
        Logger.getLogger(adminStock.class.getName()).log(Level.SEVERE, null, ex);
            }
        return list;
    }
  
  
  private void showSearchStock(){
       
        ArrayList<objStock> list = getSearchStock(jText_Search.getText());
        if (list != null){
        DefaultTableModel model = new DefaultTableModel();       
        model.setColumnIdentifiers(new Object[]{"ID","Code", "Product Name","Qte","Sub-Category","Category"});
        Object[] row = new Object[6];
        for(int i = 0; i < list.size(); i++){
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getCode();
            row[2] = list.get(i).getName();
            row[3] = list.get(i).getQte();
            row[4] = list.get(i).getSub();
            row[5] = list.get(i).getCat();
            model.addRow(row);
        }
        
        tblshowStock.setModel(model);
        
    } 
  }
  
  
    
     private void  showallStock(){
      
      
      try{
            dbConnection db = new dbConnection(); 
            Connection con=db.getConnection(); 
            DefaultTableModel model = (DefaultTableModel) tblshowStock.getModel();
            model.setRowCount(0);
            
            String sql = "SELECT stock.ID, stock.stk_PCode, stock.stk_Quantity, products.pro_Name, products.sub_ID, products.cat_ID FROM stock INNER JOIN products ON stock.stk_PCode = products.pro_Code";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel tm = (DefaultTableModel)tblshowStock.getModel();
            tm.setRowCount(0);
            while(rs.next()){
            Object o[]={rs.getInt("stock.ID"), rs.getString("stock.stk_PCode"), rs.getString("products.pro_Name"), rs.getString("stock.stk_Quantity"),rs.getString("products.sub_ID"),rs.getString("products.cat_ID"), 
            };
            tm.addRow(o);
            changeTable(3);
            }con.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);          
            }     
  }
    
    
     
     
     
     
     
       private void  showavailableStock(){
      
      
      try{
            dbConnection db = new dbConnection(); 
            Connection con=db.getConnection(); 
            //Clearing Table Data
            DefaultTableModel model = (DefaultTableModel) tblshowStock.getModel();
            model.setRowCount(0);
            
            String sql = "SELECT stock.ID, stock.stk_PCode, stock.stk_Quantity, products.pro_Name, products.sub_ID, products.cat_ID FROM stock INNER JOIN products ON stock.stk_PCode = products.pro_Code Where stock.stk_Quantity >'0' ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel tm = (DefaultTableModel)tblshowStock.getModel();
            tm.setRowCount(0);
            while(rs.next()){
            Object o[]={rs.getInt("stock.ID"), rs.getString("stock.stk_PCode"), rs.getString("products.pro_Name"), rs.getString("stock.stk_Quantity"),rs.getString("products.sub_ID"),rs.getString("products.cat_ID"), 
            };
            tm.addRow(o);
            
            changeTable(3);
            
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,e);          
            }     
  }
    
    
    
       
       
       public void changeTable( int column_index) {
        tblshowStock.getColumnModel().getColumn(column_index).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int st_val = Integer.parseInt(table.getValueAt(row, 3).toString());       
                if ((st_val < 10) && (st_val > 5)) {
                    c.setBackground(Color.YELLOW);
                } else if(st_val < 1){
                    c.setBackground(Color.RED);
                }
                else {
                    c.setBackground(Color.GREEN);
                }
                return c;
            }
        });
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        comboSub = new javax.swing.JComboBox<>();
        jText_Search = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblshowStock = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 51, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("View Stock");

        jButton1.setForeground(new java.awt.Color(204, 0, 0));
        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(350, 350, 350)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 153, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton2.setText("Show All Products");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Show Available Products");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        comboSub.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboSubItemStateChanged(evt);
            }
        });
        comboSub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboSubActionPerformed(evt);
            }
        });

        jText_Search.setBorder(javax.swing.BorderFactory.createTitledBorder("Search Stock Using Code"));
        jText_Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jText_SearchActionPerformed(evt);
            }
        });
        jText_Search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jText_SearchKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(comboSub, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jText_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboSub, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jText_Search, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 153, 102));

        jPanel9.setBackground(new java.awt.Color(0, 153, 102));
        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tblshowStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblshowStock);

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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 291, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
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
        AdminPage ap = new AdminPage();
        ap.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        showallStock();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        showavailableStock();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jText_SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jText_SearchActionPerformed
      
    }//GEN-LAST:event_jText_SearchActionPerformed

    private void jText_SearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jText_SearchKeyPressed
       
        showSearchStock();
    }//GEN-LAST:event_jText_SearchKeyPressed

    private void comboSubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboSubActionPerformed
        showStockSub();
        changeTable(3);
    }//GEN-LAST:event_comboSubActionPerformed

    private void comboSubItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboSubItemStateChanged

    }//GEN-LAST:event_comboSubItemStateChanged

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
            java.util.logging.Logger.getLogger(adminStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adminStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adminStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adminStock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new adminStock().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboSub;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jText_Search;
    private javax.swing.JTable tblshowStock;
    // End of variables declaration//GEN-END:variables
}
