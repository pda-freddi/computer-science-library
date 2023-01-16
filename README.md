# Computer Science Library

Computer Science Library is a web application that stores and manages records of books, articles and videos related to CS topics.

The motivation behind the project is that while I was looking for an idea to practice designing web applications with Java and Spring, I realized that my browser's bookmarks bar is filled with links to interesting and useful articles, books and videos related to computer science and software development. So I thought it'd be nice to have an application to store and retrieve those links efficiently and that's how Computer Science Library was born.

The application exposes a RESTful API to interact with the datasource, which consists of an embedded H2 database configured to save records in a local file.

## Technologies

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [H2 Database](https://www.h2database.com/html/main.html)
* [Hibernate ORM](https://hibernate.org/)
* [Maven](https://maven.apache.org/)

## How To Run Locally

To run this application you'll need to install [Java Development Kit (JDK)](https://www.oracle.com/br/java/technologies/downloads/) version 17 or later, if you don't have it installed already. With that requirement fulfilled, open a terminal and run the following commands to clone this repository and navigate to the application's root directory:

```
git clone https://github.com/Pedro-Freddi/computer-science-library.git
cd computer-science-library
```

Then use the following command to build and run this Spring Boot application with Maven if you're on Linux or macOS:

```
./mvnw spring-boot:run
```

Or this one if you're on Windows:

```
mvnw spring-boot:run
```

The application should now be available at ``localhost:4001/``. Refer to the [Endpoints](#endpoints) or [Usage Examples with the cURL tool](#usage-examples-with-the-curl-tool) sections to find out how to interact with it.

> :warning: By default, the database is initialized with data contained in the [data.sql](./src/main/resources/data.sql) file and changes made to the dataset (record creation, updates, deletion, etc.) will be discarded after the application is terminated, which guarantees that the database will always reflect the data.sql file. To change this behavior and persist the changes, locate the following properties in the [application.properties](./src/main/resources/application.properties) file and replace their values with the ones provided below.

```
spring.jpa.hibernate.ddl-auto=none
spring.jpa.defer-datasource-initialization=false
spring.sql.init.mode=never
```

You can access H2 database's console at ``localhost:4001/h2-console``. Use the following to log in:

```
JDBC url: jdbc:h2:file:~/data/library
Username: dbadmin
Password: dbadmin
```

The username, password and location where database files are stored can be changed in the [application.properties](./src/main/resources/application.properties) file.

## Endpoints

This section briefly documents the available endpoints.

### Record Retrieval

```
GET /books
GET /books/{id}
GET /articles
GET /articles/{id}
GET /videos
GET /videos{id}
```

These endpoints accept GET requests to retrieve all books, articles and videos or to retrieve a specific record by specifying an ID parameter in the URI.

The requests to retrieve all records of a given type can use `sort_by` and/or `asc` query parameters to specify how the list of results should be ordered:
* `sort_by` accepts one of these values: `title`, `author`, `category`, `numPages` (only for books), `readTime` (only for articles) or `length` (only for videos).  
* `asc` accepts the values true, for ascending order, and false for descending order.

If no query parameters are specified in the request, results will be ordered by ID in ascending order.

### Create New Records

```
POST /books
POST /articles
POST /videos
```

The request's body must contain the data for record creation. All fields must be present, except for the record ID, which is generated automatically. To create a new book record, for example, a POST request should be made to `/books` and its body should contain:

```
{
    "title": "Book title",
    "author": "Book author",
    "category": "Book category",
    "numPages": 500
}
```
  
On successful record creation, the new object will be sent back in the response's body.

### Update Records

```
PUT /books/{id}
PUT /articles/{id}
PUT /videos/{id}
```

Requests to update a record must specify the record's ID in the URI and send the updated entity (containing all fields, except ID) in the request's body. To update only specific fields, [check out the PATCH method](#patch-records). 

To update the article with an ID of 8, for example, a PUT request should be made to `/articles/8` and its body should contain:

```
{
    "title": "Updated title",
    "author": "Updated author",
    "category": "Article category",
    "readTime": 30
    "link": "https://example/path-to-article"
}
```

The updated record is returned in the response's body.

### Patch Records

```
PATCH /books/{id}
PATCH /articles/{id}
PATCH /videos/{id}
```

The PATCH method allows the user to update (patch) one or more fields of the record specified by the ID parameter in the URI. The difference between the PATCH and PUT methods is that the former is not guaranteed to be idempotent and doesn't require all fields of an entity to be sent in the request's body. 

To update only the title and link fields of the video record with an ID of 15, for example, a PATCH request should be made to `/videos/15`  and its body should contain:

```
{
    "title": "New title",
    "link": "https://example/path-to-video"
}
```

The updated (patched) record is returned in the response's body.


### Delete Records

```
DELETE /books/{id}
DELETE /articles/{id}
DELETE /videos/{id}
```

A record can be deleted by making a DELETE request specifying the record's type and ID in the URI. To delete the book with ID of 14, for example, a DELETE request should be made to the `/books/15` endpoint.

A message confirming that the record was deleted is sent back in the response's body.

### Search Records

```
GET /search/books
GET /search/articles
GET /search/videos
```

Search endpoints query the records of the corresponding type specified in the URI. 

Multiple query parameters can be used in any combination to refine the search results. The following table relates each endpoint and the parameters it accept.

| Endpoint | Query Parameters |
| :-----------: | :-----------: |
| /search/books | title, author, category |
| /search/articles | title, author, category, max_read_time |
| /search/videos | title, channel, category, max_length |

Observations: 
* `max_read_time` and `max_length` are specified in minutes.
* `category` must match one of the values specified in [Category.java](./src/main/java/com/pdafr/computer/science/library/enums/Category.java).
* Title, author and category are treated as case insensitive to perform the search.

## Usage Examples with the cURL tool

Retrieve the book with an ID of 4.
```
curl localhost:4001/books/4

{
    "id": 4,
    "title": "Eloquent Javascript",
    "author": "Marijn Haverbeke",
    "category": "PROGRAMMING_LANGUAGES",
    "numPages": 436
}
```

Search for articles containing "javascript" in the title, written by "Tania", belonging to the "API" categorys and with a max read time of 20 minutes.
```
curl localhost:4001/search/articles?title=javascript&author=tania&category=api&max_read_time=20

[
    {
        "id": 7,
        "title": "How to Connect to an API with JavaScript",
        "author": "Tania Rascia",
        "category": "API",
        "readTime": 20,
        "link": "https://www.taniarascia.com/how-to-connect-to-an-api-with-javascript/"
    }
]
```

Create a new video record.
```
curl -X POST -d "{ \"title\": \"Ryan Dahl: Original Node.js presentation\", \"channel\": \"stri8ted\", \"category\": \"PROGRAMMING_LANGUAGES\", \"length\": 49, \"link\": \"https://www.youtube.com/watch?v=ztspvPYybIY\" }" -H "Content-Type: application/json" localhost:4001/videos

{
    "id": 15,
    "title": "Ryan Dahl: Original Node.js presentation",
    "channel": "stri8ted",
    "category": "PROGRAMMING_LANGUAGES",
    "length": 49,
    "link": "https://www.youtube.com/watch?v=ztspvPYybIY"
}
```

Delete the video record with an ID of 15.
```
curl -X DELETE localhost:4001/videos/15

Video with ID 15 deleted successfully
```

## Future Improvements

* Build a user interface to facilitate the visualization and management of the dataset.
* Improve the robustness of input validation and error handling on endpoints.
* Implement an authentication mechanism to secure the endpoints that create/modify records.
* Deploy the application.
