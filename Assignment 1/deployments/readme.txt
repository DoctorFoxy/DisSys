Each subfolder contains its own service.

Inside each subfolder:

You can use ./run-rest-service to start the server.
It starts in the background! Even when disconnecting the VM the server stays on!
Make sure you don't run multiple instances of the service at once!

All the console output of the server is being written into the .log file.

To see if the process is running:
Use command htop (sort by mem usage) to see if the server process is running

To kill a running process:
Use command htop (sort by mem usage) to find the server process and send a SIGTERM using the F-keys (see the controls on bottom of htop)


