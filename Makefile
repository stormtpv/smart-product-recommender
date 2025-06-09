# Makefile for Smart Product Recommender

MVNW = $(shell if exist mvnw.cmd (echo mvnw.cmd) else echo ./mvnw)

# Local build and test
build:
	$(MVNW) clean install -DskipTests

test:
	$(MVNW) test

# Docker build and run
docker-up:
	docker-compose up --build

docker-down:
	docker-compose down

# Clean project
clean:
	$(MVNW) clean
