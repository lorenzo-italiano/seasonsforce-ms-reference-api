mvn clean install -Dmaven.test.skip

mv target/seasonsforce-ms-reference-api-1.0-SNAPSHOT.jar api-image/seasonsforce-ms-reference-api-1.0-SNAPSHOT.jar

cd api-image

docker build -t reference-api .

cd ../postgres-image

docker build -t reference-db .
