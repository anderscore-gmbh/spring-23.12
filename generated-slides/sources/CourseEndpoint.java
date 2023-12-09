@Endpoint // Registriert Klasse bei Spring WS
public class CourseEndpoint {

  private static final String NAMESPACE_URI = "http://www.anderscore.com/soap";

  @Autowired
  private CourseService courseService;

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetCourseRequest") // Zuordnung SOAP Request -> Handler
  @ResponsePayload // Marshalling des Response Bodies
  public GetCourseResponse getCountry(@RequestPayload GetCourseRequest request) { // Unmarshalling des Request Bodies
    Course course = courseService.findByTitle(request.getTitle());

    GetCourseResponse response = new GetCourseResponse();
    response.setCourse(course);

    return response;
  }
}
