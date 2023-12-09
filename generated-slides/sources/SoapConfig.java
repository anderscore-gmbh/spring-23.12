@EnableWs // (1) Aktivierung von Spring WS
@Configuration
public class SoapConfig extends WsConfigurerAdapter {
  
  // (2) Registrierung eines Servlets zur Verarbeitung von SOAP Requests
  @Bean
  public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(applicationContext);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean(servlet, "/ws/*");
  }

  // (3) Bereitstellung des XML Schemas
  @Bean
  public XsdSchema courseSchema() {
    return new SimpleXsdSchema(new ClassPathResource("courseSchema.xsd"));
  }

  // (4) Automatische Generierung einer WSDL
  @Bean(name = "courses")
  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema courseSchema) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("CoursesPort");
    wsdl11Definition.setLocationUri("/ws");
    wsdl11Definition.setTargetNamespace("http://www.anderscore.com/soap");
    wsdl11Definition.setSchema(courseSchema);
    return wsdl11Definition;
  }
}
