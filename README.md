# Shwamera

<h1>Ingredients:</h1>
<br/>
GET /ingredient/{id}  – get ingredient details by id <br/>
Checks if exist and 200 or 404<br/>
<br/>
GET /ingredient – list All ingredients<br/>
<br/>
<h3>@Admin</h3>
POST /ingredient/create  + [body]  – contains ingredient stateful body (id ignored if present <br/>in body)
	Checks by name in service layer if such ingredient exist and<br/>
		If exist throw unprocessible entity<br/>
		Else saves and 401<br/>
DELETE /ingredient/delete/{id}     – delete ingredient from db or 404<br/>
	Checks if with such id exists <br/>
		If yes then 200<br/>
		Else throws 404<br/>
PUT /ingredient/update/{id} + [body] –  contains ingredient stateful valid body with valid id otherwise 404 returned<br/>
	Checks if with such id exists<br/>
		If yes then updates it 200<br/>
		Else then throws unprocessable Entity<br/>
<h1>Shaurma</h1>

GET /shaurma/{id} – get predefined shaurma details from menu<br/>
POST /shaurma + [body] – save the shaurma to the current order. Till the order is proceeded <br/>the shaurma is assigned with SecureRandom() key for identifying it in the current Order. Body contains set of ingredients: {CARROT, ONIEN, MEAT} Not saved to DB yet.
	401 with uri  /order/shaurma/{transient_shaurma_id}<br/>
DELETE /shaurma/{id} - delete predefined shaurma from the order<br/>
		
<h3>@Admin</h3><br/>
GET /shaurma    - list all shaurmas<br/>
	200<br/>
POST /shaurma/create   + [body]  - contains shaurma stateful body (id ignored if present in body).<br/> Saved directly to DB
	200 or if !@Validated accordingly<br/>

PUT /shaurma/update/{id}  + [body]  - update existent DB entity. Contains shaurma stateful <br/>body with valid id otherwise 404 returned
	Checks if entity with such {id} exists<br/>
		If yes, then update that and 200<br/>
		Else throws 404<br/>
DELETE /shaurma/delete/{id}    - delete ingredient from db otherwise 404 returned<br/>
<h1>Order</h1><br/>
GET /order – see current order<br/>
GET /order/shaurma/{transient_shaurma_id} – see the custom shaurma added to current order.<br/>
	Checks if current order contains shaurma with {..id} <br/>
		If yes then gets it<br/>
		Else 404<br/>
POST /order – order Saved to DB<br/>
	Check at SOAP server if current order is payed<br/>
		If yes then 200<br/>
		After timeout accepted and contact number/<br/>
<h3>@Admin</h3>
GET /order/{order number} show order info<br/>
GET /order/all – list of order, available only for shaurmamaker<br/>
