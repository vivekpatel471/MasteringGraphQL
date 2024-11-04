/*
 * Copyright 2020 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import { ApolloServer } from 'apollo-server';
import { ApolloGateway,IntrospectAndCompose } from "@apollo/gateway";


const subgraphs = [
    { name: 'accounts', url: 'http://localhost:8899/graphql' },
    // Uncomment and modify as necessary for additional services
    // { name: 'clients', url: 'http://localhost:8081/graphql' },
];

const gateway = new ApolloGateway({
    supergraphSdl: new IntrospectAndCompose({
        subgraphs: subgraphs
    })
});

const server = new ApolloServer({
    gateway,
    subscriptions: false,
    tracing: true
});

// Define a default port
const port = process.env.PORT || 4000;

server.listen({ port }).then(({ url }) => {
    console.log(`🚀 Server ready at ${url}`);
    console.log(`Subgraphs:`);
    subgraphs.forEach(subgraph => {
        console.log(`- ${subgraph.name} at ${subgraph.url}`);
    });
});