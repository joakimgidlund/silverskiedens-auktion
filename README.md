## Silverskiedens Auktion

Microservice project for backendprogramming part 2.

A small webapp emulating an auction service.

### Launching the project
1. Place your shell in /docker
2. Run the bash script <code>./build.sh</code>
3. Run <code>docker compose up</code>
4. Make sure all containers start up properly. Either with <code>docker ps</code> or checking the status in Docker Desktop.

If any of the services haven't started properly, chances are the database containers didn't finish launching before the services.

In this case, just start the services manually again.

When all containers are up and running, go to <code>http://localhost:8085</code> to access the frontend.
Logging in is necessary to access all functions. There is a test user with username "admin" and password "password".
Registering your own also works.

When logged in, all functions are available. The functions in the frontend use most of the available endpoints. The ones that aren't implemented in the frontend are not very interesting currently.

Test data was inserted by SQL-files when creating the containers, but more lots can be created and bid on.

<strong>Disclaimer:</strong> for education purposes only. Mostly tested on Chrome.
