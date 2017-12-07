REST API

Product:

GET    /product/get/all           get set of all products
GET    /product/get/id/{id}       get product by id. id - path variable
GET    /product/get/name/{name}   get product by name. name - path variable
PUT    /product/add               add product into database. The json body must contains product object with set of ingredients
POST   /product/update            update product in database. The json body must contains product object with set of ingredients
DELETE /product/delete/{id}       delete product by id. id - path variable

Ingredient:

GET    /ingredients/get/all           get set of all ingredients
GET    /ingredients/get/id/{id}       get ingredient by id. id - path variable
GET    /ingredients/get/name/{name}   get ingredient by name. name - path variable
PUT    /ingredients/add               add ingredient into database. The json body must contains ingredient object
POST   /ingredients/update            update ingredient in database. The json body must contains ingredient object
DELETE /ingredients/delete/{id}       delete ingredient by id. id - path variable

Menu:

GET    /menu/get/all           get set of all menu
GET    /menu/get/id/{id}       get menu by id. id - path variable
GET    /menu/get/name/{name}   get menu by name. name - path variable
PUT    /menu/add               add menu into database. The json body must contains products list
POST   /menu/update            update menu in database. The json body must contains products list
DELETE /menu/delete/{id}       delete menu by id. id - path variable

Order:

GET    /order/get/all           get set of all orders
GET    /order/get/id/{id}       get order by id. id - path variable
GET    /order/get/name/{name}   get order by name. name - path variable
PUT    /order/add               add order into database. The json body must contains products list
POST   /order/update            update order in database. The json body must contains products list
DELETE /order/delete/{id}       delete order by id. id - path variable