
package inventory;


public class objStock {
    private int id;
    private String code;
    private String name;
    private String qte;
    private String sub;
    private String cat;
    
    public objStock(){}
    
    
      public objStock(int Id, String Code, String Name, String Qte, String Sub,String Cat){
    
        this.id = Id;
        this.code = Code;
        this.name = Name;
        this.qte = Qte;
        this.sub = Sub;
        this.cat = Cat;
    }
      
     public int getId(){
      return id;
    }
    
    public void setId(int Id){
        this.id = Id;
    }
    
    public String getCode(){
        return code;
    }
    
    public void setCode(String Code){
        this.code = Code;
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
    
    public String getSub(){
        return sub;
    }
    
    public void setSub(String Sub){
        this.sub = Sub;
    }
    
    public String getCat(){
        return cat;
    }
    
    public void setCat(String Cat){
        this.cat = Cat;
    }      
      
}
