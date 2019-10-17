package metricasgcp;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClientBuilder;
import com.amazonaws.services.elasticmapreduce.model.AddJobFlowStepsRequest;
import com.amazonaws.services.elasticmapreduce.model.AddJobFlowStepsResult;
import com.amazonaws.services.elasticmapreduce.model.ClusterSummary;
import com.amazonaws.services.elasticmapreduce.model.DescribeStepRequest;
import com.amazonaws.services.elasticmapreduce.model.DescribeStepResult;
import com.amazonaws.services.elasticmapreduce.model.HadoopJarStepConfig;
import com.amazonaws.services.elasticmapreduce.model.Instance;
import com.amazonaws.services.elasticmapreduce.model.ListInstancesRequest;
import com.amazonaws.services.elasticmapreduce.model.ListInstancesResult;
import com.amazonaws.services.elasticmapreduce.model.StepConfig;
import com.amazonaws.services.elasticmapreduce.model.StepStatus;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import metricas.modelos.TableInfo;
import metricasgcp.modelos.CredencialGcp;
import metricasgcp.modelos.FolderStorage;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

public class MetricasgcpBO {

  private MetricasgcpDAO DB;

  public MetricasgcpBO() {
    DB = new MetricasgcpDAO();
  }

  public String getInfoDataproc() throws ClassNotFoundException, JSONException {
    JSONObject json = new JSONObject();

    BasicAWSCredentials auth = new BasicAWSCredentials(CredencialGcp.access_key_id, CredencialGcp.secret_access_key);
    AmazonElasticMapReduce dataproc = AmazonElasticMapReduceClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(auth))
            .withRegion(Regions.fromName(CredencialGcp.region))
            .build();

    List<ClusterSummary> clusters = dataproc.listClusters().getClusters();

    JSONObject jsCluster = new JSONObject();

    for (ClusterSummary cs : clusters) {
      if (cs.getStatus().getState().equals("WAITING")) {
        int totalInstance = 0;
        ListInstancesResult instances = dataproc.listInstances(new ListInstancesRequest().withClusterId(cs.getId()));
        for (Instance instance : instances.getInstances()) {
          totalInstance++;
        }

        jsCluster.put("count_instances", totalInstance);
        jsCluster.put("id", cs.getId());
        jsCluster.put("id2", CredencialGcp.dataproc_id);
        jsCluster.put("status", "activo");
        jsCluster.put("name", cs.getName());
        jsCluster.put("created", cs.getStatus().getTimeline().getCreationDateTime().toInstant());
        json.put("gcp", jsCluster);
      }
    }
    return json.toString();
  }

  public String getStorageInfo() throws JSONException {
    JSONObject json = new JSONObject();

    BasicAWSCredentials auth = new BasicAWSCredentials(CredencialGcp.access_key_id, CredencialGcp.secret_access_key);
    AmazonS3 storage = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(auth))
            .withRegion(Regions.fromName(CredencialGcp.region))
            .build();

    ListObjectsV2Result objectStorage = storage.listObjectsV2(CredencialGcp.bucket);
    List<S3ObjectSummary> objects = objectStorage.getObjectSummaries();

    ArrayList<FolderStorage> folders = new ArrayList<>();

    for (S3ObjectSummary ob : objects) {
      if (ob.getKey().contains("/") && ob.getSize() == 0) {
        folders.add(getFilesSizesByFolder(ob.getKey(), objects));
      }
    }

    JSONArray jsArrayFiles = new JSONArray();
    for (FolderStorage folder : folders) {
      JSONObject jsFile = new JSONObject();
      jsFile.put("folder", folder.getName());
      jsFile.put("count", folder.getArchivos());
      jsFile.put("size", (folder.getSize() > 1073741824) ? (folder.getSize() / 1073741824) + " GB"
              : (folder.getSize() / 1048576) + " MB");
      jsArrayFiles.add(jsFile);
    }
    json.put("files", jsArrayFiles);

    return json.toString();
  }

  private FolderStorage getFilesSizesByFolder(String name, List<S3ObjectSummary> objects) {
    FolderStorage folder = new FolderStorage(name, 0, 0);
    for (S3ObjectSummary ob : objects) {
      if (ob.getKey().contains(name)
              && ob.getKey().trim().length() > name.trim().length()
              && ob.getSize() > 0) {
        folder.setArchivos(folder.getArchivos() + 1);
        folder.setSize(folder.getSize() + ob.getSize());
      }
    }
    return folder;
  }

  public String getTotalRowTablesHive() throws ClassNotFoundException, JSONException, SQLException {
    ArrayList<TableInfo> lista = DB.getTotalRowTablesHive();

    JSONArray jsArrayTables = new JSONArray();

    for (TableInfo t : lista) {
      JSONObject jsCount = new JSONObject();
      jsCount.put("table", t.getName());
      jsCount.put("rows", t.getRows());
      jsArrayTables.add(jsCount);
    }
    return jsArrayTables.toString();
  }

  public String launchStepDataproc(String archivo, int iteraciones, int clusters, String clusterID, String tableName)
          throws ClassNotFoundException, SQLException, JSONException {
    BasicAWSCredentials auth = new BasicAWSCredentials(CredencialGcp.access_key_id, CredencialGcp.secret_access_key);
    AmazonElasticMapReduce dataproc = AmazonElasticMapReduceClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(auth))
            .withRegion(Regions.fromName(CredencialGcp.region))
            .build();

    String[] argsList = {archivo,
      Integer.toString(iteraciones),
      Integer.toString(clusters),
      tableName};

    HadoopJarStepConfig hadoopConfig1 = new HadoopJarStepConfig()
            .withJar("s3://scriptdataproc/KmeansPoints.jar")
            .withArgs(argsList);
    StepConfig myCustomJarStep = new StepConfig("KmeansRunJar", hadoopConfig1);

    AddJobFlowStepsResult result = dataproc.addJobFlowSteps(new AddJobFlowStepsRequest()
            .withJobFlowId(clusterID)
            .withSteps(myCustomJarStep));

    String tipoInstancias = "";
    ListInstancesResult instances = dataproc.listInstances(new ListInstancesRequest().withClusterId(clusterID));
    for (Instance instance : instances.getInstances()) {
      tipoInstancias += CredencialGcp.dataproc_type_node + " - ";
    }

    JSONObject json = new JSONObject();
    json.put("stepID", result.getStepIds().get(0));
    json.put("stepStatus", "PENDING");
    json.put("tipoInstancias", tipoInstancias);
    return json.toString();
  }

  public String checkStepByClusterIDJobID(String clusterID, String stepID)
          throws ClassNotFoundException, SQLException, JSONException {

    BasicAWSCredentials auth = new BasicAWSCredentials(CredencialGcp.access_key_id, CredencialGcp.secret_access_key);
    AmazonElasticMapReduce dataproc = AmazonElasticMapReduceClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(auth))
            .withRegion(Regions.fromName(CredencialGcp.region))
            .build();

    DescribeStepResult res = dataproc.describeStep(new DescribeStepRequest()
            .withClusterId(clusterID)
            .withStepId(stepID));
    StepStatus status = res.getStep().getStatus();

    JSONObject json = new JSONObject();
    json.put("stepStatus", status.getState().trim());
    if (status.getState().trim().equals("COMPLETED")) {
      long time = (status.getTimeline().getEndDateTime().getTime()
              - status.getTimeline().getStartDateTime().getTime()) / 1000;
      json.put("time", time);
    }
    return json.toString();
  }

  public String getDataClustering(String tableName)
          throws ClassNotFoundException, SQLException, IOException {
    BasicAWSCredentials auth = new BasicAWSCredentials(CredencialGcp.access_key_id, CredencialGcp.secret_access_key);
    AmazonS3 storage = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(auth))
            .withRegion(Regions.fromName(CredencialGcp.region))
            .build();

    String fileName = "resultado/resultado" + tableName + ".json";

    S3Object fullObject = storage.getObject(
            new GetObjectRequest("scriptdataproc", fileName));
    BufferedReader reader = new BufferedReader(
            new InputStreamReader(fullObject.getObjectContent()));
    StringBuilder sb = new StringBuilder();
    String line = null;
    while ((line = reader.readLine()) != null) {
      sb.append(line);
    }
    return sb.toString();
  }

}
