# NOTE-IT
DESCRIPTION
Welcome to NoteIt, an all-in-one note-making and organization web-application. Helps you create , organize and manage your notes with ease. It's designed to help you stay productive, whether you are a student, profesional, or simply someone who loves to jot ideas and thoughts which you can further share with your team , friends or other users.




 Uses Spring framework with Java language

 Uses Hibernate ORM

 Uses Postgres Database that is running on my Local machine. (Database Connection Setup Guide below)

 The project database has three tables Users, Notes and Images. There exists a many to many relationship between Notes and Users.

 All three tables have Rest Api methods which can perfrom all CRUD operations.



Realtional Model

User Entity:
Attributes:
UserID (Primary Key): A unique identifier for each user.
Username: The username of the user for authentication and identification.
Password: The user's password (hashed for security).
Email: The user's email address for communication.
Note Entity:
Attributes:
NoteID (Primary Key): A unique identifier for each note.
Title: The title or subject of the note.
Content: The text content of the note.
CreationDate: The date the note was created.
ModificationDate: The date the note was last modified.
Description:
The "Note" entity represents individual notes created by users. Notes now include a "Folder" attribute to categorize and organize notes into folders. Users can create multiple notes and assign them to different folders.
User-Note Entity (Association Table for Many-to-Many Relationship):
Attributes:
UserID (Foreign Key): A reference to the user who is associated with the note.
NoteID (Foreign Key): A reference to the note that the user is associated with.
Description:
The "User-Note" entity represents the many-to-many relationship between users and notes.
Imaage Entity:
Attributes:
ImageID (Primary Key): A unique identifier for each image.
UploadDate: The date the image was uploaded.
Hiegth: Represents the hiegth of the image
Width: Represents the width of the image
NoteID: A reference to the note accociated with that image
ImageUrl: Represent an url for the image stored in notes
Description:
The "Image" entity represents images that can be attached to notes. Each image is associated with one or more notes.
Description of complex query
The query would allow the user to filter and search for a particular word in notes including shared ones.
Key Features:
Parameterized Search: You can specify a word, and the query will find all notes where that word appears within the content. The word you're searching for is a parameter you provide to the query.
Partial Matching: Unlike exact-match searches, our query employs a partial matching approach. This means you don't need to enter the entire word, and you'll still find relevant notes.
Case Insensitivity: The search is designed to be case-insensitive, so it doesn't matter if the word you're looking for is in uppercase or lowercase. The query treats all text uniformly, ensuring a user-friendly search experience.
Description of complex business logic
Adding an image to a note which means also updating note.modificationDate: using the following requests: 1) the image is assigned to the note, 2) the modificationDate is updated to the current date

The project has a JPQL query over multiple tables which allows us to get all images from all notes that a given user has access to.


-> Link to machine readable API documentation in the form of Swagger UI - http://localhost:8080/swagger-ui/index.html#/
-> Database Setup

Prerequisites:


PostgreSQL installed on your machine.


Steps:


Create PostgreSQL Database:

psql -U postgres
CREATE USER <YOUR_USERNAME> WITH PASSWORD '<YOUR_PASSWORD>';
CREATE DATABASE <YOUR_DATABASE_NAME> OWNER <YOUR_USERNAME>;




Update Application Configuration:
Open src/main/resources/application.properties and set:

spring.datasource.url=jdbc:postgresql://localhost:5432/<YOUR_DATABASE_NAME>
spring.datasource.username=<YOUR_USERNAME>
spring.datasource.password=<YOUR_PASSWORD>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect





Link to join Postman work-space which describes all API endpoints with data
https://app.getpostman.com/join-team?invite_code=8c9625ceda8c4df4302d5160d753d448&target_code=4069caaedbe5c259e6e5604e5c8f197a
