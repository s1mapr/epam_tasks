The system should be extended to expose the following REST APIs:
1. Change single field of gift certificate (e.g. implement the possibility to change only duration of a certificate or only price).
2. Add new entity User.
    * implement only get operations for user entity.
3. Make an order on gift certificate for a user (user should have an ability to buy a certificate).
4. Get information about user’s orders.
5. Get information about user’s order: cost and timestamp of a purchase.
    * The order cost should not be changed if the price of the gift certificate is changed.
6. Get the most widely used tag of a user with the highest cost of all orders.
    * Create separate endpoint for this query.
    * Demonstrate SQL execution plan for this query (explain).
7. Search for gift certificates by several tags (“and” condition).
8. Pagination should be implemented for all GET endpoints. Please, create a flexible and non-erroneous solution. Handle all exceptional cases.
9. Support HATEOAS on REST endpoints.
