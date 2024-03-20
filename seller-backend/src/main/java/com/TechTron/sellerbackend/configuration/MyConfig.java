package com.TechTron.sellerbackend.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;

@Configuration
public class MyConfig {

//    Without the AWSS3Config class, Spring cannot find a bean of type AmazonS3 to inject into your service. This is why you encounter the error when directly using @Autowired with private AmazonS3 amazons3;

//    AmazonS3:    Represents a client object for interacting with Amazon Simple Storage Service (S3).
//                  Provides methods for various S3 operations like uploading, downloading, listing, and managing objects (files) in S3 buckets.

//    The AmazonS3ClientBuilder acts as a factory that creates and configures instances of the AmazonS3 client.AmazonS3ClientBuilder facilitates the creation and configuration of AmazonS3 objects, enabling you to interact with Amazon S3 services.
//    AmazonS3ClientBuilder facilitates the creation and configuration of AmazonS3 objects, enabling you to interact with Amazon S3 services.

//    Analogy:
//
//    Imagine building a car (the AmazonS3 object):
//
//    You use a toolkit (the AmazonS3ClientBuilder) with various tools (configuration options) to assemble the car according to your specifications (credentials, region, etc.).
//    Once the car is built, you drive it (use the methods provided by the AmazonS3 object) to interact with the road (S3).





//    The @Bean annotation method indicates that it should create and return a bean of type AmazonS3
    @Bean
    public AmazonS3 getAmazonS3Cient() {

        String secretAccessKey = "jkn";
        String accessKeyId = "bnmb";
        String region = "vfd";

        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        // Get AmazonS3 client and return the s3Client object.

        return AmazonS3ClientBuilder
                .standard()  //sets the default configuration for the client.
                .withRegion(Regions.fromName(region))       //specifies the desired region
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)) //provides the client with AWS credentials using the BasicAWSCredentials object.
                .build();       //method builds and returns the configured AmazonS3 client object.
    }


//    This solved == [org.springframework.web.HttpMediaTypeNotSupportedException: Content-Type 'application/octet-stream' is not supported]
    public MyConfig(MappingJackson2HttpMessageConverter converter) {
        var supportedMediaTypes = new ArrayList<>(converter.getSupportedMediaTypes());
        supportedMediaTypes.add(new MediaType("application", "octet-stream"));
        converter.setSupportedMediaTypes(supportedMediaTypes);
    }


    // Here the same code can be done manually without @Bean annotation by putting the same code in the main class
    // By having in the main class :
    //              You'll need to manually pass this object to the appropriate Springdoc configuration or component
    //              You cannot use dependency injection to access or inject this object into other parts of your application since it's not a managed bean.

    //By Writing in a configuration class (using @Bean):
    //              The @Bean annotation registers the customOpenAPI() method as a bean in the Spring application context. Spring manages its
    //              lifecycle (creation, destruction) and makes it available for injection.

    //              Springdoc automatically detects and uses the bean registered by the customOpenAPI() method due to its presence in the Spring context.
    //              This simplifies integration and reduces the risk of errors.

    //              You can inject this bean using constructor injection or method injection in other parts of your application,
    //              making it easier to reuse and manage the configuration.
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Craft-Of-Ceylon Seller API: Manage Seller and their data") // Set the API title
                        .description("Manage Seller and their data efficiently.") // Add a description
                        .version("1.0.0") // Specify the API version
                        .contact(new Contact().email("support@craft-of-ceylon.com").name("Craft-Of-Ceylon Support Team"))
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation().description("API Documentation").url("https://docs.craft-of-ceylon.com/api"));
    }
}










//@Configuration
//public class AWSS3Config {
//
//    // Access key id will be read from the application.properties file during the application intialization.
//    private String accessKeyId="AKIATFUCC3MSIMJS233K";
//    // Secret access key will be read from the application.properties file during the application intialization.
////    @Value("${aws.secret_access_key}")
//    private String secretAccessKey="tN4IaqUgBrHohiEfDCQWUhFBjMWDpYR2SWPz13Ev";
//    // Region will be read from the application.properties file  during the application intialization.
//    private String region="ap-south-1";
//
//    @Bean
//    public AmazonS3 getAmazonS3Cient() {
//        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
//        // Get AmazonS3 client and return the s3Client object.
//        return AmazonS3ClientBuilder
//                .standard()
//                .withRegion(Regions.fromName(region))
//                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
//                .build();
//    }
//}
