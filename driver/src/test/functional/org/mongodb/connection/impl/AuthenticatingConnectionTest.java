/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mongodb.connection.impl;


import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mongodb.MongoCredential;
import org.mongodb.connection.ServerAddress;

import java.util.Arrays;
import java.util.Collections;

import static org.mongodb.Fixture.getBufferProvider;
import static org.mongodb.Fixture.getPrimary;
import static org.mongodb.Fixture.getSSLSettings;

public class AuthenticatingConnectionTest {
    private Connection connection;
    private String userName;
    private String password;
    private String source;
    private ServerAddress serverAddress;

    @Before
    public void setUp() throws Exception {
        userName = System.getProperty("org.mongodb.test.userName", "bob");
        password = System.getProperty("org.mongodb.test.password", "pwd123");
        source = System.getProperty("org.mongodb.test.source", "admin");
        serverAddress = new ServerAddress(System.getProperty("org.mongodb.test.serverAddress", getPrimary().toString()));

        ConnectionFactory connectionFactory = new DefaultConnectionFactory(new SocketStreamFactory(SocketSettings.builder().build(),
                getSSLSettings()), getBufferProvider(), Collections.<MongoCredential>emptyList());
        connection = connectionFactory.create(serverAddress);
    }

    @After
    public void tearDown() throws Exception {
        connection.close();
    }

    @Test
    @Ignore
    public void testMongoCRAuthentication() throws InterruptedException {
        ConnectionFactory connectionFactory = new DefaultConnectionFactory(new SocketStreamFactory(SocketSettings.builder().build(),
                getSSLSettings()), getBufferProvider(), Arrays.asList(MongoCredential.createMongoCRCredential(userName, source,
                password.toCharArray())));
        connection = connectionFactory.create(serverAddress);
    }

    @Test
    @Ignore
    public void testPlainAuthentication() throws InterruptedException {
        ConnectionFactory connectionFactory = new DefaultConnectionFactory(new SocketStreamFactory(SocketSettings.builder().build(),
                getSSLSettings()), getBufferProvider(), Arrays.asList(MongoCredential.createPlainCredential(userName, source,
                password.toCharArray())));
        connection = connectionFactory.create(serverAddress);
    }

    @Test
    @Ignore
    public void testGSSAPIAuthentication() throws InterruptedException {
        ConnectionFactory connectionFactory = new DefaultConnectionFactory(new SocketStreamFactory(SocketSettings.builder().build(),
                getSSLSettings()), getBufferProvider(), Arrays.asList(MongoCredential.createGSSAPICredential(userName)));
        connection = connectionFactory.create(serverAddress);
    }

    @Test
    @Ignore
    public void testMongoX509Authentication() throws InterruptedException {
        ConnectionFactory connectionFactory = new DefaultConnectionFactory(new SocketStreamFactory(SocketSettings.builder().build(),
                getSSLSettings()), getBufferProvider(), Arrays.asList(MongoCredential.createMongoX509Credential(
                "emailAddress=root@lazarus,CN=client,OU=Kernel,O=10Gen,L=New York City,ST=New York,C=US")));
        connection = connectionFactory.create(serverAddress);
    }
}