
package inventory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class showStock {
    
    dbConnection db = new dbConnection();     
     
    public ArrayList<objStock> getData(String Sub){

   ArrayList<objStock> list = new ArrayList<objStock>();
   Connection con=db.getConnection();
   Statement st;
   ResultSet rs;
   
   try {
   st = con.createStatement();
   rs = st.executeQuery("SELECT stock.ID, stock.stk_PCode,  stock.stk_Quantity, products.pro_Name as productName, products.sub_ID as subCategory, products.cat_ID as Category FROM stock INNER JOIN products ON stock.stk_PCode = products.pro_Code");
  
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
   Logger.getLogger(showStock.class.getName()).log(Level.SEVERE, null, ex);
   }
   return list;
   }
    
}
