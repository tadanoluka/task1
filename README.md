## Running the project

To run the project along with all necessary dependencies, you can use the provided `docker-compose.yml` file.

From the project's root directory, execute the following command:

```bash
docker-compose up -d
```

After the containers are successfully built, Swagger will be available at:
http://localhost:8088/swagger-ui.html (if you haven't changed the default ports)

If you want to stop the project, you can execute the following command in the same terminal:

```bash
docker-compose down
```
