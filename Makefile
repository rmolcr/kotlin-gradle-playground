GROUP_NAME:=$$(gradle properties -q | awk '/^group:/ {print $$2}')
APP_NAME:=$$(gradle properties -q | awk '/^name:/ {print $$2}')
APP_VERSION:=$$(gradle properties -q | awk '/^version:/ {print $$2}')

run:
	java -jar -Dspring.profiles.active=$(ACTIVE_PROFILES) app.jar

run-gradle:
	gradle bootRun --args='--spring.profiles.active=$(ACTIVE_PROFILES)'

clean:
	gradle clean

docker-build: Dockerfile
	gradle clean assemble
	docker build \
		-t $(GROUP_NAME)/$(APP_NAME):$(APP_VERSION) \
		--no-cache \
		--progress=plain \
		.

docker-run:
	docker run \
		-p 8080:8080 \
		-e "ACTIVE_PROFILES=h2" \
		$(GROUP_NAME)/$(APP_NAME):$(APP_VERSION)

docker-run-postgresql: docker-compose.yml
	docker compose up

docker-clean:
	docker rmi $(GROUP_NAME)/$(APP_NAME):$(APP_VERSION) --force
	docker compose rm --force
