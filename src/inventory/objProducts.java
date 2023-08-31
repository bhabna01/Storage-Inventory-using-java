
package inventory;


public class objProducts {
   
    private String pcode;
    private String pname;
    private String pcat;
    private String psub; 
    private String pdetail;  
    
    public objProducts( String pCode, String pName, String pCat, String pSub, String pDetail){
          
        this.pcode= pCode;
        this.pname = pName;
        this.pcat= pCat;
        this.psub = pSub;   
        this.pdetail = pDetail;
        
    }   
    
    public String getpCode(){
      return pcode;
    }
    
    public void setpCode(String pCode){
        this.pcode = pCode;
    }
    public String getpName(){
        return pname;
    }
    
    public void setpName(String pName){
        this.pname = pName;
    }
    
     public String getpCat(){
        return pcat;
    }
    
    public void setpCat(String pCat){
        this.pcat = pCat;
    }
    
    public String getpSub(){
        return psub;
    }
    
    public void setpSub(String pSub){
        this.psub = pSub;
    }
    
    
    public String getpDetail(){
        return pdetail;
    }
    
    public void setpDetail(String pDetail){
        this.pdetail = pDetail;
    }

     
}
