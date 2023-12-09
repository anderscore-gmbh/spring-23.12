@OpenAPIDefinition (info = 
    @Info(
              title = "Sample Resource",
              version = "1.0",
              description = "Sample API to demonstrate OpenAPI annotations",
              license = @License(name = "Apache 2.0", url = "http://foo.bar"),
              contact = @Contact(url = "http://www.anderscore.com", name = "Daniel Krämer", email = "daniel.kraemer@anderscore.com")
      )
)
public class SampleResource {

    @GET
    @Operation(summary = "Get all users", description = "Get all persisted anderScore users")
    public Response getUsers() {
        // ...
    }
}
