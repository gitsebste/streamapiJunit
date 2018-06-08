/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.s.streamapijunit.Ingredient;
import com.s.streamapijunit.Pizza;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.collections.transformation.SortedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author device02
 */
public class PizzaUtilTest {
    
    public PizzaUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class PizzaUtil.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        PizzaUtil.main(args);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of allPizzas method, of class PizzaUtil.
     */
    @Test
    public void testAllPizzas() {
        System.out.println("allPizzas");
        PizzaUtil instance = new PizzaUtil();
        Pizza[] expResult = Pizza.AMORE.getDeclaringClass().getEnumConstants();
        Pizza[] result = instance.allPizzas();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of findCheapestSpicy method, of class PizzaUtil.
     */
    @Test
    public void testFindCheapestSpicy() {
        System.out.println("findCheapestSpicy");
        PizzaUtil instance = new PizzaUtil();
        Pizza expResult = cheapestSpicy();
        Pizza result = instance.findCheapestSpicy();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of findMostExpensiveVegetarian method, of class PizzaUtil.
     */
    @Test
    public void testFindMostExpensiveVegetarian() {
        System.out.println("findMostExpensiveVegetarian");
        PizzaUtil instance = new PizzaUtil();
        Pizza expResult = mostExpVege();
        Pizza result = instance.findMostExpensiveVegetarian();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of iLikeMeat method, of class PizzaUtil.
     */
    @Test
    public void testILikeMeat() {
        System.out.println("iLikeMeat");
        PizzaUtil instance = new PizzaUtil();
        List<Pizza> expResult = iLikeMeat();
        List<Pizza> result = instance.iLikeMeat();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of groupByPrice method, of class PizzaUtil.
     */
    @Test
    public void testGroupByPrice() {
        System.out.println("groupByPrice");
        PizzaUtil instance = new PizzaUtil();
        Map<Integer, List<Pizza>> expResult = groupByPrice();
        Map<Integer, List<Pizza>> result = instance.groupByPrice();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of formatedMenu method, of class PizzaUtil.
     */
    @Test
    public void testFormatedMenu() {
        System.out.println("formatedMenu");
        PizzaUtil instance = new PizzaUtil();
        String expResult = formatedMenu();
        String result = instance.formatedMenu();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    private Pizza cheapestSpicy() {
        Pizza[] all = Pizza.AMORE.getDeclaringClass().getEnumConstants();
        //List<Pizza> spicy = Stream.of(all).filter(p -> p.getIngredients().stream().anyMatch(ing->ing.isSpicy())).collect(Collectors.toList());

        Map<Pizza,Integer> map = new HashMap<>();
        for(Pizza p:all){
            int sum=0;boolean spicy=false;        
            for(Ingredient ing:p.getIngredients()){
                sum+=ing.getPrice();
                if(ing.isSpicy()){
                    spicy=true;
                }
            }            
            if(spicy)map.put(p, sum);
        }
        int i=0;Pizza cheapest=null; int price=0;       
        for(Pizza key :map.keySet()){
            if(i==0){cheapest=key;price=map.get(key);}
            i++;
            if(price>map.get(key)){cheapest=key;price=map.get(key);}
        }
        return cheapest;
    }

    private Pizza mostExpVege() {
                Pizza[] all = Pizza.AMORE.getDeclaringClass().getEnumConstants();
        Map<Pizza,Integer> map = new HashMap<>();
        for(Pizza p:all){
            int sum=0;boolean spicy=false;        
            for(Ingredient ing:p.getIngredients()){
                sum+=ing.getPrice();
                if(ing.isMeat()){
                    spicy=true;
                }
            }            
            if(spicy)map.put(p, sum);
        }
        int i=0;Pizza cheapest=null; int price=0;       
        for(Pizza key :map.keySet()){
            if(i==0){cheapest=key;price=map.get(key);}
            i++;
            if(price<map.get(key)){cheapest=key;price=map.get(key);}
        }        
        return cheapest;
    }

    private List<Pizza> iLikeMeat() {
        class PizzaMeatPair{
            Pizza p;Integer meat;
            PizzaMeatPair(Pizza p,Integer meat){this.p=p;this.meat=meat;}
        }
        Pizza[] all = Pizza.AMORE.getDeclaringClass().getEnumConstants();
        List<PizzaMeatPair> pizzaMeatPairs = new ArrayList<PizzaMeatPair>();
        for(Pizza p:all){
            int sumMeat=0;     
            for(Ingredient ing:p.getIngredients()){
                if(ing.isMeat())sumMeat++;
            }            
            if(sumMeat>0)pizzaMeatPairs.add(new PizzaMeatPair(p,sumMeat));//map.put(p, sumMeat);
        }
        pizzaMeatPairs.sort((p1,p2)->p1.p.getName().compareTo(p2.p.getName()));
        pizzaMeatPairs.sort((p1,p2)->p1.meat.compareTo(p2.meat));
        ArrayList<Pizza> ret = new ArrayList<Pizza>();
        for(int i=pizzaMeatPairs.size()-1;i>-1;i--){
            ret.add(pizzaMeatPairs.get(i).p);
        }
        return ret;
    }

    private Map<Integer, List<Pizza>> groupByPrice() {
         Map<Integer, List<Pizza>> ret = new HashMap<>();
         
         Pizza[] all = Pizza.AMORE.getDeclaringClass().getEnumConstants();
         for(Pizza p:all){
             int price=0;
             for(Ingredient i:p.getIngredients())
                 price+=i.getPrice();
             if(ret.containsKey(price)){
                 ret.get(price).add(p);
             }else{
                 ArrayList<Pizza> tmp = new ArrayList<Pizza>();
                 tmp.add(p);
                 ret.put(price, tmp);
             }
         }
         return ret;
    }

    private String formatedMenu() {
        //metoda zwracająca string w pozstaci nazwa_pizzy: składnik1, składnik2,
        //składnik3 - cena, 
        //kolejne pizze oddzielone znakiem nowej linii.
        Pizza[] pizzas = Pizza.AMORE.getDeclaringClass().getEnumConstants();
        String ret = "";
        
        for(Pizza p:pizzas){
            ret+=p.getName()+": ";
            int price=0;
            for(int i=0;i<p.getIngredients().size()-1;i++){
                ret+=p.getIngredients().get(i).getName()+", ";
                price+=p.getIngredients().get(i).getPrice();
            }
            ret+=p.getIngredients().get(p.getIngredients().size()-1).getName()+" - ";
            price+=p.getIngredients().get(p.getIngredients().size()-1).getPrice();
            ret+=String.valueOf(price)+'\n';
        }
//        System.out.println("TEST ANSWER:");
//        System.out.println(ret);
        return ret;
    }
}
