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
docker compose up -d postman-tests

echo "Logs de tests:"
docker compose logs -f postman-tests

exit_code=$(docker inspect postman-tests --format='{{.State.ExitCode}}')

if [ $exit_code -ne 0 ]; then
  echo "Tests fallaron"
else
  echo "Tests pasaron correctamente"
fi

echo ""
echo "   APP Running!!"
echo ""
echo "   • API URL products: http://localhost:8080/api/products"
echo "   • no funciona :( -> Swagger UI: http://localhost:8080/api/swagger-ui.html"
echo "   • Health Check: http://localhost:8080/api/actuator/health"
echo ""
echo "  Para ver los logs: docker-compose logs -f"
echo "  Para bajar el container: docker compose down"

tail -f /dev/null
