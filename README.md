hotel-be-bff-springboot - https://github.com/tjholmes66/hotel-be-bff-springboot
hotel-ui-angular - https://github.com/tjholmes66/hotel-ui-angular
hotel-ui-react
hotel-be-ms-housekeeping-springboot
hotel-be-ms-frontdesk-springboot
hotel-be-ms-kitchen-springboot
hotel-be-ms-housekeeping-springboot
hotel-be-ms-maintenance-springboot
hotel-be-ms-payment-springboot

This is the opening project of a Hotel Management System.

The GitHub Pages on this project can be found:
https://tjholmes66.github.io/hotel-be-bff-springboot/

This is the BFF or Backend-For-Frontend written in Java and Spring Boot.
As the description lists above, we could have an angular or react UI, or both.
The UI would be separate applications capable of being deployed on their own.
The rest are microservices which are based on a domain-driven design.
Communication between these services will be done with Apache Kafka.
Authentication will be done with Keycloak which is already in Docker Container.
Oauth2 Authentication is Client, and the BFF is integrated with Keycloak.

The database will be in a MySQL Server and its own schema, this is already in a Docker container.
All the parts of this will reside in Docker Containers, and will use Kubernetes
to allow the services to be discovered.
Database Migration will be shared with Flyway, and this will be the case for each
microservice.

There will be a Blog within GitHub to document the creation of this whole system.