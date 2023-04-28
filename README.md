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