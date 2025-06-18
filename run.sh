#!/bin/bash

echo "Compilando y construyendo imagen..."
docker compose build

echo "Levantando la app..."
docker compose up -d encodelabs-api

echo "Esperando que la app esté healthy..."
until [ "$(docker inspect -f '{{.State.Health.Status}}' encodelabs-api)" == "healthy" ]; do
  echo "   → esperando health status!..."
  sleep 5
done

echo "API saludable, corriendo tests Newman..."
docker compose up --abort-on-container-exit postman-tests


exit_code=$?

# Resultado
if [ $exit_code -ne 0 ]; then
  echo "Tests fallaron"
else
  echo "Tests pasaron correctamente"
fi

echo "   APP Running!!"
echo ""
echo "   • API URL: http://localhost:8080/api"
echo "   • Swagger UI: http://localhost:8080/api/swagger-ui.html"
echo "   • Health Check: http://localhost:8080/api/actuator/health"
echo ""
echo "Para ver los logs: docker-compose logs -f"
echo "Para bajar el container: docker compose down"