/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.camel.k.examples;

import org.apache.camel.PropertyInject;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.CreateStreamResult;
import com.amazonaws.services.kinesis.model.DescribeStreamResult;
import com.amazonaws.services.kinesis.model.ResourceNotFoundException;
import org.apache.camel.component.aws.kinesis.KinesisConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomKinesisConfiguration extends KinesisConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(CustomKinesisConfiguration.class);

    private AmazonKinesis amazonKinesis;

    @PropertyInject("amazon.host")
    private String amazonHost;

    static {
        /* This makes the Localstack response parseable but is not necessary
         * when using AWS Kinesis
         */
        System.setProperty("com.amazonaws.sdk.disableCbor", "true");
    }

    private void createStream() {
        CreateStreamResult result = amazonKinesis.createStream(getStreamName(), 1);
        if (result.getSdkHttpMetadata().getHttpStatusCode() != 200) {
            LOG.error("Failed to create the stream");
        } else {
            LOG.info("Stream created successfully");
        }
    }

    public void setAmazonHost(String amazonHost) {
        this.amazonHost = amazonHost;
    }

    public String getAmazonHost() {
        return amazonHost;
    }

    private AmazonKinesis buildClient() {
        LOG.info("Using Amazon host: {}", amazonHost);

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setProtocol(Protocol.HTTP);

        String region = Regions.US_EAST_1.getName();

        AmazonKinesisClientBuilder clientBuilder = AmazonKinesisClientBuilder.standard();
        clientBuilder
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonHost, region))
                .withClientConfiguration(clientConfiguration)
                .withCredentials(new AWSCredentialsProvider() {
                    public AWSCredentials getCredentials() {
                        return new AWSCredentials() {
                            public String getAWSAccessKeyId() {
                                return "accesskey";
                            }

                            public String getAWSSecretKey() {
                                return "secretkey";
                            }
                        };
                    }

                    public void refresh() {

                    }
                });

        LOG.info("Building the client");
        return clientBuilder.build();
    }

    @Override
    public AmazonKinesis getAmazonKinesisClient() {
        if (amazonKinesis == null) {
            amazonKinesis = buildClient();

            final String streamName = getStreamName();
            LOG.info("Checking if the stream {} exists", streamName);

            try {
                DescribeStreamResult describeStreamResult = amazonKinesis.describeStream(streamName);
                if (describeStreamResult.getSdkHttpMetadata().getHttpStatusCode() != 200) {
                    LOG.info("The stream does not exist, auto creating it ...");

                    createStream();
                } else {
                    LOG.info("The stream already exists, therefore skipping auto-creation");
                }
            } catch (ResourceNotFoundException e) {
                LOG.info("The stream does not exist, auto creating it ...");
                createStream();
            }
        }

        return amazonKinesis;
    }
}
