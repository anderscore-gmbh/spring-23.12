@SpringBootTest
public class CourseEndpointMockTest {

    @Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @BeforeEach
    public void init() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

// tag::test[]
    @Test
    public void testGetCourse_Exists() throws IOException {
        Source requestPayload = new StringSource(
                "<as:getCourseRequest xmlns:as=\"http://www.anderscore.com/soap\">" +
                        "<as:id>4711</as:id>" +
                        "</as:getCourseRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getCourseResponse xmlns:ns2=\"http://www.anderscore.com/soap\">" +
                        "<ns2:course><ns2:id>4711</ns2:id><ns2:title>Spring</ns2:description></ns2:task>" +
                        "</ns2:getCourseResponse>");

        Resource xsdSchema = new ClassPathResource("xsd/courses.xsd");

        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(noFault())
                .andExpect(payload(responsePayload))
                .andExpect(validPayload(xsdSchema));
    }
// end::test[]

    @Test
    public void testGetCourse_NotExists() {
        Source requestPayload = new StringSource(
                "<as:getCourseRequest xmlns:as=\"http://www.anderscore.com/soap\">" +
                        "<as:id>1337</as:id>" +
                        "</as:getCourseRequest>");

        mockClient
                .sendRequest(withPayload(requestPayload))
                .andExpect(serverOrReceiverFault());
    }
}
