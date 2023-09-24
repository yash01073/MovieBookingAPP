# Use an official Node.js runtime as a parent image
FROM node:18

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install Angular CLI globally
RUN npm install -g @angular/cli

# Install application dependencies
RUN npm install

# Copy the rest of the application code to the working directory
COPY . .

# Build the Angular app for production
RUN ng build --configuration production

# Expose port 8081 for serving the application
EXPOSE 8081

# Start the application on port 8081
CMD ["ng", "serve", "--host", "0.0.0.0", "--port", "8081"]
