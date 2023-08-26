/* 1.	Visualizzare i clienti (customers) in ordine alfabetico*/
select * from customers order by CompanyName;

/* 2.	Visualizzare i clienti che non hanno il fax*/
select * from customers where Fax is null;

/* 3.	Selezionare i nomi dei clienti (CompanyName) che iniziano con le lettere P, Q, R, S*/
select CompanyName from customers where CompanyName like 'P%' or CompanyName like 'Q%' or CompanyName like 'R%' or CompanyName like 'S%';

/* 4.	Visualizzare Nome e Cognome degli impiegati assunti (HireDate) dopo il '1993-05-03' e aventi titolo di “Sales Representative”*/
select FirstName, LastName from employees where HireDate > '1993-05-03' and Title = 'Sales Representative';

/* 5.	Selezionare la lista dei prodotti non sospesi (attributo discontinued), visualizzando 
anche il nome della categoria di appartenenza*/
select ProductName, CategoryName from products, categories where products.CategoryID = categories.CategoryID and discontinued = 0;

/* 6. Selezionare gli ordini relativi al cliente ‘Ernst Handel’ (CompanyName)*/
select orders.* from orders, customers where orders.CustomerID = customers.CustomerID and CompanyName = 'Ernst Handel';

/* 7.	Selezionare il nome della società e il telefono dei corrieri (shippers) che hanno consegnato 
ordini nella città di ‘Rio de Janeiro’ (ShipCity in orders)
N.B. nella tabella orders l'id corriere è l'attributo ShipVia*/
select CompanyName, Phone from shippers, orders where shippers.ShipperID = orders.ShipVia and ShipCity = "Rio de Janeiro";

/* 8.	Selezionare gli ordini (OrderID, OrderDate, ShippedDate) per cui la spedizione (ShippedDate)
è avvenuta entro 30 giorni dalla data dell’ordine (OrderDate)*/
select OrderID, OrderDate, ShippedDate from orders where ShippedDate <= OrderDate + 30;

/* 9.	Selezionare l’elenco dei prodotti che hanno un costo compreso tra 18 e 50, ordinando il risultato
in ordine di prezzo crescente */
select * from products where UnitPrice between 18 and 50 order by UnitPrice asc;

/* 10.	Selezionare tutti i clienti (CustomerID, CompanyName) che hanno ordinato il prodotto 'Chang'*/
select customers.CustomerID, CompanyName from customers, orders, `order details`, products where customers.CustomerID = orders.CustomerID and orders.OrderID = `order details`.OrderID and `order details`.ProductID = products.ProductID and ProductName = 'Chang';

/* 11.	Selezionare i clienti che non hanno mai ordinato prodotti di categoria ‘Beverages’*/
select * from customers where customers.CustomerID not in (select customers.CustomerID from customers, orders, `order details`, products, categories where customers.CustomerID = orders.CustomerID and orders.OrderID = `order details`.OrderID and `order details`.ProductID = products.ProductID and products.CategoryID = categories.CategoryID and CategoryName = "Beverages");

/* 12.	Selezionare il prodotto più costoso*/
select * from products order by UnitPrice desc limit 1;

/* 13.	Visualizzare l’importo totale di ciascun ordine fatto dal cliente 'Ernst Handel' (CompanyName)*/
select sum(UnitPrice) as ImportoTotale from customers, orders, `order details` where customers.CompanyName = 'Ernst Handel' and customers.CustomerID = orders.CustomerID and orders.OrderID = `order details`.OrderID;

/* 14.	Selezionare il numero di ordini ricevuti in ciascun anno */
select count(OrderID) as NumeroOrdini, year(OrderDate) as Anno from orders group by year(OrderDate);

/* 15.	Visualizzare per ogni impiegato (EmployeeID, LastName, FirstName) il numero di clienti distinti serviti per ciascun paese (Country),
visualizzando il risultato in ordine di impiegato e di paese*/
select employees.EmployeeID, LastName, FirstName, customers.Country, count(distinct customers.CustomerID) as NumeroClienti from employees, customers, orders where employees.EmployeeID = orders.EmployeeID and orders.CustomerID = customers.CustomerID group by employees.EmployeeID, Country order by employees.EmployeeID, Country;   

/* 16.	Visualizzare per ogni corriere il numero di consegne effettuate, compresi i dati dei 
corrieri che non hanno effettuato nessuna consegna */
select shippers.ShipperID, CompanyName, count(orders.OrderID) as NumeroConsegne from shippers left join orders on shippers.ShipperID = orders.ShipVia group by shippers.ShipperID;

/* 17.	Visualizzare i fornitori (SupplierID, CompanyName) che forniscono un solo prodotto */
select suppliers.SupplierID, CompanyName from suppliers, products where suppliers.SupplierID = products.SupplierID group by suppliers.SupplierID having count(products.ProductID) = 1;

/* 18.	Visualizzare tutti gli impiegati che sono stati assunti dopo Margaret Peacock */
select * from employees where HireDate > (select HireDate from employees where LastName = 'Peacock' and FirstName = 'Margaret');

/* 19.	Visualizzare gli ordini relativi al prodotto più costoso */
select * from orders, `order details` where orders.OrderID = `order details`.OrderID and `order details`.UnitPrice = (select max(UnitPrice) from `order details`);

/* 20.	Visualizzare i nomi dei clienti per i quali l’ultimo ordine è relativo al 1998  */
select CompanyName from customers, orders where customers.CustomerID = orders.CustomerID and year(OrderDate) = 1998 and OrderDate = (select max(OrderDate) from orders where customers.CustomerID = orders.CustomerID);

/* 21.	Contare il numero di clienti che non hanno effettuato ordini */
select count(customers.CustomerID) as NumeroClienti from customers, orders where customers.CustomerID not in (select orders.CustomerID from orders);

/* 22.	Visualizzare il prezzo minimo, massimo e medio dei prodotti per ciascuna categoria */
select min(UnitPrice) as PrezzoMinimo, max(UnitPrice) as PrezzoMassimo, avg(UnitPrice) as PrezzoMedio, CategoryName from products, categories where products.CategoryID = categories.CategoryID group by CategoryName;

/* 23.	Selezionare i prodotti che hanno un prezzo superiore al prezzo medio dei prodotti forniti dallo stesso fornitore */
select * from products, suppliers where products.SupplierID = suppliers.SupplierID and UnitPrice > (select avg(UnitPrice) from products where products.SupplierID = suppliers.SupplierID);

/* 24.	Visualizzare, in ordine decrescente rispetto alla quantità totale venduta, i prodotti che hanno venduto una quantità 
totale superiore al prodotto ‘Chai’ */
select products.ProductName, sum(Quantity) as QuantitaTotale from products, `order details` where products.ProductID = `order details`.ProductID group by products.ProductID having QuantitaTotale > (select sum(Quantity) from products, `order details` where products.ProductID = `order details`.ProductID and ProductName = 'Chai') order by QuantitaTotale desc;

/* 25.	Visualizzare il nome dei clienti che hanno fatto almeno due ordini di importo superiore a 15000 */ /* NON RESTITUISCE NULLA */
select CompanyName from customers, orders where customers.CustomerID = orders.CustomerID and orders.OrderID in (select orders.OrderID from orders, `order details` where orders.OrderID = `order details`.OrderID and UnitPrice * Quantity > 15000 group by orders.OrderID having count(orders.OrderID) >= 2);

/* 26.	Individuare i codici dei clienti che hanno fatto un numero di ordini pari a quello del cliente 'Eastern Connection' */
select customers.CustomerID from customers, orders where customers.CustomerID = orders.CustomerID group by customers.CustomerID having count(orders.OrderID) = (select count(orders.OrderID) from customers, orders where customers.CustomerID = orders.CustomerID and CompanyName = 'Eastern Connection');

/* 27. Visualizzare il numero di ordini ricevuti nel 1997 e di importo superiore a 10000*/
select * from orders, `order details` where orders.OrderID = `order details`.OrderID and year(OrderDate) = 1997 and UnitPrice * Quantity > 10000;

/* 28. Visualizzare i corrieri che hanno consegnato un numero di ordini superiore al numero di ordini consegnati dal supplier Speedy Express. */
select shippers.ShipperID, CompanyName from shippers, orders where shippers.ShipperID = orders.ShipVia group by shippers.ShipperID having count(orders.OrderID) > (select count(orders.OrderID) from shippers, orders where shippers.ShipperID = orders.ShipVia and CompanyName = 'Speedy Express');

/* 29. Visualizzare i clienti che hanno ordinato prodotti di tutte le categorie */
select * from customers where customers.CustomerID in (select customers.CustomerID from customers, orders, `order details`, products, categories where customers.CustomerID = orders.CustomerID and orders.OrderID = `order details`.OrderID and `order details`.ProductID = products.ProductID and products.CategoryID = categories.CategoryID group by customers.CustomerID having count(distinct categories.CategoryID) = (select count(CategoryID) from categories));
