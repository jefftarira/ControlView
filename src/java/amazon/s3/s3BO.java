package amazon.s3;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class s3BO {

  Region region = Region.US_WEST_2;
  S3Client s3 = S3Client.builder()
                      .serviceConfiguration(cnsmr)
                       .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                       .build();
}
