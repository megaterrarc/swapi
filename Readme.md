
***Build App***
  
  - Requered
    
    Java 11
    
    Maven

    Docker
    
  - Compile
    
    mvnw clean install
  
***Init App***
 
 docker-compose -f ./compose.yaml

***Documentation***

http://localhost:8080/swagger-ui/index.html#/


*** Bugs ***

Comentado teste onde não passa devido a api não filtrar por name. Poderia fazer funcionar, porem, o tempo de execução vai ficar grande e esbarrando no rate limit da api.

![image](https://user-images.githubusercontent.com/9610355/128448781-84941c0f-7c7a-4329-a7a6-f5f993ba601e.png)
