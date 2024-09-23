# Costify

Costify is a Java-based console application designed to help users estimate and manage project costs. By offering features such as user management, project tracking, and cost analysis tools, Costify aims to empower individuals and businesses to better understand and control their project expenses. The application provides users with insights into their project costs and helps them manage materials and labor effectively.

## 🚀 About Me
Coding enthusiast and full stack developer who loves solving problems and bringing ideas to reality using web development tools.

I'm a developer who loves and uses Laravel to create web applications. My motto in web development is to create something that I want to use as a client because I think that if we understand our customers' needs, we will satisfy them. Web development is not just about a website but instead a brand and an identity that represents the developer and the client. I will do the best I can to develop the best product.

## Features

- **User Registration and Login**: Users can create an account and log in to manage their projects.
- **Manage Projects**: Users can add projects with details such as materials and labor costs.
- **Generate Estimates**: Users can generate estimates for their projects showing material and labor costs.
- **View and Edit Personal Information**: Users can view and update their personal information.
- **View Projects and Estimates**: Users can view a list of their projects and estimates.
- **Filter Projects**: Users can filter projects by various criteria such as total cost, date range, or specific activities.

## Code Overview

The project contains the following key components:

- **Auth/**
  - **Login**: Handles user login functionality.
  - **Register**: Manages user registration processes.

- **config/**
  - **Database**: Contains configuration settings for database connections.

- **Domain/**
  - **Enum/**
    - **ProjectType**: Enum class defining different types of projects.
  - **Material**: Represents material-related cost data.
  - **Labor**: Represents labor-related cost data.
  - **Estimate**: Generates estimates based on project data.
  - **Project**: Represents a project in the system, including materials and labor costs.
  - **User**: Represents a user in the system, including personal information and a list of projects and estimates.

- **Repository/**
  - **MaterialRepository**: Data access layer for handling material data.
  - **LaborRepository**: Data access layer for handling labor data.
  - **EstimateRepository**: Data access layer for handling estimate data.
  - **ProjectRepository**: Data access layer for handling project data.
  - **UserRepository**: Data access layer for handling user data.

- **Services/**
  - **Implementations/**
    - **MaterialService**: Business logic related to material data.
    - **LaborService**: Business logic related to labor data.
    - **EstimateService**: Business logic related to estimate generation.
    - **ProjectService**: Business logic related to project management.
    - **UserService**: Business logic related to user management.

- **utils/**
  - **DateUtil**: Utility functions for handling date operations.

- **Main**: Entry point of the application, handles user menus and interactions.

## 🛠 Skills
<p>
    <img src="https://skillicons.dev/icons?i=git,idea,java" height="45" alt="skills"  />
</p>
