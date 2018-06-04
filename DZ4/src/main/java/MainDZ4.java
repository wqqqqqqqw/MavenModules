import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Fruit {
    @Override
    public String toString() {
        return "I am a Fruit !!";
    }
}

class Apple extends Fruit {
    @Override
    public String toString() {
        return "I am an Apple !!";
    }
}

class AsianApple extends Apple {
    @Override
    public String toString() {
        return "I am an AsianApple !!";
    }
}

class RussianApple extends Apple {
    @Override
    public String toString() {
        return "I am an RussianApple !!";
    }
}

public class MainDZ4 {

    public static void main(String[] args) {

        //Пример использования CountMapImpl
        CountMap<Integer> map = new CountMapImpl<>();
        CountMap<Integer> map1 = new CountMapImpl<>();

        System.out.println("Map");
        map.getCount(10);
        map.add(10);
        map.add(10);
        map.add(10);
        map.remove(10);
        map.add(10);

        System.out.println("--====--");
        System.out.println("Map1");
        map1.add(5);
        map1.add(6);

        Map<Integer, Integer> map2 = new HashMap<>();

        System.out.println("--====--");
        System.out.println("Map");
        map.remove(10);
        map.getCount(10);
        map.getCount(5);

        System.out.println("--====--");
        System.out.println("Map1");
        map1.getCount(10);
        System.out.println("Добавляем Map");
        map1.addAll(map);
        map1.toMap(map2);

        System.out.println("--====--");
        System.out.println("Map2");
        System.out.println("Количество элементов 5: " + map2.get(5) + "\n");

// ==> /////////////////////////////////////////////////////////////////////
// ==> /////////////////////////////////////////////////////////////////////
// ==> /////////////////////////////////////////////////////////////////////

        System.out.println("--====--");
        //Пример использования CollectionUtils

        List<AsianApple> asianApples = new ArrayList();
        List<RussianApple> russianApples = new ArrayList();
        List<Fruit> fruits = new ArrayList();
        List<Integer> integers = CollectionUtils.newArrayList();     //Successful
        List<Apple> applesBasket = CollectionUtils.newArrayList(); //Successful

// ПРОВЕРКА         add
        applesBasket.add(new Apple());
        applesBasket.add(new AsianApple());
        applesBasket.add(new Apple());
        applesBasket.add(new RussianApple());
//      applesBusket.add(new Fruit());        //Compile time error

// ПРОВЕРКА         addAll
        CollectionUtils.<Apple>addAll(applesBasket, fruits);         //Successful
//        CollectionUtils.<Apple>addAll(asianApples, applesBasket);  //Successful
//        CollectionUtils.<Apple>addAll(asianApples, fruits);        //Successful
//        CollectionUtils.<Apple>addAll(asianApples, russianApples); //Compile time error
//        CollectionUtils.<Apple>addAll(applesBasket, integers);     //Compile time error
//        CollectionUtils.<Apple>addAll(fruits, applesBasket);       //Compile time error

// ПРОВЕРКА         limit
        List<Apple> appleBasketPart = CollectionUtils.limit(applesBasket, 3);    //Successful
//      List<Apple>        appleBasketPart = CollectionUtils.limit(integers, 1);      //Compile time error
//      List<RussianApple> appleBasketPart = CollectionUtils.limit(asianApples, 3);   //Compile time error
//      List<AsianApple>   appleBasketPart = CollectionUtils.limit(asianApples, 3);   //Successful
//      List<AsianApple>   appleBasketPart = CollectionUtils.limit(russianApples, 3); //Compile time error    
//      List<Fruit>        appleBasketPart = CollectionUtils.limit(applesBasket, 3);  //Successful

// ПРОВЕРКА         add
//      appleBasketPart.add(new Apple());
//      appleBasketPart.add(new AsianApple());
//      appleBasketPart.add(new Fruit());        //Compile time error

// ПРОВЕРКА         removeAll
        System.out.println(applesBasket);

        List<Apple> appleBasketCopy = new ArrayList<>();
        CollectionUtils.<Apple>addAll(applesBasket, appleBasketCopy);

        CollectionUtils.<Apple>removeAll(applesBasket, appleBasketPart);
        System.out.println(applesBasket);

//        CollectionUtils.<AsianApple>removeAll(applesBasket, asianApples);     //Successful
//        CollectionUtils.<AsianApple>removeAll(russianApples, asianApples);    //Compile time error 
//        CollectionUtils.<AsianApple>removeAll(asianApples, asianApples);      //Successful
//        CollectionUtils.<AsianApple>removeAll(applesBasket, appleBasketPart); //Compile time error    
//        CollectionUtils.<Fruit>removeAll(applesBasket, appleBasketPart);      //Compile time error    
//        CollectionUtils.<Fruit>removeAll(fruits, appleBasketPart);            //Successful

// ПРОВЕРКА         containsAll containsAny
        System.out.println(CollectionUtils.<Apple>containsAny(appleBasketCopy, applesBasket)); //true
        System.out.println(CollectionUtils.<Apple>containsAny(applesBasket, asianApples));     //false

        CollectionUtils.<AsianApple>add(asianApples, (AsianApple) (appleBasketPart.get(1)));
        System.out.println(CollectionUtils.<Apple>containsAll(appleBasketCopy, asianApples));  //true

// ПРОВЕРКА         range Comparable
        List<Double> d = Arrays.asList(8., 1., 3., 5., 6., 4.);
        List<Integer> i = Arrays.asList(8, 1, 3, 5, 6, 4);

        List<Integer> iRange = CollectionUtils.range(i, 3, 6);    //Successful
        List<Double> dRange = CollectionUtils.range(d, 3., 6.);  //Successful
//        List<Number> iRange = CollectionUtils.range(i, 3, 6);   //Compile time error
//        List<Double> dRange = CollectionUtils.range(i, 3., 6.); //Compile time error    

        System.out.println(iRange);
        System.out.println(dRange);

// ПРОВЕРКА         range Comparator        
        List<Double> dcRange = CollectionUtils.<Double>range(d, 3., 6., (Double o1, Double o2) -> o1.compareTo(o2));

        System.out.println(dcRange); //Successful
    }
}