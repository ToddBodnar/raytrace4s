## doesn't work yet, have to make fat jar + compile before
mvn install dependency:copy-dependencies
mvn package
mv target/*.jar dist/
mv target/dependency/ dist/lib/
