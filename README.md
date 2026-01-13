# Quiz System

## Overview
This project is a **Java-based Quiz System**. The system loads user data and quiz questions from external files, allows users to register and log in, take quizzes by topic, view results, and compete on leaderboards. The focus of this project is on **programming implementation**, not software architecture design.

The application uses a **GUI-based interface (Swing)** and supports persistent storage for users, questions, and quiz scores.

---

## Functional Requirements

### 1. Startup Behavior
When the program starts, it performs the following actions:

- Loads **user information** from a CSV file
- Loads **quiz questions** from XML files
- Stores all loaded data in memory using appropriate data structures
- Displays information about the loaded data
- Validates all questions
- Displays a menu with available user interactions

#### User Data
- User information is stored in a **CSV file**
- Each row contains:
  1. User ID  
  2. Username  
  3. Password  

#### Question Data
- Questions are stored in **XML files**

- A provided class `ReadQuestions` is used to load questions into memory
- Each question is validated to ensure:
  - It belongs to a topic
  - It has a question statement
  - It has more than one answer option
  - It has exactly one correct answer

> ⚠️ The external quiz library must be added to the project classpath before running:
```
repositories/xjtlu/cpt111/xjtlu.cpt111.assignment.quiz.lib/0.0.1/
xjtlu.cpt111.assignment.quiz.lib-0.0.1.jar
```
## Menu Functions

The system supports the following core functionalities:

### User Management
- User registration
- User authentication (login)

### Topic Selection
- Available quiz topics are automatically derived from the loaded questions

### Quiz Taking
- Each quiz contains multiple questions of **different difficulty levels**
- Questions are displayed **one by one**
- Answer options are **shuffled** each time a question is shown
- The number of questions per quiz is configurable

### Quiz Results
- The quiz score is displayed immediately after completion
- Results are saved to a **score file**
- The score file format is user-defined and must be readable when the program restarts

### User Dashboard
- Displays quiz results by topic
- Shows the **last three quiz attempts** for each user

### Leaderboard
- Displays users with the **highest scores per topic**

---

### Technologies
- Java SE
- Java Swing (GUI)
- CSV file processing
- XML parsing
- Object-Oriented Programming (OOP)

### How to Run
- Add the provided quiz library (.jar) to your IDE classpath
- Ensure user.csv and XML question files exist in the resources directory
- Run the main application class
- Use the GUI to register, log in, and start quizzes
