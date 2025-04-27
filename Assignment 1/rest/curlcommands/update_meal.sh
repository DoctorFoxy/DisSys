curl -v -X PUT "localhost:8080/restrpc/meals/b0034362-7491-49a2-ae09-9f172875fba9" \
     -H "Content-Type: application/json" \
     -d '{
           "id" : "b0034362-7491-49a2-ae09-9f172875fba9",
           "name" : "Giant Schnitzel Challenge",
           "kcal" : 4000,
           "price" : 45.0,
           "description" : "Biggest Schnitzel in the world",
           "mealType" : "MEAT"
         }'
