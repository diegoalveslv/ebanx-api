build-run-compose:
	./gradlew build
	docker compose down -v
	docker compose build
	docker compose up -d