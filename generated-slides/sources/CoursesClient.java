public class CoursesClient extends WebServiceGatewaySupport {

  private static final Logger log = LoggerFactory.getLogger(CoursesClient.class);

// tag::client[]
  public Course getCourse(String title) {
    GetCourseRequest request = new GetCourseRequest();
    request.setTitle(title);

    log.info("Requesting course with title " + title);

    GetCourseResponse response = (GetCourseResponse) getWebServiceTemplate()
        .marshalSendAndReceive("http://localhost:8080/ws/courses", request,
            new SoapActionCallback(
                "http://www.anderscore.com/soap/GetCourseRequest"));

    return response.getCourse();
  }
// end::client[]
}
