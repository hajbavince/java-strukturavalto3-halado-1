
# Rendelések

## Az alkalmazás működése

Az `Order` osztály az adatbázis egy sorát reprezentálja. Minden rendelésnél van egy azonosító, egy termék,
az, hogy hány darabot rendeltek belőle és a darabonkénti ár. Rendelést létre lehet hozni egyedi azonosítóval vagy anélkül is.

Az `OrderRepository` osztályban van egy `saveOrder(Order order)` nevű metódus, ami lementi a rendelést az
adatbázisba, és visszaadja az adatbázis által generált egyedi azonosítót.
Szintén itt van egy `getOrder()` nevű metódus, ami visszaadja az összes rendelést id-val együtt abc sorrendben.
Van még továbbá egy `getOrdersOverLimitedOrderPrice(int limit)` nevű metódus, ami a rendelések teljes ára (ez a darabszám
és a darabár szorzata) alapján csak egy bizonyos összeg fölötti rendeléseket ad vissza.

Az `OrderService` adattagja egy `OrderRepository`, ezért van lehetőség innen delegálni a kéréseket a repository felé.
Van benne egy `collectProductsAndCount()` metódus, ami lekéri az összes terméket és egy `Map` adatszerkezetben összeszedi,
hogy melyik termékből összesen hány rendelést kell teljesíteni.

* Írj tesztet az `Order` osztályra!