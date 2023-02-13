# Konzultáción elhangzó gyakorlati feladatok

Tesztesetek írása akkor is javasolt, ha ez nem szerepel a feladatleírásban.

# WEEK 01 (2023.02.06-2023.02.10)

## DAY 02

Készíts egy osztályt `Algorithms` néven, benne egy metódussal, ami egész számok listáját várja.
Tudjuk, hogy a listában minden szám egyszer szerepel, kivéve egyet. A feladat az, hogy add vissza
azt a számot amelyik többször szerepel!

## DAY 03

Készíts egy `Movie` osztályt. Minden filmnek legyen egy egyedi azonosítója, egy címe, egy gyártási dátuma 
egy hossza, és egy műfaja ami enum típusú.  
Készíts egy `MovieService` osztályt melyben filmek listája található. 
Legyen egy `addMovie()` metódus amivel filmet lehet hozzáadni a listához, de figyeljünk arra, hogy csak 1911.01.01
utáni filmek kerülhessenek a listába.  
Továbbbi metódusok:
- Legyen egy metódus ami cím alapján talál meg egy filmet. Feltételezhetjük, hogy nincs két ugyanolyan című film. 
- Legyen egy metódus, ami csak egy paraméterül kapott bizonyos évszám utáni filmeket ad vissza egy listában. 
- Legyen egy metódus ami egy map-ben visszaadja műfajonként a filmeket egy listában. 

# WEEK 02 (2023.02.13-2023.02.17)

## DAY 01

Az [orders-app példa alkalmazás](https://github.com/Strukturavaltas3-Halado-Java/java-strukturavalto3-halado/tree/main/lab-solutions/consultation_w02d01/orders-app)-t kell 
lemásolni magadhoz a saját gépedre. A tesztosztályokban már benne vannak a megoldások, ezért azokból töröld ki az összes tesztmetódust, 
majd írd meg őket az alábbiak szerint:

* Írj tesztet az `Order` osztályra! (A kétféle létrehozásra.)
* Írj tesztet az `OrderRepository` `saveOrder()` metódusára! (Az id-ra úgy kell assertet írni, hogy abban ne szerepeljen az id tényleges értéke.)
* Írj tesztet az `OrderRepository` `getOrders()` metódusára! (Kollekcióra az AssertJ-vel írj assertet!)
* Írj egy paraméterezett tesztet az `OrderRepository` `getOrdersOverLimitedOrderPrice()` metódusára,
  legalább két különböző értékkel! 
* Hozz létre egy tesztosztályt az `OrderService` osztályra, amelyben kimockolod a osztály függőségét az `OrderRepository`
  osztályra!
* Írj egy-egy tesztesetet az `OrderService` osztály `saveOrder()` és `saveOrderAndDontReturnGeneratedKeys()` metódusaira! 
  (Figyeld meg, mi a különbség egy `void` és egy tényleges visszatérési értékkel rendelkező metódus mockolt tesztelésénél!)
* Írj egy tesztesetet az `OrderService` osztály `getOrders()` metódusára! (Gondolkozz el azon, hogy egy egysoros metódusnál mit lehet érdemes egyáltalán tesztelni!)
* Írj két tesztesetet az `OrderService` osztály `getOrdersOverLimitedOrderPrice()` metódusára! Az egyikben
  egy sima lefutást tesztelj, a másikban egy kivételdobást! 
* Írj egy tesztesetet az `OrderService` osztály `collectProductsAndCount()` metódusára!
