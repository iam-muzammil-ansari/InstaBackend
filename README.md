# InstaBackend

## Table Of Contents
- [Frameworks and Language Used](#frameworks-and-language-used)
- [Data Flow](#data-flow)
  - [Controller](#controller)
  - [Services](#services)
  - [Repository](#repository)
  - [BeanFactory](#beanfactory)
  - [Database Design](#database-design)
- [Data Structures Used](#data-structures-used)
- [Project Summary](#project-summary)
- [Getting Started](#getting-started)
- [Testing Endpoints](#testing-endpoints)
- [License](#license)

## Frameworks and Language Used
- Spring Boot
- Java
- Maven
- Dependencies:
  - Spring Web
  - Lombok
  - Spring Data JPA
  - Validation
  - MySql Driver
  - javaX mailApi

## Data Flow

### Controller
- Controllers are responsible for handling incoming HTTP requests and returning appropriate responses.
- They interact with service classes to process business logic.

### Services
- Services handle the business logic of the application.
- They interact with repositories to perform database operations.
- Service classes include:
  - `AuthenticationAdminService`
  - `AuthenticationService`
  - `CommentService`
  - `FollowService`
  - `LikeService`
  - `PostService`
  - `UserService`

### Repository
- Repositories handle database CRUD operations and data access.
- They interact with the database to store and retrieve data.

### BeanFactory
- EmailHandler

The `EmailHandler` is a utility class used in the project to handle email-related tasks. It is not explicitly defined as a Spring Bean in the code provided, but it can be designed as a singleton bean managed by the Spring container to ensure its availability throughout the application.

Purpose: The `EmailHandler` is responsible for sending emails to users. In the context of the project, it is used to send authentication tokens to users when they sign in or perform other actions that require email verification.

Implementation: The implementation of the `EmailHandler` class would include methods to compose and send emails using a third-party email service or library. The specific implementation details may vary based on the chosen email service or library.

- PasswordEncrypter

The `PasswordEncrypter` is a utility class used in the project to securely encrypt passwords. Like the `EmailHandler`, it is not explicitly defined as a Spring Bean in the code provided, but it can be designed as a singleton bean managed by the Spring container for easy access.

Purpose: The `PasswordEncrypter` class serves the purpose of encrypting passwords before they are stored in the database. This is a crucial security measure to protect user credentials from unauthorized access.

Implementation: The implementation of the `PasswordEncrypter` class would include methods to perform one-way password encryption using industry-standard algorithms like bcrypt or PBKDF2. These algorithms are designed to be computationally expensive, making it difficult for attackers to reverse-engineer the original passwords from the encrypted values.

By configuring the `EmailHandler` and `PasswordEncrypter` classes as Spring Beans within the Bean Factory, the application can effectively utilize these utility classes for their respective functionalities.

### Database Design
- The database design for the above Spring Boot code involves several entities and their relationships. Below is an overview of the main entities and their associations:
1. Entity Relationship Diagram (ERD)

```+------------------+      +------------------+      +------------------+      +------------------+      +------------------+
|      User        |      |    Post          |      |     Comment      |      |      Like        |      |      Follow      |
+------------------+      +------------------+      +------------------+      +------------------+      +------------------+
| userId (PK)      |      | postId (PK)      |      | commentId (PK)   |      | likeId (PK)      |      | followId (PK)    |
| userHandle       |      | postContent     |      | commentContent   |      | instaPost        |      | currentUser      |
| userEmail        |      | postOwner (FK)  |      | commenter (FK)   |      | liker (FK)       |      | currentUserFollower (FK) |
+------------------+      +------------------+      +------------------+      +------------------+      +------------------+ 
````


2. Entity Descriptions

   - `User` : Represents a registered user in the system.
      - `userId` (`Primary Key`): Unique identifier for the user.
      - `userHandle` : The handle/username of the user.
      - `userEmail` : The email address of the user.
      - `Post` : Represents a post created by a user.
      - `postId` (`Primary Key`): Unique identifier for the post.
      - `postContent` : The content of the post.
      - `postOwner` (`Foreign Key - User`): The user who created the post.

    - `Comment` : Represents a comment made by a user on a post.
        - `commentId` (`Primary Key`): Unique identifier for the comment.
        - `commentContent` : The content of the comment.
        - `commenter` (`Foreign Key - User`): The user who made the comment.
        - `instaPost` (`Foreign Key - Post`): The post on which the comment was made.

    - `Like` : Represents a like given by a user to a post.
       - `likeId` (`Primary Key`): Unique identifier for the like.
       - `instaPost` (`Foreign Key - Post`): The post that was liked.
       - `liker` (`Foreign Key - User`): The user who liked the post.

    - `Follow` : Represents a follow relationship between users.
       - `followId` (`Primary Key`): Unique identifier for the follow relationship.
       - `currentUser` (`Foreign Key - User`): The user being followed.
       - `currentUserFollower` (`Foreign Key - User`): The user who is following.

3. Relationships

   - Each post (`Post`) belongs to a single user (`User`) who is the owner of the post.
   - Each comment (`Comment`) is associated with a single user (`User`) who made the comment and a single post (`Post`) on which the comment was made.
   - Each like (`Like`) is associated with a single user (`User`) who liked the post and a single post (`Post`) that was liked.
   - Each follow (`Follow`) represents a relationship between two users (`User`) - the user being followed and the user who is following.
## Project Summary
- InstaBackend is a Spring Boot-based backend application for a social media platform.
- It provides functionalities related to user authentication, posts, comments, likes, and follows.

## Data Structures Used
The data structure used in the above Spring Boot project include:

- List: Lists are used in various places to store collections of objects. For example:
    - In FollowService, a List of Follow objects is returned when querying for follow relationships between a user and their followers.
    - In LikeService, a List of Like objects is returned when querying for likes on a specific post.

## Getting Started
- Clone the repository from [instaBackend](https://github.com/ayaan097/InstaBackend.git).
- Install the required dependencies and packages.
- Configure the database connection.
- Build and run the application.

## Testing Endpoints
1. User Registration and Authentication Endpoints
   - `POST` /signup - Register a new user.

   - `POST` /signin - Sign in a user and generate an authentication token.

2. Post Endpoints
   - `POST` /posts - Create a new post.

   - `GET` /posts/{postId} - Get a specific post by its ID.

   - `DELETE` /posts/{postId} - Delete a post by its ID.

   - `GET` /posts - Get all posts.

3. Comment Endpoints
   - `POST` /comments - Add a new comment to a post.

   - `GET` /comments/{commentId} - Get a specific comment by its ID.

   - `DELETE` /comments/{commentId} - Delete a comment by its ID.

4. Like Endpoints
    - `POST` /likes - Like a post.

    - `GET` /likes/{likeId} - Get a specific like by its ID.

    - `DELETE` /likes/{likeId} - Unlike a post.

5. Follow Endpoints
    - `POST` /follow - Start following a user.

    - `GET` /follow/{followId} - Get a specific follow relationship by its ID.

    - `DELETE`/follow/{followId} - Unfollow a user.

6. Admin Endpoints
    - `GET` /admin/posts -Get All users posts.

    - `DELETE`/admin/posts/{postId} - Remove a post by its ID (admin access only).

## License
- This project is Open Source.
