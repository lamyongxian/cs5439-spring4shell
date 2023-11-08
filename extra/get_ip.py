import socket, os

#Get IP address of webapp
hostname = socket.gethostname()
url_name = "http://" + socket.gethostbyname(hostname) + ":8080/spring4shell/"
print("The url supplied to the SAST scan is " + url_name + ".")

#Execute OWASP ZAP (SAST)
sast_scan = "docker run -t owasp/zap2docker-stable zap-baseline.py -t " + url_name
print("The command that ran was " + sast_scan + ".")
os.system(sast_scan)
