hotel-be-bff-springboot
hotel-ui-angular
hotel-ui-react
hotel-ms-housekeeping-springboot
hotel-ms-frontdesk-springboot
hotel-ms-kitchen-springboot
hotel-ms-housekeeping-springboot
hotel-ms-maintenance-springboot

This is the opening project of a Hotel Management System.
This is the BFF or Backend-For-Frontend written in Java and Spring Boot.
As the description lists above, we could have an angular or react UI, or both.
The UI would be separate applications capable of being deployed on their own.
The rest are microservices which arw based on a domain-driven design.
Communication between these services will be done with Apache Kafka.
Authentication will be done with Keycloak which is already in Docker Container.
The database will be in a MySQL Server and its own schema, this is already in a Docker container.
All the parts of this will reside in Docker Containers, and will use Kubernetes
to allow the services to be discovered.
Database Migration will be shared with Flyway, and this will be the case for each
microservice.

There will be a Blog within GitHub to document the creation of this whole system.