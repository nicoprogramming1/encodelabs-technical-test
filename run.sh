#!/bin/bash

echo "Compilando..."
docker compose build

echo "Levantando app..."
docker compose up -d

echo "Esperando que esté saludable..."
until [ "$(docker inspect -f '{{.State.Health.Status}}' encodelabs-api)" == "healthy" ]; do
  echo "paciencia..."
  sleep 5
done

echo "   APP Running"
echo ""
echo "   • API URL: http://localhost:8080/api"
echo "   • Swagger UI: http://localhost:8080/api/swagger-ui.html"
echo "   • Health Check: http://localhost:8080/api/actuator/health"
echo ""
echo "Para ver los logs: docker-compose logs -f"
echo "Para bajar el container: docker comppose down"