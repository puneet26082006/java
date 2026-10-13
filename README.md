# JavaMiniDB

A small command-line database written in Java. It supports creating tables, inserting rows, and selecting all rows. State is serialized to `database.dat` in the current working directory.

## Compile and run

Use a JDK with `javac` and `java` on your PATH. From the repository root:

```sh
mkdir -p out
javac -d out Column.java Database.java Main.java Row.java SQLParser.java Table.java
java -cp out Main
```

On Windows Command Prompt, use `mkdir out` in place of `mkdir -p out`. Compile the source before running; existing `.class` files in the repository root may be older than the source. The `out` classpath keeps those older files out of the run.

## Example session

```sql
CREATE TABLE games (id INT, title STRING, price INT)
INSERT INTO games VALUES (1, 'Minecraft', 30)
INSERT INTO games VALUES (2, 'Tetris', 10)
SELECT * FROM games
EXIT
```

Use uppercase SQL keywords for CREATE and INSERT. The parser supports `INT` and `STRING` columns and only `SELECT * FROM table` queries. It is an educational parser: quoted commas, escaped quotes, WHERE clauses, updates, and deletes are not supported.

`EXIT`, `QUIT`, or end-of-file saves the database before closing. You can also run the included sample:

```sh
java -cp out Main < commands.md
```

Run from the same working directory to load the same data. Creating a table whose name already exists reports an error and preserves its rows. Avoid rerunning INSERT statements if you do not want duplicate rows; uniqueness constraints are not implemented.

## Local files

`out/`, newly generated `.class` files, and `database.dat` are ignored to keep build output and local records out of future commits. Already tracked `.class` files are still in the repository; the compile/run instructions above use freshly built classes instead.

Keep a backup of important local data before experimenting. The serialized file is local application state, not a format for importing files from untrusted sources.
