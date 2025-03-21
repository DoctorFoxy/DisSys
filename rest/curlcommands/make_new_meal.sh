curl -v -X POST "localhost:8080/restrpc/meals" \
     -H "Content-Type: application/json" \
     -d '{
           "name" : "Giant Schnitzel Challenge",
           "kcal" : 3500,
           "price" : 40.0,
           "description" : "Biggest Schnitzel in the world",
           "mealType" : "MEAT"
         }'
