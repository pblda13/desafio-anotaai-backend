package com.pamela.desafioanotaai.config.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsSnsConfig {

    // Configuração para fornecer credenciais e informações de configuração para o Amazon SNS

    @Value("$(aws.region)")
    private String region;
    @Value("$(aws.accessKeyId)")
    private String accessKeyId;
    @Value("$(aws.secretKey)")
    private String secretKey;
    @Value("$(aws.sns.topic.catalog.arn)")
    private String  catalogTopicArn;

    // Configura e fornece uma instância do Amazon SNS

    @Bean
    public AmazonSNS amazonSNSBuilder(){
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretKey);

        return AmazonSNSClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    // Configura e fornece um tópico específico do Amazon SNS para eventos relacionados ao catálogo

    @Bean(name = "catalogEventsTopic")
    public Topic snsCatalogTopicBuilder(){
        return  new Topic().withTopicArn(catalogTopicArn);
    }
}
