curl -v -X POST "localhost:8080/rest/orders" \
     -H "Content-Type: application/json" \
     -d '{
           "address" : "Tervuursevest 50, 3000 Leuven",
           "mealIds" :
             [
               "a",
               "b",
               "c"
             ]
         }'
