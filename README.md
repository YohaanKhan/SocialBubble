# Social Media App - Spring Boot Learning Project

## Overview
This is a Spring Boot backend developed as a learning project to explore Java, Spring Framework, and API design. It implements core features of a social media app, serving as a proof-of-concept. The project is incomplete, with authentication and authorization not implemented, as I’ve decided to move to a new tech stack and project.

## Features
- **User Profiles**: Create, view, and update profiles with name, bio, and profile photo (Base64 encoded).
- **Post System**: Create posts with photos and diary entries, visible to friends or public (settings TBD).
- **Like & Comment System**: Like and comment on posts with timestamps and authors.
- **Friend Requests**: Send, accept, and reject friend requests with status tracking.
- **Chat System**: Basic messaging support (real-time via WebSocket or polling, not fully optimized).

## Tech Stack
- **Backend**: Spring Boot (Java)
- **Database**: MongoDB (via Spring Data)
- **Dependencies**: Spring Web, Spring Data MongoDB, Validation
- **Tools**: Maven, Postman (for testing)

## Learning Outcomes
- Gained hands-on experience with Spring Boot, JPA mappings, and REST API development.
- Implemented and tested core services (UserService, PostService, FriendRequestService, MessageService).
- Learned exception handling with GlobalExceptionHandler.
- Practiced controller refactoring and endpoint testing.

## Status
- **Completed**:
  - Entity modeling and JPA relationships.
  - REST API endpoints for all features.
  - Core service logic and controller implementation.
  - Endpoint testing with Postman.
- **Incomplete**:
  - Authentication and authorization (JWT-based auth not added).
  - Real-time chat optimization.
  - Frontend integration.
  - Deployment and scaling.

## Installation
1. Clone the repository: `git clone https://github.com/your-username/social-media-app.git`
2. Navigate to the project directory: `cd social-media-app`
3. Ensure MongoDB is running (local or Atlas).
4. Build the project: `mvn clean install`
5. Run the application: `mvn spring-boot:run` or use IntelliJ’s Run button.
6. Test APIs with Postman (e.g., `GET /api/test/users`, `POST /api/test/posts`).

## Usage
- Use Postman to interact with the APIs (e.g., create users, posts, send friend requests).
- Note: No authentication is implemented, so all endpoints are publicly accessible for testing.

## Future Work
- Add JWT-based authentication and authorization.
- Develop a frontend (e.g., React) for a complete experience.
- Deploy using Docker on Heroku or Railway.
- Optimize with caching and scaling features.

## Contributing
This project is a personal learning exercise and is paused. Feel free to fork and build upon it!

## Acknowledgments
- Learned Spring Boot and API design through this project.
- Thanks to xAI’s Grok for guidance!
