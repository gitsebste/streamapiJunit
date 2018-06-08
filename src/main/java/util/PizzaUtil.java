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
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author device02
 */
public class PizzaUtil {
    
    public static void main(String[] args) {
        PizzaUtil pizzaUtil = new PizzaUtil();//.findCheapestSpicy();
        pizzaUtil.findCheapestSpicy();
    }
    
    Pizza[] allPizzas(){        
        return Pizza.AMORE.getDeclaringClass().getEnumConstants();
    }
    
    Pizza findCheapestSpicy(){
        return (Pizza) Stream.of(allPizzas()).
                filter(x->x.getIngredients().stream().anyMatch(y->y.isSpicy())).map(s->
        {
            return new Object[]{s ,s.getIngredients().stream()
                    .map(i1->i1.getPrice()).reduce(0, (a,b)->a+b)};
        }).min((o1,o2)->((Integer)o1[1]).compareTo((Integer)o2[1])).get()[0];
        //System.out.println("Code cheapest: "+p);
        //return p;
    }
    Pizza findMostExpensiveVegetarian(){
                return (Pizza) Stream.of(allPizzas()).
                filter(x->x.getIngredients().stream().anyMatch(y->!y.isMeat())).map(s->
        {
            return new Object[]{s ,s.getIngredients().stream()
                    .map(i1->i1.getPrice()).reduce(0, (a,b)->a+b)};
        }).max((o1,o2)->((Integer)o1[1]).compareTo((Integer)o2[1])).get()[0];
        //return p;
    }
    List<Pizza> iLikeMeat(){
        //metoda zwracająca same pizze mięsne, posortowane malejąco po liczbie składników mięsnych
        List<Pizza> ret = Stream.of(allPizzas())
                .filter(x->x.getIngredients().stream().anyMatch(y->y.isMeat()))
                .sorted((p1,p2)->p2.getName().compareTo(p1.getName()))
                .sorted((p1,p2)->
                        (int)p2.getIngredients().stream().filter(ing->ing.isMeat()).count()-
                                (int)p1.getIngredients().stream().filter(ing->ing.isMeat()).count())
                .collect(Collectors.toList());
        //for(Pizza p:ret)System.out.println(p.getName());
        //System.out.println();System.out.println();System.out.println();
        return ret;
    }
    Map<Integer,List<Pizza>>groupByPrice(){
        Map<Integer, List<Pizza>> ret = Stream.of(allPizzas())
                .collect(Collectors.groupingBy(p -> p .getIngredients().stream()
                        .map(i1->i1.getPrice()).reduce(0, (a,b)->a+b))); //};
        //}).max((o1,o2)->((Integer)o1[1]).compareTo((Integer)o2[1]))));
        System.out.println(ret);
        return ret;
    }
    String formatedMenu(){
        //metoda zwracająca string w pozstaci nazwa_pizzy: składnik1, składnik2,
        //składnik3 - cena, 
        //kolejne pizze oddzielone znakiem nowej linii.
        String ret = Stream.of(allPizzas()).map(p -> {
            ArrayList<String> arr = new ArrayList< >();
            ArrayList<Integer> price  = new ArrayList< >();
            p.getIngredients().stream()
                    .forEach(i->{arr.add(i.getName());price.add(i.getPrice());});
            arr.add(price.stream().reduce(0,(x,y)->x+y).toString());
            String pr = arr.remove(arr.size()-1);
            String la = arr.remove(arr.size()-1);
            return arr.stream().reduce(p.getName()+": ",(x,y)->(x+y+", "))+la+" - "+pr+'\n';
        }).reduce("",(x,y)->x+y);
        //System.out.println(ret);
        return ret;
    }
    
    
}
