b: build
build:
	mvn clean install
local-pipeline: build
	mvn integration-test
