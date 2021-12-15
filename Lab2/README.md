# Practical work #2: Spring

## Task 1: Model, Database, etc.

Similar to that of previous practical work.

## Task 2: Data layer

This time data layer is implemented using CRUD repositories allowing for less code to
be written.

## Task 3: Business layer

Business layer is implemented using Spring Services. 
Though there is not much business or logic... 

## Task 4: View layer

Nothing much has changed, now we use annotations from Spring.


# Practical work #3: RESTful web-service

## Task 1: SpringREST

We chose SpringREST as we expect it to be much more popular than JAX-RS. Apart from that, the 
application itself uses Spring here and there, why not continue using Spring for REST?

## Task2: Developing REST API

All the applications has already been developed with REST API in mind.

## Task 3: Implementing REST API

Same answer as in previous task. SpringREST by default allows for usage of both
XML and JSON.

## Task 4-6: XSL transformation

We have added a new method for `getAll` request which processes case, when
user supplies `accept` header with `application/xml` value. It uses a magical
converter that transforms XML into beautiful HTML.

![XSLT](images/xslt.png)


# Practical work 4: JMS

## Task 1: New database table

We created the following table:

![Watcher table](images/watcher_table.png)

## Task 2-6: Implementing JMS

We used Spring tools in order to implement JMS in our application:
`JmsTemplate` and `@JmsListener` annotation.

The following classes were created:

![JMS](images/jms.png)

Sender class is used to send messages to the Receiver which writes them into
the database. Each servlet calls the corresponding method of Sender during
`PUT`, `POST` and `DELETE` requests and provides name of the entity that is being changed
and its new value. Sender then creates an Event instance that encapsulates
all the necessary information and sends it to the Receiver. Receiver 
first writes event into the events table using the corresponding service,
then extracts necessary portion of information, puts it into an Email object
and writes in into the corresponding table of emails (see picture below) using the appropriate
service.

![Email](images/email.png)
