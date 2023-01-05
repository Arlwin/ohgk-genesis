# OHGK - Genesis

## Objectives
- Learn React and SpringBoot as SPA Tech Stacks.
- Learn AWS Serverless Architecture.
- Display finished projects as a portfolio.

## Tech Stack
<table>
    <tr>
        <th>Backend</th>
        <td>Java, Springboot</td>
    </tr>
    <tr>
        <th>Frontend</th>
        <td>ReactJS, MUI</td>
    </tr>
    <tr>
        <th>Infrastructure</th>
        <td>AWS API Gatewaym, AWS Lambda</td>
    </tr>
    <tr>
        <th>Database</th>
        <td>AWS DynamoDB</td>
    </tr>
    <tr>
        <th>Wireframing</th>
        <td>Figma</td>
    </tr>
</table>

## Guide
### How to run API in Docker
1. Modify `Dockerfile` file and add the following lines.

    ```
    COPY build/libs/api.jar /opt/app
    ...
    # Add these lines
    ENV AWS_ACCESS_KEY_ID=<Put Access Key here>
    ENV AWS_SECRET_ACCESS_KEY=<Put Secret Key here>
    ENV AWS_REGION=us-west-2
    #
    ...
    CMD ["java", "-jar", "/opt/app/api.jar"]
    ```

2. Build image by using the command (Run it inside the ./api folder).

    ```
    docker build -t api .
    ```

3. Run the image by using the command.

    ```
    docker run -it --rm -p 8080:8080 api
    ```

## RoadMap
- [x] Create Wireframe
- [x] Define Data / Create Datastore
- [x] Create Backend
- [ ] Create Frontend
- [ ] Create Infrastructure
- [ ] Deploy to Infrastructure
- [ ] Implement CICD
- [ ] Convert Infrastructure to IaC

## MVP
- As Anyone
    - Can see the list of my Projects based on my username.
- As me (Admin)
    - Log in
        - Create / Update / Delete my Projects.

## Version 2
- As Anyone
    - Can Sign Up
    - Can Log in
- As User
    - Create / Update / Delete own Projects.
- As Admin
    - Create / Update / Delete Users.