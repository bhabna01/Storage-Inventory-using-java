
package inventory;


public class objProductIn {
    

    private String pcode;
    private String name;
    private String qte;
    private String cost;
   
    
    
    public objProductIn(){}
    
    public objProductIn( String pCode, String Name, String Qte, String Cost){
    
        
        this.pcode= pCode;
        this.name = Name;
        this.qte = Qte;
        this.cost = Cost;
      
    }
    
   
    
    public String getpCode(){
      return pcode;
    }
    
    public void setpCode(String pCode){
        this.pcode = pCode;
    }
    public String getName(){
        return name;
    }
    
    public void setName(String Name){
        this.name = Name;
    }
    
    public String getQte(){
        return qte;
    }
    
    public void setQte(String Qte){
        this.qte = Qte;
    }
    
     public String getCost(){
        return cost;
    }
    
    public void setCost(String Cost){
        this.cost = Cost;
    }
 
    
}
