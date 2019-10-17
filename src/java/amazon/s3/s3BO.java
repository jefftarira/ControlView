package amazon.s3;

import amazon.Credenciales;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.util.List;

public class s3BO {

  public void printListFileBucket() {
    BasicAWSCredentials awsCreds = new BasicAWSCredentials(Credenciales.access_key_id, Credenciales.secret_access_key);

    final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.US_EAST_1)
            .build();

    List<Bucket> buckets = s3.listBuckets();

    System.out.println(
            "Your Amazon S3 buckets are:");
    for (Bucket b : buckets) {
      System.out.println("* " + b.getName());
    }

    System.out.println(
            "Your files are: ");

    ListObjectsV2Result result = s3.listObjectsV2(buckets.get(1).getName());
    List<S3ObjectSummary> objects = result.getObjectSummaries();
    for (S3ObjectSummary os : objects) {
      System.out.print("* " + os.getKey());
      System.out.print(" Tamaño: " + os.getSize());
      System.out.println(" última modificación:  " + os.getLastModified());
    }
  }

}
