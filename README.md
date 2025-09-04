# Groovy Selenium Web Automation

This project contains web automation tests using **Groovy + Selenium** and Gradle. The tests are designed to run on **any OS** (Windows, macOS, Linux) with minimal setup.

## Requirements

- Java JDK 17 or higher
- Gradle (if not using Gradle Wrapper)
- Chrome and/or Firefox browsers installed
- Internet access for Selenium WebDriver

## Setup

1. Clone the repository
```bash
git clone https://github.com/chamorata/technical-exam.git
cd <your-repo>
```
2. Use this command for local run
```bash 
./gradlew test -Denv=<environment>
```