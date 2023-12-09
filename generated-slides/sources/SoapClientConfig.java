@Configuration
public class CountryConfiguration {

// tag::config[]
  // (1) Bereitstellung eines (Un-)Marshallers zur Verarbeitung von SOAP Request und Response
  @Bean
  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setContextPath("com.anderscore.soap");

    return marshaller;
  }

  // (2) Definition des Clients
  @Bean
  public CourseClient courseClient(Jaxb2Marshaller marshaller) {
    CourseClient client = new CourseClient();
    client.setDefaultUri("http://localhost:8080/ws");
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);

    return client;
  }
// end::config[]
}
