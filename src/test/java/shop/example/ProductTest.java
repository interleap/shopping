package shop.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProductTest {
    
    @Test
    public void testProduct(){
        Product product1=new Product(1,"The Epic of Gilgamesh",100,null,"author","abc publications",null,null,null,null);
        String str=product1.productDetails();
        System.out.println("details:"+str);
        Product product2=new Product(2,null,100,"specs",null,null,"Blue","Motorola","G4",null);
        String str2=product2.productDetails();
        System.out.println("details:"+str2);
        Product product3=new Product(3,null,100,null,null,null,"Gold","Titan","W3","analog");
        String str3=product3.productDetails();
        System.out.println("details:"+str3);
    }
}
