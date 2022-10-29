# Computer Science Library

Computer Science Library is a web application that stores and manages records of books, articles and videos related to CS topics.

The motivation behind the project is that while I was looking for an idea to practice designing web applications with Java and Spring, I realized that my browser's bookmarks bar is filled with links to articles, books and videos related to computer science and software development. So I thought it'd be nice to have an application to store and retrieve those links efficiently and that's how Computer Science Library was born.

The application exposes a REST API to interact with the datasource, which consists of an embedded H2 database configured to save records in a local file.

## Technologies

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [H2 Database](https://www.h2database.com/html/main.html)
* [Hibernate ORM](https://hibernate.org/)
* [Maven](https://maven.apache.org/)

## Endpoints

This section briefly documents the available endpoints.

```
/books
/articles
/videos
```

These endpoints accept GET and POST requests to retrieve all records and create new records, respectively.

GET requests can use `sort_by` and `asc` query parameters to specify how the results should be ordered:
* `sort_by` accepts one of these values: `title`, `author`, `category`, `numPages` (for books), `readTime` (for articles) or `length` (for videos).  
* `asc` accepts the values true, for ascending order, and false for descending order.

If no query parameters are specified in the request, results will be ordered by ID in ascending order.  

POST requests must send the record object in the request body.

```
/books/{id}
/articles/{id}
/videos/{id}
```

Accept PUT and DELETE requests to update and delete, respectively, the record specified by the ID path parameter.

```
/search
/search/books
/search/articles
/search/videos
```

Search endpoints accept only GET requests.  

Multiple query parameters can be specified in any combination to refine the search results:

| Endpoint | Query Parameters |
| :-----------: | :-----------: |
| /search | category, title |
| /search/books | title, author, category |
| /search/articles | title, author, category, max_read_time |
| /search/videos | title, author, category, max_length |

Observations: 
* `read_time` and `length` are specified in minutes.
* `category` must match one of the values specified in [Category.java](./src/main/java/com/pdafr/computer/science/library/enums/Category.java).
* Title, author and category are treated as case insensitive to perform the search.

## Examples

```
curl localhost:4001/books/4

{
    "id": 4,
    "title": "Eloquent Javascript",
    "author": "Marijn Haverbeke",
    "category": "PROGRAMMING_LANGUAGES",
    "numPages": 436
}

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

curl -X POST -d "{ \"title\": \"Ryan Dahl: Original Node.js presentation\", \"channel\": \"stri8ted\", \"category\": \"PROGRAMMING_LANGUAGES\", \"length\": 49, \"link\": \"https://www.youtube.com/watch?v=ztspvPYybIY\" }" -H "Content-Type: application/json" localhost:4001/videos

{
    "id": 15,
    "title": "Ryan Dahl: Original Node.js presentation",
    "channel": "stri8ted",
    "category": "PROGRAMMING_LANGUAGES",
    "length": 49,
    "link": "https://www.youtube.com/watch?v=ztspvPYybIY"
}

curl -X DELETE localhost:4001/videos/15

Video with ID 15 deleted successfully

```

## Future Improvements

* Build a front-end to facilitate the visualization and management of the dataset.