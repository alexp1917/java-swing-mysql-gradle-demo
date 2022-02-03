# java-swing-mysql-gradle-demo

## Usage:

```bash
docker run -d --name jsmg_demo --rm -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 mysql
./gradlew run
```

## Other notes

Run the `DataLayer` class's `main` to generate data in the database.
