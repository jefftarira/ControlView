package metricas;

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
import configuraciones.aws.CredencialesAwsDAO;
import configuraciones.aws.modelos.CredencialAws;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import metricas.modelos.FolderS3;
import metricas.modelos.Historico;
import metricas.modelos.PointCalifornia;
import metricas.modelos.TableInfo;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

public class MetricasBO {

  private MetricasDAO DB;

  public MetricasBO() {
    DB = new MetricasDAO();
  }

  public String getClustersInfo() throws ClassNotFoundException, SQLException, JSONException {
    JSONObject json = new JSONObject();

    CredencialesAwsDAO credenciales = new CredencialesAwsDAO();
    CredencialAws credencial = credenciales.getCredenciales();

    BasicAWSCredentials awsCreds = new BasicAWSCredentials(credencial.getAccess_key(), credencial.getSecret_key());
    AmazonElasticMapReduce emr = AmazonElasticMapReduceClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.fromName(credencial.getRegion()))
            .build();

    List<ClusterSummary> clusters = emr.listClusters().getClusters();

    JSONObject jsCluster = new JSONObject();

    for (ClusterSummary cs : clusters) {
      if (cs.getStatus().getState().equals("WAITING")) {

        int totalInstance = 0;
        ListInstancesResult instances = emr.listInstances(new ListInstancesRequest().withClusterId(cs.getId()));
        for (Instance instance : instances.getInstances()) {
          totalInstance++;
        }

        jsCluster.put("count_instances", totalInstance);
        jsCluster.put("id", cs.getId());
        jsCluster.put("status", "activo");
        jsCluster.put("name", cs.getName());
        jsCluster.put("created", cs.getStatus().getTimeline().getCreationDateTime().toInstant());
        json.put("aws", jsCluster);
      }
    }

    return json.toString();
  }

  public String getS3Info() throws ClassNotFoundException, SQLException, JSONException {
    JSONObject json = new JSONObject();

    CredencialesAwsDAO credenciales = new CredencialesAwsDAO();
    CredencialAws credencial = credenciales.getCredenciales();

    BasicAWSCredentials awsCreds = new BasicAWSCredentials(credencial.getAccess_key(), credencial.getSecret_key());
    AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.fromName(credencial.getRegion()))
            .build();

    ListObjectsV2Result objectS3 = s3.listObjectsV2(credencial.getBucket_s3());
    List<S3ObjectSummary> objects = objectS3.getObjectSummaries();

    ArrayList<FolderS3> folders = new ArrayList<>();

    for (S3ObjectSummary ob : objects) {
      if (ob.getKey().contains("/") && ob.getSize() == 0) {
        folders.add(getFilesSizesByFolder(ob.getKey(), objects));
      }
    }

    JSONArray jsArrayFiles = new JSONArray();
    for (FolderS3 folder : folders) {
      JSONObject jsFile = new JSONObject();
      jsFile.put("folder", folder.getName());
      jsFile.put("count", folder.getArchivos());
      jsFile.put("size", (folder.getSize() > 1073741824) ? (folder.getSize() / 1073741824) + " GB"
              : (folder.getSize() / 1048576) + " MB");
      jsArrayFiles.add(jsFile);
    }
    json.put("files_s3", jsArrayFiles);

    return json.toString();
  }

  private FolderS3 getFilesSizesByFolder(String name, List<S3ObjectSummary> objects) {
    FolderS3 folder = new FolderS3(name, 0, 0);
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

  public String getInfoHiveEmr() throws ClassNotFoundException, SQLException, JSONException {
    ArrayList<TableInfo> lista = DB.getTotalRowTablesAws();

    JSONArray jsArrayTables = new JSONArray();

    for (TableInfo t : lista) {
      JSONObject jsCount = new JSONObject();
      jsCount.put("table", t.getName());
      jsCount.put("rows", t.getRows());
      jsArrayTables.add(jsCount);
    }
    return jsArrayTables.toString();
  }

  public String getFileNameCountsAws() throws ClassNotFoundException, SQLException, JSONException {
    ArrayList<PointCalifornia> lista = DB.getFileNameCountsAws();

    JSONArray jsArray = new JSONArray();
    for (PointCalifornia p : lista) {
      JSONObject jsCount = new JSONObject();
      jsCount.put("file_name", p.getFile_name());
      jsCount.put("total", p.getCount());
      jsArray.add(jsCount);
    }
    return jsArray.toString();
  }

  public String launchStepEmr(String archivo, int iteraciones, int clusters, String clusterID, String tableName)
          throws ClassNotFoundException, SQLException, JSONException {
    CredencialesAwsDAO credenciales = new CredencialesAwsDAO();
    CredencialAws credencial = credenciales.getCredenciales();

    BasicAWSCredentials awsCreds = new BasicAWSCredentials(
            credencial.getAccess_key(),
            credencial.getSecret_key());

    AmazonElasticMapReduce emr = AmazonElasticMapReduceClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.fromName(credencial.getRegion()))
            .build();

    String[] argsList = {archivo,
      Integer.toString(iteraciones),
      Integer.toString(clusters),
      tableName};

    HadoopJarStepConfig hadoopConfig1 = new HadoopJarStepConfig()
            .withJar("s3://scriptemrkmeans/Kmeanspoints.jar")
            .withArgs(argsList);
    StepConfig myCustomJarStep = new StepConfig("KmeansRunJar", hadoopConfig1);

    AddJobFlowStepsResult result = emr.addJobFlowSteps(new AddJobFlowStepsRequest()
            .withJobFlowId(clusterID)
            .withSteps(myCustomJarStep));

    String tipoInstancias = "";
    ListInstancesResult instances = emr.listInstances(new ListInstancesRequest().withClusterId(clusterID));
    for (Instance instance : instances.getInstances()) {
      tipoInstancias += instance.getInstanceType() + " - ";
    }

    JSONObject json = new JSONObject();
    json.put("stepID", result.getStepIds().get(0));
    json.put("stepStatus", "PENDING");
    json.put("tipoInstancias", tipoInstancias);
    return json.toString();
  }

  public String checkStepByClusterIDJobID(String clusterID, String stepID)
          throws ClassNotFoundException, SQLException, JSONException {
    CredencialesAwsDAO credenciales = new CredencialesAwsDAO();
    CredencialAws credencial = credenciales.getCredenciales();

    BasicAWSCredentials awsCreds = new BasicAWSCredentials(
            credencial.getAccess_key(),
            credencial.getSecret_key());

    AmazonElasticMapReduce emr = AmazonElasticMapReduceClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.fromName(credencial.getRegion()))
            .build();

    DescribeStepResult res = emr.describeStep(new DescribeStepRequest()
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

  public String getClusteringDataCalifornia(String tableName)
          throws ClassNotFoundException, SQLException, IOException {
    CredencialesAwsDAO credenciales = new CredencialesAwsDAO();
    CredencialAws credencial = credenciales.getCredenciales();

    BasicAWSCredentials awsCreds = new BasicAWSCredentials(
            credencial.getAccess_key(),
            credencial.getSecret_key());

    AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.fromName(credencial.getRegion()))
            .build();
    String fileName = "resultado/resultado" + tableName + ".json";

    S3Object fullObject = s3.getObject(
            new GetObjectRequest("scriptemrkmeans", fileName));
    BufferedReader reader = new BufferedReader(
            new InputStreamReader(fullObject.getObjectContent()));
    StringBuilder sb = new StringBuilder();
    String line = null;
    while ((line = reader.readLine()) != null) {
      sb.append(line);
    }
    return sb.toString();
  }

  public int guardarHistorico(Historico his) throws SQLException, ClassNotFoundException {
    return DB.insertHistorial(his);
  }

  public String getListHistorico() throws ClassNotFoundException, SQLException, JSONException {
    ArrayList<Historico> lista = DB.getListHistorico();

    JSONArray jsLista = new JSONArray();
    for (Historico h : lista) {
      JSONObject obj = new JSONObject();
      obj.put("id", h.getId());
      obj.put("cloud", h.getCloud());
      obj.put("algoritmo", h.getAlgoritmo());
      obj.put("maquinas", h.getMaquinas());
      obj.put("registros", h.getRegistros());
      obj.put("fecha", h.getFecha().toInstant());
      obj.put("tiempo", h.getTiempo());
      jsLista.add(obj);
    }

    return jsLista.toString();
  }

}
