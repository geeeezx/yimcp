# Yi Master MCP Server

## Overview
Yi Master MCP Server is a learning tutorial for Model Context Protocol implementation that integrates I Ching divination capabilities. This project demonstrates how to develop AI-powered divination services using Spring.ai and allows users to perform Six Lines (Liuyao) divination through Claude desktop interface.

## Features
- Model Context Protocol (MCP) implementation tutorial
- Six Lines (Liuyao) I Ching divination system
- Integration with Spring.ai framework
- Compatible with Claude desktop access
- Interactive divination experience

## Prerequisites
- Java Development Kit (JDK 17 or higher)
- Maven or Gradle
- Spring Boot knowledge
- Basic understanding of Spring.ai
- Claude API credentials (for desktop access)

## Installation
1. Clone the repository:
   ```
   git clone https://github.com/yourusername/yimaster-mcp.git
   cd yimaster-mcp
   ```

2. Build the project:
   ```
   ./mvnw clean install
   ```



## Usage
After starting the application, you can access the Yi Master divination service through:

1. Claude Desktop client: Connect to the server endpoint
2. Web interface: Access `http://localhost:8080` in your browser
3. API: Send requests to the divination endpoints

### Performing Six Lines (Liuyao) Divination
1. Start a new divination session
2. Focus on your question or concern
3. Follow the prompts to generate the six lines
4. Receive and interpret your hexagram reading

## Model Context Protocol (MCP) Learning
This project serves as a tutorial for understanding Model Context Protocol:

- How to structure context for AI interactions
- Implementing Spring.ai integrations
- Managing conversation state and context
- Optimizing prompts for divination accuracy

## Documentation
Detailed documentation is available in the `docs` directory:
- API Reference
- YI Basics
- Hexagram Interpretations
- MCP Implementation Guide

## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.

## License
This project is licensed under the MIT License - see the LICENSE file for details.
