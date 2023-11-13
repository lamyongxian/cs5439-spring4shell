#!/bin/bash

VAGRANT_HOST_DIR=/mnt/host_machine

sudo apt-get update > /dev/null 2>&1
echo "[+]" $( date +%T ) "Installing Java"
sudo apt-get -y install default-jdk > /dev/null 2>&1

#echo "[+]" $( date +%T ) "Installing Node & npm"
#$#curl -sL https://deb.nodesource.com/setup_6.x | sudo -E bash - > /dev/null 2>&1
#sudo apt-get -y install nodejs npm > /dev/null 2>&1

echo "[+]" $( date +%T ) "Installing pip"
sudo apt-get install -y python3-pip > /dev/null 2>&1

########################
# Docker
########################
echo "[+]" $( date +%T ) "Installing Docker"
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add - >/dev/null 2>&1
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" >/dev/null 2>&1
sudo apt-get update > /dev/null 2>&1
sudo apt-get -y install docker-ce > /dev/null 2>&1
sudo systemctl enable docker > /dev/null 2>&1
#docker-compose
echo "[+]" $( date +%T ) "Installing Docker Compose"
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose >/dev/null 2>&1
sudo chmod +x /usr/local/bin/docker-compose >/dev/null 2>&1


#git
echo "[+]" $( date +%T ) "Installing Git"
sudo apt-get install -y git >/dev/null 2>&1

# MySQL install
#echo "[+]" $( date +%T ) "Installing MySQL"
# Download and Install the Latest Updates for the OS
sudo dpkg-reconfigure -f noninteractive tzdata >/dev/null 2>&1

# Install essential packages
sudo apt-get -y install zsh htop >/dev/null 2>&1

# Install MySQL Server in a Non-Interactive mode. Default root password will be "root"
#sudo apt-get -y install mariadb-server-10.5 >/dev/null 2>&1

# Enable Ubuntu Firewall and allow SSH & MySQL Ports
sudo ufw enable >/dev/null 2>&1
sudo ufw allow 22 >/dev/null 2>&1
sudo ufw allow 3306 >/dev/null 2>&1
sudo ufw allow 8080 >/dev/null 2>&1
sudo ufw allow 8081 >/dev/null 2>&1
sudo ufw allow 8082 >/dev/null 2>&1
sudo ufw allow 8083 >/dev/null 2>&1
sudo ufw allow 80 >/dev/null 2>&1

# Install Maven
echo "[+]" $( date +%T ) "Install Maven"
sudo apt -y install maven >/dev/null 2>&1

# Reboot
#echo "[+]" $( date +%T ) "Rebooting..."
#sudo reboot >/dev/null 2>&1

# Clone Repo
echo "[+]" $( date +%T ) "Clone Repo"
mkdir -p -m a=rwx /home/vagrant/spring4shell >/dev/null 2>&1
git clone https://github.com/lamyongxian/cs5439-spring4shell.git /home/vagrant/spring4shell >/dev/null 2>&1
pwd

# Run Maven
echo "[+]" $( date +%T ) "Run Maven Package"
cd /home/vagrant/spring4shell && sudo mvn clean package -DskipTests >/dev/null 2>&1

# Docker compose
echo "[+]" $( date +%T ) "Run Docker Compose"
cd /home/vagrant/spring4shell && pwd
sudo docker-compose -f /home/vagrant/spring4shell/docker-compose.yml up --build -d

echo "Success"
