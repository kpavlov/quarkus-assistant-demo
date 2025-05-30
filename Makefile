.PHONY: build test lint format all docs leaks # always run

build:
	mvn clean package

test:
	mvn verify

run-dev:
	mvn quarkus:dev

.PHONY: promptfoo
run-mcp:
	(cd mcp && mvn quarkus:dev)

.PHONY: promptfoo
promptfoo:
	(cd promptfoo && promptfoo eval --watch --output output.yml --no-progress-bar --suggest-prompts 2 --env-file ./.env)

.PHONY: promptfoo-ui
promptfoo-ui:
	(cd promptfoo && promptfoo view --yes --env-file ./.env)

lint:prepare
	ktlint "!**/target/**"

# https://docs.openrewrite.org/recipes/maven/bestpractices
format:prepare
	ktlint --format "!**/target/**"

prepare:
	command -v ktlint >/dev/null 2>&1 || brew install ktlint --quiet

all: format lint build

leaks:
	gitleaks git .

.PHONY: otel
otel:
	lsof -ti:8888 | xargs kill -9
	osascript -e 'tell app "Terminal" to do script "otel-tui"'
	#open -a Terminal otel-tui
	#otel-tui
