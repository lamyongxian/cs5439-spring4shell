import socket

#Get IP address of webapp
hostname = socket.gethostname()
url_name = socket.gethostbyname(hostname)
print("The url supplied to the SAST scan is " + url_name + ".")
