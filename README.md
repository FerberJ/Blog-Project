[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=FerberJ_Blog-Project&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=FerberJ_Blog-Project)
# Blog-Project

## Quarkus starten
Navigate into the folder `blog-backend` and give the following command:
```shell
./mvnw quarkus:dev
```

For more information see documentation [here](blog-backend/README.md)

## Testing

There are two options to run the unit test.

First is in the Terminal. For that Quarkus has to run. 
tab `r` in the terminal and the test will run.

Successful test:
```
All 2 tests are passing (0 skipped), 2 tests were run in 1303ms. Tests completed at 20:37:14.

Press [r] to re-run, [o] Toggle test output, [:] for the terminal, [h] for more options>
```

Unsuccessful test:
```
1 test failed (1 passing, 0 skipped), 2 tests were run in 231ms. Tests completed at 20:40:52.

Press [r] to re-run, [o] Toggle test output, [:] for the terminal, [h] for more options>
```

For the second option you can open the page http://localhost:8080/q/dev-ui in your browser and navigate to the tab `Continuous Testing`. There all of the test will be visible.

# blog-backend

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/blog-backend-0.1-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Related Guides


## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)


# OpenApi
The YAML for how the OpenAPI should look like can be found under [./OpenAPI_Example.yaml](OpenAPI_Example.yaml)

# Validation

Functions of my Product:

I can Read all Blogs, search the content or title for a Blog and get a Blog by its id. 

![](/img/getBlogs.png) 
![](/img/getBlogsId.png)

Also I can Post and delete a Blog. If I Post, there is a Validation, so if the Title is to short, it will get an Error and in the Response body I will recive the detailed information about the error. 

![](/img/postBlogsError.png)

Similiar with posting a comment, it will throw an error if the comment is blank.

![](/img/postCommentError.png)

There are dto-classes for the blog and comment. The Validation is made there. For example the BlogDto:
```java
public interface BlogDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public class NewBlogDto {
        @NotBlank
        @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters long.")
        private String title;
        private String content;
    }
}
``````
--- 




# Berechtigungskonzept

| | **Admin** |**User w/ Account** | **User w/o Account**  |
| --- | ------ | --- | --- |
|GET blogs|x | x| x|
|POST blogs | x | x|- |
| GET blogs/id | x| x|x |
| DELETE blogs (Selber erstellt) | x| x| -|
| DELETE blogs (von anderen erstellt) | x| -| -|
| POST comments | x| x|- |
| PUT likedbyme |x | x| -|


## Testing

Erstellen der Benutzer für das Auth-Setup:
| Name | Rolle |
| ---|--- |
| alice | admin, user |
| bob | user |
|steve | user |

In der Datei BlogSystemTest.class, werden alle Varianten der Tabelle **Berechtigungskonzept** getestet. Ausser:
`DELETE blogs (Selber erstellt)` von User `User w/o Account`, da dieser keinen eigenen Blog erstellen kann.

Es wird immer der Statuscode in der Response getestet:
z.B.:
```java
@Test
@Order(1)
public void adminGet() {
    RestAssured.given().auth().oauth2(getAccessToken(ADMIN))
            .when().get("/blogs")
            .then()
            // Man möchte den Status-Code 200 erhalten
            .statusCode(200); 
```
**Tabelle mit den gewünschten Status-Code:**
| | **Admin** |**User w/ Account** | **User w/o Account**  |
| --- | ------ | --- | --- |
|GET blogs|200 | 200| 200|
|POST blogs | 201 | 201|401 |
| GET blogs/id | 200| 200|200|
| DELETE blogs (Selber erstellt) | 204| 204| -|
| DELETE blogs (von anderen erstellt) | 204| 403| 401|
| POST comments | 201| 201|401 |
| PUT likedbyme |201| 201| 401|

Die Tests wurden erfolgreich ausgeführt. Man musste einfach darauf achten, das immer ein Blog erstellt wird und dessen Id bekannt ist zum testen.

Dafür wurde die Methode `createBlog` erstellt. Die erstellt einen Blog und gibt die `id` zurück.
```java
    private String createBlog(String username) {
        RestAssured.given().auth().oauth2(getAccessToken(username))
                .contentType(ContentType.JSON)
                .body(jsonPayloadBlog)
                .when().post("/blogs");

        String response = RestAssured.given()
                .when()
                .auth()
                .oauth2(getAccessToken(username))
                .get("/blogs")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response()
                .asString();

        return JsonPath.from(response).getString("id[-1]");
    }
```