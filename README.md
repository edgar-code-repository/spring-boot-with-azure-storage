SPRING BOOT WITH AZURE STORAGE
---------------------------------------------------------------------------

Spring Boot REST API that retrieves files from an Azure Storage account.

---------------------------------------------------------------------------

Gradle dependency used to work with Azure:

```
  implementation 'com.azure.spring:spring-cloud-azure-starter-storage'
```

---------------------------------------------------------------------------

Configuration class that generates the instance of BlobServiceClient
needed to interact with the storage container:

```
  package com.example.demo.configuration;

  import com.azure.storage.blob.BlobServiceClient;
  import com.azure.storage.blob.BlobServiceClientBuilder;
  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;

  @Configuration
  public class BlobStorageConfiguration {

    @Value("${connectionString}")
    private String connectionString;

    @Bean
    public BlobServiceClient getBlobServiceClient() {
        return new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
    }

  }

```

The connection string used in the previous class is retrieved from an environment variable
(application.properties):

```
  connectionString=${AZURE_STORAGE_CONNECTION_STRING}
```

---------------------------------------------------------------------------
