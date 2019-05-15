# RestApi2Collections
Design and implement a REST API over two collections. The collections will be defined as follows: one 'buyers' resource and one 'transactions' resource.
Buyers will be defined as being of two types: corporate and individual buyers.
For the individual buyer, you must include at least the following fields:
id: ObjectId
buyerName: String
value: Numeric (sum of all transaction values)
dateRegistered: Date
buyerPersonalIdentification: String
transactions: Array<ObjectId>
For the corporate buyer, you must include at least the following fields:
id: ObjectId
buyerName: String
value: Numeric (sum of all transaction values)
address: String
companyIdentification: String
transactions: Array<ObjectId>
The transactions will be defined as follows:
id: ObjectId
transactionNumber: Numeric
value: Numeric
description: String
Tasks:
1. You are supposed to fully implement all CRUD operations on these resources.
2. For the 'buyers' resource you must ensure that no duplicate buyers can be inserted (hint: filter by
'buyerName'), and deleting a buyer must ensure the complete deletion of all transactions
associated to it.
*Bonus round:
Sketch an HTML + Javascript interface that can call and display the results from at least one endpoint.
Notes:
i) You are supposed to implement both tasks using the Java Programming Language, version 8+.
You are free to use any open source libraries or frameworks.
ii) You are free to choose any database server from: MongoDB, MySQL/MariaDB or PostgreSQL.
iii) You must upload the entire codebase on GitHub.
iv) Please include a short technical documentation in which you describe the classes you
designed, how they were implemented, any known bugs etc.
